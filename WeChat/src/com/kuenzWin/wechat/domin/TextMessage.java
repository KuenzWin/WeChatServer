package com.kuenzWin.wechat.domin;

/**
 * �ı���Ϣbean
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
	// ���� ����
	// ToUserName ������΢�ź�
	// FromUserName ���ͷ��ʺţ�һ��OpenID��
	// CreateTime ��Ϣ����ʱ�� �����ͣ�
	// MsgType text
	// Content �ı���Ϣ����
	// MsgId ��Ϣid��64λ����
	//
	/**
	 * �ı���Ϣ����
	 */
	private String Content;
	/**
	 * ��Ϣid��64λ����
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
