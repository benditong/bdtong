package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Demand;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyShareAndStoreTitle;

public class DemandDetailActivity extends BaseActivity {

	private Demand demand;
	private MyShareAndStoreTitle myCommonTitle;
	private TextView demand_title, demand_time, demand_contacts, demand_mobile,
			demand_content;
	private ImageView demand_image, demand_img_mobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_demand_detail);
		demand = (Demand) getIntent().getSerializableExtra("demand");
		initView();
	}

	private void initView() {
		myCommonTitle = (MyShareAndStoreTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("信息详情");
		
		demand_image = (ImageView) findViewById(R.id.demand_image);
		demand_title = (TextView) findViewById(R.id.demand_title);
		demand_time = (TextView) findViewById(R.id.demang_addtime);
		demand_contacts = (TextView) findViewById(R.id.demand_contacts);
		demand_mobile = (TextView) findViewById(R.id.demand_mobile);
		demand_content = (TextView) findViewById(R.id.demand_content);
		demand_img_mobile = (ImageView) findViewById(R.id.demand_img_mobile);
		LayoutParams pageParms = demand_image.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH*17/27;
		//demand_image.setMinimumHeight(Tools.M_SCREEN_WIDTH * 2 / 5);

		setListener(demand_img_mobile);
		initializationDate();
	}

	private void initializationDate(){
		ImageLoader.getInstance().displayImage(demand.getImglist().size() > 0 ? UrlContants.IMAGE_URL 
				+ demand.getImglist().get(0).get("imgsrc"):"http://", demand_image);
		demand_title.setText(demand.getTitle());
		demand_time.setText(demand.getAddtime());
		demand_contacts.setText(demand.getName());
		demand_mobile.setText(demand.getMobile());
		demand_content.setText(demand.getIntro());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.demand_img_mobile:
			/** 打电话 */
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+demand_mobile.getText().toString().trim())));
			break;
		default:
			break;
		}
	}
}
