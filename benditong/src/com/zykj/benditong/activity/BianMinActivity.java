package com.zykj.benditong.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.LocationManagerProxy;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.BianMin;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;

public class BianMinActivity extends BaseActivity implements
		OnItemClickListener {
	private MyCommonTitle myCommonTitle;
	private BianMin bianMin;
	private TextView tv_temperature, tv_weather, tv_city, tv_wind;
	private GridView gv_bianmin;
	private List<BianMin> bianmins = new ArrayList<BianMin>();
	private CommonAdapter<BianMin> bianminAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_facilitate_people);

		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("便民");
		initWather();
		initView();
	}

	private void initView() {
		tv_temperature = (TextView) findViewById(R.id.info_temperature);// 温度
		tv_weather = (TextView) findViewById(R.id.info_weather);// 天气
		tv_city = (TextView) findViewById(R.id.info_city);// 城市
		tv_wind = (TextView) findViewById(R.id.info_wind);// 风力
		gv_bianmin = (GridView) findViewById(R.id.gv_bianmin);
		requestData();
	}

	private void initWather() {
		LocationManagerProxy locationManagerProxy = LocationManagerProxy
				.getInstance(this);
		locationManagerProxy.requestWeatherUpdates(
				LocationManagerProxy.WEATHER_TYPE_LIVE,
				new AMapLocalWeatherListener() {

					@Override
					public void onWeatherLiveSearched(
							AMapLocalWeatherLive aMapLocalWeatherLive) {
						if (aMapLocalWeatherLive != null
								&& aMapLocalWeatherLive.getAMapException()
										.getErrorCode() == 0) {
							String city = aMapLocalWeatherLive.getCity();// 城市
							String temperature = aMapLocalWeatherLive
									.getTemperature();// 温度
							String weather = aMapLocalWeatherLive.getWeather();// 天气
							String windDir = aMapLocalWeatherLive.getWindDir();// 风向
							String windPower = aMapLocalWeatherLive
									.getWindPower();// 风力
							String hunidity = aMapLocalWeatherLive
									.getHumidity();// 空气湿度
							String reportTime = aMapLocalWeatherLive
									.getReportTime();// 数据发布时间
							tv_temperature.setText(String.format("°%s",
									temperature));
							tv_weather.setText(weather);
							tv_city.setText(city);
							tv_wind.setText(windDir + "\t\t" + windPower);
						} else {
							Tools.toast(BianMinActivity.this, "获取天气与预报失败");
						}
					}

					@Override
					public void onWeatherForecaseSearched(
							AMapLocalWeatherForecast arg0) {
					}
				});
	}

	private void requestData() {

		HttpUtils.getBianMinList(new HttpErrorHandler() {
			@Override
			public void onRecevieSuccess(JSONObject json) {
				JSONObject jsonObject = json.getJSONObject("datas");
				String strArray = jsonObject.getString("list");
				List<BianMin> list = JSONArray.parseArray(strArray,
						BianMin.class);
				bianmins.addAll(list);
				bianminAdapter = new CommonAdapter<BianMin>(
						BianMinActivity.this, R.layout.ui_item_bianmin,
						bianmins) {
					@Override
					public void convert(ViewHolder holder, BianMin bianmin) {
						holder.setText(R.id.bm_item_name,
								StringUtil.toString(bianmin.getTitle()))
								.setImageUrl(R.id.bm_item_imag,
										bianmin.getImglist().size() > 0 ? UrlContants.IMAGE_URL 
												+ bianmin.getImglist().get(0).get("imgsrc"):"http://", 10f);
					}
				};
				gv_bianmin.setAdapter(bianminAdapter);
				gv_bianmin.setOnItemClickListener(BianMinActivity.this);
				bianminAdapter.notifyDataSetChanged();
			}
		});
	}
	/**
	 * 点击打开网页
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StringUtil
				.toString(bianmins.get(position).getUrl()))));

	}

}
