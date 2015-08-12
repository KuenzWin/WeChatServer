package com.kuenzWin.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kuenzWin.wechat.utils.CheckUtils;
import com.kuenzWin.wechat.utils.MessageUtils;

public class WeChatServlet extends HttpServlet {

	/**
	 * 上传到素材的文件的文件路径
	 */
	private String filePath = "图片路径";;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter pw = response.getWriter();
		if (CheckUtils.check(signature, timestamp, nonce)) {
			pw.write(echostr);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, String> map;
		PrintWriter pw = response.getWriter();
		try {
			map = MessageUtils.xmlToMap(request);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");

			String msg = null;
			if (msgType.equals(MessageUtils.MESSAGE_TEXT)) {
				// 如果是文本消息，就应该进行回复
				if (content.equals("1")) {
					msg = MessageUtils
							.createNewsMessage(
									toUserName,
									fromUserName,
									"xx介绍",
									"图片url",
									"图片文字描述", "图片点击链接");
				} else if (content.equals("2")) {
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, MessageUtils.getSecondtMenuText());
				} else if (content.equals("3")) {
					msg = MessageUtils.createImageMessage(toUserName,
							fromUserName, filePath);
				} else if (content.equals("4")) {
					msg = MessageUtils
							.createMusicMessage(
									toUserName,
									fromUserName,
									"音乐标题",
									"音乐描述",
									"音乐url",
									"高清音乐url",
									filePath);
				} else if (content.equals("？") || content.equals("?")) {
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, MessageUtils.getFifthMenuText());
				} else {
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, "测试,您发送的消息是:" + content);
				}

			} else if (msgType.equals(MessageUtils.MESSAGE_EVENT)) {
				String event = map.get("Event");

				if (event.equals(MessageUtils.MESSAGE_EVENT__SUBSCRIBE)) {
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, MessageUtils.getMenuText());
				} else if (event.equals(MessageUtils.MESSAGE_CLICK)) {
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, MessageUtils.getMenuText());
				} else if (event.equals(MessageUtils.MESSAGE_VIEW)) {
					// 事件KEY值，设置的跳转URL
					String url = map.get("EventKey");
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, url);
				} else if (event.equals(msgType
						.equals(MessageUtils.MESSAGE_SCANCODE))) {
					String key = map.get("EventKey");
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, key);
				}
			} else if (msgType.equals(MessageUtils.MESSAGE_LOCATION)) {
				String label = map.get("Label");
				msg = MessageUtils.createTextMessage(toUserName, fromUserName,
						label);
			}
			System.out.println(msg);
			pw.print(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
