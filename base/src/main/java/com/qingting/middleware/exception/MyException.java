package com.qingting.middleware.exception;

import com.qingting.middleware.enums.Code;
import lombok.Data;

@Data
public class MyException extends RuntimeException {
    private Code code;  //异常状态码

    public MyException(Code code) {
        this.code = code;
    }

    //自定义返回错误描述
    public MyException(Code code, String message) {
        /*
        * 2019/12/04
        * 解决message内容重复
        * 用INIT来清空原有的Code的信息，这样导致实际上给GlobalException的Code是Code.INIT，是否有别的方法
        * */
        String msg = code.getMessage() + ":" + message;
        int c = code.getCode();
        code = Code.INIT;
        this.code = code;
        this.code.setMessage(msg);
        this.code.setCode(c);
    }
}
