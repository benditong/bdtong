package com.zykj.benditong.model;

import java.io.Serializable;

public class Order implements Serializable {

	/**
	 * 订单
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 订单Id
	private String oid;// 订单编号
	private String tid;// 当单产品Id
	private int type;//商家类型0-restaurant,1-hotel,2-shop
	private String name;// 姓名
	private String mobile;// 联系电话
	private String inprice;// 预计消费
	private String intime;// 用餐时间/入住时间
	private String innum;// 用餐人数/入住人数
	private String addtime;// 用餐时间/入住时间
	private String state;// 订单状态：0未付款1已付款,未消费2已消费3已退款4订单已取消
	private String title;// 餐厅，酒店，团购店铺的名称
	private String goodsimg;// 团购商品图片
	private String iscomment;// 订单是否有评论 1：有， 0：没有
	private String commentnum;// 评论数
	private String golds;//评星

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
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

	public String getInprice() {
		return inprice;
	}

	public void setInprice(String inprice) {
		this.inprice = inprice;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getInnum() {
		return innum;
	}

	public void setInnum(String innum) {
		this.innum = innum;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGoodsimg() {
		return goodsimg;
	}

	public void setGoodsimg(String goodsimg) {
		this.goodsimg = goodsimg;
	}

	public String getIscomment() {
		return iscomment;
	}

	public void setIscomment(String iscomment) {
		this.iscomment = iscomment;
	}

	public String getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(String commentnum) {
		this.commentnum = commentnum;
	}

	public String getGolds() {
		return golds;
	}

	public void setGolds(String golds) {
		this.golds = golds;
	}
	
}
