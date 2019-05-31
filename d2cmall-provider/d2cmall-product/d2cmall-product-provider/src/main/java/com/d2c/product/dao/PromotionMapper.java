package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Promotion;
import com.d2c.product.query.PromotionSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PromotionMapper extends SuperMapper<Promotion> {

    Promotion findSimpleById(@Param("id") Long id);

    int doMark(@Param("enable") boolean enable, @Param("id") Long id);

    List<Promotion> findBySearcher(@Param("searcher") PromotionSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") PromotionSearcher searcher);

    int doTiming(@Param("id") Long id, @Param("timing") int timing);

    List<Promotion> findByTagId(@Param("tagId") Long tagId, @Param("enable") Boolean enable,
                                @Param("pager") PageModel pager);

    int countByTagId(@Param("tagId") Long tagId, @Param("enable") Boolean enable);

}
