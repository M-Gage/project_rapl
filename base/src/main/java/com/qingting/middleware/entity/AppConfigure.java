package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-09-11 17:08
 */
@ApiModel(value = "com.qingting.middleware.entity.AppConfigure")
@Data
public class AppConfigure {
    /**
     * 自增长id
     */
    @ApiModelProperty(value = "自增长id")
    private Integer id;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private String appId;

    /**
     * 共债产品配置
     */
    @ApiModelProperty(value = "共债产品配置")
    private String appAll;

    /**
     * 产品开关 0：开启 -1：关闭
     */
    @ApiModelProperty(value = "产品开关 0：开启 -1：关闭")
    private String appSwitch;
}