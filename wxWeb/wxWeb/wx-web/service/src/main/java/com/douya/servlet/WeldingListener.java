package com.douya.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


/**
 * Created by HASEE on 2017/12/13.
 */
public class WeldingListener extends HttpServlet {

/*	@Override
	public void init() throws ServletException {
		FutureTask<String> task = new FutureTask<String>(new Callable<String>(){
			@Override
			public String call() throws Exception {
				taskstart(); //使用另一个线程来执行该方法，会避免占用Tomcat的启动时间
				return "Collection Completed";
			}
		});
		new Thread(task).start();
	}

	public void taskstart(){
		//com.douya.servlet.Server.server.start();
	}*/
}