package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-10-09 12:00
 */
@ApiModel(value = "com.qingting.middleware.entity.AppModelPrice")
@Data
public class AppModelPrice {
    /**
     * 自增长id
     */
    @ApiModelProperty(value = "自增长id")
    private Integer id;

    /**
     * Appid
     */
    @ApiModelProperty(value = "Appid")
    private String appId;

    /**
     * 模型id
     */
    @ApiModelProperty(value = "模型id")
    private Integer modelId;

    /**
     * 市场价格
     */
    @ApiModelProperty(value = "市场价格")
    private BigDecimal marketPrice;

    /**
     * 成本价
     */
    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;

    /**
     * 总次数
     */
    @ApiModelProperty(value = "总次数")
    private Integer totalTimes;

    /**
     * 已使用次数
     */
    @ApiModelProperty(value = "已使用次数")
    private Integer usedTimes;

    /**
     * 状态 0 可用，-1 删除
     */
    @ApiModelProperty(value = "状态 0 可用，-1 删除")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}