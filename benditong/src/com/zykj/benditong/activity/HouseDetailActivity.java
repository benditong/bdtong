package com.zykj.benditong.activity;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.RecyclingPagerAdapter;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.House;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class HouseDetailActivity extends BaseActivity {

	private House house;
	private MyCommonTitle myCommonTitle;
	private TextView house_title, house_price, house_time, house_room,
			house_square, house_floor, house_contacts, house_plot, house_rent,
			house_intro, house_address, house_decoration, house_mobile;
	private ImageView house_img_mobile;
	private AutoScrollViewPager house_image;
	private int now_pos = 0;

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

		house_image = (AutoScrollViewPager) findViewById(R.id.house_image);//轮播图
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

		LayoutParams pageParms = house_image.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH*10/27;
		
		house_image.setInterval(2000);
		house_image.startAutoScroll();
		
		house_image.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				// 回调view
				uihandler.obtainMessage(0, arg0).sendToTarget();
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageScrollStateChanged(int arg0) {}
		});
		/**
		 * 打电话
		 */
		findViewById(R.id.house_details_phone).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+house_mobile.getText().toString().trim())));
			}
		});
		initializationDate();
	}

	// 轮播图
	private AsyncHttpResponseHandler res_getAdsList = new EntityHandler<String>(
			String.class) {
		@Override
		public void onReadSuccess(final List<String> imageList) {
		}
	};
	
	/**
	 * 给详情页面的控件传值
	 */
	private void initializationDate() {
//		ImageLoader.getInstance().displayImage(StringUtil.toString(house.getImgsrc(), "http://"), house_image);
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
		// 设置轮播
		house_image.setAdapter(new RecyclingPagerAdapter() {
			@Override
			public int getCount() {
				return house.getImglist().size();
			}
			@Override
			public View getView(int position, View convertView, ViewGroup container) {
				ImageView imageView;
				if (convertView == null) {
					convertView = imageView = new ImageView(HouseDetailActivity.this);
					imageView.setScaleType(ScaleType.FIT_XY);
				} else {
					imageView = (ImageView)convertView;
				}
				String imgUrl = house.getImglist().get(position).get("imgsrc");
				CommonUtils.showPic(StringUtil.isEmpty(imgUrl)?"":UrlContants.IMAGE_URL+imgUrl, imageView);
				return convertView;
			}
		});
	}

	Handler uihandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:// 滚动的回调
				changePointView((Integer) msg.obj);
				break;
			}
		}
	};	
	
	/**
	 * 轮播图自动播放
	 * @param cur 当前显示的图片
	 */
	public void changePointView(int cur) {
		LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		View view = pointLinear.getChildAt(now_pos);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.feature_point);
			curPointView.setBackgroundResource(R.drawable.feature_point_cur);
			now_pos = cur;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (house_image != null) {
			house_image.startAutoScroll();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (house_image != null) {
			house_image.stopAutoScroll();
		}
	}
}
