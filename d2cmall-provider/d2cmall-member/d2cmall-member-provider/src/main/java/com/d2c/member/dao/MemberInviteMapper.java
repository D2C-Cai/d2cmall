package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberInvite;
import com.d2c.member.query.MemberInviteSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberInviteMapper extends SuperMapper<MemberInvite> {

    List<MemberInvite> findBySearcher(@Param("searcher") MemberInviteSearcher searcher, @Param("page") PageModel page);

    int countBySearcher(@Param("searcher") MemberInviteSearcher searcher);

    int doRefuse(@Param("id") Long id, @Param("refuseReason") String refuseReason,
                 @Param("lastModifyMan") String lastModifyMan);

    int doAgree(@Param("id") Long id, @Param("lastModifyMan") String lastModifyMan);

}
