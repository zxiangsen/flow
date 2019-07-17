package com.douya.utils;

import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsx
 */
public class OpenIdUtil {
    public static String oauth2GetOpenid(String code) {

       /* switch (classify){
            case "1":
                //自己的配置appid
                appid = "**********";
                //自己的配置APPSECRET;
                appsecret = "**********";
                break;
            case "2":
                appid = "**********";
                appsecret = "************";
                break;
            case "3":
                appid = "**********";
                appsecret = "************";
                break;
            case "4":
                appid = "**********";
                appsecret = "************";
                break;
            case "5":
                appid = "**********";
                appsecret = "************";
        }*/

        //授权（必填）
        String grant_type = "authorization_code";
        //URL
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        //请求参数
        String params = "appid=" + WXPayConstants.APPID + "&secret=" + WXPayConstants.APP_SECRET + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String data = HttpUtil.get(requestUrl, params);
        //解析相应内容（转换成json对象）
        JSONObject  json = JSONObject.fromObject(data);
        //用户的唯一标识（openid）
        String Openid =String.valueOf(json.get("openid"));
        //System.out.println(Openid);
        return Openid;
    }
}