package com.qingting.middleware.exception;

import com.qingting.middleware.util.JsonResult;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public JsonResult runtimeException(RuntimeException e){
        log.error("异常捕获:{}",e);
        //发生异常进行日志记录，写入数据库或者其他处理，此处省略
        return JsonResult.error(Code.ERROR);
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     */
    @ExceptionHandler(value = MyException.class)
    public JsonResult myExceptionHandler(MyException me){
        log.error("异常捕获：=============================================》");
        log.error("错误内容："+me.getCode().getMessage());
        log.error("=======================================================》");
        //发生异常进行日志记录，写入数据库或者其他处理，此处省略
        return JsonResult.error(me.getCode());
    }

    /**
     *  校验错误拦截处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult validationBodyException(MethodArgumentNotValidException exception){
        log.error("异常捕获："+exception);
        return JsonResult.customMessage(Code.PARAM_ERROR, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(),"");

    }
    /**
     * 参数类型转换错误
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public JsonResult parameterTypeException(HttpMessageConversionException exception){
        String localizedMessage = exception.getCause().getLocalizedMessage();
        log.error(localizedMessage);
        return JsonResult.customMessage(Code.PARAM_TYPE_ERROR,"参数类型转换错误：["
                + localizedMessage.substring(localizedMessage.indexOf("[\"")+2,localizedMessage.indexOf("\"]"))+"]====>"
                +localizedMessage.substring(localizedMessage.indexOf("type"),localizedMessage.indexOf("at")),"");

    }
}
