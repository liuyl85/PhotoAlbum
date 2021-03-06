package com.yun.album.bean;

import java.io.Serializable;

/**
 * 用户
 */
public class User implements Serializable {
    /** 编号 */
    private long id;
    /** 帐号 */
    private String acc;
    /** 密码 */
    private String pwd;
    /** 手机 */
    private String phone;
    /** 名字 */
    private String name;
    /** 性别 */
    private byte sex;
    /** 令牌 */
    private String token;

    public long getId() {
        return id;
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

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public byte getSex() {
        return sex;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
