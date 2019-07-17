package com.douya.base;

import com.douya.exception.RestException;
import com.douya.utils.StringUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * 引用依赖和绑定异常状态的处理
 */
public abstract class BaseController<S extends BaseService> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    protected S service;

    @ExceptionHandler(RestException.class)
    public RestException processApiException(RestException e){
        return e;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RestException processApiException(MethodArgumentNotValidException e) {
        List<String> msgList = Lists.newArrayList();
        for ( ObjectError objectError : e.getBindingResult().getAllErrors()){
            msgList.add(objectError.getDefaultMessage());
        }
        return new RestException(StringUtils.toString(msgList));
    }

    @ExceptionHandler({BindException.class})
    public RestException doBindException(BindException e){
        List<String> msgList = Lists.newArrayList();
        for ( ObjectError objectError : e.getBindingResult().getAllErrors()){
            msgList.add(objectError.getDefaultMessage());
        }
        return new RestException(StringUtils.toString(msgList));
    }

    @ExceptionHandler(Exception.class)
    public RestException processException(Exception e){
        return new RestException(e);
    }

}
