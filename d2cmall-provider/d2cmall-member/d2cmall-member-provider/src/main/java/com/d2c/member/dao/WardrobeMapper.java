package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Wardrobe;
import com.d2c.member.query.WardrobeSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WardrobeMapper extends SuperMapper<Wardrobe> {

    Integer countBySearcher(@Param("searcher") WardrobeSearcher query);

    List<Wardrobe> findBySearcher(@Param("searcher") WardrobeSearcher query, @Param("pager") PageModel page);

    int delete(Long id);

    int deleteByMemberId(@Param("ids") Long[] ids, @Param("memberId") Long memberId);

}
