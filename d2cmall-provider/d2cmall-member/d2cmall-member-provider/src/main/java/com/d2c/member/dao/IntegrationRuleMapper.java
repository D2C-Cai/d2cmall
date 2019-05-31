package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.IntegrationRule;
import com.d2c.member.query.IntegrationRuleSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntegrationRuleMapper extends SuperMapper<IntegrationRule> {

    List<IntegrationRule> findBySearch(@Param("searcher") IntegrationRuleSearcher searcher,
                                       @Param("page") PageModel page);

    int countBySearch(@Param("searcher") IntegrationRuleSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    IntegrationRule findVaildByType(@Param("type") String type);

}
