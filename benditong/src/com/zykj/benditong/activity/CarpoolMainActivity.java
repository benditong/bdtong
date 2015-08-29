package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CarpoolAdapter;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.TextAdapter.OnItemClickListener;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Car;
import com.zykj.benditong.model.Restaurant;
import com.zykj.benditong.view.XListView;

public class CarpoolMainActivity extends BaseActivity {
	private Button btn_carpoolNeeder, btn_carpoolOwner, btn_carpoolSignUp;
	private ImageButton btn_carpoolBack;
	// private static int NUM = 5;// perpage默认每页显示10条信息
	private int nowpage = 1;// 当前显示的页面
	private List<Car> cars = new ArrayList<Car>();
	private Handler mHandler;
	private ListView car_list;
	private CarpoolAdapter adapter;
	private RequestParams params = new RequestParams();

	// private String data[] = new String[] { "济南市＊＊路＊＊建筑", "青岛市＊＊路＊＊建筑",
	// "2015-6-17   7:00", "16位", "￥200" };
	// private List<Map<String, String>> list = new ArrayList<Map<String,
	// String>>();
	// private ListView datalist;
	// RelativeLayout mRelativeLayout;
	// CarpoolAdapter mCarpoolAdapter;

	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool);

		initView();
		// getListData(0);
		//HttpUtils.getLikeList(res_getLikeList, "5");
		//HttpUtils.getlist(mhandler, params);

	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_carpoolBack = (ImageButton) findViewById(R.id.carpool_main_back);
		btn_carpoolNeeder = (Button) findViewById(R.id.btn_carpool_need);
		btn_carpoolOwner = (Button) findViewById(R.id.btn_carpool_owner);
		car_list = (ListView) findViewById(R.id.listView_carpool_details);

		btn_carpoolSignUp = (Button) findViewById(R.id.btn_carpool_sign_up);

		setListener(btn_carpoolBack, btn_carpoolNeeder, btn_carpoolOwner);
		/*JsonHttpResponseHandler jsonGetcarList = new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				
				
				
			}

		};*/
		adapter = new CarpoolAdapter(CarpoolMainActivity.this, cars);
		car_list.setAdapter(adapter);
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
