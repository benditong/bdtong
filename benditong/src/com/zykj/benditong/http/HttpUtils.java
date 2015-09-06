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
    public static void getAdsList(AsyncHttpResponseHandler handler, String type){
        client.get(UrlContants.getUrl(UrlContants.GETADSLIST)+"&id="+type, handler);
    }
    
    /*点单列表*/
    public static void getOrderList(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.GETORDERLIST), params, handler);
    }
    
    /*点单详情*/
    public static void getOrder(AsyncHttpResponseHandler handler, String orderid){
        client.get(UrlContants.getUrl(UrlContants.GETORDER)+"&id="+orderid, handler);
    }
    
    /*用户登录*/
    public static void login(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.LOGIN), params, handler);
    }
    
    /*猜你喜欢*/
    public static void getLikeList(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.LIKELIST), params, handler);
    }
    
    /*上传头像*/
    public static void postUserAvatar(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.POSIUSERAVATAR), params, handler);
    }
    
    /*获取分类*/
    public static void getcategory(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.GETCATEGORY), params, handler);
    }
    
    /*获取餐厅、酒店、商铺列表*/
    public static void getRestaurants(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.GETRESTAURANTS), params, handler);
    }
    
    /*获取餐厅、酒店、商铺商品*/
    public static void getgoodslist(AsyncHttpResponseHandler handler, String tid){
        client.get(UrlContants.getUrl(UrlContants.GETGOODLIST)+"&tid="+tid, handler);
    }
    
    /*获取餐厅、酒店、商铺商品*/
    public static void getCommentsList(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.COMMENTLIST), params, handler);
    }
   
    /*获取默认的拼车信息*/
    public static void getList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETCARLIST), params, handler);
    }
   
    /*获取默认的拼车详情信息*/
    public static void getInfo(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.CARINFO), params, handler);
    }
    /*获取默认的车主 拼车信息*/
    public static void offerCar(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.OFFERCAR), params, handler);
    }
    
    /*获取默认的用户 拼车信息*/
    public static void needcar(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.NEEDCAR), params, handler);
    }

    /*获取城市列表*/
    public static void getArea(AsyncHttpResponseHandler handler){
    	client.post(UrlContants.getUrl(UrlContants.GETAREA), handler);
    }

    /*获取城市列表*/
    public static void submit(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.SUBMIT), params, handler);
    }
}
