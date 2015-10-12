package com.zykj.benditong.activity;

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
import com.zykj.benditong.utils.TextUtil;
import com.zykj.benditong.utils.Tools;

public class CarpoolSignUpActivity extends BaseActivity {
	/**
	 * 报名提交信息
	 */
	private TextView textView_orign, textView_destination,
			textView_depart_time, textView_remain_seats;
	private EditText editText_sign_persons, editText_name, editText_phone;
	private String mobileCode;
	private ImageButton imageButton;
	private Button btn_submit;
	private Car car;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_sign_up);
		car = (Car) getIntent().getSerializableExtra("car");
		initView();
	}

	private void initView() {
		textView_orign = (TextView) findViewById(R.id.textView_orign);
		textView_destination = (TextView) findViewById(R.id.textView_destination);
		textView_depart_time = (TextView) findViewById(R.id.textView_departure_time);
		textView_remain_seats = (TextView) findViewById(R.id.textView_remain_seats);
		editText_sign_persons = (EditText) findViewById(R.id.user_sign_up_num);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_mobile);
		imageButton = (ImageButton) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_carpool_submit);
		textView_orign.setText(car.getFrom_address());
		textView_destination.setText(car.getTo_address());
		textView_depart_time.setText(car.getStarttime());
		textView_remain_seats.setText(car.getRemain());

		setListener(imageButton, btn_submit);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_carpool_submit:
			submitRegistInfo();
			break;
		}
	}
	private void submitRegistInfo() {
		mobileCode=editText_phone.getText().toString().trim();
		if (editText_sign_persons.getText().toString().trim().length() <= 0) {
			Tools.toastMessage(CarpoolSignUpActivity.this, "报名人数不能为空");
		}else if (editText_name.getText().toString().trim().length() <= 0) {
			Tools.toast(this, "联系人不能为空");
		}
		else if(!TextUtil.isMobile(mobileCode)) {
			Tools.toast(CarpoolSignUpActivity.this, "手机格式不正确");
		}else if(Integer.parseInt(editText_sign_persons.getText().toString())>Integer.parseInt(textView_remain_seats.getText().toString())){
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
		//Float tatal=(float) (Integer.parseInt(car.getPrice().toString())*Integer.parseInt(editText_sign_persons.getText().toString()));
		//textView_cost.setText(String.format("￥%.2f",  tatal));
		params.put("name", editText_name.getText().toString().trim());
		params.put("mobile", editText_phone.getText().toString().trim());
		/**
		 * 获取拼车信息详情并且报名
		 */
		HttpUtils.postcaroder(new HttpErrorHandler() {

			@Override
			public void onRecevieSuccess(JSONObject json) {
				Tools.toastMessage(CarpoolSignUpActivity.this, "报名成功");
				finish();
				//textView_persons.setText("Integer.parseInt(textView_persons.getText().toString())-(Integer.parseInt(editText_sign_persons.getText().toString())");
				//upData();
//				int orderSeats=Integer.parseInt(editText_sign_persons.getText().toString().trim());
//				int remainSeats=Integer.parseInt(textView_remain_seats.getText().toString().trim());
//				remainSeats-=orderSeats;
//				Intent intent=new Intent(CarpoolSignUpActivity.this, CarpoolMainActivity.class);
//				intent.putExtra("remain", String.valueOf(remainSeats)) ;
//				startActivity(intent);
			}
			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				super.onRecevieFailed(status, json);
				Tools.toastMessage(CarpoolSignUpActivity.this, "报名失败");
			}
		}, params);
	}
}
