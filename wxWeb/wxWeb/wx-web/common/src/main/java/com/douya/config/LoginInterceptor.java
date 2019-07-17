package com.douya.config;

import com.douya.base.Constants;
import com.douya.exception.RestException;
import com.douya.utils.CommonUtils;
import com.douya.utils.MySessionContext;
import com.douya.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Enumeration;


/**
 * Created by lb on 2017/12/3.
 */
public class LoginInterceptor implements HandlerInterceptor {
    /*@Value("${login.session.validDate}")
    private String validDate;*/

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Object name = session.getAttribute("username");
        System.out.println("登录拦截"+name);
        // 不拦截
        //重新设置token的过期时间
        //session.setMaxInactiveInterval(2592000);//TODO 过期时间后期修改
        return true;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}