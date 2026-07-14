package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.invoice.InvoiceSubjectInfoReqDto;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象发票MD5处理
 * @date 2025/7/24 13:37
 */

public interface InvoiceMD5Service {

    String executeSign(InvoiceSubjectInfoReqDto reqDto, BaseNode node);

    Boolean executeResult(InvoiceSubjectInfoReqDto reqDto, BaseNode node, Integer cancelType);

}
