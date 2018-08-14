package com.yun.album.bean;

import com.yun.album.common.properties.SystemProperties;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 动态验证码
 */
public class ValidateCode implements Serializable {
    private String phone;
    private String code;
    private LocalDateTime validateTime;
    private LocalDateTime cdTime;

    public ValidateCode(String phone, String code, LocalDateTime createTime, SystemProperties properties) {
        this.phone = phone;
        this.code = code;
        this.validateTime = createTime.plus(properties.getValidateTimeLength(), ChronoUnit.SECONDS);
        this.cdTime = createTime.plus(properties.getValidateCdTimeLength(), ChronoUnit.SECONDS);
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public boolean isExpire() {
        return validateTime.isBefore(LocalDateTime.now());
    }

    public boolean isCding() {
        return cdTime.isBefore(LocalDateTime.now());
    }
}
