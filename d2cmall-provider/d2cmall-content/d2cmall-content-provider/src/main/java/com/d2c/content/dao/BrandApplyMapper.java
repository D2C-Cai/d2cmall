package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.BrandApply;
import com.d2c.content.query.BrandApplySearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandApplyMapper extends SuperMapper<BrandApply> {

    BrandApply findByMemberId(@Param("memberId") Long memberId);

    List<BrandApply> findBySearcher(@Param("searcher") BrandApplySearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") BrandApplySearcher searcher);

}
