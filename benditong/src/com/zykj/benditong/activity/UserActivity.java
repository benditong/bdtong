package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.RoundImageView;

public class UserActivity extends BaseActivity {

	private RelativeLayout rl_me_top;
	private RoundImageView rv_me_avatar;
	private TextView tv_me_mobile,user_login,user_money,user_integral;
	private LinearLayout user_store,app_about,app_explain,reset_password,app_version;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_user_activity);
		
		initView();
		requestData();
	}
	
	/**
	 * 加载页面
	 */
	private void initView(){
		rl_me_top = (RelativeLayout)findViewById(R.id.rl_me_top);//用户信息
		rv_me_avatar = (RoundImageView)findViewById(R.id.rv_me_avatar);//用户头像
		tv_me_mobile = (TextView)findViewById(R.id.tv_me_mobile);//用户手机
		user_login = (TextView)findViewById(R.id.user_login);//用户登录
		
		user_money = (TextView)findViewById(R.id.user_money);//我的钱包
		user_integral = (TextView)findViewById(R.id.user_integral);//积分

		user_store = (LinearLayout)findViewById(R.id.user_store);//我的收藏
		app_about = (LinearLayout)findViewById(R.id.app_about);//关于我们
		app_explain = (LinearLayout)findViewById(R.id.app_explain);//应用说明
		reset_password = (LinearLayout)findViewById(R.id.reset_password);//重置密码
		app_version = (LinearLayout)findViewById(R.id.app_version);//版本更新

		LayoutParams pageParms = rl_me_top.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH * 10 / 29;

		LayoutParams imgParms = rv_me_avatar.getLayoutParams();
		imgParms.width = Tools.M_SCREEN_WIDTH * 2 / 11;
		imgParms.height = Tools.M_SCREEN_WIDTH * 2 / 11;
		
		setListener(user_login, user_money, user_integral, user_store, app_about, app_explain, reset_password, app_version);
	}
	
	/**
	 * 请求服务器数据---首页
	 */
	private void requestData(){
		if(CommonUtils.CheckLogin()){
			tv_me_mobile.setVisibility(View.VISIBLE);
			user_login.setVisibility(View.GONE);
			rl_me_top.setOnClickListener(this);
			String avatar = BaseApp.getModel().getAvatar();
			tv_me_mobile.setText(StringUtil.isEmpty(BaseApp.getModel().getMobile())?BaseApp.getModel().getUsername():BaseApp.getModel().getMobile());//默认账户
			ImageLoader.getInstance().displayImage(avatar, rv_me_avatar);//用户头像
			//getmemberenvelopes();
		}else{
			tv_me_mobile.setVisibility(View.GONE);
			user_login.setVisibility(View.VISIBLE);
			rl_me_top.setOnClickListener(null);
			
			user_money.setText("￥0.00");//用户余额
			user_integral.setText("0");//用户资产
			rv_me_avatar.setImageResource(R.drawable.user_head_img);;//用户头像
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 11:
			/*成功登陆之后*/
			if (data != null) {requestData();}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.user_login:
			break;
		case R.id.user_money:
			isLogin();
			break;
		case R.id.user_integral:
			isLogin();
			break;
		case R.id.user_store:
			isLogin();
			break;
		case R.id.app_about:
			break;
		case R.id.app_explain:
			break;
		case R.id.reset_password:
			isLogin();
			break;
		case R.id.app_version:
			break;
		default:
			break;
		}
	}
	
	//退出操作
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		CommonUtils.exitkey(keyCode, this);
		return super.onKeyDown(keyCode, event);
	}
	
	private void isLogin(){
		if(!CommonUtils.CheckLogin()){ Tools.toast(this, "请先登录"); return; }
	}
}
