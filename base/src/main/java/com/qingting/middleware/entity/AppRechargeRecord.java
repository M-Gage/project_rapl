package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
*@author Gage
*@describe ${Description}
*@date 2019-10-08 17:31 
*/
@ApiModel(value="com.qingting.middleware.entity.AppRechargeRecord")
@Data
public class AppRechargeRecord {
    /**
    * 自增长id
    */
    @ApiModelProperty(value="自增长id")
    private Integer id;

    /**
    * app主键
    */
    @ApiModelProperty(value="app主键")
    private String appId;

    /**
    * 充值金额
    */
    @ApiModelProperty(value="充值金额")
    private BigDecimal price;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}