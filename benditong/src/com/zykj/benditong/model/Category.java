package com.zykj.benditong.model;

import java.io.Serializable;

public class Category implements Serializable{
	
	/**
	 * 餐厅分类
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private String tid;
	private String cid;
	private String oby;
	private String title;
	private String ischeck;
	private String addtime;
	private String istop;
	private String isimg;
	private String imgsrc;
	private String intro;
	private String content;
	private String seotitle;
	private String seokeys;
	private String seodesc;
	
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
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getOby() {
		return oby;
	}
	public void setOby(String oby) {
		this.oby = oby;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getIstop() {
		return istop;
	}
	public void setIstop(String istop) {
		this.istop = istop;
	}
	public String getIsimg() {
		return isimg;
	}
	public void setIsimg(String isimg) {
		this.isimg = isimg;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSeotitle() {
		return seotitle;
	}
	public void setSeotitle(String seotitle) {
		this.seotitle = seotitle;
	}
	public String getSeokeys() {
		return seokeys;
	}
	public void setSeokeys(String seokeys) {
		this.seokeys = seokeys;
	}
	public String getSeodesc() {
		return seodesc;
	}
	public void setSeodesc(String seodesc) {
		this.seodesc = seodesc;
	}
}
