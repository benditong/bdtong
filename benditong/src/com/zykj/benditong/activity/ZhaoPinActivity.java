package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.ZhaoPin;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class ZhaoPinActivity extends BaseActivity implements IXListViewListener, OnItemClickListener{
	private static int PERPAGE = 2;// perpager默认每页显示的条数
	private int nowpage = 1;// 当前显示的页面
	
	private MyCommonTitle myCommonTitle;
	private ImageView img_publish;
	private RadioGroup tab_zhaopin;
	private int mType = 0;
	
	private XListView mListView;
	private CommonAdapter<ZhaoPin> zhaopinAdapter;
	private List<ZhaoPin> zhaoPins = new ArrayList<ZhaoPin>();
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhaopin);

		initView();
	}

	/**
	 * 初始化页面
	 */
	private void initView() {
		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(null, this);
		myCommonTitle.setTitle("招聘");
		img_publish = (ImageView) findViewById(R.id.aci_shared_btn);
		img_publish.setImageResource(R.drawable.img_publish);

		tab_zhaopin = (RadioGroup) findViewById(R.id.tab_zhaopin);
		tab_zhaopin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				mType = checkedId;
            	nowpage = 1;
				requestData();
			}
		});
		mListView = (XListView)findViewById(R.id.zhaopin_listview);//选择标签
		zhaopinAdapter = new CommonAdapter<ZhaoPin>(this, R.layout.ui_item_zhaopin, zhaoPins) {
			@Override
			public void convert(ViewHolder holder, ZhaoPin zhaoPin) {
				String time = StringUtil.toString(zhaoPin.getAddtime());
				holder.setText(R.id.zp_zhiwei_name, StringUtil.toString(zhaoPin.getTitle()))
						.setText(R.id.zp_item_salary, Html.fromHtml("薪资：<font color=#FDBB2B>"+StringUtil.toString(zhaoPin.getPay())+"元</font>"))
						.setText(R.id.zp_item_persons, "人数："+StringUtil.toString(zhaoPin.getNum()))
						.setText(R.id.zp_publish_time, time.length()<10?time:time.substring(5, 10))
						.setText(R.id.zp_item_comp_name, StringUtil.toString(zhaoPin.getConame()));
			}
		};
        mListView.setAdapter(zhaopinAdapter);
		mListView.setDividerHeight(0);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(this);
		/*请求招聘分类*/
		HttpUtils.getZhaoPinCategory(res_getCategory);
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		params.put("tid", mType==9999?"":mType);
		params.put("perpage", PERPAGE);
		params.put("nowpage", nowpage);
		/*请求招聘列表*/
		HttpUtils.getZhaoPinList(getZhaoPinList, params);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.aci_shared_btn:
			startActivity(new Intent(this, ZhaoPinPublishActivity.class));
			break;
		default:
			break;
		}
	}

	/**
	 * 请求招聘分类
	 */
	private AsyncHttpResponseHandler res_getCategory = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONArray category = json.getJSONObject(UrlContants.jsonData).getJSONArray("list");
			addRadioButton(9999, "全部", category.size());
			for (int i = 0; i < category.size(); i++) {
				addRadioButton(Integer.valueOf(category.getJSONObject(i).getString("id")), 
						category.getJSONObject(i).getString("title"), category.size());
			}
		}
	};
	
	private void addRadioButton(int id, String title, int num){
		RadioButton radioButton = new RadioButton(ZhaoPinActivity.this);
		radioButton.setText(title);
		radioButton.setId(id);

        radioButton.setTextSize(18f);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setTextColor(getResources().getColorStateList(R.drawable.tab_font_color));
        radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        radioButton.setBackgroundResource(R.drawable.tab_order_bg);
        radioButton.setChecked(id == 9999);
        int defaultdisplay = num<4?num+1:5;
        tab_zhaopin.addView(radioButton, Tools.M_SCREEN_WIDTH/defaultdisplay, LinearLayout.LayoutParams.MATCH_PARENT);
	}

	/**
	 * 请求招聘列表
	 */
	private AsyncHttpResponseHandler getZhaoPinList = new EntityHandler<ZhaoPin>(ZhaoPin.class) {
		@Override
		public void onReadSuccess(List<ZhaoPin> list) {
			if (nowpage == 1) {zhaoPins.clear();}
			zhaoPins.addAll(list);
			zhaopinAdapter.notifyDataSetChanged();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View convertView, int position, long id) {
		// 点击进入详情
		Intent intent = new Intent(this, ZhaoPinDetailsActivity.class);
		intent.putExtra("zhaoPin", zhaoPins.get(position - 1));
		intent.putExtra("tid", zhaoPins.get(position - 1).getId());
		startActivity(intent.putExtra("zhaoPin", zhaoPins.get(position - 1)));
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