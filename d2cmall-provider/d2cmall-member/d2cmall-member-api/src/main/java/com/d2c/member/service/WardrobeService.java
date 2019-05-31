package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Wardrobe;
import com.d2c.member.query.WardrobeSearcher;

public interface WardrobeService {

    /**
     * 新增
     *
     * @param wardrobe
     * @return
     */
    Wardrobe insert(Wardrobe wardrobe);

    /**
     * 根据searcher查询
     *
     * @param query
     * @param page
     * @return
     */
    PageResult<Wardrobe> findBySearcher(WardrobeSearcher query, PageModel page);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 会员批量删除
     *
     * @param ids
     * @param memberId
     * @return
     */
    int deleteByMemberId(Long[] ids, Long memberId);

    /**
     * 更新
     *
     * @param wardrobe
     * @return
     */
    int update(Wardrobe wardrobe);

    /**
     * 查询数量
     *
     * @param query
     * @return
     */
    Integer countBySearcher(WardrobeSearcher query);

}
