package com.qingting.middleware.enums;

/**
 * 返回状态字典
 */
public enum Code {
    /*流程错误*/
    ACCOUNT_ERROR(110, "账号密码错误"),
    ACCOUNT_DISABLED(111, "账号被禁用"),
    FILED_NULL(112, "字段为空"),
    PARAM_NULL(113, "入参为空"),

    /*请求结果*/
    SUCCESS(200, "请求成功"),
    THIRD_PARTY_ERROR(201, "第三方返回错误"),
    RETURN_NULL(202, "返回数据为空"),
    SQL_RESULT_NULL(203, "数据库查询结果为空"),
    THIRD_PARTY_EXCEPTION(204, "请求三方时出现异常"),
    BLACKLIST_USER(205, "该用户为黑名单用户"),
    DUPLICATE_ERROR(206, "数据重复"),
    RETURN_SCORE_ERROR(207, "分数返回配置错误"),

    /*参数错误*/
    PARAM_ERROR(300, "请求参数有误"),
    LOGIN_ERROR(301, "账号密码不能为空"),
    PARAM_TYPE_ERROR(302, "参数类型转换错误"),
    MODEL_NULL_ERROR(303, "查询不到对应模型,请确保模型在使用中"),
    MODEL_MATCH_ERROR(305, "模型数据与模型路径不匹配"),
    MODEL_REPORT_MISS(304, "关键报文缺失"),
    UPDATE_PASSWORD_ERROR(312, "原始密码错误"),
    GZIP_UNCOMPRESS_ERROR(380, "参数解压错误"),
    GZIP_COMPRESS_ERROR(381, "参数压缩错误"),
    GZIP_COMPRESS_NUll(382, "压缩参数为空"),
    GZIP_UNCOMPRESS_NUll(383, "解压参数为空"),
    TOO_MANY_TIMES(384, "输入次数太多"),

    /*验证错误*/
    ENCRYPT_ERROR(460, "加密错误"),
    DECRYPT_ERROR(461, "解密错误"),
    SIGN_ERROR(462, "数字签名出错"),
    GET_SIGN_ERROR(463, "生成数字签名错误"),
    VERIFY_SIGN_ERROR(464, "数字签名验证错误"),
    NO_AVAILABLE_TIMES(465, "可用次数为0，请联系商务"),


    /*系统错误*/
    ERROR(500, "系统异常"),
    ENTER_ERROR(501, "系统异常,录入失败"),
    CREATE_ERROR(502,"插入失败"),
    DELETE_ERROR(503,"删除失败"),
    UPDATE_ERROR(504,"修改失败"),
    YML_ERROR(505, "YML获取参数错误"),
    DATA_EXISTING(506, "数据已存在，不可重复"),

    /*方法错误*/
    JSON_ERROR(600, "JSON 序列化错误"),
    JSON_PARSE_ERROR(601, "JSON 解析错误"),
    JSON_PARSE_NULL(602, "JSON 解析内容为空"),
    MAP_KEY_NULL(603, "MAP获取的key值为空"),
    MAP_VALUE_NULL(604, "MAP的key获取的内容为空"),
    STREAM_TURN_BYTE_ERROR(605, "输入流转化成字节流错误"),

    /*其他*/
    REDIS_NULL(700, "REDIS未获取到相关信息"),

    TOKEN_ERROR(710, "TOKEN不合法"),
    TOKEN_FAIL(711, "TOKEN失效"),
    TOKEN_NULL(712, "TOKEN为空"),
    TOKEN_EXPIRED(713, "TOKEN过期"),


    INIT(0,"");


    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

