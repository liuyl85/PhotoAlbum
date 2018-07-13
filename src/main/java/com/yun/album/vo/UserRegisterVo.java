package com.yun.album.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 用户
 */
public class UserRegisterVo {
    /** 帐号 */
    @NotEmpty
    @Pattern(regexp = "^[a-z][a-z_0-9]{5,17}$")
    private String acc;
    /** 密码 */
    @NotEmpty
    @Length(min = 6, max = 18)
    private String pwd;
    /** 手机 */
    @NotEmpty
    @Pattern(regexp = "^[1](([3][0-9])|([4][579])|([5][^469])|([6][6])|([7][35678])|([8][0-9])|([9][8,9]))[0-9]{8}$")
    private String phone;

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

}
