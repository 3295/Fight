package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获
 */
@ControllerAdvice
public class GlobalExceptionHandle {
    private final static Logger log= LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map exceptionResult(Exception e){
        log.error(e.getMessage(),e);
        Map map=new HashMap();
        map.put("errorCode",1001);
        map.put("errorMsg","系统错误");
        return map;
    }
}
