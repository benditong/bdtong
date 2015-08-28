package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.GuessLike;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class LikeActivity extends BaseActivity implements IXListViewListener, OnItemClickListener{

	private static int PERPAGE=2;//perpage默认每页显示10条信息
	private int nowpage=1;//当前显示的页面 
	
	private MyCommonTitle myCommonTitle;
	private XListView mListView;
	private CommonAdapter<GuessLike> adapter;
	private List<GuessLike> likes = new ArrayList<GuessLike>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_user_list);
		adapter = new CommonAdapter<GuessLike>(LikeActivity.this, R.layout.ui_item_like, likes) {
			@Override
			public void convert(ViewHolder holder, GuessLike like) {
				holder.setText(R.id.product_title, like.getTitle())
				.setText(R.id.product_content, StringUtil.isEmpty(like.getIntro())?"该商品暂无说明":like.getIntro());
				TextView ptext = holder.getView(R.id.product_price);
				ptext.setText(Html.fromHtml("<big><big><font color=#25B6ED>"+like.getPrice()+"</font></big></big>元"));
			}
		};
		
		initView();
		requestData();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("猜你喜欢");
		
		mListView = (XListView)findViewById(R.id.advert_listview);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(adapter);
	}
	private void requestData() {
		/** 猜你喜欢 */
		RequestParams params = new RequestParams();
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);
		HttpUtils.getLikeList(res_getLikeList, params);
	}
	
	/**
	 * 猜你喜欢列表
	 */
	private AsyncHttpResponseHandler res_getLikeList = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jsonObject = json.getJSONObject(UrlContants.jsonData);
			String strArray = jsonObject.getString("list");
			List<GuessLike> list = JSONArray.parseArray(strArray, GuessLike.class);
			if(nowpage == 1){likes.clear();}
			likes.addAll(list);
			adapter.notifyDataSetChanged();
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}
}
