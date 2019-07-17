package com.douya.base;


import com.douya.utils.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum RestStatus {

    SUCCESS(200),
    ERROR(-1),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "内容不存在"),
    SERVER_ERROR(500, "服务器异常");

    public Integer code;
    public String msg;

    RestStatus(Integer code){
        this.code = code;
        switch (code){
            case 200 : this.msg = "执行成功" ;break;
            case -1 : this.msg = "系统繁忙，请稍后再试" ;break;
            default: this.msg = "系统未定义";
        }
    }

    RestStatus(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Map<String, Object> asMap(){
        Map map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("msg", this.msg);
        return map;
    }

    public String asString() {
        try {
            return JSONUtils.getObjectMapper().writeValueAsString(this.asMap());
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return "";
        }
    }

}
