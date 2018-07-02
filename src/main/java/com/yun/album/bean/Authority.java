package com.yun.album.bean;

/**
 * 权限
 */
public class Authority {
    /** 编号 */
    private int id;
    /** 上级权限编号 */
    private int parentId;
    /** 名称 */
    private String name;
    /** 动作 */
    private String action;
    /** 序号 */
    private byte index;
}
