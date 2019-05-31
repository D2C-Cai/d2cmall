package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.ShareRedPackets;
import com.d2c.order.query.ShareRedPacketsSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface ShareRedPacketsService {

    /**
     * 新增
     *
     * @param shareRedPackets
     * @return
     */
    ShareRedPackets insert(ShareRedPackets shareRedPackets);

    /**
     * 通过团ID获取参团列表
     *
     * @param groupId
     * @return
     */
    List<ShareRedPackets> findByGroupId(Long groupId, String orderByStr);

    /**
     * 查询我的参与历史（非团长）
     *
     * @param memberId
     * @param promotionId
     * @param initiator
     * @return
     */
    List<ShareRedPackets> findHistory(Long memberId, Long promotionId, Integer initiator);

    /**
     * 查询该团已瓜分的钱
     *
     * @param id
     * @return
     */
    BigDecimal sumMoneyByGroupId(Long id);

    PageResult<ShareRedPackets> findBySearcher(ShareRedPacketsSearcher searcher, PageModel page);

    Integer countBySearcher(ShareRedPacketsSearcher searcher);

    int updateStatusByGroupId(Long groupId, Integer status);

}
