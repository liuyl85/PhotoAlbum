package com.yun.album.bean;

import java.time.LocalDateTime;

/**
 * 管理员
 */
public class Manager {
    /** 编号 */
    private long id;
    /** 上级编号 */
    private long parentId;
    /** 帐号 */
    private String acc;
    /** 密码 */
    private String pwd;
    /** 名称 */
    private String name;
    /** 手机 */
    private String mobile;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 上次登录IP */
    private String lastLoginIp;
    /** 上次登录时间 */
    private LocalDateTime lastLoginTime;
}
