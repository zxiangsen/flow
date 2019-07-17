package com.douya.exception;

import com.douya.base.Constants;
import com.douya.base.RestStatus;
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
 *
 */
public class RestException extends RuntimeException {

    protected Logger logger = LoggerFactory.getLogger(RestException.class);

    private Integer code = -2;  //异常代码
    private String msg;         //异常提示
    private String error;       //异常错误

    public RestException(Exception e) {
        super(e.getMessage());
        logger.error("系统异常", e);
        e.printStackTrace();
        StringWriter swriter = new StringWriter();
        PrintWriter pwriter = new PrintWriter(swriter);
        e.printStackTrace(pwriter);
        pwriter.flush();
        swriter.flush();
        this.code = RestStatus.ERROR.code;
        this.msg = RestStatus.ERROR.msg;
        this.error = swriter.toString();
        try {
            pwriter.close();
            swriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public RestException(String msg){
        super(msg);
        this.msg = msg;
    }

    public RestException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public RestException(Constants constants) {
        this.code = constants.code;
        this.msg = constants.msg;
    }

    public RestException(Integer code, String msg, String error) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, Object> asMap(){
        Map map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("msg", this.msg);
        if(error!=null){
            map.put("error", this.error);
        }
        return map;
    }

    public boolean isError(){
        return this.code == RestStatus.ERROR.code;
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