package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;

public class CarpoolSignUpActivity extends BaseActivity {
	private ImageButton  imageButton;
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
		imageButton=(ImageButton) findViewById(R.id.btn_back);
		btn_submit=(Button) findViewById(R.id.btn_carpool_submit);
		setListener(imageButton,btn_submit);
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
