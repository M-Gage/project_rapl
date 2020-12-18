package com.qingting.middleware.bean.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "模型请求参数对象")
public class ModelParamBean {

    @ApiModelProperty(value = "模型编号（加密）", required = true)
    @NotBlank(message = "模型编号不能为空")
    private String modelNo;

    @ApiModelProperty(value = "模型名称（加密）", required = true)
    @NotBlank(message = "模型名称不能为空")
    private String modelName;

    @ApiModelProperty(value = "渠道号")
    private String channelId;

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "用户信息", required = true)
    @NotBlank(message = "用户信息不能为空")
    private String user;

    @JsonProperty(value = "afufxpg")
    @ApiModelProperty(value = "致诚阿福-风险评估")
    private String aFufxpg;

    @ApiModelProperty(value = "魔蝎报文")
    private String moXie;

    @ApiModelProperty(value = "有盾报文")
    private String youDun;

    @ApiModelProperty(value = "信用探针报文")
    private String creditTanzhen;

    private String baiQiShi;

    @ApiModelProperty(value = "孚临报文")
    private String fuLin;

    private String qianCheng;

    @ApiModelProperty(value = "致诚阿福-黑名单")
    private String zhiChengAFu;

    @ApiModelProperty(value = "新源-多头报文")
    private String xinYuan;

    @ApiModelProperty(value = "百融-借贷意向报文")
    private String baiRong;

    @ApiModelProperty(value = "运营商报告")
    private String operatorReport;

}
