package com.kuenzWin.wechat.domin;

/**
 * 文本消息bean
 * @date 2015-8-1
 */
public class TextMessage extends BaseMessage {

	// <xml>
	// <ToUserName><![CDATA[toUser]]></ToUserName>
	// <FromUserName><![CDATA[fromUser]]></FromUserName>
	// <CreateTime>1348831860</CreateTime>
	// <MsgType><![CDATA[text]]></MsgType>
	// <Content><![CDATA[this is a test]]></Content>
	// <MsgId>1234567890123456</MsgId>
	// </xml>
	// 参数 描述
	// ToUserName 开发者微信号
	// FromUserName 发送方帐号（一个OpenID）
	// CreateTime 消息创建时间 （整型）
	// MsgType text
	// Content 文本消息内容
	// MsgId 消息id，64位整型
	//
	/**
	 * 文本消息内容
	 */
	private String Content;
	/**
	 * 消息id，64位整型
	 */
	private long MsgId;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public long getMsgId() {
		return MsgId;
	}

	public void setMsgId(long msgId) {
		MsgId = msgId;
	}

}
