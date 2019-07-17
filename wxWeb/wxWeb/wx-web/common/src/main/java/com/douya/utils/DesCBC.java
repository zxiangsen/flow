package com.douya.utils;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.misc.BASE64Encoder;
import sun.security.provider.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.AlgorithmParameterSpec;


public class DesCBC {
   /* public static void main(String[] args) throws Exception{
        IvParameterSpec localIvParameterSpec = new IvParameterSpec("166290ac9728c73d".getBytes());
        DESKeySpec localDESKeySpec = new DESKeySpec("6c12b11d4f6698bd166290ac9728c73d".getBytes());
        SecretKey localSecretKey = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
        Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        localCipher.init(1, localSecretKey, localIvParameterSpec);
        String str = Base64.encode(localCipher.doFinal("douyakeji".getBytes()));

//        String str = EncryptDES("douyakeji", "6c12b11d4f6698bd166290ac9728c73d");
        System.out.println(str);
    }

    public static String EncryptDES(String encryptString, String encryptKey)
    {
        String ret = "";

        try
        {

            byte[] key = encryptKey.getBytes("GB2312");
            DESKeySpec dks = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            IvParameterSpec iv = new IvParameterSpec("166290ac9728c73d".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);


            byte[] src = encryptString.getBytes("UTF-8");
            src = cipher.doFinal(src);

            BASE64Encoder enc=new BASE64Encoder();
            ret = enc.encode(src);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }*/

}