package com.zykj.benditong.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.RecyclingPagerAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.AdsImages;
import com.zykj.benditong.model.GuessLike;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.AutoListView;

@SuppressLint("HandlerLeak")
public class IndexActivity extends BaseActivity {

	private AutoScrollViewPager viewPager;
	private AutoListView index_list;
	private LinearLayout im_b1pinche,im_b1canyin,im_b1jiudian,im_b1shangpu,tv_index_order;
	/** 当前的位置 */
	private int now_pos = 0;
	private List<AdsImages> imageList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_index_activity);
		
		initView();
		requestData();
	}
	
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.im_b1canyin:
			/* 餐饮  */
			startActivity(new Intent(IndexActivity.this, CanyinActivity.class));
			break;
		case R.id.im_b1jiudian:
			/* 酒店  */
			break;
		case R.id.im_b1shangpu:
			/* 商铺  */
			break;
		case R.id.im_b1pinche:
			/* 拼车  */
			startActivity(new Intent(IndexActivity.this, CarpoolMainActivity.class));
			break;
		default:
			break;
		}
	}
	
	/**
	 * 加载页面
	 */
	private void initView(){
		viewPager = (AutoScrollViewPager) findViewById(R.id.index_slider);//轮播图
		index_list = (AutoListView)findViewById(R.id.index_list);//猜你喜欢
		im_b1canyin = (LinearLayout)findViewById(R.id.im_b1canyin);//餐饮
		im_b1jiudian = (LinearLayout)findViewById(R.id.im_b1jiudian);//酒店
		im_b1shangpu = (LinearLayout)findViewById(R.id.im_b1shangpu);//商铺
		im_b1pinche = (LinearLayout)findViewById(R.id.im_b1pinche);//拼车
		tv_index_order = (LinearLayout)findViewById(R.id.tv_index_order);//更多

		LayoutParams pageParms = viewPager.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH*10/27;
		
		viewPager.setInterval(2000);
		viewPager.startAutoScroll();
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				// 回调view
				uihandler.obtainMessage(0, arg0).sendToTarget();
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageScrollStateChanged(int arg0) {}
		});
		
		setListener(im_b1canyin, im_b1jiudian, im_b1shangpu, im_b1pinche, tv_index_order);
	}
	
	/**
	 * 请求服务器数据---首页
	 */
	private void requestData(){
		RequestParams params = new RequestParams();
		params.put("type", "slideFocus");
		HttpUtils.getAdsList(res_getAdsList, params);
		HttpUtils.getLikeList(res_getLikeList, "5");
	}
	
	/**
	 * 请求首页猜你喜欢
	 */
	private AsyncHttpResponseHandler res_getLikeList = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
	        JSONArray jsonArray = json.getJSONObject(UrlContants.jsonData).getJSONArray("list");
	        List<GuessLike> list=JSONArray.parseArray(jsonArray.toString(), GuessLike.class);
			index_list.setAdapter(new CommonAdapter<GuessLike>(IndexActivity.this, R.layout.ui_item_like, list) {
				@Override
				public void convert(ViewHolder holder, GuessLike like) {
					holder.setText(R.id.product_title, like.getTitle())
					.setText(R.id.product_content, StringUtil.isEmpty(like.getIntro())?"该商品暂无说明":like.getIntro());
					TextView ptext = holder.getView(R.id.product_price);
					ptext.setText(Html.fromHtml("<big><big><font color=#25B6ED>"+like.getPrice()+"</font></big></big>元"));
				}
			});
		}
	};
	
	//轮播图
	private AsyncHttpResponseHandler res_getAdsList = new EntityHandler<AdsImages>(AdsImages.class){
		@Override
		public void onReadSuccess(List<AdsImages> list) {
			IndexActivity.this.imageList = list;
			// 设置轮播
			viewPager.setAdapter(new RecyclingPagerAdapter() {
				@Override
				public int getCount() { return imageList.size(); }
				@Override
				public View getView(int position, View convertView, ViewGroup container) {
					ImageView imageView;
					if (convertView == null) {
						convertView = imageView = new ImageView(IndexActivity.this);
						imageView.setScaleType(ScaleType.FIT_XY);
						convertView.setTag(imageView);
					} else {
						imageView = (ImageView) convertView.getTag();
					}
					CommonUtils.showPic(imageList.get(1).getImgsrc(), imageView);
					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
//							Intent detailIntent = new Intent(IndexActivity.this, MessageDetailActivity.class);
//							detailIntent.putExtra("special_id", imageList.get(position));
//							startActivity(new Intent(IndexActivity.this, MessageDetailActivity.class));
						}
					});
					return convertView;
				}
			});
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		if (viewPager != null) {
			viewPager.startAutoScroll();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (viewPager != null) {
			viewPager.stopAutoScroll();
		}
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
	
	//退出App
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		CommonUtils.exitkey(keyCode, this);
		return super.onKeyDown(keyCode, event);
	}
}
