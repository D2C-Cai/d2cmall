package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.PiliLive;
import com.d2c.member.query.PiliLiveSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PiliLiveMapper extends SuperMapper<PiliLive> {

    List<PiliLive> findBySearcher(@Param("searcher") PiliLiveSearcher searcher, @Param("pager") PageModel page);

    int countBySearcher(@Param("searcher") PiliLiveSearcher searcher);

    int doClose(@Param("id") Long id, @Param("replayUrl") String replayUrl);

    PiliLive findLastOne(@Param("memberId") Long memberId);

    int doIn(@Param("id") Long id, @Param("vcount") Integer vcount);

    int deleteByMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    int deleteById(@Param("id") Long id);

    Integer countHot();

    List<PiliLive> findHot(@Param("pager") PageModel page);

    int updateMark(@Param("id") Long id, @Param("mark") Integer mark, @Param("modifyMan") String modifyMan);

    int updateTop(@Param("id") Long id, @Param("top") Integer top, @Param("modifyMan") String modifyMan);

    int updateVfans(@Param("id") Long id, @Param("vfans") Integer vfans, @Param("modifyMan") String modifyMan);

    int updateVrate(@Param("id") Long id, @Param("vrate") Integer vrate, @Param("modifyMan") String modifyMan);

    int updateRatio(@Param("id") Long id, @Param("ratio") Integer ratio, @Param("modifyMan") String modifyMan);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

}
