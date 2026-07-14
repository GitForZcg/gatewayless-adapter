package com.personal.demo.response.member.charge;

import lombok.Data;

/**
 * 分页信息DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class PageOptionsResultDto {

    /** 当前页码 */
    private Integer currentPage;

    /** 每页条数 */
    private Integer perPage;

    /** 是否展示更多页码 */
    private Boolean showMorePage;

    /** 是否展示上一页下一页 */
    private Boolean showPreAndNextPage;

    /** 是否在url追加总条数 */
    private Boolean urlAddTotalItems;

    /** 是否展示总条数 */
    private Boolean showTotalItems;

    /** 当前页样式类名 */
    private String curPageClass;

    /** 总条数 */
    private Integer totalItems;

    /** 起始索引 */
    private Integer start;

    /** 结束索引 */
    private Integer to;
}
