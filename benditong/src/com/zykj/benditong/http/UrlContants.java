package com.zykj.benditong.http;

/**
 * @author Administrator
 * 服务器路径
 */
public class UrlContants {

    public static final String BASE_URL = "http://121.42.194.222/api.php?";

    public static final String BASEURL = BASE_URL+"%s";

    public static final String jsonData = "datas";

    public static final String ERROR = "{\"code\":400,\"message\":\"请求失败\",\"datas\":null}";

    public static final String GETADSLIST = "c=public&a=getAdsList&type=slideFocus";//轮播图
    
    public static final String GETORDERLIST = "c=info&a=getOrderList";

    public static final String LOGIN = "c=user&a=login";//登录
    
    public static final String LIKELIST =  "c=info&a=getLikeList";//猜你喜欢
    
    public static final String POSIUSERAVATAR =  "c=user&a=postUserAvatar";//猜你喜欢
    
    public static String getUrl(String token){
        if(token==null || token.equals("")){
            return BASE_URL;
        }
      return  String.format(BASEURL,token);
    }
}
