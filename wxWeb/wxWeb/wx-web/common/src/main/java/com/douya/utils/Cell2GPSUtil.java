package com.douya.utils;

import com.douya.exception.RestException;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;


/**
 * Created by HASEE on 2018/3/13.
 */
public class Cell2GPSUtil {

	private static String apikey = "0E88D8DB83EC6A90400AFBC02E41764C"; //订阅 key
	private static String oid = "7613";                                    //订阅 id
	private static String hex = "16";                                        //10进制或16进制，MCC / MNC / dbm 必须为10进制。
	private static String output = "json";                                //返回数据格式

	private static Cell2GPSUtil cell2GPSUtil = new Cell2GPSUtil();

	private String getWebData(String domain) {
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		try {
			java.net.URL url = new java.net.URL(domain);
			is = url.openStream();
			isr = new InputStreamReader(is, "utf-8");
			in = new BufferedReader(isr);
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line).append("\n");

			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
				if (isr != null) {
					isr.close();
					isr = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * @param mnc
	 * @param lac
	 * @param cell
	 * @return
	 */
	private static Map getGPS(String mnc, String lac, String cell, String mcc) {
		//http://api.gpsspg.com/bs/?oid=7605&key=254421798BF8969A5FFEE2CEE555B26C&bs=460,00,18be,9172&hex=16&to=1&output=xml
		String url = "http://api.gpsspg.com/bs/?oid=" + oid + "&key=" + apikey + "&bs=" + mcc + "," + mnc + "," + lac + "," + cell + "&hex=" + hex + "&to=1&output=" + output;
		String data = cell2GPSUtil.getWebData(url);
		System.out.println(String.format("========请求基站获取的数据为:%s", data));
		Map<String, Object> map = JSONObject.fromObject(data);//解析json字符串获取经纬度
		if ((int) map.get("count") <= 0) {
			throw new RestException("获取辅助定位数据失败.状态码为:" + map.get("status"));
		}
		System.out.println("============获取辅助定位信息成功===========,状态码:" + map.get("status"));
		return map;
	}

	public static void main(String[] args) {
		getCoordinates("+CENG: 0,460,00,25dc,c34b,19,22");
	}

	public static Map getCoordinates(String domain) {
		System.out.println(String.format("===========请求基站数据为:%s==========", domain));
		domain = domain.replaceAll("~", "");
		if (domain.length() <= 0) {
			return null;
		}
		//1.根据获取到的字符串进行逗号分割
		String[] xmls = domain.replaceAll("\"", "").split(",");
		String mnc = "";
		String lac = "";
		String cell = "";
		String mcc = "";
		for (int i = 0; i < xmls.length; i++) {
			if (xmls[i].length() > 0 && i == 1) {
				mcc = xmls[1];
			} else if (xmls[i].length() > 0 && i == 2) {
				mnc = xmls[2];
			} else if (xmls[i].length() > 0 && i == 3) {
				lac = xmls[3];
			} else if (xmls[i].length() > 0 && i == 4) {
				cell = xmls[4];
			} else {
				//待拓展标签
			}
		}
		if (cell.equals("") || mnc.equals("") || lac.equals("") || mcc.equals("")) {
			throw new RestException("辅助定位获取数据错误!!");
		}
		return getGPS(mnc, lac, cell, mcc);
	}

}