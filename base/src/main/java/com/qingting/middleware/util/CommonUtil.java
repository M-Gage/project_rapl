package com.qingting.middleware.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
@Slf4j
public class CommonUtil {

    /**
     * 判断是否是非法字符
     *
     * @param str 操作的字符串
     * @return 判断结果
     */
    public static boolean containSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }


    /**
     * 对数字固定长度，不足在前面补零
     *
     * @param integer 原数字
     * @param length  需要的数字长度
     * @return 固定长度的数字字符串
     */
    public static String zeroPadding(Integer integer, int length) {
        String s = integer.toString();
        if (s.length() > length) {
            s = s.substring(s.length() - length, s.length());
        } else {
            s = String.format("%0" + length + "d", integer);
        }
        return s;
    }

    /**
     * 返回长度为【strLength】的随机数，在前面补0
     *
     * @param strLength 长度
     * @return 随机数
     */
    public static String getFixLengthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        double press = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLengthString = String.valueOf(press);

        // 返回固定的长度的随机数
        return fixLengthString.substring(1, strLength + 1);
    }

    /**
     * 获取uuid
     *
     * @return UUID字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 删除空格
     *
     * @param str 原字符串
     * @return 没有空格的字符串
     */
    public static String replaceSpace(String str) {
        return str.replaceAll("[\\s]+", "");
    }

    /**
     * 去除开头和结尾
     *
     * @return
     */
    public static String cutStartAndEnd(String s) {
        String s1 = replaceSpace(s);
        return s1.substring(1, s1.length() - 1);
    }

    /**
     * 将实体对象转换成JSON格式的字符串
     *
     * @param valueType 对象类型
     * @return json字符串
     */
    public static <T> String toJson(T valueType) {
        return JSONObject.toJSONString(valueType);
    }

    /**
     * JSON格式的字符串转成实体对象
     *
     * @param jsonStr   json 字符串
     * @param valueType 对象类型
     * @return 对象实体（泛型）
     */
    public static <T> T fromJson(String jsonStr, Class<T> valueType) {
        try {
            return JSONObject.parseObject(jsonStr, valueType);
        } catch (Exception e) {
            log.error("JSON解析失败：" + jsonStr + "字符串转" + valueType);
            e.printStackTrace();
            return null;
        }
    }

    /***
     * json 转 List<T>
     *
     * @param jsonStr json 字符串
     * @param clazz 对象类型
     * @return 对象集合（泛型）
     */
    public static <T> List<T> jsonToList(String jsonStr, Class<T> clazz) {
        try {
            return JSONArray.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            log.error("JSON解析失败：" + jsonStr + "字符串转" + clazz);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json 字符串转JSONObject
     *
     * @param jsonStr json 字符串
     * @return JSONObject
     */
    public static JSONObject jsonToMap(String jsonStr) {
        if (StringUtils.isBlank(jsonStr))
            throw new MyException(Code.JSON_PARSE_NULL);
        try {

            return (JSONObject) JSON.parse(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(Code.JSON_PARSE_ERROR, jsonStr + "字符串转JSONObject");
        }
    }

    /**
     * 校验map中是否存在key对应的value
     *
     * @param jo  map
     * @param key key
     * @return String
     */
    public static String mapGetString(JSONObject jo, String key) {
        if (StringUtils.isBlank(key)) throw new MyException(Code.MAP_KEY_NULL);
        String val = jo.getString(key);
        if (val == null) {
            throw new MyException(Code.MAP_VALUE_NULL,  key + "的value为空,该值必须传入");
        }
        return val;
    }


    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return Map
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return Object
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = String.valueOf(map.get(field.getName()));
                    if (datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        field.set(obj, new Date(Long.parseLong(datetimestamp)));
                    }
                } else {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
