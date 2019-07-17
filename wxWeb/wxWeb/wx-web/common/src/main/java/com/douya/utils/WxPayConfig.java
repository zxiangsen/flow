package com.douya.utils;

public class WxPayConfig {
    //小程序appid
    public static final String appid = "wx008c20180254f61d";
    //微信支付的商户id
    public static final String mch_id = "1494879482";
    //微信支付的商户密钥
    public static final String key = "fH5CDKW7GksfjjkLQHANw8jbFvAXiAx9";
    //支付成功后的服务器回调url
    public static final String notify_url = "https://5f2017.cn/weldingMachine//Pay/paySuccess";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
