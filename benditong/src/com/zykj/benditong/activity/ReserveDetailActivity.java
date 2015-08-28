package com.zykj.benditong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class ReserveDetailActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private String orderid;
	private TextView order_num,order_name,order_time,order_pnum,order_price,order_people,
	order_mobile,order_date,order_cancel,order_pay;
	private ImageView order_img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_order_reserve_detail);
		
		orderid = getIntent().getStringExtra("orderid");
		initView();
		requestData();
	}
	
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("订单详情");

		order_num = (TextView)findViewById(R.id.order_num);
		order_img = (ImageView)findViewById(R.id.order_img);
		order_name = (TextView)findViewById(R.id.order_name);
		order_time = (TextView)findViewById(R.id.order_time);
		order_pnum = (TextView)findViewById(R.id.order_pnum);
		order_price = (TextView)findViewById(R.id.order_price);
		order_people = (TextView)findViewById(R.id.order_people);
		order_mobile = (TextView)findViewById(R.id.order_mobile);
		order_date = (TextView)findViewById(R.id.order_date);
		order_cancel = (TextView)findViewById(R.id.order_cancel);
		order_pay = (TextView)findViewById(R.id.order_pay);
	}
	private void requestData(){
		HttpUtils.getOrder(res_getOrder, orderid);
	}
	
	private HttpErrorHandler res_getOrder = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			final Order order = JSONObject.parseObject(json.getString(UrlContants.jsonData), Order.class);
			order_num.setText("订单编号："+order.getId());
			ImageLoader.getInstance().displayImage(order.getImgsrc()+order.getGoodsimg(), order_img);
			order_name.setText(order.getGoodsname());
			order_time.setText("用餐时间："+order.getIntime());
			order_pnum.setText("用餐人数："+order.getInnum()+"人");
			order_price.setText("预计消费："+order.getInprice()+"元");
			order_people.setText(order.getName());
			order_mobile.setText(order.getMobile());
			order_date.setText(order.getInnum());
			order_cancel.setText("3".equals(order.getState())?"删除订单":"取消订单");
			order_pay.setText("1".equals(order.getState())?"付款":"评价");
			order_cancel.setVisibility("2".equals(order.getState())?View.GONE:View.VISIBLE);
			order_cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if("3".equals(order.getState()) || "0".equals(order.getState())){
						Tools.toast(ReserveDetailActivity.this, "删除订单");
					}else{
						Tools.toast(ReserveDetailActivity.this, "取消订单");
					}
				}
			});
			order_pay.setVisibility(("1".equals(order.getState()) || "3".equals(order.getState()))?View.GONE:View.VISIBLE);
			order_pay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if("0".equals(order.getState()) || "0".equals(order.getState())){
						Tools.toast(ReserveDetailActivity.this, "付款");
					}else{
						Tools.toast(ReserveDetailActivity.this, "评价");
					}
				}
			});;
		}

		@Override
		public void onRecevieFailed(String status, JSONObject json) {
			Tools.toast(ReserveDetailActivity.this, "没有此条信息或者信息还未审核!");
		}
	};
}
