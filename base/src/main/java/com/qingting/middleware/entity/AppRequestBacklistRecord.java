package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.qingting.middleware.entity.AppRequestBacklistRecord")
@Data
public class AppRequestBacklistRecord {
    /**
    * 自增长id
    */
    @ApiModelProperty(value="自增长id")
    private Integer id;

    /**
    * app表外键
    */
    @ApiModelProperty(value="app表外键")
    private String appId;

    @ApiModelProperty(value="null")
    private String blackListName;

    /**
    * 任务id
    */
    @ApiModelProperty(value="任务id")
    private String taskId;

    /**
    * 渠道id
    */
    @ApiModelProperty(value="渠道id")
    private String channelId;

    /**
    * 返回报文
    */
    @ApiModelProperty(value="返回报文")
    private String requestResult;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}