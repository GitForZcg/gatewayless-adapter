package com.personal.demo.pojo.base;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.pojo.dto.WxPayOrderReqDto;
import com.personal.demo.pojo.dto.WxPaySubOrderReqDto;
import com.personal.demo.pojo.dto.request.product.ExternalProductDetailRequest;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: sm2加验签demo
 * @date 2025/7/16 09:32
 */
@Data
@Accessors(chain = true)
public class BaseRequestDemo {

    private final static String appid = "mwXQr2J0RkoVVDzbr7n528vLIwpGz4r1";
    private final static String secret = "REDACTED";

    public static void main(String[] args) {

        final String personalPriKey = "9A2E0F656B172463155613461366FB04AF0BD945311401270228C419E37C1A69";
//        final String personalPubKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEy8shg1LZqxAL04JVRiQTMIiOG+r4gUofmh+sof5pG4rf6cYV7Y6jbcPPtpGqOgLwvlxU7sRhabqdVmxijEI+lA==";

//        final String threePriKey2 = "BD255733008D9751E07FC48242C1D8502B69E22C51B25711B568E3759C822AC1";
        final String threePubKey2 = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAExcDViUPu+zPz3SSKiyUuEhuSzg9MHVbQEqG66o4l2wixrm7eSFgI/3MjK3Rf1uWYV/ELB2GA7AX6UewEl4oNtw==";
        String TIMEZONE = "Asia/Shanghai";

//        WxPayOrderReqDto reqDto = new WxPayOrderReqDto();
////        reqDto.setOrderId(UUID.randomUUID().toString());
//        reqDto.setOrderId("abc123");
//        WxPaySubOrderReqDto paySubOrderReqDto = new WxPaySubOrderReqDto()
//                .setSubMerId("123")
//                .setSubOrderId("XXX");
//
//
//        List<WxPaySubOrderReqDto> subList = List.of(paySubOrderReqDto);
//        reqDto.setSubOrderList(subList);

        //{
        //    "batchNumber": "PCB623114204339",
        //    "bid": "9799cf5e-1a50-4d03-ab1f-5c2213b3598b",
        //    "mid": "9799cf5e-1856-466e-a232-1ed3bc0cf299",
        //    "sid": "9a8c15f0-b892-4515-9fe4-93de54c81c00"
        //}

        ExternalProductDetailRequest reqDto = new ExternalProductDetailRequest();
        reqDto.setSid("9a8c15f0-b892-4515-9fe4-93de54c81c00");
        reqDto.setMid( "9799cf5e-1856-466e-a232-1ed3bc0cf299");
        reqDto.setBid("9799cf5e-1a50-4d03-ab1f-5c2213b3598b");
        reqDto.setBatchNumber("PCB62311738321a");

        String json = GsonFactory.getInstance().toJson(reqDto);
        System.out.println("加签前的报文 json  " + json);

        //对待加签内容进行排序拼接
        String signContent = SignatureUtil.getSignContent(reqDto, TypeHelperUtil.mapOf(String.class, Object.class));
        System.out.println("加签前的报文字符串 =  " + signContent);

        //加签
        String sign = SM2Util.sm2Sign(signContent, personalPriKey);
        System.out.println("加签后的报文内容:  " + sign);

        long timeStamp = ZonedDateTime.now(ZoneId.of(TIMEZONE)).toEpochSecond();
        //构建api签名
        String apiSign = buildApiSign(sign, timeStamp);

        //构建api请求头
        Map<String, Object> apiHeader = buildApiHeader(timeStamp, apiSign, sign);
        System.out.println("apiHeader = " + apiHeader);

//        Map<String, String> response = HttpUtil.postForEntity("https://personal.com:8080/adapter" + "/test", signContent, apiHeader);
//        //返回结果验签
//        Boolean checkResult = checkSign(response.get("sign"), GsonFactory.getInstance().toJson(response));
//        if (!checkResult) {
//            throw new RuntimeException("验证签名失败");
//        }

//        Map<String, Object> map = new HashMap<>();
//        map.put("testId", "mlThnx06x4");
//        map.put("testMsg", "TEST::success");
//        map.put("testItem", "TEST_SIGN");
//        map.put("type", "PRODUCT");
//        String signContent1 = SignatureUtil.getSignContent(map, TypeHelperUtil.mapOf(String.class, Object.class));
//        System.out.println("signContent1 = " + signContent1);
//
//
//        Boolean b = checkSign("MEUCICuouY4MZJfCr3mnp41T+0CqleZWTIsydaBhCwyvNZLTAiEAiTXwPcpniKt7bipZ2zqJ/KFxK7+35ccl5EEtmlfeRBw=", GsonFactory.getInstance().toJson(map));
//        System.out.println("b = " + b);
        //MEUCIQDiahVkJnnXOJ/kNltzcU2DGiBncRGpK9kDVJ7SJsgoTAIgXlU8wV5RnFry2e2Qp78QRxAJEPdVMh+HcTeZ6Sg8SUI=

        //{
        //    "batchNumber": "PCB623114204339",
        //    "bid": "9799cf5e-1a50-4d03-ab1f-5c2213b3598b",
        //    "mid": "9799cf5e-1856-466e-a232-1ed3bc0cf299",
        //    "sid": "9a8c15f0-b892-4515-9fe4-93de54c81c00"
        //}
        Map<String, Object> map = new HashMap<>();
        map.put("batchNumber", "PCB623114204339");
        map.put("bid", "9799cf5e-1a50-4d03-ab1f-5c2213b3598b");
        map.put("mid", "9799cf5e-1856-466e-a232-1ed3bc0cf299");
        map.put("sid", "9a8c15f0-b892-4515-9fe4-93de54c81c00");
        String signContent1 = SignatureUtil.getSignContent(map, TypeHelperUtil.mapOf(String.class, Object.class));
        System.out.println("signContent1 = " + signContent1);

        String sign1 = SM2Util.sm2Sign(signContent1, personalPriKey);
        System.out.println("sign1 = " + sign1);

        boolean b = SM2Util.sm2Check(signContent1, "MEUCIQDiahVkJnnXOJ/kNltzcU2DGiBncRGpK9kDVJ7SJsgoTAIgXlU8wV5RnFry2e2Qp78QRxAJEPdVMh+HcTeZ6Sg8SUI=", "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEy8shg1LZqxAL04JVRiQTMIiOG+r4gUofmh+sof5pG4rf6cYV7Y6jbcPPtpGqOgLwvlxU7sRhabqdVmxijEI+lA==");
        System.out.println("b = " + b);

    }

