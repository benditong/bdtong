package com.zykj.benditong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class UserLoginActivity extends BaseActivity{
	
	private MyCommonTitle myCommonTitle;
	private EditText uu_username,uu_password;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_user_login);
		
		initView();
	}

	/**
	 * 初次加载页面
	 */
	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(this, null);
		myCommonTitle.setTitle("登录");
		myCommonTitle.setEditTitle("注册");

		uu_username = (EditText)findViewById(R.id.uu_username);//用户名
		uu_password = (EditText)findViewById(R.id.uu_password);//密码
		Button login_in = (Button)findViewById(R.id.login_in);//登录

		TextView forgetpwd = (TextView)findViewById(R.id.forgetpwd);//忘记密码
		
		setListener(login_in,forgetpwd);//点击事件
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.login_in:
			/*登录*/
	        String username=uu_username.getText().toString().trim();
	        final String password=uu_password.getText().toString().trim();
	        if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
	            Tools.toast(UserLoginActivity.this, "请先填写账号和密码");
	            return;
	        }
	        RequestParams params = new RequestParams();
	        params.put("mob", username);
	        params.put("pass", password);
	        HttpUtils.login(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					JSONObject data = json.getJSONObject(UrlContants.jsonData);
					String avatar = StringUtil.toStringOfObject(data.getString("avatar"));
					BaseApp.getModel().setAvatar(avatar.replace("app.do", UrlContants.SERVERIP));//头像
					BaseApp.getModel().setMobile(StringUtil.toStringOfObject(data.getString("mobile")));//手机号
					BaseApp.getModel().setMoney(StringUtil.toStringOfObject(data.getString("account")));//我的钱包
					BaseApp.getModel().setIntegral(StringUtil.toStringOfObject(data.getString("coins")));//积分
					BaseApp.getModel().setPassword(password);//登录密码
					BaseApp.getModel().setUserid(StringUtil.toStringOfObject(data.getString("id")));//用户Id
					BaseApp.getModel().setUsername(StringUtil.toStringOfObject(data.getString("username")));//真实姓名
			        submitDeviceToken(data.getString("id"));
				}
				@Override
				public void onRecevieFailed(String status, JSONObject json) {
					Tools.toast(UserLoginActivity.this, json.getString("message"));
				}
			}, params);
			break;
		case R.id.forgetpwd:
			/*忘记密码*/
			startActivity(new Intent(this, UserRegisterActivity.class).putExtra("type", "forget"));
			break;
		case R.id.aci_edit_btn:
			/*注册*/
			startActivity(new Intent(this, UserRegisterActivity.class).putExtra("type", "register"));
			break;
		}
	}
	
	private void submitDeviceToken(String userid){
		//Tools.toast(this, device_token);
		setResult(Activity.RESULT_OK, getIntent().putExtra("userid", userid));
		finish();
	}
}
