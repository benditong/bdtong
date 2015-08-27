package com.zykj.benditong.adapter;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import com.zykj.benditong.R;
import com.zykj.benditong.activity.CarpoolMainActivity;
import com.zykj.benditong.activity.CarpoolSignUpActivity;
import com.zykj.benditong.model.Car;

import android.R.anim;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CarpoolAdapter extends BaseAdapter {
	
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<Car> list;
	CarpoolMainActivity mCarpoolMainActivity;

	public CarpoolAdapter(Context context,List<Car> list) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Car car=list.get(position);
		
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.ui_carpool_details,null);
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
		holder.tv_orign.setText(car.getFrom_address());
		holder.tv_destination.setText(car.getTo_adderess());
		holder.tv_departure_time.setText(car.getStarttime());
		holder.tv_remain_seats.setText(car.getSeat());
		holder.tv_price.setText(car.getPrice());
		
		holder.btn_sign_up.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(mCarpoolMainActivity, CarpoolSignUpActivity.class);
				mContext.startActivity(intent);

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
