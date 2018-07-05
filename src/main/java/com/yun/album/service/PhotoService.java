package com.yun.album.service;

import com.yun.album.bean.PageInfo;

public interface PhotoService {

    boolean addPhotoInfo();

    boolean delPhoto();

    void getPhoto();

    PageInfo getPhotoList(int pageNum, int pageSize);

}
