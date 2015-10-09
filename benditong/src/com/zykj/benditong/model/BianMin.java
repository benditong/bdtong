package com.zykj.benditong.model;

import java.io.Serializable;

public class BianMin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;// 信息编号
	private String type;// 信息类型
	private String title;// 信息标题
	private String imgsrc;// 信息LOGO
	private String url;// 信息链接地址
	private String addtime;// 信息发布日期

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

}
