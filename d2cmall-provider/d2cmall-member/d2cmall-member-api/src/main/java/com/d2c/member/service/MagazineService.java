package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Magazine;
import com.d2c.member.query.MagazineSearcher;

public interface MagazineService {

    /**
     * 插入一条记录
     *
     * @param magazine
     * @return
     */
    Magazine insert(Magazine magazine);

    /**
     * 更新一条记录
     *
     * @param magazine
     * @return
     */
    int update(Magazine magazine);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Magazine findById(Long id);

    /**
     * 分页查询
     *
     * @param searcher
     * @return
     */
    PageResult<Magazine> findBySearcher(MagazineSearcher searcher, PageModel page);

    /**
     * 按条件统计总数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(MagazineSearcher searcher);

    /**
     * 上下架
     *
     * @param quiz
     * @return
     */
    int updateStatus(Long id, Integer status, String operator);

}
