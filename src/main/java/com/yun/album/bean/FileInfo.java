package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 文件信息
 */
public class FileInfo {
    /** 编号 */
    private long id;
    /** 文件类型编号 */
    private long category;
    /** 名称 */
    private String title;
    /** 文件大小 */
    private long size;
    /** 文件路径 */
    private String filePath;
    /** 上传时间 */
    private LocalDateTime uploadTime;
}
