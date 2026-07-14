package com.personal.demo.serivce.member.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Li QianQian
 * describe:
 */
public class PriceUtils {
    /**
     * 金额转换: 元 - > 分
     * @param inputPrice
     * @return
     */
    public static int convertYuanToFen(BigDecimal inputPrice) {
        if (inputPrice == null) {
            return 0; // 或抛出异常，根据业务需求
        }

        try {
            BigDecimal fenAmount = inputPrice.multiply(new BigDecimal("100"));

            // 四舍五入
            fenAmount = fenAmount.setScale(0, RoundingMode.HALF_UP);

            // 检查范围并转换
            return fenAmount.intValueExact();
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("【金额转换失败 元 -> 分 】 输入金额: " + inputPrice, e);
        }
    }

    /**
     * 金额转换: 分 -> 元
     * @param fenStr 以分为单位的金额字符串
     * @return 以元为单位的BigDecimal，保留两位小数
     */
    public static BigDecimal convertFenToYuan(String fenStr) {
        if (fenStr == null || fenStr.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            BigDecimal fen = new BigDecimal(fenStr);
            return fen.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("【金额转换失败 分 -> 元】 输入金额: " + fenStr, e);
        }
    }

    public static BigDecimal converBaiFenBi(Integer paran) {
        BigDecimal result = BigDecimal.valueOf(paran).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP);

        if (result.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return result.setScale(0, RoundingMode.HALF_UP);
        } else {
            return result.setScale(1, RoundingMode.HALF_UP);
        }
    }
}
