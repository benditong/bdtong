package com.zykj.benditong.activity;

import android.os.Bundle;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class UserWalletActivity extends BaseActivity {

	private MyCommonTitle myCommonTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_order_activity);
		
		initView();
		requestData();
	}
	
	/**
	 * 初始化页面
	 */
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("登录");
	}

	/**
	 * 加载数据
	 */
	private void requestData(){
	}
	
}
