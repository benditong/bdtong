package com.zykj.benditong.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.AssessActivity;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;

public class PurchaseAdapter extends CommonAdapter<Order> {

	public PurchaseAdapter(Context context, int resource, List<Order> datas) {
		super(context, resource, datas);
	}

	@Override
	public void convert(ViewHolder holder,final Order order) {
		String state = order.getState();
		holder.setText(R.id.res_name, StringUtil.toString(order.getTitle())).setText(R.id.order_state, "0".equals(state)?"未付款":"1".equals(state)?
				"未消费":"2".equals(state)?"已消费":"3".equals(state)?"已退款":"订单取消")//0未付款1已付款,未消费2已消费3已退款4订单已取消
			.setImageUrl(R.id.order_img, StringUtil.toString(order.getGoodsimg()), 10f)
			.setText(R.id.product_name, StringUtil.toString(order.getTitle()))
			.setText(R.id.product_price, "￥"+StringUtil.toString(order.getInprice()))
			.setText(R.id.product_content, StringUtil.toString(order.getName()))
			.setText(R.id.product_num, "x"+StringUtil.toString(order.getInnum()));
		final TextView order_cancel = holder.getView(R.id.order_cancel);//删除订单
		TextView order_pay = holder.getView(R.id.order_pay);//评价
		order_cancel.setText("1".contains(order.getState())?"取消订单":"删除订单");
		order_pay.setText("0".contains(order.getState())?"付款":"评价");
		order_pay.setVisibility("02".contains(order.getState())?View.VISIBLE:View.GONE);
		
		TextView total_price = holder.getView(R.id.total_price);
		Float tatal = Float.parseFloat(order.getInprice())*Integer.parseInt(order.getInnum());
		total_price.setText(Html.fromHtml("<font color=#adadb0></font><font color=#000000>"+String.format("￥%.2f", tatal)+"</font>"));
		order_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String text = order_cancel.getText().toString();
				new AlertDialog.Builder(mContext).setTitle(text).setMessage("您确定要"+text+"吗?")
                	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialogInterface, int position) {
						RequestParams params = new RequestParams();
						params.put("id", order.getId());
						if("1".contains(order.getState())){
							params.put("state", "4");
							HttpUtils.updateorder(new HttpErrorHandler() {
								@Override
								public void onRecevieSuccess(JSONObject json) {
									Tools.toast(mContext, "订单取消成功");
									mDatas.remove(order);
									notifyDataSetChanged();
								}
							}, params);
						}else{
							HttpUtils.delOrder(new HttpErrorHandler() {
								@Override
								public void onRecevieSuccess(JSONObject json) {
									Tools.toast(mContext, "订单删除成功");
									mDatas.remove(order);
									notifyDataSetChanged();
								}
							}, params);
						}
		            }
		        }).setNegativeButton("取消",null).show();
			}
		});
		holder.getView(R.id.order_pay).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if("2".equals(order.getState())){
					mContext.startActivity(new Intent(mContext, AssessActivity.class).putExtra("order", order));
				}else{
					//mContext.startActivity(new Intent(mContext, AssessActivity.class).putExtra("order", order));
					Tools.toast(mContext, "付款");
				}
			}
		});
	}
}
