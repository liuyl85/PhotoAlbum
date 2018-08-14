package com.yun.album.dao;

import com.yun.album.bean.User;
import com.yun.album.bean.UserAccount;
import com.yun.album.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 用户表操作接口
 */
@Component
public interface IUserDao {

    /**
     * 添加用户
     * @param user 用户对象
     */
    void insert(UserEntity user);

    /**
     * 根据帐号查询用户对象
     * @param acc 账号
     * @return 用户对象
     */
    User selectByAcc(String acc);

    /**
     * 根据电话查询用户帐号信息
     * @param phone 电话
     * @return 用户帐号信息
     */
    UserAccount selectAccByPhone(String phone);

    /**
     * 根据用户编号更新用户信息
     * @param id 用户编号
     * @param pwd 用户密码
     * @return 操作结果
     */
    int updateById(@Param("id") long id, @Param("pwd") String pwd);
}
