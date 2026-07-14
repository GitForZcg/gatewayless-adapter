package com.personal.demo.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sm2Key {

    //标准公钥头
    private String sm2_h;
    //裸公钥X
    private String sm2_x;
    //裸公钥Y
    private String sm2_y;

    public Sm2Key(String sm2_h, String sm2_x, String sm2_y) {
        this.sm2_h = sm2_h;
        this.sm2_x = sm2_x;
        this.sm2_y = sm2_y;
    }

}
