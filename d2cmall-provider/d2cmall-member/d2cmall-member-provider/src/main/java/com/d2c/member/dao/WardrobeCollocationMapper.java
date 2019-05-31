package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.WardrobeCollocation;
import com.d2c.member.query.WardrobeCollocationSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WardrobeCollocationMapper extends SuperMapper<WardrobeCollocation> {

    int doVerify(@Param("id") Long id, @Param("verifyMan") String verifyMan);

    int doCancelVerify(@Param("id") Long id, @Param("lastModifyMan") String lastModifyMan);

    Integer countBySearcher(@Param("searcher") WardrobeCollocationSearcher query);

    List<WardrobeCollocation> findBySearcher(@Param("searcher") WardrobeCollocationSearcher query,
                                             @Param("pager") PageModel page);

    int doRefuse(@Param("id") Long id, @Param("lastModifyMan") String lastModifyMan);

    Integer countMine(@Param("memberId") Long memberId, @Param("searcher") WardrobeCollocationSearcher query);

    List<WardrobeCollocation> findMine(@Param("memberId") Long memberId,
                                       @Param("searcher") WardrobeCollocationSearcher query, @Param("pager") PageModel page);

    int deleteByMemberId(@Param("ids") Long[] id, @Param("memberId") Long memberId);

    int delete(@Param("id") Long id);

}
