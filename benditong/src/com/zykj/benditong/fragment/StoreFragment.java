package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.GroupBuyDetailActivity;
import com.zykj.benditong.activity.ShopDetailActivity;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Good;
import com.zykj.benditong.model.Restaurant;
import com.zykj.benditong.swiptodelete.SwipeDismissListViewTouchListener;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class StoreFragment extends Fragment implements IXListViewListener, OnItemClickListener{
	private boolean ischecked;
	private static int PERPAGE=2;//perpage默认每页显示10条信息
	private MyCommonTitle myCommonTitle;
	private int nowpage=1;//当前显示的页面 
	private int mType=1;
	private Good good;
	private Restaurant restaurant;
    private XListView mListView;
	private List<Good> goods = new ArrayList<Good>();
	private CommonAdapter<Good> goodAdapter;
	
	public static StoreFragment getInstance(int type){
		StoreFragment fragment=new StoreFragment();
		Bundle bundle=new Bundle();
		bundle.putInt("type", type);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mListView = new XListView(getActivity(), null);
        mListView.setLayoutParams(params);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);
        return mListView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle arguments = getArguments();
		mType=arguments.getInt("type");
        requestData();
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		//params.put("type", "0".equals(mType)?"good":"restaurant");//预订餐厅或者酒店
		params.put("type", mType);
		params.put("uid", StringUtil.toString(BaseApp.getModel().getUserid()));//BaseApp.getModel().getUserid();
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);
		HttpUtils.getCollectionList(getCollectionList,params);
	}
	private AsyncHttpResponseHandler getCollectionList=new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jsonObject = json.getJSONObject("datas");
			String strArray = jsonObject.getString("list");
			List<Good> list = JSONArray.parseArray(strArray, Good.class);
			if (nowpage == 1) {
				goods.clear();
			}
			//String remainSeats=getIntent().getExtras().get("remainSeats").toString();
			goods.addAll(list);
			goodAdapter=new CommonAdapter<Good>(getActivity(),R.layout.ui_item_collection,goods) {

				@Override
				public void convert(ViewHolder holder, Good good) {
					holder.setImageUrl(R.id.collection_img,StringUtil.toString(good.getImgsrc()))
					      .setText(R.id.collection_name, StringUtil.toString(good.getTitle()))
					      .setText(R.id.collection_time, StringUtil.toString(good.getAddtime()));
				}
			};
			mListView.setAdapter(goodAdapter);
			mListView.setOnItemClickListener(StoreFragment.this);
			goodAdapter.notifyDataSetChanged();
			
			//ListView listView =(ListView) getView();
//			SwipeDismissListViewTouchListener touchListener=
//					new SwipeDismissListViewTouchListener(mListView, 
//							new SwipeDismissListViewTouchListener.DismissCallbacks(){
//						@Override
//						public boolean canDismiss(int position) {
//							return true;
//						}
//
//						@Override
//						public void onDismiss(ListView listView,
//								int[] reverseSortedPositions) {
//						for (int position:reverseSortedPositions) {
//							
//								goods.remove(goodAdapter.getItem(position));
//							}
//						goodAdapter.notifyDataSetChanged();
//						}
//					});
//			((View) goods).setOnTouchListener(touchListener);
		}
	};
	
	//下拉刷新 重建
	@Override
	public void onRefresh() {
		nowpage = 1;
		requestData();
		mListView.stopRefresh();
	}
	
	//上拉加载分页
	@Override
	public void onLoadMore() {
		nowpage += 1;
		requestData();
		mListView.stopLoadMore();
	}
	/**
	 * listview 点击事件 
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if("type".equals(mType)){
			Intent intent =new Intent(getActivity(), GroupBuyDetailActivity.class);
			intent.putExtra("goodId", StringUtil.toString(good.getId()));
			startActivity(intent);
		}else {
			Intent intent =new Intent(getActivity(),ShopDetailActivity.class);
			intent.putExtra("restaurant", StringUtil.toString(restaurant.getId()));
			
			startActivity(intent);
		
		}
	}

}

