package com.douya.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class DES {

    // 对称加密，这里不用在意为什么写了两行，都是一样的,
    private static String encryptKey = "da72nos3";
    private static String decryptKey = "da72nos3";

    // 向量/偏移量
    private static String ivStr = "17229v3c";
    private static byte[] iv = ivStr.getBytes();


    //加密
    public static String encryptDES(String encryptString) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        return Base64.encodeBase64String(encryptedData);
    }



    //解密
    public static String decryptDES(String decryptString) throws Exception {
        byte[] byteMi = new Base64().decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

    public static void main(String args[]) {
        try {
            String result =  encryptDES("1547022823835");
            System.out.println(result);
            System.out.println(decryptDES(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
