package com.zykj.benditong.http;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zykj.benditong.view.MyRequestDailog;

/**
 * Created by ss on 15-4-17.
 */
public abstract class EntityHandler<T> extends HttpErrorHandler{

    private  Class<T> c;

    public EntityHandler(Class<T> clzz){
        this.c=clzz;
    }

    @Override
    public void onRecevieSuccess(JSONObject json) {
        JSONArray jsonArray = json.getJSONArray(UrlContants.jsonData);
        List<T> list=JSONArray.parseArray(jsonArray.toString(), c);
        onReadSuccess(list);
    }

	@Override
	public void onRecevieFailed(String status, JSONObject json) {
		MyRequestDailog.closeDialog();
	}

    public abstract void  onReadSuccess(List<T> list);
}
