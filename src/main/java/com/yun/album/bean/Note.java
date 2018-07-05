package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 笔记
 */
public class Note {
    /** 编号 */
    private long id;
    /** 名称 */
    private String name;
    /** 内容 */
    private String content;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 创建时间 */
    private LocalDateTime createTime;

    private Note() {}

    public Note(long id, String name, String content, LocalDateTime createTime) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.updateTime = createTime;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
}
