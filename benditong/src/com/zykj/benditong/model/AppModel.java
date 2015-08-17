package com.zykj.benditong.model;

import android.content.Context;

import com.zykj.benditong.utils.SharedPreferenceUtils;

/**
 * @author Administrator
 * 登录用户信息
 */
public class AppModel {
	
    private String username;//登录账号
    private String password;//登录密码
    private String userid;//用户Id
    private String avatar;//头像
    private String mobile;//手机
    private String money;//红包金额
    private String integral;//红包个数

    private static SharedPreferenceUtils utils;
    
    public static AppModel init(Context context){
        AppModel model =new AppModel();
        utils = SharedPreferenceUtils.init(context);

        if(utils.getUsername()!=null){
            model.username = utils.getUsername();
        }

        if(utils.getPassword() != null){
            model.password= utils.getPassword();
        }

        if(utils.getUserid() != null){
            model.userid= utils.getUserid();
        }

        if(utils.getAvatar() != null){
            model.avatar= utils.getAvatar();
        }

        if(utils.getMobile() != null){
            model.mobile= utils.getMobile();
        }

        if(utils.getMoney() != null){
            model.money= utils.getMoney();
        }

        if(utils.getIntegral() != null){
            model.integral= utils.getIntegral();
        }

        return model;
    }
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
        utils.setUsername(username);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
        utils.setPassword(password);
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
        utils.setUserid(userid);
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
        utils.setAvatar(avatar);
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
        utils.setMobile(mobile);
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
        utils.setMoney(money);
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
        utils.setIntegral(integral);
	}
	public void clear(){
		this.setUsername("");
		this.setPassword("");
		this.setUserid("");
		this.setAvatar("");
		this.setMobile("");
		this.setMoney("");
		this.setIntegral("");
		utils.clear();
	}
}
