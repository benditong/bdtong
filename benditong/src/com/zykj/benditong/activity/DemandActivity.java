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

import com.zykj.benditong.R;
import com.zykj.benditong.fragment.SupplyDemandFragment;
import com.zykj.benditong.view.MyCommonTitle;

/**
 * @author csh 2015-09-30
 * 供求
 */
public class DemandActivity extends FragmentActivity implements OnClickListener{

	private MyCommonTitle myCommonTitle;
    private RadioGroup tab_store;
    private SupplyDemandFragment supplyFragment,demandFragment;
    private int checkedId = R.id.reserve_tab1;//当前Fragment
	
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
		myCommonTitle.setTitle("供求");
		myCommonTitle.setLisener(null, this);
		ImageView demandAdd = (ImageView)findViewById(R.id.aci_shared_btn);
		demandAdd.setImageResource(R.drawable.img_publish);
		
    	tab_store = (RadioGroup)findViewById(R.id.tab_order);
    	((RadioButton)findViewById(R.id.reserve_tab1)).setText("供应");
    	((RadioButton)findViewById(R.id.reserve_tab2)).setText("求购");

    	supplyFragment = SupplyDemandFragment.getInstance(1);//供应
    	demandFragment = SupplyDemandFragment.getInstance(2);//需求

    	tab_store.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				DemandActivity.this.checkedId = checkedId;
				if (checkedId == R.id.reserve_tab1) {
					getSupportFragmentManager().beginTransaction().show(supplyFragment).hide(demandFragment).commit();
				} else if (checkedId == R.id.reserve_tab2) {
					getSupportFragmentManager().beginTransaction().hide(supplyFragment).show(demandFragment).commit();
				}
			}
		});
	}

	/**
	 * 加载数据
	 */
	private void requestData(){
		getSupportFragmentManager().beginTransaction().add(R.id.reserve_framelayout, supplyFragment)
			.add(R.id.reserve_framelayout, demandFragment).show(supplyFragment).hide(demandFragment).commit();
	}
	@Override
	public void onClick(View view) {
		if (checkedId == R.id.reserve_tab1){
			startActivity(new Intent(this, DemandAddActivity.class).putExtra("type", 0));//添加供应
		} else if (checkedId == R.id.reserve_tab2) {
			startActivity(new Intent(this, DemandAddActivity.class).putExtra("type", 1));//添加求购
		}
	}
}