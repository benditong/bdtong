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
	private String person;//联系人
	private String mobile;//联系电话
	private String construct;//信息描述
	
	/**
	 * @param title 标题
	 * @param person 联系人
	 * @param mobile 联系电话
	 * @param construct 信息描述
	 */
	public Demand(String title, String person, String mobile, String construct){
		this.title = title;
		this.person = person;
		this.mobile = mobile;
		this.construct = construct;
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
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getConstruct() {
		return construct;
	}
	public void setConstruct(String construct) {
		this.construct = construct;
	}
}
