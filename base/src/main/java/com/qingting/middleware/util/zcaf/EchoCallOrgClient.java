package com.qingting.middleware.util.zcaf;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * 平台回调
 */
public class EchoCallOrgClient {

    public static void main(String[] args) {
        String eString = "ow6X2lFQSklfdWjAMkpY7ReZQPUtBr7%2B4%2B4wuWdidzUCJA4KkMV14TfKnqjgUZXYX2o5udViWH6FEU8SXU3Yeld%2FQlk%3D";
        String key = "7fcd08d4f0891387";
        try {
            System.out.println("返回结果:"+encrypt(eString,key));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对明文进行RC4加密、URL编码.
     *
     * @param text 明文
     * @return 密文
     */
    public static String encrypt(String text, String rc4Key) throws UnsupportedEncodingException {
        /** RC4加密 */
        String textEncrypted = RC4_128_V2.encode(text, rc4Key);
        /** URL编码 */
        String textEncryptedEncoded = URLEncoder.encode(textEncrypted, "UTF-8");
        return textEncryptedEncoded;
    }

    /**
     * 对密文进行URL解码、RC4解密.
     *
     * @param text 密文
     * @return 明文
     */
    public static String decrypt(String text, String rc4Key) throws UnsupportedEncodingException {
        /** URL解码 */
        String textDecoded = URLDecoder.decode(text, "utf-8");
        /** RC4解密 */
        String textDecodedDecrypted = RC4_128_V2.decode(textDecoded, rc4Key);
        return textDecodedDecrypted;
    }

}
