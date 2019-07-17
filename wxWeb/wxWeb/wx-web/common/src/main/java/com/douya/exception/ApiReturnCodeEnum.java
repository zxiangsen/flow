package com.douya.exception;

import com.douya.utils.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

/**
 * 对外Api接口的返回码的枚举类
 * User: LuoChao
 * Date: 2017/3/14 0014
 * Time: 14:38
 */
public enum ApiReturnCodeEnum {

    SUCCESS("0", "请求成功"),

    SYSTEM_BUSY("-1", "系统繁忙，请稍后再试"),
    SYSTEM_ERROR("-2", "系统错误"),
    SYSTEM_OVERTIME("-3", "系统超时"),

    LACK_PARAMETER("1001", "缺失参数"),
    ILLEGAL_PARAMETER("1002", "不合法的参数"),
    ILLEGAL_APPID("1003", "不合法的appId"),
    ILLEGAL_REQUEST_FORMAT("1004", "不合法的请求格式"),
    LACK_APPID("1005", "缺少appId参数"),
    LACK_APPSECRET("1006", "缺少appSecret参数"),
    NEED_GET_REQUEST("1007", "需要GET请求"),
    NEED_POST_REQUEST("1008", "需要POST请求"),
    PARSE_JSON_ERROR("1009", "解析JSON内容错误"),
    INVALID_PARAMETER("1010", "参数错误"),
    ILLEGAL_URL("1012", "不合法的URL"),
    ILLEGAL_PARAMETER_WITH_POST("1013", "POST数据参数不合法"),
    TOKEN_OVERTIME("1014", "Token已过期"),
    TOKEN_WRONG("1015", "Token不正确");

    private String code;//返回码
    private String description;//说明

    private ApiReturnCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> asMap(){
        Map map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("msg", this.description);

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
