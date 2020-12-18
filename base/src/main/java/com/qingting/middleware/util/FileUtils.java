package com.qingting.middleware.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    /**
     * 读取本地JSON数据
     *
     * @param path       本地路径
     * @return json      返回数据
     */
    public static String read(String path){

        if(StringUtils.isBlank(path)){
            return null;
        }
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            System.out.println("#######文件内容读取失败#######:"+ path);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            String rep = jsonStr.replaceAll("\\r", "");
            String result = rep.replaceAll("\\n", "");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
