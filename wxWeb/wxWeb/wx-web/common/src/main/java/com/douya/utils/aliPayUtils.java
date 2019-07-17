package com.douya.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HASEE on 2018/4/8.
 */
public class aliPayUtils {

	public static String verifyResponse(String notify_id,String partner) {
		//获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

		String veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;

		return checkUrl(veryfy_url);
	}


	/**
	 * 获取远程服务器ATN结果
	 * @param urlvalue 指定URL路径地址
	 * @return 服务器ATN结果
	 * 验证结果集：
	 * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
	 * true 返回正确信息
	 * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	public static String checkUrl(String urlvalue) {
		String inputLine = "";
		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
					.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = "";
		}
		return inputLine;
	}
}