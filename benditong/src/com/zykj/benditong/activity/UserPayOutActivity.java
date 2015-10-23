package com.zykj.benditong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class UserPayOutActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private EditText user_payout,user_alipay;
	private Button pay_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_user_payout);
		
		initView();
	}
	
	/**
	 * 初始化页面
	 */
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("余额转出");

		user_payout = (EditText)findViewById(R.id.user_payout);
		user_alipay = (EditText)findViewById(R.id.user_alipay);
		pay_button = (Button)findViewById(R.id.pay_button);
		pay_button.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.pay_button:
			pay_button.setOnClickListener(null);
			if(StringUtil.isEmpty(user_payout.getText().toString())){
				Tools.toast(this, "请输入提现金额");
				return;
			}
			if(StringUtil.isEmpty(user_alipay.getText().toString())){
				Tools.toast(this, "请输入支付宝");
				return;
			}
			float payout = Float.valueOf(user_payout.getText().toString());
			if(payout > Float.valueOf(BaseApp.getModel().getMoney())){
				Tools.toast(this, "余额不足");
			}else{
				posttixian(payout, user_alipay.getText().toString());
			}
			break;
		default:
			break;
		}
	}

	private void posttixian(final float payout, String alipay){
		RequestParams params = new RequestParams();
		params.put("uid", BaseApp.getModel().getUserid());
		params.put("money", payout);
		params.put("alipay", alipay);
		HttpUtils.posttixian(new HttpErrorHandler() {
			@Override
			public void onRecevieSuccess(JSONObject json) {
				Tools.toast(UserPayOutActivity.this, json.getString("message"));
				setResult(Activity.RESULT_OK, getIntent().putExtra("payout", payout));
				finish();
			}
			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				pay_button.setOnClickListener(UserPayOutActivity.this);
				Tools.toast(UserPayOutActivity.this, json.getString("message"));
			}
		}, params);
	}
}
