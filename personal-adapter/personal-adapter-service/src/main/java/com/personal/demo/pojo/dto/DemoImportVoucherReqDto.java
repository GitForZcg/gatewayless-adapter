package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.DemoFinanceMd5Param;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

/**
 * 适配样例导入凭证请求参数对象
 * @date: 2025/9/11 10:58 
 */
@Data
public class DemoImportVoucherReqDto implements DemoFinanceMd5Param, Serializable {

	@Serial
	private static final long serialVersionUID = 1671845453397647830L;
	
	/**
	 * 凭证主题类型（1 ：门店 2：结算公司  3：供应商）
	 */
	private String vouchergrouptype;

	/**
	 * 公司编码
	 */
	private String companyNumber;

	/**
	 *  记账日期
	 */
	private String bookedDate;
	
	/**
	 *  业务日期
	 */
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
	private int periodYear;

	/**
	 *  期间，月
	 */
	private int periodNumber;

	/**
	 *  凭证类型编码
	 *  001记账凭证;002收款凭证;003付款凭证;004转账凭证
	 */
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
	private int entrySeq;

	/**
	 * 分录摘要
	 */
	private String voucherAbstract;

	/**
	 * 云财务科目编码
	 */
	private String accountNumber;

	/**
	 * 币别编码	001
	 */
	private String currencyNumber;

	/**
	 * 汇率 1
	 */
	private int localRate;

	/**
	 * 分录反向，1为借，0为贷
	 */
	private int entryDC;

	/**
	 * 分录或辅助帐金额
	 */
	private BigDecimal originalAmount;

	private int itemflag;

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
	private String asstActType1;

	/**
	 * 核算项目1编码
	 */
	private String asstActNumber1;

	/**
	 * 核算项目1名称
	 */
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

	private String asstActType5;

	private String asstActNumber5;

	private String asstActName5;

	private String asstActType6;

	private String asstActNumber6;

	private String asstActName6;

	private String asstActType7;

	private String asstActNumber7;

	private String asstActName7;

	@Override
	public Set<String> needSign() {
		return null;
	}

	@Override
	public Set<String> needSignParam() {
		return null;
	}

	@Override
	public String orderId() {
		return null;
	}
}