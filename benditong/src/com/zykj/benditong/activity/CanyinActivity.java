package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Category;
import com.zykj.benditong.model.Restaurant;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.ExpandTabView;
import com.zykj.benditong.view.ViewLeft;
import com.zykj.benditong.view.ViewRight;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

/**
 * @author Administrator
 * csh 2015-08-07
 */
public class CanyinActivity extends BaseActivity implements IXListViewListener, OnItemClickListener{
	
	private static int NUM=5;//perpage默认每页显示10条信息
	private int nowpage=1;//当前显示的页面 
	private int orderby=1;//排序
	private String tid="";//分类

	//private MySearchTitle mySearchTitle;
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private List<Category> mCategory = new ArrayList<Category>();
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	private CommonAdapter<Restaurant> adapter;
	private XListView canyin_list;
	private ViewLeft viewLeft;
	private Handler mHandler;//异步加载或刷新
	private ViewRight viewRight;
	private RequestParams params = new RequestParams();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_canyin_activity);
		mHandler = new Handler();
		params.put("type", "restaurant");
		
		initView();
		requestData();
		initListener();
	}
	
	/**
	 * 加载页面
	 */
	private void initView(){
		//mySearchTitle = (MySearchTitle)findViewById(R.id.aci_mytitle);
		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		canyin_list = (XListView) findViewById(R.id.canyin_list);
		canyin_list.setDividerHeight(0);
		canyin_list.setPullLoadEnable(true);
		canyin_list.setXListViewListener(this);
		adapter = new CommonAdapter<Restaurant>(this, R.layout.ui_item_restaurant, restaurants) {
			@Override
			public void convert(ViewHolder holder, Restaurant restaurant) {
				float score = Float.valueOf(StringUtil.toString(restaurant.getXinyong(), "0")); 
				holder.setText(R.id.restaurant_name, restaurant.getTitle())//
					.setImageUrl(R.id.restaurant_id, restaurant.getImgsrc(), 10f)//
					.setText(R.id.restaurant_score, Html.fromHtml("评价：<font color=#FFC500>"+String.format("%.1f分", score)+"</font>"))//
					.setText(R.id.restaurant_address, restaurant.getAddress());
			}
		};
		canyin_list.setAdapter(adapter);
		
		viewLeft = new ViewLeft(this);
		viewRight = new ViewRight(this);
		mViewArray.add(viewLeft);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("分类");
		mTextArray.add("排序方式");
		expandTabView.setValue(mTextArray, mViewArray);
		HttpUtils.getcategory(res_getcategory, params);
	}
	
	private void requestData(){
		params.put("tid", tid);
		params.put("orderby", orderby);
		params.put("lng", BaseApp.getModel().getLongitude());//经度
		params.put("lat", BaseApp.getModel().getLatitude());//纬度
		params.put("nowpage", nowpage);//当前第几页
		params.put("perpage", NUM);//每页显示数目
		HttpUtils.getRestaurants(res_getRestaurants, params);
	}
	
	private AsyncHttpResponseHandler res_getRestaurants = new EntityHandler<Restaurant>(Restaurant.class) {
		@Override
		public void onReadSuccess(List<Restaurant> list) {
			if(nowpage == 1){restaurants.clear();}
			restaurants.addAll(list);
			adapter.notifyDataSetChanged();
		}
	};

	/**
	 * 请求餐厅分类
	 */
	private AsyncHttpResponseHandler res_getcategory = new EntityHandler<Category>(Category.class) {
		@Override
		public void onReadSuccess(List<Category> list) {
			mCategory = list;
			List<String> names = new ArrayList<String>();
			for (Category category : list) {
				names.add(category.getTitle());
			}
			viewLeft.setDatas(names);
		}
	};

	/**
	 * 添加菜单点击事件
	 */
	private void initListener() {
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
			@Override
			public void getValue(int position, String showText) {
				onRefresh(viewLeft, showText);
				tid = mCategory.get(position).getId();
				requestData();
			}
		});
		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
			@Override
			public void getValue(String orderbyid, String showText) {
				onRefresh(viewRight, showText);
				orderby = Integer.valueOf(orderbyid);
				nowpage = 1;
				requestData();
			}
		});
	}
	
	private void onRefresh(View view, String showText) {
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
	}
	
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void onBackPressed() {
		if (!expandTabView.onPressBack()) {
			finish();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> convertView, View view, int position, long id) {
		startActivity(new Intent(CanyinActivity.this, CanyinDetailActivity.class).putExtra("restaurant", restaurants.get(position-1)));
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				nowpage = 1;
				requestData();
				onLoad();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				nowpage += 1;
				requestData();
				onLoad();
			}
		}, 1000);
	}

	private void onLoad() {
		canyin_list.stopRefresh();
		canyin_list.stopLoadMore();
		canyin_list.setRefreshTime("刚刚");
	}
}
