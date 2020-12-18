package com.qingting.middleware.util;

import com.qingting.middleware.enums.Code;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("数据返回类")
public class JsonResult<T> {

    @ApiModelProperty("响应编码号")
    private int code;

    @ApiModelProperty("具体信息")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    public static <T> JsonResult customMessage(Code code,String message, T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(message);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static <T> JsonResult normal(Code code, T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(code.getMessage());
        jsonResult.setData(data);
        return jsonResult;
    }



    public static JsonResult normal(Code code) {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(code.getMessage());
        return jsonResult;
    }

    public static <T> JsonResult ok(String message, T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage(message);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static <T> JsonResult ok(String message) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult error(Code code) {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(code.getMessage());
        return jsonResult;
    }

    public static JsonResult success() {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage("请求成功");
        return jsonResult;
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage("请求成功");
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult failed() {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(500);
        jsonResult.setMessage("系统异常");
        return jsonResult;
    }

}
