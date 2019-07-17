package com.easemob.server.example.comm;


import com.easemob.server.example.api.impl.EasemobAuthToken;
import com.easemob.server.example.api.impl.EasemobChatMessage;
import com.easemob.server.example.api.impl.EasemobIMUsers;
import com.easemob.server.example.api.impl.EasemobSendMessage;
import io.swagger.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by easemob on 2017/3/31.
 */
public class OrgInfo {

    public static String ORG_NAME ="1110190703055972";
    public static String APP_NAME = "qazwsxedcrfvtgbyhnujmikolpzhai";
    public static final Logger logger = LoggerFactory.getLogger(OrgInfo.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHH");

    private EasemobIMUsers ease = new EasemobIMUsers();

     // 新增IM用户 官方 用户名/密码
    public void insertUser()throws Exception{

        RegisterUsers users = new RegisterUsers();
        Object object = null;
        io.swagger.client.model.User payload = new io.swagger.client.model.User().username("9525").password("452782876");
        users.add(payload);
         //添加  IM用户信息
         object= ease.createNewIMUserSingle(users);
        System.out.println(object);
    }

    // 新增IM用户 私人 用户名/密码/昵称
    public void insertNewUser()throws Exception{

        RegisterUsers users = new RegisterUsers();

        // 添加 IM用户 /补充昵称
        com.easemob.server.example.comm.User user = new com.easemob.server.example.comm.User();
        user.setUsername("9534");
        user.setPassword("452782876");
        user.setNickname("9533");
        users.add(user);
        Object object = ease.createNewIMUserSingle(users);
        System.out.println(object);
    }

    // 获取 IM用户信息
    public void getUser()throws Exception{

        Object object =ease.getIMUserByUserName("9527");

        System.out.println(object);

    }

    // 修改昵称
    public void UserNickName()throws Exception{

        Nickname nickname  = new Nickname();
        nickname.setNickname("客服1号");

        Object object =  ease.modifyIMUserNickNameWithAdminToken("9533",nickname);
        System.out.println(object);

    }

    // 修改环信密码
    public void UpdatePassword()throws Exception{

        Nickname nickname  = new Nickname();
        nickname.setNickname("客服1号");

        Object object =  ease.modifyIMUserNickNameWithAdminToken("9533",nickname);
        System.out.println(object);

    }

    // 删除IM用户
    public void deleteIMUser()throws Exception{

        Object object =  ease.deleteIMUserByUserName("9532");
        System.out.println(object);

    }

    // 获取 token
    public void AuthToken()throws Exception{

        EasemobAuthToken eat = new EasemobAuthToken();
        Object object = eat.getAuthToken();
        System.out.println(object);
    }

    // 发送文本信息
    public void Message()throws Exception{
        // 发送文本信息
        EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
        Msg msg = new Msg();

        //users[用户]/ chatgroups[聊天组] / chatrooms[聊天室]
        msg.setTargetType("users");

        UserName userName = new UserName();
        userName.add("zhangsan");// 信息接收用户
        msg.setTarget(userName);

        // 发送内容/类型
        MsgContent msgContent = new MsgContent();
        msgContent.setType(MsgContent.TypeEnum.TXT);
        msgContent.setMsg("测试内容12356");
        msg.setMsg(msgContent);

        msg.setFrom("9527");
        //msg.setExt();
        //msg.setAction("");

        Object object = easemobSendMessage.sendMessage(msg);
        System.out.println(object);
    }

    // 获取聊天内容 文件
    public void ChatMessage()throws Exception{

        // 获取聊天记录
        EasemobChatMessage easemobChatMessage = new EasemobChatMessage();

        // 根据时间条件下载历史消息文件
        Object object = easemobChatMessage.exportChatMessages("2019070814");
        System.out.println(object);

    }

    // 获取聊天内容2.0
    public void ChatMessage2()throws Exception{

        // 获取聊天记录
        EasemobChatMessage easemobChatMessage = new EasemobChatMessage();
        // 根据时间条件下载历史消息文件
        String senvenDayAgo = String.valueOf(System.currentTimeMillis() - 15 * 24 * 60 * 60 * 1000);

        Object object = easemobChatMessage.exportChatMessages(null,null,"select * where timestamp>"+senvenDayAgo);

        System.out.println(object);

    }

    // 获取聊天内容3.0
    public void ChatMessage3()throws Exception{

        EasemobChatMessage easemobChatMessage = new EasemobChatMessage();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        String timeStr = SDF.format(calendar.getTime());
        Object object = easemobChatMessage.exportChatMessages("2019070814");
        if (null == object) {
            logger.info("Failed to get expected response by calling GET chatmessages API, maybe there is no chatmessages history at {}", timeStr);
        } else {
            logger.info(object.toString());
        }
        //System.out.println(object);

    }

    public static void main(String[] args) throws Exception {
       new OrgInfo().ChatMessage2();
    }

}
