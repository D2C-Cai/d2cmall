package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.ShareRedPacketsPromotion;
import com.d2c.order.query.ShareRedPacketsPromotionSearcher;

public interface ShareRedPacketsPromotionService {

    ShareRedPacketsPromotion insert(ShareRedPacketsPromotion shareRedPacketsPromotion);

    Integer countBySearcher(ShareRedPacketsPromotionSearcher searcher);

    PageResult<ShareRedPacketsPromotion> findBySearcher(ShareRedPacketsPromotionSearcher searcher, PageModel page);

    int update(ShareRedPacketsPromotion shareRedPacketsPromotion);

    ShareRedPacketsPromotion findNowPromotion();

    int updateStatus(Long id, Integer status);

}
