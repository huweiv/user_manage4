package com.huweiv.domain;

import java.time.LocalDateTime;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName KaptchaImageVO
 * @Description TODO
 * @CreateTime 2022/4/12 14:02
 */
public class KaptchaImageVO {

    private String code;
    private LocalDateTime expiredTime;

    public KaptchaImageVO(String code, int expiredTime) {
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredTime);
    }

    public String getCode() {
        return code;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredTime);
    }
}
