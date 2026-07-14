package com.personal.demo.serivce.member;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseTradePublicParam;
import com.personal.demo.serivce.AbstractMemberMD5Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * SM2 统一调用工具类
 * <p>
 * 职责：
 * 1. 执行 SM2 签名、验签、发送报文
 * 2. 接收标准外层格式 { "errcode":0, "errmsg":"OK", "res":{...} }
 * 3. 自动提取 res 并反序列化为目标业务对象
 * <p>
 * 使用范围：
 * 所有依赖 AbstractSM2Service 的接口，均可通过本工具一行代码完成“签名→调接口→拿结果”
 * <p>
 *
 * @author fxs
 */
@Slf4j
@Component
public class Sm2Invoker {

    private final AbstractMemberMD5Service memberMD5Service;


    public Sm2Invoker(AbstractMemberMD5Service memberMD5Service) {
        this.memberMD5Service = memberMD5Service;
    }

    /**
     * 统一封装：签名 → 调接口 → 提取 res 并转为目标类型
     *
     * @param reqDto 任意请求体（会向上转型为 Object 传给 SM2 服务）
     * @param node   节点信息
     * @param clazz  res 里要转成的目标类型
     * @param <T>    返回的业务 DTO
     * @return 目标业务对象
     */
    public <T extends BaseMemberPublicParam, R> R invoke(T reqDto,
                                                         BaseNode node, Class<R> clazz) {

        LinkedHashMap<String, Object> dataMap = memberMD5Service.executeSign(reqDto, node);

        return memberMD5Service.executeResult(dataMap, node, clazz);
    }

    public <T extends BaseMemberPublicParam, R> R invoke(T reqDto,
                                                         BaseNode node,  Type type) {

        LinkedHashMap<String, Object> dataMap = memberMD5Service.executeSign(reqDto, node);
        return memberMD5Service.executeResult(dataMap, node, type);
    }


    public <T extends BaseMemberPublicParam, R> List<R> invokeList(T reqDto,
                                                         BaseNode node, Class<R> clazz) {

        LinkedHashMap<String, Object> dataMap = memberMD5Service.executeSign(reqDto, node);

        return memberMD5Service.executeResultList(dataMap, node, clazz);
    }


    public <T extends BaseMemberPublicParam, R> Map<String, Object> invokeMap(T reqDto, BaseNode node) {

        LinkedHashMap<String, Object> dataMap = memberMD5Service.executeSign(reqDto, node);

        return memberMD5Service.executeResultMap(dataMap, node);
    }

    public <T extends BaseTradePublicParam, R> Map<String, Object> invokeMap(T reqDto, BaseNode node) {

        LinkedHashMap<String, Object> dataMap = memberMD5Service.executeSign(reqDto, node);

        return memberMD5Service.executeResultMap(dataMap, node);
    }

    /**
     * 统一封装：签名 → 调接口 → 提取 res 并转为目标类型
     *
     * @param reqDto 任意请求体（会向上转型为 Object 传给 SM2 服务）
     * @param node   节点信息
     * @param clazz  res 里要转成的目标类型
     * @param <T>    返回的业务 DTO
     * @return 目标业务对象
     */
    public <T extends BaseTradePublicParam, R> R invoke(T reqDto,
                                                        BaseNode node, Class<R> clazz) {

        LinkedHashMap<String, Object> dataMap = memberMD5Service.executeSign(reqDto, node);

        return memberMD5Service.executeResult(dataMap, node, clazz);
    }

}
