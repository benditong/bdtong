package com.zykj.benditong.fragment;

import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ZhaoPinFragment extends Fragment implements IXListViewListener,
		OnItemClickListener {

	private static int perpager = 2;// perpager默认每页显示的条数
	private int nowpage = 1;// 当前显示的页面
	private int mType = 0;
	private XListView mListView;

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
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(	ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		mListView=new XListView(getActivity(), null);
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
		Bundle argumens = getArguments();
		mType = argumens.getInt("type");
		requestData();

	}

	/**
	 * 请求服务器获取数据
	 */
	private void requestData() {

	}
/**
 * 点击事件
 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
           //点击进入详情
		
		
		
	}

	// 下拉刷新
	@Override
	public void onRefresh() {
		nowpage = 1;
		requestData();
		mListView.stopRefresh();

	}
	// 上拉加载分页
	@Override
	public void onLoadMore() {
		nowpage += 1;
		requestData();
		mListView.stopLoadMore();
	}
}
