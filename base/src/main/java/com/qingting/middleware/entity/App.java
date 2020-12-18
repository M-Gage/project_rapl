package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-11 16:32
 */
@ApiModel(value = "com.qingting.middleware.entity.App")
@Data
public class App {
    /**
     * 自增长id
     */
    @ApiModelProperty(value = "自增长id")
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
     * 公钥
     */
    @ApiModelProperty(value = "公钥")
    private String publicKey;

    /**
     * 私钥
     */
    @ApiModelProperty(value = "私钥")
    private String privateKey;

    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;

    /**
     * 可用金额
     */
    @ApiModelProperty(value = "可用金额")
    private BigDecimal availableAmount;

    /**
     * 模型id
     */
    @ApiModelProperty(value = "模型id")
    private Integer modelId;

    /**
     * A系统报文获取开关 0 开启 -1 关闭
     */
    @ApiModelProperty(value = "A系统报文获取开关 0 开启 -1 关闭")
    private Integer reportSwitch;

    /**
     * 用户单独使用因子值
     */
    @ApiModelProperty(value = "用户单独使用因子值")
    private Integer factorId;

    /**
     * 用户使用的因子值集合（逗号分隔）
     */
    @ApiModelProperty(value = "用户使用的因子值集合（逗号分隔）")
    private String factorList;

    /**
     * 因子开关:0：单个因子，1：多个因子
     */
    @ApiModelProperty(value = "因子开关:0：单个因子，1：多个因子")
    private Integer factorSwitch;

    /**
     * 展示因子:0默认展示，其他展示配置的id，多个用逗号分隔
     */
    @ApiModelProperty(value = "展示因子:0默认展示，其他展示配置的id，多个用逗号分隔")
    private String factorShow;

    /**
     * 状态（0：正常 -1：删除）
     */
    @ApiModelProperty(value = "状态（0：正常 -1：删除）")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String sysUserId;
}