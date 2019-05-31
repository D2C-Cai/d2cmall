package com.d2c.order.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CouponGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CouponGroupMapper extends SuperMapper<CouponGroup> {

    int countByMemberIdAndGroupId(@Param("memberId") Long memberId, @Param("groupId") Long groupId);

    List<Map<String, Object>> sumByDefGroupId(@Param("groupId") Long defGroupId);

    int countByLoginCodeAndGroupId(@Param("loginCode") String loginCode, @Param("groupId") Long id);

}