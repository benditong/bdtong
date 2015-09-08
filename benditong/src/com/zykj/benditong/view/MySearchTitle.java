package com.zykj.benditong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.benditong.R;

public class MySearchTitle extends RelativeLayout {
	
	private TextView titleAddressee;
	private EditText titleSearch;
	private ImageView titleSearchEdit;

	public MySearchTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.ui_mysearchtitle, this);
		titleAddressee = (TextView) findViewById(R.id.address);
		titleSearch = (EditText) findViewById(R.id.search_input);
		titleSearchEdit = (ImageView) findViewById(R.id.search_delete);
	}

	public void setAddresseeListener(OnClickListener addresseeClickListener) {
		if(addresseeClickListener!=null) {
			titleAddressee.setOnClickListener(addresseeClickListener);
		}
	}

	public void setAddresseeText(String text) {
		if(text!=null) {
			titleAddressee.setText(text);
		}
	}

	public void setSearchDelListener(OnClickListener addresseeClickListener) {
		if(addresseeClickListener!=null) {
			titleSearchEdit.setOnClickListener(addresseeClickListener);
		}
	}
	
	public void setSearchListener(OnClickListener searchClickListener) {
		if(searchClickListener!=null) {
			titleSearch.setOnClickListener(searchClickListener);
		}
	}
	
	public String getSearchStr() {
		String str = titleAddressee.getText().toString();
		return str;
	}
}
