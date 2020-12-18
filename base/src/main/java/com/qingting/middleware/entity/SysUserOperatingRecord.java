package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
@ApiModel(value="com.qingting.middleware.entity.SysUserOperatingRecord")
@Data
public class SysUserOperatingRecord {
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
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;

    /**
    * 类型,1：save:增,2：delte:删:3：update:改:4：select:查
    */
    @ApiModelProperty(value="类型,1：save:增,2：delte:删:3：update:改:4：select:查")
    private Byte type;

    /**
    * 模块/Controller层名称
    */
    @ApiModelProperty(value="模块/Controller层名称")
    private String controllerName;

    /**
    * 操作信息
    */
    @ApiModelProperty(value="操作信息")
    private String info;

    /**
    * 入参数据
    */
    @ApiModelProperty(value="入参数据")
    private String param;

    /**
    * 结果,0:成功,1或其他:失败
    */
    @ApiModelProperty(value="结果,0:成功,1或其他:失败")
    private Byte result;
}