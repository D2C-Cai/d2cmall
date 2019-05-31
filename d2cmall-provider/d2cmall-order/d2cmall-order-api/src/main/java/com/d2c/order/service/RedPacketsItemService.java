package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.query.RedPacketsItemSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface RedPacketsItemService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RedPacketsItem findById(Long id);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<RedPacketsItem> findBySearcher(RedPacketsItemSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearcher(RedPacketsItemSearcher searcher);

    /**
     * 新增
     *
     * @param redPacketsItem
     * @return
     */
    RedPacketsItem insert(RedPacketsItem redPacketsItem);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 红包返利
     *
     * @param id
     * @param redAmount
     * @return
     */
    int doRebate(Long id, BigDecimal redAmount);

    /**
     * 根据会员id和业务查询
     *
     * @param memberId
     * @param type
     * @return
     */
    List<RedPacketsItem> findByTypeAndMember(Long memberId, String type);

    /**
     * 根据业务id和类型查
     *
     * @param orderId
     * @param type
     * @return
     */
    RedPacketsItem findByTransactionAndType(Long orderId, String type);

}
