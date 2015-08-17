package com.zykj.benditong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.zykj.benditong.BaseTabActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyRadioButton;

public class MainActivity extends BaseTabActivity{
	
	private TabHost m_tab;
	private Intent intent_1;
	private Intent intent_2;
	private Intent intent_3;
	// 单选按钮组
	private RadioGroup m_rgroup;
	// 4个单选按钮
	private MyRadioButton m_radio_index;
	private MyRadioButton m_radio_restaurant;
	private MyRadioButton m_radio_setting;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_layout);
		
		m_tab = getTabHost();
		initView();
	}

	private void initView() {
		// 设置圆角边线不启用
		// final TabWidget _widget = m_tab.getTabWidget();
		// _widget.setStripEnabled(false);
		intent_1 = new Intent(this, IndexActivity.class);
		intent_2 = new Intent(this, OrderActivity.class);
		intent_3 = new Intent(this, UserActivity.class);

		m_tab.addTab(buildTagSpec("test1", 0, intent_1));
		m_tab.addTab(buildTagSpec("test2", 1, intent_2));
		m_tab.addTab(buildTagSpec("test3", 2, intent_3));

		m_rgroup = (RadioGroup) findViewById(R.id.tab_rgroup);
		m_radio_index = (MyRadioButton) findViewById(R.id.tab_radio1);
		m_radio_index.getLayoutParams().width = Tools.M_SCREEN_WIDTH/3;
		m_radio_restaurant = (MyRadioButton) findViewById(R.id.tab_radio2);
		m_radio_restaurant.getLayoutParams().width = Tools.M_SCREEN_WIDTH/3;
		m_radio_setting = (MyRadioButton) findViewById(R.id.tab_radio3);
		m_radio_setting.getLayoutParams().width = Tools.M_SCREEN_WIDTH/3;

		m_rgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == m_radio_index.getId()) {
					m_tab.setCurrentTabByTag("test1");
				} else if (checkedId == m_radio_restaurant.getId()) {
					m_tab.setCurrentTabByTag("test2");
				} else if (checkedId == m_radio_setting.getId()) {
					m_tab.setCurrentTabByTag("test3");
				}
			}
		});
		m_tab.setCurrentTab(0);
	}

	private TabHost.TabSpec buildTagSpec(String tagName, int tagLable,
			Intent content) {
		return m_tab.newTabSpec(tagName).setIndicator(tagLable + "")
				.setContent(content);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {
		Tools.Log("当前tabActivity退出");
		super.onDestroy();
	}
}
