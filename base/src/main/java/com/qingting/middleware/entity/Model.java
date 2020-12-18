package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-09 16:56
 */
@ApiModel(value = "com.qingting.middleware.entity.Model")
@Data
public class Model {
    /**
     * 自动增长id
     */
    @ApiModelProperty(value = "自动增长id")
    private Integer id;

    /**
     * 模型名称
     */
    @ApiModelProperty(value = "模型名称")
    private String modelName;

    /**
     * 模型编号
     */
    @ApiModelProperty(value = "模型编号")
    private String modelNo;

    /**
     * 因子主键
     */
    @ApiModelProperty(value = "因子字段主键")
    private Integer factorId;

    /**
     * 用户使用的因子值集合（逗号分隔）
     */
    @ApiModelProperty(value = "用户使用的因子值集合（逗号分隔）")
    private String factorList;

    /**
     * 集合因子开关：0：开启，-1：关闭 ，默认-1
     */
    @ApiModelProperty(value = "集合因子开关：0：开启，-1：关闭 ，默认-1")
    private Integer factorListSwitch;

    /**
     * 扩展分数
     */
    @ApiModelProperty(value = "扩展分数")
    private Integer extendScore;

    /**
     * A系统报文获取开关 0 开启 -1 关闭
     */
    @ApiModelProperty(value = "A系统报文获取开关 0 开启 -1 关闭")
    private Integer reportSwitch;

    /**
     * 状态 0：正常 -1：删除
     */
    @ApiModelProperty(value = "状态 0：正常 -1：删除")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}