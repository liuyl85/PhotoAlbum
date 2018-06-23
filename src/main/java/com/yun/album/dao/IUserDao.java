package com.yun.album.dao;

import com.yun.album.bean.User;
import org.springframework.stereotype.Component;

/**
 * 用户表操作接口
 */
@Component
public interface IUserDao {

    User selectByAcc(String acc);

    void insert(User user);
}
