package com.zykj.benditong.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class House implements Serializable {
	/**
	 * 房产
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;// 标题
	private String price;// 价格
	private String tingshi;// 厅室
	private String area;// 面积
	private String fitment;//内部装修


	private String floor;// 所在楼层
	private String allfloor;// 总楼层数
	private String plot;// 小区名称
	private String plotaddress;// 小区地址
	private String intro;// 房源简介
	private String type;// 租住方式：合租房：1，整租房：2
	private String name;// 联系人
	private String mobile;// 联系电话
	private String addtime;
	private List<Map<String, String>> imglist;//图片

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTingshi() {
		return tingshi;
	}

	public void setTingshi(String tingshi) {
		this.tingshi = tingshi;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	public String getFitment() {
		return fitment;
	}

	public void setFitment(String fitment) {
		this.fitment = fitment;
	}
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getAllfloor() {
		return allfloor;
	}

	public void setAllfloor(String allfloor) {
		this.allfloor = allfloor;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getPlotaddress() {
		return plotaddress;
	}

	public void setPlotaddress(String plotaddress) {
		this.plotaddress = plotaddress;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public List<Map<String, String>> getImglist() {
		return imglist;
	}

	public void setImglist(List<Map<String, String>> imglist) {
		this.imglist = imglist;
	}
}
