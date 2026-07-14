package com.personal.demo.request.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/11 13:55
 */
@Data
@Accessors(chain = true)
public class InvoiceGetParams {

    /**
     * 1、门店uuid（必填，json传输）
     */
    private String storeCode;

    /**
     * 2、销方税号（必填，json传输）
     */
    private String sellerNum;

    /**
     * 4、购买方类型（必填，json传输）01企业，03个人 - 以下为直连开票模式，目前只有tms通道支持
     */
    private String gmflx;

    /**
     * 5、购买方纳税人识别号（必填，json传输）购买方识别号规则详见"购方税号规则",当"购买方类型"为企业时(即01),购方税号需要必填.专票时必填
     */
    private String gmfsbh;

    /**
     * 6、发票抬头（必填，json传输）
     */
    private String gmfmc;

    /**
     * 7、购买方地址（非必填，json传输）
     */
    private String gmfdz;

    /**
     * 8、购买方电话（非必填，json传输）购买方固定信息电话信息
     */
    private String gmfdh;

    /**
     * 9、购买方银行名称（非必填，json传输）购买方对应的开户行银行
     */
    private String gmfyh;

    /**
     * 10、购买方银行账号（非必填，json传输）购买方对应的开户行账号
     */
    private String gmfzh;

    /**
     * 12、购买方邮箱（非必填，json传输）如果填写,发票类别为电子发票时会自动发送邮件（手机或邮箱选填一个）
     */
    private String gmfdzyx;

    /**
     * 3、单号id（必填，json传输）
     */
    private String num;

    /**
     * 13、订单支付时间（必填，json传输）YYYY-MM-dd HH:mm:ss - 控制N天内可开票
     */
    private String strReportDate;

    /**
     * 15、当前开票金额（必填，json传输）
     */
    private BigDecimal invoiceAmount;

    private BigDecimal remainInvoicePrice;

    /**
     * 16、总金额（必填，json传输）当前开票包含订单的总金额
     */
    private BigDecimal payableAmount;
    /**
     * 取消开票标识
     */
    private Integer cancelType = 0;

}
