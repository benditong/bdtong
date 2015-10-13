package com.zykj.benditong.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.TextUtil;
import com.zykj.benditong.utils.Tools;

public class CarpoolOwnerActivity extends BaseActivity {
	private EditText editText_orign, editText_destination,
			editText_depart_time, editText_seats, editText_model,
			editText_name, editText_phone;
	private ImageButton imageButton;
	private Button btn_submit;
    private String mobileCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_owner);
		initView();

	}

	private void initView() {
		editText_orign = (EditText) findViewById(R.id.user_origin);
		editText_destination = (EditText) findViewById(R.id.user_destination);
		editText_depart_time = (EditText) findViewById(R.id.user_departure_time);
		editText_depart_time.setInputType(InputType.TYPE_NULL);
		editText_seats = (EditText) findViewById(R.id.user_remain_seats);
		editText_model = (EditText) findViewById(R.id.user_car_model);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_phone);
		imageButton = (ImageButton) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_carpool_submit);
		setListener(editText_depart_time,imageButton, btn_submit);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.user_departure_time:
			CommonUtils.showDateTimePicker(this, editText_depart_time);
			break;
		case R.id.btn_back:
			finish();
			break;

		case R.id.btn_carpool_submit:
			submitCarpoolInfo();
			break;
		}

	}

	private void submitCarpoolInfo() {
		mobileCode=editText_phone.getText().toString().trim();
		if (editText_orign.getText().length() <= 0) {
			Tools.toast(this, "出发地不能为空");
		}else if(editText_destination.getText().length() <= 0) {
			Tools.toast(this, "目的地不能为空");
		}else if(editText_depart_time.getText().toString().length()<=0){
			Tools.toast(this, "出发时间不能为空");
		}else if(editText_seats.getText().toString().length() <= 0) {
			Tools.toast(this, "剩余座位不能为空");
		}else if(editText_model.getText().length() <= 0) {
			Tools.toast(this, "车型不能为空");
		}else if(editText_name.getText().length() <= 0) {
			Tools.toast(this, "联系人不能为空");
		}else if(!TextUtil.isMobile(mobileCode)){
        	Tools.toast(CarpoolOwnerActivity.this, "手机号格式不对");
        }else {
        	addData();
		}
	}

	private void addData() {
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
			super.onRecevieFailed(status, json);
			Tools.toastMessage(CarpoolOwnerActivity.this, "提交失败");
		}
		}, params);

	}

}
