package com.kuenzWin.wechat.utils;

import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUtils {

	private static final String TOKEN = "在微信公众号那里定义的Token";

	/**
	 * 加密/校验流程如下： 1. 将token、timestamp、nonce三个参数进行字典序排序 2.
	 * 将三个参数字符串拼接成一个字符串进行sha1加密 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * 
	 * @return
	 */
	public static boolean check(String signature, String timestamp, String nonce) {
		String[] array = { TOKEN, timestamp, nonce };
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(array);
		// 2. 将三个参数字符串拼接成一个字符串
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
		}
		//3.shar1加密
		String shar1 = getSha1(sb.toString());
		//4.加密后的字符串可与signature对比，标识该请求来源于微信
		return shar1.equals(signature);
	}

	public static String getSha1(String str) {
		if (null == str || 0 == str.length())
			return null;
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
