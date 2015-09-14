package com.zykj.benditong.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zykj.benditong.R;
import com.zykj.benditong.activity.CarpoolSignUpActivity;
import com.zykj.benditong.model.Car;

public class CarpoolAdapter extends BaseAdapter {
	
	LayoutInflater mLayoutInflater;
	List<Car> dataList;
	Context context;
	public CarpoolAdapter(Context context,List<Car> dataList) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.dataList=dataList;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Car getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.ui_item_carpool_details,null);
			holder.tv_orign = (TextView) convertView
					.findViewById(R.id.textView_orign_2);
			holder.tv_destination = (TextView) convertView
					.findViewById(R.id.textView_destination_2);
			holder.tv_departure_time = (TextView) convertView
					.findViewById(R.id.textView_departure_time_2);
			holder.tv_remain_seats = (TextView) convertView
					.findViewById(R.id.textView_remain_seats_2);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.textView_price_2);
			holder.btn_sign_up = (Button) convertView
					.findViewById(R.id.btn_carpool_sign_up);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_orign.setText((dataList).get(position).getFrom_address());
		holder.tv_destination.setText((dataList).get(position).getTo_address());
		holder.tv_departure_time.setText((dataList).get(position).getStarttime());
		holder.tv_remain_seats.setText((dataList).get(position).getSeat());
		holder.tv_price.setText((dataList).get(position).getPrice());
		
		holder.btn_sign_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context, CarpoolSignUpActivity.class);
				intent.putExtra("car", (dataList).get(position));
				intent.putExtra("tid", (dataList).get(position).getId());
				context.startActivity(intent.putExtra("car", dataList.get(position)));
			}
		});

		return convertView;
	}

	public final class ViewHold {
		public TextView tv_orign;
		public TextView tv_destination;
		public TextView tv_departure_time;
		public TextView tv_remain_seats;
		public TextView tv_price;
		public Button btn_sign_up;
	}
}
