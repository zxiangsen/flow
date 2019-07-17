package com.douya.dto;

import com.douya.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lb on 2017/12/4.
 */
@ApiModel(value = "UserDTO", description = "用户数据传输类")
public class CashDTO {

    @ApiModelProperty(value = "用户id -- 加密")
    private String userId;
    @ApiModelProperty(value = "提现金额 -- 加密")
    private String money;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}