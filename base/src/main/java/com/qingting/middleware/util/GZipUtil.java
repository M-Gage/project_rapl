
package com.qingting.middleware.util;


import com.qingting.middleware.enums.Code;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import com.qingting.middleware.exception.MyException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * gzip 工具类
 */
public class GZipUtil {

    public GZipUtil() {
    }

    /**
     * 将字符串进行gzip压缩(返回字符串）
     *
     * @param str
     * @return String
     */
    public static String compressToString(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new MyException(Code.GZIP_COMPRESS_NUll);
        } else {
            try {
                return new String(compressToByte(str), StandardCharsets.UTF_8);
            } catch (Exception var2) {
                throw new RuntimeException("GZipUtil.compressData error", var2);
            }
        }
    }

    /**
     * 将字符串进行gzip压缩(返回base64编码字符串）
     *
     * @param str
     * @return String
     */
    public static String compressToBase64String(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new MyException(Code.GZIP_COMPRESS_NUll);
        } else {
            try {
                return Base64.getEncoder().encodeToString(compressToByte(str));
            } catch (Exception var2) {
                throw new RuntimeException("GZipUtil.compressData error", var2);
            }
        }
    }


    /**
     * 将字符串进行gzip压缩（返回字节数组）
     *
     * @param str
     * @return byte[]
     */
    public static byte[] compressToByte(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new MyException(Code.GZIP_COMPRESS_NUll);
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try(GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                gzip.write(str.getBytes(StandardCharsets.UTF_8));
                gzip.finish();
                return out.toByteArray();
            } catch (IOException var4) {
                throw new MyException(Code.GZIP_COMPRESS_ERROR);
            }

        }
    }

    /**
     * 将字节数组进行gzip解压
     *
     * @param bytes
     * @return String
     */
    public static String uncompressToString(byte[] bytes) {
        if (null != bytes && bytes.length != 0) {
            return uncompressToString( new ByteArrayInputStream(bytes));
        } else {
            throw new MyException(Code.GZIP_UNCOMPRESS_NUll);
        }
    }

    /**
     * 将输入流进行gzip解压
     *
     * @param inputStream
     * @return String
     */
    public static String uncompressToString(InputStream inputStream) {
        if (inputStream == null) {
            throw new MyException(Code.GZIP_UNCOMPRESS_NUll);
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                GZIPInputStream gunzip = new GZIPInputStream(inputStream);
                byte[] buffer = new byte[256];
                int n;
                while((n = gunzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                gunzip.close();
                return out.toString("UTF-8");
            } catch (IOException var5) {
                throw new MyException(Code.GZIP_UNCOMPRESS_ERROR);
            }
        }
    }


}
