package com.zykj.benditong.http;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zykj.benditong.view.MyRequestDailog;

/**
 * Created by ss on 15-4-14.
 */
public abstract class AbstractHttpHandler extends AsyncHttpResponseHandler{

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        try {
            String responseString=new String(responseBody, HTTP.UTF_8);
            JSONObject json = (JSONObject) JSON.parse(responseString);
            onJsonSuccess(json);
        } catch (Exception e) {
        	Log.e("JSON--parse", e.toString());
            onJsonSuccess(JSONObject.parseObject(UrlContants.ERROR));
        } 
    }

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
		MyRequestDailog.closeDialog();
        onJsonSuccess(JSONObject.parseObject(UrlContants.ERROR));
	}
    
	public abstract void onJsonSuccess(JSONObject json);

}
