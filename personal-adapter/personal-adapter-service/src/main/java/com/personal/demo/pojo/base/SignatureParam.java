package com.personal.demo.pojo.base;

import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/18 11:15
 */
public interface SignatureParam {

    /**
     * 是否需要签名
     */
    Set<String> needSignature();

    /**
     * 创建签名
     */
    default Set<String> createSignature(Set<String> set) {
        return set;
    }

    /**
     * 需要加密或解密的参数
     */
    Set<String> needSignatureParam();

    /**
     * 创建加密或者解密的参数
     */
    default Set<String> createSignatureParam(Set<String> set) {
        return set;
    }

}
