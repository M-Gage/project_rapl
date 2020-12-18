package com.qingting.middleware.bean;

import lombok.Data;

/**
 * @author Gage
 * @describe 校验用户结果返回对象
 * @date 2019-09-30 11:16
 */
@Data
public class VerifyUserBean {
    private int result;
    private String msg;
    private String phone;
}
