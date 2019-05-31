package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.PartnerItem;
import com.d2c.order.query.PartnerItemSearcher;

import java.util.List;
import java.util.Map;

public interface PartnerItemService {

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PartnerItem> findBySearcher(PartnerItemSearcher searcher, PageModel page);

    /**
     * 新增数据
     *
     * @param partnerLog
     * @return
     */
    PartnerItem insert(PartnerItem partnerLog);

    /**
     * 金额统计
     *
     * @param partnerId
     * @return
     */
    List<Map<String, Object>> findSummaryByType(Long partnerId);

}
