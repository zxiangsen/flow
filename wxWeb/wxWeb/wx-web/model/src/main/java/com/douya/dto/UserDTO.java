package com.douya.dto;

import com.douya.model.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;

/**
 * Created by lb on 2017/12/4.
 */
@ApiModel(value = "UserDTO", description = "用户数据传输类")
public class UserDTO extends User {

    private String type;//类型：1忘记密码 2修改密码
    private String code;//验证码
    private String msgId;//验证码id

    private int page; //页数

    private String newPassword;//修改密码时的新密码


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}