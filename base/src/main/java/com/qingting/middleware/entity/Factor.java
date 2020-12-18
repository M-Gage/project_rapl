package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-13 14:50
 */
@ApiModel(value = "com.qingting.middleware.entity.Factor")
@Data
public class Factor {
    /**
     * 自增长id
     */
    @ApiModelProperty(value = "自增长id")
    private Integer id;

    /**
     * 因子名称
     */
    @ApiModelProperty(value = "因子名称")
    private String name;

    /**
     * 处理策略
     */
    @ApiModelProperty(value = "处理策略")
    private String processingStrategy;

    /**
     * 报文
     */
    @ApiModelProperty(value = "报文")
    private String report;

    /**
     * 报文对应的因子字段
     */
    @ApiModelProperty(value = "报文对应的因子字段")
    private String factorFiled;

    /**
     * 报文对应的因子公式
     */
    @ApiModelProperty(value = "报文对应的因子公式")
    private String factorFormula;

    /**
     * 扩展分数
     */
    @ApiModelProperty(value = "扩展分数")
    private Integer extendScore;

    /**
     * 计算总和公式
     */
    @ApiModelProperty(value = "计算总和公式")
    private String sum;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}