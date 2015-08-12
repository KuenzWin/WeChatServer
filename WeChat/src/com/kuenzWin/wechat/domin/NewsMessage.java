package com.kuenzWin.wechat.domin;

import java.util.List;

/**
 * 图文消息bean
 * 
 * @date 2015-8-2
 */
public class NewsMessage extends BaseMessage {

	// <xml>
	// <ToUserName><![CDATA[toUser]]></ToUserName>
	// <FromUserName><![CDATA[fromUser]]></FromUserName>
	// <CreateTime>12345678</CreateTime>
	// <MsgType><![CDATA[news]]></MsgType>
	// <ArticleCount>2</ArticleCount>
	// <Articles>
	// <item>
	// <Title><![CDATA[title1]]></Title>
	// <Description><![CDATA[description1]]></Description>
	// <PicUrl><![CDATA[picurl]]></PicUrl>
	// <Url><![CDATA[url]]></Url>
	// </item>
	// <item>
	// <Title><![CDATA[title]]></Title>
	// <Description><![CDATA[description]]></Description>
	// <PicUrl><![CDATA[picurl]]></PicUrl>
	// <Url><![CDATA[url]]></Url>
	// </item>
	// </Articles>
	// </xml>

	/**
	 * 图文消息个数，限制为10条以内
	 */
	private int ArticleCount;
	/**
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 */
	private List<News> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<News> getArticles() {
		return Articles;
	}

	public void setArticles(List<News> articles) {
		Articles = articles;
	}

}
