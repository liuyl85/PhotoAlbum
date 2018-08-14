package com.yun.album.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.config")
public class SystemProperties {
    /** 服务器编号 */
    private int serverId;
    /** 文件上传路径 */
    private String uploadFilePath;
    /** 图片上传路径 */
    private String uploadPhotoPath;
    /** 上传缓存大小 */
    private int uploadBufferSize;
    /** 文件下载路径 */
    private String downloadFilePath;
    /** 图片下载路径 */
    private String downloadPhotoPath;
    /** 验证码CD时间（秒） */
    private long validateCdTimeLength;
    /** 验证码有效时间（秒） */
    private long validateTimeLength;
    /** Token秘钥 */
    private String tokenSecret;
    /** Token有效时长（小时） */
    private long tokenValidTimeLength;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public String getUploadPhotoPath() {
        return uploadPhotoPath;
    }

    public void setUploadPhotoPath(String uploadPhotoPath) {
        this.uploadPhotoPath = uploadPhotoPath;
    }

    public int getUploadBufferSize() {
        return uploadBufferSize;
    }

    public void setUploadBufferSize(int uploadBufferSize) {
        this.uploadBufferSize = uploadBufferSize;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public String getDownloadPhotoPath() {
        return downloadPhotoPath;
    }

    public void setDownloadPhotoPath(String downloadPhotoPath) {
        this.downloadPhotoPath = downloadPhotoPath;
    }

    public long getValidateCdTimeLength() {
        return validateCdTimeLength;
    }

    public void setValidateCdTimeLength(long validateCdTimeLength) {
        this.validateCdTimeLength = validateCdTimeLength;
    }

    public long getValidateTimeLength() {
        return validateTimeLength;
    }

    public void setValidateTimeLength(long validateTimeLength) {
        this.validateTimeLength = validateTimeLength;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public long getTokenValidTimeLength() {
        return tokenValidTimeLength;
    }

    public void setTokenValidTimeLength(long tokenValidTimeLength) {
        this.tokenValidTimeLength = tokenValidTimeLength;
    }
}
