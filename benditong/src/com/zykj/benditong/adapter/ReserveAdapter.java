package com.zykj.benditong.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.zykj.benditong.R;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.StringUtil;

public class ReserveAdapter extends CommonAdapter<Order> {

	public ReserveAdapter(Context context, int resource, List<Order> datas) {
		super(context, resource, datas);
	}

	@Override
	public void convert(ViewHolder holder,final Order order) {
		String state = order.getState();
		holder.setImageUrl(R.id.order_img, StringUtil.toString(order.getGoodsimg()), 10f)
			.setText(R.id.restaurant_name, StringUtil.toString(order.getGoodsname()))
			.setText(R.id.restaurant_time, "￥"+StringUtil.toString(order.getAddtime()))
			.setText(R.id.reserve_time, StringUtil.toString(order.getIntime()));
		holder.getView(R.id.order_delete).setVisibility("2".equals(state)?View.GONE:View.VISIBLE);
		holder.getView(R.id.order_assess).setVisibility(("1".equals(state) || "3".equals(state))?View.GONE:View.VISIBLE);
	}
}
