package com.douya.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lb on 2017/12/4.
 *  忘记 密码 接口专用
 */
@ApiModel(value = "UserTWO", description = "用户数据传输类")
public class UserTWO {

    @ApiModelProperty(value = "手机号码")
    private String phone;            //     手机号码
    @ApiModelProperty(value = "验证码ID")
    private String messageId;         //    验证码ID
    @ApiModelProperty(value = "验证码")
    private String VerificationCode;  //    验证码
    @ApiModelProperty(value = "新密码")
    private String newPassword;       //    新密码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}