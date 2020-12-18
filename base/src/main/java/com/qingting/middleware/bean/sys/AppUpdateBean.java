package com.qingting.middleware.bean.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Gage
 * @describe App修改对象
 * @date 2019-09-12 09:59
 */
@ApiModel(value = "App修改对象")
@Data
public class AppUpdateBean {
    /**
     * 自增长id
     */
    @ApiModelProperty(value = "自增长id")
    @NotBlank(message = "appId不能为空")
    private String appId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String appName;

    /**
     * app唯一标识
     */
    @ApiModelProperty(value = "app唯一标识")
    private String appKey;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * A系统报文获取开关 0 开启 -1 关闭
     */
    @ApiModelProperty(value = "A系统报文获取开关 0 开启 -1 关闭")
    private Integer reportSwitch;

    /**
     * 状态（0：正常 -1：删除）
     */
    @ApiModelProperty(value = "状态（0：正常 -1：删除）")
    private Integer status;

    /**
     * 是否修改密钥
     */
    @ApiModelProperty(value = "是否修改密钥 true 修改")
    private Boolean updateKey;
}
