package com.personal.demo.pojo.dto.invoice;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 纳税人详情信息
 * @date 2025/9/11 13:34
 */
public class InvoiceDetailInfoReqDto {

    /**
     * 1、门店uuid
     * json传输
     * 必填字段
     */
    private String strOrganId;

    /**
     * 2、销方税号
     * json传输
     * 必填字段
     */
    private String sellerNum;

    /**
     * 3、单号id
     * json传输
     * 必填字段
     */
    private String num;

    /**
     * 4、购买方类型
     * 01企业，03个人
     * json传输
     * 必填字段
     * 以下为直连开票模式，目前只有tms通道支持
     */
    private String GMFLX;

    /**
     * 5、购买方纳税人识别号
     * 购买方识别号规则详见"购方税号规则"
     * 当"购买方类型"为企业时(即01),购方税号需要必填
     * 专票时必填
     * json传输
     * 必填字段
     */
    private String GMFSBH;

    /**
     * 6、发票抬头
     * json传输
     * 必填字段
     */
    private String GMFMC;

    /**
     * 7、购买方地址
     * json传输
     * 非必填字段
     */
    private String GMFDZ;

    /**
     * 8、购买方电话
     * 购买方固定信息电话信息
     * json传输
     * 非必填字段
     */
    private String GMFDH;

    /**
     * 9、购买方银行名称
     * 购买方对应的开户行银行
     * json传输
     * 非必填字段
     */
    private String GMFYH;

    /**
     * 10、购买方银行账号
     * 购买方对应的开户行账号
     * json传输
     * 非必填字段
     */
    private String GMFZH;

    /**
     * 11、购买方手机
     * 购买方手机号（手机或邮箱选填一个）
     * json传输
     * 非必填字段
     */
    private String GMFSJH;

    /**
     * 12、购买方邮箱
     * 如果填写,发票类别为电子发票时会自动发送邮件（手机或邮箱选填一个）
     * json传输
     * 非必填字段
     */
    private String GMFDZYX;

    /**
     * 13、订单支付时间
     * 格式：YYYY-MM-dd HH:mm:ss
     * 控制N天内可开票
     * json传输
     * 必填字段
     */
    private String strReportDate;

    /**
     * 14、开票税率
     * json传输
     * 非必填字段
     */
    private BigDecimal taxRate;

    /**
     * 15、当前开票金额
     * json传输
     * 必填字段
     */
    private BigDecimal invoiceAmount;

    /**
     * 16、总金额
     * 当前开票包含订单的总金额
     * json传输
     * 必填字段
     */
    private BigDecimal totalAmount;

    /**
     * 17、可开票额度
     * OMP无入库业务订单-对接方需要传递此值
     * OMP有业务订单-对接方可不传递此值（如果传递不生效）
     * json传输
     * 必填字段
     */
    private BigDecimal remainInvoiceAmount;

    /**
     * 18、手机号
     * 用json传输
     * 默认为空，微生活会员传手机号
     * 非必填字段
     */
    private String phone;

    /**
     * 19、会员卡号
     * 用json传输
     * 默认为空，微生活储值开票传会员卡号
     * 非必填字段
     */
    private String cardNumber;

    public String getStrOrganId() {
        return strOrganId;
    }

    public void setStrOrganId(String strOrganId) {
        this.strOrganId = strOrganId;
    }

    public String getSellerNum() {
        return sellerNum;
    }

    public void setSellerNum(String sellerNum) {
        this.sellerNum = sellerNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGMFLX() {
        return GMFLX;
    }

    public void setGMFLX(String GMFLX) {
        this.GMFLX = GMFLX;
    }

    public String getGMFSBH() {
        return GMFSBH;
    }

    public void setGMFSBH(String GMFSBH) {
        this.GMFSBH = GMFSBH;
    }

    public String getGMFMC() {
        return GMFMC;
    }

    public void setGMFMC(String GMFMC) {
        this.GMFMC = GMFMC;
    }

    public String getGMFDZ() {
        return GMFDZ;
    }

    public void setGMFDZ(String GMFDZ) {
        this.GMFDZ = GMFDZ;
    }

    public String getGMFDH() {
        return GMFDH;
    }

    public void setGMFDH(String GMFDH) {
        this.GMFDH = GMFDH;
    }

    public String getGMFYH() {
        return GMFYH;
    }

    public void setGMFYH(String GMFYH) {
        this.GMFYH = GMFYH;
    }

    public String getGMFZH() {
        return GMFZH;
    }

    public void setGMFZH(String GMFZH) {
        this.GMFZH = GMFZH;
    }

    public String getGMFSJH() {
        return GMFSJH;
    }

    public void setGMFSJH(String GMFSJH) {
        this.GMFSJH = GMFSJH;
    }

    public String getGMFDZYX() {
        return GMFDZYX;
    }

    public void setGMFDZYX(String GMFDZYX) {
        this.GMFDZYX = GMFDZYX;
    }

    public String getStrReportDate() {
        return strReportDate;
    }

    public void setStrReportDate(String strReportDate) {
        this.strReportDate = strReportDate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getRemainInvoiceAmount() {
        return remainInvoiceAmount;
    }

    public void setRemainInvoiceAmount(BigDecimal remainInvoiceAmount) {
        this.remainInvoiceAmount = remainInvoiceAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
