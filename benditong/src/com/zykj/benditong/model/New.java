package com.zykj.benditong.model;

import java.io.Serializable;

public class New implements Serializable{
	
	/**
	 * 新闻资讯
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String content;
	private String newId;
	private String price;
	private String imgUrl;
	
	public New(String title, String content, String price){
		this.title = title;
		this.content = content;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNewId() {
		return newId;
	}
	public void setNewId(String newId) {
		this.newId = newId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
