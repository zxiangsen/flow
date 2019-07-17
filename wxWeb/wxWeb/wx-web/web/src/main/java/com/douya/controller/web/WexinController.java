package com.douya.controller.web;

import com.douya.base.BaseController;
import com.douya.webservice.WexinService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import javax.security.auth.login.Configuration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@RestController
@RequestMapping(value = "/webemployee")
public class WexinController extends BaseController<WexinService>  {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 微信分享
	 * 请求参数: 当前页面URL
	 * 返回参数: 时间戳,字符串,签名,AppID
	 */

	@RequestMapping(value = "/signature")
	public Map del(String url) throws Exception{
		Map map = new HashMap();
		map = service.sign3(url);
		return map;
	}
	public static void main(String[] args) throws Exception {


	}



}