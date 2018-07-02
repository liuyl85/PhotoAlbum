package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 用户图片类型
 */
public class PhotoCategory {
    /** 编号 */
    private long id;
    /** 上级编号 */
    private long parentId;
    /** 名称 */
    private String name;
    /** 序号 */
    private int index;
    /** 创建时间 */
    private LocalDateTime createTime;
}
