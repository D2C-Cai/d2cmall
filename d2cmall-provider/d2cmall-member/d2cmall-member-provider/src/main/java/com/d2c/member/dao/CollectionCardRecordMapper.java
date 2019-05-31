package com.d2c.member.dao;

import com.d2c.member.model.CollectionCardRecord;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionCardRecordMapper extends SuperMapper<CollectionCardRecord> {

    List<CollectionCardRecord> findByMemberId(@Param("memberId") Long memberId, @Param("promotionId") Long promotionId);

    List<CollectionCardRecord> findStageRecord(@Param("memberId") Long memberId,
                                               @Param("promotionId") Long promotionId);

    List<CollectionCardRecord> findRecently(Integer size);

}
