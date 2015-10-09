package com.zykj.benditong.model;

import java.io.Serializable;

public class ZhaoPin implements Serializable{
/**
 * 招聘
 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;// 职位名称
	private String pay;// 薪资区间
	private String num;// 人数
	private String tid;// 职位类别ID编号
	private String intro;// 职位描述
	private String name;// 联系人
	private String mobile;// 联系电话
	private String coname;// 公司名称
	private String coaddress;// 公司地址
	private String conintro;// 公司简介
	private String addtime;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getConame() {
		return coname;
	}
	public void setConame(String coname) {
		this.coname = coname;
	}
	public String getCoaddress() {
		return coaddress;
	}
	public void setCoaddress(String coaddress) {
		this.coaddress = coaddress;
	}
	public String getConintro() {
		return conintro;
	}
	public void setConintro(String conintro) {
		this.conintro = conintro;
	}
	
	
}
