package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.AwardProduct;
import com.d2c.product.query.AwardProductSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AwardProductMapper extends SuperMapper<AwardProduct> {

    List<AwardProduct> findBySearcher(@Param("searcher") AwardProductSearcher searcher, @Param("pager") PageModel page);

    int countBySearcher(@Param("searcher") AwardProductSearcher searcher);

    int updateCouponToDecrease(@Param("id") Long id);

    int updateStatus(@Param("id") Long id, @Param("enable") Integer enable);

    AwardProduct findByMaxWeight(Long sessionId);

    List<AwardProduct> findBySessionIdAndNow(Long sessionId);

}
