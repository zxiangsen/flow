package com.douya.config;

import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by lb on 2017/12/3.
 */
//@Configuration
//@ControllerAdvice(annotations = { Api.class })
public class ApiRequestBody implements RequestBodyAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ApiRequestBody.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     *  请求之前解密
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            return new MyHttpInputMessage(inputMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return inputMessage;
        }
    }

    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;
        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            InputStream inputStream = inputMessage.getBody();
            char[] bodys = new char[1024];
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            int length = -1;
            while ((length = bufferedReader.read(bodys)) > 0) {
                stringBuffer.append(bodys, 0, length);
            }
            System.out.println(stringBuffer.toString());
            this.body = IOUtils.toInputStream(stringBuffer.toString(), "UTF-8");
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return null;
    }
}