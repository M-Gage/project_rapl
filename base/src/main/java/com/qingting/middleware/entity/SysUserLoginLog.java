package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
@ApiModel(value="com.qingting.middleware.entity.SysUserLoginLog")
@Data
public class SysUserLoginLog {
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    private String userId;

    /**
    * 用户状态
    */
    @ApiModelProperty(value="用户状态")
    private String userStatus;

    /**
    * ip
    */
    @ApiModelProperty(value="ip")
    private String ip;

    /**
    * 返回数据
    */
    @ApiModelProperty(value="返回数据")
    private String data;

    /**
    * 提示信息
    */
    @ApiModelProperty(value="提示信息")
    private String msg;

    /**
    * 返回码
    */
    @ApiModelProperty(value="返回码")
    private String code;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;
}