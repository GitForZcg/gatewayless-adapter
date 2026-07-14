package com.personal.demo.pojo.dto.request.product;

import com.personal.demo.pojo.base.BaseValidationSignParam;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fxs
 * @Date: 2025/8/13 10:33
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExternalProductDetailRequest extends BaseValidationSignParam {

    /**
     * 集团代码
     */
    @NotEmpty(message = "商户代码为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String mid;

    /**
     * 品牌代码
     */
    @NotEmpty(message = "品牌代码为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String bid;

    /**
     * 批次号
     */
    @NotEmpty(message = "批次号为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String batchNumber;


    /**
     * 门店代码
     */
    @NotEmpty(message = "门店代码 为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String sid;

    /**
     * 菜类代码（二级菜品分类）
     */
    private String sortCode;


    @Override
    public Map<String, Object> getOriginalParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("sortCode", sortCode);
        params.put("mid", mid);
        params.put("bid", bid);
        params.put("batchNumber", batchNumber);
        return params;
    }
    @Override
    protected Map<String, Object> getHeaderParams() {
        // 返回当前类特有的头部参数（如果有的话）
        // 这里可以添加除了基础签名参数之外的其他头部参数
        Map<String, Object> params = new HashMap<>();
        // 例如：params.put("version", "1.0");
        return params;
    }

    @Override
    public Map<String, Object> getSignatureParams() {
        // 获取父类的基础签名参数（appid, sign, apisign, timestamp）
        Map<String, Object> params = super.getSignatureParams();

        // 添加子类特有的头部参数
        params.putAll(this.getHeaderParams());

        // 添加业务参数
        params.putAll(this.getOriginalParams());

        return params;
    }
}
