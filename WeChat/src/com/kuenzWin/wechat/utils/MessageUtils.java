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
 * 消息工具类，包括XML转换成Map工具类和
 * 
 * @date 2015-8-1
 */
public class MessageUtils {

	/**
	 * 文本消息类型
	 */
	public static final String MESSAGE_TEXT = "text";
	/**
	 * 图片消息类型
	 */
	public static final String MESSAGE_IMAGE = "image";
	/**
	 * 图文消息类型
	 */
	public static final String MESSAGE_NEWS = "news";
	/**
	 * 语音消息类型
	 */
	public static final String MESSAGE_VOICE = "voice";
	/**
	 * 视频消息类型
	 */
	public static final String MESSAGE_VIDEO = "video";
	/**
	 * 音乐消息类型
	 */
	public static final String MESSAGE_MUSIC = "music";
	/**
	 * 短视频消息类型
	 */
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";
	/**
	 * 地理位置消息类型
	 */
	public static final String MESSAGE_LOCATION = "location";
	/**
	 * 链接消息类型
	 */
	public static final String MESSAGE_LINK = "link";
	/**
	 * 关注/取消关注事件
	 */
	public static final String MESSAGE_EVENT = "event";
	/**
	 * 扫描带参数二维码事件,用户未关注时，进行关注后的事件推送
	 */
	public static final String MESSAGE_EVENT__SUBSCRIBE = "subscribe";
	/**
	 * 扫描带参数二维码事件,用户已关注时的事件推送
	 */
	public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";
	/**
	 * 上报地理位置事件
	 */
	public static final String MESSAGE_EVENT_LOCATION = "LOCATION";

	/**
	 * 点击菜单拉取消息时的事件推送
	 */
	public static final String MESSAGE_CLICK = "CLICK";
	/**
	 * 点击菜单跳转链接时的事件推送
	 */
	public static final String MESSAGE_VIEW = "VIEW";
	/**
	 * 扫码事件推送
	 */
	public static final String MESSAGE_SCANCODE = "scancode_push";

	/**
	 * XML转换成Map
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
	 * 文本消息转换成xml
	 * 
	 * @param msg
	 *            要转换成xml的文本bean
	 * @return
	 */
	private static String textMessageToXml(TextMessage msg) {
		XStream stream = new XStream();
		// 把根节点替换成xml
		stream.alias("xml", TextMessage.class);
		return stream.toXML(msg);
	}

	public static String getMenuText() {
		System.out.println("getMenu");
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注xx，图文消息、文本消息、图片消息、音乐消息。请按菜单提示进行操作：\n\n");
		sb.append("1、图文消息\n");
		sb.append("2、文本消息\n");
		sb.append("3、图片消息\n");
		sb.append("4、音乐消息\n");
		sb.append("回复?，更多惊喜，你懂的~~~");
		return sb.toString();
	}

	public static String getFirstMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("聊人生");
		return sb.toString();
	}

	public static String getSecondtMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("聊理想");
		return sb.toString();
	}

	public static String getThirdMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("请吃饭");
		return sb.toString();
	}

	public static String getForthMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("吃辣条");
		return sb.toString();
	}

	public static String getFifthMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n惊喜~~\n");
		return sb.toString();
	}

	/**
	 * 设置消息的开发者微信号、 发送方帐号（一个OpenID）、创建时间（设置为当前时间）
	 * 
	 * @param base
	 *            消息
	 * @param toUserName
	 *            开发者微信号
	 * @param fromUserName
	 *            发送方帐号（一个OpenID）
	 */
	private static void setFromAndTo(BaseMessage base, String toUserName,
			String fromUserName) {
		base.setFromUserName(toUserName);
		base.setToUserName(fromUserName);
		base.setCreateTime(new Date().getTime());
	}

	/**
	 * 构造文本消息字符串
	 * 
	 * @param toUserName
	 *            开发者微信号
	 * @param fromUserName
	 *            发送方帐号（一个OpenID）
	 * @param type
	 *            消息类型
	 * @param content
	 *            消息内容
	 * @return 已将TextMessage对象转换成xml格式的字符串
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
	 * 图文消息转换成xml
	 * 
	 * @param msg
	 *            要转换成xml的文本bean
	 * @return
	 */
	private static String newsMessageToXml(NewsMessage msg) {
		XStream stream = new XStream();
		// 把根节点替换成xml
		stream.alias("xml", NewsMessage.class);
		stream.alias("item", News.class);
		return stream.toXML(msg);
	}

	/**
	 * 创建图文信息
	 * 
	 * @param toUserName
	 *            开发者微信号
	 * @param fromUserName
	 *            发送方帐号（一个OpenID）
	 * @param title
	 *            图文消息标题
	 * @param picUrl
	 *            图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 * @param description
	 *            图文消息描述
	 * @param url
	 *            点击图文消息跳转链接
	 * @return 图文消息的XML格式的字符串
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
	 * 通过素材管理接口上传图片文件，得到的id。
	 * 
	 * @param filePath
	 *            图片文件路径
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
			System.out.println("上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传失败");
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
	 * 创建图片消息
	 * 
	 * @param toUserName
	 *            开发者微信号
	 * @param fromUserName
	 *            发送方帐号（一个OpenID）
	 * @param filePath
	 *            要上传的图片的本地路径
	 * @return 图片消息的XML格式的字符串
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
	 * 图片消息转换成xml
	 * 
	 * @param msg
	 *            要转换成xml的文本bean
	 * @return
	 */
	private static String imageMessageToXml(ImageMessage msg) {
		XStream stream = new XStream();
		// 把根节点替换成xml
		stream.alias("xml", msg.getClass());
		return stream.toXML(msg);
	}

	/**
	 * 创建音乐消息
	 * 
	 * @param toUserName
	 *            开发者微信号
	 * @param fromUserName
	 *            发送方帐号（一个OpenID）
	 * @param title
	 *            音乐标题
	 * @param description
	 *            音乐描述
	 * @param musicUrl
	 *            音乐链接
	 * @param hQMusicUrl
	 *            高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 * @param filePath
	 *            要上传的获取缩略图的本地路径,上传该路径路径之后获取缩略图的媒体id（通过素材管理接口上传多媒体文件，得到的id）
	 * @return 音乐消息的XML格式的字符串
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
	 * 音乐消息转换成xml
	 * 
	 * @param msg
	 *            要转换成xml的文本bean
	 * @return
	 */
	private static String musicMessageToXml(MusicMessage msg) {
		XStream stream = new XStream();
		// 把根节点替换成xml
		stream.alias("xml", msg.getClass());
		return stream.toXML(msg);
	}

}
