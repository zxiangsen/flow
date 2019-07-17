package com.douya.security;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCk5B5OzWq2o/YCAe5+4b/hg1v2TjCt4Ezj2X7yWMSji2LNOABBZyjiMXURvZ0QexOKlqgbSJFMvDf+4/v1cF/V6EXDv36i+1xO/aOU5itiiuHTUSuGdkF1/RzI3QHfCBlRO1gtKtkmhVf5ZEWyH8WHa7q0V8N/5qzAAOUD3auvgwIDAQAB";


    public static String encrypt( String str) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    public static void main(String args[]) {
        try {
            String result =  encrypt("https://zxs2018.oss-cn-beijing.aliyuncs.com/zqsd/A1B2C3.png");
            System.out.println(result.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
