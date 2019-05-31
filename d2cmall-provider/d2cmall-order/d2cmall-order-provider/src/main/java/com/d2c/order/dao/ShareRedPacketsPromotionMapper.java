package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.ShareRedPacketsPromotion;
import com.d2c.order.query.ShareRedPacketsPromotionSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShareRedPacketsPromotionMapper extends SuperMapper<ShareRedPacketsPromotion> {

    Integer countBySearcher(@Param("searcher") ShareRedPacketsPromotionSearcher searcher);

    List<ShareRedPacketsPromotion> findBySearcher(@Param("searcher") ShareRedPacketsPromotionSearcher searcher,
                                                  @Param("pager") PageModel page);

    ShareRedPacketsPromotion findNowPromotion();

}
