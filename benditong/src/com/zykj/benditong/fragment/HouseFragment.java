package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.HttpAbstractParamBean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.HouseDetailActivity;
import com.zykj.benditong.activity.ZhaoPinDetailsActivity;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.House;
import com.zykj.benditong.model.ZhaoPin;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HouseFragment extends Fragment implements IXListViewListener,
		OnItemClickListener {
	private static int PERPAGE = 2;// perpager默认每页显示的条数
	private int nowpage = 1;// 当前显示的页面
	private int mType = 1;
	private XListView mListView;
	private House house;
	private List<House> houses = new ArrayList<House>();
	private CommonAdapter<House> houseAdapter;
	private Handler mHandler = new Handler();// 异步加载或刷新

	public static HouseFragment getInstance(int type) {
		HouseFragment fangChanFragment = new HouseFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("type", type);
		fangChanFragment.setArguments(bundle);
		return fangChanFragment;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mListView = new XListView(getActivity(), null);
		mListView.setLayoutParams(params);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(this);
		return mListView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle argument = getArguments();
		mType = argument.getInt("type");
		requestData();
	}

	/**
	 * 请求服务器获取数据
	 */
	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", mType);
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);

		HttpUtils.getHouseList(getHouseList, params);
	}

	private AsyncHttpResponseHandler getHouseList = new HttpErrorHandler() {

		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jsonObject = json.getJSONObject("datas");
			String strArray = jsonObject.getString("list");
			List<House> list = JSONArray
					.parseArray(strArray, House.class);
			if (nowpage == 1) {
				houses.clear();
				houses.addAll(list);
				houseAdapter = new CommonAdapter<House>(getActivity(),R.layout.ui_item_house, houses) {
					@Override
					public void convert(ViewHolder holder, House house) {
						holder.setText(R.id.fc_title,StringUtil.toString(house.getPlot()))
								.setText(R.id.fc_price,StringUtil.toString(house.getPrice()))
								.setText(R.id.fc_address,StringUtil.toString(house.getPlotaddress()))
								.setText(R.id.fc_addtime,StringUtil.toString(house.getAddtime()));
					}
				};
				mListView.setAdapter(houseAdapter);
				mListView.setOnItemClickListener(HouseFragment.this);
				houseAdapter.notifyDataSetChanged();
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        //点击进入详情
		Intent intent=new Intent(getActivity(), HouseDetailActivity.class);
		intent.putExtra("house", houses.get(position-1));
		startActivity(intent.putExtra("house", houses.get(position - 1)));
		
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
		mHandler.postDelayed(new  Runnable() {
			
			@Override
			public void run() {
				nowpage+=1;
				requestData();
				onLoad();
			}
		}, 1000);

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
}
