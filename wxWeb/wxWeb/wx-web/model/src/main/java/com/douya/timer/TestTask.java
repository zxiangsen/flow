package com.douya.timer;

import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestTask extends TimerTask{
    Logger logger = Logger.getLogger(this.getClass());

    // 定时任务类
    private Timer timer;

    public void test() {
        timer = new Timer();
        timer.schedule(this, 20, 30000);//30 秒
    }

    @Override
    public void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        System.out.println("定时器测试..");// new Date()为获取当前系统时间
        logger.error("定时器测试:"+df.format(new Date()));

    }

}
