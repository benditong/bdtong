package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.model.ZhaoPin;
import com.zykj.benditong.view.MyCommonTitle;

public class ZhaoPinDetailsActivity extends BaseActivity {
	
	private MyCommonTitle myCommonTitle;
	private TextView tv_position, tv_publish_time, tv_salary, tv_comp_name,
			tv_comp_address, tv_persons, tv_require, tv_comp_about,
			tv_contacts, tv_mobile;
	private ImageView img_phone;
	private ZhaoPin zhaoPin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhaopin_details);
		zhaoPin = (ZhaoPin) getIntent().getSerializableExtra("zhaoPin");
		initView();
	}

	private void initView() {

		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("招聘详情");
		
		tv_position = (TextView) findViewById(R.id.zp_details_position);
		tv_publish_time = (TextView) findViewById(R.id.zp_details_publish_time);
		tv_salary = (TextView) findViewById(R.id.zp_details_salary);
		tv_comp_name = (TextView) findViewById(R.id.zp_details_comp_name);
		tv_comp_address = (TextView) findViewById(R.id.zp_details_comp_address);
		tv_persons = (TextView) findViewById(R.id.zp_details_persons);
		tv_require = (TextView) findViewById(R.id.zp_details_require);
		tv_comp_about = (TextView) findViewById(R.id.zp_details_comp_about);
		tv_contacts = (TextView) findViewById(R.id.zp_details_contacts);
		tv_mobile = (TextView) findViewById(R.id.zp_details_mobile);
		
		tv_position.setText(zhaoPin.getTitle());
		tv_publish_time.setText(zhaoPin.getAddtime());
		tv_salary.setText(zhaoPin.getPay()+"元");
		tv_comp_name.setText(zhaoPin.getConame());
		tv_comp_address.setText(zhaoPin.getCoaddress());
		tv_persons.setText(zhaoPin.getNum());
		tv_require.setText(zhaoPin.getIntro());
		tv_comp_about.setText(zhaoPin.getConintro());
		tv_contacts.setText(zhaoPin.getName());
		tv_mobile.setText(zhaoPin.getMobile());
		
		img_phone = (ImageView) findViewById(R.id.zp_details_phone);
		img_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tv_mobile.getText().toString().trim())));
			}
		});
	
	}
}
