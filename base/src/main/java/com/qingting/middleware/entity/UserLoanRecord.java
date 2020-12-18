package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-09-20 11:39
 */
@ApiModel(value = "com.qingting.middleware.entity.UserLoanRecord")
@Data
public class UserLoanRecord {
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
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String indId;

    /**
    * 借款金额
    */
    @ApiModelProperty(value="借款金额")
    private String indMoneyJie;

    /**
     * 放款时间
     */
    @ApiModelProperty(value = "放款时间")
    private Date indFangTime;

    /**
     * 最后还款时间
     */
    @ApiModelProperty(value = "最后还款时间")
    private Date lastRepaymentTime;

    /**
     * 是否结清：0：未结清,1：已结清，2：续期中，3：逾期  默认 0
     */
    @ApiModelProperty(value = "是否结清：0：未结清,1：已结清，2：续期中，3：逾期  默认 0")
    private Integer indIfPay;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
    * 状态 0：正常 -1：无效
    */
    @ApiModelProperty(value = "状态 0：正常 -1：无效")
    private Integer status;
}