package com.douya.model;

import com.douya.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "User", description = "用户信息表")
@JsonInclude
public class User extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -8931219595141552227L;

	@JsonView(User.class)
    @ApiModelProperty(value = "id")
    protected Long id;
    @ApiModelProperty(value = "手机号")
    protected String phone;
    @ApiModelProperty(value = "密码")
    protected String password;

    @ApiModelProperty(value = "邀请码")
    protected String code;

    @JsonView(User.class)
    @ApiModelProperty(value = "个人头像")
    protected String portrait;

    @ApiModelProperty(value = "个人二维码")
    protected String qrcode;

    @ApiModelProperty(value = "用户类型(身份{0[默认]1[普通VIP],2[经理],3[高级VIP],4[总监]}")
    protected String identity;

    @ApiModelProperty(value = "直属上级")
    protected String superiorid;

    @ApiModelProperty(value = "无限上级")
    protected String superiorids;

    @ApiModelProperty(value = "零钱")
    protected String money;

    @ApiModelProperty(value = "注册日期")
    protected Date createDate;
    @ApiModelProperty(value = "会员日期")
    protected Date memberDate;

    @ApiModelProperty(value = "更新时间")
    protected Date updateDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSuperiorid() {
        return superiorid;
    }

    public void setSuperiorid(String superiorid) {
        this.superiorid = superiorid;
    }

    public String getSuperiorids() {
        return superiorids;
    }

    public void setSuperiorids(String superiorids) {
        this.superiorids = superiorids;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(Date memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}