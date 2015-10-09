package com.zykj.benditong.model;

import java.io.Serializable;

public class Demand implements Serializable{
	
	/**
	 * csh 2015-09-30
	 * 供应、求购
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;//标题
	private String type;//0供求 1求购
	private String imgsrc;//图片
	private String contacts;//联系人
	private String mobile;//联系电话
	private String intro;//信息描述
	private String addtime;
	
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}

}
