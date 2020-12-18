package com.qingting.middleware.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES CBC 加密、解密算法
 * 
 * @author xiaoliang.chen
 * @version $Id: DesCbcSecurity.java, v 0.1 2017年12月16日 下午12:55:29 xiaoliang.chen Exp $
 */
//todo 有空整合一下
public class DESCrypto {

    public static final String SECRET_DES    = "DES";
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static void main(String[] args) throws Exception {
        
        String key = "IKo4FGe1";
        String bbbString = "customerId=HJGS1806081013&customerProdId=PROD1806159021508163743&name=吴仁彪&mobile=1582716758&idCardNo=42092319910204529&timestamp=1528527568482";

        System.out.println("最终加密后： " + encrypt(bbbString, key));
    }
    /**
     * 加密
     * @author xiaoliang.chen
     * 2017年12月16日 下午12:59:28
     * @param content
     * @param key
     * @return
     */
    public static String encrypt(String content, String key) {
        return byteToHexString(encrypt(content.getBytes(), key.getBytes()));
    }

    public static byte[] encrypt(byte[] content, byte[] keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_DES);
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @author xiaoliang.chen
     * 2017年12月16日 下午1:01:01
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        byte[] contentBytes = hexStringToBytes(content);
        return decrypt(contentBytes, key.getBytes());
    }
    
    public static String decrypt(byte[] content, byte[] keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_DES);
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            byte[] result = cipher.doFinal(content);
            String contentString = new String(result);
            return contentString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}