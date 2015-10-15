package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.CanyinDetailActivity;
import com.zykj.benditong.activity.GroupBuyDetailActivity;
import com.zykj.benditong.activity.ShopDetailActivity;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.menuListView.SwipeMenu;
import com.zykj.benditong.menuListView.SwipeMenuCreator;
import com.zykj.benditong.menuListView.SwipeMenuItem;
import com.zykj.benditong.menuListView.SwipeMenuListView;
import com.zykj.benditong.menuListView.SwipeMenuListView.IXListViewListener;
import com.zykj.benditong.menuListView.SwipeMenuListView.OnMenuItemClickListener;
import com.zykj.benditong.model.Good;
import com.zykj.benditong.model.Restaurant;
import com.zykj.benditong.utils.StringUtil;

public class StoreFragment extends Fragment implements IXListViewListener, OnItemClickListener, OnMenuItemClickListener{
	private static int PERPAGE=10;//perpage默认每页显示10条信息
	private int nowpage=1;//当前显示的页面 
	private int mType=1;//1收藏的产品 2收藏的店铺
    private SwipeMenuListView mListView;
	private List<Good> goods = new ArrayList<Good>();
	private CommonAdapter<Good> goodAdapter;
	private Handler mHandler;
	
	public static StoreFragment getInstance(int type){
		StoreFragment fragment=new StoreFragment();
		Bundle bundle=new Bundle();
		bundle.putInt("type", type);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	private SwipeMenuCreator creator = new SwipeMenuCreator() {
		@Override
		public void create(SwipeMenu menu) {
			SwipeMenuItem openItem = new SwipeMenuItem(getActivity());
			// set item background
			openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
			// set item width
			openItem.setWidth(dp2px(90));
			// set item title
			openItem.setTitle("删除");
			// set item title fontsize
			openItem.setTitleSize(18);
			// set item title font color
			openItem.setTitleColor(Color.WHITE);
			// add to menu
			menu.addMenuItem(openItem);
		}
	};
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mListView = new SwipeMenuListView(getActivity(), null);
		mListView.setDividerHeight(0);
        mListView.setLayoutParams(params);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(this);
        return mListView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mHandler = new Handler();
		mType=getArguments().getInt("type");//1收藏的产品 2收藏的店铺
		goodAdapter=new CommonAdapter<Good>(getActivity(),R.layout.ui_item_collection,goods) {
			@Override
			public void convert(ViewHolder holder, Good good) {
				String time=StringUtil.toString(good.getAddtime());
				holder.setImageUrl(R.id.collection_img,StringUtil.toString(good.getImgsrc()))
				      .setText(R.id.collection_name, StringUtil.toString(good.getTitle()))
				      .setText(R.id.collection_time, time.length()<10?time:time.substring(5, 10)+"  收藏");
			}
		};
		mListView.setAdapter(goodAdapter);
		mListView.setOnItemClickListener(this);
        requestData();
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", mType);//1收藏的产品 2收藏的店铺
		params.put("uid", StringUtil.toString(BaseApp.getModel().getUserid()));
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);
		HttpUtils.getCollectionList(getCollectionList,params);
	}
	
	/**
	 * 收藏列表
	 */
	private AsyncHttpResponseHandler getCollectionList=new EntityHandler<Good>(Good.class) {
		@Override
		public void onReadSuccess(List<Good> list) {
			if (nowpage == 1) {
				goods.clear();
			}
			goods.addAll(list);
			goodAdapter.notifyDataSetChanged();
		}
	};

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	
	//下拉刷新 重建
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
	
	//上拉加载分页
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
	/**
	 * listview 点击事件 
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final Good good = goods.get(position-1);
//		Tools.toast(getActivity(), goods.get(position-1).getId());
		if("1".equals(mType)){
			//1收藏的产品
			startActivity(new Intent(getActivity(), GroupBuyDetailActivity.class)
					.putExtra("goodId", good.getPid())
					.putExtra("shopId", good.getTid()));
		}else{
			//2收藏的店铺
			HttpUtils.getShopInfo(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					Restaurant restaurant = JSONObject.parseObject(json.getString(UrlContants.jsonData), Restaurant.class);
					startActivity(new Intent(getActivity(),
							"shop".equals(good.getType())?ShopDetailActivity.class:CanyinDetailActivity.class)
							.putExtra("restaurant", restaurant));
				}
			}, good.getPid());
		}
	}

	@Override
	public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
		switch (index) {
		case 0:
			RequestParams params = new RequestParams();
			params.put("id", goods.get(position).getId());
			params.put("uid", BaseApp.getModel().getUserid());
			HttpUtils.delCollectionInfo(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					goods.remove(position);
					goodAdapter.notifyDataSetChanged();
				}
			}, params);
		}
		return false;
	}
}

