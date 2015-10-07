package com.zykj.benditong.activity;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.BianMin;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class BianMinActivity extends BaseActivity {
	private MyCommonTitle myCommonTitle;
	private BianMin bianMin;
	private TextView tv_temperature, tv_weather, tv_city, tv_wind;
	private GridView zixun_gv, gouwu_gv, chaxun_gv, shenghuo_gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_facilitate_people);

		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("便民");
		initWather();
		initView();
	}

	private void initWather() {
		LocationManagerProxy locationManagerProxy = LocationManagerProxy.getInstance(this);
		locationManagerProxy.requestWeatherUpdates(LocationManagerProxy.WEATHER_TYPE_LIVE, new AMapLocalWeatherListener() {
			
			@Override
			public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
				if(aMapLocalWeatherLive!=null&&aMapLocalWeatherLive.getAMapException().getErrorCode()==0){
					String city=aMapLocalWeatherLive.getCity();//城市
					String temperature=aMapLocalWeatherLive.getTemperature();//温度
					String weather=aMapLocalWeatherLive.getWeather();//天气
					String windDir =aMapLocalWeatherLive.getWindDir();//风向
					String windPower=aMapLocalWeatherLive.getWindPower();//风力
					String hunidity=aMapLocalWeatherLive.getHumidity();//空气湿度
					String reportTime=aMapLocalWeatherLive.getReportTime();//数据发布时间
					tv_temperature.setText(String.format("°%s",temperature));
					tv_weather.setText(weather);
					tv_city.setText(city);
					tv_wind.setText(windDir+"\t\t"+windPower);
				}else {
					Tools.toast(BianMinActivity.this, "获取天气与预报失败");
				}
			}
			
			@Override
			public void onWeatherForecaseSearched(AMapLocalWeatherForecast arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initView() {
		tv_temperature = (TextView) findViewById(R.id.info_temperature);// 温度
		tv_weather = (TextView) findViewById(R.id.info_weather);// 天气
		tv_city = (TextView) findViewById(R.id.info_city);// 城市
		tv_wind = (TextView) findViewById(R.id.info_wind);// 风力
		zixun_gv = (GridView) findViewById(R.id.gv_find_zixun);// 资讯
		gouwu_gv = (GridView) findViewById(R.id.gv_find_gouwu);// 购物
		chaxun_gv = (GridView) findViewById(R.id.gv_find_chaxun);// 查询
		shenghuo_gv = (GridView) findViewById(R.id.gv_find_shenghuo);// 生活

		//requestData();
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		// params.put("type", StringUtil.toString(bianMin.getType()));
		params.put("title", StringUtil.toString(bianMin.getTitle()));
		params.put("imgsrc", StringUtil.toString(bianMin.getImgsrc()));
		params.put("url", StringUtil.toString(bianMin.getUrl()));

		HttpUtils.getBianMinList(new HttpErrorHandler() {

			@Override
			public void onRecevieSuccess(JSONObject json) {

			}
		}, params);
	}
	
}
