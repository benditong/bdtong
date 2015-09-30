package com.zykj.benditong.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class ZhaoPinDetailsActivity extends BaseActivity {
	private MyCommonTitle myCommonTitle;
	private TextView tv_position, tv_publish_time, tv_salary, tv_comp_name,
			tv_comp_address, tv_persons, tv_require, tv_comp_about,
			tv_contacts, tv_mobile;
	private ImageView img_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhaopin_details);

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
		
		img_phone = (ImageView) findViewById(R.id.zp_details_phone);
	}
}
