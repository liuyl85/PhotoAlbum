package com.yun.album.service;

import com.yun.album.vo.UserVo;

/**
 * 用户操作接口
 */
public interface IUserService {

    /**
     * 用户注册
     * @param vo 用户信息
     * @return 操作结果
     */
    int register(UserVo vo);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 令牌
     */
    String login(String username, String password);

    /**
     * 刷新令牌
     * @param oldToken 原令牌
     * @return 新令牌
     */
    String refreshToken(String oldToken);

}
