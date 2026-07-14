package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.DemoFinanceMd5Param;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

/**
 * 适配样例导入凭证响应对象
 * @date: 2025/9/15 11:53 
 */
@Data
public class DemoImportVoucherRespDto implements DemoFinanceMd5Param, Serializable {

	@Serial
	private static final long serialVersionUID = 5727917056791903943L;

	/**
	 * 公司编码
	 */
	private String corpNumber;
	private String message;
	/**
	 *  期间，月
	 */
	private String periodNumber;
	/**
	 * 状态
	 * INSERT_ERROR、INSERT_SUCCESS、UPDATE_ERROR、UPDATE_SUCCESS
	 */
	private String status;
	private String targetBillid;
	private String targetBillnumber;
	private String voucherSoucerBillid;
	/**
	 * 凭证来源编号
	 */
	private String voucherSourceNumber; 

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