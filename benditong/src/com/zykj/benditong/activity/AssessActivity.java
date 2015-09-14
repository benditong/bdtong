package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Assess;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class AssessActivity extends BaseActivity implements IXListViewListener{

	private static int PERPAGE=10;//perpage默认每页显示10条信息
	private int nowpage=1;//当前显示的页面 
	
	private String type,pid;
	private MyCommonTitle myCommonTitle;
	private XListView mListView;
	private CommonAdapter<Assess> adapter;
	private List<Assess> assesslist = new ArrayList<Assess>();
	private Handler mHandler = new Handler();//异步加载或刷新
	
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
				GridView grid_images = holder.getView(R.id.grid_images);//
				SimpleAdapter adapt = new SimpleAdapter(AssessActivity.this, assess.getImglist(), 
						R.layout.ui_simple_image, new String[]{"imgsrc"}, new int[]{R.id.assess_image});
			 	adapt.setViewBinder(new ViewBinder(){
					@Override
					public boolean setViewValue(View view, Object data, String textRepresentation) { 
						if (view instanceof ImageView && data != null) {
		                    ImageView iv = (ImageView) view;
		                    ImageLoader.getInstance().displayImage(UrlContants.IMAGE_URL+data.toString(), iv);
		                    return true;
		                }else{
			                return false;
		                }
					}  
			   	}); 
				grid_images.setAdapter(adapt);
			}
		};
		type = getIntent().getStringExtra("type");
		pid = getIntent().getStringExtra("pid");
		
		initView();
		requestData();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("评价列表");
		
		mListView = (XListView)findViewById(R.id.advert_listview);
		mListView.setDividerHeight(0);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
		mListView.setAdapter(adapter);
	}
	
	private void requestData() {
		/** 评论列表 */
		RequestParams params = new RequestParams();
		params.put("type", type);//restaurant餐厅(餐厅);hotel酒店;shop店铺
		params.put("tid", pid);//评价的商家ID编号
		params.put("nowpage", nowpage);//当前页
		params.put("perpage", PERPAGE);//每页数量
		HttpUtils.getCommentsList(res_getCommentsList, params);
	}
	
	/**
	 * 评论列表
	 */
	private AsyncHttpResponseHandler res_getCommentsList = new EntityHandler<Assess>(Assess.class) {
		@Override
		public void onReadSuccess(List<Assess> list) {
			if(nowpage == 1){assesslist.clear();}
			assesslist.addAll(list);
			adapter.notifyDataSetChanged();
		}
	};

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
