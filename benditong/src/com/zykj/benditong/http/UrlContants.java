package com.zykj.benditong.http;

/**
 * @author Administrator
 * 服务器路径
 */
public class UrlContants {

    public static final String SERVERIP = "121.42.194.222";

    public static final String BASE_URL = "http://121.42.194.222/api.php?";

    public static final String BASEURL = BASE_URL+"%s";

    public static final String jsonData = "datas";

    public static final String ERROR = "{\"code\":400,\"message\":\"请求失败\",\"datas\":null}";

    public static final String GETADSLIST = "c=public&a=getAdsList&type=slideFocus";//轮播图
    
    public static final String GETORDERLIST = "c=info&a=getOrderList";//获取订单
    
    public static final String GETORDER = "c=info&a=getOrder";//获取订单详情

    public static final String LOGIN = "c=user&a=login";//登录
    
    public static final String LIKELIST =  "c=info&a=getLikeList";//猜你喜欢
    
    public static final String POSIUSERAVATAR = "c=user&a=postUserAvatar";//猜你喜欢
    
    public static final String GETCATEGORY = "c=info&a=getcategory";//获取餐饮、酒店、商铺分类

    public static final String GETRESTAURANTS = "c=info&a=getlist";//获取餐饮、酒店、商铺列表
    
    public static final String GETGOODLIST = "c=info&a=getgoodslist";//获取餐饮、酒店、商铺商品
    
    public static final String COMMENTLIST = "c=info&a=getCommentsList";//获取评价列表
    
    public static final String CARLIST = "c=info&a=getlist";//获取列表
    
    public static final String CARINFO = "c=info&a=getinfo";//获取列表
    
    public static final String NEEDCAR = "c=car&a=needcar";//我要拼车
    
    public static final String OFFERCAR = "c=car&a=offercar";//我要拼车
    
    public static String getUrl(String token){
        if(token==null || token.equals("")){
            return BASE_URL;
        }
      return  String.format(BASEURL,token);
    }
}
