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
	 * 应用ID
	 */
	private static final String APPID = "应用ID";
	/**
	 * 应用密钥
	 */
	private static final String APPSECRET = "应用密钥";

	/**
	 * 请求的url地址
	 */
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?";

	private static final String ACCESS_TOKEN_URL_PARAMS = "grant_type=client_credential&appid="
			+ APPID + "&secret=" + APPSECRET;

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * get请求
	 * 
	 * @return JSON对象
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
	 * post请求
	 * 
	 * @return JSON对象
	 */
	private static JSONObject doPost(String url, String outStr) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		JSONObject obj = null;
		try {
			// 发出HTTP request
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
	 * 获取AccessToken
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
	 * 文件上传
	 * 
	 * @param filePath
	 *            文件路径 调用接口凭证
	 * @param accessToken
	 *            accessToken
	 * @param type
	 *            媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @return 媒体文件上传后，获取时的唯一标识
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
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace(
				"TYPE", type);
		System.out.println(url);
		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
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

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
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
	 * 创建菜单
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
		btn31.setName("扫码");
		btn31.setType("scancode_push");
		btn31.setKey("31");

		ClickButton btn32 = new ClickButton();
		btn32.setName("地理位置选择器");
		btn32.setType("location_select");
		btn32.setKey("32");

		Button btn = new Button();
		btn.setName("菜单");
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
