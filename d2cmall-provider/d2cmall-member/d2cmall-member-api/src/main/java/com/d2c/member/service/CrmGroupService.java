package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.CrmGroup;
import com.d2c.member.query.CrmGroupSearcher;

public interface CrmGroupService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CrmGroup findById(Long id);

    /**
     * 根据name查询
     *
     * @param name
     * @return
     */
    CrmGroup findByName(String name);

    /**
     * 根据searcher查询
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<CrmGroup> findBySearcher(PageModel page, CrmGroupSearcher searcher);

    /**
     * 新增
     *
     * @param crmGroup
     * @return
     */
    CrmGroup insert(CrmGroup crmGroup);

    /**
     * 更新
     *
     * @param crmGroup
     * @return
     */
    int update(CrmGroup crmGroup);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

}
