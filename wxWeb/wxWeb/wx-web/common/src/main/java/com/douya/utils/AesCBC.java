package com.douya.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lb on 2017/12/1.
 */
public class AesCBC {
    private static String sKey="6c12b11d";
    private static String ivParameter="166290ac";
    private static AesCBC instance=null;
    //private static
    private AesCBC(){

    }
    public static AesCBC getInstance(){
        if (instance==null)
            instance= new AesCBC();
        return instance;
    }
    // 加密
    public String encrypt(String sSrc, String encodingFormat) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
    }

    // 解密
    public String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,encodingFormat);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        // 需要加密的字串
        String str = "phone=18320134174&password=e10adc3949ba59abbe56e057f20f883e&type=1&mark=1&registrationId=1234567";
//        System.out.println("加密前的字串是："+str);
        // 加密
        String enString = AesCBC.getInstance().encrypt(str, "utf-8");
        System.out.println("加密后的字串是："+ enString);


        // 解密
//        String DeString = AesCBC.getInstance().decrypt(str,"utf-8",sKey,ivParameter);
//        System.out.println("解密后的字串是：" + DeString);
    }
}