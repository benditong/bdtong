package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.ReserveDetailActivity;
import com.zykj.benditong.adapter.ReserveAdapter;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.MyRequestDailog;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class ReserveListFragment extends Fragment implements IXListViewListener, OnItemClickListener{
	
	private static int PERPAGE=10;//perpage默认每页显示10条信息
	
	private int nowpage=1;//当前显示的页面
	private int mType=0;
	
    private XListView mListView;
	private ReserveAdapter adapter;
	private List<Order> orders = new ArrayList<Order>();
	private Handler mHandler = new Handler();//异步加载或刷新
	
	public static ReserveListFragment getInstance(int type){
		ReserveListFragment fragment=new ReserveListFragment();
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
		mListView.setDividerHeight(0);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        return mListView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle arguments = getArguments();
		mType=arguments.getInt("type");
		
        adapter = new ReserveAdapter(getActivity(), R.layout.ui_item_reserve, orders, mType);
        mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
        requestData();
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", 0 == mType ? "restaurant":"hotel");//预订餐厅或者酒店
		params.put("uid", StringUtil.toString(BaseApp.getModel().getUserid(), ""));//当前用户id
		params.put("nowpage", nowpage);//当前第几页
		params.put("perpage", PERPAGE);//每页显示的数量
		HttpUtils.getOrderList(res_getOrderList,params);
	}
	
	private AsyncHttpResponseHandler res_getOrderList=new EntityHandler<Order>(Order.class) {
		@Override
		public void onReadSuccess(List<Order> list) {
			MyRequestDailog.closeDialog();
			if(nowpage == 1){orders.clear();}
			orders.addAll(list);
			adapter.notifyDataSetChanged();
		}
	};
	
	/**
	 * listview 点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Order order = orders.get(position-1);
		Intent intent=new Intent(getActivity(), ReserveDetailActivity.class);
		intent.putExtra("order", order);
		intent.putExtra("type", mType);
		getActivity().startActivity(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		nowpage = 1;
		requestData();
	}

	@Override
	public void onRefresh() {
		/**下拉刷新 重建*/
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
		/**上拉加载分页*/
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
}

