package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.BrandApply;
import com.d2c.content.query.BrandApplySearcher;

public interface BrandApplyService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    BrandApply findById(Long id);

    /**
     * 根据memberId查询
     *
     * @param memberId
     * @return
     */
    BrandApply findByMemberId(Long memberId);

    /**
     * 根据search查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<BrandApply> findBySearcher(BrandApplySearcher searcher, PageModel page);

    /**
     * 新增数据
     *
     * @param brandApply
     * @return
     */
    BrandApply insert(BrandApply brandApply);

    /**
     * 更新
     *
     * @param brandApply
     * @return
     */
    int update(BrandApply brandApply);

}
