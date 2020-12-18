package com.qingting.middleware.bean.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Gage
 * @describe app对应模型创建实体
 * @date 2019-10-09 11:43
 */
@Data
public class AppModelCreateBean {

    /**
     * Appid
     */
    @ApiModelProperty(value="Appid")
    @NotBlank(message = "Appid不能为空")
    private String appId;

    /**
     * 模型id
     */
    @ApiModelProperty(value="模型id")
    @NotNull(message = "模型id不能为空")
    private Integer modelId;

    /**
     * 市场价格
     */
    @ApiModelProperty(value = "市场价格")
    @NotNull(message = "市场价格不能为空")
    private BigDecimal marketPrice;

    /**
     * 成本价
     */
    @ApiModelProperty(value="成本价")
    @NotNull(message = "成本价不能为空")
    private BigDecimal costPrice;

    /**
     * 可用次数
     */
    @ApiModelProperty(value="可用次数")
    private Integer availableTimes;


}
