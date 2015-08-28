package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Assess;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class AssessActivity extends BaseActivity implements IXListViewListener{

	private static int PERPAGE=2;//perpage默认每页显示10条信息
	private int nowpage=1;//当前显示的页面 
	
	private String type,pid;
	private MyCommonTitle myCommonTitle;
	private XListView mListView;
	private CommonAdapter<Assess> adapter;
	private List<Assess> assesslist = new ArrayList<Assess>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_user_list);
		adapter = new CommonAdapter<Assess>(AssessActivity.this, R.layout.ui_item_assess, assesslist) {
			@Override
			public void convert(ViewHolder holder, Assess assess) {
				holder.setImageUrl(R.id.res_assess_img, assess.getAvatar(), 10f)
				.setText(R.id.res_assess_name, assess.getName())
				.setRating(R.id.res_assess_star, Float.valueOf(assess.getGolds()))
				.setText(R.id.res_assess_content, assess.getContent())
				.setText(R.id.res_assess_time, assess.getAddtime());
			}
		};
		type = "restaurant";//getIntent().getStringExtra("type");
		pid = "1";//getIntent().getStringExtra("pid");
		
		initView();
		requestData();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("评价列表");
		
		mListView = (XListView)findViewById(R.id.advert_listview);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
		mListView.setAdapter(adapter);
	}
	private void requestData() {
		/** 猜你喜欢 */
		RequestParams params = new RequestParams();
		params.put("type", type);//restaurant餐厅(餐厅);hotel酒店;shop店铺
		params.put("pid", pid);//分类ID,默认为空时获取全部分类信息
		params.put("nowpage", nowpage);//当前页
		params.put("perpage", PERPAGE);//每页数量
		HttpUtils.getCommentsList(res_getCommentsList, params);
	}
	
	/**
	 * 猜你喜欢列表
	 */
	private AsyncHttpResponseHandler res_getCommentsList = new EntityHandler<Assess>(Assess.class) {
		@Override
		public void onReadSuccess(List<Assess> list) {
			if(nowpage == 1){assesslist.clear();}
			assesslist.addAll(list);
			adapter.notifyDataSetChanged();
		}
	};
	
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
}
