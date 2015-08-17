package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.RecyclingPagerAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.AdsImages;
import com.zykj.benditong.model.New;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.AutoListView;

@SuppressLint("HandlerLeak")
public class IndexActivity extends BaseActivity {

	private AutoScrollViewPager viewPager;
	private AutoListView index_list;
	private LinearLayout im_b1nvshi,im_b1nanshi,im_b1muying,im_b1huazhuang,tv_index_order;
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
	
	/**
	 * 加载页面
	 */
	private void initView(){
		viewPager = (AutoScrollViewPager) findViewById(R.id.index_slider);//轮播图
		index_list = (AutoListView)findViewById(R.id.index_list);//猜你喜欢
		im_b1nvshi = (LinearLayout)findViewById(R.id.im_b1nvshi);//餐饮
		im_b1nanshi = (LinearLayout)findViewById(R.id.im_b1nanshi);//酒店
		im_b1muying = (LinearLayout)findViewById(R.id.im_b1muying);//商铺
		im_b1huazhuang = (LinearLayout)findViewById(R.id.im_b1huazhuang);//拼车
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
		
		setListener(im_b1nvshi, im_b1nanshi, im_b1muying, im_b1huazhuang, tv_index_order);
	}
	
	/**
	 * 请求服务器数据---首页
	 */
	private void requestData(){
		HttpUtils.getAdsList(res_getAdsList);
		List<New> news = new ArrayList<New>();
		news.add(new New("通往冥王星之路", "经过在太空里历时9年半、长达48亿公里的漫长飞行，美国宇航局的新视野号探测器即将抵达它的目的地：冥王星（Pluto）。", "88"));
		news.add(new New("跨越时空的抗战遗迹", "1945年3月，美国援华运送物资的车队途经贵州晴隆“二十四道拐”盘山公路时的情景（资料照片，陈亚林提供）。右图：2015年6月24日拍摄的二十四道拐”。", "12"));
		news.add(new New("长沙大学生穿民国学生装义卖", "7月12日晚上，长沙太平老街，身着民国学生装的大学生在向市民义卖。当天，来自省内各大高校的36名女大学生，为支持“抗战主题皮影剧全国巡演”，她们身着民国学生装，端着募捐箱和装槟榔的篮子在街头义卖。摄影：李健/湖南日报新闻影像中心", "55"));
		news.add(new New("山东土豪为萌宠海狮庆生", "2015年7月13日，青岛一土豪小伙在青岛某五星级酒店的游泳池边为萌宠海狮庆祝生日", "8"));
		news.add(new New("盘点盛夏趣味遮阳武器", "7月13日是入伏首日，我国中东部地区迎来大范围高温天气，中央气象台拉响高温黄色预警。面对“烧烤模式”，人们充分发挥想象力，出高招，“研发”出各种“遮阳武器”。", "123"));
		index_list.setAdapter(new CommonAdapter<New>(this, R.layout.ui_item_like, news) {
			@Override
			public void convert(ViewHolder holder, New news) {
				holder.setText(R.id.product_title, news.getTitle())
				.setText(R.id.product_content, news.getContent().length()>50?news.getContent().substring(0,50):news.getContent());
				TextView ptext = holder.getView(R.id.product_price);
				ptext.setText(Html.fromHtml("<big><big><font color=#25B6ED>"+news.getPrice()+"</font></big></big>元"));
			}
		});
		
	}
	
	//轮播图
	AsyncHttpResponseHandler res_getAdsList = new EntityHandler<AdsImages>(AdsImages.class){
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
