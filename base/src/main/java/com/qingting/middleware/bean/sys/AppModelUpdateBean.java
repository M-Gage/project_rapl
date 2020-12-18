package com.qingting.middleware.bean.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Gage
 * @describe
 * @date 2019-10-09 13:42
 */
@Data
public class AppModelUpdateBean {
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
     * 可用次数
     */
    @ApiModelProperty(value="可用次数")
    private Integer availableTimes;

    /**
     * 状态 0 可用，-1 删除
     */
    @ApiModelProperty(value = "状态 0 可用，-1 删除")
    private Integer status;

}
