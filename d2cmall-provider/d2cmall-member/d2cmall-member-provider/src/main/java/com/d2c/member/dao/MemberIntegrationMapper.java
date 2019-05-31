package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.query.MemberIntegrationSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberIntegrationMapper extends SuperMapper<MemberIntegration> {

    List<MemberIntegration> findBySearch(@Param("searcher") MemberIntegrationSearcher searcher,
                                         @Param("page") PageModel page);

    int countBySearch(@Param("searcher") MemberIntegrationSearcher searcher);

    int countByExchange(@Param("memberId") Long memberId, @Param("productId") Long productId);

}
