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
	private String tid;
	private TextView textView_orign, textView_destination,
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
		car = (Car) getIntent().getSerializableExtra("car");
		tid = "1";
		initView();
	}

	private void initView() {
		textView_orign = (TextView) findViewById(R.id.textView_orign);
		textView_destination = (TextView) findViewById(R.id.textView_destination);
		textView_depart_time = (TextView) findViewById(R.id.textView_departure_time);
		textView_persons = (TextView) findViewById(R.id.textView_remain_seats);
		editText_sign_persons = (EditText) findViewById(R.id.user_sign_up_num);
		textView_cost = (TextView) findViewById(R.id.textView_cost);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_mobile);
		imageButton = (ImageButton) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_carpool_submit);

		textView_orign.setText(car.getFrom_address());
		textView_destination.setText(car.getTo_address());
		textView_depart_time.setText(car.getStarttime());
		textView_persons.setText(car.getSeat());

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
			Tools.toastMessage(CarpoolSignUpActivity.this, "报名人数不能为空");
		}
		if (editText_name.getText().toString().trim().length() <= 0) {
			Tools.toast(this, "联系人不能为空");
		}
		if (editText_phone.getText().toString().trim().length() <= 0) {
			Tools.toast(this, "联系电话不能为空");
		}
	
		if(Integer.parseInt(editText_sign_persons.getText().toString())>Integer.parseInt(textView_persons.getText().toString())){
			Tools.toastMessage(CarpoolSignUpActivity.this, "你预定的座位已超出");
		}else {
			addData();
		}
	}

	/**
	 * 添加拼车待输入的信息
	 */
	private void addData() {
		RequestParams params = new RequestParams();
		params.put("tid", car.getId());
		params.put("seat", editText_sign_persons.getText().toString().trim());
		Float tatal=(float) (Integer.parseInt(car.getPrice().toString())*Integer.parseInt(editText_sign_persons.getText().toString()));
		textView_cost.setText(String.format("￥%.2f",  tatal));
		params.put("name", editText_name.getText().toString().trim());
		params.put("mobile", editText_phone.getText().toString().trim());
		/**
		 * 获取拼车信息详情并且报名
		 */
		HttpUtils.postcaroder(new HttpErrorHandler() {

			@Override
			public void onRecevieSuccess(JSONObject json) {
				Tools.toastMessage(CarpoolSignUpActivity.this, "报名成功");
				//textView_persons.setText("Integer.parseInt(textView_persons.getText().toString())-(Integer.parseInt(editText_sign_persons.getText().toString())");
				finish();
			}

			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				super.onRecevieFailed(status, json);
				Tools.toastMessage(CarpoolSignUpActivity.this, "报名失败");
			}
		}, params);
	}
}
