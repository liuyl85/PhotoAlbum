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
    /** 密码错误 */
    public static final int PWD_IS_ERROR = 107;
    /** 新密码与原始密码相同 */
    public static final int TWO_PWD_IS_SAME = 108;
    /** 验证码过期 */
    public static final int VALIDATE_CODE_IS_EXPIRE = 109;
    /** 验证码错误 */
    public static final int VALIDATE_CODE_ERROR = 110;
    /** 帐号不存在 */
    public static final int ACCOUNT_IS_NOT_FIND = 111;
    /** 验证码CD中 */
    public static final int VALIDATE_CODE_IS_CDING = 112;
    /** 参数错误 */
    public static final int PARAMS_IS_WRONGFUL = 113;
    /** 文件是空 */
    public static final int FILE_IS_EMPTY = 114;
    /** 读取文件失败 */
    public static final int READ_FILE_FAILED = 115;
}
