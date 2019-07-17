package com.douya.utils;
import java.util.*;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付宝退款功能
 */

public class Alipaytraderefund  {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    //秘钥
    public static final  String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDjFIrLBpHnoFl2cm3LzqcqRMTKFzKgmBW3bIzqF2N7PAk8JH0FksUWo3tcN6gXTkLan7PeUZ6xQ1Kw+X7UjVF4+VgWwBNAwb6dw9VBAcY4zuta+jM2eA/CWjYP3XTtHErEJAXhfjg/bwJ7Z+y1WS6t2U8AoDAYolTL7G0m9/5WWkTThdsu9fbRWrLhNMKlQfEsNO5Gm2uL/uJ/JJnYhiBPocSpQ4BE+3WvgEZ99+tq4TZlRkyhtZUpyEXYHjqzkDkkjF07fxT3J1EhmD/RuU9hqLMFFuKAd7Hb2iaRTi41TLwNauzQlBj9WIDSxvjvbnb1afgcuaLpQ9DZMm06EgjhAgMBAAECggEAGDoI1MBOsLea09hBfoz9qLTNigIPlLKlHnymTBNSucMd+Ryrefy+qjum3Z/SfyoKV+jR99YdaCwWlmsH7QVmu9FeZI75ft8sxwgtqFHYJBdvZLVs4VLN255kRi0KXIG4OFTfi787oQjm6uObjX2GTWXx188/UmF/SoJe4tlyeSPU4HlbPgP56vgd8IZmM15PJFjZpyINNZFFKR9HAniI5pfz1XwvIZY4IGVHeBeBiCLYMiuFCRcYwVyDtJYcGhkt3cjk53PO8+bljm2L/T72npokcTMhEROc0meUXUhWTOZa/QoY4QJZP8Xd5bjvwlwPiMKuXTEApaQ2QbicxgkAAQKBgQD0KnURmV/chemAp0/IaG0GNWxeZQfWRorS7vwmbPnJJ+YihD7LLhRyJsOyGT88aDcLfGtqUEoO3hGbgjEgt7n97HICU5Sm9CcKTO5FiYdqcY0P5lEx4B0TSPLym3ELf+mfuBx0FTrIBwGOq/7mWDgM9hFxgFEJZzU9GuFaY25J4QKBgQDuFhdjYhA/PU7klR9P/pILFh/r2rQ6xvk7tFDd+VTblKmtXm1mFh7lal6PzZ6ON9A7tuzHyac1utMvRlXvzDFTkXwWWmp89eYnlnRJjq1b8mxeKjYVO2xFT1+h6kSxdHwDBN0xcSAd6z3XXZkBxjsH/YHbjvVGFFUGWG0/aOGfAQKBgQCDbMcO7guXW81QHr7nFK56hQ8L0D3jrJzU5SZ8WHQrPnq1YZGbM/Z1a0AzATv0e/QFDreSxhzW29mGZxGilsJaV4cWkX6WTx0kgjxO2bQ2K2DOih5yRjvgQYfj5p92nSdXfXZzVWoKeRrxfMr7mcix0VGaUoNMGG2jsnBSguq+gQKBgQCGM4OZbHQ2AUvQAtJ0l80gXKc/nbaLjw57fec7R8hyQTjnVVua+IsPy8ZjjoZ6KbHBhxmuf9c1rQpPEnlsQo/nSBFG1E5EA6No2yDPOZI0StCUv3xeuXENHHGOZeu0jzE7zmkT7m0pECl6lPKjEmbB2T5yUQGmL93BRuTFokvTAQKBgQC/2yfN0ek3JPvDzdMjGR0WDrO/guL9fL0ujepkCrMOsA8EKO3e0oWZ+NHGmQUFu14iiux7moqPynEWdimmpoI5yLw3fhW+41s+b2S9A+3IME6LwpADnPh+WQzxFjnJFaecMdRdjNtUot1PkZ170Az9+DFPxBT1kkVhxSwWI3ARAw==";
    //公钥
    public static final  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtr+OM2ha9crh9Hbu07YSPvTc2FlEmfHEe7Xh8mM3rir6o2qcYty2V+St+r/2/tlQWYIEc0OmwmYiCRepzosv6xFDCYgiWIgoVN7N90i/CdizWUS8MrIlMj1fEmweBbgdwyCGJOsYWC0O+1KQSCS98dpYCW8muzNMksiHAO3GjOXHn7vhi88guQhAQw+10sI3Oeo8NMUQpCWzrLv+2S/czN3uQv1Uc9+rkU3/lTM/unmUp+fxarZnRGIixWhJ9KjfWPsKaccB/aS9PUjI38DAxGkppEAcCWD2XEpRaRJjzS7ZQfr+4uU2A+xGOwU7l80uzGscvu9ZEem9NnDJ9gQbaQIDAQAB";
    //支付宝分配给开发者的应用ID
    public static final String appid = "2017121800936365";

