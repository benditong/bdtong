package com.zykj.benditong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class AppExplainActivity extends BaseActivity {
	
	private MyCommonTitle myCommonTitle;
	private ImageView mCallButton;
	private TextView tv_phone,tv_instructions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_explain);
		
		myCommonTitle=(MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("应用说明");
		
		tv_instructions=(TextView) findViewById(R.id.tv_instructions);
		tv_instructions.setText(Html.fromHtml(tv_instructions.getText().toString()));
		tv_phone=(TextView) findViewById(R.id.phone);
		mCallButton=(ImageView) findViewById(R.id.imag_buton_phone);
		mCallButton.setOnTouchListener(new OnTouchListener() {
		
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tv_phone.getText().toString().trim()));
				AppExplainActivity.this.startActivity(intent);
				return true;
			}
		});
		
	}
	
	

}
