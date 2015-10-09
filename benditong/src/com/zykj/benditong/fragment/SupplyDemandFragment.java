
package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.DemandDetailActivity;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Demand;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class SupplyDemandFragment extends Fragment implements IXListViewListener, OnItemClickListener{
	
	private static int PERPAGE=2;//perpage默认每页显示10条信息
	private int nowpage=1;//当前显示的页面 
	private int mType=1;//1供应 2需求
	private Demand demand;
    private XListView mListView;
	private List<Demand> demands = new ArrayList<Demand>();
	private CommonAdapter<Demand> demandAdapter;
	private Handler mHandler;
	
	public static SupplyDemandFragment getInstance(int type){
		SupplyDemandFragment fragment=new SupplyDemandFragment();
		Bundle bundle=new Bundle();
		bundle.putInt("type", type);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mListView = new XListView(getActivity(), null);
		mListView.setDividerHeight(0);
        mListView.setLayoutParams(params);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setOnItemClickListener(this);
        mListView.setXListViewListener(this);
        return mListView;
	}
	
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//		mHandler = new Handler();
//		mType=getArguments().getInt("type");
//		demandAdapter = new CommonAdapter<Demand>(getActivity(), R.layout.ui_item_demand, demands) {
//			@Override
//			public void convert(ViewHolder holder, final Demand demand) {
//				holder.setText(R.id.demand_title, demand.getTitle())
//						.setImageUrl(R.id.demand_image, StringUtil.toString(demand.getImgsrc(), "http://"), 10f)
//						.setText(R.id.demand_content, demand.getConstruct());
//				holder.getView(R.id.demand_call).setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View view) {
//			            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+demand.getMobile())));
//					}
//				});
//			}
//		};
//		mListView.setAdapter(demandAdapter);
//		mListView.setOnItemClickListener(this);
//        requestData();
//	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle argument = getArguments();
		mType = argument.getInt("type");
		requestData();
	}
	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", mType);
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);
		HttpUtils.getSupplyDemandList(getSupplyDemandList, params);
//		List<Demand> list = new ArrayList<Demand>();
//		for (int i = 0; i < 2; i++) {
//			Demand demand = new Demand("马厂湖咸鸭蛋", "叶良辰", "13325095533", 
//					"咸鸭蛋以新鲜鸭蛋为主要原料经过腌制而成的再制蛋，营养丰富，富含脂肪、蛋白质及人体所需的各种氨基酸、钙、磷、铁、各种微量元素、维生素等。");
//			list.add(demand);
//		}
//		if(nowpage == 1){demands.clear();}
//		demands.addAll(list);
//		demandAdapter.notifyDataSetChanged();
	}
	private AsyncHttpResponseHandler getSupplyDemandList=new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jsonObject=json.getJSONObject("datas");
			String strArray=jsonObject.getString("list");
			List<Demand> list=JSONArray.parseArray(strArray, Demand.class);
			if(nowpage==1){
				demands.clear();
				demands.addAll(list);
				demandAdapter=new CommonAdapter<Demand>(getActivity(),R.layout.ui_item_demand,demands) {
					
					@Override
					public void convert(ViewHolder holder, Demand demand) {
						holder.setText(R.id.demand_title, StringUtil.toString(demand.getTitle()))
						.setText(R.id.demand_content, StringUtil.toString(demand.getIntro()))
						.setText(R.id.demand_call, StringUtil.toString(demand.getMobile()));
					}
				};
				mListView.setAdapter(demandAdapter);
				mListView.setOnItemClickListener(SupplyDemandFragment.this);
				demandAdapter.notifyDataSetChanged();
			}
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
		Intent intent =new Intent(getActivity(), DemandDetailActivity.class);
		intent.putExtra("demand", demands.get(position-1));
		//intent.putExtra("demandId", demand.getId());
		startActivity(intent);
	}
}