package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.TextUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class ZhaoPinPublishActivity extends BaseActivity implements OnItemSelectedListener {
	private MyCommonTitle myCommonTitle;
	private EditText ed_position, ed_persons, ed_salary, ed_position_sort,
			ed_description, ed_contacts, ed_mobile, ed_comp_name,
			ed_comp_address, ed_comp_about;
	private Button btn_publish;
	private Spinner salaSpinner;
	private List<String> list;
	private ArrayAdapter<String> adapter;

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
		salaSpinner=(Spinner) findViewById(R.id.zp_salary_select);
		ed_persons = (EditText) findViewById(R.id.zp_persons);
		ed_position_sort=(EditText) findViewById(R.id.zp_zhiweisort);
		ed_description = (EditText) findViewById(R.id.zp_description);
		ed_contacts = (EditText) findViewById(R.id.zp_contacts);
		ed_mobile = (EditText) findViewById(R.id.zp_mobile);
		ed_comp_name = (EditText) findViewById(R.id.zp_company_name);
		ed_comp_address = (EditText) findViewById(R.id.zp_company_address);
		ed_comp_about = (EditText) findViewById(R.id.zp_company_about);

		list = new ArrayList<String>();
		list.add("0-1999");
		list.add("2000-2999");
		list.add("3000-3999");
		list.add("4000-4999");
		list.add("5000以上");
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		salaSpinner.setAdapter(adapter);
		salaSpinner.setOnItemSelectedListener(ZhaoPinPublishActivity.this);


		btn_publish = (Button) findViewById(R.id.zp_publish);
		setListener(btn_publish);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.zp_publish:
			if(StringUtil.isEmpty(ed_position.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "职位不能为空");return;
			}
			if(StringUtil.isEmpty(salaSpinner.toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "薪资区间不能为空");return;
			}
			if(StringUtil.isEmpty(ed_persons.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "招聘人数不能为空");return;
			}
			if(StringUtil.isEmpty(ed_position_sort.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "职位类别不能为空");return;
			}
			if(StringUtil.isEmpty(ed_description.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "职位描述不能为空");return;
			}
			if(StringUtil.isEmpty(ed_contacts.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "联系人不能为空");return;
			}	
			if(StringUtil.isEmpty(ed_mobile.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "联系电话不能为空");return;
			}
			if(!TextUtil.isMobile(ed_mobile.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "联系电话格式不正确");return;
			}
			if(StringUtil.isEmpty(ed_comp_name.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "公司名称不能为空");return;
			}
			if(StringUtil.isEmpty(ed_comp_address.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "公司地址不能为空");return;
			}
			if(StringUtil.isEmpty(ed_comp_about.getText().toString().trim())){
				Tools.toast(ZhaoPinPublishActivity.this, "公司简介不能为空");return;
			}
			//提交数据
			submitData();
			break;
		default:
			break;
		}
	}
	private void submitData() {
		RequestParams params=new RequestParams();
	
		params.put("title", ed_position.getText().toString().trim());
		params.put("pay", list.get(salaSpinner.getSelectedItemPosition()));//薪资区间
		params.put("num", ed_persons.getText().toString().trim());
		params.put("tid", ed_position_sort.getText().toString().trim());//职位类别
		params.put("intro", ed_description.getText().toString().trim());
		params.put("name", ed_contacts.getText().toString().trim());
		params.put("mobile", ed_mobile.getText().toString().trim());
		params.put("coname", ed_comp_name.getText().toString().trim());
		params.put("coaddress", ed_comp_address.getText().toString().trim());
		params.put("cointro", ed_comp_about.getText().toString().trim());
		
		HttpUtils.SubmitZhaoPinInfo(new HttpErrorHandler() {
			
			@Override
			public void onRecevieSuccess(JSONObject json) {
				Tools.toast(ZhaoPinPublishActivity.this, "招聘信息发布成功");
				finish();
			}
			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				super.onRecevieFailed(status, json);
				Tools.toast(ZhaoPinPublishActivity.this, "招聘信息发布失败");
			}
		}, params);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//salaSpinner= (String)arg0.getItemAtPosition(arg2);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}
