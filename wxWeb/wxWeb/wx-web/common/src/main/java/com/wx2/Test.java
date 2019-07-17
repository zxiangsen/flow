package com.wx2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 成功 收不到信息
 * https://www.cnblogs.com/2YSP/p/7158039.html
 */
public class Test {
     public static final String URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";

     public static void main(String[] args) {
         //获取token
         //AccessToken token = WeiXinUtil.getAccessToken();

         String token = "23_4NylI7oWy-g5QKpyT2udBMySSYqi148wqHNZIQPS9U6Tp20LdIZWuRlChP7MtFL33fq9Ye4zHE7d3fHYR3KkwQ8kIvE2MOEDfPyALyZoT37C612PyH5XNLspSXLxZQGsU-sciM2vjRPOzHwmOURjAJAWCZ";
         String url = URL.replace("ACCESS_TOKEN", token);


         MassMessage massMessage = new MassMessage();
         List<String> list = new ArrayList<String>();
         list.add("o6XTz0hRZtHuBraB_RC1C_dnTPJI");
         list.add("o6XTz0hRZtHuBraB_RC1C_dnTPJI");
         massMessage.setTouser(list);
         massMessage.setMsgtype("text");
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("content", "hello，<a href='https://www.baidu.com'>点我去百度了</a>");
         massMessage.setText(map);

         Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
         String str = gson.toJson(massMessage);
         System.out.println("str:" + str);
         JSONObject jso = WeiXinUtil.doPost(url, str);
         System.out.println(jso.toString());
     }
 }