package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 图片信息
 */
public class Photo {
    /** 编号 */
    private long id;
    /** 名称 */
    private String title;
    /** 宽度 */
    private int width;
    /** 高度 */
    private int height;
    /** 文件大小 */
    private long size;
    /** 原图路径 */
    private String path;
    /** 上传时间 */
    private LocalDateTime uploadTime;

    public long getId() {
        return id;
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

    public long getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
}
