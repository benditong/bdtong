package com.zykj.benditong.model;

import java.io.Serializable;

public class AdsImages implements Serializable{

	/**
	 * 首页轮播图
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String imgsrc;
	private String url;
	
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
	
}
