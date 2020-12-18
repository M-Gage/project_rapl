package com.qingting.middleware.util.zcaf;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author Ning Qing Qing
 * 
 * RC4 128bit 加密&解密算法，2016-01-29更新版本V2
 *
 */
public class RC4_128_V2 {
	
	private static final String RC4 = "RC4";
	private static final String UTF8 = "UTF-8";
	
	/**
	 * RC4加密明文（可能包含汉字），输出是经过Base64的；如果加密失败，返回值是null
	 * @param plainText
	 * @param rc4Key
	 * @return
	 */
	public static final String encode(final String plainText, final String rc4Key )
	{
		try
		{
			final Cipher c1 = Cipher.getInstance(RC4);
			c1.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
			return new String( Base64.encodeBase64(c1.doFinal(plainText.getBytes(UTF8))) );
		}
		catch(final Throwable t)
		{
			t.printStackTrace();
			return null;
		}
	}
	
	/**
	 * RC4从密文解密为明文，输入是经过Base64的；如果解密失败，返回值是null
	 * @param encodedText
	 * @param rc4Key
	 * @return
	 */
	public static final String decode(final String encodedText, final String rc4Key )
	{
		try
		{
			final Cipher c1 = Cipher.getInstance(RC4);
			c1.init(Cipher.DECRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
			return new String( c1.doFinal(Base64.decodeBase64(encodedText.getBytes())), UTF8 );
		}
		catch( final Throwable t )
		{
			t.printStackTrace();
			return null;
		}		
	}
}
