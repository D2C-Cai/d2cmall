package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberTag;
import com.d2c.member.query.MemberTagSearcher;

public interface MemberTagService {

    /**
     * 根据id获取标签
     *
     * @param id
     * @return
     */
    MemberTag findById(Long id);

    /**
     * 查询标签
     *
     * @param page
     * @return
     */
    PageResult<MemberTag> findBySearch(PageModel page, MemberTagSearcher searcher);

    /**
     * 查询标签数量
     *
     * @param page
     * @return
     */
    Integer countBySearch(MemberTagSearcher searcher);

    /**
     * 新增
     *
     * @param memberTag
     * @return
     */
    MemberTag insert(MemberTag memberTag);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 更新
     *
     * @param memberTag
     * @return
     */
    int update(MemberTag memberTag);

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