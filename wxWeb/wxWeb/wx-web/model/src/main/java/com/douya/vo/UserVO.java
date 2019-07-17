package com.douya.vo;

import com.douya.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lb on 2017/12/5.
 */
@ApiModel(value = "UserVO", description = "用户视图表")
@JsonInclude()
public class UserVO extends User {
    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "sessionId")
    private String sessionId;

    @ApiModelProperty(value = "门店id")
    private Long storeId;

    @ApiModelProperty(value = "用来验证用户是否是第一次验证码登录")
    private boolean flag;
    @ApiModelProperty(value = "门店类型")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}