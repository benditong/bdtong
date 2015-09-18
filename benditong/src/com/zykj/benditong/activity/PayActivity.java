package com.zykj.benditong.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class PayActivity extends BaseActivity {

	private MyCommonTitle myCommonTitle;
	
	private TextView order_name,order_price,user_wellet,pay_price,pay_alipay,pay_weixin;
	private ImageView sel_alipay,sel_weixin;
	private Button reserve_go;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_pay_activity);
		
		initView();
		addValues();
	}

	private void addValues() {
		String payname = getIntent().getStringExtra("order_name");
		float payprice = getIntent().getFloatExtra("order_price", 0f);
		String money = BaseApp.getModel().getMoney();
		
		
		order_name.setText(payname);
		order_price.setText(payprice+"");
		user_wellet.setText(money);
		float shouldpay = payprice-Float.valueOf(money) > 0 ? payprice-Float.valueOf(money) : 0;
		pay_price.setText(shouldpay+"");
		sel_alipay.setSelected(true);
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("支付订单");
		
		order_name = (TextView)findViewById(R.id.order_name);//订单名称
		order_price = (TextView)findViewById(R.id.order_price);//订单价格
		user_wellet = (TextView)findViewById(R.id.user_wellet);//钱包余额
		pay_price = (TextView)findViewById(R.id.pay_price);//还需支付
		pay_alipay = (TextView)findViewById(R.id.pay_alipay);//支付宝支付
		pay_weixin = (TextView)findViewById(R.id.pay_weixin);//微信支付
		pay_alipay.setText(Html.fromHtml("<big>支付宝支付</big><br><font color=#707070>推荐有支付宝账户的用户使用</font>"));
		pay_weixin.setText(Html.fromHtml("<big>微信支付</big><br><font color=#707070>推荐安装微信5.0及以上版本的使用</font>"));
		
		sel_alipay = (ImageView)findViewById(R.id.sel_alipay);//选择支付宝
		sel_weixin = (ImageView)findViewById(R.id.sel_weixin);//选择微信
		reserve_go = (Button)findViewById(R.id.reserve_go);//确认支付
		setListener(sel_alipay, sel_weixin, reserve_go);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.sel_alipay:
			sel_alipay.setSelected(true);
			sel_weixin.setSelected(false);
			break;
		case R.id.sel_weixin:
			sel_alipay.setSelected(false);
			sel_weixin.setSelected(true);
			break;
		case R.id.reserve_go:
			Tools.toast(this, sel_alipay.isSelected()?"支付宝":"微信");
			break;
		default:
			break;
		}
	}
}
