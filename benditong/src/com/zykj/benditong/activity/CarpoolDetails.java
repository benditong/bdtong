package com.zykj.benditong.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.model.Car;


public class CarpoolDetails extends BaseActivity{
	
	private Car car;
	private TextView  tv_orign,tv_destination,tv_starttime,tv_seats,tv_price;
	private ListView  car_list;
	private Button  btn_submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_sign_up);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_orign=(TextView) findViewById(R.id.textView_orign);
		tv_destination=(TextView) findViewById(R.id.textView_destination);
		tv_starttime=(TextView) findViewById(R.id.textView_departure_time);
		tv_seats=(TextView) findViewById(R.id.textView_remain_seats);
		tv_price=(TextView) findViewById(R.id.textView_price);
		btn_submit=(Button) findViewById(R.id.btn_carpool_submit);
	}
	
}