    //支付宝退款(全额退款)
    public static Map refund(String out_trade_no,String trade_no,double refund_amount)throws Exception{

        Map map = new LinkedHashMap();
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appid,merchant_private_key,"json","GBK",alipay_public_key,"RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(alipay(out_trade_no,trade_no,refund_amount));
        AlipayTradeRefundResponse response = alipayClient.execute(request);

        map.put("code",response.getCode());
        map.put("subCode",response.getSubCode());
        map.put("msg",response.getMsg());
        map.put("subMsg",response.getSubMsg());

        if(response.isSuccess()){
            System.out.println("调用成功");
            map.put("content","退款申请成功,退款金额会在1个工作日之内返回您的个人账户,请注意查收!");
        } else {
            System.out.println("调用失败");
            map.put("content","退款申请失败!");
        }

        return map;
    }


    public static String alipay(String out_trade_no,String trade_no,double refund_amount) {
        Map map = new LinkedHashMap();
        map.put("out_trade_no", out_trade_no);//商户订单号
        map.put("trade_no", trade_no);//支付宝交易号
        map.put("refund_amount", refund_amount);//需要退款的金额

        Map map2 = new LinkedHashMap();
        map2.put("goods_id", "apple-01");//商品的编号
        map2.put("goods_name", "ipad");//商品名称
        map2.put("quantity", 1);//商品数量
        map2.put("price", refund_amount);//商品单价，单位为元

        List list = new ArrayList();
        list.add(map2);
        map.put("goods_detail", list);
        JSONObject json  = JSONObject.fromObject(map);
        return json.toString();
    }

     //支付宝退款(部分退款)    需要添加 out_request_no
    //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
    public static Map refundPart(String out_trade_no,String trade_no,double price,double refund_amount)throws Exception{

        Map map = new LinkedHashMap();
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appid,merchant_private_key,"json","GBK",alipay_public_key,"RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(alipay2(out_trade_no,trade_no,price,refund_amount));
        AlipayTradeRefundResponse response = alipayClient.execute(request);

        map.put("code",response.getCode());
        map.put("subCode",response.getSubCode());
        map.put("msg",response.getMsg());
        map.put("subMsg",response.getSubMsg());

        if(response.isSuccess()){
            System.out.println("支付宝退款(部分退款)调用成功");
            map.put("content","支付宝退款(部分退款)申请成功,退款金额会在1个工作日之内返回您的个人账户,请注意查收!");
        } else {
            System.out.println("支付宝退款(部分退款)调用失败");
            map.put("content","支付宝退款(部分退款)申请失败!");
        }

        return map;
    }


    public static String alipay2(String out_trade_no,String trade_no,double price,double refund_amount) {
        Map map = new LinkedHashMap();
        map.put("out_trade_no", out_trade_no);//商户订单号
        map.put("trade_no", trade_no);//支付宝交易号
        map.put("refund_amount", refund_amount);//需要退款的金额
        map.put("out_request_no", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));//如需部分退款，则此参数必传

        Map map2 = new LinkedHashMap();
        map2.put("goods_id", "apple-01");//商品的编号
        map2.put("goods_name", "ipad");//商品名称
        map2.put("quantity", 1);//商品数量
        map2.put("price", price);//商品单价，单位为元

        List list = new ArrayList();
        list.add(map2);
        map.put("goods_detail", list);
        JSONObject json  = JSONObject.fromObject(map);
        return json.toString();
    }

}
