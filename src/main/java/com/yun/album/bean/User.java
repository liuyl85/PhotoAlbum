package com.yun.album.bean;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 */
public class User {
    /** 编号 */
    private long id;
    /** 帐号 */
    private String acc;
    /** 密码 */
    private String pwd;
    /** 创建时间 */
    private LocalDateTime createTime;

    private List<String> roles;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getAcc() {
        return acc;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }
}
