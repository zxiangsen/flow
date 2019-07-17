package com.github.wxpay.sdk;

import com.douya.utils.WxPayConfig;
import org.json.JSONObject;
import org.json.XML;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class TestWXPay {

    private WXPay wxpay;
    private WXPayConfigImpl config;
    private String out_trade_no;
    private String total_fee;

     public TestWXPay() throws Exception {
          config      = WXPayConfigImpl.getInstance();
          wxpay       = new WXPay(config);
         total_fee    = "1";
         out_trade_no = "201701017496748980290321";
         out_trade_no = "201613091059590000003433-asd002";
    }

    /**
     * 扫码支付  下单
     */
    public void doUnifiedOrder()throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("appid", WxPayConfig.appid);//公众账号ID  是
        data.put("attach", "123");//公众账号ID  是
        data.put("body", "9527");  //商品描述  是
        data.put("mch_id", WxPayConfig.mch_id);//商户号   是
        data.put("out_trade_no", out_trade_no+"1"); // 商户订单号 是
        //data.put("device_info", "");// 设备号  否  自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("fee_type", "CNY");// 标价币种  否
        data.put("total_fee", "10");//  标价金额  是 订单总金额，单位为分，详见支付金额
        data.put("spbill_create_ip", "123.12.12.123");// 终端IP  是  APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
        data.put("notify_url", "http://www.weixin.qq.com/wxpay/pay.php");// 通知地址  	是
        data.put("trade_type", "NATIVE");// 交易类型  是   JSAPI 公众号支付 NATIVE 扫码支付 APP APP支付
        data.put("product_id", "12");// 商品ID  否
        data.put("nonce_str", "ec2316275641faa3aacf3cc599e8730f");//随机字符串  是
        data.put("sign",new WXPayUtil().generateSignature(data,WxPayConfig.key));//签名 是

        try {
            System.out.println("data{}:"+data);
            Map<String, String> r = wxpay.unifiedOrder(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭订单
    public void doOrderClose() {
        System.out.println("关闭订单");
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", out_trade_no);
        try {
            Map<String, String> r = wxpay.closeOrder(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询订单
    public void doOrderQuery() throws Exception {
        System.out.println("查询订单");
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("appid", "wx008c20180254f61d");//公众账号ID
         
        data.put("mch_id", "1494879482");//商户号
        
        data.put("transaction_id", "4200000163201808105478458362");//微信订单号
        
        //data.put("out_trade_no", "157921805316734");//商户订单号

        data.put("nonce_str", "ec2316275641faa3aacf3cc599e8730f");//随机字符串
        
        //获取签名
        //new WXPayUtil().generateSignature(data,"");
        data.put("sign", "120919FA8F25A05666F53CBA1EC4F948");//签名
        
        try {
        	System.out.println(data);
            Map<String, String> r = wxpay.orderQuery(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  

	//撤销
    public void doOrderReverse() {
        System.out.println("撤销");
        HashMap<String, String> data = new HashMap<String, String>();
        //data.put("out_trade_no", out_trade_no);
        data.put("transaction_id", "4008852001201608221962061594");
        try {
            Map<String, String> r = wxpay.reverse(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 长链接转短链接
     * 测试成功
     */
    public void doShortUrl() {
        String long_url = "weixin://wxpay/bizpayurl?pr=etxB4DY";
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("long_url", long_url);
        try {
            Map<String, String> r = wxpay.shortUrl(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退款
     * 已测试
     * @throws Exception 
     */
    public  Map doRefund(String transaction_id,double total_fee,double  refund_fee) throws Exception {
        Map map = new HashMap();
        HashMap<String, String> data = new HashMap<String, String>();

        data.put("appid", WxPayConfig.appid);//公众账号ID
        data.put("mch_id", WxPayConfig.mch_id);//商户号
        data.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));//随机字符串
        data.put("transaction_id",transaction_id);//微信订单号
        data.put("out_refund_no", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20));//商户退款单号
        data.put("total_fee",(Math.round(total_fee*100))+"");//订单金额
        data.put("refund_fee",(Math.round(refund_fee*100))+"");//退款金额
        data.put("sign", new WXPayUtil().generateSignature(data,WxPayConfig.key));//签名
        System.out.println(data);
        try {
            Map<String, String> m = wxpay.refund(data);
            JSONObject json = XML.toJSONObject(m.get("content"));
            JSONObject j = json.getJSONObject("xml");
            boolean result_code = j.has("result_code");

            if(j.get("return_code").toString().equals("SUCCESS")) {//返回状态码  SUCCESS/FAIL
                if(result_code && j.get("result_code").toString().equals("SUCCESS")) {
                    map.put("code","SUCCESS");
                    map.put("msg","退款申请成功,退款金额会在1个工作日之内返回您的个人账户,请注意查收!");
                }else {
                    map.put("code","FAIL");
                    map.put("msg","退款申请异常:"+j.get("err_code_des").toString()+"!");
                }
            }else {
                map.put("code","FAIL");
                map.put("msg","退款申请失败,"+j.get("return_msg").toString()+"!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
           return map;
    }

    /**
     * 查询退款
     * 已经测试
     */
    public void doRefundQuery() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_refund_no", out_trade_no);
        try {
            Map<String, String> r = wxpay.refundQuery(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对账单下载
     * 已测试
     */
    public void doDownloadBill() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("bill_date", "20161102");
        data.put("bill_type", "ALL");
        try {
            Map<String, String> r = wxpay.downloadBill(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetSandboxSignKey() throws Exception {
        WXPayConfigImpl config = WXPayConfigImpl.getInstance();
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("mch_id", config.getMchID());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        data.put("sign", sign);
        WXPay wxPay = new WXPay(config);
        String result = wxPay.requestWithoutCert("https://api.mch.weixin.qq.com/sandbox/pay/getsignkey", data, 10000, 10000);
        System.out.println(result);
    }


//    public void doReport() {
//        HashMap<String, String> data = new HashMap<String, String>();
//        data.put("interface_url", "20160822");
//        data.put("bill_type", "ALL");
//    }

    /**
     * 小测试
     */
    public void test001() {
        String xmlStr="<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "<appid><![CDATA[wx273fe72f2db863ed]]></appid>\n" +
                "<mch_id><![CDATA[1228845802]]></mch_id>\n" +
                "<nonce_str><![CDATA[lCXjx3wNx45HfTV2]]></nonce_str>\n" +
                "<sign><![CDATA[68D7573E006F0661FD2A77BA59124E87]]></sign>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<openid><![CDATA[oZyc_uPx_oed7b4q1yKmj_3M2fTU]]></openid>\n" +
                "<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
                "<trade_type><![CDATA[NATIVE]]></trade_type>\n" +
                "<bank_type><![CDATA[CFT]]></bank_type>\n" +
                "<total_fee>1</total_fee>\n" +
                "<fee_type><![CDATA[CNY]]></fee_type>\n" +
                "<transaction_id><![CDATA[4008852001201608221983528929]]></transaction_id>\n" +
                "<out_trade_no><![CDATA[20160822162018]]></out_trade_no>\n" +
                "<attach><![CDATA[]]></attach>\n" +
                "<time_end><![CDATA[20160822202556]]></time_end>\n" +
                "<trade_state><![CDATA[SUCCESS]]></trade_state>\n" +
                "<cash_fee>1</cash_fee>\n" +
                "</xml>";
        try {
            System.out.println(xmlStr);
            System.out.println("+++++++++++++++++");
            System.out.println(WXPayUtil.isSignatureValid(xmlStr, config.getKey()));
            Map<String, String> hm = WXPayUtil.xmlToMap(xmlStr);
            System.out.println("+++++++++++++++++");
            System.out.println(hm);
            System.out.println(hm.get("attach").length());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testUnifiedOrderSpeed() throws Exception {
        TestWXPay dodo = new TestWXPay();

        for (int i=0; i<100; ++i) {
            long startTs = System.currentTimeMillis();
            out_trade_no = out_trade_no+i;
            dodo.doUnifiedOrder();
            long endTs = System.currentTimeMillis();
            System.out.println(endTs-startTs);
            Thread.sleep(1000);
        }

    }

    public static void main(String[] args) throws Exception {

        TestWXPay dodo = new TestWXPay();
        //dodo.doRefund("4008852001201608221962061594",1,1);
        dodo.doUnifiedOrder();
        //dodo.doUnifiedOrder();
      //  dodo.doOrderQuery();
/*        // dodo.doGetSandboxSignKey();

         dodo.doUnifiedOrder();
        // dodo.doOrderQuery();
       //  dodo.doDownloadBill();
        // dodo.doShortUrl();
        // dodo.test001();
        // dodo.doOrderQuery();
        // dodo.doOrderClose();
        // dodo.doRefund();
        // dodo.doRefundQuery();
        // dodo.doOrderReverse();
        // dodo.test001();
        // dodo.testUnifiedOrderSpeed();

        dodo.doOrderQuery();
        dodo.doOrderReverse();
        dodo.doOrderQuery();
        dodo.doOrderReverse();
        dodo.doOrderQuery();*/

        // dodo.doRefund();
        //dodo.doUnifiedOrder();
        //dodo.doOrderQuery();


       /* int a=43;
        int b=24;
        DecimalFormat df = new DecimalFormat("0.0");//格式化小数
        String num = df.format((float)a/b);//返回的是String类型
        System.out.println("ddd==="+num);*/

    }
}
