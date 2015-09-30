package com.zykj.benditong.activity;

import static cn.smssdk.framework.utils.R.getStringRes;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.TextUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.MyRequestDailog;

public class UserRegisterActivity extends BaseActivity{
	
	private MyCommonTitle myCommonTitle;
	private EditText uu_username,phone_code,uu_password;
	private String username, password;
	private String type;
	private Button identifying_code;

	private static String APPKEY = "a3936d65dbca";//93073e1ab3a8
	private static String APPSECRET = "8f01dfc260f81666795c55b520ba9f38";//e1ae8ab56cc63e61be4e4c16159af622
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_user_register);
		type = getIntent().getStringExtra("type");
		//初始化短信验证
		SMSSDK.initSDK(this,APPKEY,APPSECRET);
		EventHandler eh=new EventHandler(){
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eh);
		initView();
	}

	/**
	 * 初次加载页面
	 */
	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("forget".equals(type)?"重置密码":"注册");
		
		uu_username = (EditText)findViewById(R.id.uu_username);//手机号
		phone_code = (EditText)findViewById(R.id.phone_code);//验证码
		identifying_code = (Button)findViewById(R.id.identifying_code);//发送验证码
		uu_password = (EditText)findViewById(R.id.uu_password);//密码
		Button app_register_in = (Button)findViewById(R.id.app_register_in);//注册
		app_register_in.setText("forget".equals(type)?"重置密码":"注册");
		
		setListener(identifying_code, app_register_in);
	}

	@Override
	public void onClick(View view) {
        username=uu_username.getText().toString().trim();
		switch(view.getId()){
		case R.id.identifying_code:
	        if(!TextUtil.isMobile(username)){
	        	Tools.toast(UserRegisterActivity.this, "手机号格式不对");
	        	return;
	        }
			/*发送手机验证码*/
	        identifying_code.setOnClickListener(null);
	        new MyCount(60000, 1000).start();//一分钟倒计时
			SMSSDK.getVerificationCode("86",username);
			break;
		case R.id.app_register_in:
			/*注册、重置*/
	        String code=phone_code.getText().toString().trim();
	        password=uu_password.getText().toString().trim();
	        if(!TextUtil.isCode(code,4)){
	        	Tools.toast(UserRegisterActivity.this, "验证码格式不对");
	        	return;
	        }
	        if(StringUtil.isEmpty(password)){
	            Tools.toast(this, "密码不能为空");return;
	        }
			if (!TextUtil.isPasswordLengthLegal(password)){
				Tools.toast(this, "密码长度合法性校验6-20位任意字符");return;
			}
			MyRequestDailog.showDialog(this, "");
			SMSSDK.submitVerificationCode("86", username, phone_code.getText().toString().trim());
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("result", "result="+result);
			Log.e("event", "event="+event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//短信注册成功后，返回MainActivity,然后提示新好友
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
					//Tools.toast(UserRegisterActivity.this, "提交验证码成功");
					registerNewUser();
					//uu_password.setFocusable(true);
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Tools.toast(UserRegisterActivity.this, "验证码已经发送");
				}
			} else {
				((Throwable) data).printStackTrace();
				int resId = getStringRes(UserRegisterActivity.this, "smssdk_network_error");
				Tools.toast(UserRegisterActivity.this, event == SMSSDK.EVENT_GET_VERIFICATION_CODE?"验证码频繁，请稍后再试！":"验证码错误");
				if (resId > 0) {
					Tools.toast(UserRegisterActivity.this, resId+"");
				}
			}
		}

		private void registerNewUser() {
			RequestParams params = new RequestParams();
			if ("forget".equals(type)) {
				params.put("mob", username);
				params.put("password", password);
				HttpUtils.resetPassword(new HttpErrorHandler() {
					@Override
					public void onRecevieSuccess(JSONObject json) {
						MyRequestDailog.closeDialog();
						Tools.toast(UserRegisterActivity.this, json.getString("message"));
//						Tools.toast(UserRegisterActivity.this, "密码重置成功");
						finish();
					}
					@Override
					public void onRecevieFailed(String status, JSONObject json) {
						MyRequestDailog.closeDialog();
						Tools.toast(UserRegisterActivity.this, json.getString("message"));
					}
				}, params);
				
			} else {
				params.put("mob", username);
				params.put("pass", password);
				HttpUtils.register(new HttpErrorHandler() {
					@Override
					public void onRecevieSuccess(JSONObject json) {
						MyRequestDailog.closeDialog();
						Tools.toast(UserRegisterActivity.this, json.getString("message"));
						finish();
					}
					@Override
					public void onRecevieFailed(String status, JSONObject json) {
						Tools.toast(UserRegisterActivity.this, json.getString("message"));
					}
				}, params);
			}
		}
	};    
	
	/* 定义一个倒计时的内部类 */
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
 
        @Override
        public void onFinish() {
        	identifying_code.setText("点击获取验证码");
        	identifying_code.setOnClickListener(UserRegisterActivity.this);
        }
 
        @Override
        public void onTick(long millisUntilFinished) {
        	identifying_code.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
