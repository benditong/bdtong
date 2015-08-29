package com.zykj.benditong.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class CanyinInputActivity extends BaseActivity{

	private MyCommonTitle myCommonTitle;
	private String type;
	private TextView form1,form2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_canyin_input);
		type = getIntent().getStringExtra("type");
		
		initView();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("restaurant".equals(type)?"确认订餐":"确认预定");
		
		form1 = (TextView)findViewById(R.id.form1);
		form2 = (TextView)findViewById(R.id.form2);

		form1.setText("restaurant".equals(type)?"用餐时间":"入住时间");
		form2.setText("restaurant".equals(type)?"用餐人数":"入住人数");
	}
}