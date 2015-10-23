package com.zykj.benditong.model;

import java.io.Serializable;

public class Car implements Serializable{
	/**
	 * 拼车
	 */
	private static final long serialVersionUID = 1L;
	
	private String tid;//报名信息的ID编号
	private String id;//信息编号
	private String from_address;//出发地
	private String to_address;//目的地
	private String starttime;//出发时间
	private String endtime;//预计到发时间
	private String seat;//人数
	private String price;//预计费用
	private String ischeck;//信息状态
	private String addtime;//信息发布日期
	private String car;//车型
	private String name;//姓名
	private String mobile;//联系电话
	private String seatprice;   //座位与价格段划分
	private String orderSeat; //已订购的座位数
	private String remain;     //剩余座位数
	private String trueprice; //当前座位数的价格
	private String area;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom_address() {
		return from_address;
	}
	public void setFrom_address(String from_address) {
		this.from_address = from_address;
	}
	public String getTo_address() {
		return to_address;
	}
	public void setTo_address(String to_address) {
		this.to_address = to_address;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
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
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getSeatprice() {
		return seatprice;
	}
	public void setSeatprice(String seatprice) {
		this.seatprice = seatprice;
	}
	public String getOrderSeat() {
		return orderSeat;
	}
	public void setOrderSeat(String orderSeat) {
		this.orderSeat = orderSeat;
	}
	public String getRemain() {
		return remain;
	}
	public void setRemain(String remain) {
		this.remain = remain;
	}
	public String getTrueprice() {
		return trueprice;
	}
	public void setTrueprice(String trueprice) {
		this.trueprice = trueprice;
	}
	
}
