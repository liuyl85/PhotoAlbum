package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 图片信息
 */
public class PhotoInfo {
    /** 编号 */
    private long id;
    /** 所在文件夹编号 */
    private long folderId;
    /** 名称 */
    private String title;
    /** 宽度 */
    private int width;
    /** 高度 */
    private int height;
    /** 经度 */
    private String longitude;
    /** 纬度 */
    private String latitude;
    /** 拍摄时间 */
    private LocalDateTime shootTime;
    /** 文件大小 */
    private long size;
    /** 原图路径 */
    private String path;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 上传时间 */
    private LocalDateTime uploadTime;

    public PhotoInfo() {}

    public PhotoInfo(long id, long folderId, String title, int width, int height, String longitude,
                     String latitude, LocalDateTime shootTime, long size, String path, LocalDateTime uploadTime) {
        this.id = id;
        this.folderId = folderId;
        this.title = title;
        this.width = width;
        this.height = height;
        this.longitude = longitude;
        this.latitude = latitude;
        this.shootTime = shootTime;
        this.size = size;
        this.path = path;
        this.updateTime = uploadTime;
        this.uploadTime = uploadTime;
    }

    public long getId() {
        return id;
    }

    public long getFolderId() {
        return folderId;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public LocalDateTime getShootTime() {
        return shootTime;
    }

    public long getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
}
