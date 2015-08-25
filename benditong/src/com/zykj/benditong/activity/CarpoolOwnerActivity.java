package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.R.id;

public class CarpoolOwnerActivity extends BaseActivity {
	private EditText editText_orign,editText_destination,editText_depart_time,editText_seats,
    editText_model,editText_name,editText_phone;
	private ImageButton  imageButton;
	private Button  btn_submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_owner);
		initView();
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		editText_orign=(EditText) findViewById(R.id.user_origin);
		editText_destination=(EditText) findViewById(R.id.user_destination);
		editText_depart_time=(EditText) findViewById(R.id.user_departure_time);
		editText_seats=(EditText) findViewById(R.id.user_remain_seats);
		editText_model=(EditText) findViewById(R.id.user_car_model);
		editText_name=(EditText) findViewById(R.id.user_name);
		editText_phone=(EditText) findViewById(R.id.user_phone);
		
		imageButton=(ImageButton) findViewById(R.id.btn_back);
		btn_submit=(Button) findViewById(R.id.btn_carpool_submit);
		setListener(imageButton,btn_submit);
		
		Time time=new Time();
		time.setToNow();//获得系统现在的时间
		int year=time.year;
		int month=time.month+1;
		int day=time.monthDay;
		int hour=time.hour;
		String strTime=year+"-"+month+"-"+day+"-"+hour+":00";
		editText_depart_time.setHint(strTime);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			startActivity(new Intent(this, CarpoolMainActivity.class));
			break;

		case R.id.btn_carpool_submit:
			
			break;
		}
		
	
	}
	

	
}
