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
import com.zykj.benditong.R;
import com.zykj.benditong.activity.HouseDetailActivity;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.House;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class HouseFragment extends Fragment implements IXListViewListener,OnItemClickListener {
	private static int PERPAGE = 10;// perpager默认每页显示的条数
	private int nowpage = 1;// 当前显示的页面
	private int mType = 1;//1合租 2整租
	private XListView mListView;
	private List<House> houses = new ArrayList<House>();
	private CommonAdapter<House> houseAdapter;
	private Handler mHandler = new Handler();// 异步加载或刷新

	/**
	 * @param type 1合租 2整租
	 * @return 实例化房租列表
	 */
	public static HouseFragment getInstance(int type) {
		HouseFragment fangChanFragment = new HouseFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("type", type);
		fangChanFragment.setArguments(bundle);
		return fangChanFragment;
	}

	/**
	 * 初始化页面
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mListView = new XListView(getActivity(), null);
		mListView.setLayoutParams(params);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(this);
		return mListView;
	}

	/**
	 * 配置页面参数
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle argument = getArguments();
		mHandler = new Handler();
		mType = argument.getInt("type");
		houseAdapter = new CommonAdapter<House>(getActivity(),R.layout.ui_item_house, houses) {
			@Override
			public void convert(ViewHolder holder, House house) {
				holder.setText(R.id.fc_title,StringUtil.toString(house.getPlot()))
						.setText(R.id.fc_price,StringUtil.toString(house.getPrice()))
						.setText(R.id.fc_address,StringUtil.toString(house.getPlotaddress()))
						.setText(R.id.fc_addtime,StringUtil.toString(house.getAddtime()))
						.setImageUrl(R.id.fangchan_img, house.getImglist().size() > 0 ? UrlContants.IMAGE_URL 
								+ house.getImglist().get(0).get("imgsrc"):"http://", 10f);
			}
		};
		mListView.setAdapter(houseAdapter);
		mListView.setOnItemClickListener(this);
		requestData();
	}
	
	/**
	 * 请求放租列表
	 */
	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("type", mType);
		params.put("nowpage", nowpage);
		params.put("perpage", PERPAGE);
		HttpUtils.getHouseList(getHouseList, params);
	}

	private AsyncHttpResponseHandler getHouseList = new EntityHandler<House>(House.class) {
		@Override
		public void onReadSuccess(List<House> list) {
			if(nowpage==1){houses.clear();}
			houses.addAll(list);
			houseAdapter.notifyDataSetChanged();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        //点击进入详情
		Intent intent=new Intent(getActivity(), HouseDetailActivity.class);
		intent.putExtra("house", houses.get(position-1));
		startActivity(intent);
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
/**
 * 添加完毕后自动刷新列表
 */
	public void reflush() {
		requestData();
	}
}
