package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Distributor;
import com.d2c.member.query.DistributorSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DistributorMapper extends SuperMapper<Distributor> {

    Distributor findByMemberInfoId(@Param("memberId") Long memberId);

    List<Distributor> findBySearch(@Param("searcher") DistributorSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") DistributorSearcher searcher);

    int updateStatusById(@Param("id") Long id, @Param("status") int status);

    int updateGroupIdById(@Param("id") Long id, @Param("groupId") Long groupId);

    int updateFreeShipFeeById(@Param("id") Long id, @Param("status") int status);

    int updateReship(@Param("id") Long id, @Param("status") int status);

}
