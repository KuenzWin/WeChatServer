package com.kuenzWin.wechat.domin;

public class MusicMessage extends BaseMessage {

	// <xml>
	// <ToUserName><![CDATA[toUser]]></ToUserName>
	// <FromUserName><![CDATA[fromUser]]></FromUserName>
	// <CreateTime>12345678</CreateTime>
	// <MsgType><![CDATA[music]]></MsgType>
	// <Music>
	// <Title><![CDATA[TITLE]]></Title>
	// <Description><![CDATA[DESCRIPTION]]></Description>
	// <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
	// <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
	// <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
	// </Music>
	// </xml>

	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}

}
