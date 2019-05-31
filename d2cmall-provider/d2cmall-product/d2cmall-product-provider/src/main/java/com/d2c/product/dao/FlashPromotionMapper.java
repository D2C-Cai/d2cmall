package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.query.FlashPromotionSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FlashPromotionMapper extends SuperMapper<FlashPromotion> {

    int deleteById(@Param("id") Long id, @Param("operator") String operator);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("operator") String operator);

    int countBySearcher(@Param("searcher") FlashPromotionSearcher searcher);

    List<FlashPromotion> findBySearcher(@Param("searcher") FlashPromotionSearcher searcher,
                                        @Param("page") PageModel page);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

    int updatePromotion(@Param("flashPromotion") FlashPromotion flashPromotion);

    List<FlashPromotion> findSession(@Param("promotionScope") Integer promotionScope,
                                     @Param("startDate") Date beginDate, @Param("endDate") Date endDate, @Param("channel") String channel);

    List<FlashPromotion> findBySessionAndDate(@Param("session") String session, @Param("startDate") Date startDate,
                                              @Param("promotionScope") Integer promotionScope, @Param("channel") String channel);

    FlashPromotion findByStartDateAndScope(@Param("startDate") Date startDate,
                                           @Param("promotionScope") Integer promotionScope, @Param("channel") String channel);

    List<FlashPromotion> findNoEndOrderByScore(@Param("channel") String channel);

}
