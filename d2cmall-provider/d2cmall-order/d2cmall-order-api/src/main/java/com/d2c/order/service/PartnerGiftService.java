package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.PartnerGift;
import com.d2c.order.query.PartnerGiftSearcher;

public interface PartnerGiftService {

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    PartnerGift findById(Long id);

    /**
     * 新增数据
     *
     * @param partnerBill
     * @return
     */
    PartnerGift insert(PartnerGift partnerGift);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PartnerGift> findBySearcher(PartnerGiftSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearcher(PartnerGiftSearcher searcher);

}
