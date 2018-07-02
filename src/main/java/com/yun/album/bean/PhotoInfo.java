package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 图片信息
 */
public class PhotoInfo {
    /** 编号 */
    private long id;
    /** 图片类型编号 */
    private long category;
    /** 名称 */
    private String title;
    /** 宽度 */
    private int width;
    /** 高度 */
    private int height;
    /** 经度 */
    private double longitude;
    /** 纬度 */
    private double latitude;
    /** 拍摄时间 */
    private LocalDateTime shootTime;
    /** 文件大小 */
    private long size;
    /** 缩略图路径 */
    private String thumbnailPath;
    /** 正常图路径 */
    private String normalPath;
    /** 原图路径 */
    private String originalPath;
    /** 上传时间 */
    private LocalDateTime uploadTime;
}
