package com.zykj.benditong.activity;

import java.util.Map;

import org.apache.http.Header;

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
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.Tools;

public class CarpoolOwnerActivity extends BaseActivity {
	private EditText editText_orign, editText_destination,
			editText_depart_time, editText_seats, editText_model,
			editText_name, editText_phone;
	private ImageButton imageButton;
	private Button btn_submit;

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

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			startActivity(new Intent(this, CarpoolMainActivity.class));
			break;

		case R.id.btn_carpool_submit:
			submitCarpoolInfo();
			break;
		}

	}

	private void submitCarpoolInfo() {
		if (editText_orign.getText().length() <= 0) {
			Tools.toast(this, "出发地不能为空");
		}
		if (editText_destination.getText().length() <= 0) {
			Tools.toast(this, "目的地不能为空");
		}
		if(editText_depart_time.getText().toString().length()<=0){
			Tools.toast(this, "出发时间不能为空");
			return;
		}
		if (editText_seats.getText().toString().length() <= 0) {
			Tools.toast(this, "剩余座位不能为空");
		}
		if (editText_model.getText().length() <= 0) {
			Tools.toast(this, "车型不能为空");
		}
		if (editText_name.getText().length() <= 0) {
			Tools.toast(this, "联系人不能为空");
		}
		if (editText_phone.getText().toString().length() <= 0) {
			Tools.toast(this, "联系电话不能为空");
		}
		addData();
	}

	private void addData() {
		// TODO Auto-generated method stub
		RequestParams params=new RequestParams();
		params.put("from_address", editText_orign.getText().toString());
		params.put("to_address", editText_destination.getText().toString());
		params.put("starttime", editText_depart_time.getText().toString());
		params.put("seat", editText_seats.getText().toString());
		params.put("car", editText_model.getText().toString());
		params.put("name", editText_name.getText().toString());
		params.put("mobile", editText_phone.getText().toString());
		/**
		 * 我是车主
		 */
		HttpUtils.offerCar(new HttpErrorHandler() {
			
			@Override
			public void onRecevieSuccess(JSONObject json) {
				Tools.toastMessage(CarpoolOwnerActivity.this, "提交成功");
				finish();
			}
		@Override
		public void onRecevieFailed(String status, JSONObject json) {
			// TODO Auto-generated method stub
			super.onRecevieFailed(status, json);
			Tools.toastMessage(CarpoolOwnerActivity.this, "提交失败");
		}
		}, params);

	}

}
