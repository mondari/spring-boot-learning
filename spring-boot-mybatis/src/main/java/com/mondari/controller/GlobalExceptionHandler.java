package com.mondari.controller;

import com.mondari.domain.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统错误")
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseResult> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        List<ObjectError> allErrors = e.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : allErrors) {
            if (!"".equals(sb.toString())) {
                sb.append(';');
            }
            sb.append(error.toString().split(";")[0]);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), sb.toString())
        );
    }

}
