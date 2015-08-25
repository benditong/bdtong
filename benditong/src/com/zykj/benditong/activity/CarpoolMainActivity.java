package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;

public class CarpoolMainActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_carpoolNeeder, btn_carpoolOwner, btn_carpoolSignUp;
	private ImageButton btn_carpoolBack;
	private String data[] = new String[] { "济南市＊＊路＊＊建筑", "青岛市＊＊路＊＊建筑",
			"2015-6-17   7:00", "16位", "￥200" };
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private ListView datalist;
	private SimpleAdapter mySimpleAdapter = null;
	RelativeLayout mRelativeLayout;
	ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool);

		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_carpoolBack = (ImageButton) findViewById(R.id.carpool_main_back);
		btn_carpoolNeeder = (Button) findViewById(R.id.btn_carpool_need);
		btn_carpoolOwner = (Button) findViewById(R.id.btn_carpool_owner);
		btn_carpoolSignUp = (Button) findViewById(R.id.btn_carpool_sign_up);
		setListener(btn_carpoolBack, btn_carpoolNeeder, btn_carpoolOwner);

		datalist = (ListView) findViewById(R.id.listView_carpool_details);

		Map<String, String> map = new HashMap<String, String>();
		map.put("orign", data[0]);
		map.put("destination", data[1]);
		map.put("time", data[2]);
		map.put("seats", data[3]);
		map.put("price", data[4]);

		list.add(map);

		mySimpleAdapter = new SimpleAdapter(this, list,
				R.layout.ui_carpool_details, new String[] { "orign",
						"destination", "time", "seats", "price" }, new int[] {
						R.id.textView_orign_2, R.id.textView_destination_2,
						R.id.textView_departure_time_2,
						R.id.textView_remain_seats_2, R.id.textView_price_2 });
		datalist.setAdapter(mySimpleAdapter);

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
		// case R.id.listView_carpool_details:
		// startActivity(new Intent(this, CarpoolSignUpActivity.class));
		// break;
		}
	}

}
