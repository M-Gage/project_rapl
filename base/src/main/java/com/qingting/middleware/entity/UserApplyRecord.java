package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-10 14:03
 */
@ApiModel(value = "com.qingting.middleware.entity.UserApplyRecord")
@Data
public class UserApplyRecord {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Integer id;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private String appId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id")
    private String taskId;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间	")
    private Date applyTime;

    /**
     * 致诚阿福标识：0：未调用，1：调用
     */
    @ApiModelProperty(value = "致诚阿福标识：0：未调用，1：调用")
    private Boolean zcafFlag;

    /**
     * 审核状态	0 ：待审核 ；1：审核通过 ；2：审核不通过
     */
    @ApiModelProperty(value = "审核状态	0 ：待审核 ；1：审核通过 ；2：审核不通过	")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}