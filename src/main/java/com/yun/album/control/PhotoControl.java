package com.yun.album.control;

import com.yun.album.bean.*;
import com.yun.album.common.Constant;
import com.yun.album.common.StatusCode;
import com.yun.album.common.properties.SystemProperties;
import com.yun.album.security.JwtTokenUtil;
import com.yun.album.service.PhotoService;
import com.yun.album.util.ImageUtils;
import com.yun.album.util.RedisUtils;
import com.yun.album.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

@RestController
@RequestMapping("/photo")
public class PhotoControl {
    private final Logger logger = LoggerFactory.getLogger(PhotoControl.class);
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SystemProperties systemProperties;
    @Resource
    private PhotoService photoService;

    @PostMapping(value = "/create", params = {"parentId", "name"})
    public ResultData createFolder(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long parentId, String name) {
        try {
            if(StringUtils.isEmpty(name) || !name.matches("^\\w{1,10}$")){
                return new ResultData(StatusCode.PARAMS_IS_WRONGFUL);
            }

            boolean result = photoService.createFolder(user.getId(), parentId, name);
            return new ResultData(result ? StatusCode.SUCCESS : StatusCode.ERROR);
        } catch (Exception e) {
            logger.error("create folder error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/rename", params = {"id", "name"})
    public ResultData renameFile(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long id, String name) {
        try {
            if(StringUtils.isEmpty(name) || !name.matches("^\\w{1,10}$")){
                return new ResultData(StatusCode.PARAMS_IS_WRONGFUL);
            }

            boolean renameResult = photoService.renameFile(user.getId(), id, name);
            return new ResultData(renameResult ? StatusCode.SUCCESS : StatusCode.ERROR);
        } catch (Exception e) {
            logger.error("rename file error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/move", params = {"id", "targetId"})
    public ResultData moveFile(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long id, long targetId) {
        try {
            boolean moveResult = photoService.moveFile(user.getId(), id, targetId);
            return new ResultData(moveResult ? StatusCode.SUCCESS : StatusCode.ERROR);
        } catch (Exception e) {
            logger.error("move file error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/delete_folder", params = {"id"})
    public ResultData deleteFolder(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long id) {
        try {
            boolean deleteResult = photoService.deleteFolder(user.getId(), id);
            return new ResultData(deleteResult ? StatusCode.SUCCESS : StatusCode.ERROR);
        } catch (Exception e) {
            logger.error("delete folder error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/delete", params = {"id"})
    public ResultData deletePhoto(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long id) {
        try {
            boolean deleteResult = photoService.deleteFile(user.getId(), id);
            return new ResultData(deleteResult ? StatusCode.SUCCESS : StatusCode.ERROR);
        } catch (Exception e) {
            logger.error("delete photo error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/upload", params = {"folderId", "file"})
    public ResultData uploadPhoto(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long folderId, MultipartFile file) {
        String savePath = null;
        try {
            if(file.isEmpty()){
                return new ResultData(StatusCode.FILE_IS_EMPTY);
            }

            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf('.'));
            String uuid = Tools.getUUID();

            savePath = MessageFormat.format("{0}/{1}/{2}{3}", systemProperties.getUploadPhotoPath(), String.valueOf(user.getId()), uuid, fileSuffix);
            Files.createDirectories(Paths.get(savePath).getParent());

            try(InputStream in = file.getInputStream(); BufferedInputStream bin = new BufferedInputStream(in); FileOutputStream out = new FileOutputStream(savePath)){
                byte[] buff = new byte[systemProperties.getUploadBufferSize()];
                int readLength;
                while((readLength = bin.read(buff)) != -1){
                    if(readLength > 0){
                        out.write(buff, 0, readLength);
                    }
                }
                out.flush();
            }

            boolean result = photoService.addPhotoInfo(user.getId(), folderId, fileName, savePath);
            if(result){
                return new ResultData(StatusCode.SUCCESS);
            }else{
                Files.delete(Paths.get(savePath));
                return new ResultData(StatusCode.ERROR);
            }
        } catch (Exception e) {
            logger.error("upload photo error.", e);
            if(savePath != null){
                try {
                    Files.delete(Paths.get(savePath));
                } catch (IOException ex) {
                    logger.error("delete file error.", ex);
                }
            }
            return new ResultData(StatusCode.ERROR);
        }
    }

    @GetMapping(value = "/download", params = {"id", "type"})
    public void downloadPhoto(HttpServletResponse res, @RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long id) {
        try {
            Photo photo = photoService.getPhotoById(user.getId(), id);
            if(photo == null || photo.getSize() == -1){
                res.setStatus(404);
                return;
            }

            String photoFileName = photo.getPath();

            res.setHeader("Content-Disposition", MessageFormat.format("attachment;filename={0}{1}", photo.getTitle(), photoFileName.substring(photoFileName.lastIndexOf('.'))));
            res.setHeader("Content-Type","application/octet-stream");
            res.setHeader("X-Accel-Redirect", MessageFormat.format("{0}/{1}/{2}", systemProperties.getDownloadPhotoPath(), String.valueOf(user.getId()), photoFileName));
        } catch (Exception e) {
            logger.error("download photo error.", e);
            res.setStatus(500);
        }
    }

    @GetMapping(value = "/icon", params = {"id", "token"})
    public void photoIcon(long id, String token, HttpServletResponse res) {
        try {
            String userAcc = jwtTokenUtil.getUserAccFromToken(token);
            if(userAcc == null){
                res.setStatus(404);
                return;
            }

            User user = redisUtils.get(MessageFormat.format(Constant.USER_KEY, userAcc));
            Photo photo = photoService.getPhotoById(user.getId(), id);
            if(photo == null || photo.getSize() == -1){
                res.setStatus(404);
                return;
            }

            String photoPath = MessageFormat.format("{0}/{1}/{2}", systemProperties.getUploadPhotoPath(), String.valueOf(user.getId()), photo.getPath());
            File file = new File(photoPath);
            if(file.isFile()){
                res.setHeader("Content-Type", "application/x-jpg");
                ImageUtils.compress(file, 64, 64, res.getOutputStream());
            }else{
                res.setStatus(404);
            }
        } catch (Exception e) {
            logger.error("get photo icon error.", e);
            res.setStatus(500);
        }
    }

    @GetMapping(value = "/list", params = {"folderId", "pageNum", "pageSize"})
    public ResultData getPhotoList(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long folderId, int pageNum, int pageSize) {
        try {
            if(pageNum < 1){
                return new ResultData(StatusCode.PARAMS_IS_WRONGFUL);
            }

            PageInfo<PhotoInfo> page = photoService.getFileByParentId(user.getId(), folderId, pageNum, pageSize);
            return new ResultData(StatusCode.SUCCESS, page);
        } catch (Exception e) {
            logger.error("get photo list error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

}
