package com.huweiv.exception;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName SystemException
 * @Description TODO
 * @CreateTime 2022/5/11 16:09
 */
public class SystemException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SystemException(Integer code) {
        this.code = code;
    }

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
