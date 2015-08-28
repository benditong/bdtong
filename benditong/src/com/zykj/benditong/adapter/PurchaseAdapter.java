package com.zykj.benditong.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zykj.benditong.R;
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
			.setText(R.id.product_name, StringUtil.toString(order.getGoodsname()))
			.setText(R.id.product_price, "￥"+StringUtil.toString(order.getInprice()))
			.setText(R.id.product_content, StringUtil.toString(order.getName()))
			.setText(R.id.product_num, "x"+StringUtil.toString(order.getInnum()))
			.setText(R.id.order_cancel, "3".equals(state)?"删除订单":"取消订单")
			.setText(R.id.order_pay, "1".equals(state)?"付款":"评价");
		TextView total_price = holder.getView(R.id.total_price);
		Float tatal = Float.parseFloat(order.getInprice())*Integer.parseInt(order.getInnum());
		total_price.setText(Html.fromHtml("<font color=#adadb0></font><font color=#000000>"+String.format("￥%.2f", tatal)+"</font>"));
		holder.getView(R.id.order_cancel).setVisibility("2".equals(state)?View.GONE:View.VISIBLE);
		holder.getView(R.id.order_cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if("3".equals(order.getState()) || "0".equals(order.getState())){
					Tools.toast(mContext, "删除订单");
				}else{
					Tools.toast(mContext, "取消订单");
				}
			}
		});;
		holder.getView(R.id.order_pay).setVisibility(("1".equals(state) || "3".equals(state))?View.GONE:View.VISIBLE);
		holder.getView(R.id.order_pay).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				if("0".equals(order.getState()) || "0".equals(order.getState())){
					Tools.toast(mContext, "付款");
				}else{
					Tools.toast(mContext, "评价");
				}
			}
		});;
		
	}

}
