package com.zykj.benditong.activity;

import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.PrivateCredentialPermission;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
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
	private ViewFlipper flipper;
	private  int[] resId={R.drawable.pic_1,R.drawable.pic_2,R.drawable.pic_3,R.drawable.pic_4};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
		initView(R.layout.ui_welcome);
//		flipper=(ViewFlipper) findViewById(R.id.flipper);
//		for(int i=0;i<resId.length;i++){
//			flipper.addView(getImageView(resId[i]));
//		}
//		flipper.setInAnimation(this,R.anim.popshow_anim);
//		flipper.setOutAnimation(this,R.anim.pophidden_anim);
//		flipper.setFlipInterval(500);
//		flipper.startFlipping();
		
		
		mLocationManger=LocationManagerProxy.getInstance(this);
		//进行一次定位
		mLocationManger.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, mLocationListener);
		
		checkLogin();
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
	
	private void checkLogin(){
		if(StringUtil.isEmpty(BaseApp.getModel().getUsername())){
			return;
		}
        RequestParams params = new RequestParams();
        params.put("mob", BaseApp.getModel().getMobile());
        params.put("pass", BaseApp.getModel().getPassword());
        HttpUtils.login(new HttpErrorHandler() {
			@Override
			public void onRecevieSuccess(JSONObject json) {
				JSONObject data = json.getJSONObject(UrlContants.jsonData);
				String avatar = StringUtil.toStringOfObject(data.getString("avatar"));
				BaseApp.getModel().setAvatar(avatar.replace("app.do", UrlContants.SERVERIP));//头像
				BaseApp.getModel().setMobile(StringUtil.toStringOfObject(data.getString("mobile")));//手机号
				BaseApp.getModel().setMoney(StringUtil.toStringOfObject(data.getString("account")));//我的钱包
				BaseApp.getModel().setIntegral(StringUtil.toStringOfObject(data.getString("points")));//积分
				BaseApp.getModel().setPassword(BaseApp.getModel().getPassword());//登录密码
				BaseApp.getModel().setUserid(StringUtil.toStringOfObject(data.getString("id")));//用户Id
				BaseApp.getModel().setUsername(StringUtil.toStringOfObject(data.getString("username")));//真实姓名
				BaseApp.getModel().setSign(StringUtil.toStringOfObject(data.getString("sign")));//是否签到
			}
			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				BaseApp.getModel().clear();
				Tools.toast(Welcome.this, "登录失效!");
			}
		}, params);
	}	
	
	//定位
	private LocationManagerProxy mLocationManger;
	private AMapLocationListener mLocationListener=new AMapLocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		
		@Override
		public void onProviderEnabled(String provider) {}
		
		@Override
		public void onProviderDisabled(String provider) {}
		
		@Override
		public void onLocationChanged(Location location) {}
		
		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null && location.getAMapException().getErrorCode() == 0) {
				BaseApp.getModel().setLatitude(location.getLatitude()+"");
				BaseApp.getModel().setLongitude(location.getLongitude()+"");
				Tools.toast(Welcome.this, "lat="+location.getLatitude()+"long="+location.getLongitude());
			}else{
				Tools.toast(Welcome.this, "定位出现异常");
			}
		}
	};
	private ImageView getImageView(int resId)
	{
		ImageView image =new ImageView(this);
		image.setBackgroundResource(resId);
		return image;
	}
}
