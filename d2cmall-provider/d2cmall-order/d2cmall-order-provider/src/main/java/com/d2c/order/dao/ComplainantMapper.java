package com.d2c.order.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Complainant;
import org.apache.ibatis.annotations.Param;

public interface ComplainantMapper extends SuperMapper<Complainant> {

    Complainant findByMemberId(@Param("memberId") Long memberId);

    int updateTimes(@Param("memberId") Long memberId, @Param("times") int times);

}
