package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.ZhaoPinDetailsActivity;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.ZhaoPin;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ZhaoPinFragment extends Fragment implements IXListViewListener,
		OnItemClickListener {

	private static int PERPAGE = 10;// perpager默认每页显示的条数
	private int nowpage = 1;// 当前显示的页面
	private int mType = 0;
	private XListView mListView;
	private ZhaoPin zhaoPin;
	private List<ZhaoPin> zhaoPins = new ArrayList<ZhaoPin>();
	private CommonAdapter<ZhaoPin> zhaopinAdapter;
	private Handler mHandler = new Handler();// 异步加载或刷新

	public static ZhaoPinFragment getInstance(int type) {
		ZhaoPinFragment zhaopinFragment = new ZhaoPinFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("type", type);
		zhaopinFragment.setArguments(bundle);
		return zhaopinFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mListView = new XListView(getActivity(), null);
		mListView.setLayoutParams(params);
		mListView.setDividerHeight(0);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(this);
		return mListView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle argumens = getArguments();
		mType = argumens.getInt("type");

		zhaopinAdapter = new CommonAdapter<ZhaoPin>(getActivity(),
				R.layout.ui_item_zhaopin, zhaoPins) {
			@Override
			public void convert(ViewHolder holder, ZhaoPin zhaoPin) {
				holder.setText(R.id.zp_zhiwei_name,
						StringUtil.toString(zhaoPin.getId()))
						.setText(R.id.zp_item_salary,
								StringUtil.toString(zhaoPin.getPay()))
						.setText(R.id.zp_item_persons,
								StringUtil.toString(zhaoPin.getNum()))
						.setText(R.id.zp_publish_time,
								StringUtil.toString(zhaoPin.getAddtime()))
						.setText(R.id.zp_item_comp_name,
								StringUtil.toString(zhaoPin.getConame()));
			}
		};
		mListView.setAdapter(zhaopinAdapter);
		mListView.setOnItemClickListener(ZhaoPinFragment.this);
		zhaopinAdapter.notifyDataSetChanged();
		requestData();

	}

	/**
	 * 请求服务器获取数据
	 */
	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", mType);
		params.put("perpage", PERPAGE);
		params.put("nowpage", nowpage);

		HttpUtils.getZhaoPinList(getZhaoPinList, params);
	}

	private AsyncHttpResponseHandler getZhaoPinList = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jsonObject = json.getJSONObject("datas");
			String strArray = jsonObject.getString("list");
			List<ZhaoPin> list = JSONArray.parseArray(strArray, ZhaoPin.class);
			if (nowpage == 1) {
				zhaoPins.clear();
				zhaoPins.addAll(list);
			}
		}
	};

	/**
	 * 点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// 点击进入详情
		Intent intent = new Intent(getActivity(), ZhaoPinDetailsActivity.class);
		intent.putExtra("zhaoPin", zhaoPins.get(position - 1));
		intent.putExtra("tid", zhaoPins.get(position - 1).getId());
		startActivity(intent.putExtra("zhaoPin", zhaoPins.get(position - 1)));

	}

	// 下拉刷新
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

	// 上拉加载分页
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
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	/**
	 * 添加完毕后自动刷新列表
	 */
	public void flush() {
		requestData();
	}
}
