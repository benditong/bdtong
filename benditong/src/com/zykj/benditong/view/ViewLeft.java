package com.zykj.benditong.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zykj.benditong.R;
import com.zykj.benditong.adapter.TextAdapter;


public class ViewLeft extends RelativeLayout implements ViewBaseAction{

	private ListView mListView;
	private List<String> items = new ArrayList<String>();//显示字段
	private OnSelectListener mOnSelectListener;
	private TextAdapter adapter;

	public ViewLeft(Context context) {
		super(context);
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_distance, this, true);
		setBackgroundColor(getResources().getColor(R.color.white));
		//setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
		mListView = (ListView) findViewById(R.id.listView);
		adapter = new TextAdapter(context, items, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
		adapter.setTextSize(17);
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position, String showText) {
				if (mOnSelectListener != null) {
					mOnSelectListener.getValue(position, showText);
				}
			}
		});
	}
	
	public void setDatas(List<String> names){
		adapter.notifyCategoryList(names);
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(int position, String showText);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
