package com.zykj.benditong.activity;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Car;
import com.zykj.benditong.utils.Tools;

public class CarpoolSignUpActivity extends BaseActivity {
	/**
	 * 报名提交信息
	 */
	private TextView textView_orign, textView_destination,tv_price,
			textView_depart_time, textView_persons, textView_cost;
	private EditText editText_sign_persons, editText_name, editText_phone;
	private ImageButton imageButton;
	private Button btn_submit;
	private Car car;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_sign_up);
		car =  (Car) getIntent().getSerializableExtra("car");

		initView();
	}

	private void initView() {
		textView_orign = (TextView) findViewById(R.id.textView_orign);
		textView_destination = (TextView) findViewById(R.id.textView_destination);
		textView_depart_time = (TextView) findViewById(R.id.textView_departure_time);
		textView_persons = (TextView) findViewById(R.id.textView_remain_seats);
		editText_sign_persons = (EditText) findViewById(R.id.user_sign_up_num);
		textView_cost = (TextView) findViewById(R.id.textView_cost);
		//tv_price=(TextView) findViewById(R.id.textView_price_2);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_mobile);
		imageButton = (ImageButton) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_carpool_submit);

//		int sum=0,a=0,b=0;
//		String str1=editText_sign_persons.getText().toString().trim();
//		String str2=tv_price.getText().toString().trim();
//		a=Integer.parseInt(str1);
//		b=Integer.parseInt(str2);
//		sum=a*b;
		//sum=String.
		textView_orign.setText(car.getFrom_address());
		textView_destination.setText(car.getTo_address());
		textView_depart_time.setText(car.getStarttime());
		textView_persons.setText(car.getSeat());
		textView_cost.setText(car.getPrice());

		setListener(imageButton, btn_submit);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			startActivity(new Intent(this, CarpoolMainActivity.class));
			break;
		case R.id.btn_carpool_submit:
			submitRegistInfo();
			break;
		}

	}

	private void submitRegistInfo() {
		if (editText_sign_persons.getText().toString().trim().length() <= 0) {
			Tools.toastMessage(CarpoolSignUpActivity.this, "报名人数至少为一人");
		}
		if (editText_name.getText().toString().trim().length() <= 0) {
			Tools.toast(this, "联系人不能为空");
		}
		if (editText_phone.getText().toString().trim().length() <= 0) {
			Tools.toast(this, "联系电话不能为空");
		}
		addData();
	}

	/**
	 * 添加拼车待输入的信息
	 */
	private void addData() {
		RequestParams params = new RequestParams();
		params.put("orderSeat", editText_sign_persons.getText().toString());
		params.put("name", editText_name.getText().toString());
		params.put("mobile", editText_phone.getText().toString());
		/**
		 * 获取拼车信息详情并且报名
		 */
//		HttpUtils.getInfo(new HttpErrorHandler() {
//
//			@Override
//			public void onRecevieSuccess(JSONObject json) {
//				Tools.toastMessage(CarpoolSignUpActivity.this, "报名成功");
//			}
//
//			@Override
//			public void onRecevieFailed(String status, JSONObject json) {
//				super.onRecevieFailed(status, json);
//				Tools.toastMessage(CarpoolSignUpActivity.this, "报名失败");
//			}
//		}, params);
	}
}
