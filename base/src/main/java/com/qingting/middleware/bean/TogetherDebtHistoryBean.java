package com.qingting.middleware.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gage
 * @describe
 * @date 2019-09-20 11:46
 */
@ApiModel("用户放还款历史记录")
@Data
public class TogetherDebtHistoryBean {

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 用户身份证号
     */
    @ApiModelProperty(value = "用户身份证号")
    private String userIdcard;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

    /**
     * 总订单数
     */
    @ApiModelProperty("总订单数")
    private Integer orderCount;

    /**
     * 平台总订单数
     */
    @ApiModelProperty("平台总订单数")
    private Integer platformOrderCount;

    /**
     * 还款订单笔数
     */
    @ApiModelProperty("还款订单笔数")
    private Integer repaymentCount;

    /**
     * 平台还款订单笔数
     */
    @ApiModelProperty("平台还款订单笔数")
    private Integer platformRepaymentCount;

    /**
     * 逾期订单笔数
     */
    @ApiModelProperty("逾期订单笔数")
    private Integer overdueCount;

    /**
     * 平台逾期订单笔数
     */
    @ApiModelProperty("平台逾期订单笔数")
    private Integer platformOverdueCount;

    /**
     * 共债数
     */
    @ApiModelProperty(value = "共债数")
    private Integer debtCount;

    /**
     * 平台共债数
     */
    @ApiModelProperty(value = "平台共债数")
    private Integer platformDebtCount;

    /**
     * 申请数
     */
    @ApiModelProperty(value = "申请数")
    private Integer applyCount;

    /**
     * 平台申请数
     */
    @ApiModelProperty(value = "平台申请数")
    private Integer platformApplyCount;

    /**
     * 使用产品数
     */
    @ApiModelProperty("使用产品数")
    private Integer appCount;

    /**
     * 平台使用产品数
     */
    @ApiModelProperty("平台使用产品数")
    private Integer platformAppCount;

    /**
     * 最后一次还款时间
     */
    @ApiModelProperty("最后一次还款时间")
    private String lastRepaymentTime;

    /**
     * 最后一次借款时间
     */
    @ApiModelProperty("最后一次借款时间")
    private String lastIndFangTime;

}
