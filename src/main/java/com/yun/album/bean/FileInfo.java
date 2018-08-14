package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 文件信息
 */
public class FileInfo {
    /** 编号 */
    private long id;
    /** 所在文件夹编号 */
    private long folderId;
    /** 名称 */
    private String title;
    /** 文件大小 */
    private Long size;
    /** 文件路径 */
    private String path;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 上传时间 */
    private LocalDateTime uploadTime;

    public FileInfo(long id, long folderId, String title, LocalDateTime uploadTime) {
        this.id = id;
        this.folderId = folderId;
        this.title = title;
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

    public Long getSize() {
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
