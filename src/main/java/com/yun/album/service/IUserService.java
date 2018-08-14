package com.yun.album.service;

import com.yun.album.bean.User;
import com.yun.album.vo.UserRegisterVo;

/**
 * 用户操作接口
 */
public interface IUserService {

    /**
     * 获取验证码
     * @param phone 用户电话
     * @return 操作结果
     */
    int getValidationCode(String phone);

    /**
     * 用户注册
     * @param vo 用户信息
     * @return 操作结果
     */
    int register(UserRegisterVo vo);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return (int:错误码 User:用户对象)
     */
    Object login(String username, String password);

    /**
     * 刷新令牌
     * @param oldToken 原令牌
     * @return 新令牌
     */
    String refreshToken(String oldToken);

    /**
     * 编辑密码
     * @param user 用户对象
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 操作结果
     */
    int editPassword(User user, String oldPwd, String newPwd);

    /**
     * 重设密码
     * @param phone 用户电话
     * @param pwd 密码
     * @param code 验证码
     * @return 操作结果
     */
    int resetPassword(String phone, String pwd, String code);


}
