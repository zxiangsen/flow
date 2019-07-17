package com.douya.config;

import com.douya.utils.DES;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.*;


/**
 * Created by lb on 2018/1/31.
 */
public class MyHttpRequestWrapper extends HttpServletRequestWrapper {
	private byte[] data;
	private Map<String, String> headers;
	private Map<String, String[]> parameters;

	public MyHttpRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		//init();
		headers = new HashMap<>();
		//parameters = getRequestMap(request);
		parameters = request.getParameterMap();//参考：http://blog.csdn.net/comliu/article/details/1586174

		Enumeration enumeration1 = request.getParameterNames();
		String value1 = null;
		while (enumeration1.hasMoreElements()) {
			String name = (String) enumeration1.nextElement();
			value1 = request.getParameter(name);
			//URLDecoder.decode(value, "UTF-8") 服务器会自动解码一次，所以需要前端编码一次密文
			value1 = DES.decryptDES(value1);
			System.out.println(value1);
		}

		//GET方法
		if ("GET".equals(request.getMethod().toUpperCase())) {
			Enumeration enumeration = request.getParameterNames();
			String value = null;
			while (enumeration.hasMoreElements()) {
				String name = (String) enumeration.nextElement();
				value = request.getParameter(name);
				//URLDecoder.decode(value, "UTF-8") 服务器会自动解码一次，所以需要前端编码一次密文
				value = DES.decryptDES(value);
				Map<String, Object> map = assembleParamsMap(value);
				addAllParameter(map);
			}
		} else if ("POST".equals(request.getMethod().toUpperCase())) {
			String body = new String(IOUtils.toByteArray(request.getInputStream()), "UTF-8");
			JSONObject jsonObject = JSONObject.fromObject(body);
			//解码后解密
			data = DES.decryptDES(URLDecoder.decode((String) jsonObject.get("key"))).getBytes("UTF-8");
		}

	}

	/**
	 * 数据格式：phone=18320134174&password=e10adc3949ba59abbe56e057f20f883e&type=1&mark=1&registrationId=1234567
	 * @param value
	 * @return
	 */
	public Map<String, Object> assembleParamsMap(String value) {
		Map<String, Object> map;
		String[] params = value.split("&");
		if (params != null && params.length > 0) {
			map = new HashedMap();
			String[] str;
			for (String param : params) {
				str = param.split("=");
				if (str != null && str.length > 0) {
					if (str.length == 1) {
						map.put(str[0], null);//防止传字段不传值
					} else
						map.put(str[0], str[1]);
				}
			}
			return map;
		}
		return null;
	}


	public void putHeader(String name, String value){
		this.headers.put(name, value);
	}

	/**
	 *  重写getHeader方法，在拦截器中调用时则调用此方法
	 * @param name
	 * @return
	 */
	@Override
	public String getHeader(String name) {
		String headerValue = headers.get(name);

		if (headerValue != null){
			return headerValue;
		}
		return ((HttpServletRequest) getRequest()).getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		Set<String> set = new HashSet<>(headers.keySet());

		Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
		while (e.hasMoreElements()) {
			String n = e.nextElement();
			set.add(n);
		}

		return Collections.enumeration(set);
	}

	public void addParameter(String key, Object value) {
		try {
			if(value != null) {
				Method method = parameters.getClass().getMethod("setLocked",new Class[]{boolean.class});
				method.invoke(parameters, new Object[]{new Boolean(false)});
				if(value instanceof String[]) {
					parameters.put(key , (String[])value);
				}else if(value instanceof String) {
					parameters.put(key , new String[] {(String)value});
				}else {
					parameters.put(key , new String[] {String.valueOf(value)});
				}
				method.invoke(parameters, new Object[]{new Boolean(true)});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加多个参数
	 * @param map
	 */
	public void addAllParameter(Map<String, Object> map) {
		for(Map.Entry<String , Object> entry : map.entrySet()) {
			addParameter(entry.getKey() , (String) entry.getValue());
		}
	}

	@Override
	public String getParameter(String name) {
		String[] values = parameters.get(name);
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}

	@Override
	public String[] getParameterValues(String name) {
		return parameters.get(name);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new MyInputStream(new ByteArrayInputStream(data));
	}

	private class MyInputStream extends ServletInputStream {
		private InputStream inputStream;

		public MyInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public int read() throws IOException {
			return inputStream.read();
		}
	}
}