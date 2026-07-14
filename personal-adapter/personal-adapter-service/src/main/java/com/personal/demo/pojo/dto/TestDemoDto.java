package com.personal.demo.pojo.dto;


import com.personal.demo.pojo.base.BaseEcoPublicParam;
import com.personal.demo.request.group.ValidationGroups;
import com.google.common.collect.ImmutableSet;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class TestDemoDto extends BaseEcoPublicParam {

    @NotEmpty(message = "aceId不能为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String aceId;

    @NotEmpty(message = "type不能为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String type;

    //todo 改造
    public static final Set<String> NEED_SIGN_PARAMS = ImmutableSet.of("aceId", "type");


    @Override
    protected Map<String, Object> getPublicParams() {
        return super.getSignatureParams();
    }

    @Override
    public Map<String, Object> getSignatureParams() {
        Map<String, Object> params = this.getPublicParams();
        params.putAll(this.getOriginalParams());
        return params;
    }

    @Override
    public Map<String, Object> getOriginalParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("aceId", aceId);
        params.put("type", type);
        return params;
    }

}
