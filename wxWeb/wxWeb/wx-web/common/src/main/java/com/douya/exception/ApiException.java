package com.douya.exception;

import com.douya.utils.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方系统调用接口的异常类
 */
public class ApiException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ApiException.class);

    private String code;//异常错误码
    private String msg;//异常错误说明
    private String error;//异常错误

    public ApiException(Exception e) {
        logger.error("第三方系统调用接口发生异常：", e);
        e.printStackTrace();

        StringWriter swriter = new StringWriter();
        PrintWriter pwriter = new PrintWriter(swriter);
        e.printStackTrace(pwriter);

        pwriter.flush();
        swriter.flush();

        this.code = ApiReturnCodeEnum.SYSTEM_ERROR.getCode();
        this.msg = ApiReturnCodeEnum.SYSTEM_ERROR.getDescription();
        this.error = swriter.toString();

        try {
            pwriter.close();
            swriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public ApiException(String msg){
        this.msg = msg;
    }

    public ApiException(ApiReturnCodeEnum apiReturnCodeEnum) {
        this.code = apiReturnCodeEnum.getCode();
        this.msg = apiReturnCodeEnum.getDescription();
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, Object> asMap(){
        Map map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("msg", this.msg);
        if (error != null) {
            map.put("error", this.error);
        }

        return map;
    }

    public String asString(){
        try {
            return JSONUtils.getObjectMapper().writeValueAsString(this.asMap());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
