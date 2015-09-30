package com.zykj.benditong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class ZhaoPinPublishActivity extends BaseActivity {
	private MyCommonTitle myCommonTitle;
	private EditText ed_position, ed_persons, ed_salary, ed_position_sort,
			ed_description, ed_contacts, ed_mobile, ed_comp_name,
			ed_comp_address, ed_comp_about;
	private Button btn_publish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhaopin_publish);
		initView();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("招聘发布");
		ed_position = (EditText) findViewById(R.id.zp_position);
		ed_persons = (EditText) findViewById(R.id.zp_persons);
		ed_description = (EditText) findViewById(R.id.zp_description);
		ed_contacts = (EditText) findViewById(R.id.zp_contacts);
		ed_mobile = (EditText) findViewById(R.id.zp_mobile);
		ed_comp_name = (EditText) findViewById(R.id.zp_company_name);
		ed_comp_address = (EditText) findViewById(R.id.zp_company_address);
		ed_comp_about = (EditText) findViewById(R.id.zp_company_about);

		btn_publish = (Button) findViewById(R.id.zp_publish);
		setListener(btn_publish);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.zp_publish:
			if(StringUtil.isEmpty(ed_position.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "职位不能为空");
			}
			
			break;
		default:
			break;
		}
	}
}
