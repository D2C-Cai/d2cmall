package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.AwardRecord;
import com.d2c.order.query.AwardRecordSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AwardRecordMapper extends SuperMapper<AwardRecord> {

    List<AwardRecord> findListByRecently(Long sessionId);

    List<AwardRecord> findBySearcher(@Param("searcher") AwardRecordSearcher searcher, @Param("pager") PageModel page);

    int countBySearcher(@Param("searcher") AwardRecordSearcher searcher);

    List<AwardRecord> findByMemberIdAndSessionId(@Param("memberId") Long memberId, @Param("sessionId") Long sessionId);

    List<String> findAwardLevelName();

}
