package com.personal.demo.consts;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/2 10:47
 */
public interface BizCode {
    /*
     * ===========================>>>>>>响应内容 21xx 开头 <<<<<<===========================
     */

    /**
     * 验证签名错误
     */
    String VERIFY_FAIL = "211";

    /**
     * 响应失败
     */
    String RESP_FAIL = "212";

    /*
     * ===========================>>>>>>支付业务 31xx 开头 <<<<<<===========================
     */

    /**
     * 调用节点动作不存在
     */
    String NODE_NO_FOUND = "311";
    /**
     * 调用节点不支持
     */
    String NODE_NOT_SUPPORTED = "312";
    /**
     * 调用类型不支持
     */
    String TYPE_NOT_SUPPORTED = "313";
    /**
     * 转化器不存在
     */
    String CONVERTER_NOT_FOUND = "314";
    /**
     * 找不到目标类
     */
    String CLASS_NOT_FOUND = "315";
    /**
     * 参数转化异常
     */
    String PARAMS_CONVERT_ERROR = "316";


    /*
     * ===========================>>>>>>参数校验 41xx 开头 <<<<<<===========================
     */
    /**
     * 参数非法
     */
    String VALID_GROUP_VALID = "414";
    /**
     * 参数非法
     */
    String PARAM_VALID = "411";
    /**
     * 参数非法
     */
    String TYPE_NOT_FOUND = "412";


    /*
     * ===========================>>>>>>验证签名 41xx 开头 <<<<<<===========================
     */
    String SIGNATURE_ERROR = "403";

}
