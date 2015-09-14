package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class UserWalletActivity extends BaseActivity {

	private MyCommonTitle myCommonTitle;
	private TextView user_money;
	private LinearLayout pay_out;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_user_wallet);
		
		initView();
	}
	
	/**
	 * 初始化页面
	 */
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("我的钱包");

		user_money = (TextView)findViewById(R.id.user_money);
		pay_out = (LinearLayout)findViewById(R.id.pay_out);

		user_money.setText(String.format("%.2f元", Float.valueOf(BaseApp.getModel().getMoney())));
		pay_out.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		startActivityForResult(new Intent(this, UserPayOutActivity.class),6);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			user_money.setText(String.format("%.2f元", Float.valueOf(BaseApp.getModel().getMoney())-data.getFloatExtra("payout", 0)));
			BaseApp.getModel().setMoney(String.format("%.2f", Float.valueOf(BaseApp.getModel().getMoney())-data.getFloatExtra("payout", 0)));
		}
	}
}
