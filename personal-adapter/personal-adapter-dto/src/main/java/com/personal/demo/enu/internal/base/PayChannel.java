package com.personal.demo.enu.internal.base;

public enum PayChannel {


    CASH ("2","cash", "微信支付"),
    BALANCE ("1003", "balance", "储值余额支付");


    private String thirdPayType;
    private String code;
    private String message;


    PayChannel(String thirdPayType,String code, String message) {
        this.code = code;
        this.thirdPayType = thirdPayType;
        this.message = message;
    }
    public String getThirdPayType() {
        return thirdPayType;
    }
    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }



}
