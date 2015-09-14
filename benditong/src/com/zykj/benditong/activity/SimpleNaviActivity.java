package com.zykj.benditong.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.NaviLatLng;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;

/**
 *导航界面
 */
public class SimpleNaviActivity extends Activity implements AMapNaviViewListener {
	//起点终点
//	private NaviLatLng mNaviStart = new NaviLatLng(39.989614, 116.481763);
//	private NaviLatLng mNaviEnd = new NaviLatLng(39.983456, 116.3154950);
    //起点终点列表
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	//导航View
	private AMapNaviView mAmapAMapNaviView;
	
	private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String lat = getIntent().getStringExtra("lat");
		String lng = getIntent().getStringExtra("lng");
		mRouteCalculatorProgressDialog=new ProgressDialog(this);
		mRouteCalculatorProgressDialog.setCancelable(true);

		mStartPoints.add(new NaviLatLng(Float.valueOf(BaseApp.getModel().getLatitude()), Float.valueOf(BaseApp.getModel().getLongitude())));
		mEndPoints.add(new NaviLatLng(Float.valueOf(lat), Float.valueOf(lng)));

		mRouteCalculatorProgressDialog.show();
//		AMapNavi.getInstance(this).calculateWalkRoute(mNaviEnd);
		AMapNavi.getInstance(this).calculateDriveRoute(mStartPoints, mEndPoints, null, AMapNavi.DrivingDefault);
		
		setContentView(R.layout.activity_simplenavi);
		init(savedInstanceState);
	}

	/**
	 * 初始化
	 * @param savedInstanceState
	 */
	private void init(Bundle savedInstanceState) {
		mAmapAMapNaviView = (AMapNaviView) findViewById(R.id.simplenavimap);
		mAmapAMapNaviView.onCreate(savedInstanceState);
		mAmapAMapNaviView.setAMapNaviViewListener(this);
		// 开启实时导航
		AMapNavi.getInstance(this).startNavi(AMapNavi.GPSNaviMode);
		mRouteCalculatorProgressDialog.dismiss();
	}

//-----------------------------导航界面回调事件------------------------
	/**
	 * 导航界面返回按钮监听
	 * */
	@Override
	public void onNaviCancel() {
		finish();
	}
   
	@Override
	public void onNaviSetting() {

	}

	@Override
	public void onNaviMapMode(int arg0) {
	}
	
	@Override
	public void onNaviTurnClick() {
	}

	@Override
	public void onNextRoadClick() {
	}

	@Override
	public void onScanViewButtonClick() {
	}
	/**
	 * 
	 * 返回键监听事件
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		finish();
		return super.onKeyDown(keyCode, event);
	}

	// ------------------------------生命周期方法---------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mAmapAMapNaviView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		mAmapAMapNaviView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mAmapAMapNaviView.onPause();
		AMapNavi.getInstance(this).stopNavi();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mAmapAMapNaviView.onDestroy();
	}

	@Override
	public void onLockMap(boolean arg0) {
	}
}
