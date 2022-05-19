package com.huweiv.domain;


import lombok.Data;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName Result
 * @Description TODO
 * @CreateTime 2022/5/10 22:07
 */
@Data
public class Result {
    private Object data;
    private Integer code;
    private String msg;

    public Result() {
    }

    public Result(Object data, Integer code) {
        this.data = data;
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
