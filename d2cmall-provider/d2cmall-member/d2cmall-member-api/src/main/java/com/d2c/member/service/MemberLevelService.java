package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberLevel;
import com.d2c.member.query.MemberLevelSearcher;

public interface MemberLevelService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MemberLevel findById(Long id);

    /**
     * 根据等级查询
     *
     * @param level
     * @return
     */
    MemberLevel findByLevel(int level);

    /**
     * 新增
     *
     * @param memberLevel
     * @return
     */
    MemberLevel insert(MemberLevel memberLevel);

    /**
     * 更新
     *
     * @param memberLevel
     * @return
     */
    int update(MemberLevel memberLevel);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<MemberLevel> findBySearch(MemberLevelSearcher searcher, PageModel page);

    /**
     * 根据searcher数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(MemberLevelSearcher searcher);

    /**
     * 查找达到要求的最高级
     *
     * @param amount
     * @return
     */
    MemberLevel findVaildLevel(Integer amount);

}
