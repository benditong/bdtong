package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Category;
import com.zykj.benditong.model.Good;
import com.zykj.benditong.model.Restaurant;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.AutoListView;
import com.zykj.benditong.view.MyCommonTitle;

public class CanyinDetailActivity extends BaseActivity {
	
	private Restaurant restaurant;
	private MyCommonTitle myCommonTitle;
	private RelativeLayout aci_header;
	private TextView restaurant_name,res_address,res_introduct,res_assess_num,res_assess_name,res_assess_content,res_assess_time,res_assess_more;
	private RatingBar restaurant_star,res_assess_star;
	private ImageView restaurant_img,res_phone,res_assess_img;
	private AutoListView  restaurant_list;
	private Button reserve_go;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_canyin_detail);
		restaurant = (Restaurant)getIntent().getSerializableExtra("restaurant");
		
		initView();
	}
	
	private void initView(){
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(this, null);
		myCommonTitle.setTitle("restaurant".equals(restaurant.getType())?"餐厅详情":"hotel".equals(restaurant.getType())?"酒店详情":"商铺详情");

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
		restaurant_list = (AutoListView)findViewById(R.id.restaurant_list);
		reserve_go = (Button)findViewById(R.id.reserve_go);
		
		LayoutParams pageParms = aci_header.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH*2/5;
		
		setListener(res_address,res_phone,res_assess_more,reserve_go);
		HttpUtils.getgoodslist(res_getgoodslist, restaurant.getId());
		initializationDate();
	}

	/**
	 * 请求餐厅、酒店、商铺商品
	 */
	private AsyncHttpResponseHandler res_getgoodslist = new EntityHandler<Good>(Good.class) {
		@Override
		public void onReadSuccess(List<Good> list) {
			restaurant_list.setAdapter(new CommonAdapter<Good>(CanyinDetailActivity.this, R.layout.ui_item_canyin, list){
				@Override
				public void convert(ViewHolder holder, Good good) {
					holder.setText(R.id.good_name, good.getTitle())//
						.setImageUrl(R.id.good_img, good.getImgsrc(), 10f)//
						.setText(R.id.good_price, Html.fromHtml("<big><big><font color=#25B6ED>"+good.getPrice()+"</font></big></big>元"));
				}
			});
		}
	};
	
	@SuppressWarnings("deprecation")
	private void initializationDate(){
		Bitmap bitmap = ImageLoader.getInstance().loadImageSync(restaurant.getImgsrc());
		BitmapDrawable bitDrawable = new BitmapDrawable(bitmap);
		bitDrawable.setTileModeXY(TileMode.REPEAT , TileMode.REPEAT );  
		bitDrawable.setDither(true);  
		aci_header.setBackgroundDrawable(bitDrawable); 
		
		restaurant_name.setText(restaurant.getTitle());
		restaurant_star.setRating(Float.valueOf(StringUtil.toString(restaurant.getXinyong(), "0")));
		ImageLoader.getInstance().displayImage(restaurant.getImgsrc(), restaurant_img);
		res_address.setText(restaurant.getAddress());
		res_introduct.setText(restaurant.getIntro());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.res_address:
			break;
		case R.id.res_phone:
			break;
		case R.id.res_assess_more:
			/** 更多评价 */
			startActivity(new Intent(CanyinDetailActivity.this,AssessActivity.class)
				.putExtra("type", restaurant.getType()).putExtra("pid", restaurant.getId()));
			break;
		case R.id.reserve_go:
			break;
		default:
			break;
		}
	}
}
