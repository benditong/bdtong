package com.zykj.benditong.activity;

import android.os.Bundle;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class AppExplainActivity extends BaseActivity {
	
	private MyCommonTitle myCommonTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_explain);
		myCommonTitle=(MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("应用说明");
		
	}
	
	

}
