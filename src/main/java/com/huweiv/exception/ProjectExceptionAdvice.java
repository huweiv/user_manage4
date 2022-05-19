package com.huweiv.exception;

import com.huweiv.domain.Result;
import com.huweiv.util.Code;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName ProjectExceptionAdvice
 * @Description TODO
 * @CreateTime 2022/5/11 17:18
 */
@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex) {
        return new Result(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex) {
        return new Result(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        return new Result(Code.SYSTEM_UNKNOW_ERR, "系统繁忙, 请稍后再试!");
    }
}
