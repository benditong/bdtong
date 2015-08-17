package com.zykj.benditong.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zykj.benditong.R;
import com.zykj.benditong.fragment.StoreFragment;
import com.zykj.benditong.view.MyCommonTitle;

public class UserStoreActivity extends FragmentActivity {

	private MyCommonTitle myCommonTitle;
    private RadioGroup tab_store;
    private StoreFragment storeFragment1,storeFragment2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_user_store);
		
		initView();
		requestData();
	}
	
	/**
	 * 初始化页面
	 */
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("我的收藏");
    	tab_store = (RadioGroup)findViewById(R.id.tab_order);

    	storeFragment1 = StoreFragment.getInstance(0);//收藏的产品
    	storeFragment2 = StoreFragment.getInstance(1);//收藏的店铺

    	tab_store.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.reserve_tab1) {
					getSupportFragmentManager().beginTransaction().show(storeFragment1).hide(storeFragment2).commit();
				} else if (checkedId == R.id.reserve_tab2) {
					getSupportFragmentManager().beginTransaction().hide(storeFragment1).show(storeFragment2).commit();
				}
			}
		});
	}

	/**
	 * 加载数据
	 */
	private void requestData(){
	}
	
}
