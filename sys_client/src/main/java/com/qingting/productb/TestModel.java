package com.qingting.productb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.ReportBaseBean;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.ReportCompleteService;
import com.qingting.middleware.util.GZipUtil;
import com.qingting.middleware.util.HttpUtil;
import com.qingting.middleware.util.RSAEncryptionUtil;
import com.qingting.middleware.util.SpringUtil;
import com.qingting.middleware.util.thread.ChildThreadException;
import com.qingting.middleware.util.thread.MultiParallelThreadHandler;
import com.qingting.middleware.util.thread.MultiThreadHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;


public class TestModel {
    /*
     *基础参数，请进行更换
     * */
    private final static String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbnv86k+GpNyHzPXCGmIsb5tqI8JG70W9cgg2UsRLI+UBAta/DePVlKTCsWA/hD2/j1EuWkh4jcGtwLrxQs7MIw/Y7pNUJPK/AvzWj7aKToN3Yyhi1L0w7uYrciYgMrM9F4U1hpUYDeVmOlsj1RBhT/rzoruqEM9NqOYMP0kverQIDAQAB";
    private final static String appKey = "devTest";
    private final static String key = "0825";
//        private final static String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfehFIzdL+6VSQA2C1nHbDmEpNu7o2Z4UIjNAJDAD4okZCNrnekXhvrCjpnf6MJukmVvF49gBpuQMxHg7+S0gmz/J3A3s+nPPIU8VMYUUuDBS3xZivmyAtmLxTUpIaiftPat0klo/bg0PlpO5QW5JzX7/UZDBSc+eAUi8EcEi10QIDAQAB";
//    private final static String appKey = "productTest";
//    private final static String key = "0svvjhrhlk#v0wV^";
//            private final static String loginUrl = "http://47.105.133.53:8082/aladdin/login";
//    private final static String modelUrl = "http://47.105.133.53:8082/aladdin/api/model/fangRiTu";
    private final static String modelUrl = "http://localhost:8086/api/model/fangRiTu";
    private final static String loginUrl = "http://localhost:8086/login";
//        private final static String modelUrl = "http://47.105.133.53:8082/aladdin/api/model/fangRiTu";
//    private final static String loginUrl = "http://47.105.133.53:8082/aladdin/login";
//    private final static String modelUrl = "http://localhost:8082/aladdin/api/model/fangRiTu";
//    private final static String loginUrl = "http://localhost:8082/aladdin/login";
//    private final static String modelNo = "MX_05_191213";
//    private final static String modelName = "QT_MX_Z_V5";
    private final static String modelNo = "MX_06_200106";
    private final static String modelName = "QT_MX_Z_V6";


    public static void main(String[] args) throws Exception {
        /*
         * 登录获取token
         * */
        String token = login(appKey, key, loginUrl);

        MultiThreadHandler handler = new MultiParallelThreadHandler();

        List<String> l = new ArrayList<String>();
//        l.add("{\"name\":\"程乐\",\"mobile\":\"15605817085\",\"idCard\":\"330822199001265713\",\"CHSIStatus\":\"0\"}");


        l.add("{\"name\":\"孔德功\",\"mobile\":\"15025906650\",\"idCard\":\"622923197804200810\",\"CHSIStatus\":\"0\"}");


//        l.add("{\"name\":\"孔德功\",\"mobile\":15025906650\",\"idCard\":\"622923197804200810\",\"CHSIStatus\":\"0\"}");

        /*
         * 调取风控
         * */
        /*for (String s : l) {
            String model = model(token, modelUrl, s);
        }*/

        for (String s : l) {
            handler.addTask(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return model(token, modelUrl, s);
                }
            });
        }


        try {
            System.out.println("开始.....");
            List<Object> call = handler.call();
            for (Object o : call) {
                System.out.println(o.toString());
            }
        } catch (ChildThreadException e) {
            e.printStackTrace();
        }


    }

    static String model(String token, String url, String ui) throws Exception {
        /*
         * 分控基础参数
         * */
        Map param = new TreeMap();
        String path = "C:\\Users\\Administrator\\Documents\\WeChat Files\\mgz1262389660\\FileStorage\\File\\2020-01\\1070275465564430358.json";
        File file = new File(path);
        StringBuilder stringBuilder = new StringBuilder();
        if (file.exists()) { // 判断文件或目录是否存在
            if (file.isFile()) {
                //该缓冲流有一个readLine()独有方法
                BufferedReader br = new BufferedReader(new FileReader(file));//该缓冲流有一个readLine()独有方法
                String str = null;
                while ((str = br.readLine()) != null) {
                    stringBuilder.append(str);
                }
            }
        }

        param.put("operatorReport",stringBuilder.toString());
        param.put("user", RSAEncryptionUtil.encryptByPublicKey(ui, pubKey, true));
        param.put("modelNo", RSAEncryptionUtil.encryptByPublicKey(modelNo, pubKey, true));
        param.put("modelName", RSAEncryptionUtil.encryptByPublicKey(modelName, pubKey, true));
        //可选，用于检验渠道推送质量
        param.put("channelId", "13245878");

        System.out.println(String.format("风控输入参数[param=>%s]", param));

        //发起请求
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        //拼装请求头
//        post.setHeader("Content-Encoding", "gzip");
        post.setHeader("Content-type", "application/json;charset=UTF-8");
        post.setHeader("Authorization", token);
        post.setHeader("Content-Encoding", "gzip");
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(GZipUtil.compressToByte(JSONObject.toJSONString(param)));
        post.setEntity(byteArrayEntity);
//            while (true){
        try {

            //发送请求
            HttpResponse response = client.execute(post);
            String responseStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            Map<String, String> res = (Map<String, String>) JSON.parse(responseStr);
            String data = "";
            try {
                data = RSAEncryptionUtil.decryptByPublicKey(res.get("data"), pubKey, true);
            } catch (Exception e) {
                System.out.println(String.format("ui=> %s 返回空", ui));
            }
            System.out.println(String.format(" 返回[data：=>%s]", data));
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
//            }
        return null;
    }

    static String login(String appKey, String key, String url) throws Exception {
        /*
         * 登录基础参数
         * */
        Map param = new TreeMap();
        param.put("appKey", appKey);
        param.put("key", RSAEncryptionUtil.encryptByPublicKey(key, pubKey, false));

        System.out.println(String.format("登录输入参数[param=>%s]", param));

        //发起请求
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        //拼装请求头
        post.setHeader("Content-type", "application/json;charset=UTF-8");
        StringEntity byteArrayEntity = new StringEntity(JSONObject.toJSONString(param), StandardCharsets.UTF_8);
        post.setEntity(byteArrayEntity);
        try {
            //发送请求
            HttpResponse response = client.execute(post);
            String responseStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            Map<String, String> res = (Map<String, String>) JSON.parse(responseStr);
            System.out.println(String.format(" 返回token：=> %s", res.get("data")));
            return res.get("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}