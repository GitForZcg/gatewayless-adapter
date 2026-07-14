package com.personal.demo.response.member.coupon;

import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class PageOptions {

    /**
     * 当前第几页
     */
    private Integer currentPage;

    /**
     * 页面大小
     */
    private Integer perPage;

    /**
     * 总条数
     */
    private Integer totalItems;

    /**
     *
     */
    private Boolean showMorePage;

    /**
     * 起始位置
     */
    private Integer start;

    /**
     * 结束位置
     */
    private Integer to;
}
