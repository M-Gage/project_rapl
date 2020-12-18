package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-09 16:07
 */
@ApiModel(value = "com.qingting.middleware.entity.UserRequestModelRecord")
@Data
public class UserRequestModelRecord {
    /**
     * 自增长id
     */
    @ApiModelProperty(value = "自增长id")
    private Integer id;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String appId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String username;

    /**
     * 用户身份证号
     */
    @ApiModelProperty(value = "用户身份证号")
    private String idCard;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String mobile;

    /**
     * 风控评分
     */
    @ApiModelProperty(value = "风控评分")
    private String score;

    /**
     * 风控模型
     */
    @ApiModelProperty(value = "风控模型")
    private Integer modelId;

    /**
     * 因子id
     */
    @ApiModelProperty(value = "因子id")
    private Integer factorId;

    /**
     * 报文解析出的因子值
     */
    @ApiModelProperty(value = "报文解析出的因子值 ")
    private String usedFactor;

    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id")
    private String taskId;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String channelId;

    /**
     * 返回报文
     */
    @ApiModelProperty(value = "返回报文")
    private String requestResult;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}