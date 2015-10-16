package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.MyCommonTitle;

public class AppAboutActivity extends BaseActivity{
	private MyCommonTitle myCommonTitle; 
	private ImageView mCallButton;
	private TextView tv_phone,about_us, app_url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		myCommonTitle=(MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("关于我们");
		tv_phone=(TextView) findViewById(R.id.app_phone);
		about_us=(TextView) findViewById(R.id.app_about_us);
		//about_us.setText(Html.fromHtml(about_us.getText().toString()));
		app_url=(TextView) findViewById(R.id.app_url);
		
		mCallButton=(ImageView) findViewById(R.id.imag_buton_phone);
		
		setListener(mCallButton,app_url);
		
		requestData();
	}
	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.imag_buton_phone:
			Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tv_phone.getText().toString().trim()));
			AppAboutActivity.this.startActivity(intent);
			break;
		case R.id.app_url:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtil.toString(app_url.getText()))));
			break;
		default:
			break;
		}
	}
	private void requestData() {
		RequestParams params=new RequestParams();
		
		params.put("type", "about");
		params.put("id", "2");
		
		HttpUtils.getAboutUs(new HttpErrorHandler() {
			@Override
			public void onRecevieSuccess(JSONObject json) {
				JSONObject jsonObject = json.getJSONObject("datas");
				about_us.setText(jsonObject.getString("content"));
				app_url.setText(jsonObject.getString("url"));
				tv_phone.setText(jsonObject.getString("mobile"));
			}
		},params);
	}
    }




