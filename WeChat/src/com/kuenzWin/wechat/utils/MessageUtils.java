package com.kuenzWin.wechat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.kuenzWin.wechat.domin.AccessToken;
import com.kuenzWin.wechat.domin.BaseMessage;
import com.kuenzWin.wechat.domin.Image;
import com.kuenzWin.wechat.domin.ImageMessage;
import com.kuenzWin.wechat.domin.Music;
import com.kuenzWin.wechat.domin.MusicMessage;
import com.kuenzWin.wechat.domin.News;
import com.kuenzWin.wechat.domin.NewsMessage;
import com.kuenzWin.wechat.domin.TextMessage;
import com.thoughtworks.xstream.XStream;

/**
 * ��Ϣ�����࣬����XMLת����Map�������
 * 
 * @date 2015-8-1
 */
public class MessageUtils {

	/**
	 * �ı���Ϣ����
	 */
	public static final String MESSAGE_TEXT = "text";
	/**
	 * ͼƬ��Ϣ����
	 */
	public static final String MESSAGE_IMAGE = "image";
	/**
	 * ͼ����Ϣ����
	 */
	public static final String MESSAGE_NEWS = "news";
	/**
	 * ������Ϣ����
	 */
	public static final String MESSAGE_VOICE = "voice";
	/**
	 * ��Ƶ��Ϣ����
	 */
	public static final String MESSAGE_VIDEO = "video";
	/**
	 * ������Ϣ����
	 */
	public static final String MESSAGE_MUSIC = "music";
	/**
	 * ����Ƶ��Ϣ����
	 */
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";
	/**
	 * ����λ����Ϣ����
	 */
	public static final String MESSAGE_LOCATION = "location";
	/**
	 * ������Ϣ����
	 */
	public static final String MESSAGE_LINK = "link";
	/**
	 * ��ע/ȡ����ע�¼�
	 */
	public static final String MESSAGE_EVENT = "event";
	/**
	 * ɨ���������ά���¼�,�û�δ��עʱ�����й�ע����¼�����
	 */
	public static final String MESSAGE_EVENT__SUBSCRIBE = "subscribe";
	/**
	 * ɨ���������ά���¼�,�û��ѹ�עʱ���¼�����
	 */
	public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";
	/**
	 * �ϱ�����λ���¼�
	 */
	public static final String MESSAGE_EVENT_LOCATION = "LOCATION";

	/**
	 * ����˵���ȡ��Ϣʱ���¼�����
	 */
	public static final String MESSAGE_CLICK = "CLICK";
	/**
	 * ����˵���ת����ʱ���¼�����
	 */
	public static final String MESSAGE_VIEW = "VIEW";
	/**
	 * ɨ���¼�����
	 */
	public static final String MESSAGE_SCANCODE = "scancode_push";

	/**
	 * XMLת����Map
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		List<Element> list = root.elements();

		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}

		ins.close();
		return map;
	}

	/**
	 * �ı���Ϣת����xml
	 * 
	 * @param msg
	 *            Ҫת����xml���ı�bean
	 * @return
	 */
	private static String textMessageToXml(TextMessage msg) {
		XStream stream = new XStream();
		// �Ѹ��ڵ��滻��xml
		stream.alias("xml", TextMessage.class);
		return stream.toXML(msg);
	}

	public static String getMenuText() {
		System.out.println("getMenu");
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ��עxx��ͼ����Ϣ���ı���Ϣ��ͼƬ��Ϣ��������Ϣ���밴�˵���ʾ���в�����\n\n");
		sb.append("1��ͼ����Ϣ\n");
		sb.append("2���ı���Ϣ\n");
		sb.append("3��ͼƬ��Ϣ\n");
		sb.append("4��������Ϣ\n");
		sb.append("�ظ�?�����ྪϲ���㶮��~~~");
		return sb.toString();
	}

