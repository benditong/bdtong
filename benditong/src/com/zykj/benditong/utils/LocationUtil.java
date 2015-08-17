package com.zykj.benditong.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class  LocationUtil  {
public final static int GPSTIMEOUT = 0;
public final static int STATUS_CHANGED = 1;
public final static int SELECT_LOCATION = 2;
public final static int DEFAULT_LOCATION_COMPLETED = 3;
public final static int GET_LOCATIONBUILDINGLIST_FAILED = 4;
public final static int CANCELGPS_COMPLETED = 5;
public final static int SELECT_LOCATION_COMPLETED = 6;
public final static int REFRESHGPS_COMPLETED = 7;
public final static int REFRESHGPS_NOPROVIDER = 8;
public final static int GETLOCATION_FAILED = 9;

protected static String sLastLocation = "";
public static final String NONE_AVAILABLE_LOCATION = "...";


private  LocationObsever mLocationObsever = null;

/** location services */
private  LocationManager mLocationManager = null;

private  Context mContext = null;

/** location services */
private  MyLocationListener mLocationListener = null;

/** latitude and longitude of current location*/
public static String mLat = "";
public static String mLon = "";

/** time out for GPS location update */
private  Timer mGpsTimer = new Timer();	
/** TimerTask for time out of GPS location update */
private  GpsTimeOutTask mGpsTimeOutTask = new GpsTimeOutTask();
/** GPS location update time out in milliseconds*/
private  long mGpsTimeOut = 180000;//3 minutes
/** handler for time out of GPS location update*/
private Handler mGpsTimerHandler = new Handler() {
	public void handleMessage(Message msg) {

		if (mLocationManager == null) {
			return;
		}
		if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			System.out.println("=====use network to get location");
			mLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0,
					mLocationListener);
		} else {
			mLocationManager.removeUpdates(mLocationListener);

			// mLocationObsever.notifyChange(SETADDLOCATIONBUTTONSTATE_1_SETLOCATIONDES_1,null);
			if (mLocationObsever != null) {
				mLocationObsever.notifyChange(GPSTIMEOUT, null);
			}
		}
	}
};


public void initiLocationUtil (Context context, LocationObsever locationobsever){
	mLocationObsever = locationobsever;	
	mContext = context;
	mLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);	
	mLocationListener = new MyLocationListener();	
}



private  class MyLocationListener implements LocationListener{
	private boolean mLocationReceived = false;
	@Override
	public void onLocationChanged(Location location) {
		if(location != null && !mLocationReceived){
			mLocationReceived = true;
			String lon = String.valueOf(location.getLongitude());
			String lat = String.valueOf(location.getLatitude());	
			if(mLocationObsever != null){
				mLocationObsever.notifyChange(DEFAULT_LOCATION_COMPLETED, lat+","+lon);
			}
		}else  if(location == null){
			if(mLocationObsever != null){
				mLocationObsever.notifyChange(GETLOCATION_FAILED, null);	
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status,
			Bundle extras) {
		// TODO Auto-generated method stub
		//if GPS provider is not accessible, try network provider
		if(provider.equals(LocationManager.GPS_PROVIDER) && status != LocationProvider.AVAILABLE){
			if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
				mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
						0, 
						0, 
						mLocationListener);
			}else{
				mLocationManager.removeUpdates(mLocationListener);

				if(mLocationObsever != null){
					mLocationObsever.notifyChange(STATUS_CHANGED, null);
				}
			}
		}
	}
}


public void RefreshGPS(boolean calledByCreate){	
	
	mLocationManager.removeUpdates(mLocationListener);
	boolean providerEnable = true;
	//看是否有GPS权限
	if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
		//开始进行定位 mLocationListener为位置监听器
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
				0, 
				0, 
				mLocationListener);
		//start time out timer
		mGpsTimer = new Timer(); 
		mGpsTimeOutTask = new GpsTimeOutTask();
		mGpsTimer.schedule(mGpsTimeOutTask, mGpsTimeOut);
		
	}
	
	if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
				0, 
				0, 
				mLocationListener);
		providerEnable = true;
	}
			
	if(providerEnable){
		if(mLocationObsever != null){
			mLocationObsever.notifyChange(REFRESHGPS_COMPLETED, null);
		}

	}else{
		if(mLocationObsever != null){
			mLocationObsever.notifyChange(REFRESHGPS_NOPROVIDER, null);
		}

	}
	
}


/**
 * cancel operations of refreshing GPS
 */
public  void cancelRefreshGPS(){
	if(mLocationManager != null){
		mLocationManager.removeUpdates(mLocationListener);
	}
	
	if(mLocationObsever != null){
		mLocationObsever.notifyChange(CANCELGPS_COMPLETED, null);	
	}
}	



public  void destroy (){
	if(mLocationManager != null){
	     mLocationManager.removeUpdates(mLocationListener);
	}
	
	if(mGpsTimer != null){
		mGpsTimer.cancel();
	} 
	
	cancelRefreshGPS();
	
	mContext = null;
	mLocationObsever = null;
	
	System.gc();
}


/**
 * this class is TimerTask for time out of GPS location update
 */
private  class GpsTimeOutTask extends TimerTask{
	public void run() {
		Message message = new Message();       
		message.what = 1;       
		mGpsTimerHandler.sendMessage(message);     
	}  
}

public static String getLastLocation() {
	return sLastLocation;
}

public static void setLastLocation(String strLocation) {
	sLastLocation = strLocation;
}	
public abstract class LocationObsever {
	
    public abstract void notifyChange(int arg, String des);
    
}
}