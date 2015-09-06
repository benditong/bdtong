package com.zykj.benditong.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.TextUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class CanyinInputActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private String type,formid;
	private TextView form1,form2;
	private EditText order_time,order_people,order_pay,order_name,order_mobile;
	private Button reserve_go;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_canyin_input);
		type = getIntent().getStringExtra("type");
		formid = getIntent().getStringExtra("id");
		
		initView();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("restaurant".equals(type)?"确认订餐":"确认预定");
		
		form1 = (TextView)findViewById(R.id.form1);
		form2 = (TextView)findViewById(R.id.form2);
		form1.setText("restaurant".equals(type)?"用餐时间":"入住时间");
		form2.setText("restaurant".equals(type)?"用餐人数":"入住人数");

		order_time = (EditText)findViewById(R.id.order_time);
		order_time.setInputType(InputType.TYPE_NULL);
		order_people = (EditText)findViewById(R.id.order_people);
		order_pay = (EditText)findViewById(R.id.order_pay);
		order_name = (EditText)findViewById(R.id.order_name);
		order_mobile = (EditText)findViewById(R.id.order_mobile);
		reserve_go = (Button)findViewById(R.id.reserve_go);
		
		setListener(order_time,reserve_go);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.order_time:
			CommonUtils.showDateTimePicker(this, order_time);
			break;
		case R.id.reserve_go:
			String time = order_time.getText().toString().trim();
			String people = order_people.getText().toString().trim();
			String pay = order_pay.getText().toString().trim();
			String name = order_name.getText().toString().trim();
			String mobile = order_mobile.getText().toString().trim();
			if(StringUtil.isEmpty(time)){
				Tools.toast(this, "restaurant".equals(type)?"用餐时间不能为空!":"入住时间不能为空!");
			}else if(StringUtil.isEmpty(people) || Integer.valueOf(people) <= 0){
				Tools.toast(this, "restaurant".equals(type)?"用餐人数无效!":"入住人数无效!");
			}else if(StringUtil.isEmpty(pay) || Integer.valueOf(people) <= 0){
				Tools.toast(this, "预计消费金额无效!");
			}else if(StringUtil.isEmpty(name)){
				Tools.toast(this, "用餐时间不能为空!");
			}else if(!TextUtil.isMobile(mobile)){
				Tools.toast(this, "联系方式不对!");
			}else{
				//type=restaurant&tid=&uid=&intime=&innum=&inprice=&name=&mobile=
				RequestParams params = new RequestParams();
				params.put("type", type);
				params.put("tid", formid);
				params.put("uid", BaseApp.getModel().getUserid());
				params.put("intime", time);
				params.put("innum", people);
				params.put("inprice", pay);
				params.put("name", name);
				params.put("mobile", mobile);
				HttpUtils.submit(new HttpErrorHandler() {
					@Override
					public void onRecevieSuccess(JSONObject json) {
						Tools.toast(CanyinInputActivity.this, "预定成功!");
						finish();
					}
					@Override
					public void onRecevieFailed(String status, JSONObject json) {
						Tools.toast(CanyinInputActivity.this, "预定失败!");
					}
				}, params);
			}
			break;
		default:
			break;
		}
	}
}