package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MagazinePage;
import com.d2c.member.query.MagazinePageSearcher;

public interface MagazinePageService {

    /**
     * 插入一条记录
     *
     * @param magazinePage
     * @return
     */
    MagazinePage insert(MagazinePage magazinePage);

    /**
     * 更新一条记录
     *
     * @param magazinePage
     * @return
     */
    int update(MagazinePage magazinePage);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MagazinePage findById(Long id);

    /**
     * 根据code查询
     *
     * @param id
     * @return
     */
    MagazinePage findByCode(String code);

    /**
     * 分页查询
     *
     * @param searcher
     * @return
     */
    PageResult<MagazinePage> findBySearcher(MagazinePageSearcher searcher, PageModel page);

    /**
     * 按条件统计总数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(MagazinePageSearcher searcher);

    /**
     * 上下架
     *
     * @param quiz
     * @return
     */
    int updateStatus(Long id, Integer status, String operator);

    /**
     * 绑定杂志定义
     *
     * @param id
     * @param magazineId
     * @return
     */
    int updateMagazineId(Long id, Long magazineId);

    /**
     * 更新顺序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

}
