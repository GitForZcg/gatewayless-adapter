package com.personal.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MD5Utils {

    /**
     * MD5加密
     */
    public static String getMD5Content(String input) {
        try {
            // 拿到MD转换器
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte[] inputByteArray = input.getBytes();
            messageDigest.update(inputByteArray);

            byte[] resultByteArray = messageDigest.digest();

            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            log.warn(String.format("[MD5Utils]-getMD5Content NoSuchAlgorithmException:%s", e));
            return null;
        }
    }

    /**
     * 将字节数组转换成16进组的字符串
     */
    public static String byteArrayToHex(byte[] byteArray) {
        // 初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        char[] resultCharArray = new char[byteArray.length * 2];

        int index = 0;
        // 遍历字节数组，通过位运算，转换成字符放到字符数据中
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        return new String(resultCharArray);
    }


    public static String MD5(String plaintext, String key) {
        MessageDigest messageDigest = null;
        try {
            plaintext += key;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(plaintext.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("",e);
            return "";
        }

        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }
    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes(StandardCharsets.UTF_8));
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }
    public static String Md5Encrypt(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }
    public static void main(String args[]) {
        long startTime=System.currentTimeMillis();   //获取开始时间
        String str="{\"corpids\":[\"09628f10c12d41ac9feb8a8a93e744fc\",\"0EB12081338B47E4B339FD1EF6F7149A\",\"30505dc17ff647dbb5bead74ff666d0a\",\"374A7311243C472DA0D28728EB0E7474\",\"47F25AB30F534E7EAFE4B4B9C56FBEE1\",\"4E86106C3CC6487D8212F3AAF4A9EB3C\",\"4E8635C0B7724FB995774428A024CEC1\",\"5F8ACF3B67E44B228CDE6A160B3E2682\",\"880BC257F403415BADC5B8F54056A902\",\"89726377CF7149509B76917DBFF65E24\",\"8ca369a8cee44151a8d5190e472cca7b\",\"925A560C260D40DDB5A6E0E38F3D704B\",\"945297017A7F482884CCA42C6CE90299\",\"9F4247059FCA43D386D6266774052A25\",\"A49310801F9D49FCB9ADE1869DA00EA1\",\"a6b0fee016d04a39a2563a8e43f5f63b\",\"aa63c53acf1241768f8cd4fce28632ad\",\"B7A9AB9EB7FC467BB3A786F12C9F45AD\",\"B868521B4E794A1896C2F746257B8A5F\",\"BD64681F8F7741EC9C8E6191944BE6D1\",\"C7F2EEAB936640F6A4A347BCEA405DE1\",\"CAA8138E04B74E38B147F34D324B3063\",\"D0FAF6E8A4224D8F9A25290AA4D8411A\",\"d3065831f00343899b5484f5671753f9\",\"E1CD3AE184874CA8854E6C7DA90B7518\",\"E668C4AF2A6E49DEB8C7039DAF8CEB50\",\"E71CB26EC24243339810985C4DF93ED4\",\"e760aa68578740b483f2490802949f89\",\"EC3C8BAA0D35435894CA638B705A7CF6\",\"f128ffb5a7e640cf9cb571841b1336dd\",\"F18B521EF386468BAC705104793496E9\",\"F2120ABB63824BF69F9F1E17F5728473\",\"F24E947C2B544482AB60533B4637A157\",\"f4b149f78ffe4a9394df177a5b829004\",\"FC597876044544BF8216F121A472D086\"],\"cleanNegative\":false,\"usemx\":false,\"ac_max\":1,\"per_max\":\"201801\",\"per_min\":\"201801\",\"number_begin\":\"\",\"number_end\":\"\"}{\"corpids\":[\"09628f10c12d41ac9feb8a8a93e744fc\",\"0EB12081338B47E4B339FD1EF6F7149A\",\"30505dc17ff647dbb5bead74ff666d0a\",\"374A7311243C472DA0D28728EB0E7474\",\"47F25AB30F534E7EAFE4B4B9C56FBEE1\",\"4E86106C3CC6487D8212F3AAF4A9EB3C\",\"4E8635C0B7724FB995774428A024CEC1\",\"5F8ACF3B67E44B228CDE6A160B3E2682\",\"880BC257F403415BADC5B8F54056A902\",\"89726377CF7149509B76917DBFF65E24\",\"8ca369a8cee44151a8d5190e472cca7b\",\"925A560C260D40DDB5A6E0E38F3D704B\",\"945297017A7F482884CCA42C6CE90299\",\"9F4247059FCA43D386D6266774052A25\",\"A49310801F9D49FCB9ADE1869DA00EA1\",\"a6b0fee016d04a39a2563a8e43f5f63b\",\"aa63c53acf1241768f8cd4fce28632ad\",\"B7A9AB9EB7FC467BB3A786F12C9F45AD\",\"B868521B4E794A1896C2F746257B8A5F\",\"BD64681F8F7741EC9C8E6191944BE6D1\",\"C7F2EEAB936640F6A4A347BCEA405DE1\",\"CAA8138E04B74E38B147F34D324B3063\",\"D0FAF6E8A4224D8F9A25290AA4D8411A\",\"d3065831f00343899b5484f5671753f9\",\"E1CD3AE184874CA8854E6C7DA90B7518\",\"E668C4AF2A6E49DEB8C7039DAF8CEB50\",\"E71CB26EC24243339810985C4DF93ED4\",\"e760aa68578740b483f2490802949f89\",\"EC3C8BAA0D35435894CA638B705A7CF6\",\"f128ffb5a7e640cf9cb571841b1336dd\",\"F18B521EF386468BAC705104793496E9\",\"F2120ABB63824BF69F9F1E17F5728473\",\"F24E947C2B544482AB60533B4637A157\",\"f4b149f78ffe4a9394df177a5b829004\",\"FC597876044544BF8216F121A472D086\"],\"cleanNegative\":false,\"usemx\":false,\"ac_max\":1,\"per_max\":\"201801\",\"per_min\":\"201801\",\"number_begin\":\"\",\"number_end\":\"\"}{\"corpids\":[\"09628f10c12d41ac9feb8a8a93e744fc\",\"0EB12081338B47E4B339FD1EF6F7149A\",\"30505dc17ff647dbb5bead74ff666d0a\",\"374A7311243C472DA0D28728EB0E7474\",\"47F25AB30F534E7EAFE4B4B9C56FBEE1\",\"4E86106C3CC6487D8212F3AAF4A9EB3C\",\"4E8635C0B7724FB995774428A024CEC1\",\"5F8ACF3B67E44B228CDE6A160B3E2682\",\"880BC257F403415BADC5B8F54056A902\",\"89726377CF7149509B76917DBFF65E24\",\"8ca369a8cee44151a8d5190e472cca7b\",\"925A560C260D40DDB5A6E0E38F3D704B\",\"945297017A7F482884CCA42C6CE90299\",\"9F4247059FCA43D386D6266774052A25\",\"A49310801F9D49FCB9ADE1869DA00EA1\",\"a6b0fee016d04a39a2563a8e43f5f63b\",\"aa63c53acf1241768f8cd4fce28632ad\",\"B7A9AB9EB7FC467BB3A786F12C9F45AD\",\"B868521B4E794A1896C2F746257B8A5F\",\"BD64681F8F7741EC9C8E6191944BE6D1\",\"C7F2EEAB936640F6A4A347BCEA405DE1\",\"CAA8138E04B74E38B147F34D324B3063\",\"D0FAF6E8A4224D8F9A25290AA4D8411A\",\"d3065831f00343899b5484f5671753f9\",\"E1CD3AE184874CA8854E6C7DA90B7518\",\"E668C4AF2A6E49DEB8C7039DAF8CEB50\",\"E71CB26EC24243339810985C4DF93ED4\",\"e760aa68578740b483f2490802949f89\",\"EC3C8BAA0D35435894CA638B705A7CF6\",\"f128ffb5a7e640cf9cb571841b1336dd\",\"F18B521EF386468BAC705104793496E9\",\"F2120ABB63824BF69F9F1E17F5728473\",\"F24E947C2B544482AB60533B4637A157\",\"f4b149f78ffe4a9394df177a5b829004\",\"FC597876044544BF8216F121A472D086\"],\"cleanNegative\":false,\"usemx\":false,\"ac_max\":1,\"per_max\":\"201801\",\"per_min\":\"201801\",\"number_begin\":\"\",\"number_end\":\"\"}{\"corpids\":[\"09628f10c12d41ac9feb8a8a93e744fc\",\"0EB12081338B47E4B339FD1EF6F7149A\",\"30505dc17ff647dbb5bead74ff666d0a\",\"374A7311243C472DA0D28728EB0E7474\",\"47F25AB30F534E7EAFE4B4B9C56FBEE1\",\"4E86106C3CC6487D8212F3AAF4A9EB3C\",\"4E8635C0B7724FB995774428A024CEC1\",\"5F8ACF3B67E44B228CDE6A160B3E2682\",\"880BC257F403415BADC5B8F54056A902\",\"89726377CF7149509B76917DBFF65E24\",\"8ca369a8cee44151a8d5190e472cca7b\",\"925A560C260D40DDB5A6E0E38F3D704B\",\"945297017A7F482884CCA42C6CE90299\",\"9F4247059FCA43D386D6266774052A25\",\"A49310801F9D49FCB9ADE1869DA00EA1\",\"a6b0fee016d04a39a2563a8e43f5f63b\",\"aa63c53acf1241768f8cd4fce28632ad\",\"B7A9AB9EB7FC467BB3A786F12C9F45AD\",\"B868521B4E794A1896C2F746257B8A5F\",\"BD64681F8F7741EC9C8E6191944BE6D1\",\"C7F2EEAB936640F6A4A347BCEA405DE1\",\"CAA8138E04B74E38B147F34D324B3063\",\"D0FAF6E8A4224D8F9A25290AA4D8411A\",\"d3065831f00343899b5484f5671753f9\",\"E1CD3AE184874CA8854E6C7DA90B7518\",\"E668C4AF2A6E49DEB8C7039DAF8CEB50\",\"E71CB26EC24243339810985C4DF93ED4\",\"e760aa68578740b483f2490802949f89\",\"EC3C8BAA0D35435894CA638B705A7CF6\",\"f128ffb5a7e640cf9cb571841b1336dd\",\"F18B521EF386468BAC705104793496E9\",\"F2120ABB63824BF69F9F1E17F5728473\",\"F24E947C2B544482AB60533B4637A157\",\"f4b149f78ffe4a9394df177a5b829004\",\"FC597876044544BF8216F121A472D086\"],\"cleanNegative\":false,\"usemx\":false,\"ac_max\":1,\"per_max\":\"201801\",\"per_min\":\"201801\",\"number_begin\":\"\",\"number_end\":\"\"}";
        log.info(getMD5(str));
        long endTime=System.currentTimeMillis(); //获取结束时间

        log.info("程序运行时间： "+(endTime-startTime)+"ms");
    }


}
