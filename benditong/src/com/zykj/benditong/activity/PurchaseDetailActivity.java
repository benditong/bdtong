package com.zykj.benditong.activity;

import android.os.Bundle;
import android.text.Html;
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

public class PurchaseDetailActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private String orderid;
	private TextView order_num,product_name,product_price,product_content,product_num,total_price,
	order_mobile,order_date,order_cancel,order_pay;
	private ImageView order_img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_order_purchase_detail);
		
		orderid = getIntent().getStringExtra("orderid");
		initView();
		requestData();
	}
	
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("订单详情");

		order_num = (TextView)findViewById(R.id.order_num);
		order_img = (ImageView)findViewById(R.id.order_img);
		product_name = (TextView)findViewById(R.id.product_name);
		product_price = (TextView)findViewById(R.id.product_price);
		product_content = (TextView)findViewById(R.id.product_content);
		product_num = (TextView)findViewById(R.id.product_num);
		total_price = (TextView)findViewById(R.id.total_price);
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
			order_num.setText(order.getInnum());
			ImageLoader.getInstance().displayImage(order.getGoodsimg(), order_img);
			product_name.setText(order.getTitle());
			product_price.setText("￥"+order.getInprice());
			product_content.setText(order.getName());
			product_num.setText("x"+order.getInnum());
			order_cancel.setText("3".equals(order.getState())?"删除订单":"取消订单");
			order_pay.setText("1".equals(order.getState())?"付款":"评价");
			Float tatal = Float.parseFloat(order.getInprice())*Integer.parseInt(order.getInnum());
			total_price.setText(Html.fromHtml("<font color=#adadb0>总价：</font><font color=#000000>"+String.format("￥%.2f", tatal)+"</font>"));
			order_mobile.setText(order.getMobile());
			order_cancel.setVisibility("2".equals(order.getState())?View.GONE:View.VISIBLE);
			order_cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if("3".equals(order.getState()) || "0".equals(order.getState())){
						Tools.toast(PurchaseDetailActivity.this, "删除订单");
					}else{
						Tools.toast(PurchaseDetailActivity.this, "取消订单");
					}
				}
			});;
			order_pay.setVisibility(("1".equals(order.getState()) || "3".equals(order.getState()))?View.GONE:View.VISIBLE);
			order_pay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if("0".equals(order.getState()) || "0".equals(order.getState())){
						Tools.toast(PurchaseDetailActivity.this, "付款");
					}else{
						Tools.toast(PurchaseDetailActivity.this, "评价");
					}
				}
			});;
		}
	};
}
