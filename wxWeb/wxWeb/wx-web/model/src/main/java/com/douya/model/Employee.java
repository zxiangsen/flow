package com.douya.model;

import com.douya.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "Employee", description = "员工信息表")
public class Employee extends BaseEntity {

    @ApiModelProperty(value = "登陆账号")
    protected String userName;
    
}

