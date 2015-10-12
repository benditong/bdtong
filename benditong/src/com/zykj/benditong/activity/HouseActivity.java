package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zykj.benditong.R;
import com.zykj.benditong.fragment.HouseFragment;
import com.zykj.benditong.view.MyCommonTitle;

public class HouseActivity extends FragmentActivity implements
		OnClickListener {
	private MyCommonTitle myCommonTitle;
	private ImageView img_publish;
	private RadioGroup tab_fangChan;
	private HouseFragment fangChanFragment1, fangChanFragment2;
	private int checkedId = R.id.fangchan_tab1;//当前Fragment
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_house);

		initView();
		requestData();
	}

	/**
	 * 加载页面
	 */
	private void initView() {
		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(null, this);
		myCommonTitle.setTitle("房产");
		img_publish = (ImageView) findViewById(R.id.aci_shared_btn);
		img_publish.setImageResource(R.drawable.img_publish);

		tab_fangChan = (RadioGroup) findViewById(R.id.tab_fangchan);
		fangChanFragment1 = HouseFragment.getInstance(1);// 合租房
		fangChanFragment2 = HouseFragment.getInstance(2);// 整租房

		tab_fangChan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.fangchan_tab1) {
					getSupportFragmentManager().beginTransaction()
							.show(fangChanFragment1).hide(fangChanFragment2)
							.commit();
				}
				if (checkedId == R.id.fangchan_tab2) {
					getSupportFragmentManager().beginTransaction()
							.show(fangChanFragment2).hide(fangChanFragment1)
							.commit();
				}
			}
		});
	}

	private void requestData() {
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fangchan_framelayout, fangChanFragment1)
				.add(R.id.fangchan_framelayout, fangChanFragment2)
				.show(fangChanFragment1).hide(fangChanFragment2).commit();

	}

	@Override
	public void onClick(View vieww) {
		if (checkedId == R.id.fangchan_tab1){
			startActivity(new Intent(this, HouseAddActivity.class).putExtra("type", 1));//发布合租
		} else if (checkedId == R.id.fangchan_tab2) {
			startActivity(new Intent(this, HouseAddActivity.class).putExtra("type", 2));//发布整租
		}
	}
}
