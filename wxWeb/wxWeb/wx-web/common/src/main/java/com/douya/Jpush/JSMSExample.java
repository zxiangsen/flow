package com.douya.Jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.ApacheHttpClient;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.account.AccountBalanceResult;
import cn.jsms.api.account.AppBalanceResult;
import cn.jsms.api.common.model.BatchSMSPayload;
import cn.jsms.api.common.model.BatchSMSResult;
import cn.jsms.api.common.model.RecipientPayload;
import cn.jsms.api.schedule.model.ScheduleResult;
import cn.jsms.api.schedule.model.ScheduleSMSPayload;
import cn.jsms.api.template.SendTempSMSResult;
import cn.jsms.api.template.TempSMSResult;
import cn.jsms.api.template.TemplatePayload;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极光推送
 * 1：发送验证码
 * 2：校验验证码
 */
public class JSMSExample {

    protected static final Logger LOG = LoggerFactory.getLogger(JSMSExample.class);

    private static final String appkey = "b65efe003269c0d4732b1269";
    private static final String masterSecret = "318de2ab9e19b1a2c6c1e63a";

   // private static final String devKey = "242780bfdd7315dc1989fedb";
  //  private static final String devSecret = "2f5ced2bef64167950e63d13";

    public static void main(String[] args) {
    	   testSendSMSCode();
        //testSendValidSMSCode();
    }

    //发送短信验证码
    public static void testSendSMSCode() {
        Map map = new HashMap();
        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder().setMobileNumber("13169018176").setTempId(159196).build();
        LOG.info("payload:"+payload);
        try {
            SendSMSResult res = client.sendSMSCode(payload);
            LOG.info("sendSMSCode:"+res.toString());
            if(res.getMessageId() == null){//发送失败!
                map.put("status","Error");
                map.put("messageId",res.getMessageId());
                map.put("content","验证码发送失败请联系管理员!");
                //return map;
            }
            map.put("status","Success");
            map.put("messageId",res.getMessageId());
            map.put("content","");
            //return map;
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
        //return map;
    }


    //验证码校验
    public static void testSendValidSMSCode() {
        SMSClient client = new SMSClient(masterSecret, appkey);
        try {
            ValidSMSResult res = client.sendValidSMSCode("662105937517", "653613");
            System.out.println(res.toString());
            LOG.info(res.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 50010) {
                // do something
            }
            System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public Map testSendSMSCodeApp(String phone) {
        Map map = new HashMap();
        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder().setMobileNumber(phone).setTempId(159196).build();
        LOG.info("payload:"+payload);
        try {
            SendSMSResult res = client.sendSMSCode(payload);
            LOG.info("sendSMSCode:"+res.toString());
            if(res.getMessageId() == null){//发送失败!
                map.put("status","Error");
                map.put("messageId",res.getMessageId());
                map.put("content","验证码发送失败请联系管理员!");
                return map;
            }
            map.put("status","Success");
            map.put("messageId",res.getMessageId());
            map.put("content","");
            return map;
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
        return map;
    }

    public boolean testSendValidSMSCodeApp(String messageId,String code) throws Exception{
        boolean lean = false;
        SMSClient client = new SMSClient(masterSecret, appkey);
        ValidSMSResult res = null;
        try {
            res = client.sendValidSMSCode(messageId, code);
            if(res.getIsValid()){
                lean = true;
            }
            LOG.info(res.toString());
            return lean;
        } catch (APIConnectionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 50010) {
                // do something
            }
            System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }finally {
            LOG.error("res:"+res);
            return lean;
        }

    }


}