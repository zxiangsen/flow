package com.douya.model;

import com.douya.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Login", description = "登录表")
public class Login extends BaseEntity{
    @ApiModelProperty(value = "登录手机号")
    protected String phone;

    @ApiModelProperty(value = "登录token")
    protected String token;

    @ApiModelProperty(value = "登录类型：1APP 2微信  3QQ")
    protected String loginType;

    @ApiModelProperty(value = "登录sessionId")
    protected String sessionId;

    @ApiModelProperty(value = "登录IP")
    protected String ip;

    @ApiModelProperty(value = "预留字段3")
    protected String spare3;

    @ApiModelProperty(value = "预留字段4")
    protected String spare4;

    @ApiModelProperty(value = "预留字段5")
    protected String spare5;

    public Login() {
        this.loginType = "1";//默认1
    }

    public Login(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSpare3() {
        return spare3;
    }

    public void setSpare3(String spare3) {
        this.spare3 = spare3;
    }

    public String getSpare4() {
        return spare4;
    }

    public void setSpare4(String spare4) {
        this.spare4 = spare4;
    }

    public String getSpare5() {
        return spare5;
    }

    public void setSpare5(String spare5) {
        this.spare5 = spare5;
    }



}