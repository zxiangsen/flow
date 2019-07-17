package com.douya.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lb on 2017/12/4.
 * 用户 下单 专用
 */
@ApiModel(value = "OrderONE", description = "用户数据传输类")
public class OrderONE {

    @ApiModelProperty(value = "用户id    加密")
    private String userId;                //
    @ApiModelProperty(value = "商品id    加密{99,9991,9992,9993} 只有这4种")
    private String commodityId;          //
    @ApiModelProperty(value = "联系人")
    private String contactName;            //
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;                //
    @ApiModelProperty(value = "联系地址")
    private String contactAddress;           //
    @ApiModelProperty(value = "收货方式{0[物流],1[自提]} ")
    private String receivingType;           //


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getReceivingType() {
        return receivingType;
    }

    public void setReceivingType(String receivingType) {
        this.receivingType = receivingType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
}