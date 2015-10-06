package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.model.Demand;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyShareAndStoreTitle;

public class DemandDetailActivity extends BaseActivity{
	
	private Demand demand;
	private MyShareAndStoreTitle myCommonTitle;
	private TextView demand_title,demand_time,demand_person,demand_mobile,demand_content;
	private ImageView demand_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_demand_detail);
		demand = (Demand)getIntent().getSerializableExtra("demand");
		
		initView();
	}
	
	private void initView(){
		myCommonTitle = (MyShareAndStoreTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(null, this);
		myCommonTitle.setTitle("信息详情");

		demand_image = (ImageView)findViewById(R.id.demand_image);
		demand_title = (TextView)findViewById(R.id.demand_title);
		demand_time = (TextView)findViewById(R.id.demand_time);
		demand_person = (TextView)findViewById(R.id.demand_person);
		demand_mobile = (TextView)findViewById(R.id.demand_mobile);
		demand_content = (TextView)findViewById(R.id.demand_content);
		
		demand_image.setMinimumHeight(Tools.M_SCREEN_WIDTH * 2 / 5);
		
		setListener(demand_mobile);
		initializationDate();
	}
	
	private void initializationDate(){
		ImageLoader.getInstance().displayImage(StringUtil.toString(demand.getImgsrc(), "http://"), demand_image);
		demand_title.setText(demand.getTitle());
		demand_time.setText("发布时间："+demand.getTitle());
		demand_person.setText("发布时间："+demand.getPerson());
		demand_mobile.setText("发布时间："+demand.getMobile());
		demand_content.setText("发布时间："+demand.getConstruct());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.res_address:
			/** 导航  */
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+demand.getMobile())));
			break;
		default:
			break;
		}
	}
}
