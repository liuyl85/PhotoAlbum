package com.yun.album.control;

import com.yun.album.bean.enums.PhotoType;
import com.yun.album.bean.ResultData;
import com.yun.album.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/album/photo")
public class PhotoControl {
    private final Logger logger = LoggerFactory.getLogger(PhotoControl.class);

    @PostMapping(value = "/upload")
    public ResultData uploadPhoto(MultipartFile file) {
        try {
            return null;
        } catch (Exception e) {
            logger.error("upload photo error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @GetMapping(value = "/download", params = {"id", "type"})
    public void downloadPhoto(long id, PhotoType type, HttpServletResponse res) {
        try {
//            res.setHeader("Content-Disposition", MessageFormat.format("attachment;filename={0}", versionInfo.getVersionName()));
//            res.setHeader("Content-Type","application/octet-stream");
//            res.setHeader("X-Accel-Redirect", "/photo/" + versionInfo.getFileName());
        } catch (Exception e) {
            logger.error("download photo error.", e);
            res.setStatus(500);
        }
    }

    @GetMapping(value = "/list")
    public ResultData getPhotoList() {
        try {
            return null;
        } catch (Exception e) {
            logger.error("get photo list error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/delete")
    public ResultData deletePhoto(@RequestParam long id) {
        try {
            return null;
        } catch (Exception e) {
            logger.error("delete photo error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

}
