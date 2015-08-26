package com.zykj.benditong.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.model.AppModel;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyRequestDailog;

public class CarpoolNeederActivity extends BaseActivity {
	/**
	 * 拼车提交信息
	 */
	private EditText editText_orign, editText_destination,
			editText_depart_time, editText_persons, editText_cost,
			editText_name, editText_phone;
	private Button btn_submit;
	private ImageButton imageButton;
	/**
	 * 界面信息
	 */
	private String from_address,to_address,starttime,seats,price,name,phone;
	
	private Map<String, String> submit_data;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_carpool_needer);
		initView();
		
		name=getSharedPreferenceValue(AppModel.NAME);
		phone=getSharedPreferenceValue(AppModel.PHONE);
	}
	
	final Handler handle=new Handler(){
		 public void handleMessage(Message msg) {
	            switch (msg.what) {
	                case 0:// 提交信息
	                    MyRequestDailog.closeDialog();
	                    String data = (String) msg.obj;
	                    JSONObject object = null;
	                    try {
	                        object = new JSONObject(data);
	                    } catch (Exception e) {
	                    }

	                    try {
	                        if (object == null) {
	                            Tools.toast(CarpoolNeederActivity.this, "提交失败");
	                        } else {
	                            if ((object.getInt("state") == 1)) {// 完善成功
	                                //getData(0);
	                                // 重新获取信息
	                            }else {
	                            	 Tools.toast(CarpoolNeederActivity.this, "提交失败");
	                            }
	                        }
	                    } catch (Exception e) {
	                    	 Tools.toast(CarpoolNeederActivity.this, "提交失败");
	                    }

	                    break;
	            }
	        }
	    };
		

	private void initView() {
		// TODO Auto-generated method stub
		editText_orign = (EditText) findViewById(R.id.user_origin);
		editText_destination = (EditText) findViewById(R.id.user_destination);
		editText_depart_time = (EditText) findViewById(R.id.user_departure_time);
		editText_persons = (EditText) findViewById(R.id.user_persons);
		editText_cost = (EditText) findViewById(R.id.user_car_cost);
		editText_name = (EditText) findViewById(R.id.user_name);
		editText_phone = (EditText) findViewById(R.id.user_phone);

		imageButton = (ImageButton) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_carpool_submit);

		setListener(btn_submit, imageButton);

		Time time = new Time();
		time.setToNow();// 获得系统现在的时间
		int year = time.year;
		int month = time.month + 1;
		int day = time.monthDay;
		int hour = time.hour;
		String strTime = year + "-" + month + "-" + day + "-" + hour + ":00";
		editText_depart_time.setHint(strTime);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			startActivity(new Intent(this, CarpoolMainActivity.class));
			break;

		case R.id.btn_carpool_submit:
			submitCarpoolInfo();
			break;

		}
	}

	private void submitCarpoolInfo() {
		if(from_address.length()<=0){
			Tools.toast(this, "出发地不能为空");
			return;
		}
		if(to_address.length()<=0){
			Tools.toast(this, "目的地不能为空");
			return;
		}
		if(starttime.length()<=0){
			Tools.toast(this, "出发时间不能为空");
			return;
		}
		if(seats.length()<=0){
			Tools.toast(this, "人数不能为空");
			return;
		}
		if(price.length()<=0){
			Tools.toast(this, "预计费用不能为空");
			return;
		}
		if(name.length()<=0){
			Tools.toast(this, "姓名不能为空");
			return;
		}
		if(phone.length()<=0){
			Tools.toast(this, "联系电话不能为空");
			return;
		}
		submit_data=new HashMap<String, String>();
		submit_data.put("from_address", from_address.trim());
		submit_data.put("to_address", to_address.trim());
		submit_data.put("starttime", starttime.trim());
		submit_data.put("seats", seats.trim());
		submit_data.put("price", price.trim());
		submit_data.put("name", name.trim());
		submit_data.put("phone", phone.trim());
		
		MyRequestDailog.showDialog(this, "");
		startThread(0);
		
	}

	private void startThread(int i) {
		
		
		
	}

}
