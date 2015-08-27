package com.zykj.benditong.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.model.Restaurant;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class CanyinDetailActivity extends BaseActivity {
	
	private Restaurant restaurant;
	private MyCommonTitle myCommonTitle;
	private RelativeLayout aci_header;
	private TextView restaurant_name,res_address,res_introduct,res_assess_num,res_assess_name,res_assess_content,res_assess_time,res_assess_more;
	private RatingBar restaurant_star,res_assess_star;
	private ImageView restaurant_img,res_phone,res_assess_img;
	private ListView restaurant_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_canyin_detail);
		restaurant = getIntent().getParcelableExtra("restaurant");
		
		initView();
	}
	
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(this, null);
		myCommonTitle.setTitle("餐厅详情");

		aci_header = (RelativeLayout)findViewById(R.id.aci_header);
		restaurant_name = (TextView)findViewById(R.id.restaurant_name);
		restaurant_star = (RatingBar)findViewById(R.id.restaurant_star);
		restaurant_img = (ImageView)findViewById(R.id.restaurant_img);
		res_address = (TextView)findViewById(R.id.res_address);
		res_phone = (ImageView)findViewById(R.id.res_phone);
		res_introduct = (TextView)findViewById(R.id.res_introduct);
		res_assess_num = (TextView)findViewById(R.id.res_assess_num);
		res_assess_img = (ImageView)findViewById(R.id.res_assess_img);
		res_assess_name = (TextView)findViewById(R.id.res_assess_name);
		res_assess_star = (RatingBar)findViewById(R.id.res_assess_star);
		res_assess_content = (TextView)findViewById(R.id.res_assess_content);
		res_assess_time = (TextView)findViewById(R.id.res_assess_time);
		res_assess_more = (TextView)findViewById(R.id.res_assess_more);
		restaurant_list = (ListView)findViewById(R.id.restaurant_list);
		
		LayoutParams pageParms = aci_header.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH*2/5;
		initializationDate();
	}
	
	private void initializationDate(){
		Bitmap bitmap = ImageLoader.getInstance().loadImageSync(restaurant.getImgsrc());
	}
}
