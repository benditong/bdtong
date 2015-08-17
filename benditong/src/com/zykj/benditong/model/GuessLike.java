package com.zykj.benditong.model;

import java.io.Serializable;

public class GuessLike implements Serializable{
	
	/**
	 * 猜你喜欢
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String imgsrc;
	private String price;
	private String intro;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
}
