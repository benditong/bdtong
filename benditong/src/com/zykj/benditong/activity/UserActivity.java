package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.RoundImageView;

public class UserActivity extends BaseActivity {

	private RelativeLayout rl_me_top;
	private Button login_out;
	private RoundImageView rv_me_avatar;
	private TextView tv_me_mobile,user_login,user_money,user_integral,user_sign_text;
	private LinearLayout user_store,app_about,app_explain,reset_password,app_version,user_sign,user_left,user_right;
	
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
		user_sign = (LinearLayout)findViewById(R.id.user_sign);//是否签到
		user_sign_text = (TextView)findViewById(R.id.user_sign_text);//是否签到
		
		user_money = (TextView)findViewById(R.id.user_money);//我的钱包
		user_left = (LinearLayout)findViewById(R.id.user_left);//我的钱包
		user_integral = (TextView)findViewById(R.id.user_integral);//积分
		user_right = (LinearLayout)findViewById(R.id.user_right);//我的钱包

		user_store = (LinearLayout)findViewById(R.id.user_store);//我的收藏
		app_about = (LinearLayout)findViewById(R.id.app_about);//关于我们
		app_explain = (LinearLayout)findViewById(R.id.app_explain);//应用说明
		reset_password = (LinearLayout)findViewById(R.id.reset_password);//重置密码
		app_version = (LinearLayout)findViewById(R.id.app_version);//版本更新
		
		login_out = (Button)findViewById(R.id.login_out);

		LayoutParams pageParms = rl_me_top.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH * 10 / 29;

		LayoutParams imgParms = rv_me_avatar.getLayoutParams();
		imgParms.width = Tools.M_SCREEN_WIDTH * 2 / 11;
		imgParms.height = Tools.M_SCREEN_WIDTH * 2 / 11;
		
		setListener(user_login, rv_me_avatar, tv_me_mobile, user_sign, user_left, user_right, user_store, app_about, app_explain, reset_password, app_version, login_out);
	}
	
	/**
	 * 请求服务器数据---首页
	 */
	private void requestData(){
		if(CommonUtils.CheckLogin()){
			tv_me_mobile.setVisibility(View.VISIBLE);
			rv_me_avatar.setVisibility(View.VISIBLE);
			user_login.setVisibility(View.GONE);
			login_out.setVisibility(View.VISIBLE);
			user_sign.setVisibility(View.VISIBLE);
//			rv_me_avatar.setOnClickListener(this);
			String avatar = BaseApp.getModel().getAvatar();
			tv_me_mobile.setText(StringUtil.isEmpty(BaseApp.getModel().getUsername())?BaseApp.getModel().getMobile():BaseApp.getModel().getUsername());//默认账户
			ImageLoader.getInstance().displayImage(avatar, rv_me_avatar);//用户头像
			user_money.setText(String.format("￥%.2f", Float.valueOf(BaseApp.getModel().getMoney())));//用户余额
			user_integral.setText(BaseApp.getModel().getIntegral());//用户资产
			user_sign_text.setText("ok".equals(BaseApp.getModel().getSign())?"已签到":"签到");
		}else{
			tv_me_mobile.setVisibility(View.GONE);
			rv_me_avatar.setVisibility(View.GONE);
			user_login.setVisibility(View.VISIBLE);
			login_out.setVisibility(View.GONE);
			user_sign.setVisibility(View.GONE);
//			rv_me_avatar.setOnClickListener(null);
			
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
			requestData();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		user_money.setText("￥"+StringUtil.toString(BaseApp.getModel().getMoney(), "0.00"));//用户余额
		user_integral.setText(StringUtil.toString(BaseApp.getModel().getIntegral(), "0"));//用户资产
	}

	@Override
	public void onClick(View view) {
		Intent intent =null;
		switch (view.getId()) {
		case R.id.user_login:
			/* 用户登录 */
			startActivityForResult(new Intent(this, UserLoginActivity.class), 11);
			break;
		case R.id.rv_me_avatar:
			/* 用户信息 */
			isLogin();
			startActivityForResult(new Intent(this,UserInfoActivity.class), 11);
			break;
		case R.id.tv_me_mobile:
			/* 用户信息 */
			isLogin();
			startActivityForResult(new Intent(this,UserInfoActivity.class), 11);
			break;
		case R.id.user_sign:
			/* 签到 */
			if("ok".equals(BaseApp.getModel().getSign())){Tools.toast(this, "今天已经签到");return;}
			RequestParams params = new RequestParams();
			params.put("uid", BaseApp.getModel().getUserid());
			HttpUtils.usersign(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					user_sign_text.setText("已签到");
					BaseApp.getModel().setSign("ok");
				}
			}, params);
			break;
		case R.id.user_left:
			isLogin();
			startActivity(new Intent(this, UserWalletActivity.class));
			break;
		case R.id.user_right:
			isLogin();
			RequestParams urlparams = new RequestParams();
			urlparams.put("uid", BaseApp.getModel().getUserid());
			urlparams.put("points", BaseApp.getModel().getIntegral());
			HttpUtils.getLoginUrl(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					String url = json.getJSONObject(UrlContants.jsonData).getString("url");
					Intent intent = new Intent().setClass(UserActivity.this, CreditActivity.class);
		            intent.putExtra("navColor", "#25b6ed");//配置导航条的背景颜色，请用#ffffff长格式。
		            intent.putExtra("titleColor", "#ffffff");//配置导航条标题的颜色，请用#ffffff长格式。
		            intent.putExtra("url", url);//配置自动登陆地址，每次需服务端动态生成。
					startActivity(intent);
				}
			}, urlparams);
			break;
		case R.id.user_store:
			isLogin();
			if (CommonUtils.CheckLogin()) {
				startActivity(new Intent(this, UserStoreActivity.class));
			}else {
				//startActivity(new Intent(this, UserLoginActivity.class));
				startActivityForResult(new Intent(this, UserLoginActivity.class), 11);
			}
			break;
		case R.id.app_about:
			intent = new Intent(this, AppAboutActivity.class);
			startActivity(intent);
			break;
		case R.id.app_explain:
			intent = new Intent(this, AppExplainActivity.class);
			startActivity(intent);
			break;
		case R.id.reset_password:
			isLogin();
			if(CommonUtils.CheckLogin()){
				//myCommonTitle.setTitle("重置密码");
				intent = new Intent(this, UserRegisterActivity.class);
				intent.putExtra("type", "forget");
				startActivity(intent);
			}else {
				intent =new Intent(UserActivity.this,UserLoginActivity.class);
				startActivityForResult(intent, 11);
			}
			break;
		case R.id.app_version:
			Tools.toast(this, "已是最新版本");
			break;
		case R.id.login_out:
			BaseApp.getModel().clear();
			requestData();
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
