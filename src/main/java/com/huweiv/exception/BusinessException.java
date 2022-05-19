package com.huweiv.exception;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName BusinessException
 * @Description TODO
 * @CreateTime 2022/5/11 16:13
 */
public class BusinessException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BusinessException(Integer code) {
        this.code = code;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
