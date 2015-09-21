package com.zykj.benditong.http;

import org.apache.http.Header;

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
        }else{
         	onRecevieSuccess(json);
        }
    }

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        onRecevieFailed("400", JSON.parseObject(UrlContants.ERROR));
		MyRequestDailog.closeDialog();
	}

    public abstract void onRecevieSuccess(JSONObject json);

    public void onRecevieFailed(String status,JSONObject json){};
}
