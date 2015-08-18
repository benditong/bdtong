package com.zykj.benditong.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MySearchTitle;

/**
 * @author Administrator
 * csh 2015-08-07
 */
public class CanyinActivity extends BaseActivity{
	
	public static String ADVERTTYPE = "1";

	private MySearchTitle mySearchTitle;
	
	private TextView gift_left,gift_right;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_canyin_activity);
		
		initView();
	}
	
	/**
	 * 加载页面
	 */
	private void initView(){
		mySearchTitle = (MySearchTitle)findViewById(R.id.aci_mytitle);
		
		gift_left = (TextView) findViewById(R.id.gift_left);
		gift_right = (TextView) findViewById(R.id.gift_right);
		
		setListener(gift_left, gift_right);
	}
}
