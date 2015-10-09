package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.model.House;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class HouseDetailActivity extends BaseActivity {

	private House house;
	private MyCommonTitle myCommonTitle;
	private TextView house_title, house_price, house_time, house_room,
			house_square, house_floor, house_contacts, house_plot, house_rent,
			house_intro, house_address, house_decoration, house_mobile;
	private ImageView house_image,house_img_mobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_house_detail);
		house = (House) getIntent().getSerializableExtra("house");

		initView();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		// myCommonTitle.setLisener(null, this);
		myCommonTitle.setTitle("房产详情");

		house_image = (ImageView) findViewById(R.id.house_image);
		house_title = (TextView) findViewById(R.id.house_title);
		house_price = (TextView) findViewById(R.id.house_price);
		house_time = (TextView) findViewById(R.id.house_submit_time);
		house_room = (TextView) findViewById(R.id.house_room);
		house_square = (TextView) findViewById(R.id.house_square);
		house_floor = (TextView) findViewById(R.id.house_floor);
		house_plot = (TextView) findViewById(R.id.house_name);
		house_contacts = (TextView) findViewById(R.id.house_contacts);
		house_rent = (TextView) findViewById(R.id.house_rent);
		house_intro = (TextView) findViewById(R.id.house_intro);
		house_address = (TextView) findViewById(R.id.house_address);
		house_decoration = (TextView) findViewById(R.id.house_decoration);
		house_mobile = (TextView) findViewById(R.id.house_mobile);
		/**
		 * 打电话
		 */
		house_img_mobile=(ImageView) findViewById(R.id.house_details_phone);
		house_img_mobile.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+house_mobile.getText().toString().trim())));
				return true;
			}
		});
		house_image.setMinimumHeight(Tools.M_SCREEN_WIDTH * 2 / 5);

		initializationDate();
	}
/**
 * 给详情页面的控件传值
 */
	private void initializationDate() {
		ImageLoader.getInstance().displayImage(StringUtil.toString(house.getImgsrc(), "http://"), house_image);
		house_title.setText(house.getTitle());
		house_price.setText(house.getPrice()+"元/月");
		house_time.setText(house.getAddtime());
		house_room.setText(house.getTingshi());
		house_square.setText(house.getArea()+"m²");
		house_floor.setText(house.getFloor()+"层");
		house_plot.setText(house.getPlot());
		house_rent.setText("1".equals(StringUtil.toString(house.getType()))?"合租":"整租");
		house_intro.setText(house.getIntro());
		house_address.setText(house.getPlotaddress());
		house_contacts.setText(house.getName());
		house_mobile.setText(house.getMobile());
	}
}
