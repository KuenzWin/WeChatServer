package com.kuenzWin.wechat.menu;

/**
 * һ���˵����飬����ӦΪ1~3��
 * 
 * @date 2015-8-3
 */
public class Button {

	// {
	// "button":[
	// {
	// "type":"click",
	// "name":"���ո���",
	// "key":"V1001_TODAY_MUSIC"
	// },
	// {
	// "name":"�˵�",
	// "sub_button":[
	// {
	// "type":"view",
	// "name":"����",
	// "url":"http://www.soso.com/"
	// },
	// {
	// "type":"view",
	// "name":"��Ƶ",
	// "url":"http://v.qq.com/"
	// },
	// {
	// "type":"click",
	// "name":"��һ������",
	// "key":"V1001_GOOD"
	// }]
	// }]
	// }

	/**
	 * �˵�����Ӧ��������
	 */
	protected String type;
	/**
	 * �˵����⣬������16���ֽڣ��Ӳ˵�������40���ֽ�
	 */
	protected String name;
	/**
	 * �����˵����飬����ӦΪ1~5��
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
