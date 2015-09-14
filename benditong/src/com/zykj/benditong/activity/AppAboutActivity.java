package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class AppAboutActivity extends BaseActivity{
	private MyCommonTitle myCommonTitle; 
	private ImageButton mCallButton;
	private TextView tv_phone,about_us;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.about_us);
		myCommonTitle=(MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("关于我们");
		tv_phone=(TextView) findViewById(R.id.phone);
		about_us=(TextView) findViewById(R.id.about_us);
		about_us.setText(Html.fromHtml(about_us.getText().toString()));
		mCallButton=(ImageButton) findViewById(R.id.imag_buton_phone);
		mCallButton.setOnTouchListener(new OnTouchListener() {
		
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tv_phone.getText().toString().trim()));
				AppAboutActivity.this.startActivity(intent);
				return true;
			}
		});
		
	}
    }




