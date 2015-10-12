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
	private RadioGroup tab_zhaopin;
	private ZhaoPinFragment zhaopinFragment1, zhaopinFragment2,
			zhaopinFragment3, zhaopinFragment4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhaopin);

		initView();
		requestData();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(null, this);
		myCommonTitle.setTitle("招聘");
		img_publish = (ImageView) findViewById(R.id.aci_shared_btn);
		img_publish.setImageResource(R.drawable.img_publish);

		// zp_hListView = (HorizontalListView) findViewById(R.id.zp_hlistView);
		tab_zhaopin = (RadioGroup) findViewById(R.id.tab_zhaopin);

		zhaopinFragment1 = ZhaoPinFragment.getInstance(1);
		zhaopinFragment2 = ZhaoPinFragment.getInstance(2);
		zhaopinFragment3 = ZhaoPinFragment.getInstance(3);
		zhaopinFragment4 = ZhaoPinFragment.getInstance(4);

		tab_zhaopin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.zp_tab1) {
					getSupportFragmentManager().beginTransaction()
							.show(zhaopinFragment1).hide(zhaopinFragment2)
							.hide(zhaopinFragment3).hide(zhaopinFragment4)
							.commit();
				}
				if (checkedId == R.id.zp_tab2) {
					getSupportFragmentManager().beginTransaction()
							.show(zhaopinFragment2).hide(zhaopinFragment1)
							.hide(zhaopinFragment3).hide(zhaopinFragment4)
							.commit();
				}
				if (checkedId == R.id.zp_tab3) {
					getSupportFragmentManager().beginTransaction()
							.show(zhaopinFragment3).hide(zhaopinFragment2)
							.hide(zhaopinFragment1).hide(zhaopinFragment4)
							.commit();
				}
				if (checkedId == R.id.zp_tab4) {
					getSupportFragmentManager().beginTransaction()
							.show(zhaopinFragment4).hide(zhaopinFragment2)
							.hide(zhaopinFragment3).hide(zhaopinFragment1)
							.commit();
				}

			}
		});
	}

	private void requestData() {
		getSupportFragmentManager().beginTransaction()
				.add(R.id.zhaopin_framelayout, zhaopinFragment1)
				.add(R.id.zhaopin_framelayout, zhaopinFragment2)
				.add(R.id.zhaopin_framelayout, zhaopinFragment3)
				.add(R.id.zhaopin_framelayout, zhaopinFragment4)
				.show(zhaopinFragment1).hide(zhaopinFragment2)
				.hide(zhaopinFragment3).hide(zhaopinFragment4).commit();
	}

	@Override
	public void onClick(View vieww) {
		// TODO Auto-generated method stub
		switch (vieww.getId()) {
		case R.id.aci_shared_btn:
			startActivityForResult(new Intent(ZhaoPinActivity.this,
					ZhaoPinPublishActivity.class), 1);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1) {
			zhaopinFragment1.flush();// 刷新
		}
	}
}
