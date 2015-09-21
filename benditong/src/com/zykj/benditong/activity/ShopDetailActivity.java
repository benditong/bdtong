package com.zykj.benditong.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.navi.AMapNavi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Assess;
import com.zykj.benditong.model.Good;
import com.zykj.benditong.model.Restaurant;
import com.zykj.benditong.utils.ImageUtil;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.AutoListView;
import com.zykj.benditong.view.MyShareAndStoreTitle;

public class ShopDetailActivity extends BaseActivity implements OnItemClickListener{
	
	private Restaurant restaurant;
	private MyShareAndStoreTitle myCommonTitle;
	private RelativeLayout aci_header;
	private TextView restaurant_name,res_address/*,res_assess_num*/,res_assess_name,res_assess_content,res_assess_time,res_assess_more;
	private RatingBar restaurant_star,res_assess_star;
	private ImageView restaurant_img,res_phone,res_assess_img,header_background;
	private AutoListView  restaurant_list;
	private GridView grid_images;
	private List<Good> datalist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_shop_detail);
		restaurant = (Restaurant)getIntent().getSerializableExtra("restaurant");
		
		initView();
	}
	
	private void initView(){
		myCommonTitle = (MyShareAndStoreTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(this, null);
		myCommonTitle.setTitle("商铺详情");

		aci_header = (RelativeLayout)findViewById(R.id.aci_header);
		header_background = (ImageView)findViewById(R.id.header_background);
		restaurant_name = (TextView)findViewById(R.id.restaurant_name);
		restaurant_star = (RatingBar)findViewById(R.id.restaurant_star);
		restaurant_img = (ImageView)findViewById(R.id.restaurant_img);
		res_address = (TextView)findViewById(R.id.res_address);
		res_phone = (ImageView)findViewById(R.id.res_phone);
//		res_assess_num = (TextView)findViewById(R.id.res_assess_num);
		res_assess_img = (ImageView)findViewById(R.id.res_assess_img);
		res_assess_name = (TextView)findViewById(R.id.res_assess_name);
		res_assess_star = (RatingBar)findViewById(R.id.res_assess_star);
		grid_images = (GridView)findViewById(R.id.grid_images);
		res_assess_content = (TextView)findViewById(R.id.res_assess_content);
		res_assess_time = (TextView)findViewById(R.id.res_assess_time);
		res_assess_more = (TextView)findViewById(R.id.res_assess_more);
		restaurant_list = (AutoListView)findViewById(R.id.restaurant_list);
		restaurant_list.setOnItemClickListener(this);
		
		LayoutParams pageParms = aci_header.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH*2/5;
		
		setListener(res_address,res_phone,res_assess_more);
		HttpUtils.getgoodslist(res_getgoodslist, restaurant.getId());
		initializationDate();
	}

	/**
	 * 请求餐厅、酒店、商铺商品
	 */
	private AsyncHttpResponseHandler res_getgoodslist = new EntityHandler<Good>(Good.class) {
		@Override
		public void onReadSuccess(List<Good> list) {
	        datalist=list;
			restaurant_list.setAdapter(new CommonAdapter<Good>(ShopDetailActivity.this, R.layout.ui_item_shop, list){
				@Override
				public void convert(ViewHolder holder, Good good) {
					holder.setText(R.id.good_name, good.getTitle())//
						.setImageUrl(R.id.good_img, good.getImgsrc(), 10f)//
						.setText(R.id.good_price, Html.fromHtml("<big><big><font color=#25B6ED>"+good.getPrice()+"</font></big></big>元"))
						.setText(R.id.good_old_price, "360元", Paint.STRIKE_THRU_TEXT_FLAG)
						.setText(R.id.good_sell_num, "已售1402");
				}
			});
		}
	};
	
	private void initializationDate(){
		ImageLoader.getInstance().displayImage(restaurant.getCoverimg(), header_background);
		restaurant_name.setText(restaurant.getTitle());
		restaurant_star.setRating(Float.valueOf(StringUtil.toString(restaurant.getAvg(), "0")));
		ImageLoader.getInstance().displayImage(restaurant.getImgsrc(), restaurant_img);
		res_address.setText(restaurant.getAddress());
		RequestParams params = new RequestParams();
		params.put("type", "shop");//restaurant餐厅(餐厅);hotel酒店;shop店铺
		params.put("tid", restaurant.getId());//评价的商家ID编号
		params.put("nowpage", 1);//当前页
		params.put("perpage", 1);//每页数量
		HttpUtils.getCommentsList(res_getCommentsList, params);
	}
	
	/**
	 * 评论列表
	 */
	private AsyncHttpResponseHandler res_getCommentsList = new EntityHandler<Assess>(Assess.class) {
		@Override
		public void onReadSuccess(List<Assess> list) {
			if(list.size() > 0){
				Assess assess = list.get(0);
				ImageUtil.displayImage2Circle(res_assess_img, assess.getAvatar(), 10f, null);
				res_assess_name.setText(assess.getName());
				res_assess_star.setRating(Float.valueOf(assess.getGolds()));
				res_assess_content.setText(assess.getContent());
				res_assess_time.setText(assess.getAddtime());
				SimpleAdapter adapt = new SimpleAdapter(ShopDetailActivity.this, assess.getImglist(), 
						R.layout.ui_simple_image, new String[]{"imgsrc"}, new int[]{R.id.assess_image});
			 	adapt.setViewBinder(new ViewBinder(){
					@Override
					public boolean setViewValue(View view, Object data, String textRepresentation) { 
						if (view instanceof ImageView && data != null) {
		                    ImageView iv = (ImageView) view;
		                    LayoutParams pageParms = iv.getLayoutParams();
		    				pageParms.width = 80;
		    				pageParms.height = 80;
		    				ImageUtil.displayImage2Circle(iv, UrlContants.IMAGE_URL+data.toString(), 5f, null);
		                    return true;
		                }else{
			                return false;
		                }
					}  
			   	}); 
				grid_images.setAdapter(adapt);
			}else{
				closeComments();
			}
		}
		@Override
		public void onRecevieFailed(String status, JSONObject json) {
			closeComments();
		}
	};
	
	/**
	 * 暂无评价
	 */
	private void closeComments(){
		res_assess_img.setVisibility(View.INVISIBLE);
		res_assess_name.setVisibility(View.INVISIBLE);
		res_assess_star.setVisibility(View.INVISIBLE);
		grid_images.setVisibility(View.GONE);
		res_assess_content.setText("暂无评价");
		res_assess_time.setVisibility(View.INVISIBLE);
		res_assess_more.setOnClickListener(null);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.res_address:
			/** 导航  */
			AMapNavi.getInstance(this).startGPS();
			startActivity(new Intent(ShopDetailActivity.this, SimpleNaviActivity.class)
							.putExtra("lat", restaurant.getLat())
							.putExtra("lng", restaurant.getLng())
							.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.res_phone:
			/** 打电话  */
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+restaurant.getTel())));
			break;
		case R.id.res_assess_more:
			/** 更多评价 */
			startActivity(new Intent(ShopDetailActivity.this,AssessListActivity.class)
				.putExtra("type", restaurant.getType()).putExtra("pid", restaurant.getId()));
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View convertView, int position, long checkId) {
		Good good = datalist.get(position);
		startActivity(new Intent(this, GroupBuyDetailActivity.class)
			.putExtra("goodId", good.getId()).putExtra("shopId", restaurant.getId()));
	}
}
