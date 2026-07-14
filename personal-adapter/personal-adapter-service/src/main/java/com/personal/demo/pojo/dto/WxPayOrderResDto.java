package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.AdapterBaseSignParam;
import com.google.common.collect.ImmutableSet;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxPayOrderResDto extends AdapterBaseSignParam implements Serializable {


    private String testId;
    private String testMsg;
    private String testItem;
    private String type;
    /**
     * 需要加签的属性参数
     * 看接口文档
     */
    public static final Set<String> NEED_SIGN_PARAMS = ImmutableSet.of("testId", "testMsg", "testItem", "type");

//    /**
//     * 需要加/解密的属性参数
//     */
//    public static final Set<String> NEED_ENCRYPT_OR_DECRYPT_PARAMS = ImmutableSet.of("rt22_marketingRule");


    @Override
    protected Set<String> needSign() {
        return createSignature(NEED_SIGN_PARAMS);
    }

    @Override
    protected Set<String> needSignParam() {
        return createSignatureParam(null);
    }

}
