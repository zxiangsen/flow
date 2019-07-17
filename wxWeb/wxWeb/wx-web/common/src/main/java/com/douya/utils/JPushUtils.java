package com.douya.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;

/**
 * Created by HASEE on 2017/12/5.
 */
public class JPushUtils {

    protected static final Logger LOG = LoggerFactory.getLogger(JPushUtils.class);

    //TODO 配置文件转移
    private static final String userAppkey = "b65efe003269c0d4732b1269";

    private static final String userMasterSecret = "318de2ab9e19b1a2c6c1e63a";

    private static final String clientAppKey = "5fbe339de20eb30aa21cfbac";

    private static final String clientMasterSecret = "426aff978489238669f37a24";

    private static final SMSClient client = new SMSClient(userMasterSecret,userAppkey);

    /*
	* 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	* 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	* 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
	*/
    private static int timeToLive =  60 * 60 * 24;

    public static final  JPushClient userJpush = new JPushClient(userMasterSecret,userAppkey);

    public static final  JPushClient clientJpush = new JPushClient(clientMasterSecret,clientAppKey);

    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = (int) MAX/2;
    /**
     * 保持 sendNo 的唯一性是有必要的
     */
    public static int getRandomSendNo() {
        return (int) (MIN + Math.random() * (MAX - MIN));
    }

    /**
      * 发送短信验证码
      * phone :
      *   要发送验证码的手机号
      * return :
      *   String msg_id;
    * */
    public static String sendSMSCode(String phone) {
        SMSPayload payload
                = SMSPayload.newBuilder()

                .setMobileNumber(phone)

                .setTempId(146619)

                .build();
        String result = null;
        try {
            SendSMSResult res = client.sendSMSCode(payload);

            LOG.info(res.toString());
            result = res.getMessageId();

        } catch (APIConnectionException e) {

            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {

            LOG.error("Error response from JPush server. Should review and fix it. ", e);

            LOG.info("HTTP Status: " + e.getStatus());

            LOG.info("Error Message: " + e.getMessage());

        }
        return result;//返回的是"msg_id"对应的值
    }

    /**
      * 验证码校验
      * msg_id : 获取验证码时返回的参数结果
      * valid  : 用户输入的验证码
      * return boolean;
    * */
    public static Boolean sendValidSMSCode(String msgId,String phone) {
        Boolean boo = false;
        SMSClient client = new SMSClient(userMasterSecret, userAppkey);
        try {
            ValidSMSResult res = client.sendValidSMSCode(msgId,phone);
            System.out.println(res.toString());
            LOG.info(res.toString());
            boo = res.getIsValid();
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
        return boo;
    }

	/**
	 * 推送单个用户
	 */
	public static void main(String[] args) {
		//PushPayload pushPayload = sendByRegistrationId("160a3797c8295e33c04", "title", "1", "1");

        //用户
		//JPushClient jPushClient = new JPushClient(userMasterSecret, userAppkey);
        //sendPush(jPushClient,"18071adc0320146d00b","哈哈哈--","1","1");

        //商家
        JPushClient jPushClient = new JPushClient(clientMasterSecret,clientAppKey );
        sendPush(jPushClient,"1507bfd3f7cce1b104e","哈哈哈","1","1","payVoice");

        //发送验证码
		//sendSMSCode("15673226634");

        //推送全部用户
        //sendAll("test----");
	}

    public static void sendPush(JPushClient jPushClient,String registrationId,String notification_title, String orderValue,String statusValue,String sound){
        try {
            System.out.println(jPushClient.sendPush(sendByRegistrationId(registrationId,notification_title,orderValue,statusValue,sound)));
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param registrationId 极光id
     * @param notification_title 显示相应标题
     * @return
     */
    public static PushPayload sendByRegistrationId(String registrationId,String notification_title, String orderValue,String statusValue,String sound) {

                 //创建一个IosAlert对象，可指定APNs的alert、title等字段
                 //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
                 return PushPayload.newBuilder()
                         //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                         .setPlatform(Platform.all())
                         //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                         .setAudience(Audience.registrationId(registrationId))
                         //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                         .setNotification(Notification.newBuilder()
                                         //指定当前推送的android通知
                                         .addPlatformNotification(AndroidNotification.newBuilder()
                                                        .setAlert(notification_title)
                                                        //.setTitle(notification_title) 注释
                                                        //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                                        .addExtra("orderId",orderValue)
                                                        .addExtra("sound",sound)
                                                        .addExtra("status",statusValue)
                                                        .build())
                                         //指定当前推送的iOS通知
                                         .addPlatformNotification(IosNotification.newBuilder()
                                                       //传一个IosAlert对象，指定apns title、title、subtitle等
                                                       .setAlert(notification_title)
                                                       //直接传alert
                                                       //此项是指定此推送的badge自动加1
                                                       .incrBadge(1)
                                                       //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                                       // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                                       .setSound("sound.caf")
                                                       //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                                         .addExtra("orderId",orderValue)
                                                         .addExtra("status",statusValue)
                                                         //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                                         //取消此注释，消息推送时ios将无法在锁屏情况接收
                                                         // .setContentAvailable(true)
                                                         .build())
                                       .build())
                    //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                    // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                    // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                    .setMessage(Message.newBuilder()

                                       .setMsgContent("MsgContent")

                                       .setTitle("title")

                                       .addExtra("message extras key","null")
                                        .build())

                    .setOptions(Options.newBuilder()
                                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                                        .setApnsProduction(false)
                                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                                        .setSendno(1)
                                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                                        .setTimeToLive(86400)
                                       .build())
                         .build();
             }

    /**
     * 推送全部用户
     */
    public static void sendAll(String title){
        try {
            System.out.println(userJpush.sendNotificationAll(title).getResponseCode());;
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }


}
