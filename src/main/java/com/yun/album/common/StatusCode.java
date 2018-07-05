package com.yun.album.common;

/**
 * 状态码
 */
public class StatusCode {
    /** 正常 */
    public static final int SUCCESS = 1;

    /** 系统错误 */
    public static final int ERROR = 101;
    /** 用户已存在 */
    public static final int USER_ALREADY_EXISTED = 102;
    /** MD5加密失败 */
    public static final int MD5_DIGEST_FAILED = 103;
    /** 用户名或密码错误 */
    public static final int ACC_OR_PWD_ERROR = 104;
    /** 创建Id失败 */
    public static final int CREATE_ID_ERROR = 105;
    /** 刷新令牌失败 */
    public static final int REFRESH_TOKEN_FAILED = 106;
}
