package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.CollectionCard;
import com.d2c.member.query.CollectionCardSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionCardMapper extends SuperMapper<CollectionCard> {

    Integer countBySearcher(@Param("searcher") CollectionCardSearcher searcher);

    List<CollectionCard> findBySearcher(@Param("searcher") CollectionCardSearcher searcher,
                                        @Param("pager") PageModel page);

    List<CollectionCard> findByMemberId(@Param("memberId") Long memberId, @Param("promotionId") Long promotionId);

}
