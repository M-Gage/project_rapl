package com.qingting.productb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.util.CommonUtil;
import com.qingting.middleware.util.RSAEncryptionUtil;
import com.qingting.middleware.util.RedisUtil;
import com.qingting.middleware.util.thread.ChildThreadException;
import com.qingting.middleware.util.thread.MultiParallelThreadHandler;
import com.qingting.middleware.util.thread.MultiThreadHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Callable;

//import io.github.swagger2markup.GroupBy;
//import io.github.swagger2markup.Language;
//import io.github.swagger2markup.Swagger2MarkupConfig;
//import io.github.swagger2markup.Swagger2MarkupConverter;
//import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
//import io.github.swagger2markup.markup.builder.MarkupLanguage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    RedisUtil redisUtil;

    public static void main(String[] args) throws Exception {


        String param = "{\"method\":\"GET\",\"orderId\":\"P00201912201020293854215\",\"createTime\":1576808517181,\"count\":3,\"url\":\"https://sxqb.water0737.com/main/biz/ybNotify?data=dBxqPHUzYktiLZKJu9U+pW37bUIZ6Kx08qwfo1OBNF9XfVPmjLE23Gq20DBW8bvHELbtQHpjxNacfkwVRteBzON9OhrME0vFFnZlKuWPYttfmWhJA92wqSzLveIwEePbV2TInvhiBFvZ3k7HnTweuczyKch8Edr5QKM8M/zCQo4baCF7xAb5ovyIo6j2BqKaQRF6+zxssU0DuHKBJ9Z5mLmkRtaIyV/zhQDzn+eLmKRNztSNmNwD0GKw/pV5EiJbs3Unx1xP9/j+9Yn8N1CQk472AOJsKNKoTgU35BU0X1NBfAiSDpol7p//kHjx53V2ILkMZL7lZsB0PPZjwgKf/yTP4SVjYsbn+Cer5SrjFG4+CQvcdOZamAgbJrMIQmdsc9NlXzIRdEewJko4gJkEQq3PydYlHBpN1zCRPit4hDgoWHB7Z3WnOyEZH+23SwFfeumMAb6WhJspO+1VQ7wOvzleCs7js9Ncz5pUpVSbbfqU+lk0q/C8SezhOettOP1+\"}";


        System.out.println(String.format("登录输入参数[param=>%s]", param));

        //发起请求
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://sxqb.water0737.com/main/biz/ybNotify");
        //拼装请求头
        post.setHeader("Content-type", "application/json;charset=UTF-8");
        StringEntity byteArrayEntity = new StringEntity(JSONObject.toJSONString(param), StandardCharsets.UTF_8);
        post.setEntity(byteArrayEntity);
        try {
            //发送请求
            HttpResponse response = client.execute(post);
            String responseStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            System.out.println(String.format(" 返回responseStr：=> %s", responseStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void phoneNumberHandle() throws Exception {
        String oldPath = "D:\\13400000000-13499999999.txt";
        File oldFile = new File(oldPath);
        String oldStr = null;
        if (oldFile.exists() ) { // 判断文件或目录是否存在
            if (oldFile.isFile() ) {
                int lineNumber = -1;
                //该缓冲流有一个readLine()独有方法
                BufferedReader oldBr = new BufferedReader(new FileReader(oldPath));//该缓冲流有一个readLine()独有方法
                while ((oldStr = oldBr.readLine()) != null) {
                    lineNumber++;
                    String phoneNumber = "134" + CommonUtil.zeroPadding(lineNumber, 8);
                    redisUtil.set(oldStr,phoneNumber);
                }
            }
        }
    }

    @org.junit.Test
    public void phoneNumberCheck() throws Exception {
        String newPath = "D:\\WorkSpace\\DockingDocument\\zsp_20200113_1182\\zsp_20200113_1182-phone-0";
        File newFile = new File(newPath);

        String outPath = "D:\\outPhoneList.txt";
        File outFile = new File(outPath);

        StringBuilder stringBuilder = new StringBuilder();

        String newStr = null;
        if (newFile.exists() ) { // 判断文件或目录是否存在
            if (newFile.isFile() ) {
                int count = 0;
                //该缓冲流有一个readLine()独有方法
                BufferedReader newBr = new BufferedReader(new FileReader(newFile));//该缓冲流有一个readLine()独有方法
                while ((newStr = newBr.readLine()) != null) {
                    count++;
                    String phoneNumber = (String) redisUtil.get(newStr);
                    System.out.println(count +" -> "+newStr);
                    System.out.println(phoneNumber);
                    stringBuilder.append(phoneNumber);
                    stringBuilder.append("\n");
                }

                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                FileOutputStream outPutStream = new FileOutputStream(outFile);
                String context = stringBuilder.toString();//将可变字符串变为固定长度的字符串，方便下面的转码；
                byte[] bytes = context.getBytes("UTF-8");//因为中文可能会乱码，这里使用了转码，转成UTF-8；
                outPutStream.write(bytes);//开始写入内容到文件；
                outPutStream.close();
            }
        }
    }

    @org.junit.Test
    public void rsaTest() throws Exception {

        String s = "dBxqPHUzYktiLZKJu9U+pW37bUIZ6Kx08qwfo1OBNF9XfVPmjLE23Gq20DBW8bvHELbtQHpjxNacfkwVRteBzON9OhrME0vFFnZlKuWPYttfmWhJA92wqSzLveIwEePbV2TInvhiBFvZ3k7HnTweuczyKch8Edr5QKM8M/zCQo4baCF7xAb5ovyIo6j2BqKaQRF6+zxssU0DuHKBJ9Z5mLmkRtaIyV/zhQDzn+eLmKRNztSNmNwD0GKw/pV5EiJbs3Unx1xP9/j+9Yn8N1CQk472AOJsKNKoTgU35BU0X1NBfAiSDpol7p//kHjx53V2ILkMZL7lZsB0PPZjwgKf/yTP4SVjYsbn+Cer5SrjFG4+CQvcdOZamAgbJrMIQmdsc9NlXzIRdEewJko4gJkEQq3PydYlHBpN1zCRPit4hDgoWHB7Z3WnOyEZH+23SwFfeumMAb6WhJspO+1VQ7wOvzleCs7js9Ncz5pUpVSbbfqU+lk0q/C8SezhOettOP1+";

        s = s.replaceAll("\\+", "%2B");
        System.out.println(s);

        //初始化密钥
        //生成密钥对
//        Map<String, Object> keyMap = RSAEncryptionUtil.initKey();
//
//        //公钥
//        String privateKeyStr = RSAEncryptionUtil.getPrivateKey(keyMap);
//
//        //私钥
//        String publicKeyStr = RSAEncryptionUtil.getPublicKey(keyMap);
//        System.out.println("公钥：" + publicKeyStr);
//        System.out.println("私钥：" + privateKeyStr);

        //原始文件
        //线上
        String name = "sanxin";
        ModelFiled mf = new ModelFiled(name);
        String str = mf.str;
//                "
//        String str = "{\n" +
//                "            \"productId\": \"8002\",\n" +
//                "                \"mchOrderNo\":\"\",\n" +
//                "                \"amount\": \"0.01\",\n" +
//                "                \"subject\": \"网络购物\",\n" +
//                "                \"body\": \"网络购物\",\n" +
//                "                \"notifyUrl\": \"http://127.0.0.1:8191/api/paydemo/notify.htm\",\n" +
//                "                \"returnUrl\": \"http://127.0.0.1:8191/api/paydemo/return.htm\",\n" +
//                "                \"extra\": \"\",\n" +
//                "                \"sign\":\"\"\n" +
//                "        }";
//        str = "{\"name\":\"刘坤\",\"idCard\":\"420923199108192474\",\"mobile\":\"18571551732\",\"CHSIStatus\":\"0\"}";
        String priKey = mf.priKey;
        String pubKey = mf.pubKey;
//        String data = "dBxqPHUzYktiLZKJu9U pW37bUIZ6Kx08qwfo1OBNF9XfVPmjLE23Gq20DBW8bvHELbtQHpjxNacfkwVRteBzON9OhrME0vFFnZlKuWPYttfmWhJA92wqSzLveIwEePbV2TInvhiBFvZ3k7HnTweuczyKch8Edr5QKM8M/zCQo4baCF7xAb5ovyIo6j2BqKaQRF6 zxssU0DuHKBJ9Z5mLmkRtaIyV/zhQDzn eLmKRNztSNmNwD0GKw/pV5EiJbs3Unx1xP9/j 9Yn8N1CQk472AOJsKNKoTgU35BU0X1NBfAiSDpol7p//kHjx53V2ILkMZL7lZsB0PPZjwgKf/yTP4SVjYsbn Cer5SrjFG4 CQvcdOZamAgbJrMIQmdsc9NlXzIRdEewJko4gJkEQq3PydYlHBpN1zCRPit4hDgoWHB7Z3WnOyEZH 23SwFfeumMAb6WhJspO 1VQ7wOvzleCs7js9Ncz5pUpVSbbfqU lk0q/C8SezhOettOP1";

        //私钥加密公钥解密
//        String encrypt = RSAEncryptionUtil.encryptByPrivateKey(str, priKey, false);
//        System.out.println("加密后的数据：" + encrypt);
//        String decrypt = RSAEncryptionUtil.decryptByPublicKey(encrypt, pubKey, false);
//        if (decrypt.equals(str)) {
//            System.out.println("解密后的数据：" + decrypt);
//        }

        //公钥解密
        String decrypt = RSAEncryptionUtil.decryptByPublicKey(s, pubKey, true);
        System.out.println("解密后的数据：" + decrypt);

//        String decrypt = RSAEncryptionUtil.decryptByPrivateKey(data,priKey, false);
//        System.out.println("解密后的数据：" + decrypt);

        //公钥加密私钥解密
//        String encrypt = RSAEncryptionUtil.encryptByPublicKey(str, pubKey, true);
//        System.out.println("加密后的数据：" + encrypt);
//        //RPFtHfpccgFvU/6w7VQEgrmXt6DmarESNWDB/MMucsnfpM3Luz8LVKW3efKSwo13cpYJCCgVixSGfUFd8Bj3SFnVJT3PCnv1KYVqfYQgXu101nUPA6YrdWm+ghjzndqSV4yTcxXrz8suATceYVjEDENudLONesIRA5Z2CTvNhks=
//        String decrypt = RSAEncryptionUtil.decryptByPrivateKey(encrypt, priKey, true);
//        if (decrypt.equals(str)) {
//            System.out.println("解密后的数据：" + decrypt);
//        }

    }


    static class ModelFiled {
        String str;
        String priKey;
        String pubKey;

        ModelFiled(String name) {
            if ("sanxin".equals(name)) {
                str = "0825";
                priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJPiJAgvs7c0ANT4hEuReNJJmw5ODax2+UOJosKgjwtyN5YZ/lfIh5zfH4sDv/nIy3N7DMEmR4LLL/R9Fv9YGWjGmpZsoX382MeLHnNMrnhQ8WPqbjrx+oX2HI+wv000K1w7npl41y/DDNTQLGL0JuHjV2CP4fxvLrf5X0oT75zNAgMBAAECgYB7DGiU0zRDVSb4DkzuH/4bY5l8HqAsB9bCRozCSxybFo9/+uLyIe4NLm6tmhIsAdKfpRxovrQz9WNSmJ8BGkoQ6RqWIhgld159gzrAL062nFQtHAAM9qGNO3doQ82xOh3PMR1RcpHIMgwTAb8iZvfnxGj2RwX0GsQ6DU3TDfxCgQJBANxT8HpslytsCh4YVSva5UweHljWb36eypKohkkOKZ3RozQZYLT0OVI8GP9M04EJB7ffWsj60LdVo4FfvWfvIKECQQCr04zXTPtwXVU4vmzDGrnG7tdIavYxCR4RhAvoARxqNj6RJiAwiWtdWH7yZYdf4HUxUc4z31lYl+uJ9wgnfJCtAkBqW0q2jjzbBShNJsnWhbAyckeL8teXc+etrBRGp8OHH2988tMwBsS04bXfZ6Kho05KgFM+uXSPS5PGHAU+aryBAkAZ1+KqC0ESIGeN9k4jdiSVAB1PHg9TRrzIA6VKysf8TZZRfHuA8Vo9mu7VBGI9EGRv+47ZDHHCum7JvGb0kW6tAkAKGr4YtCwYvZnR+Dohdeu7NcvMuQIe/X/Ajcfkh0VOqf/rDmVrbntd99nSJRd4oQzdqsfXs4TnRUHpLEyo/O2k";
                pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT4iQIL7O3NADU+IRLkXjSSZsOTg2sdvlDiaLCoI8LcjeWGf5XyIec3x+LA7/5yMtzewzBJkeCyy/0fRb/WBloxpqWbKF9/NjHix5zTK54UPFj6m468fqF9hyPsL9NNCtcO56ZeNcvwwzU0Cxi9Cbh41dgj+H8by63+V9KE++czQIDAQAB";
            }
            if ("devTest".equals(name)) {
                str = "0825";
                priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJue/zqT4ak3IfM9cIaYixvm2ojwkbvRb1yCDZSxEsj5QEC1r8N49WUpMKxYD+EPb+PUS5aSHiNwa3AuvFCzswjD9juk1Qk8r8C/NaPtopOg3djKGLUvTDu5ityJiAysz0XhTWGlRgN5WY6WyPVEGFP+vOiu6oQz02o5gw/SS96tAgMBAAECgYEAgtmI33cymjaqTD0P3YxsA1Tz0Yr97r+l9FHCG5FcKzIxDXf3RtPw+lNNAsy05Vc2jImX2Q4ZW1EOdXBGlVDTqq31jF6g9wJ/bMWthfnjXkbRtoLNwIncj51O5cjFu7A70tbJcACQIb4WOe4iyob/eaSveJAr1K8/mWJEZhEIJ2ECQQDTBnc+uEpYcIy2U9h1L+atpi/lMBcqQuURwWamFFPDldA0PDwexsewqdbp5Twy8ALu2RL3vxVd8+AYvruGhIXVAkEAvMmuLrOl8TE/E7Wvix4JBZiEEyVW3Retb/Bbuu9H/k3c9xXTZPuDZDD2+vTvzHidO1mJWf6AKdetF4vXSJOpeQJAXPtzXXp8n71v8wK81N33abpZTkZDXsa9AnbSUQmR4xNG+00zPGhgItKjNMpiHRuuXync9rcGpjBIJP8dx+bqxQJBAIcA6rC3A4oRn3vmx1wC5Iy93mUapJ2C2yJbjh2GE8PFxqhjUUK7oeA6K8SW+A0Oi9HvW4uhteE2PK/D7vYVsnECQAiwm9ySMYHhZHFSGTDEIhiHf8Hh+JU9SJFH/xL5n/oD9YTv93MPJomusvfZCTJyZXmVEd3pHj0OiRm5eX0zUdI=";
                pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbnv86k+GpNyHzPXCGmIsb5tqI8JG70W9cgg2UsRLI+UBAta/DePVlKTCsWA/hD2/j1EuWkh4jcGtwLrxQs7MIw/Y7pNUJPK/AvzWj7aKToN3Yyhi1L0w7uYrciYgMrM9F4U1hpUYDeVmOlsj1RBhT/rzoruqEM9NqOYMP0kverQIDAQAB";
            }
            if ("jijiWallet".equals(name)) {
                str = "hngM:?73|MT4mMPh";
                priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALOSB/1Ia3kuPH/KwMvDCeMCN7epepHRBg1bVAWQ1jmqncpDzaQAu470OtvM6jABft2i3CIXCSahWxt2owL7/e9CokcdakAOBHr8NV6LtjW0FZgdbz6OZDNeP6pcDz/5tO+0C5APu24pwaGrcTuAyEOXyFBLnYH3erP35bCXaYyJAgMBAAECgYA3QHaO22qeNbjNxU//Ijr3i2Yjb9VT6pHHKFO5BrCzu6ijZKtwi/0p5kH6AZoJPVyQy5yaLKZXKYzD7YQI1gpywNj0Z8pw5EO+whvs4Yff4R1ES0BPY3jDpxc2a1H9Q7vTNDe+yBEAuWLITL0nEBlX2jOVHpWZirN/xd14FfI+gQJBAOH5vTaIO0tRx3Rz2FYamPdOKkeGottVP+uweNleBEGMHCH+gDm05S6EUtgq0kZdHeNvrVf2cpUfoycGac4im1kCQQDLbeIjH7V7e3OCis7LjgKq9zi2Ru0HMB9CvO2PBuS8JQgG3Fziz/k3Hdpgsc2EgMFXbJBjoNz67Uih4kkxbMSxAkBRxxQveOukXUsGHKpMtsFmthXc3p9vKpHAVJPXbguyQOcpcjWrLIjH5cEpU43XQvl/BjpAHALpbpKXm92MaL+5AkBc/AkVjPTjUHZoARKtMGg/xWkCyECnvHNNihX7/manKTYWDLvYYL/FVbvv3tXV7xRw5YrKP0lC2RsOA+T41oLBAkEAw1yYD6uaE9B8W13zMjbTxJ1PnslKVUbMFUX/QKXjkxVLS+Uo2FJyQLtdtnxSxqaEH3nrKAi7Ub5PyAKEC0yrKQ==";
                pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzkgf9SGt5Ljx/ysDLwwnjAje3qXqR0QYNW1QFkNY5qp3KQ82kALuO9DrbzOowAX7dotwiFwkmoVsbdqMC+/3vQqJHHWpADgR6/DVei7Y1tBWYHW8+jmQzXj+qXA8/+bTvtAuQD7tuKcGhq3E7gMhDl8hQS52B93qz9+Wwl2mMiQIDAQAB";
            }
            if ("pay".equals(name)) {
                str = "";
                priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJKyzCim8c0s434P4rL8yiZG9GMIXbq7K0wMDEN2AfnMm8SEIdWPBNXLyaOq7u09xL0URTYLrzck02Yoyhe5LXTNV3F8CpCfkxC5l3tr0BnLImqSxDaowRc1sEwZbBuWDvC/be9P7cJkNAZongRbhdO1vzHaOajzX6xsMgohwS4VAgMBAAECgYEAheh+IhGaIW77TkRT7IyCPoSAarHlVSGrOSS6QQ5EaXCTM8RlWhrOEBaQvjy3EopSv0H0l4v86fpMmjFboGIhkd4D1Mojytc8hVLbLMkCVXhq53TcPRAWh6ZHLLjxnx6frsPZ2Otm0Tb1JDGbFquBMEAckaaPmrL5l+t1+IzTFGECQQDKuAgby2sN3TWOSBZu2ohde1KTfysLk7Vb+Q7PbUcaK17CaYwTr8HS9DJ1TewUsqTmdt9RYKew4kFha6aCLJQdAkEAuUFsEEBbndLQ44KTiq8/rG2lEbpDlwixVbaX3kepzAYW8qm0x8llwI4sK0n//CBRb7oKkbWAuF7KrOnY6TlwWQJAP+MLuya6kF38uOCDIWCBxWROO+nB+R4N8/Rj9H8t/1NXw9FbsgCcBw2qsUljc4pxaahg7NaXQqmrjTZN/YxrCQJBAJmvDFbvDRIAsgoLaRrEB66jxyzNuQSy0Wqp75MXIGWBJ8hnwPF8wkzbpQjEevhOV0C4tcvrJr3M5ZZM6QWSAQECQGnvFLxF/klbmaYf6+UhTJCeR4tF8l/gD/Vx88lRHUehuF4qDZ6biQ7bYYzBmNm4Qk4VcIGNdHX6xy5ZV3HilKc=";
                pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSsswopvHNLON+D+Ky/MomRvRjCF26uytMDAxDdgH5zJvEhCHVjwTVy8mjqu7tPcS9FEU2C683JNNmKMoXuS10zVdxfAqQn5MQuZd7a9AZyyJqksQ2qMEXNbBMGWwblg7wv23vT+3CZDQGaJ4EW4XTtb8x2jmo81+sbDIKIcEuFQIDAQAB";
            }
            if ("modelTest_1016".equals(name)) {
                str = "|iZ,NQW)t&19jLFo";
                priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIdK5Ulxn6jlBYqQfTle7dYSwdmZbwi4O7XWdYrlkShsnQv74RWhDuuSWfHK/dQmoB4BWC9j9JnwdD4qUylV3dxFaSLlQtm2QFYaJ1Z9N3gDsEgMAE3RH1hDpQwcNFLEmTFQxZ6Vjml5BJixeczxhO6ujwcAOejXX0M0hikNmSHHAgMBAAECgYBr68yXF3Ia0gXnkHazqwvhtji4EXLG69+Fn7tHvvRZ4Lot8xCLplw2ghoJjLmJ3/3dsYjc1MkreunfJPgNgxdkEvBbkQe1MQDKEQOhSetBAJFexRGIiPbwFVuLrBqfLpsO1+tlz6gou9ZPdoXBQ/6Khqqgh0pKZshFznm2WrPKYQJBAMWcgz+rCBWPMSh36ktWFvwASzS/+6cZ6/K34GpGvG3SqgtuMdZlVp8fmmqcdjYtvnEznDaA1BIfcvJpccVp5LkCQQCvRIlPaUSS8QsocZ9PlRIw29Z08I1siI6QRkeFWuY4j3rtB5jNyGCpsbJhcGJFJmcqys5QHAJ/9HKHKSjoVPp/AkA3K5g1GVH7euvViY0fFVz7ZDWa95KRt5n5PL7pzO/klXEmGdDOM6K4cEFTTHVwcNr/jdIqIVOT77XO2gOKgyL5AkAirZXZf4OQ616AzxJtZZpmzmh9VMc2rUI/65flG9onYMQ6t+EU/kuZ583al6jp87I/9kah9RjNhmlOsuckKQQzAkEAk8K6tylsMuH0f8ti95yROs629fRcx3xSpDsl/J8p47pHtAGBLrI1DoCnoZg5wi6WNnA95sdXeTGK9b0mH8L2Rw==";
                pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHSuVJcZ+o5QWKkH05Xu3WEsHZmW8IuDu11nWK5ZEobJ0L++EVoQ7rklnxyv3UJqAeAVgvY/SZ8HQ+KlMpVd3cRWki5ULZtkBWGidWfTd4A7BIDABN0R9YQ6UMHDRSxJkxUMWelY5peQSYsXnM8YTuro8HADno119DNIYpDZkhxwIDAQAB";
            }
            if ("jmicronLoan".equals(name)) {
                str = "j$xs~/0RSSH6>Pt2";
                priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK04CVi2De0YJfUIYrrxupH5iozTbPMqEm3Hi6qUtz3Zj4dSW2hxTh8fU9aAwg1wFhKl0ONrvQX4ZHUTemuAcVYDj1KaW4Lr4pm23dtiCFMQQw33W0QtFe3iZB8kOajDxzTLPbeCTGk+UEXa4GXlvjlZsAtGGVxr9+3mMlBctpXlAgMBAAECgYA8aPaZgaSJs4pJUotcN0Gh9y1iv7mXdGzI7HbBHuxpHZ74yrgmTJ26LOrIPE9w8E5CYqHfLovVsTMXwZtIMptTmQk99Sb1o8WIHJb+j8eeNRX7DgBTiHxcHda2Y7HyZ9k4+pGvY4EtTriImFN2xXa+5xhTuxkyugFNMHJn7QcJYQJBAN1ny+AioGn9JGjp+2xS8kqbBDBq+GgrMoLICV4iIuOg7YwvZpqnh2xE3oWjP0yO+aI+ww1vUEV20TgbiEObRV8CQQDISMfjS6ujjVVhrPZf6QNDDkZ7/c8eypobB9T2eXtfKwSgselxcw2McjAJkTW7LCdHWx4/gjKk+xKf757L+Ac7AkBc84i6To8+MBKrISB299PU0oPNt/sV8JzCHy0fogE3w4Y3RmczIoZORBOP01ttztCq9uJ4r3Tp1No9TgHFMFjNAkEAlyVlxACtLSZR0v6mh8bfMVGqm5P1JLWdCt0kOEWBd1aNjX/2Pimnhd/+pJmzROeTVORMtXzaH2tiqNQ6sI8jVQJBAIiPaR1GBqUF3PBET8sJ1LPzBlLTShLZMi4Zn7yXaNWRiGeUD1TxTZTIuQOOrccJuqGeJFNgZ/NYyBKTIFX4QD0=";
                pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtOAlYtg3tGCX1CGK68bqR+YqM02zzKhJtx4uqlLc92Y+HUltocU4fH1PWgMINcBYSpdDja70F+GR1E3prgHFWA49SmluC6+KZtt3bYghTEEMN91tELRXt4mQfJDmow8c0yz23gkxpPlBF2uBl5b45WbALRhlca/ft5jJQXLaV5QIDAQAB";
            }
        }
    }





    /*swagger生成文档*/
 /*   @org.junit.Test
    public void generateAsciiDocs() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8086/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("../docs/asciidoc/swagger_api"));
    }

    @org.junit.Test
    public void generateMarkdown() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8086/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("../docs/markdown/swagger_api"));
    }*/
}
