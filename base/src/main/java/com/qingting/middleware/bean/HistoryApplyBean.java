package com.qingting.middleware.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户历史操作轨迹")
@Data
public class HistoryApplyBean {

    @ApiModelProperty(value = "相同手机号过去1小时内调用次数")
    private Integer mobile_1h_cnt;

    @ApiModelProperty(value = "相同手机号过去3小时内调用次数")
    private Integer mobile_3h_cnt;

    @ApiModelProperty(value = "相同手机号过去12小时内调用次数")
    private Integer mobile_12h_cnt;

    @ApiModelProperty(value = "相同手机号过去1天内调用次数")
    private Integer mobile_1d_cnt;

    @ApiModelProperty(value = "相同手机号过去3天内调用次数")
    private Integer mobile_3d_cnt;

    @ApiModelProperty(value = "相同手机号过去7天内调用次数")
    private Integer mobile_7d_cnt;

    @ApiModelProperty(value = "相同手机号过去14天内调用次数")
    private Integer mobile_14d_cnt;

    @ApiModelProperty(value = "相同手机号过去30天内调用次数")
    private Integer mobile_30d_cnt;

    @ApiModelProperty(value = "相同手机号过去60天内调用次数")
    private Integer mobile_60d_cnt;

    @ApiModelProperty(value = "相同身份证过去1小时内调用次数")
    private Integer idCard_1h_cnt;

    @ApiModelProperty(value = "相同身份证过去3小时内调用次数")
    private Integer idCard_3h_cnt;

    @ApiModelProperty(value = "相同身份证过去12小时内调用次数")
    private Integer idCard_12h_cnt;

    @ApiModelProperty(value = "相同身份证过去1天内调用次数")
    private Integer idCard_1d_cnt;

    @ApiModelProperty(value = "相同身份证过去3天内调用次数")
    private Integer idCard_3d_cnt;

    @ApiModelProperty(value = "相同身份证过去7天内调用次数")
    private Integer idCard_7d_cnt;

    @ApiModelProperty(value = "相同身份证过去14天内调用次数")
    private Integer idCard_14d_cnt;

    @ApiModelProperty(value = "相同身份证过去30天内调用次数")
    private Integer idCard_30d_cnt;

    @ApiModelProperty(value = "相同身份证过去60天内调用次数")
    private Integer idCard_60d_cnt;

}
