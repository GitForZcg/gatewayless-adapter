package com.personal.demo.pojo.base;

import com.personal.demo.anno.Signature;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/18 11:13
 */

@Data
@Getter
@Setter
@Signature(strategy = "SM2")
public abstract class AdapterBaseSignParam implements SignatureParam {

    protected abstract Set<String> needSign();

    protected abstract Set<String> needSignParam();

    @Override
    public Set<String> needSignature() {
        return this.needSign();
    }

    @Override
    public Set<String> needSignatureParam() {
        return this.needSignParam();
    }

}
