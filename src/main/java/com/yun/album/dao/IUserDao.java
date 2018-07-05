package com.yun.album.dao;

import com.yun.album.bean.User;
import com.yun.album.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * 用户表操作接口
 */
@Component
public interface IUserDao {

    User selectByAcc(String acc) throws Exception;

    void insert(UserEntity user) throws Exception;
}
