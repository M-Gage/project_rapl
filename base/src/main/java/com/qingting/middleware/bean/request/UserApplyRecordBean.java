package com.qingting.middleware.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Gage
 * @describe 用户申请记录
 * @date 2019-11-14 16:04
 */
@Data
@ApiModel(value = "用户申请记录")
public class UserApplyRecordBean {

    @ApiModelProperty(value = "用户手机号")
    @NotNull(message = "用户手机号不能为空")
    private List<String> userMobile;

    @ApiModelProperty(value = "审核状态0 ：待审核 ；1：审核通过 ；2：审核不通过")
    @NotBlank(message = "审核状态不能为空")
    private String status;
}
