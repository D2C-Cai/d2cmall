package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.CollectionCardDef;
import com.d2c.member.query.CollectionCardDefSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionCardDefMapper extends SuperMapper<CollectionCardDef> {

    List<CollectionCardDef> findBySearcher(@Param("searcher") CollectionCardDefSearcher query,
                                           @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") CollectionCardDefSearcher query);

    List<CollectionCardDef> findOrderByWeight(Long promotionId);

    CollectionCardDef findMaxWeight(Long promotionId);

    int doReduce(Long id);

    List<CollectionCardDef> findByPromotionId(Long promotionId);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

}
