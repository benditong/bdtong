package com.zykj.benditong.activity;

import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.AbstractHttpHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.AppModel;
import com.zykj.benditong.utils.Tools;

public class CarpoolNeederActivity extends BaseActivity {
	/**
	 * 拼车提交信息
	 */
	private EditText editText_orign, editText_destination,
			editText_depart_time, editText_persons, editText_cost,
			editText_name, editText_phone;
	private Button btn_submit;
	private ImageButton imageButton;
	/**
	 * 界面信息
	 */
	private String from_address,to_address,starttime,seats,price,name,phone;
	
	private Map<String, String> submit_data;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_needer);
		initView();
		
//		name=getSharedPreferenceValue(AppModel.NAME);
//		phone=getSharedPreferenceValue(AppModel.PHONE);
	}
	
	
	private void initView() {
		// TODO Auto-generated method stub
		editText_orign = (EditText) findViewById(R.id.user_origin);
		editText_destination = (EditText) findViewById(R.id.user_destination);
		editText_depart_time = (EditText) findViewById(R.id.user_departure_time);
		editText_persons = (EditText) findViewById(R.id.user_persons);
		editText_cost = (EditText) findViewById(R.id.user_car_cost);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_phone);

		imageButton = (ImageButton) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_carpool_submit);

		setListener(btn_submit, imageButton);

		Time time = new Time();
		time.setToNow();// 获得系统现在的时间
		int year = time.year;
		int month = time.month + 1;
		int day = time.monthDay;
		int hour = time.hour;
		String strTime = year + "-" + month + "-" + day + "-" + hour + ":00";
		editText_depart_time.setHint(strTime);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			startActivity(new Intent(this, CarpoolMainActivity.class));
			break;

		case R.id.btn_carpool_submit:
			submitCarpoolInfo();
			finish();
			break;

		}
	}

	private void submitCarpoolInfo() {
		if(editText_orign.length()<=0){
			Tools.toast(this, "出发地不能为空");
			return;
		}
		if(editText_destination.length()<=0){
			Tools.toast(this, "目的地不能为空");
			return;
		}
//		if(editText_depart_time.length()<=0){
//			Tools.toast(this, "出发时间不能为空");
//			return;
//		}
		if(editText_persons.length()<=0){
			Tools.toast(this, "人数不能为空");
			return;
		}
		if(editText_cost.length()<=0){
			Tools.toast(this, "预计费用不能为空");
			return;
		}
		if(editText_name.length()<=0){
			Tools.toast(this, "姓名不能为空");
			return;
		}
		if(editText_phone.length()<=0){
			Tools.toast(this, "联系电话不能为空");
			return;
		}
		addData();
//		submit_data=new HashMap<String, String>();
//		submit_data.put("from_address", from_address.trim());
//		submit_data.put("to_address", to_address.trim());
//		submit_data.put("starttime", starttime.trim());
//		submit_data.put("seats", seats.trim());
//		submit_data.put("price", price.trim());
//		submit_data.put("name", name.trim());
//		submit_data.put("phone", phone.trim());
		
	}


	private void addData() {
		// TODO Auto-generated method stub
		RequestParams params=new RequestParams();
		params.add(from_address, editText_orign.getText().toString());
		params.add(to_address, editText_destination.getText().toString());
		params.add(starttime, editText_depart_time.getText().toString());
		params.add(seats, editText_persons.getText().toString());
		params.add(price, editText_cost.getText().toString());
		params.add(name, editText_name.getText().toString());
		params.add(phone, editText_phone.getText().toString());
		
		HttpUtils.getCars(new AbstractHttpHandler() {
			
			@Override
			public void onJsonSuccess(com.alibaba.fastjson.JSONObject json) {
				// TODO Auto-generated method stub
				 String status = json.getString("status");
	                if ("1".equals(status)) {
	                    setResult(Activity.RESULT_OK);
	                    finish();
	                } else {
	                    Tools.toast(CarpoolNeederActivity.this, json.getString("msg"));
	                }
			}
		}, params);
	}

}
