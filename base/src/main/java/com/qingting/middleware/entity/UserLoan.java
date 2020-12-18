package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "用户借/还款记录")
@Data
public class UserLoan {
    @ApiModelProperty(value = "自增长id")
    private Integer id;

    @ApiModelProperty(value = "产品id")
    private String appId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户身份证号")
    private String userIdcard;

    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

    @ApiModelProperty(value = "订单id")
    private String indId;

    @ApiModelProperty(value = "借款金额")
    private String indMoneyJie;

    @ApiModelProperty(value = "放款时间")
    private Date indFangTime;

    @ApiModelProperty(value = "是否结清：0 未结清 ；1 已结清 ,-1:无效订单；默认 0")
    private String indIfPay;

    @ApiModelProperty(value = "结清时间")
    private Date indRepayTime;

    @ApiModelProperty(value = "结清金额")
    private String indRepayMoney;
}