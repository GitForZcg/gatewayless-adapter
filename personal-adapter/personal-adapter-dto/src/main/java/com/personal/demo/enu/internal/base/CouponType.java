package com.personal.demo.enu.internal.base;


import java.util.Objects;

public enum CouponType {

    LI_JIAN_QUAN("产品兑换券",2,2,1,3,802),
    ZHE_KOU_QUAN("折扣券",2,2,3,7,802),
    DAI_JIN_QUAN("代金券",1,1,4,1,802),


    /**
     * 钱包券以后不再用
     */
    QIAN_BAO_QUAN("钱包券",999,999,2,1,802),
    ;

    public final String desc;
    /**
     * 微生活券类型
     */
    public final Integer wshType;
    /**
     * 适配样例营销中心券类型
     */
    public final Integer proType;
    /**
     * 券类型
     */
    public final Integer personalType;

    /**
     * 适配样例 ceType
     */
    public final Integer ceType;

    /**
     * PM标识ID（802表示微生活）
     */
    public final Integer pmid;


    CouponType(String desc,Integer type,Integer type1,Integer type2, Integer ceType,Integer pmid){
        this.desc = desc;
        this.wshType = type;
        this.proType = type1;
        this.personalType = type2;
        this.ceType = ceType;
        this.pmid = pmid;
    }

    public static CouponType valueOf(Integer type){
        for (CouponType couponType : values()) {
            if(Objects.equals(couponType.personalType, type)){
                return couponType;
            }
        }
        throw new IllegalArgumentException("未知的优惠券类型");
    }
    public static CouponType getCouponTypeByCeType(Integer ceType){
        for (CouponType couponType : values()) {
            if(Objects.equals(couponType.ceType, ceType)){
                return couponType;
            }
        }
        throw new IllegalArgumentException("未知的优惠券类型");
    }


}
