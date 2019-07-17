package com.wx;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.douya.utils.HttpClient;
import com.easemob.server.example.comm.OrgInfo;
import org.apache.http.client.utils.HttpClientUtils;

public class WxTemplateMsg {

           /**
          * @method packJsonmsg
          * @描述: TODO(封装微信模板:警告模板) 
          * @参数@param first  头部
          * @参数@param content  内容
          * @参数@param occurtime  发生时间
          * @参数@param remark  说明
          * @参数@return
          * @返回类型：JSONObject
          */
     public  static JSONObject packJsonmsg(String first, String content, String remark)throws Exception{
             JSONObject json = new JSONObject();
             try {
              JSONObject jsonFirst = new JSONObject();
              jsonFirst.put("value", first);
              jsonFirst.put("color", "#173177");
              json.put("first", jsonFirst);
             JSONObject WarningContent = new JSONObject();
              WarningContent.put("value", content);
              WarningContent.put("color", "#173177");
             json.put("content", WarningContent);
              JSONObject occurtime = new JSONObject();
              String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
                occurtime.put("value",str);
                occurtime.put("color", "#173177");
             json.put("occurtime", occurtime);
             JSONObject jsonRemark = new JSONObject();
              jsonRemark.put("value", remark);
              jsonRemark.put("color", "#173177");
              json.put("Remark", jsonRemark);
            } catch (JSONException e) {
             e.printStackTrace();
             }
            return json;
            }
        /**
      * @throws Exception 
      * @method sendWechatmsgToUser
      * @描述: TODO(发送模板信息给用户) 
      * @参数@param touser  用户的openid
      * @参数@param templat_id  信息模板id
      * @参数@param url  用户点击详情时跳转的url
      * @参数@param data  模板详情变量 Json格式
      * @参数@return
      * @返回类型：String
      */
     public  String sendWechatmsgToUser(String touser, String templat_id, String clickurl, JSONObject data) throws Exception{
             String token ="23_SPaKmPMUyFBbWHD_zm0JI5hwx4cmdMpjj_WMVG3cbNYOL5zL1WvyZo9FAPFpvlg0jrn1vZ4lb7NdkCYSKf82ltBPa6qng4-Tfc-WngoaGaiKv13SCxAs7i37BNI0bJMZYiorY0Yj7QXKJ68OGWJjADAZPC";
             //String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
             String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token;
             System.out.println("请求微信的Url"+url);
             JSONObject json = new JSONObject();
             Map<String,String> map = new HashMap<String,String>();
            try {
                map.put("touser", touser);
                map.put("template_id", templat_id);
                map.put("url", clickurl);
                map.put("data", "");
             } catch (JSONException e) {
             e.printStackTrace();
             }

             String result = HttpClientUtil.doPost(url, map,"utf-8");
             System.out.println("请求微信的Url 返回的结果"+result);
             try {
             JSONObject parseObject = JSONObject.parseObject(result);

            String errmsg = (String) parseObject.get("errmsg");
            if(!"ok".equals(errmsg)){ //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
             return "error";
             }
             } catch (JSONException e) {
            e.printStackTrace();
             }
             return "success";
             }



    public static void main(String[] args) throws Exception {
        WxTemplateMsg wxTemplateMsg = new WxTemplateMsg();
        String retMsg = wxTemplateMsg.sendWechatmsgToUser(
                "o6XTz0hRZtHuBraB_RC1C_dnTPJI",
                "模板id",
                "跳转的连接",
                packJsonmsg("", "内容", "说明"));
    }

}
