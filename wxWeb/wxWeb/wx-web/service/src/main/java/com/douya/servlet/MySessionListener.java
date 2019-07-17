package com.douya.servlet;

import com.douya.exception.RestException;
import com.douya.model.Login;
import com.douya.service.LoginService;
import com.douya.timer.TestTask;
import com.douya.time.timeSign;
import com.douya.utils.MySessionContext;
import com.douya.webservice.WexinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by lb on 2017/12/11.
 */
public class MySessionListener implements HttpSessionListener, ServletContextListener {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private WebApplicationContext webApplicationContext;
	@Autowired
	private LoginService loginService;


	@Override
	public void sessionCreated(HttpSessionEvent se) {
		MySessionContext.addSeesion(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		System.out.println("===================================>>>>>>>session过期：" + sessionEvent.getSession().getId());
		Login login = new Login();
		login.setSessionId(sessionEvent.getSession().getId());
		loginService.delete(login);
		MySessionContext.delSession(sessionEvent.getSession());
	}

	//容器初始化时调用，用于初始化service等资源
	//servlet和listener是由servlet容器(Tomcat)管理，而service，dao等却由spring容器管理，所以Tomcat无法识别@Autowired等注解，
	//也就无法注入bean，因此可以通过WebApplicationContext手动获取bean，前提是ContextLoaderListener在web中的初始化顺序必须在监听器之前
	@Override
	public void contextInitialized(ServletContextEvent sce) {


		logger.debug("web启动...");
        //new TestTask().test();
        new timeSign().test();
		//做定时器
		webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		if (webApplicationContext != null) {
			if (loginService == null) {
				loginService = (LoginService) webApplicationContext.getBean("loginService");
			}
		}else
			throw new RestException("初始化应用上下文失败!");
	}

	//服务器关闭时调用，用于关闭资源文件等
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("web关闭...");
	}
}