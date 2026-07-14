package com.personal.demo.serivce.member.util;

import com.personal.demo.response.member.coupon.CouponDetailResultDto;
import com.common.tools.GsonUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Li QianQian
 * describe:
 * 优惠券排序工具类
 */
public class CouponSortUtil {
    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            // 添加中文日期格式支持
            DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy年M月d日 H:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy年MM月dd日 H:mm:ss")
    };

    private static final long HOURS_72_IN_MILLIS = 72 * 60 * 60 * 1000L;

    /**
     * 对优惠券列表进行排序，72小时内过期的优惠券放在最前面
     *
     * @param couponList 优惠券列表
     * @return 排序后的优惠券列表
     */
    public static List<CouponDetailResultDto> sortCouponsByExpiration(List<CouponDetailResultDto> couponList) {
        if (couponList == null || couponList.isEmpty()) {
            return couponList;
        }

        couponList.sort(createCouponComparator());
        return couponList;
    }

    /**
     * 创建优惠券比较器
     */
    private static Comparator<CouponDetailResultDto> createCouponComparator() {
        return (coupon1, coupon2) -> {
            LocalDateTime expiration1 = parseDateTime(coupon1.getValidityEndTime());
            LocalDateTime expiration2 = parseDateTime(coupon2.getValidityEndTime());

            // 如果两个日期都为空，视为相等
            if (expiration1 == null && expiration2 == null) {
                return 0;
            }

            // 其中一个为空，空值排在后面
            if (expiration1 == null) {
                return 1;
            }
            if (expiration2 == null) {
                return -1;
            }

            // 检查是否为72小时内过期
            boolean willExpireIn72Hours1 = isWithin72Hours(expiration1);
            boolean willExpireIn72Hours2 = isWithin72Hours(expiration2);

            // 72小时内过期的排在前面
            if (willExpireIn72Hours1 && !willExpireIn72Hours2) {
                return -1;
            }
            if (!willExpireIn72Hours1 && willExpireIn72Hours2) {
                return 1;
            }

            // 都在72小时内或都不在72小时内，按过期时间升序排列
            //return expiration1.compareTo(expiration2);
            // 返回过期时间较早的排在前面
            return expiration1.isBefore(expiration2) ? -1 : (expiration1.isEqual(expiration2) ? 0 : 1);
        };
    }

    /**
     * 解析日期时间字符串为LocalDateTime
     */
    private static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException e) {
                // 尝试下一个格式
                continue;
            }
        }

        // 如果所有格式都失败，尝试处理可能的时间戳格式
        try {
            long timestamp = Long.parseLong(dateTimeStr);
            return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, java.time.ZoneOffset.ofHours(8));
        } catch (NumberFormatException e) {
            // 不是时间戳格式，返回null
            return null;
        }
    }

    /**
     * 检查给定的过期时间是否在72小时内
     */
    private static boolean isWithin72Hours(LocalDateTime expirationTime) {
        if (expirationTime == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime seventyTwoHoursLater = now.plusHours(72);

        return !expirationTime.isAfter(seventyTwoHoursLater) && !expirationTime.isBefore(now);
    }

    /**
     * 批量处理优惠券的标红状态（72小时内过期的标红）
     */
    public static void markCouponsExpiringSoon(List<CouponDetailResultDto> couponList) {
        if (couponList == null || couponList.isEmpty()) {
            return;
        }

        for (CouponDetailResultDto coupon : couponList) {
            LocalDateTime expirationTime = parseDateTime(coupon.getValidityEndTime());
            if (expirationTime != null) {
                coupon.setIsMarkedRed(isWithin72Hours(expirationTime));
            } else {
                coupon.setIsMarkedRed(false);
            }
        }
    }

    /**
     * 综合方法：排序并标记标红状态
     */
    public static List<CouponDetailResultDto> processCouponList(List<CouponDetailResultDto> couponList) {
        markCouponsExpiringSoon(couponList);
        return sortCouponsByExpiration(couponList);
    }
    /**
     * 测试方法：验证生成模拟优惠券数据的逻辑
     */
    /*@Test
    public void testGenerateTestCoupons() {
        List<CouponDetailResultDto> coupons = new ArrayList<>();

        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        // 1. 72小时内过期的优惠券
        CouponDetailResultDto teshu = new CouponDetailResultDto();
        teshu.setCouponId("COUPON_001");
        teshu.setTemplateId(1001);
        teshu.setCouponName("特殊券----------------------");
        teshu.setCouponType(1);
        teshu.setValidityEndTime("2026年3月13日 23:59:59");
        coupons.add(teshu);


        // 1. 72小时内过期的优惠券
        CouponDetailResultDto coupon1 = new CouponDetailResultDto();
        coupon1.setCouponId("COUPON_001");
        coupon1.setTemplateId(1001);
        coupon1.setCouponName("48小时过期券");
        coupon1.setCouponType(1);
        coupon1.setValidityEndTime(now.plusHours(48).format(formatter));
        coupons.add(coupon1);

        // 2. 正常过期的优惠券（7天后）
        CouponDetailResultDto coupon2 = new CouponDetailResultDto();
        coupon2.setCouponId("COUPON_002");
        coupon2.setTemplateId(1002);
        coupon2.setCouponName("7天过期券");
        coupon2.setCouponType(2);
        coupon2.setValidityEndTime(now.plusDays(7).format(formatter));
        coupons.add(coupon2);

        // 3. 72小时内过期但更早的优惠券
        CouponDetailResultDto coupon3 = new CouponDetailResultDto();
        coupon3.setCouponId("COUPON_003");
        coupon3.setTemplateId(1003);
        coupon3.setCouponName("24小时过期券");
        coupon3.setCouponType(1);
        coupon3.setValidityEndTime(now.plusHours(24).format(formatter));
        coupons.add(coupon3);

        // 4. 无过期时间的优惠券
        CouponDetailResultDto coupon4 = new CouponDetailResultDto();
        coupon4.setCouponId("COUPON_004");
        coupon4.setTemplateId(1004);
        coupon4.setCouponName("无过期时间券");
        coupon4.setCouponType(3);
        coupon4.setValidityEndTime(null);
        coupons.add(coupon4);

        // 5. 已过期的优惠券
        CouponDetailResultDto coupon5 = new CouponDetailResultDto();
        coupon5.setCouponId("COUPON_005");
        coupon5.setTemplateId(1005);
        coupon5.setCouponName("已过期券");
        coupon5.setCouponType(1);
        coupon5.setValidityEndTime(now.minusDays(1).format(formatter));
        coupons.add(coupon5);

        // 6. 边界情况：正好72小时过期的优惠券
        CouponDetailResultDto coupon6 = new CouponDetailResultDto();
        coupon6.setCouponId("COUPON_006");
        coupon6.setTemplateId(1006);
        coupon6.setCouponName("72小时边界券");
        coupon6.setCouponType(2);
        coupon6.setValidityEndTime(now.plusHours(72).format(formatter));
        coupons.add(coupon6);

        // 验证生成的优惠券列表
        //assertNotNull(coupons);
        //assertEquals(6, coupons.size());

        // 验证每种优惠券类型
        //assertEquals("48小时过期券", coupons.get(0).getCouponName());
        //assertEquals("7天过期券", coupons.get(1).getCouponName());
        //assertEquals("24小时过期券", coupons.get(2).getCouponName());
        //assertEquals("无过期时间券", coupons.get(3).getCouponName());
        //assertEquals("已过期券", coupons.get(4).getCouponName());
        //assertEquals("72小时边界券", coupons.get(5).getCouponName());

        // 验证无过期时间的优惠券
        //assertNull(coupons.get(3).getValidityEndTime());

        processCouponList(coupons);


        System.out.println("测试生成的优惠券列表：" + GsonUtils.beanToJson(coupons));
    }*/
}
