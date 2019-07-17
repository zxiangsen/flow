package com.easemob.server.example.comm;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

public class User extends io.swagger.client.model.User {

    @SerializedName("nickname")
    private String nickname = null;

    public User() {
    }

    public User nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @ApiModelProperty(
            example = "null",
            value = ""
    )
    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
