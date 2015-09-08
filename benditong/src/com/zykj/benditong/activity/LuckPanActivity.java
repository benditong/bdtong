package com.zykj.benditong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MySurfaceView;

public class LuckPanActivity extends BaseActivity{
	
	private MySurfaceView mSurfaceView;
	private ImageView mStartBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_luckpan);
		
		mSurfaceView = (MySurfaceView) findViewById(R.id.id_luckpan);
		mStartBtn = (ImageView) findViewById(R.id.id_strat_btn);
		mStartBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!mSurfaceView.isStart()){
					mSurfaceView.luckyStart();
					mStartBtn.setImageResource(R.drawable.stop);
				}else{
					if(!mSurfaceView.isShouldEnd()){
						mSurfaceView.luckyEnd();
						mStartBtn.setImageResource(R.drawable.start);
					}
				}
			}
		});
	}
}
