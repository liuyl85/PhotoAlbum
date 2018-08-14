package com.yun.album.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yun.album.bean.Folder;
import com.yun.album.bean.PageInfo;
import com.yun.album.bean.Photo;
import com.yun.album.bean.PhotoInfo;
import com.yun.album.dao.PhotoDao;
import com.yun.album.service.PhotoService;
import com.yun.album.util.IdFactory;
import com.yun.album.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;

/**
 * 图片操作接口实现
 */
@Service
public class PhotoServiceImpl implements PhotoService {
    private Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    @Resource
    private PhotoDao photoDao;

    @Override
    public boolean createFolder(long userId, long parentId, String folderName) {
        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return false;
        }

        if(photoDao.selectFolderExist(userId, parentId) < 1){
            return false;
        }

        Folder folder = new Folder(newId, parentId, folderName, LocalDateTime.now());
        photoDao.insertFolder(userId, folder);
        return true;
    }

    @Override
    public boolean addPhotoInfo(long userId, long folderId, String photoName, String photoPath) {
        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return false;
        }

        if(photoDao.selectFolderExist(userId, folderId) < 1){
            return false;
        }

        PhotoInfo photoInfo = readPhotoInfo(folderId, photoName, photoPath);
        if(photoInfo == null){
            return false;
        }

        photoDao.insertPhoto(userId, photoInfo);
        return true;
    }

    @Override
    public boolean renameFile(long userId, long fileId, String fileName) {
        int result = photoDao.updateFile(userId, fileId, null, fileName, LocalDateTime.now());
        return result == 1;
    }

    @Override
    public boolean moveFile(long userId, long fileId, long folderId) {
        if(photoDao.selectFolderExist(userId, folderId) < 1){
            return false;
        }

        int childCount = photoDao.selectChildExist(userId, folderId, fileId);
        if(childCount > 0){
            return false;
        }

        int result = photoDao.updateFile(userId, fileId, folderId, null, LocalDateTime.now());
        return result == 1;
    }

    @Override
    public boolean deleteFolder(long userId, long folderId) {
        int result = photoDao.selectChildNumber(userId, folderId);
        if(result > 0){
            return false;
        }

        result = photoDao.deleteFolder(userId, folderId);
        return result == 1;
    }

    @Override
    @Transactional
    public boolean deleteFile(long userId, long... fileIds) {
        int result = photoDao.deletePhoto(userId, fileIds);
        return fileIds.length == result;
    }

    @Override
    public Photo getPhotoById(long userId, long photoId) {
        return photoDao.selectPhotoById(userId, photoId);
    }

    @Override
    public PageInfo<PhotoInfo> getFileByParentId(long userId, long folderId, int pageNum, int pageSize) {
        Page<PhotoInfo> page = PageHelper.startPage(pageNum, pageSize);
        photoDao.selectFileByParentId(userId, folderId);
        return new PageInfo<>(page);
    }

    private PhotoInfo readPhotoInfo(long folderId, String photoName, String photoPath) {
        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return null;
        }

        int width = 0;
        int height = 0;
        LocalDateTime shootTime = null;
        String longitude = null;
        String latitude = null;
        File photoFile = new File(photoPath);
        int imgAttrCount = 5;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(photoFile);
            myFor : for(Directory directory : metadata.getDirectories()){
                for(Tag tag : directory.getTags()){
                    String tagName = tag.getTagName();  //标签名
                    String desc = tag.getDescription(); //标签信息
                    switch (tagName) {
                        case "Image Height"://图片高度
                            height = Integer.parseInt(desc.substring(0, desc.indexOf(' ')));
                            --imgAttrCount;
                            break;
                        case "Image Width"://图片宽度
                            width = Integer.parseInt(desc.substring(0, desc.indexOf(' ')));
                            --imgAttrCount;
                            break;
                        case "Date/Time Original":
                            shootTime = Tools.parseStringToDateTime(desc);
                            --imgAttrCount;
                            break;
                        case "GPS Latitude"://纬度
                            latitude = desc;
                            --imgAttrCount;
                            break;
                        case "GPS Longitude"://经度
                            longitude = desc;
                            --imgAttrCount;
                            break;
                        default: continue;
                    }

                    if(imgAttrCount <= 0){
                        break myFor;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("read photo file error.", e);
            return null;
        }

        String originalPath = photoFile.getName();
        LocalDateTime nowTime = LocalDateTime.now();
        if(shootTime == null){
            shootTime = nowTime;
        }

        return new PhotoInfo(newId, folderId, photoName, width, height, longitude, latitude, shootTime, photoFile.length(), originalPath, nowTime);
    }
}
