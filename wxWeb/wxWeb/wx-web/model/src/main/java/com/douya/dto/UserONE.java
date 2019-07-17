package com.douya.dto;

import com.douya.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lb on 2017/12/4.
 * 注册 接口 专用
 */
@ApiModel(value = "UseroNE", description = "用户数据传输类")
public class UserONE {

    @ApiModelProperty(value = "手机号码")
    private String phone;            //     手机号码
    @ApiModelProperty(value = "密码")
    private String password;         //     密码
    @ApiModelProperty(value = "邀请码")
    private String code;              //    邀请码
    @ApiModelProperty(value = "验证码ID")
    private String messageId;         //    验证码ID
    @ApiModelProperty(value = "验证码")
    private String VerificationCode;  //    验证码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        VerificationCode = verificationCode;
    }
}