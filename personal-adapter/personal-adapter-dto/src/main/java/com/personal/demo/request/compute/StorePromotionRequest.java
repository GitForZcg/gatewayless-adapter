package com.personal.demo.request.compute;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2026年01月17日 10:35 AM
 */
@Data
@Accessors(chain = true)
public class StorePromotionRequest implements Serializable {

    /**
     * 门店编码
     */
    @NotEmpty(message = "门店编码不能为空", groups = ValidationGroups.computeGroup.class)
    private String storeCode;

    /**
     * 会员编码
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.computeGroup.class)
    private String memberCode;

    /**
     * 会员openId
     */

    private String openId;

    /**
     * 会员等级 id
     */
    private String gradeId;



}
