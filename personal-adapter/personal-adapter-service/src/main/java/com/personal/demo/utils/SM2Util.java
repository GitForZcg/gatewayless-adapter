package com.personal.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.personal.demo.utils.GmUtil.*;


@Slf4j
public class SM2Util {

    private static final String SM2_KEY_TITLE = "3059301306072a8648ce3d020106082a811ccf5501822d03420004";

    public static final String USER_ID = "1234567812345678";

    public static String sm2Sign(String content, String privateKey) {
        try {
            //init privateKey
            BCECPrivateKey bcecPrivateKey = BCUtil.getPrivatekeyFromD(new BigInteger(privateKey, 16));

            byte[] sign = BCUtil.signSm3WithSm2(content.getBytes(), USER_ID.getBytes(), bcecPrivateKey);

            return encodeBase64(signRawToAsn1(sign));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 数字信封加密(SM4+SM2)
     */
    public static Map<String, Object> encrypt(String publicKey, String... contents) throws Exception {
        // 公钥信息
        Sm2Key sm2Vo = parseBase64TRawKey(publicKey);
        if (null == sm2Vo) {
            throw new IllegalArgumentException("公钥信息错误");
        }
        BCECPublicKey bcecPublicKey = GmUtil.getPublickeyFromXY(new BigInteger(sm2Vo.getSm2_x(), 16), new BigInteger(sm2Vo.getSm2_y(), 16));

        // 随机生成一个SM4密钥
        String wSm4Key = GeneralUtils.randomStr(16);

        String[] encContents = new String[contents.length];
        for (int i = 0; i < encContents.length; i++) {
            //SM4加密明文
            byte[] wEncByt = GmUtil.sm4EncryptCBC(wSm4Key.getBytes(), contents[i].getBytes(), SM4_CBC_IV);
            String encContent = new String(org.bouncycastle.util.encoders.Base64.encode(wEncByt));
            System.out.println("密文数据" + encContent);
            encContents[i] = encContent;
        }

        // SM2加密对称密钥
        byte[] wEncKeyByt = cipherRawToAsn1(sm2EncryptOld(wSm4Key.getBytes(), bcecPublicKey));
        String wDigEvp = new String(org.bouncycastle.util.encoders.Base64.encode(wEncKeyByt));
        System.out.println("数字信封数据(" + wDigEvp.length() + ")" + wDigEvp);
        Map<String, Object> r = new HashMap<>();
        r.put("digEvp", wDigEvp);
        r.put("encContents", encContents);
        return r;
    }

    /**
     * 数字信封解密(SM4+SM2)
     */
    public static String[] decrypt(String privateKey, String wDigEvp, String... encContents) throws Exception {
        BCECPrivateKey bcecPrivateKey = GmUtil.getPrivatekeyFromD(new BigInteger(privateKey, 16));

        //SM2解密对称密钥
        byte[] wDigEvpByt = org.bouncycastle.util.encoders.Base64.decode(wDigEvp);
        System.out.println("HEX DEBUG:" + new String(Hex.encode(wDigEvpByt)));
        byte[] wOriKeyByt = sm2DecryptOld(cipherAsn1ToRaw(wDigEvpByt), bcecPrivateKey);


        System.out.println("解密出来的明文SM4密钥:" + new String(wOriKeyByt));
        System.out.println("解密出来的明文SM4密钥长度:" + wOriKeyByt.length);

        String[] contents = new String[encContents.length];
        for (String wEcnTxt2 : encContents) {
            byte[] wEncByt2 = org.bouncycastle.util.encoders.Base64.decode(wEcnTxt2);

            byte[] bs = new byte[16];
            System.arraycopy(wOriKeyByt, 0, bs, 0, 16);
            System.out.println("解密出来的明文SM4密钥:" + new String(bs));
            //SM4解密
            byte[] wOriTxtByt = sm4DecryptCBC(bs, wEncByt2, SM4_CBC_IV);
            System.out.println("解密出来的明文:" + new String(wOriTxtByt));
        }
        return contents;
    }

    public static boolean sm2Check(String content, String rawSign, String publicKey) {
        try {
            //init PublicKey
            Sm2Key sm2Vo = parseBase64TRawKey(publicKey);
            if (null == sm2Vo) {
                return false;
            }
            BCECPublicKey bcecPublicKey = BCUtil.getPublickeyFromXY(new BigInteger(sm2Vo.getSm2_x(), 16), new BigInteger(sm2Vo.getSm2_y(), 16));

            byte[] sign = signAsn12Raw(decodeBase64(rawSign));

            return BCUtil.verifySm3WithSm2(content.getBytes(), USER_ID.getBytes(), sign, bcecPublicKey);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * BASE64格式公钥转换为裸公钥
     */
    public static Sm2Key parseBase64TRawKey(String sm2Key) {
        if (null == sm2Key) {
            return null;
        }

        String sm2_asn1 = Hex.toHexString(decodeBase64(sm2Key));
        if (!sm2_asn1.startsWith(SM2_KEY_TITLE)) {
            return null;
        }

        String sm2_xy = sm2_asn1.substring(SM2_KEY_TITLE.length(), sm2_asn1.length());
        String sm2_x = sm2_xy.substring(0, sm2_xy.length() / 2);
        String sm2_y = sm2_xy.substring(sm2_xy.length() / 2, sm2_xy.length());

        return new Sm2Key(SM2_KEY_TITLE, sm2_x, sm2_y);
    }

    /**
     * 将字节数组转换为Base64格式字符串
     */
    public static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 将Base64格式字符串转为字节数组
     */
    public static byte[] decodeBase64(String data) {
        return Base64.getDecoder().decode(data);
    }

    /**
     * 将BC SM2 RAW签名值转化为ASN1格式签名值
     */
    private static byte[] signRawToAsn1(byte[] bcCipTxt) throws Exception {

        byte[] netSignCipTxt = new byte[73];

        byte[] signR = new byte[32];
        byte[] signS = new byte[32];

        System.arraycopy(bcCipTxt, 0, signR, 0, 32);
        System.arraycopy(bcCipTxt, 32, signS, 0, 32);

        //signR补位
        int wPos = 4;
        netSignCipTxt[0] = 0x30;
        netSignCipTxt[2] = 0x02;
        if ((signR[0] & 0xFF) >= 128) {
            netSignCipTxt[wPos - 1] = 0x21;
            netSignCipTxt[wPos] = 0x00;
            wPos += 1;
        } else {
            netSignCipTxt[wPos - 1] = 0x20;
        }
        System.arraycopy(signR, 0, netSignCipTxt, wPos, 32);
        wPos += 32;

        //signS补位
        netSignCipTxt[wPos] = 0x02;
        wPos += 1;
        if ((signS[0] & 0xFF) >= 128) {
            netSignCipTxt[wPos] = 0x21;
            wPos += 1;
            netSignCipTxt[wPos] = 0x00;
            wPos += 1;
        } else {
            netSignCipTxt[wPos] = 0x20;
            wPos += 1;
        }
        System.arraycopy(signS, 0, netSignCipTxt, wPos, 32);
        wPos += 32;

        if (70 == wPos) {
            netSignCipTxt[1] = 0x44;
        } else if (71 == wPos) {
            netSignCipTxt[1] = 0x45;
        } else if (72 == wPos) {
            netSignCipTxt[1] = 0x46;
        } else {
            throw new Exception("signRawToAsn1 Error!");
        }

        byte[] resultBytes = new byte[wPos];
        System.arraycopy(netSignCipTxt, 0, resultBytes, 0, wPos);

        return resultBytes;
    }

    /**
     * 将ASN1格式签名值转化为BC SM2 RAW 签名值
     *
     * @param signature Asn1格式签名值
     * @return byte[] Raw签名值
     */
    private static byte[] signAsn12Raw(byte[] signature) throws Exception {

        byte[] resultBytes = new byte[64];

        //截取signR
        int wPos = 3;
        if ((signature[wPos] & 0xFF) == 32) {
            wPos += 1;
        } else if ((signature[wPos] & 0xFF) == 33) {
            wPos += 2;
        } else {
            throw new Exception("signR length Error!");
        }
        System.arraycopy(signature, wPos, resultBytes, 0, 32);
        wPos += 32;

        //截取signS
        wPos += 1;
        if ((signature[wPos] & 0xFF) == 32) {
            wPos += 1;
        } else if ((signature[wPos] & 0xFF) == 33) {
            wPos += 2;
        } else {
            throw new Exception("signS length Error!");
        }
        System.arraycopy(signature, wPos, resultBytes, 32, 32);


        return resultBytes;
    }

}
