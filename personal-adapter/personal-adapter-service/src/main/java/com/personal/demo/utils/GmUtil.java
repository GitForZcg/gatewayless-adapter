package com.personal.demo.utils;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * 本程序旨在给商户联调提供测试demo，不对最终内容做质量保障，仅作为使用方参考。
 */
public class GmUtil {

    private static final X9ECParameters x9ECParameters = GMNamedCurves
            .getByName("sm2p256v1");
    private static final ECDomainParameters ecDomainParameters = new ECDomainParameters(
            x9ECParameters.getCurve(), x9ECParameters.getG(),
            x9ECParameters.getN());
    private static final ECParameterSpec ecParameterSpec = new ECParameterSpec(
            x9ECParameters.getCurve(), x9ECParameters.getG(),
            x9ECParameters.getN());

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * @return r||s，直接拼接byte数组的rs
     */
    public static byte[] signSm3WithSm2(byte[] msg, byte[] userId,
                                        PrivateKey privateKey) {
        return rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(msg, userId,
                privateKey));
    }

    /**
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signSm3WithSm2Asn1Rs(byte[] msg, byte[] userId,
                                              PrivateKey privateKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature signer = Signature.getInstance("SM3withSM2", "BC");
            signer.setParameter(parameterSpec);
            signer.initSign(privateKey, new SecureRandom());
            signer.update(msg, 0, msg.length);
            byte[] sig = signer.sign();
            return sig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * r||s，直接拼接byte数组的rs
     */
    public static boolean verifySm3WithSm2(byte[] msg, byte[] userId,
                                           byte[] rs, PublicKey publicKey) {
        return verifySm3WithSm2Asn1Rs(msg, userId, rsPlainByteArrayToAsn1(rs),
                publicKey);
    }

    /**
     * asn1的规范format
     */
    public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] userId,
                                                 byte[] rs, PublicKey publicKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature verifier = Signature.getInstance("SM3withSM2", "BC");
            verifier.setParameter(parameterSpec);
            verifier.initVerify(publicKey);
            verifier.update(msg, 0, msg.length);
            return verifier.verify(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * bc加解密使用旧标c1||c2||c3，此方法在加密后调用，将结果转化为c1||c3||c2
     */
    private static byte[] changeC1C2C3ToC1C3C2(byte[] c1c2c3) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1; // sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
        final int c3Len = 32; // new SM3Digest().getDigestSize();
        byte[] result = new byte[c1c2c3.length];
        System.arraycopy(c1c2c3, 0, result, 0, c1Len); // c1
        System.arraycopy(c1c2c3, c1c2c3.length - c3Len, result, c1Len, c3Len); // c3
        System.arraycopy(c1c2c3, c1Len, result, c1Len + c3Len, c1c2c3.length
                - c1Len - c3Len); // c2
        return result;
    }

    /**
     * bc加解密使用旧标c1||c3||c2，此方法在解密前调用，将密文转化为c1||c2||c3再去解密
     */
    private static byte[] changeC1C3C2ToC1C2C3(byte[] c1c3c2) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1; // sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
        final int c3Len = 32; // new SM3Digest().getDigestSize();
        byte[] result = new byte[c1c3c2.length];
        System.arraycopy(c1c3c2, 0, result, 0, c1Len); // c1: 0->65
        System.arraycopy(c1c3c2, c1Len + c3Len, result, c1Len, c1c3c2.length
                - c1Len - c3Len); // c2
        System.arraycopy(c1c3c2, c1Len, result, c1c3c2.length - c3Len, c3Len); // c3
        return result;
    }

    /**
     * c1||c3||c2
     */
    public static byte[] sm2Decrypt(byte[] data, PrivateKey key) {
        return sm2DecryptOld(changeC1C3C2ToC1C2C3(data), key);
    }

    /**
     * c1||c3||c2
     */

    public static byte[] sm2Encrypt(byte[] data, PublicKey key) {
        return changeC1C2C3ToC1C3C2(sm2EncryptOld(data, key));
    }

    /**
     * c1||c2||c3
     */
    public static byte[] sm2EncryptOld(byte[] data, PublicKey key) {
        BCECPublicKey localECPublicKey = (BCECPublicKey) key;
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(
                localECPublicKey.getQ(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters,
                new SecureRandom()));
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * c1||c2||c3
     */
    public static byte[] sm2DecryptOld(byte[] data, PrivateKey key) {
        BCECPrivateKey localECPrivateKey = (BCECPrivateKey) key;
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(
                localECPrivateKey.getD(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(false, ecPrivateKeyParameters);
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sm4Encrypt(byte[] keyBytes, byte[] plain) {
        if (keyBytes.length != 16)
            throw new RuntimeException("err key length");
        if (plain.length % 16 != 0)
            throw new RuntimeException("err data length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher out = Cipher.getInstance("SM4/ECB/NoPadding", "BC");
            out.init(Cipher.ENCRYPT_MODE, key);
            return out.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sm4Decrypt(byte[] keyBytes, byte[] cipher) {
        if (keyBytes.length != 16)
            throw new RuntimeException("err key length");
        if (cipher.length % 16 != 0)
            throw new RuntimeException("err data length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher in = Cipher.getInstance("SM4/ECB/NoPadding", "BC");
            in.init(Cipher.DECRYPT_MODE, key);
            return in.doFinal(cipher);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static byte[] sm4EncryptCBC(byte[] keyBytes, byte[] plain, byte[] iv) {
        if (keyBytes.length != 16)
            throw new RuntimeException("err key length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher out = Cipher.getInstance("SM4/CBC/PKCS5Padding", "BC");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            out.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            return out.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sm4DecryptCBC(byte[] keyBytes, byte[] cipher, byte[] iv) {
        if (keyBytes.length != 16)
            throw new RuntimeException("err key length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher in = Cipher.getInstance("SM4/CBC/PKCS5Padding", "BC");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            in.init(Cipher.DECRYPT_MODE, key, ivSpec);
            return in.doFinal(cipher);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * SM3
     */
    public static byte[] sm3(byte[] bytes) {
        SM3Digest sm3 = new SM3Digest();
        sm3.update(bytes, 0, bytes.length);
        byte[] result = new byte[sm3.getDigestSize()];
        sm3.doFinal(result, 0);
        return result;
    }

    private final static int RS_LEN = 32;

    private static byte[] bigIntToFixexLengthBytes(BigInteger rOrS) {
        byte[] rs = rOrS.toByteArray();
        if (rs.length == RS_LEN)
            return rs;
        else if (rs.length == RS_LEN + 1 && rs[0] == 0)
            return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
        else if (rs.length < RS_LEN) {
            byte[] result = new byte[RS_LEN];
            Arrays.fill(result, (byte) 0);
            System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
            return result;
        } else {
            throw new RuntimeException("err rs: " + Hex.toHexString(rs));
        }
    }

    /**
     * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
     *
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     */
    private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer) {
        ASN1Sequence seq = ASN1Sequence.getInstance(rsDer);
        byte[] r = bigIntToFixexLengthBytes(ASN1Integer.getInstance(
                seq.getObjectAt(0)).getValue());
        byte[] s = bigIntToFixexLengthBytes(ASN1Integer.getInstance(
                seq.getObjectAt(1)).getValue());
        byte[] result = new byte[RS_LEN * 2];
        System.arraycopy(r, 0, result, 0, r.length);
        System.arraycopy(s, 0, result, RS_LEN, s.length);
        return result;
    }

    /**
     * BC的SM3withSM2验签需要的rs是asn1格式的，这个方法将直接拼接r||s的字节数组转化成asn1格式
     *
     * @param sign in plain byte array
     * @return rs result in asn1 format
     */
    private static byte[] rsPlainByteArrayToAsn1(byte[] sign) {
        if (sign.length != RS_LEN * 2)
            throw new RuntimeException("err rs. ");
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN,
                RS_LEN * 2));
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        try {
            return new DERSequence(v).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("EC", "BC");
            kpGen.initialize(ecParameterSpec, new SecureRandom());
            return kpGen.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BCECPrivateKey getPrivatekeyFromD(BigInteger d) {
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d,
                ecParameterSpec);
        return new BCECPrivateKey("EC", ecPrivateKeySpec,
                BouncyCastleProvider.CONFIGURATION);
    }

    public static BCECPublicKey getPublickeyFromXY(BigInteger x, BigInteger y) {
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters
                .getCurve().createPoint(x, y), ecParameterSpec);
        return new BCECPublicKey("EC", ecPublicKeySpec,
                BouncyCastleProvider.CONFIGURATION);
    }

    public static PublicKey getPublickeyFromX509File(File file) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509",
                    "BC");
            FileInputStream in = new FileInputStream(file);
            X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
            // System.out.println(x509.getSerialNumber());
            return x509.getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* 以下为提供的测试demo */

    public final static String USER_ID = "1234567812345678";
    public final static byte[] SM4_CBC_IV = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * API输出国密密钥SM2生成demo
     */
    public static void CmbGenKeyTest() {

        // 生成公私钥对 ---------------------
        KeyPair kp = generateKeyPair();
        BCECPrivateKey prikey = (BCECPrivateKey) kp.getPrivate();
        BCECPublicKey pubkey = (BCECPublicKey) kp.getPublic();

        //System.out.println("私钥(10进制,不推荐存储格式):" + prikey.getD().toString(10));
        System.out.println("私钥(HEX,推荐存储格式):" + prikey.getD().toString(16));
        System.out.println("公钥X:" + pubkey.getQ().getAffineXCoord() + ": " + pubkey.getQ().getAffineXCoord().toString().length());
        System.out.println("公钥Y:" + pubkey.getQ().getAffineYCoord() + ": " + pubkey.getQ().getAffineYCoord().toString().length());
        if (pubkey.getQ().getAffineXCoord().toString().length() != 64) {
            System.out.println("-----------------");
        }

    }

    /**
     * API输出国密加签核签demo
     */
    public static void CmbSgnVfyTest() {

        // 配置私钥 ---------------------
        BigInteger d = new BigInteger(
                "5d1186c623347d00387db63bff5f167756038056706bebef0fdf67d56d5154a",
                16);
        BCECPrivateKey bcecPrivateKey = getPrivatekeyFromD(d);

        // 配置公钥
        // 公钥X坐标PublicKeyXHex:
        // dcbb8622facdd1e9ac5a71d9fd0f9ab7af4883537495ca364b5070bea759df13
        // 公钥Y坐标PublicKeyYHex:
        // b1f285a1ed1bba2e059653c17b41d4c09e6be0477143b4f61aa018563aa2fba6
        PublicKey publicKey = getPublickeyFromXY(
                new BigInteger(
                        "dcbb8622facdd1e9ac5a71d9fd0f9ab7af4883537495ca364b5070bea759df13",
                        16),
                new BigInteger(
                        "b1f285a1ed1bba2e059653c17b41d4c09e6be0477143b4f61aa018563aa2fba6",
                        16));

        byte[] msg = "123".getBytes();
        byte[] userId = USER_ID.getBytes();

        String wSm3Hash = Hex.toHexString(sm3(msg));

        byte[] sig = signSm3WithSm2Asn1Rs(wSm3Hash.getBytes(), userId, bcecPrivateKey);
        System.out.println("SIGN:" + new String(Base64.encode(sig)));

        System.out.println(verifySm3WithSm2Asn1Rs(wSm3Hash.getBytes(), userId, sig, publicKey));

    }

    /**
     * API输出国密数字信封加密解密demo
     */
    public static void CmbEncDecyTest() throws Exception {

        // 配置私钥 ---------------------
        BigInteger d = new BigInteger(
                "a9a2d3607afac913cee8b871106e1d8d4a140feefa8d2d047f1990b59be4b945",
                16);
        BCECPrivateKey bcecPrivateKey = getPrivatekeyFromD(d);

        // 配置公钥
        // 公钥X坐标PublicKeyXHex:
        // 21b7733e43bbbddb21b46ed350187680f0afa73fbd49cf9351e7e70376c77163
        // 公钥Y坐标PublicKeyYHex:
        // 7503f1f60eaa47cb8124a088f1366ce9ce1b77e4ac270d286b56a67c066d9ed4
        PublicKey publicKey = getPublickeyFromXY(
                new BigInteger(
                        "21b7733e43bbbddb21b46ed350187680f0afa73fbd49cf9351e7e70376c77163",
                        16),
                new BigInteger(
                        "7503f1f60eaa47cb8124a088f1366ce9ce1b77e4ac270d286b56a67c066d9ed4",
                        16));



        /*----------------------------------------------*/
        //数字信封加密(SM4+SM2)

        //明文数据
        String wOriTxt = "123";

        //随机生成一个SM4密钥(此处为了演示demo，固定该密钥)
        String wSm4Key = "1234567890123456";

        //SM4加密明文
        byte[] wEncByt = sm4EncryptCBC(wSm4Key.getBytes(), wOriTxt.getBytes(), SM4_CBC_IV);
        System.out.println("密文数据" + new String(Base64.encode(wEncByt)));


        //SM2加密对称密钥  
        //注意只有wEncKeyByt和wEncKeyBytOldAsn1形式的密文可以与加密机调通，建议使用OLD ASN1形式的密文
        //byte[] wEncKeyByt = cipherRawToAsn1(sm2Encrypt(wSm4Key.getBytes(), publicKey));
        byte[] wEncKeyByt = sm2Encrypt(wSm4Key.getBytes(), publicKey);
        byte[] wEncKeyBytAsn1 = cipherRawToAsn1(sm2Encrypt(wSm4Key.getBytes(), publicKey));

        byte[] wEncKeyBytOld = sm2EncryptOld(wSm4Key.getBytes(), publicKey);
        byte[] wEncKeyBytOldAsn1 = cipherRawToAsn1(sm2EncryptOld(wSm4Key.getBytes(), publicKey));

        String wDigEvp = new String(Base64.encode(wEncKeyByt));
        System.out.println("数字信封数据(" + wDigEvp.length() + ")" + wDigEvp);

        String wDigEvp1 = new String(Base64.encode(wEncKeyBytAsn1));
        System.out.println("数字信封数据ASN1(" + wDigEvp1.length() + ")" + wDigEvp1);

        String wDigEvp2 = new String(Base64.encode(wEncKeyBytOld));
        System.out.println("数字信封数据OLD(" + wDigEvp2.length() + ")" + wDigEvp2);

        String wDigEvp3 = new String(Base64.encode(wEncKeyBytOldAsn1));
        System.out.println("数字信封数据OLD ASN1(" + wDigEvp3.length() + ")" + wDigEvp3);


        /*----------------------------------------------*/
        //数字信封解密(SM4+SM2)

        //SM2解密对称密钥
        byte[] wDigEvpByt = Base64.decode(wDigEvp);
        //byte wDigRawByt2[] = cipherAsn1ToRaw(wDigEvpByt2);
        System.out.println("HEX DEBUG:" + new String(Hex.encode(wDigEvpByt)));
        byte[] wOriKeyByt = sm2Decrypt(wDigEvpByt, bcecPrivateKey);


        System.out.println("解密出来的明文SM4密钥:" + new String(wOriKeyByt));
        System.out.println("解密出来的明文SM4密钥长度:" + wOriKeyByt.length);

        String wEcnTxt2 = "xnWvN8pX621oYuKHSoGBiA==";
        byte[] wEncByt2 = Base64.decode(wEcnTxt2);

        byte[] bs = new byte[16];
        System.arraycopy(wOriKeyByt, 0, bs, 0, 16);
        System.out.println("解密出来的明文SM4密钥:" + new String(bs));
        //SM4解密
        byte[] wOriTxtByt = sm4DecryptCBC(bs, wEncByt2, SM4_CBC_IV);
        System.out.println("解密出来的明文:" + new String(wOriTxtByt));

    }

    /**
     * 将BC C1C2C3 SM2 密文转化为ASN1格式C1C3C2密文
     *
     * @param bcCipTxt SM2 密文
     * @return byte[] ASN1格式密文
     */
    public static byte[] cipherRawToAsn1(byte[] bcCipTxt) throws Exception {

        //去除RAW密文开头的 04 标志位
        int bcCipLen = bcCipTxt.length - 1;
        if (96 >= bcCipLen) {
            throw new Exception("RAW ciphertext length error:" + bcCipLen);
        }

        //计算密文部分实际长度
        int C3Len = bcCipLen - 96;

        byte[] keyX = new byte[32];
        byte[] keyY = new byte[32];
        byte[] C3 = new byte[C3Len];
        byte[] C2 = new byte[32];

        System.arraycopy(bcCipTxt, 1, keyX, 0, 32);
        System.arraycopy(bcCipTxt, 33, keyY, 0, 32);
        System.arraycopy(bcCipTxt, 65, C3, 0, C3Len);
        System.arraycopy(bcCipTxt, 65 + C3Len, C2, 0, 32);

        byte[] netSignCipTxt = new byte[bcCipLen + 13];

        //keyX补位
        int wPos = 4;
        netSignCipTxt[0] = 0x30;
        netSignCipTxt[2] = 0x02;
        if ((keyX[0] & 0xFF) >= 128) {
            netSignCipTxt[wPos - 1] = 0x21;
            netSignCipTxt[wPos] = 0x00;
            wPos += 1;
        } else {
            netSignCipTxt[wPos - 1] = 0x20;
        }
        System.arraycopy(keyX, 0, netSignCipTxt, wPos, 32);
        wPos += 32;

        //keyY补位
        netSignCipTxt[wPos] = 0x02;
        wPos += 1;
        if ((keyY[0] & 0xFF) >= 128) {
            netSignCipTxt[wPos] = 0x21;
            wPos += 1;
            netSignCipTxt[wPos] = 0x00;
            wPos += 1;
        } else {
            netSignCipTxt[wPos] = 0x20;
            wPos += 1;
        }
        System.arraycopy(keyY, 0, netSignCipTxt, wPos, 32);
        wPos += 32;

        //copy C2
        netSignCipTxt[wPos] = 0x04;
        wPos += 1;
        netSignCipTxt[wPos] = 0x20;
        wPos += 1;
        System.arraycopy(C2, 0, netSignCipTxt, wPos, 32);
        wPos += 32;

        //copy C3
        netSignCipTxt[wPos] = 0x04;
        wPos += 1;
        netSignCipTxt[wPos] = (byte) C3Len;
        wPos += 1;
        System.arraycopy(C3, 0, netSignCipTxt, wPos, C3Len);
        wPos += C3Len;

        //总长度
        netSignCipTxt[1] = (byte) (wPos - 2);

        byte[] resultBytes = new byte[wPos];
        System.arraycopy(netSignCipTxt, 0, resultBytes, 0, wPos);

        return resultBytes;
    }

    /**
     * 将ASN1格式C1C3C2密文转化为BC SM2 C1C2C3密文
     *
     * @param asn1CipTxt byte[] ASN1格式密文
     * @return byte[] BC SM2 密文
     */
    public static byte[] cipherAsn1ToRaw(byte[] asn1CipTxt) throws Exception {

        //截取keyX
        int wPos = 3;
        if ((asn1CipTxt[wPos] & 0xFF) == 32) {
            wPos += 1;
        } else if ((asn1CipTxt[wPos] & 0xFF) == 33) {
            wPos += 2;
        } else {
            throw new Exception("keyX length Error!");
        }
        byte[] keyX = new byte[32];
        System.arraycopy(asn1CipTxt, wPos, keyX, 0, 32);
        wPos += 32;

        //截取keyY
        wPos += 1;
        if ((asn1CipTxt[wPos] & 0xFF) == 32) {
            wPos += 1;
        } else if ((asn1CipTxt[wPos] & 0xFF) == 33) {
            wPos += 2;
        } else {
            throw new Exception("keyY length Error!");
        }
        byte[] keyY = new byte[32];
        System.arraycopy(asn1CipTxt, wPos, keyY, 0, 32);
        wPos += 32;

        //截取C2
        wPos += 1;
        if ((asn1CipTxt[wPos] & 0xFF) == 32) {
            wPos += 1;
        } else {
            throw new Exception("C2 length Error!");
        }
        byte[] C2 = new byte[32];
        System.arraycopy(asn1CipTxt, wPos, C2, 0, 32);
        wPos += 32;

        //截取C3
        wPos += 1;
        int C3Len = asn1CipTxt[wPos] & 0xFF;
        if (0 < C3Len) {
            wPos += 1;
        } else {
            throw new Exception("C3 length Error!");
        }

        byte[] C3 = new byte[C3Len];
        System.arraycopy(asn1CipTxt, wPos, C3, 0, C3Len);
        wPos += C3Len;

        //组装
        byte[] resultBytes = new byte[97 + C3Len];

        resultBytes[0] = 0x04;
        System.arraycopy(keyX, 0, resultBytes, 1, 32);
        System.arraycopy(keyY, 0, resultBytes, 33, 32);
        System.arraycopy(C3, 0, resultBytes, 65, C3Len);
        System.arraycopy(C2, 0, resultBytes, 65 + C3Len, 32);

        return resultBytes;
    }

//    public static void main(String[] args) throws Exception {
//
//
//        long start = System.currentTimeMillis();
//
//        int c = 1;
//        while (c-- > 0)
//            CmbGenKeyTest();
//
//        System.out.println("CmbGenKeyTest consumes " + (System.currentTimeMillis() - start) + "ms");
//
//        CmbSgnVfyTest();
//        CmbEncDecyTest();
//
//        int count = 1;
//        long start1 = System.currentTimeMillis();
//        while (count-- > 0)
//            CmbSgnVfyTest();
//
//
//        long start2 = System.currentTimeMillis();
//        count = 1;
//        CmbEncDecyTest();
//        while (count-- > 0)
//            CmbEncDecyTest();
//
//        System.out.println("CmbSgnVfyTest consumes " + (System.currentTimeMillis() - start1) + "ms");
//        System.out.println("CmbEncDecyTest consumes " + (System.currentTimeMillis() - start2) + "ms");
//
//
//    }


}
