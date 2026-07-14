package com.personal.demo.dto.finance;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 向适配样例导入的凭证分录信息
 *
 * @date: 2025/9/11 11:39
 */
@Data
public class ImportVoucherEntryInfoDto implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -6266215710118253559L;

    /**
     * 凭证主题类型（1 ：门店 2：结算公司  3：供应商）
     */
    @NotEmpty(message = "凭证主题类型为空", groups = ValidationGroups.financeGroup.class)
    private String vouchergrouptype;

    /**
     * 公司编码
     */
    @NotEmpty(message = "公司编码为空", groups = ValidationGroups.financeGroup.class)
    private String companyNumber;

    /**
     *  记账日期
     */
    @NotEmpty(message = "记账日期为空", groups = ValidationGroups.financeGroup.class)
    private String bookedDate;

    /**
     *  业务日期
     */
    @NotEmpty(message = "业务日期为空", groups = ValidationGroups.financeGroup.class)
    private String bizDate;

    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     *  期间，年
     */
    @Min(value = 2025, message = "期间，年无效", groups = ValidationGroups.financeGroup.class)
    private int periodYear;

    /**
     *  期间，月
     */
    @Min(value = 1, message = "期间，月无效", groups = ValidationGroups.financeGroup.class)
    @Max(value = 12, message = "期间，月无效", groups = ValidationGroups.financeGroup.class)
    private int periodNumber;

    /**
     *  凭证类型编码
     *  001记账凭证;002收款凭证;003付款凭证;004转账凭证
     */
    @NotEmpty(message = "凭证类型编码为空", groups = ValidationGroups.financeGroup.class)
    private String voucherType;

    /**
     * 附件数量
     */
    private int attaches;

    /**
     * 凭证参考信息
     */
    private String description;

    /**
     * 源凭证编码
     */
    @NotEmpty(message = "源凭证编码为空", groups = ValidationGroups.financeGroup.class)
    private String voucherNumber;

    /**
     * 制单人
     */
    private String creator;

    /**
     * 过账人
     */
    private String poster;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 分录号
     */
    @Min(value = 1, message = "分录号无效", groups = ValidationGroups.financeGroup.class)
    @Max(value = 100, message = "分录号无效", groups = ValidationGroups.financeGroup.class)
    private int entrySeq;

    /**
     * 分录摘要
     */
    @NotEmpty(message = "分录摘要为空", groups = ValidationGroups.financeGroup.class)
    private String voucherAbstract;

    /**
     * 云财务科目编码
     */
    @NotEmpty(message = "云财务科目编码为空", groups = ValidationGroups.financeGroup.class)
    private String accountNumber;

    /**
     * 币别编码	001
     */    
    private String currencyNumber = "001";

    /**
     * 汇率 1
     */
    private int localRate = 1;

    /**
     * 分录反向，1为借，0为贷
     */
    @Min(value = 0, message = "分录反向无效", groups = ValidationGroups.financeGroup.class)
    @Max(value = 1, message = "分录反向无效", groups = ValidationGroups.financeGroup.class)
    private int entryDC;

    /**
     * 分录或辅助帐金额
     */
    @NotNull(message = "分录或辅助帐金额为空", groups = ValidationGroups.financeGroup.class)
    private BigDecimal originalAmount;

    private int itemflag = 0;

    /**
     * 对方科目分录号
     */
    private int oppAccountSeq;

    /**
     * 主表项目
     */
    private String primaryItem;

    /**
     * 主表系数
     */
    private String supplyItem;

    /**
     * 附表项目
     */
    private int primaryCoef;

    /**
     * 附表系数
     */
    private int supplyCoef;

    /**
     * 现金流量金额
     */
    private int cashflowAmountOriginal;

    /**
     * 辅助帐行号
     */
    @Min(value = 1, message = "辅助帐行号无效", groups = ValidationGroups.financeGroup.class)
    @Max(value = 50, message = "辅助帐行号无效", groups = ValidationGroups.financeGroup.class)
    private int asstSeq;

    /**
     * 数量
     */
    private int qty;

    /**
     * 计量单位
     */
    private String measurement;

    /**
     * 原价
     */
    private int price;

    /**
     * 业务编码
     */
    private String bizNumber;

    /**
     * 结算方式
     */
    private String settlementNumber;

    /**
     * 结算号
     */
    private String settlementType;

    /**
     * 挂账，核销
     */
    private int cussent;

    /**
     * 核算项目1类型编码
     */
    @NotEmpty(message = "核算项目1类型编码为空", groups = ValidationGroups.financeGroup.class)
    private String asstActType1;

    /**
     * 核算项目1编码
     */
    @NotEmpty(message = "核算项目1编码为空", groups = ValidationGroups.financeGroup.class)
    private String asstActNumber1;

    /**
     * 核算项目1名称
     */
    @NotEmpty(message = "核算项目1名称为空", groups = ValidationGroups.financeGroup.class)
    private String asstActName1;

    /**
     * 核算项目2类型编码
     */
    private String asstActType2;

    /**
     * 核算项目2编码
     */
    private String asstActNumber2;

    /**
     * 核算项目2名称
     */
    private String asstActName2;

    /**
     * 核算项目3类型编码
     */
    private String asstActType3;

    /**
     * 核算项目3编码
     */
    private String asstActNumber3;

    /**
     * 核算项目3名称
     */
    private String asstActName3;

    private String asstActType4;

    private String asstActNumber4;

    private String asstActName4;
}