    private static Boolean checkSign(String sign, String responseStr) {
        try {
            Map<String, Object> responseBodyMap = GsonFactory.getInstance().fromJson(responseStr, TypeHelperUtil.mapOf(String.class, Object.class));
            String contentStr = getSortedStr(responseBodyMap);
            boolean result = SM2Util.sm2Check(contentStr, sign, "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAExcDViUPu+zPz3SSKiyUuEhuSzg9MHVbQEqG66o4l2wixrm7eSFgI/3MjK3Rf1uWYV/ELB2GA7AX6UewEl4oNtw==");
            System.out.println("验证签名结果: " + (result ? "成功" : "失败"));
            return result;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
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
            throw e;
        }
    }


    @NotNull
    private static String buildApiSign(String sign, long timeStamp) {
        Map<String, Object> apiSign = new TreeMap<>();
        apiSign.put("appid", appid);
        apiSign.put("secret", "REDACTED");
        apiSign.put("timestamp", "" + timeStamp);
        apiSign.put("sign", sign);
        // MD5加密
        String MD5Content = SignatureUtil.getSignContent(apiSign);
        return Objects.requireNonNull(MD5Utils.getMD5Content(MD5Content)).toLowerCase();
    }

    @NotNull
    private static Map<String, Object> buildApiHeader(long timeStamp, String apiSign, String sign) {
        Map<String, Object> apiHeader = new HashMap<>();
        apiHeader.put("appid", appid);
        apiHeader.put("timestamp", "" + timeStamp);
        apiHeader.put("apisign", apiSign);
        apiHeader.put("sign", sign);
        return apiHeader;
    }


}
