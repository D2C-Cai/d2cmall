package com.d2c.order.service;

import com.d2c.order.model.ShareRedPacketsGroup;

public interface ShareRedPacketsGroupService {

    /**
     * 新建拼团
     *
     * @param group
     * @return
     */
    ShareRedPacketsGroup insert(ShareRedPacketsGroup group);

    /**
     * 查询拼团
     *
     * @param id
     * @return
     */
    ShareRedPacketsGroup findById(Long id);

    /**
     * 通过开团人查询
     *
     * @param memberId
     * @return
     */
    ShareRedPacketsGroup findByInitiatorMemberId(Long memberId, Long promotionId);

    /**
     * 修改当前人数
     *
     * @param id
     * @param number
     * @return
     */
    int updateNumber(Long id, Integer number);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

}
