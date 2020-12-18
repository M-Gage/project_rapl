package com.qingting.middleware.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@ApiModel(value = "共债返回数据")
public class TogetherDebtResultBean {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户身份证")
    private String userIdcard;

    @ApiModelProperty(value = "用户电话号码")
    private String userMobile;

    @ApiModelProperty(value = "配置平台共债数")
    private Integer monitorCount;

    @ApiModelProperty(value = "所有平台数")
    private Integer allCount;

    @ApiModelProperty(value = "所有平台id")
    private String allAppId;

    @ApiModelProperty(value = "总共债平台数")
    private Integer totalCount;

    @ApiModelProperty(value = "共债平台id")
    private String debtAppId;

    @ApiModelProperty(value = "还款平台数")
    private Integer repaymentCount;

    @ApiModelProperty(value = "还款平台id")
    private String repaymentAppId;

    @ApiModelProperty(value = "逾期平台数")
    private Integer overdueCount;

    @ApiModelProperty(value = "逾期平台id")
    private String overdueAppId;

    @ApiModelProperty(value = "各平台最后一次更新时间")
    private Map<String, Date> platformUpdateTime;
}
