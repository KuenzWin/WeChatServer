package com.kuenzWin.wechat.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.kuenzWin.wechat.domin.AccessToken;
import com.kuenzWin.wechat.menu.Button;
import com.kuenzWin.wechat.menu.ClickButton;
import com.kuenzWin.wechat.menu.Menu;
import com.kuenzWin.wechat.menu.ViewButton;

public class WeChatUtils {

	/**
	 * Ӧ��ID
	 */
	private static final String APPID = "Ӧ��ID";
	/**
	 * Ӧ����Կ
	 */
	private static final String APPSECRET = "Ӧ����Կ";

	/**
	 * �����url��ַ
	 */
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?";

	private static final String ACCESS_TOKEN_URL_PARAMS = "grant_type=client_credential&appid="
			+ APPID + "&secret=" + APPSECRET;

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * get����
	 * 
	 * @return JSON����
	 */
	private static JSONObject doGet(String url) {
		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(url);
		JSONObject obj = new JSONObject();

		try {
			HttpResponse response = client.execute(get);
			obj = getJSONObject(obj, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * post����
	 * 
	 * @return JSON����
	 */
	private static JSONObject doPost(String url, String outStr) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		JSONObject obj = null;
		try {
			// ����HTTP request
			post.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = client.execute(post);
			obj = getJSONObject(obj, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;

	}

	private static JSONObject getJSONObject(JSONObject obj,
			HttpResponse response) throws IOException, ParseException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String objStr = EntityUtils.toString(entity, "UTF-8");
			obj = JSONObject.fromObject(objStr);
		}
		return obj;
	}

	/**
	 * ��ȡAccessToken
	 * 
	 * @param method
	 * @return
	 */
	public static AccessToken getToken(String method) {
		AccessToken token = new AccessToken();
		JSONObject obj = null;
		if (method.equals("get")) {
			String url = ACCESS_TOKEN_URL;
			url = url + ACCESS_TOKEN_URL_PARAMS;
			obj = doGet(url);
		} else {
			obj = doPost(ACCESS_TOKEN_URL, ACCESS_TOKEN_URL_PARAMS);
		}
		if (obj != null) {
			token.setToken(obj.getString("access_token"));
			token.setExpiresIn(obj.getInt("expires_in"));
		}
		return token;
	}

	/**
	 * �ļ��ϴ�
	 * 
	 * @param filePath
	 *            �ļ�·�� ���ýӿ�ƾ֤
	 * @param accessToken
	 *            accessToken
	 * @param type
	 *            ý���ļ����ͣ��ֱ���ͼƬ��image����������voice������Ƶ��video��������ͼ��thumb��
	 * @return ý���ļ��ϴ��󣬻�ȡʱ��Ψһ��ʶ
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken, String type)
			throws IOException, NoSuchAlgorithmException,
			NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace(
				"TYPE", type);
		System.out.println(url);
		URL urlObj = new URL(url);
		// ����
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// ��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// ���ñ߽�
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// ��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// �����ͷ
		out.write(head);

		// �ļ����Ĳ���
		// ���ļ������ļ��ķ�ʽ ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// ��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// ����BufferedReader����������ȡURL����Ӧ
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);

		System.out.println(jsonObj);
		String typeName = "media_id";
		if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}

	/**
	 * �����˵�
	 * 
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();

		ClickButton btn11 = new ClickButton();
		btn11.setName("click");
		btn11.setType("click");
		btn11.setKey("11");

		ViewButton btn21 = new ViewButton();
		btn21.setName("view");
		btn21.setType("view");
		btn21.setUrl("http://www.sina.com.cn");

		ClickButton btn31 = new ClickButton();
		btn31.setName("ɨ��");
		btn31.setType("scancode_push");
		btn31.setKey("31");

		ClickButton btn32 = new ClickButton();
		btn32.setName("����λ��ѡ����");
		btn32.setType("location_select");
		btn32.setKey("32");

		Button btn = new Button();
		btn.setName("�˵�");
		btn.setSub_button(new Button[] { btn31, btn32 });

		menu.setButton(new Button[] { btn11, btn21, btn });

		return menu;

	}

	public static int createMenu(String menu) {
		// https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
		String token = AccessTokenUtils.getAccessToken().getToken();
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeChatUtils.doPost(url, menu);
		int result = -1;
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	public static JSONObject queryMenu() throws ParseException, IOException {
		String token = AccessTokenUtils.getAccessToken().getToken();
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGet(url);
		return jsonObject;
	}

	public static int deleteMenu() throws ParseException, IOException {
		String token = AccessTokenUtils.getAccessToken().getToken();
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGet(url);
		int result = 0;
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

}
