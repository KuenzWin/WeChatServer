package com.kuenzWin.wechat.utils;

import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUtils {

	private static final String TOKEN = "��΢�Ź��ں����ﶨ���Token";

	/**
	 * ����/У���������£� 1. ��token��timestamp��nonce�������������ֵ������� 2.
	 * �����������ַ���ƴ�ӳ�һ���ַ�������sha1���� 3. �����߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
	 * 
	 * @return
	 */
	public static boolean check(String signature, String timestamp, String nonce) {
		String[] array = { TOKEN, timestamp, nonce };
		// 1. ��token��timestamp��nonce�������������ֵ�������
		Arrays.sort(array);
		// 2. �����������ַ���ƴ�ӳ�һ���ַ���
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
		}
		//3.shar1����
		String shar1 = getSha1(sb.toString());
		//4.���ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
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
