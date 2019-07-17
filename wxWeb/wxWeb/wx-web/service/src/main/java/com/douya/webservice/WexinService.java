package com.douya.webservice;

import com.douya.base.BaseService;
import com.douya.base.Constants;
import com.douya.easemob.easemobUserUtils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by lb on 2017/12/4.
 * 微信 分享
 */
@Service
@Transactional(readOnly = false)
public class WexinService extends BaseService {
	private Logger logger = LoggerFactory.getLogger(WexinService.class);

    // 注意 IP白名单 添加IP


    //开发者ID
	private String AppID= "wx495ceaf465694382";
    //开发者密码
	private	String	AppSecret= "11c26554e53706e4ae10c7531b63c45c";

	private String Token_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private String Ticket_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";


	private String token = "";

	private String Ticket = "";

	// 1 GET access_token
	public String getToken()throws Exception {
		String url = Token_URL.replace("APPID",AppID).replace("APPSECRET",AppSecret);
		logger.error("getToken [URL] -- "+url);
		JSONObject jsonObj = doGetStr(url);
		if(!StringUtils.isEmpty(jsonObj)){
			logger.error("getToken:"+jsonObj);
		/*	if(jsonObj.get("errcode").toString().equals("40164")){
              logger.error("服务器IP发生变更,请更新IP白名单");
			   return "XXX";
			}*/
			return jsonObj.getString("access_token");
		}
		return "XXX";
	}

	// 2 GET jsapi_ticket
	public String getJsTicket(String access_token) throws Exception {
		String url = Ticket_URL.replace("ACCESS_TOKEN",access_token);

		logger.error("getJsTicket -- URL:"+url);
		JSONObject jsonObj = doGetStr(url);
		if (!StringUtils.isEmpty(jsonObj)) {
			logger.error("getJsTicket:"+jsonObj);
			return jsonObj.getString("ticket");
		 }
		 return "XXX";
	  }

	// 3: 根据 ticket 生成 JS-SDK权限验证的签名
	public  Map<String, String> sign(String url) throws Exception{
			Map<String, String> ret = new HashMap<String, String>();
			String nonce_str = create_nonce_str();
			String timestamp = create_timestamp();
			String string1;
			String signature = "";

		    String token = getToken();
		    String jsapi_ticket = getJsTicket(token);
            //String jsapi_ticket = Ticket;
			//注意这里参数名必须全部小写，且必须有序
			string1 = "jsapi_ticket=" + jsapi_ticket +
					"&noncestr=" + nonce_str +
					"&timestamp=" + timestamp +
					"&url=" + url;
			System.out.println(string1);
			try
			{
				MessageDigest crypt = MessageDigest.getInstance("SHA-1");
				crypt.reset();
				crypt.update(string1.getBytes("UTF-8")); //对string1 字符串进行SHA-1加密处理
				signature = byteToHex(crypt.digest());  //对加密后字符串转成16进制
			}
			catch (NoSuchAlgorithmException e){
				e.printStackTrace();
			}catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
			ret.put("appId",AppID);
			ret.put("url", url);
			ret.put("jsapi_ticket", jsapi_ticket);
			ret.put("nonceStr", nonce_str);
			ret.put("timestamp", timestamp);
			ret.put("signature", signature);
			return ret;
	}

	// 本地测试专用
	public  Map<String, String> sign2(String ticket,String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		String jsapi_ticket = ticket;
		//注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket +
				"&noncestr=" + nonce_str +
				"&timestamp=" + timestamp +
				"&url=" + url;
		System.out.println(string1);
		try
		{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8")); //对string1 字符串进行SHA-1加密处理
			signature = byteToHex(crypt.digest());  //对加密后字符串转成16进制
		}
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		return ret;
	}

    // 最新 获取签名
 	public  Map<String, String> sign3(String url) throws Exception{
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
        if(token.equals("")){
        	logger.error("获取签名时,判断定时器失效.");
			token = getToken();
			Ticket = getJsTicket(token);
		}
		//String jsapi_ticket = Ticket;
		//注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + Ticket +
				"&noncestr=" + nonce_str +
				"&timestamp=" + timestamp +
				"&url=" + url;
		System.out.println(string1);
		try
		{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8")); //对string1 字符串进行SHA-1加密处理
			signature = byteToHex(crypt.digest());  //对加密后字符串转成16进制
		}
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		ret.put("appId",AppID);
		ret.put("url", url);
		ret.put("jsapi_ticket", Ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		return ret;
	}

	// 调用 微信官方接口
	public JSONObject doGetStr(String url) throws IOException{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet  httpGet = new HttpGet(url);//HttpGet使用Get方式发送请求URL
		JSONObject jsonObj = null;
		HttpResponse  res = httpClient.execute(httpGet);//使用httpClient从Client执行httpGet的请求
		HttpEntity  entity = res.getEntity();//从HttpResponse中获取结果
		if(!StringUtils.isEmpty(entity)){
			String result =   EntityUtils.toString(entity,"utf-8");
			jsonObj = JSONObject.fromObject(result);//字符串类型转换为JSON对象
		}
		return jsonObj;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	//生成随机字符串
	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}
	//生成时间戳字符串
	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}



	// 定时器 更新 数据
    public void updateSign() throws Exception{
		WexinService ws = new WexinService();
		token =ws.getToken();
        logger.error("GET -- token  -- "+token);
        Ticket = ws.getJsTicket(token);
	    logger.error("GET -- Ticket -- "+Ticket);
	}

	public static void main(String[] args) throws Exception {
/*		WexinService ws = new WexinService();
		String token =ws.getToken();
        System.out.println("token:"+token);
	    String ticket = ws.getJsTicket(token);
		//String ticket = "LIKLckvwlJT9cWIhEQTwfBmqjbgGEOCYnmTLoNRNFsIdmd4N_BWqjgcPuUj_gVJ8L9NRk6R44Gnxq2LwzvZz9w";
		//String url = "http://www.w0ejp2u.top/ckb/indexA2.html";
		String url = "http://www.w0ejp2u.top/wx1/indexA3.html";
		Map map = new HashMap();
		map = ws.sign2(ticket,url);
		System.out.println(map);*/

	/*	String str = "https://www.cnblogs.com/alisapan/p/6490590.html";
		String[]  strs=str.split("#");
		System.out.println(strs[0]);*/

	    // 1 2 3 4 5 6 7

        long sd = System.currentTimeMillis() - 24L * 3600 * 1000;
        System.out.println(sd);
	}


}

