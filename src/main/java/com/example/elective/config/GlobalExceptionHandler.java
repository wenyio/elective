package com.example.elective.config;

import com.example.elective.entity.base.ResponseResult;
import com.example.elective.entity.base.SystemRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/28
 * @see: com.example.elective.config
 * @version: v1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    private ResponseResult<Object> handlerErrorInfo(HttpServletRequest request, Exception e) {
        log.error(e.toString());
        return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
