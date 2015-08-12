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
	 * �ϴ����زĵ��ļ����ļ�·��
	 */
	private String filePath = "ͼƬ·��";;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
		String signature = request.getParameter("signature");
		// ʱ���
		String timestamp = request.getParameter("timestamp");
		// �����
		String nonce = request.getParameter("nonce");
		// ����ַ���
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
				// ������ı���Ϣ����Ӧ�ý��лظ�
				if (content.equals("1")) {
					msg = MessageUtils
							.createNewsMessage(
									toUserName,
									fromUserName,
									"xx����",
									"ͼƬurl",
									"ͼƬ��������", "ͼƬ�������");
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
									"���ֱ���",
									"��������",
									"����url",
									"��������url",
									filePath);
				} else if (content.equals("��") || content.equals("?")) {
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, MessageUtils.getFifthMenuText());
				} else {
					msg = MessageUtils.createTextMessage(toUserName,
							fromUserName, "����,�����͵���Ϣ��:" + content);
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
					// �¼�KEYֵ�����õ���תURL
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
