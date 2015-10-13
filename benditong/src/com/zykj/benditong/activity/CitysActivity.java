package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CitysAdapter;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Area;
import com.zykj.benditong.utils.CharacterParser;
import com.zykj.benditong.view.BladeView;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.PinnedHeaderListView;

public class CitysActivity extends Activity {
	private static final String FORMAT = "^[a-z,A-Z].*$";
	private PinnedHeaderListView mListView;
	private BladeView mLetter;
	private MyCommonTitle myCommonTitle;
	private CitysAdapter mAdapter;
	//private String[] datas;
	private List<Area> citys;
	// 首字母集
	private List<String> mSections;
	// 根据首字母存放数据
	private Map<String, List<Area>> mMap;
	// 首字母位置集
	private List<Integer> mPositions;
	// 首字母对应的位置
	private Map<String, Integer> mIndexer;
	//汉字转换成拼音的类
	private CharacterParser characterParser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_citylist);
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		requestData();
	}
	
	private void requestData(){
		HttpUtils.getArea(new EntityHandler<Area>(Area.class) {
			@Override
			public void onReadSuccess(List<Area> list) {
				citys = list;
				initData();
				initView();
			}
		});
	}

	private void initData() {
		//datas = getResources().getStringArray(R.array.countries);
		mSections = new ArrayList<String>();// 首字母集
		mMap = new HashMap<String, List<Area>>();// 根据首字母存放数据
		mPositions = new ArrayList<Integer>();// 首字母位置集
		mIndexer = new HashMap<String, Integer>();// 首字母对应的位置
		for (int i = 0; i < citys.size(); i++) {
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(citys.get(i).getTitle());//中文转拼音
			String firstName = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);//获取首字母
			if (firstName.matches(FORMAT)) {
				if (mSections.contains(firstName)) {
					mMap.get(firstName).add(citys.get(i));
				} else {
					mSections.add(firstName);
					List<Area> list = new ArrayList<Area>();
					list.add(citys.get(i));
					mMap.put(firstName, list);
				}
			} else {
				if (mSections.contains("#")) {
					mMap.get("#").add(citys.get(i));
				} else {
					mSections.add("#");
					List<Area> list = new ArrayList<Area>();
					list.add(citys.get(i));
					mMap.put("#", list);
				}
			}
		}
		Collections.sort(mSections);
		int position = 0;
		for (int i = 0; i < mSections.size(); i++) {
			mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
			mPositions.add(position);//首字母在listview中位置，存入list中
			position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
		}
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("选择城市");
		
		mListView = (PinnedHeaderListView) findViewById(R.id.friends_display);
		mLetter = (BladeView) findViewById(R.id.friends_myletterlistview);
		mLetter.setOnItemClickListener(new BladeView.OnItemClickListener() {
			@Override
			public void onItemClick(String s) {
				if (mIndexer.get(s) != null) {
					mListView.setSelection(mIndexer.get(s));
				}
			}
		});
		mAdapter = new CitysAdapter(this, citys, mMap, mSections, mPositions);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(mAdapter);
		mListView.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.listview_head, mListView, false));
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View convertView, int position, long checkedId) {
				int section = mAdapter.getSectionForPosition(position);
				Area area = mMap.get(mSections.get(section)).get(position-mPositions.get(section));
				setResult(Activity.RESULT_OK, new Intent().putExtra("area", area));
				finish();
			}
		});
	}
}