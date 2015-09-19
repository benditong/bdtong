package com.zykj.benditong.activity;

import java.util.Map;

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
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.TextUtil;
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
	private String mobileCode;
	private Map<String, String> submit_data;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_needer);
		initView();

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

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
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
		if(editText_orign.getText().length()<=0){
			Tools.toast(this, "出发地不能为空");
			return;
		}else if(editText_destination.getText().length()<=0){
			Tools.toast(this, "目的地不能为空");
			return;
		}else if(editText_depart_time.getText().toString().length()<=0){
			Tools.toast(this, "出发时间不能为空");
			return;
		}else if(editText_persons.getText().toString().length()<=0){
			Tools.toast(this, "人数不能为空");
			return;
		}else if(editText_cost.getText().toString().length()<=0){
			Tools.toast(this, "预计费用不能为空");
			return;
		}else if(editText_name.getText().length()<=0){
			Tools.toast(this, "姓名不能为空");
			return;
		}else if(!TextUtil.isMobile(mobileCode)){
        	Tools.toast(CarpoolNeederActivity.this, "手机号格式不对");
        	return;
        }else {
        	addData();
		}
	}
	private void addData() {
		// TODO Auto-generated method stub
		RequestParams params=new RequestParams();
		params.put("from_address", editText_orign.getText().toString());
		params.put("to_address", editText_destination.getText().toString());
		params.put("starttime", editText_depart_time.getText().toString());
		params.put("seat", editText_persons.getText().toString());
		params.put("price", editText_cost.getText().toString());
		params.put("name", editText_name.getText().toString());
		params.put("mobile", editText_phone.getText().toString());
		/*我要拼车*/
		HttpUtils.needcar(new HttpErrorHandler() {
			
			@Override
			public void onRecevieSuccess(JSONObject json) {
				Tools.toast(CarpoolNeederActivity.this, "提交成功！");
				finish();
			}
			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				super.onRecevieFailed(status, json);
				Tools.toast(CarpoolNeederActivity.this, "提交失败！");
			}
		},params);
	}

}
