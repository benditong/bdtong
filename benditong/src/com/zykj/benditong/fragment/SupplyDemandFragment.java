
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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.activity.DemandDetailActivity;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Demand;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class SupplyDemandFragment extends Fragment implements IXListViewListener, OnItemClickListener{
	
	private static int PERPAGE=10;//perpage默认每页显示10条信息
	private int nowpage=1;//当前显示的页面 
	private int mType=1;//1供应 2需求
    private XListView mListView;
	private List<Demand> demands = new ArrayList<Demand>();
	private CommonAdapter<Demand> demandAdapter;
	private Handler mHandler;
	
	/**
	 * @param type 1供应 2需求
	 * @return 实例化供应列表
	 */
	public static SupplyDemandFragment getInstance(int type){
		SupplyDemandFragment fragment=new SupplyDemandFragment();
		Bundle bundle=new Bundle();
		bundle.putInt("type", type);
		fragment.setArguments(bundle);
		return fragment;
	}

	/**
	 * 初始化页面
	 */
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
	
	/**
	 * 配置页面参数
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		mHandler = new Handler();
		mType=getArguments().getInt("type");
		demandAdapter = new CommonAdapter<Demand>(getActivity(), R.layout.ui_item_demand, demands) {
			@Override
			public void convert(ViewHolder holder, final Demand demand) {
				holder.setText(R.id.demand_title, StringUtil.toString(demand.getTitle()))
						.setText(R.id.demand_content, StringUtil.toString(demand.getIntro()))
						.setImageUrl(R.id.demand_image, demand.getImglist().size() > 0 ? UrlContants.IMAGE_URL 
								+ demand.getImglist().get(0).get("imgsrc"):"http://", 10f);
				holder.getView(R.id.demand_call).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
			            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+demand.getMobile())));
					}
				});
			}
		};
		mListView.setAdapter(demandAdapter);
		mListView.setOnItemClickListener(this);
        requestData();
	}
	
	/**
	 * 请求供应列表
	 */
	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", mType);//1供应 2需求
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);
		HttpUtils.getSupplyDemandList(getSupplyDemandList, params);
	}
	
	/**
	 * 异步请求供应列表
	 */
	private AsyncHttpResponseHandler getSupplyDemandList=new EntityHandler<Demand>(Demand.class) {
		@Override
		public void onReadSuccess(List<Demand> list) {
			if(nowpage==1){demands.clear();}
			demands.addAll(list);
			demandAdapter.notifyDataSetChanged();
		}
	};

	//结束加载、刷新
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
		startActivity(intent);
	}
}