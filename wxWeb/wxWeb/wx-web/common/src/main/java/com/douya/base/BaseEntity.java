package com.douya.base;


import com.douya.utils.IdSnowflake;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(
            value = "ID",
            hidden = true
    )
	//@JsonSerialize(using = ToStringSerializer.class) 此方法只能单独对字段进行转换
    protected Long id;
    @ApiModelProperty(
            value = "备注"
    )
    protected String remarks;
    /*@ApiModelProperty(
            value = "创建者",
            hidden = true
    )
    protected Admin createBy;*/
    @ApiModelProperty(
            value = "创建时间",
            hidden = true
    )

    protected Date createDate;
    /*@ApiModelProperty(
            value = "更新者",
            hidden = true
    )
    protected Admin updateBy;*/
    @ApiModelProperty(
            value = "更新时间",
            hidden = true
    )
    protected Date updateDate;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void preInsert() {
        if(this.getId() == null) {
            this.setId(IdSnowflake.getLocalInstance().nextId(this.getClass()));
        }
        this.setUpdateDate(new Date());
        this.setCreateDate(this.getUpdateDate());
    }

    public void preUpdate() {
        this.setUpdateDate(new Date());
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8"
    )
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8"
    )
    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
