package com.zykj.benditong.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.HttpAbstractParamBean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.FangChan;
import com.zykj.benditong.model.ZhaoPin;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FangChanFragment extends Fragment implements IXListViewListener,
		OnItemClickListener {
	private static int PERPAGE = 2;// perpager默认每页显示的条数
	private int nowpage = 1;// 当前显示的页面
	private int mType = 0;
	private XListView mListView;
	private FangChan fangChan;
	private List<FangChan> fangChans = new ArrayList<FangChan>();
	private CommonAdapter<FangChan> fangChanAdapter;
	private Handler mHandler = new Handler();// 异步加载或刷新

	public static FangChanFragment getInstance(int type) {
		FangChanFragment fangChanFragment = new FangChanFragment();
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

		HttpUtils.getFangChanList(getFangChanList, params);
	}

	private AsyncHttpResponseHandler getFangChanList = new HttpErrorHandler() {

		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jsonObject = json.getJSONObject("datas");
			String strArray = jsonObject.getString("list");
			List<FangChan> list = JSONArray
					.parseArray(strArray, FangChan.class);
			if (nowpage == 1) {
				fangChans.clear();
				fangChans.addAll(list);

				fangChanAdapter = new CommonAdapter<FangChan>(getActivity(),
						R.layout.ui_item_fangchan, fangChans) {

					@Override
					public void convert(ViewHolder holder, FangChan fangChan) {
						holder.setText(R.id.fc_title,
								StringUtil.toString(fangChan.getPlot()))
								.setText(
										R.id.fc_price,
										StringUtil.toString(fangChan.getPrice()))
								.setText(
										R.id.fc_address,
										StringUtil.toString(fangChan
												.getPlotaddress()))
								.setText(
										R.id.fc_addtime,
										StringUtil.toString(fangChan
												.getAddtime()));
					}
				};
			}
		}

	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		
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
