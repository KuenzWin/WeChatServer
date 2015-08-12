package com.kuenzWin.wechat.menu;

/**
 * 一级菜单数组，个数应为1~3个
 * 
 * @date 2015-8-3
 */
public class Button {

	// {
	// "button":[
	// {
	// "type":"click",
	// "name":"今日歌曲",
	// "key":"V1001_TODAY_MUSIC"
	// },
	// {
	// "name":"菜单",
	// "sub_button":[
	// {
	// "type":"view",
	// "name":"搜索",
	// "url":"http://www.soso.com/"
	// },
	// {
	// "type":"view",
	// "name":"视频",
	// "url":"http://v.qq.com/"
	// },
	// {
	// "type":"click",
	// "name":"赞一下我们",
	// "key":"V1001_GOOD"
	// }]
	// }]
	// }

	/**
	 * 菜单的响应动作类型
	 */
	protected String type;
	/**
	 * 菜单标题，不超过16个字节，子菜单不超过40个字节
	 */
	protected String name;
	/**
	 * 二级菜单数组，个数应为1~5个
	 */
	protected Button[] sub_button;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

}
