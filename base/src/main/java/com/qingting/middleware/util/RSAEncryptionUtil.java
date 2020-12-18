package com.qingting.middleware.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Rsa 加密工具类
 */
public class RSAEncryptionUtil {
    //非对称密钥算法
    private static final String KEY_ALGORITHM = "RSA";
    //数字签名使用的安全哈希算法，用SHA算法进行签名，用RSA算法进行加密
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;
    //公钥
    private static final String PUBLIC_KEY = "RSAPublicKey";
    //私钥
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 初始化密钥对
     *
     * @return Map 甲方密钥的Map
     */
    public static Map<String, Object> initKey() throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //甲方私钥
        RSAPrivateKey privateKey1 = (RSAPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey1);
        return keyMap;
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 私钥加密
     *
     * @param data   待加密数据
     * @param priKey 密钥
     * @return String 加密数据
     */
    public static String encryptByPrivateKey(String data, String priKey, boolean isSub) throws Exception {
        byte[] privateKey = Base64.decodeBase64(priKey);
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey pk = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher ci = Cipher.getInstance(KEY_ALGORITHM);
        ci.init(Cipher.ENCRYPT_MODE, pk);

        if (isSub) {
            return subEncrypt(data, ci);
        } else {
            return Base64.encodeBase64String(ci.doFinal(data.getBytes()));
        }
    }

    /**
     * 公钥加密
     *
     * @param data   待加密数据
     * @param pubKey 密钥
     * @return String 加密数据
     */
    public static String encryptByPublicKey(String data, String pubKey, boolean isSub) throws Exception {
        byte[] pubilcKey = Base64.decodeBase64(pubKey);
        //取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubilcKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成公钥
        PublicKey pk = keyFactory.generatePublic(x509KeySpec);
        //数据加密
        Cipher ci = Cipher.getInstance(KEY_ALGORITHM);
        ci.init(Cipher.ENCRYPT_MODE, pk);
        if (isSub) {
            return subEncrypt(data, ci);
        } else {
            return Base64.encodeBase64String(ci.doFinal(data.getBytes()));
        }
    }

    /**
     * 分段加密
     *
     * @param data 原始文件
     * @param ci   加密类
     * @return String 加密后的文件
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    private static String subEncrypt(String data, Cipher ci) throws Exception {
        byte[] bytes = data.getBytes();
        int inputLen = bytes.length;
        int offLen = 0;//偏移量
        int i = 0;
        ByteArrayOutputStream bops = new ByteArrayOutputStream();
        while (inputLen - offLen > 0) {
            byte[] cache;
            if (inputLen - offLen > 117) {
                cache = ci.doFinal(bytes, offLen, 117);
            } else {
                cache = ci.doFinal(bytes, offLen, inputLen - offLen);
            }
            bops.write(cache);
            i++;
            offLen = 117 * i;
        }
        bops.close();
        byte[] encryptedData = bops.toByteArray();


        return Base64.encodeBase64String(encryptedData);
    }


    /**
     * 公钥解密
     *
     * @param data   待解密数据
     * @param pubKey 密钥
     * @return byte[] 解密数据
     */
    public static String decryptByPublicKey(String data, String pubKey, boolean isSub) throws Exception {
        data = data.replace(" ", "+").replaceAll("%2B", "+");
        byte[] publicKey = Base64.decodeBase64(pubKey);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        //产生公钥
        PublicKey pk = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher ci = Cipher.getInstance(keyFactory.getAlgorithm());
        ci.init(Cipher.DECRYPT_MODE, pk);
        if (isSub) {
            return subDecrypt(data, ci);
        } else {
            return new String(ci.doFinal(Base64.decodeBase64(data)));
        }

    }

    /**
     * 私钥解密
     *
     * @param data   待解密数据
     * @param priKey 密钥
     * @return byte[] 解密数据
     */
    public static String decryptByPrivateKey(String data, String priKey, boolean isSub) throws Exception {
        data = data.replace(" ", "+").replaceAll("%2B", "+");
        byte[] privateKey = Base64.decodeBase64(priKey);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化私钥
        //密钥材料转换
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        //产生私钥
        PrivateKey pk = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher ci = Cipher.getInstance(keyFactory.getAlgorithm());
        ci.init(Cipher.DECRYPT_MODE, pk);
        if (isSub) {
            return subDecrypt(data, ci);
        } else {
            return new String(ci.doFinal(Base64.decodeBase64(data)));
        }
    }

    /**
     * 分段解密
     *
     * @param data 解密文件
     * @param ci   加密类
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    private static String subDecrypt(String data, Cipher ci) throws Exception {
        byte[] bytes = Base64.decodeBase64(data);
        int inputLen = bytes.length;
        int offLen = 0;
        int i = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (inputLen - offLen > 0) {
            byte[] cache;
            if (inputLen - offLen > 128) {
                cache = ci.doFinal(bytes, offLen, 128);
            } else {
                cache = ci.doFinal(bytes, offLen, inputLen - offLen);
            }
            byteArrayOutputStream.write(cache);
            i++;
            offLen = 128 * i;

        }
        byteArrayOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new String(byteArray);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return String 数字签名
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        //解密私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey2);
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        //解密公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }
}
