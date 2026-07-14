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
 * @date 2025/7/16 09:42
 */

@Data
@Getter
@Setter
@Signature(strategy = "SM2", signField = "sign", appIdField = "appid")
public abstract class BaseValidationSignParam implements SignatureValidationParam {

    private String appid;
    private String sign;
    private String apisign;
    private String timestamp;

    @Override
    public String getSignature() {
        return this.sign;
    }

    @Override
    public String getAppId() {
        return this.appid;
    }

    protected abstract Map<String, Object> getHeaderParams();

    @Override
    public Map<String, Object> getSignatureParams() {
        Map<String, Object> headerParams = new HashMap<>();
        headerParams.put("appid", this.appid);
        headerParams.put("sign", this.sign);
        headerParams.put("apisign", this.apisign);
        headerParams.put("timestamp", this.timestamp);
        return headerParams;
    }
}
