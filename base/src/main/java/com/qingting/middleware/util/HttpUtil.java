package com.qingting.middleware.util;

import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * http工具类
 */
@Slf4j
public class HttpUtil {

    private static final CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    public static final String TYPE_JSON = "application/json"; //消息主体是序列化后的 JSON 字符串
    public static final String TYPE_MULTIPART = "multipart/form-data"; //常见的 POST 数据提交的方式。我们使用表单上传文件时，必须让 form 的 enctype 等于这个值。
    public static final String TYPE_XML = "text/xml"; //是一种使用 HTTP 作为传输协议，XML 作为编码方式的远程调用规范
    public static final String TYPE_FROM = "application/x-www-form-urlencoded"; //提交的数据按照 key1=val1&key2=val2 的方式进行编码，key和val都进行了URL转码

    /**
     * 普通http请求
     *
     * @param url         请求路径
     * @param param       请求参数
     * @param contentType 请求类型(建议使用上方字段）
     * @return 请求结果
     * @throws Exception
     */
    public static String doHttpRequest(String url, String param, String contentType){
        //设置传入参数
        StringEntity se = new StringEntity(param, StandardCharsets.UTF_8);//解决中文乱码问题
        se.setContentEncoding("UTF-8");
        HttpPost hp = new HttpPost(url);
        hp.setHeader("Connection", "close");
        //设置网络文件的类型和网页的编码
        hp.setHeader("Content-Type", contentType);//
        hp.setEntity(se);
        String responseStr;
        try {
            //发送请求
            HttpResponse resp = closeableHttpClient.execute(hp);
            //获取返回内容
            responseStr = EntityUtils.toString(resp.getEntity(),StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("请求三方时出现异常:url ->[{}],param ->[{}],Content-Type ->[{}]",url,param,contentType);
            throw new MyException(Code.THIRD_PARTY_EXCEPTION);
        }
        return responseStr;
    }

    /**
     * 压缩文件http请求
     *
     * @param url   请求路径
     * @param param 请求参数
     * @return 请求结果
     * @throws Exception
     */
    public static String doHttpRequest(String url, String param) {
        //发送post请求
        HttpPost post = new HttpPost(url);
        //设置参数（压缩后的字节数组）
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(GZipUtil.compressToByte(param));
        post.setEntity(byteArrayEntity);
        post.setHeader("Content-Encoding", "gzip");
        String responseStr = "";
        try {
            //发送请求
            HttpResponse response = closeableHttpClient.execute(post);
            //获取返回内容
            responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("请求三方时出现异常:url ->[{}],param ->[{}]",url,param);
            throw new MyException(Code.THIRD_PARTY_EXCEPTION);
        }
        return responseStr;
    }
}
