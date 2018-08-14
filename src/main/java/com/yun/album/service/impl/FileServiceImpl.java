package com.yun.album.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yun.album.bean.FileInfo;
import com.yun.album.bean.Folder;
import com.yun.album.bean.PageInfo;
import com.yun.album.dao.FileDao;
import com.yun.album.service.FileService;
import com.yun.album.util.IdFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 文件操作接口实现
 */
@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileDao fileDao;

    @Override
    public boolean createFolder(long userId, long parentId, String folderName) {
        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return false;
        }

        Folder folder = fileDao.selectFolderById(userId, parentId);
        if(folder == null){
            return false;
        }

        folder = new Folder(newId, parentId, folderName, LocalDateTime.now());
        fileDao.insertFolder(userId, folder);
        return true;
    }

    @Override
    public boolean addFileInfo(long userId, long folderId, FileInfo file) {
        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return false;
        }

        if(fileDao.selectFolderById(userId, folderId) == null){
            return false;
        }

        fileDao.insertFile(userId, file);
        return true;
    }

    @Override
    public boolean renameFile(long userId, long fileId, String fileName) {
        int result = fileDao.updateFile(userId, fileId, null, fileName, LocalDateTime.now());
        return result == 1;
    }

    @Override
    public boolean moveFile(long userId, long fileId, long folderId) {
        Folder folder = fileDao.selectFolderById(userId, folderId);
        if(folder == null){
            return false;
        }

        int childCount = fileDao.selectChildFile(userId, folderId, fileId);
        if(childCount > 0){
            return false;
        }

        int result = fileDao.updateFile(userId, fileId, folderId, null, LocalDateTime.now());
        return result == 1;
    }

    @Override
    public boolean deleteFolder(long userId, long folderId) {
        int result = fileDao.selectChildNumber(userId, folderId);
        if(result > 0){
            return false;
        }

        result = fileDao.deleteFolder(userId, folderId);
        return result == 1;
    }

    @Override
    public boolean deleteFile(long userId, long... fileIds) {
        int result = fileDao.deleteFile(userId, fileIds);
        return fileIds.length == result;
    }

    @Override
    public Folder getFolderById(long userId, long folderId) {
        return fileDao.selectFolderById(userId, folderId);
    }

    @Override
    public FileInfo getFileById(long userId, long fileId) {
        return fileDao.selectFileById(userId, fileId);
    }

    @Override
    public PageInfo<FileInfo> getFileByParentId(long userId, long folderId, int pageNum, int pageSize) {
        Page<FileInfo> page = PageHelper.startPage(pageNum, pageSize);
        fileDao.selectFileByParentId(userId, folderId);
        return new PageInfo<>(page);
    }


}
