package com.d2c.order.service;

import com.d2c.order.model.O2oSubscribeItem;

import java.util.List;

public interface O2oSubscribeItemService {

    /**
     * 通过id，查找门店预约明细数据
     *
     * @param id
     * @return
     */
    O2oSubscribeItem findById(Long id);

    /**
     * 通过门店预约id，查找对应的明细数据
     *
     * @param id
     * @return
     */
    List<O2oSubscribeItem> findBySubscribeId(Long id);

    /**
     * 通过门店id，查找明细的数量
     *
     * @param id
     * @return
     */
    int countBySubscribeId(Long id);

    /**
     * 插入一条门店明细数据
     *
     * @param item
     * @return
     */
    O2oSubscribeItem insert(O2oSubscribeItem item);

    /**
     * 通过门店id和会员id，删除门店明细
     *
     * @param id
     * @param memberId
     * @return
     */
    int deleteBySubscribeIdAndMemberId(Long id, Long memberId);

    /**
     * 删除关闭预约的商品订单
     *
     * @return
     */
    int deleteByProductIds(List<Long> ids);

    /**
     * 删除明细
     *
     * @param itemId
     * @param memberId
     * @return
     */
    int deleteByIdAndMemberId(Long itemId, Long memberId);

    /**
     * 合并数据
     *
     * @param memberSourceId
     * @param memberTargetId
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

}
