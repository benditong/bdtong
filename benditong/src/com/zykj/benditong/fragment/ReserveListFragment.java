package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.PurchaseAdapter;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.view.MyRequestDailog;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class ReserveListFragment extends Fragment implements IXListViewListener, OnItemClickListener{
	
	private static int PERPAGE=2;//perpage默认每页显示10条信息
	
	private int nowpage=1;//当前显示的页面 
	private int mType=0;
	
    private XListView mListView;
	private PurchaseAdapter adapter;
	private List<Order> orders = new ArrayList<Order>();
	private EntityHandler<Order> mNetHandler;
	
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
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        return mListView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle arguments = getArguments();
		mType=arguments.getInt("type");
		
        adapter = new PurchaseAdapter(getActivity(), R.layout.ui_item_reserve, orders);
        mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
        requestData();
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", "0".equals(mType)?"restaurant":"hotel");//预订餐厅或者酒店
		params.put("uid", "3");//BaseApp.getModel().getUserid();
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
	
	private EntityHandler<Order> creatResponseHandler(){
		if(mNetHandler==null){
			mNetHandler=new EntityHandler<Order>(Order.class) {
				@Override
				public void onReadSuccess(List<Order> list) {
					MyRequestDailog.closeDialog();
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
//		Order order = orders.get(position-1);
//		Intent intent=new Intent(getActivity(), OrderDetailActivity.class);
//		intent.putExtra("order", order);
//		getActivity().startActivity(intent);
	}
}

