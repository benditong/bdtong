package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class BianMinActivity extends BaseActivity {
	private MyCommonTitle myCommonTitle;
	private LinearLayout baiduLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_bianmin);

		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("便民");

		initView();
	}

	private void initView() {
		baiduLayout = (LinearLayout) findViewById(R.id.im_p1baidu);

		setListener(baiduLayout);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.im_p1baidu:

			break;

		default:
			break;
		}
	}
}
