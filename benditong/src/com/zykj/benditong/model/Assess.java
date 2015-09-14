package com.zykj.benditong.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Assess implements Serializable{
	
	/**
	 * 评价
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String content;
	private String golds;
	private String addtime;
	private String name;
	private String avatar;
	private List<Map<String, String>> imglist;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGolds() {
		return golds;
	}
	public void setGolds(String golds) {
		this.golds = golds;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<Map<String, String>> getImglist() {
		return imglist;
	}
	public void setImglist(List<Map<String, String>> imglist) {
		this.imglist = imglist;
	}
}