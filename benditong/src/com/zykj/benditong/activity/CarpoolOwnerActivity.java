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

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.R.id;
import com.zykj.benditong.http.AbstractHttpHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.Tools;

public class CarpoolOwnerActivity extends BaseActivity {
	private EditText editText_orign, editText_destination,
			editText_depart_time, editText_seats, editText_model,
			editText_name, editText_phone;
	private ImageButton imageButton;
	private Button btn_submit;

	/**
	 * 界面信息
	 */
	private String from_address, to_address, starttime, seats, car, name,
			phone;

	private Map<String, String> submit_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_owner);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		editText_orign = (EditText) findViewById(R.id.user_origin);
		editText_destination = (EditText) findViewById(R.id.user_destination);
		editText_depart_time = (EditText) findViewById(R.id.user_departure_time);
		editText_seats = (EditText) findViewById(R.id.user_remain_seats);
		editText_model = (EditText) findViewById(R.id.user_car_model);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_phone);

		imageButton = (ImageButton) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_carpool_submit);
		setListener(imageButton, btn_submit);

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
		// TODO Auto-generated method stub
		if (editText_orign.length() <= 0) {
			Tools.toast(this, "出发地不能为空");
		}
		if (editText_destination.length() <= 0) {
			Tools.toast(this, "目的地不能为空");
		}
		if (editText_seats.length() <= 0) {
			Tools.toast(this, "剩余座位不能为空");
		}
		if (editText_model.length() <= 0) {
			Tools.toast(this, "车型不能为空");
		}
		if (editText_name.length() <= 0) {
			Tools.toast(this, "联系人不能为空");
		}
		if (editText_phone.length() <= 0) {
			Tools.toast(this, "联系电话不能为空");
		}
		addData();
	}

	private void addData() {
		// TODO Auto-generated method stub
		RequestParams params=new RequestParams();
		params.add(from_address, editText_orign.getText().toString());
		params.add(to_address, editText_destination.getText().toString());
		params.add(starttime, editText_depart_time.getText().toString());
		params.add(seats, editText_seats.getText().toString());
		params.add(car, editText_model.getText().toString());
		params.add(name, editText_name.getText().toString());
		params.add(phone, editText_phone.getText().toString());
		
		HttpUtils.getCars(new AbstractHttpHandler() {
			@Override
			public void onJsonSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				 String status = json.getString("status");
	                if ("1".equals(status)) {
	                    setResult(Activity.RESULT_OK);
	                    finish();
	                } else {
	                    Tools.toast(CarpoolOwnerActivity.this, json.getString("msg"));
	                }
			}
		}, params);
	}

}
