package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 文件夹
 */
public class Folder {
    private long id;
    private long parentId;
    private String name;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;

    public Folder() {}

    public Folder(long id, long parentId, String name, LocalDateTime createTime) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.updateTime = createTime;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public long getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
}
