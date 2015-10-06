package com.zykj.benditong.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zykj.benditong.view.MyRequestDailog;

/**
 * Created by ss on 15-4-14.
 */
public abstract class HttpErrorHandler extends AbstractHttpHandler {
    private static final String TAG="httpResponse";
    @Override
    public void onJsonSuccess(JSONObject json) {
       String status= json.getString("code");
       String msg=  json.getString("message");
        if(TextUtils.isEmpty(status) || !status.equals("200")){
            if(!TextUtils.isEmpty(msg)){
            Log.e(TAG,msg);}
            onRecevieFailed(status,json);
    		MyRequestDailog.closeDialog();
        }else{
         	onRecevieSuccess(json);
        }
    }

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        try {
			String responseString=new String(responseBody, HTTP.UTF_8);
	        onRecevieFailed("400", JSON.parseObject(UrlContants.ERROR));
			MyRequestDailog.closeDialog();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

    public abstract void onRecevieSuccess(JSONObject json);

    public void onRecevieFailed(String status,JSONObject json){};
}
