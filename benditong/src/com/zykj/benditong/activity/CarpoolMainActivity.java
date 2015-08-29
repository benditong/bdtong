package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CarpoolAdapter;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Car;
import com.zykj.benditong.utils.Tools;

public class CarpoolMainActivity extends BaseActivity {
	private Button btn_carpoolNeeder, btn_carpoolOwner, btn_carpoolSignUp;
	private ImageButton btn_carpoolBack;
	// private static int NUM = 5;// perpage默认每页显示10条信息
	private int nowpage = 1;// 当前显示的页面
	private Handler mHandler;
	private ListView car_listView;
	private CarpoolAdapter adapter;
	private ArrayList<HashMap<String, String>> arrayList_hashMap=new ArrayList<HashMap<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool);
		initView();
		getShopListData();

	}

	private void getShopListData() {
		RequestParams params = new RequestParams();
		params.put("type", "need");
		params.put("nowpage", "1");//第几页
		params.put("perpage", "5");//每页条数
		HttpUtils.getList(new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] header, byte[] stringjson) {
				/** 访问成功 */
				String jsonString = new String(stringjson);
	            JSONObject jsonObject = (JSONObject) JSON.parse(jsonString);
	            JSONObject datas = jsonObject.getJSONObject("datas");
	            String jsonArray = datas.getString("list");
	            List<Car> list = JSONArray.parseArray(jsonArray, Car.class);

				adapter = new CarpoolAdapter(CarpoolMainActivity.this, list);
	            car_listView.setAdapter(adapter);
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				/** 访问失败 */
				Toast.makeText(CarpoolMainActivity.this, "请求失败", Toast.LENGTH_LONG).show();
//				Tools.toast(CarpoolMainActivity.this, "请求失败");
			}
		}, params);
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_carpoolBack = (ImageButton) findViewById(R.id.carpool_main_back);
		btn_carpoolNeeder = (Button) findViewById(R.id.btn_carpool_need);
		btn_carpoolOwner = (Button) findViewById(R.id.btn_carpool_owner);
		car_listView = (ListView) findViewById(R.id.listView_carpool_details);

		btn_carpoolSignUp = (Button) findViewById(R.id.btn_carpool_sign_up);

		setListener(btn_carpoolBack, btn_carpoolNeeder, btn_carpoolOwner);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.carpool_main_back:
			startActivity(new Intent(this, IndexActivity.class));
			break;
		case R.id.btn_carpool_need:
			startActivity(new Intent(this, CarpoolNeederActivity.class));
			break;
		case R.id.btn_carpool_owner:
			startActivity(new Intent(this, CarpoolOwnerActivity.class));
			break;
		}
	}

}
