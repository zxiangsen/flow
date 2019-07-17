package com.douya.dto;

import com.douya.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lb on 2017/12/4.
 */
@ApiModel(value = "Userlogin", description = "用户数据传输类")
public class Userlogin{

    @ApiModelProperty(value = "手机号码/账号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

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
}