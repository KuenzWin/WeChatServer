package com.kuenzWin.wechat.domin;

/**
 * ��Ϣ����
 * 
 * @date 2015-8-2
 */
public class BaseMessage {

	/**
	 * ������΢�ź�
	 */
	protected String ToUserName;
	/**
	 * ���ͷ��ʺţ�һ��OpenID��
	 */
	protected String FromUserName;
	/**
	 * ��Ϣ����ʱ�� �����ͣ�
	 */
	protected long CreateTime;
	/**
	 * ��Ϣ����
	 */
	protected String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
