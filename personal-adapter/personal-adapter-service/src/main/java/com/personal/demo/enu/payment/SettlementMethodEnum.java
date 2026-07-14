package com.personal.demo.enu.payment;

public enum SettlementMethodEnum {
    /**
     * 先款后货
     */
    PBD("PBD", "RDD174258163361526867"),
    /**
     * 先货后款
     */
    DBP("DBP", "RDD174258144761884760");

    private  String code;
    private  String desc;


    SettlementMethodEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据code获取desc
     */
    public static String getDescByCode(String code) {
        for (SettlementMethodEnum method : values()) {
            if (method.getCode().equals(code)) {
                return method.getDesc();
            }
        }
        return null;
    }

    /**
     * 根据desc获取code
     */
    public static String getCodeByDesc(String desc) {
        for (SettlementMethodEnum method : values()) {
            if (method.getDesc().equals(desc)) {
                return method.getCode();
            }
        }
        return null;
    }


}
