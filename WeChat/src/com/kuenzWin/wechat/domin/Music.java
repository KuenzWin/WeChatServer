package com.kuenzWin.wechat.domin;

public class Music {

	// <Music>
	// <Title><![CDATA[TITLE]]></Title>
	// <Description><![CDATA[DESCRIPTION]]></Description>
	// <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
	// <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
	// <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
	// </Music>

	/**
	 * ���ֱ���
	 */
	private String Title;
	/**
	 * ��������
	 */
	private String Description;
	/**
	 * ��������
	 */
	private String MusicUrl;
	/**
	 * �������������ӣ�WIFI��������ʹ�ø����Ӳ�������
	 */
	private String HQMusicUrl;
	/**
	 * ����ͼ��ý��id��ͨ���زĹ���ӿ��ϴ���ý���ļ����õ���id
	 */
	private String ThumbMediaId;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

}
