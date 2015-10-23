package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CarpoolAdapter;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Car;
import com.zykj.benditong.utils.Tools;
//import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.XListView;
import com.zykj.benditong.view.XListView.IXListViewListener;

public class CarpoolMainActivity extends BaseActivity implements
		IXListViewListener, OnItemClickListener {
	private static int PERPAGE = 5;// perpage默认每页显示5条信息
	private int nowpage = 1;// 当前显示的页面
	private Button btn_carpoolNeeder, btn_carpoolOwner;
	private ImageButton btn_carpoolBack;
	private XListView car_listView;
	private Handler mHandler;
//	private CommonAdapter<Car> adapter;
	CarpoolAdapter adapter;
	private List<Car> cars = new ArrayList<Car>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool);
		mHandler = new Handler();
		initView();
		getShopListData();
	}
	private void initView() {
		btn_carpoolBack = (ImageButton) findViewById(R.id.carpool_main_back);
		btn_carpoolNeeder = (Button) findViewById(R.id.btn_carpool_need);
		btn_carpoolOwner = (Button) findViewById(R.id.btn_carpool_owner);
		car_listView = (XListView) findViewById(R.id.car_listview);
		setListener(btn_carpoolBack, btn_carpoolNeeder, btn_carpoolOwner);
		car_listView.setDividerHeight(0);
		car_listView.setPullLoadEnable(true);
		car_listView.setPullRefreshEnable(true);
		car_listView.setXListViewListener(this);
//		adapter = new CommonAdapter<Car>(CarpoolMainActivity.this,
//				R.layout.ui_item_carpool_details, cars) {
//			@Override
//			public void convert(ViewHolder holder, Car car) {
//				holder.setText(R.id.textView_orign_2, car.getFrom_address())
//						.setText(R.id.textView_destination_2,
//								car.getTo_address())
//						.setText(R.id.textView_departure_time_2,
//								car.getStarttime())
//						.setText(R.id.textView_remain_seats_2, car.getSeat())
//						.setText(R.id.textView_price_2, car.getPrice());
////						.setButton(R.id.btn_carpool_sign_up,"立即报名", new OnClickListener() {
////
////							@Override
////							public void onClick(View v) {
////							}
////						});
//			}
//		};
		car_listView.setAdapter(adapter);
		//car_listView.setOnItemClickListener(this);

	}

	private void getShopListData() {
		RequestParams params = new RequestParams();
		params.put("type", "need");
		params.put("area",  Tools.CURRENTCITY);
		params.put("nowpage", nowpage);// 第几页
		params.put("perpage", PERPAGE);// 每页条数
		HttpUtils.getList(res_getList, params);
	}

	private AsyncHttpResponseHandler res_getList = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jsonObject = json.getJSONObject("datas");
			String strArray = jsonObject.getString("list");
			List<Car> list = JSONArray.parseArray(strArray, Car.class);
			if (nowpage == 1) {
				cars.clear();
			}
			//String remainSeats=getIntent().getExtras().get("remainSeats").toString();
			cars.addAll(list);
			adapter=new CarpoolAdapter(CarpoolMainActivity.this, cars);
			car_listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	};
	// HttpUtils.getList(new AsyncHttpResponseHandler() {
	// @Override
	// public void onSuccess(int statusCode, Header[] header,
	// byte[] stringjson) {
	// /** 访问成功 */
	// String jsonString = new String(stringjson);
	// JSONObject jsonObject = (JSONObject) JSON.parse(jsonString);
	// JSONObject datas = jsonObject.getJSONObject("datas");
	// String jsonArray = datas.getString("list");
	// //if (!StringUtil.isEmpty(jsonArray)) {
	// List<Car> list = JSONArray.parseArray(jsonArray, Car.class);
	// if (nowpage == 1) {
	// cars.clear();
	// }// 清楚列表数据
	// cars.addAll(list);
	// //adapter = new CarpoolAdapter(CarpoolMainActivity.this, cars);
	// //car_listView.setAdapter(adapter);
	// adapter.notifyDataSetChanged();
	// //}
	// }

	// @Override
	// public void onFailure(int arg0, Header[] arg1, byte[] arg2,
	// Throwable arg3) {
	// /** 访问失败 */
	// Toast.makeText(CarpoolMainActivity.this, "请求失败",
	// Toast.LENGTH_LONG).show();
	// // Tools.toast(CarpoolMainActivity.this, "请求失败");
	// }
	// }, params);
	// }
	// 底部刷新加载数据
	// private void loadMoreData() {
	// RequestParams params = new RequestParams();
	// params.put("type", "need");
	// params.put("nowpage", nowpage);// 第几页
	// params.put("perpage", PERPAGE);// 每页条数
	// HttpUtils.getList(new AsyncHttpResponseHandler() {
	// @Override
	// public void onSuccess(int statusCode, Header[] header,
	// byte[] stringjson) {
	// /** 访问成功 */
	// String jsonString = new String(stringjson);
	// JSONObject jsonObject = (JSONObject) JSON.parse(jsonString);
	// JSONObject datas = jsonObject.getJSONObject("datas");
	// String jsonArray = datas.getString("list");
	// if (!StringUtil.isEmpty(jsonArray)) {
	// List<Car> list = JSONArray.parseArray(jsonArray, Car.class);
	// cars.addAll(list);
	// adapter = new CarpoolAdapter(CarpoolMainActivity.this, cars);
	// car_listView.setAdapter(adapter);
	// adapter.notifyDataSetChanged();
	// } else {
	// onLoad();
	// }
	// }
	//
	// @Override
	// public void onFailure(int arg0, Header[] arg1, byte[] arg2,
	// Throwable arg3) {
	// /** 访问失败 */
	// Toast.makeText(CarpoolMainActivity.this, "请求失败",
	// Toast.LENGTH_LONG).show();
	// // Tools.toast(CarpoolMainActivity.this, "请求失败");
	// }
	// }, params);
	//
	// }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.carpool_main_back:
			finish();
			break;
		case R.id.btn_carpool_need:
			startActivity(new Intent(this, CarpoolNeederActivity.class));
			break;
		case R.id.btn_carpool_owner:
			startActivity(new Intent(this, CarpoolOwnerActivity.class));
			break;
		}
	}

	@Override
	public void onRefresh() {
		try {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					nowpage = 1;
					getShopListData();
					onLoad();
					// car_listView.stopRefresh();
				}
			}, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 上拉加载分页
	@Override
	public void onLoadMore() {
		try {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					nowpage += 1;
					getShopListData();
					// loadMoreData();
					onLoad();
					// car_listView.stopLoadMore();
				}
			}, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void onLoad() {
		car_listView.stopRefresh();
		car_listView.stopLoadMore();
		car_listView.setRefreshTime("刚刚");
	}
	//item 监听事件，可以点击Button实现或者item实现，因为上面代码setItemClickListener注销了，下面代码未实现
	@Override
	public void onItemClick(AdapterView<?> conView, View view, int position,
			long id) {
		Intent intent = new Intent(CarpoolMainActivity.this,
				CarpoolSignUpActivity.class);
		intent.putExtra("car", cars.get(position - 1));
		intent.putExtra("tid", cars.get(position - 1).getId());
		startActivity(intent.putExtra("car", cars.get(position - 1)));
		finish();
		// CarpoolSignUpActivity.class).putExtra("car", cars.get(arg2-1)));
	}
}
