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
 	
 	  /*关于我们*/
 		public static void getAboutUs(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.GETABOUTUS),params,  handler);
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
    
    /*用户注册*/
    public static void register(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.REGISTER), params, handler);
    }
    
    /*重置密码*/
    public static void resetPassword(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.RESETPASSWORD), params, handler);
    }
    
    /*修改昵称*/
    public static void resetUsername(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.RESETUSERNAME), params, handler);
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
    
    /*获取餐厅、酒店、商铺评论列表*/
    public static void getCommentsList(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.COMMENTLIST), params, handler);
    }
   
    /*获取默认的拼车信息*/
    public static void getList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETCARLIST), params, handler);
    }
   
//    /*获取默认的拼车详情信息*/
//    public static void getInfo(AsyncHttpResponseHandler handler, RequestParams params){
//    	client.post(UrlContants.getUrl(UrlContants.CARINFO), params, handler);
//    }
    /*获取默认的车主 拼车信息*/
    public static void offerCar(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.OFFERCAR), params, handler);
    }
    
    /*获取默认的用户 拼车信息*/
    public static void needcar(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.NEEDCAR), params, handler);
    }
    /*获取默认的用户 拼车信息*/
    public static void postcaroder(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.POSTCARORDER), params, handler);
    }

    /*获取城市列表*/
    public static void getArea(AsyncHttpResponseHandler handler){
    	client.post(UrlContants.getUrl(UrlContants.GETAREA), handler);
    }

    /*提交餐饮、酒店*/
    public static void submit(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.SUBMIT), params, handler);
    }

    /*获取产品详情*/
    public static void getGoods(AsyncHttpResponseHandler handler, String goodId){
    	client.get(UrlContants.getUrl(UrlContants.GETGOODS)+"&id="+goodId, handler);
    }
    
    /*获取便民列表*/
    public static void getBianMinList(AsyncHttpResponseHandler handler){
        client.post(UrlContants.getUrl(UrlContants.GETBIANMINLIST), handler);
        }
    /*获取招聘类别*/
    public static void getZhaoPinCategory(AsyncHttpResponseHandler handler){
    	client.post(UrlContants.getUrl(UrlContants.GETZHAOPINCATEGORY), handler);
    }
    /*获取招聘列表*/
    public static void getZhaoPinList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETZHAOPINLIST), params, handler);
    }
    /*发布招聘信息*/
    public static void SubmitZhaoPinInfo(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.SUBMITZHAOPININFO), params, handler);
    }
    /*获取招聘详情*/
    public static void GetZhaoPinInfo(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETZHAOPININFO), params, handler);
    }
    /*获取房产列表*/
    public static void getHouseList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETHOUSELIST), params, handler);
    }
    /*发布房产信息*/
    public static void submitHouseInfo(AsyncHttpResponseHandler handler, String params){
    	client.get(UrlContants.getUrl(UrlContants.SUBMITHOUSEINFO) + params, handler);
    }
    /*获取房产详情*/
    public static void getHouseInfo(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETHOUSEINFO), params, handler);
    }
    /*获取供求列表*/
    public static void getSupplyDemandList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETSUPPLYDEMANDLIST), params, handler);
    }
    /*发布供求信息*/
    public static void submitSupplyDemandInfo(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.SUBMITSUPPLYDEMANDINFO), params, handler);
    }
    /*获取供求详情*/
    public static void getSupplyDemandInfo(AsyncHttpResponseHandler handler, String demandId){
    	client.post(UrlContants.getUrl(UrlContants.GETSUPPLYDEMANDINFO)+"&id="+demandId, handler);
    }
    /* 添加收藏的餐饮、酒店    */
    public static void addCollection(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.ADDCOLLECTION), params, handler);
    }
    
    /*获取收藏列表*/
    public static void getCollectionList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETCOLLECTIONLIST), params, handler);
    }
    
    /*删除收藏列表*/
    public static void deleteCollectionList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.DELETECOLLECTIONLIST), params, handler);
    }
    
    /*获取收藏详情？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？*/
    public static void getCollectionInfo(AsyncHttpResponseHandler handler, String collectionId){
    	client.get(UrlContants.getUrl(UrlContants.GETCOLLECTIONINFO), handler);
    }

    
    /*获取产品评论列表*/
    public static void getGoodsCommentsList(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GOODSCOMMENTSLIST), params, handler);
    }

    /*提交团购*/
    public static void submitShopOrder(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.SUBMITSHOPORDER), params, handler);
    }
    

    /*积分商场*/
    public static void getLoginUrl(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETLOGINURL), params, handler);
    }

    /*积分商场*/
    public static void getDrawUrl(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.GETDRAWURL), params, handler);
    }

    /*是否签到*/
    public static void usersign(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.USERSIGN), params, handler);
    }

    /*申请提现*/
    public static void posttixian(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.POSTTIXIANLIST), params, handler);
    }

    /*更新状态*/
    public static void updateorder(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.UPDATEORDER), params, handler);
    }

    /*提交评论*/
    public static void postComments(AsyncHttpResponseHandler handler, String params){
    	client.get(UrlContants.getUrl(UrlContants.POSTCOMMENTS)+params, handler);
    }

    /*删除订单*/
    public static void delOrder(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.DELORDER), params, handler);
    }
    
    /*单张图片上传*/
    public static void uploadone(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.UPLOADONE), params, handler);
    }
    
    /*支付*/
    public static void orderPay(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.ORDERPAY, params, handler);
    }
    
    /*获取酒店详情*/
    public static void getShopInfo(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.getUrl(UrlContants.SHOPDETAIL), params, handler);
    }
    
    /*删除收藏*/
    public static void delCollectionInfo(AsyncHttpResponseHandler handler, RequestParams params){
    	client.post(UrlContants.DELCOLLECTIONINFO, params, handler);
    }
}
