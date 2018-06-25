package com.yun.album.bean;

import java.util.List;

public class User {
    private long id;
    private String acc;
    private String pwd;
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
