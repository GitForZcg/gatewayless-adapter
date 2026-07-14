package com.personal.demo.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 发票加密工具类
 * @date 2025/9/11 13:47
 */
@Slf4j
public class InvoiceEncryptUtils {

    private static final String AES_TYPE = "AES/ECB/PKCS5Padding";

    public static Key generateKey(String key) throws Exception {
        try {
            return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw e;
        }
    }

    /**
     * AES编码-加密
     */
    public static String AESEncrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return new String(Base64.getEncoder().encode(encrypt));
    }

    /**
     * AES解码-解密
     */
    public static String AESDecrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            encrypt = cipher.doFinal(Base64.getDecoder().decode(plainText));
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        assert encrypt != null;
        return new String(encrypt);
    }

    public static String getMD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes(StandardCharsets.UTF_8);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return null;
        }
    }

    public static String encryptASEMD5(String content, String password) {
        String aesEncrype = InvoiceEncryptUtils.AESEncrypt(password, content).trim();
        aesEncrype = aesEncrype.replace("\r\n", "");
        aesEncrype = aesEncrype.replace("\n", "");
        String md5str = Objects.requireNonNull(InvoiceEncryptUtils.getMD5(aesEncrype)).trim();
        md5str = md5str.replace("\r\n", "");
        md5str = md5str.replace("\n", "");
        return md5str;
    }


//    public static void main(String[] args) {
//        String kpInfo = "{\"sceneNotify\":1,\"reserveField\":\"\"}";
//        String secretKey = "OMPSAASZFP2222I9";
//        String signKey = InvoiceEncryptUtils.encryptASEMD5(kpInfo, secretKey).substring(0, 4);
//        System.out.println("signKey = " + signKey);
//    }

}
