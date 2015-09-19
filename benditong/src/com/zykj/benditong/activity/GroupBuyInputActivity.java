package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.MyRequestDailog;

public class GroupBuyInputActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private String tid,pid,inprice,goodname;
	private TextView good_name,good_price,good_num,good_all_price;
	private ImageView order_jian,order_jia;
	private Button reserve_go;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_groupbuy_input);
		
		tid = getIntent().getStringExtra("tid");//tid商铺ID编号
		pid = getIntent().getStringExtra("pid");//pid商品ID编号
		inprice = getIntent().getStringExtra("inprice");//inprice商品价格
		goodname = getIntent().getStringExtra("goodname");//goodname商品名称
		
		initView();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("提交订单");

		good_name = (TextView)findViewById(R.id.good_name);
		good_name.setText(goodname);
		good_price = (TextView)findViewById(R.id.good_price);
		good_price.setText(inprice+"元");
		order_jian = (ImageView)findViewById(R.id.order_jian);
		good_num = (TextView)findViewById(R.id.good_num);
		order_jia = (ImageView)findViewById(R.id.order_jia);
		good_all_price = (TextView)findViewById(R.id.good_all_price);
		good_all_price.setText(inprice+"元");
		reserve_go = (Button)findViewById(R.id.reserve_go);
		
		setListener(order_jian,order_jia,reserve_go);
	}

	@Override
	public void onClick(View view) {
		int num = Integer.valueOf(good_num.getText().toString());
		switch (view.getId()) {
		case R.id.order_jian:
			if(num > 1){
				good_num.setText(--num+"");
				good_num.setText(num+"");
				good_all_price.setText(String.format("%.2f元", num * Float.valueOf(inprice)));
			}
			break;
		case R.id.order_jia:
			good_num.setText(++num+"");
			good_num.setText(num+"");
			good_all_price.setText(String.format("%.2f元", num * Float.valueOf(inprice)));
			break;
		case R.id.reserve_go:
			MyRequestDailog.showDialog(this, "");
			final int goodsnum = num;
			RequestParams params = new RequestParams();
			params.put("type", "shop");
			params.put("tid", tid);
			params.put("uid", BaseApp.getModel().getUserid());
			params.put("pid", pid);
			params.put("innum", num);
			params.put("inprice", inprice);
			params.put("name", BaseApp.getModel().getUsername());
			params.put("mobile", BaseApp.getModel().getMobile());
			HttpUtils.submitShopOrder(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					MyRequestDailog.closeDialog();
					Tools.toast(GroupBuyInputActivity.this, "预定成功!");
					startActivity(new Intent(GroupBuyInputActivity.this, PayActivity.class)
							.putExtra("order_name", "").putExtra("order_price", goodsnum * Float.valueOf(inprice)));
					finish();
				}
				@Override
				public void onRecevieFailed(String status, JSONObject json) {
					Tools.toast(GroupBuyInputActivity.this, "预定失败!");
				}
			}, params);
			break;
		default:
			break;
		}
	}
}