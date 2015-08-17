package com.zykj.benditong.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



/**
 * Created by csh on 15-7-21.
 */
public class HttpUtils {

    private HttpUtils(){

    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        //使用默认的 cacheThreadPool
        client.setTimeout(15);
        client.setConnectTimeout(15);
        client.setMaxConnections(20);
        client.setResponseTimeout(20);
    }
    
    /*轮播图*/
    public static void getAdsList(AsyncHttpResponseHandler handler){
        client.get(UrlContants.getUrl(UrlContants.GETADSLIST), handler);
    }
    
    /*轮播图*/
    public static void getOrderList(AsyncHttpResponseHandler handler, RequestParams params){
        client.get(UrlContants.getUrl(UrlContants.GETORDERLIST), params, handler);
    }
    
    /*用户登录*/
    public static void login(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.LOGIN), params, handler);
    }
    
    /*猜你喜欢*/
    public static void getLikeList(AsyncHttpResponseHandler handler, String num){
        client.get(UrlContants.getUrl(UrlContants.LIKELIST+"&num="+num), handler);
    }
    
    /*上传头像*/
    public static void postUserAvatar(AsyncHttpResponseHandler handler, RequestParams params){
        client.get(UrlContants.getUrl(UrlContants.POSIUSERAVATAR), params, handler);
    }
}
