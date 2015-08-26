package com.zykj.benditong.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;

public class Welcome extends BaseActivity {
	
	private LocationClient mLocationClient;
	private MyLocationListener mLocationListener;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());
		initView(R.layout.ui_welcome);
		
		initLocation();
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				String is_intro = getSharedPreferenceValue(BaseApp.IS_INTRO);
				boolean should_intro = false;
				int version = Tools.getAppVersion(Welcome.this);
				String save_version = getSharedPreferenceValue(BaseApp.VERSION);
				int save_version_int = save_version.equals("") ? -1 : Integer
						.parseInt(save_version);

				if (is_intro.length() > 0 && version == save_version_int) {// 已经进行过指引,且版本号符合
					should_intro = false;
				} else {
					should_intro = false;//true
				}

				if (should_intro) {
//					Intent intent = new Intent(Welcome.this, IntroActivity.class);
//					startActivity(intent);
				} else {
					Intent intent = new Intent(Welcome.this, MainActivity.class);
					startActivity(intent);
				}
				finish();

			}
		};
		timer.schedule(task, 2000);
	}

	@Override
	protected void onStart() {
		super.onStart();
		//开启定位
		if(!mLocationClient.isStarted())
			mLocationClient.start();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//停止定位
		mLocationClient.stop();
	}
	
	private void checkLogin(){
		if(StringUtil.isEmpty(BaseApp.getModel().getUsername())){
			return;
		}
        RequestParams params = new RequestParams();
        params.put("mob", BaseApp.getModel().getUsername());
        params.put("pass", BaseApp.getModel().getPassword());
        HttpUtils.login(new HttpErrorHandler() {
			@Override
			public void onRecevieSuccess(JSONObject json) {
				JSONObject data = json.getJSONObject(UrlContants.jsonData);
				String avatar = StringUtil.toStringOfObject(data.getString("avatar"));
				BaseApp.getModel().setAvatar(avatar.replace("app.do", UrlContants.SERVERIP));//头像
				BaseApp.getModel().setMobile(StringUtil.toStringOfObject(data.getString("mobile")));//手机号
				BaseApp.getModel().setMoney(StringUtil.toStringOfObject(data.getString("account")));//我的钱包
				BaseApp.getModel().setIntegral(StringUtil.toStringOfObject(data.getString("coins")));//积分
				BaseApp.getModel().setPassword(BaseApp.getModel().getPassword());//登录密码
				BaseApp.getModel().setUserid(StringUtil.toStringOfObject(data.getString("id")));//用户Id
				BaseApp.getModel().setUsername(StringUtil.toStringOfObject(data.getString("username")));//真实姓名
			}
			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				BaseApp.getModel().clear();
				Tools.toast(Welcome.this, "登录失效!");
			}
		}, params);
	}

	private void initLocation() {
		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		
		mLocationClient.setLocOption(option);
	}
	
	private class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			BaseApp.getModel().setLatitude(String.valueOf(location.getLatitude()));
			BaseApp.getModel().setLongitude(String.valueOf(location.getLongitude()));
			Tools.toast(Welcome.this, String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude()));
			checkLogin();
		}
	}
}
