package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
*@author Gage
*@describe ${Description}
*@date 2019-09-04 15:33 
*/
@ApiModel(value="com.qingting.middleware.entity.UserRepaymentRecord")
@Data
public class UserRepaymentRecord {
    /**
    * 自增长id
    */
    @ApiModelProperty(value="自增长id")
    private Integer id;

    /**
    * 订单id
    */
    @ApiModelProperty(value="订单id")
    private String indId;

    /**
    * 结清时间
    */
    @ApiModelProperty(value="结清时间")
    private Date indRepayTime;

    /**
    * 结清金额
    */
    @ApiModelProperty(value="结清金额")
    private String indRepayMoney;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
    * 状态 0：正常 1：无效（操作失误）
    */
    @ApiModelProperty(value="状态 0：正常 1：无效（操作失误）")
    private Integer status;
}