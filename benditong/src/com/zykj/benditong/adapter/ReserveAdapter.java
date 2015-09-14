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
		holder.setImageUrl(R.id.order_img, StringUtil.toString(order.getGoodsimg()), 15f)//图片
			.setText(R.id.restaurant_name, StringUtil.toString(order.getTitle()))//餐厅名
			.setText(R.id.restaurant_time, "￥"+StringUtil.toString(order.getIntime()))//用餐时间
			.setText(R.id.reserve_time, StringUtil.toString(order.getAddtime()));//预定时间
		//state订单状态：0未付款1已付款,未消费2已消费3已退款4订单已取消
		holder.getView(R.id.order_delete).setVisibility("0234".contains(state)?View.VISIBLE:View.GONE);
		holder.getView(R.id.order_assess).setVisibility("234".contains(state)?View.VISIBLE:View.GONE);
	}
}