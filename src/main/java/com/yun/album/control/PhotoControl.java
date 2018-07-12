package com.yun.album.control;

import com.yun.album.bean.ResultData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("photo")
public class PhotoControl {

    public ResultData uploadPhoto(MultipartFile file) {
        return null;
    }

    public void downLoadPhoto() {

    }

    public ResultData getPhotoList() {
        return null;
    }

    public ResultData deletePhoto() {
        return null;
    }

}
