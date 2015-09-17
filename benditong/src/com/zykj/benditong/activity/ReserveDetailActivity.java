package com.zykj.benditong.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class ReserveDetailActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private Order order;
	private int type;
	private TextView order_num,order_name,order_time,order_pnum,order_price,order_people,
	order_mobile,order_date,order_delete,order_assess;
	private ImageView order_img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_order_reserve_detail);
		
		order = (Order)getIntent().getSerializableExtra("order");
		type = getIntent().getIntExtra("type", 0);
//		orderid = getIntent().getStringExtra("orderid");
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
		order_delete = (TextView)findViewById(R.id.order_delete);
		order_assess = (TextView)findViewById(R.id.order_assess);
	}
	private void requestData(){
//		HttpUtils.getOrder(res_getOrder, orderid);
		order_num.setText("订单编号："+order.getOid());
		ImageLoader.getInstance().displayImage(order.getGoodsimg(), order_img);//图片
		order_name.setText(order.getTitle());
		order_time.setText((0 == type ?"用餐时间：":"入住时间：")+order.getIntime());
		order_pnum.setText((0 == type ?"用餐人数：":"入住人数：")+order.getInnum()+"人");
		order_price.setText("预计消费："+order.getInprice()+"元");
		order_people.setText(order.getName());
		order_mobile.setText(order.getMobile());
		order_date.setText(order.getAddtime());
		//state订单状态：0未付款1已付款,未消费2已消费3已退款4订单已取消       5删除订单  6已评价
		order_delete.setVisibility("0".contains(order.getState())?View.VISIBLE:View.GONE);
		order_assess.setVisibility("1234".contains(order.getState())?View.VISIBLE:View.GONE);
		
		setListener(order_delete, order_assess);
		if("1".equals(order.getIscomment())){
			order_assess.setOnClickListener(null);
			order_assess.setBackgroundResource(R.drawable.bg_null_grey);
			order_assess.setText("已评价");
			order_assess.setTextColor(ReserveDetailActivity.this.getResources().getColor(R.color.grey));
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.order_delete:
			/**删除订单*/
			new AlertDialog.Builder(this).setTitle("取消订单").setMessage("您确定要删除订单吗?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position) {
    				RequestParams params = new RequestParams();
    				params.put("id", order.getId());
    				HttpUtils.delOrder(new HttpErrorHandler() {
    					@Override
    					public void onRecevieSuccess(JSONObject json) {
    						setResult(Activity.RESULT_OK);
    						finish();
    						Tools.toast(ReserveDetailActivity.this, "订单删除成功");
    					}
    				}, params);
                }
            }).setNegativeButton("取消",null).show();
			break;
		case R.id.order_assess:
			/**评价*/
			startActivity(new Intent(ReserveDetailActivity.this, AssessActivity.class)
							.putExtra("order", order).putExtra("type", type));
			break;
		default:
			break;
		}
	}
}
