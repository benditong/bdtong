package com.zykj.benditong.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.benditong.R;

public class MyShareAndStoreTitle extends RelativeLayout {
	
	private ImageView titleBack;
	private ImageView storedBtn;
	private ImageView sharedBtn;

	public MyShareAndStoreTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.ui_myshareandstore, this);
		titleBack = (ImageView) findViewById(R.id.aci_back_btn);//后退
		sharedBtn = (ImageView) findViewById(R.id.aci_shared_btn);//分享
		storedBtn = (ImageView) findViewById(R.id.aci_store_btn);//收藏
		titleBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Activity) getContext()).finish();
			}
		});
	}

	public void setLisener(OnClickListener sharedListener, OnClickListener storeListener) {
		if(sharedListener != null) {
			sharedBtn.setVisibility(View.VISIBLE);
			sharedBtn.setOnClickListener(sharedListener);
		}
		if(storeListener != null) {
			storedBtn.setVisibility(View.VISIBLE);
			storedBtn.setOnClickListener(storeListener);
		}
	}
	
	public void setTitle(String title) {
		TextView titleText = (TextView) findViewById(R.id.aci_title_tv);
		titleText.setText(title);
	}

	public void setSubTitle(String subtitle) {
		TextView subtitleText = (TextView) findViewById(R.id.aci_subtitle_tv);
		subtitleText.setVisibility(View.VISIBLE);
		subtitleText.setText(subtitle);
	}

	public void setBackBtnVisible(boolean flag) {
		if(flag) {
			titleBack.setVisibility(View.VISIBLE);
		} else {
			titleBack.setVisibility(View.INVISIBLE);
		}
	}
}
