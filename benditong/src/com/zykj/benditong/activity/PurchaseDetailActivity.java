package com.zykj.benditong.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class PurchaseDetailActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private Order order;
	private TextView order_num,product_name,product_price,product_content,product_num,total_price,
	order_mobile,order_date,order_cancel,order_pay;
	private ImageView order_img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_order_purchase_detail);
		
		order = (Order)getIntent().getSerializableExtra("order");
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
//	private void requestData(){
//		HttpUtils.getOrder(res_getOrder, orderid);
//	}
	
	private void requestData(){
//		order = JSONObject.parseObject(json.getString(UrlContants.jsonData), Order.class);
		order_num.setText("订单编号："+order.getOid());
		ImageLoader.getInstance().displayImage(order.getGoodsimg(), order_img);
		product_name.setText(order.getTitle());
		product_price.setText("￥"+order.getInprice());
		product_content.setText(order.getName());
		product_num.setText("x"+order.getInnum());
		Float tatal = Float.parseFloat(order.getInprice())*Integer.parseInt(order.getInnum());
		total_price.setText(Html.fromHtml("<font color=#adadb0>总价：</font><font color=#000000>"+String.format("￥%.2f", tatal)+"</font>"));
		order_mobile.setText(order.getMobile());
		order_date.setText(order.getAddtime());
		
		order_cancel.setText("1".contains(order.getState())?"取消订单":"删除订单");
		order_pay.setText("0".contains(order.getState())?"付款":"评价");
		order_pay.setVisibility("02".contains(order.getState())?View.VISIBLE:View.GONE);
		setListener(order_cancel, order_pay);
		if("1".equals(order.getIscomment())){
			order_pay.setOnClickListener(null);
			order_pay.setBackgroundResource(R.drawable.bg_null_grey);
			order_pay.setText("已评价");
			order_pay.setTextColor(PurchaseDetailActivity.this.getResources().getColor(R.color.grey));
		}
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.order_cancel:
			/**取消、删除按钮*/
			String text = order_cancel.getText().toString();
			new AlertDialog.Builder(this).setTitle(text).setMessage("您确定要"+text+"吗?")
            	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialogInterface, int position) {
					RequestParams params = new RequestParams();
					params.put("id", order.getId());
					if("1".contains(order.getState())){
						params.put("state", "4");
						HttpUtils.updateorder(res_updateorder, params);
					}else{
						HttpUtils.delOrder(res_delOrder, params);
					}
	            }
	        }).setNegativeButton("取消",null).show();
			break;
		case R.id.order_pay:
			/**付款、评价按钮*/
			if("2".equals(order.getState())){
				startActivityForResult(new Intent(this, AssessActivity.class)
							.putExtra("order", order).putExtra("type", 3), 1);//评价
			}else{
				startActivityForResult(new Intent(this, PayActivity.class).putExtra("order", order), 2);
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1 && resultCode == Activity.RESULT_OK){
			order_pay.setOnClickListener(null);
			order_pay.setBackgroundResource(R.drawable.bg_null_grey);
			order_pay.setText("已评价");
			order_pay.setTextColor(getResources().getColor(R.color.grey));
		}else if(requestCode == 2 && resultCode == Activity.RESULT_OK){
			order_pay.setVisibility(View.GONE);
		}
	}

	private AsyncHttpResponseHandler res_updateorder = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			setResult(Activity.RESULT_OK);
			finish();
			Tools.toast(PurchaseDetailActivity.this, "订单取消成功");
		}
	};
	
	private AsyncHttpResponseHandler res_delOrder = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			setResult(Activity.RESULT_OK);
			finish();
			Tools.toast(PurchaseDetailActivity.this, "订单删除成功");
		}
	};
}
