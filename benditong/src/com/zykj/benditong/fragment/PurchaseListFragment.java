package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.PurchaseDetailActivity;
import com.zykj.benditong.adapter.PurchaseAdapter;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.view.MyRequestDailog;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class PurchaseListFragment extends Fragment implements IXListViewListener, OnItemClickListener{
	
	private static int PERPAGE=2;//perpage默认每页显示10条信息
	
	private int nowpage=1;//当前显示的页面 
	private int state=0;//订单状态：0未付款1已付款,未消费2已消费3已退款4订单已取消
	
    private XListView mListView;
	private PurchaseAdapter adapter;
	private List<Order> orders = new ArrayList<Order>();
	private HttpErrorHandler mNetHandler;
	
	public static PurchaseListFragment getInstance(int state){
		PurchaseListFragment fragment=new PurchaseListFragment();
		Bundle bundle=new Bundle();
		bundle.putInt("state", state);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mListView = new XListView(getActivity(), null);
        mListView.setDividerHeight(0);
        mListView.setLayoutParams(params);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        return mListView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle arguments = getArguments();
		state=arguments.getInt("state");
		
        adapter = new PurchaseAdapter(getActivity(), R.layout.ui_item_purchase, orders);
        mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
        requestData();
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", "group");
		params.put("uid", "3");//BaseApp.getModel().getUserid();
		params.put("state", state);
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);
		HttpUtils.getOrderList(creatResponseHandler(),params);
	}
	
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
	private HttpErrorHandler creatResponseHandler(){
		if(mNetHandler==null){
			mNetHandler=new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					MyRequestDailog.closeDialog();
			        JSONArray jsonArray = json.getJSONObject(UrlContants.jsonData).getJSONArray("list");
			        List<Order> list=JSONArray.parseArray(jsonArray.toString(), Order.class);
					if(nowpage == 1){orders.clear();}
					orders.addAll(list);
					adapter.notifyDataSetChanged();
				}
			};
		};
		return mNetHandler;
	}
	
	/**
	 * listview 点击事件 
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String orderId = orders.get(position-1).getId();
		Intent intent=new Intent(getActivity(), PurchaseDetailActivity.class);
		intent.putExtra("orderid", orderId);
		getActivity().startActivity(intent);
	}
}

