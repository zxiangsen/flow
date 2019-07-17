package com.douya.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lb on 2017/12/4.
 *  修改密码 接口专用
 */
@ApiModel(value = "UserTHREE", description = "用户数据传输类")
public class UserTHREE {

    @ApiModelProperty(value = "用户id")
    private String userId;                    //
    @ApiModelProperty(value = "密码")
    private String password;         //     密码
    @ApiModelProperty(value = "新密码")
    private String newPassword;       //    新密码

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}