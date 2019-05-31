package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.LiveRoom;
import com.d2c.member.query.LiveRoomSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LiveRoomMapper extends SuperMapper<LiveRoom> {

    LiveRoom findByMemberId(@Param("memberId") Long memberId);

    List<LiveRoom> findBySearcher(@Param("searcher") LiveRoomSearcher searcher, @Param("page") PageModel page);

    int countBySearcher(@Param("searcher") LiveRoomSearcher searcher);

    int doDeleteLiveRoom(@Param("id") Long id);

    int doBindCoupons(@Param("id") Long id, @Param("couponId") Long couponId, @Param("operator") String operator,
                      @Param("couponGroupId") Long couponGroupId);

}
