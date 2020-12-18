package com.qingting.middleware.bean.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

/**
 * @author Gage
 * @describe
 * @date 2019-10-09 10:18
 */
@Data
@ApiModel(value = "充值对象")
public class AppRechargeBean {
    /**
     * 自增长id
     */
    @ApiModelProperty(value = "自增长id")
    @NotBlank(message = "appId不能为空")
    private String appId;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @Min(value = 0,message = "充值不能小于0")
    private BigDecimal rechargeAmount;

}
