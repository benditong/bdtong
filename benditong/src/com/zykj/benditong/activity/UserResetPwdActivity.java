package com.zykj.benditong.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.view.MyCommonTitle;

public class UserResetPwdActivity extends BaseActivity{
	private MyCommonTitle myCommonTitle;
	private EditText uu_username,phone_code,uu_userpassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ui_user_reset_pwd);
		myCommonTitle=(MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("重置密码");
	}

}
