package com.douya.config;

import com.douya.exception.ApiException;
import com.douya.exception.RestException;
import com.douya.base.RestStatus;
import com.douya.utils.DES;
import com.douya.utils.JSONUtils;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回Body处理
 */
@Configuration //相当于把该类作为spring的xml配置文件中的<beans>
//@ControllerAdvice注解将作用在所有注解了@RequestMapping的控制器的方法上
@ControllerAdvice(annotations = { Api.class }) //只对使用了swagger的Api注解的类进行controller增强
public class ApiResponseBody implements ResponseBodyAdvice<Object> {
    //ResponseBodyAdvice是spring4.1的新特性，其作用是在响应体写出之前做一些处理
    private static final Logger logger = LoggerFactory.getLogger(ApiResponseBody.class);

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     *  在返回数据之前执行
     */
    public Object beforeBodyWrite(Object object, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(object == null) {
            object = Maps.newHashMap();
        }

        Map result;
        if(object instanceof RestException) {
            RestException exec = (RestException)object;
            if(!exec.isError() && exec.getCode().intValue() != 0) {
                logger.warn("提示错误：" + exec.getMsg());
            }

            result = ((RestException)object).asMap();
        } else if(object instanceof ApiException) {
            result = ((ApiException)object).asMap();
            logger.warn("ApiException", object);
        } else if(object instanceof RestStatus) {
            result = ((RestStatus)object).asMap();
        } else {
            result = RestStatus.SUCCESS.asMap();
            result.put("data", object);
        }

        /*Map<String, String> map = null;
        try {
            map = new HashMap();
            String value = DES.encryptDES(JSONUtils.parseObject2JsonString(result));
            map.put("key", URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        //return DES.encryptDES(JSONUtils.parseObject2JsonString(result));
        return result;
    }

}