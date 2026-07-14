package com.personal.demo.utils;


import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Slf4j
public class SignatureUtil {

    public SignatureUtil() {
    }

    public static String rsaSign(String content, String privateKey, String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            if (null == charset || charset.isEmpty()) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed, Boolean.FALSE));
        } catch (Exception e) {
            log.warn(String.format("RSAcontent = %s; charset = %s  Exception: %s", content, charset, e));
            throw e;
        }
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins != null && null != algorithm && !algorithm.isEmpty()) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = Base64.decodeBase64(copyToString(ins, StandardCharsets.UTF_8));
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static String copyToString(InputStream in, Charset charset) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringBuilder out = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, charset);
            char[] buffer = new char[4096];

            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                out.append(buffer, 0, bytesRead);
            }

            return out.toString();
        }
    }

    public static String getSignContent(Map<String, Object> sortedParams) {
        return getSortedStr(sortedParams);
    }

    /**
     * 固定参数签名
     */
    public static String calculateSign(Map<String, Object> sortedParams, Set<String> needSignParams) {
        if (needSignParams == null || needSignParams.isEmpty()) {
            throw new RuntimeException("needSignParams is required");
        }
        // 固定参数签名模式（也要排序）
        List<String> keyList = new ArrayList<>(sortedParams.keySet());
        Collections.sort(keyList); // 先排序
        StringBuilder sb = new StringBuilder();
        boolean stared = false;
        for (String key : keyList) {
            if (needSignParams.contains(key)) {
                Object value = sortedParams.get(key);
                if (stared) {
                    sb.append("&");
                }
                stared = true;
                String valueStr = (value == null ? Separator.NONE : value.toString()); // 根据实际的Separator.NONE调整
                sb.append(key).append("=").append(valueStr);
            }
        }
        return sb.toString();
    }

    public static <T> String getSignContent(T obj, Type type) {
        Map<String, Object> sortedParams = GsonFactory.getInstance().fromJson(GsonFactory.getInstance().toJson(obj), type);
        return getSortedStr(sortedParams);
    }

    @NotNull
    private static String getSortedStr(Map<String, Object> sortedParams) {
        try {
            List<String> keyList = new ArrayList<>(sortedParams.keySet());
            Collections.sort(keyList);
            boolean stared = false;
            StringBuilder signParams = new StringBuilder();
            for (String key : keyList) {
                if (stared) {
                    signParams.append("&");
                }
                stared = true;
                signParams.append(key).append("=").append(sortedParams.get(key));
            }
            return signParams.toString();
        } catch (Exception e) {
            log.warn(String.format("getSignContent Exception: %s", e));
            throw e;
        }
    }

    public static String getSortedExclusiveBlankStr(Map<String, Object> sortedParams) {
        try {
            List<String> keyList = new ArrayList<>(sortedParams.keySet());
            Collections.sort(keyList);
            boolean started = false; // 注意：修正了变量名拼写 stared -> started
            StringBuilder signParams = new StringBuilder();
            for (String key : keyList) {
                Object value = sortedParams.get(key);
                // 排除值为空字符串的情况
                if (value == null || "".equals(value.toString())) {
                    continue;
                }

                if (started) {
                    signParams.append("&");
                }
                started = true;
                signParams.append(key).append("=").append(value);
            }
            return signParams.toString();
        } catch (Exception e) {
            log.warn(String.format("getSignContent Exception: %s", e));
            throw e;
        }
    }


    public static boolean rsaCheck(String content, String sign, String publicKey, String charset) throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            if (null == charset || charset.isEmpty()) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            log.warn(String.format("RSAcontent = %s; charset = %s  Exception: %s", content, charset, e));
            throw e;
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = Base64.decodeBase64(copyToString(ins, StandardCharsets.UTF_8));
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }


}
