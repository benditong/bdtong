package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;

public class CarpoolSignUpActivity extends BaseActivity {
	/**
	 * 报名提交信息
	 */
	private EditText editText_orign, editText_destination,
			editText_depart_time, editText_persons, editText_sign_persons,
			editText_cost, editText_name, editText_phone;
	private ImageButton imageButton;
	private Button btn_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_sign_up);
		initView();
	}

	private void initView() {
		editText_orign = (EditText) findViewById(R.id.textView_orign);
		editText_destination = (EditText) findViewById(R.id.textView_destination);
		editText_depart_time = (EditText) findViewById(R.id.textView_departure_time);
		editText_persons = (EditText) findViewById(R.id.textView_remain_seats);
		editText_sign_persons = (EditText) findViewById(R.id.user_sign_up_num);
		editText_cost = (EditText) findViewById(R.id.textView_price);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_mobile);		
		
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
			submitRegistInfo();
			break;
		}

	}

	private void submitRegistInfo() {
		// TODO Auto-generated method stub
		
	}
	
	
}
