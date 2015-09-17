package com.zykj.benditong.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ReserveAdapter extends CommonAdapter<Order> {
	
	private int type;

	public ReserveAdapter(Context context, int resource, List<Order> datas, int type) {
		super(context, resource, datas);
		this.type = type;
	}

	@Override
	public void convert(ViewHolder holder,final Order order) {
		order.setType(type);//0-restaurant,1-hotel,2-shop
		holder.setImageUrl(R.id.order_img, StringUtil.toString(order.getGoodsimg()), 15f)//图片
			.setText(R.id.restaurant_name, StringUtil.toString(order.getTitle()))//餐厅名
			.setText(R.id.restaurant_time, (0 == type ?"用餐时间：":"入住时间：")+StringUtil.toString(order.getIntime()))//用餐时间
			.setText(R.id.reserve_time, "预定时间："+StringUtil.toString(order.getAddtime()));//预定时间
		//state订单状态：0未付款1已付款,未消费2已消费3已退款4订单已取消
		TextView order_delete = holder.getView(R.id.order_delete);//删除订单
		TextView order_assess = holder.getView(R.id.order_assess);//评价
		order_delete.setVisibility("0".contains(order.getState())?View.VISIBLE:View.GONE);
		order_assess.setVisibility("1234".contains(order.getState())?View.VISIBLE:View.GONE);
		order_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				new AlertDialog.Builder(mContext).setTitle("取消订单").setMessage("您确定要删除订单吗?")
	                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialogInterface, int position) {
	    				RequestParams params = new RequestParams();
	    				params.put("id", order.getId());
	    				HttpUtils.delOrder(new HttpErrorHandler() {
	    					@Override
	    					public void onRecevieSuccess(JSONObject json) {
	    						Tools.toast(mContext, "订单删除成功");
	    						mDatas.remove(order);
	    						notifyDataSetChanged();
	    					}
	    				}, params);
	                }
	            }).setNegativeButton("取消",null).show();
			}
		});
		if("0".equals(order.getIscomment())){
			order_assess.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					mContext.startActivity(new Intent(mContext, AssessActivity.class).putExtra("order", order));
				}
			});
		}else{
			order_assess.setOnClickListener(null);
			order_assess.setBackgroundResource(R.drawable.bg_null_grey);
			order_assess.setText("已评价");
			order_assess.setTextColor(mContext.getResources().getColor(R.color.grey));
		}
	}
}