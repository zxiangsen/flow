package com.douya.utils;

/**
 * Created by Administrator on 2018/1/11.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * DES的CBC加密模式
 */
public class DES {
    private Logger logger = LoggerFactory.getLogger(DES.class);

    /**
     * 加密/解密key
     */
    private static String encryptKey = "6c12b11d";
    private static String decryptKey = "6c12b11d";
    /**
     * 偏移矢量
     */
    private static String ivStr = "166290ac";
    private static byte[] iv = ivStr.getBytes();

    /**
     * 加密
     * @param encryptString
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));

            return Base64.encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param decryptString
     * @return
     */
    public static String decryptDES(String decryptString) {
        try {
            byte[] byteMi = new Base64().decode(new String(decryptString.getBytes("UTF-8")));
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
//          IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);

            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(encryptDES("{'machineCode':'WW_ENGI00000004'}"));
        //System.out.println(encryptDES("password"));
        //System.out.println(decryptDES("d6y5QQF+Kp+BDCSzJJ1vFg=="));
        //System.out.println(encryptDES("234242432434343434324324234342"));
        //System.out.println(decryptDES("VKlRSW0E9d6PYKr5Wufpmrct0HOz7oqKOc7Q/+S/gEo="));
        System.out.println(encryptDES("phone=18320134174&password=e10adc3949ba59abbe56e057f20f883e&type=1&mark=2&registrationId=2345678"));
        //System.out.println(encryptDES("C09DF06C1ECA1B7CB2CE78EA3896BE36"));
        //System.out.println(decryptDES(""));
        //System.out.println(URLEncoder.encode("bAK5ciXCb/4gLA0SrddXjy9tSUPldIace7kA+e/ogWreAE4Cul9kXvOsRdXJk35pU/aXUAHK+XYBQ+3puYAMfzvFTo7VxcVj/88VfXKcOI4052im+EJWtPAN9bZ3W7gXGxc+Jc2EV3+O0fa27qFdRASS4d4XkjhFWGEzivRra2w=", "UTF-8"));
        //System.out.println(URLDecoder.decode("", "UTF-8"));
    }
}
