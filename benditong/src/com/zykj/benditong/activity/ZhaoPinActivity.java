package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.fragment.ZhaoPinFragment;
import com.zykj.benditong.view.HorizontalListView;
import com.zykj.benditong.view.MyCommonTitle;

public class ZhaoPinActivity extends FragmentActivity implements
		OnClickListener {
	private MyCommonTitle myCommonTitle;
	private ImageView img_publish;
	private ZhaoPinFragment[] fragments;
	private HorizontalListView zp_hListView;
	private ZhaoPinFragment zhaopinFragment1, zhaopinFragment2,
			zhaopinFragment3, zhaopinFragment4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhaopin);

		initView();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(null, this);
		myCommonTitle.setTitle("招聘");
		img_publish = (ImageView) findViewById(R.id.aci_shared_btn);
		img_publish.setImageResource(R.drawable.img_publish);

		zp_hListView = (HorizontalListView) findViewById(R.id.zp_hlistView);

	}

	@Override
	public void onClick(View vieww) {
		// TODO Auto-generated method stub
		switch (vieww.getId()) {
		case R.id.aci_shared_btn:
			startActivity(new Intent(ZhaoPinActivity.this,
					ZhaoPinPublishActivity.class));
			break;

		default:
			break;
		}
	}
}