	public static String getFirstMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("������");
		return sb.toString();
	}

	public static String getSecondtMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("������");
		return sb.toString();
	}

	public static String getThirdMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��Է�");
		return sb.toString();
	}

	public static String getForthMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("������");
		return sb.toString();
	}

	public static String getFifthMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n��ϲ~~\n");
		return sb.toString();
	}

	/**
	 * ������Ϣ�Ŀ�����΢�źš� ���ͷ��ʺţ�һ��OpenID��������ʱ�䣨����Ϊ��ǰʱ�䣩
	 * 
	 * @param base
	 *            ��Ϣ
	 * @param toUserName
	 *            ������΢�ź�
	 * @param fromUserName
	 *            ���ͷ��ʺţ�һ��OpenID��
	 */
	private static void setFromAndTo(BaseMessage base, String toUserName,
			String fromUserName) {
		base.setFromUserName(toUserName);
		base.setToUserName(fromUserName);
		base.setCreateTime(new Date().getTime());
	}

	/**
	 * �����ı���Ϣ�ַ���
	 * 
	 * @param toUserName
	 *            ������΢�ź�
	 * @param fromUserName
	 *            ���ͷ��ʺţ�һ��OpenID��
	 * @param type
	 *            ��Ϣ����
	 * @param content
	 *            ��Ϣ����
	 * @return �ѽ�TextMessage����ת����xml��ʽ���ַ���
	 */
	public static String createTextMessage(String toUserName, String fromUserName,
			String content) {
		TextMessage textMsg = new TextMessage();
		setFromAndTo(textMsg, toUserName, fromUserName);
		textMsg.setMsgType(MessageUtils.MESSAGE_TEXT);
		textMsg.setContent(content);
		String msg = MessageUtils.textMessageToXml(textMsg);
		return msg;
	}

	/**
	 * ͼ����Ϣת����xml
	 * 
	 * @param msg
	 *            Ҫת����xml���ı�bean
	 * @return
	 */
	private static String newsMessageToXml(NewsMessage msg) {
		XStream stream = new XStream();
		// �Ѹ��ڵ��滻��xml
		stream.alias("xml", NewsMessage.class);
		stream.alias("item", News.class);
		return stream.toXML(msg);
	}

	/**
	 * ����ͼ����Ϣ
	 * 
	 * @param toUserName
	 *            ������΢�ź�
	 * @param fromUserName
	 *            ���ͷ��ʺţ�һ��OpenID��
	 * @param title
	 *            ͼ����Ϣ����
	 * @param picUrl
	 *            ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ360*200��Сͼ200*200
	 * @param description
	 *            ͼ����Ϣ����
	 * @param url
	 *            ���ͼ����Ϣ��ת����
	 * @return ͼ����Ϣ��XML��ʽ���ַ���
	 */
	public static String createNewsMessage(String toUserName, String fromUserName,
			String title, String picUrl, String description, String url) {

		List<News> list = new ArrayList<News>();
		News news = new News();
		news.setTitle(title);
		news.setPicUrl(picUrl);
		news.setDescription(description);
		news.setUrl(url);
		list.add(news);

		NewsMessage newsMsg = new NewsMessage();
		setFromAndTo(newsMsg, toUserName, fromUserName);
		newsMsg.setMsgType(MESSAGE_NEWS);
		newsMsg.setArticles(list);
		newsMsg.setArticleCount(list.size());

		return MessageUtils.newsMessageToXml(newsMsg);
	}

	/**
	 * ͨ���زĹ���ӿ��ϴ�ͼƬ�ļ����õ���id��
	 * 
	 * @param filePath
	 *            ͼƬ�ļ�·��
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private static String upload(String filePath) throws ParseException,
			IOException {

		AccessToken token = AccessTokenUtils.getAccessToken();
		System.out.println(token.getToken());
		String MediaId = null;
		try {
			MediaId = WeChatUtils.upload(filePath, token.getToken(), "image");
			System.out.println("�ϴ��ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ϴ�ʧ��");
		}
		return MediaId;
	}

	// <xml>
	// <ToUserName><![CDATA[toUser]]></ToUserName>
	// <FromUserName><![CDATA[fromUser]]></FromUserName>
	// <CreateTime>12345678</CreateTime>
	// <MsgType><![CDATA[image]]></MsgType>
	// <Image>
	// <MediaId><![CDATA[media_id]]></MediaId>
	// </Image>
	// </xml>
	/**
	 * ����ͼƬ��Ϣ
	 * 
	 * @param toUserName
	 *            ������΢�ź�
	 * @param fromUserName
	 *            ���ͷ��ʺţ�һ��OpenID��
	 * @param filePath
	 *            Ҫ�ϴ���ͼƬ�ı���·��
	 * @return ͼƬ��Ϣ��XML��ʽ���ַ���
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String createImageMessage(String toUserName,
			String fromUserName, String filePath) throws ParseException,
			IOException {
		ImageMessage imageMsg = new ImageMessage();
		setFromAndTo(imageMsg, toUserName, fromUserName);
		imageMsg.setMsgType(MESSAGE_IMAGE);

		Image image = new Image();
		image.setMediaId(upload(filePath));

		imageMsg.setImage(image);

		return imageMessageToXml(imageMsg);
	}

	/**
	 * ͼƬ��Ϣת����xml
	 * 
	 * @param msg
	 *            Ҫת����xml���ı�bean
	 * @return
	 */
	private static String imageMessageToXml(ImageMessage msg) {
		XStream stream = new XStream();
		// �Ѹ��ڵ��滻��xml
		stream.alias("xml", msg.getClass());
		return stream.toXML(msg);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param toUserName
	 *            ������΢�ź�
	 * @param fromUserName
	 *            ���ͷ��ʺţ�һ��OpenID��
	 * @param title
	 *            ���ֱ���
	 * @param description
	 *            ��������
	 * @param musicUrl
	 *            ��������
	 * @param hQMusicUrl
	 *            �������������ӣ�WIFI��������ʹ�ø����Ӳ�������
	 * @param filePath
	 *            Ҫ�ϴ��Ļ�ȡ����ͼ�ı���·��,�ϴ���·��·��֮���ȡ����ͼ��ý��id��ͨ���زĹ���ӿ��ϴ���ý���ļ����õ���id��
	 * @return ������Ϣ��XML��ʽ���ַ���
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String createMusicMessage(String toUserName,
			String fromUserName, String title, String description,
			String musicUrl, String hQMusicUrl, String filePath)
			throws ParseException, IOException {
		MusicMessage musicMsg = new MusicMessage();
		setFromAndTo(musicMsg, toUserName, fromUserName);
		musicMsg.setMsgType(MESSAGE_MUSIC);

		Music music = new Music();
		music.setTitle(title);
		music.setDescription(description);
		music.setMusicUrl(musicUrl);
		music.setHQMusicUrl(hQMusicUrl);
		music.setThumbMediaId(upload(filePath));

		musicMsg.setMusic(music);

		return musicMessageToXml(musicMsg);
	}

	/**
	 * ������Ϣת����xml
	 * 
	 * @param msg
	 *            Ҫת����xml���ı�bean
	 * @return
	 */
	private static String musicMessageToXml(MusicMessage msg) {
		XStream stream = new XStream();
		// �Ѹ��ڵ��滻��xml
		stream.alias("xml", msg.getClass());
		return stream.toXML(msg);
	}

}
