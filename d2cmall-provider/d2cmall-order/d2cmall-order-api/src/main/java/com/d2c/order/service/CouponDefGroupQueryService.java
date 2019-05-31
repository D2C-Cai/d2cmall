package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.query.CouponDefGroupSearcher;

import java.util.Date;
import java.util.List;

public interface CouponDefGroupQueryService {

    /**
     * 通过定义组id获得具体的定义组数据
     *
     * @param groupId 定义组id
     * @return
     */
    CouponDefGroup findById(Long groupId);

    /**
     * 通过定义组code获得具体的定义组数据
     *
     * @param groupId 定义组id
     * @return
     */
    CouponDefGroup findByCode(String code);

    /**
     * 通过暗码查找出优惠券定义组
     *
     * @param password 暗码
     * @return
     */
    CouponDefGroup findByCipher(String cipher);

    PageResult<Long> findByUpdateClaimed(Date modifyDate, PageModel page);

    /**
     * 通过查询条件和分页条件，得到封装后优惠券定义组的分页数据
     *
     * @param searcher 查询条件
     * @param page     分页
     * @return
     */
    PageResult<CouponDefGroup> findBySearch(CouponDefGroupSearcher searcher, PageModel page);

    /**
     * 通过查询条件得到符合条件的数量
     *
     * @param searcher 查询条件
     * @return
     */
    int countBySearch(CouponDefGroupSearcher searcher);

    /**
     * 通过查询条件和分页条件，得到封装后优惠券定义组的简单数据集合
     *
     * @param searcher
     * @param page
     * @return
     */
    List<HelpDTO> findHelpDtosBySearch(CouponDefGroupSearcher searcher, PageModel page);

    boolean getCheckClaimLimit(CouponDefGroup group, Long memberInfoId, String loginCode);

    /**
     * 更新缓存
     *
     * @param dto
     * @return
     */
    CouponDefGroup updateCache(CouponDefGroup dto);

    void clearCache(Long id);

}
