package com.kuenzWin.wechat.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.kuenzWin.wechat.domin.AccessToken;

/**
 * AccessToken��ȡ������
 * ��Ϊtoken�Ļ�ȡ��������(2000��)�������������ƻ�ȡ�����Խ�token��Ϣд��һ���ļ��У���ȡtokenʱ��ȡ����ļ�
 * �����ļ��е�token��ϢδʧЧ�� ��token����Ч��δ������ʹ�þɵ�token�����ѹ������ȡ�µ�token��������id����Ч��д���ļ���
 * 
 * @author ������
 * @date 2015-8-2
 */
public class AccessTokenUtils {

	/**
	 * ��ȡtoken
	 * 
	 * @param path
	 *            �洢token��Ϣ�ļ����ļ�·��
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken token = null;

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			String path = AccessTokenUtils.class.getClassLoader()
					.getResource("com/kuenzWin/wechat/utils/access_token.data")
					.toURI().getPath();
			System.out.println("path:" + path);
			fis = new FileInputStream(path);

			// ��ȡaccess_token.data�ļ�����
			int len = -1;
			byte[] data = new byte[1024];
			String msg = "";
			while ((len = fis.read(data)) != -1) {
				msg += new String(data, 0, len);
			}

			long created = 0;
			int expires_in = 0;
			System.out.println("msg:" + msg);
			// ����ļ����������ݵĻ�
			if (!msg.equals("")) {
				String[] msgs = msg.split(",");
				// ��ȡ����ʱ��
				created = Long.parseLong(msgs[2]);
				// ��ȡtokenʧЧʱ��
				expires_in = Integer.parseInt(msgs[1]);
				// �����ڵ�ʱ����token����ʱ�����С��ʧЧʱ�䣬��֤����token��Ȼ��Ч��ֱ����װ��token����󷵻�
				if (System.currentTimeMillis() - created < expires_in * 1000) {
					token = new AccessToken();
					token.setToken(msgs[0]);
					token.setExpiresIn(expires_in);
					System.out.println("old token create by "
							+ new Date(created).toLocaleString());
					return token;
				}
			}

			// �ļ��е�token��ϢʧЧ����ȡ�µ�token
			token = WeChatUtils.getToken("get");
			created = System.currentTimeMillis();
			System.out.println("new token create by "
					+ new Date(created).toLocaleString());

			// ���µ�token��Ϣд���ļ���
			fos = new FileOutputStream(path);
			msg = token.getToken() + "," + token.getExpiresIn() + "," + created;
			System.out.println("new");
			fos.write(msg.getBytes());
			fos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return token;
	}
}
