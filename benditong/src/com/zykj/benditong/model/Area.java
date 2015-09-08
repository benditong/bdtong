package com.zykj.benditong.model;

import java.io.Serializable;
import java.util.List;

public class Area implements Serializable{
	/**
	 * 城市
	 */
	private static final long serialVersionUID = 1L;
	private String id;//城市id
	private String tid;//父id
	private String cid;//城市号
	private String oby;//排序
	private String title;//城市名
	private String letter;//首字母
	private String ischeck;//审核状态
	private String addtime;//添加时间
	private List<Area> subarea;//下属城市
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
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
	public List<Area> getSubarea() {
		return subarea;
	}
	public void setSubarea(List<Area> subarea) {
		this.subarea = subarea;
	}
}
