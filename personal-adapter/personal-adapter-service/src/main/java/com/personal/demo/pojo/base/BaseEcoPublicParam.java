package com.personal.demo.pojo.base;

import com.personal.demo.anno.Signature;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/8 10:48
 */
@Data
@Getter
@Setter
@Signature(strategy = "MD5", signField = "sign", appIdField = "app_id")
public abstract class BaseEcoPublicParam implements SignatureValidationParam {

    private String sign;
    private String app_id;

    @Override
    public String getSignature() {
        return this.sign;
    }

    @Override
    public String getAppId() {
        return this.app_id;
    }

    protected abstract Map<String, Object> getPublicParams();

    @Override
    public Map<String, Object> getSignatureParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", this.app_id);
        params.put("sign", this.sign);
        return params;
    }

}
