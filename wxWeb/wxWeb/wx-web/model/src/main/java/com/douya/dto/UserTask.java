package com.douya.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lb on 2017/12/4.
 * 注册 接口 专用
 */
@ApiModel(value = "UserTask", description = "用户数据传输类")
public class UserTask {

    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "任务id")
    private String taskId;
    @ApiModelProperty(value = "完成任务是截去的图片保存的URL")
    private String icon;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}