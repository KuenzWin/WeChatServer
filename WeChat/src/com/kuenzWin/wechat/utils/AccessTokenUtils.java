package com.kuenzWin.wechat.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.kuenzWin.wechat.domin.AccessToken;

/**
 * AccessToken获取工具类
 * 因为token的获取次数有限(2000次)，不可能无限制获取，所以将token信息写入一个文件中，获取token时读取这个文件
 * ，若文件中的token信息未失效， 即token的有效期未过，则使用旧的token，若已过，则获取新的token，并将其id和有效期写入文件中
 * 
 * @author 温坤哲
 * @date 2015-8-2
 */
public class AccessTokenUtils {

	/**
	 * 获取token
	 * 
	 * @param path
	 *            存储token信息文件的文件路径
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

			// 读取access_token.data文件内容
			int len = -1;
			byte[] data = new byte[1024];
			String msg = "";
			while ((len = fis.read(data)) != -1) {
				msg += new String(data, 0, len);
			}

			long created = 0;
			int expires_in = 0;
			System.out.println("msg:" + msg);
			// 如果文件中是有内容的话
			if (!msg.equals("")) {
				String[] msgs = msg.split(",");
				// 获取创建时间
				created = Long.parseLong(msgs[2]);
				// 获取token失效时间
				expires_in = Integer.parseInt(msgs[1]);
				// 若现在的时间与token创建时间相隔小于失效时间，则证明该token任然有效，直接组装该token对象后返回
				if (System.currentTimeMillis() - created < expires_in * 1000) {
					token = new AccessToken();
					token.setToken(msgs[0]);
					token.setExpiresIn(expires_in);
					System.out.println("old token create by "
							+ new Date(created).toLocaleString());
					return token;
				}
			}

			// 文件中的token信息失效，获取新的token
			token = WeChatUtils.getToken("get");
			created = System.currentTimeMillis();
			System.out.println("new token create by "
					+ new Date(created).toLocaleString());

			// 将新的token信息写入文件中
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
