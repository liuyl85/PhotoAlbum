package com.yun.album.common;

/**
 * 状态码
 */
public class StatusCode {
    /** 正常 */
    public static final int SUCCESS = 200;

    /** 系统错误 */
    public static final int ERROR = 500;
    /** 用户已存在 */
    public static final int USER_ALREADY_EXISTED = 520;
    /** MD5加密失败 */
    public static final int MD5_DIGEST_FAILED = 521;
    /** 用户名或密码错误 */
    public static final int ACC_OR_PWD_ERROR = 522;
    /** 创建Id失败 */
    public static final int CREATE_ID_ERROR = 523;
}
