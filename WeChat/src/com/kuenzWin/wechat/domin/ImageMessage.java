package com.kuenzWin.wechat.domin;

/**
 * Í¼Æ¬ÏûÏ¢bean
 * 
 * @date 2015-8-2
 */
public class ImageMessage extends BaseMessage {

	// <xml>
	// <ToUserName><![CDATA[toUser]]></ToUserName>
	// <FromUserName><![CDATA[fromUser]]></FromUserName>
	// <CreateTime>12345678</CreateTime>
	// <MsgType><![CDATA[image]]></MsgType>
	// <Image>
	// <MediaId><![CDATA[media_id]]></MediaId>
	// </Image>
	// </xml>
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image Image) {
		this.Image = Image;
	}

}
