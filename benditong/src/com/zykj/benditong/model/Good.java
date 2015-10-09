package com.zykj.benditong.model;

import java.io.Serializable;

public class Good implements Serializable{
	
	/**
	 * 菜品
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private String title;
	private String imgsrc;
	private String price;
	private String markprice;
	private String content;
	private String intro;
	private String tid;
	private String istop;
	private String ischeck;
	private String addtime;
	private String lasts;
	private String usetime;
	private String guize;
	private String pid;
	
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMarkprice() {
		return markprice;
	}
	public void setMarkprice(String markprice) {
		this.markprice = markprice;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getIstop() {
		return istop;
	}
	public void setIstop(String istop) {
		this.istop = istop;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getLasts() {
		return lasts;
	}
	public void setLasts(String lasts) {
		this.lasts = lasts;
	}
	public String getUsetime() {
		return usetime;
	}
	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}
	public String getGuize() {
		return guize;
	}
	public void setGuize(String guize) {
		this.guize = guize;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
}