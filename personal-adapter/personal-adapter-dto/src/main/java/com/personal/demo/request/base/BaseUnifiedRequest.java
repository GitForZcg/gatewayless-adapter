package com.personal.demo.request.base;


import com.personal.demo.enu.internal.base.EnumNodeValueDeserializer;
import com.personal.demo.enu.internal.node.OrderNode;
import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 统一检索请求参数
 * @date 2025/7/2 10:41
 */
@Data
public class BaseUnifiedRequest {

    @NotNull(message = "type不能为空")
    private ServiceType type;

    @NotNull(message = "node不能为空")
    @JsonDeserialize(using = EnumNodeValueDeserializer.class)
    private BaseNode node;

    @NotNull(message = "params不能为空")
    private Object params;

    public static Builder builder() {
        return new Builder();
    }

    // Builder内部类
    public static class Builder {
        private final BaseUnifiedRequest request;

        private Builder() {
            request = new BaseUnifiedRequest();
        }

        public Builder type(ServiceType flowType) {
            request.type = flowType;
            return this;
        }

        public Builder node(BaseNode node) {
            request.node = node;
            return this;
        }


        public Builder params(Object unifiedPaymentParams) {
            request.params = unifiedPaymentParams;
            return this;
        }

        public BaseUnifiedRequest build() {
            return request;
        }
    }
}
