package com.yun.album.vo;

/**
 * 用户
 */
public class UserEditVo {
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

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

}
