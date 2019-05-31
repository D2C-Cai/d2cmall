package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.PartnerInvite;
import com.d2c.member.query.PartnerInviteSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerInviteMapper extends SuperMapper<PartnerInvite> {

    List<PartnerInvite> findBySearcher(@Param("searcher") PartnerInviteSearcher searcher,
                                       @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") PartnerInviteSearcher searcher);

    int doAward(@Param("id") Long id);

    int doRefresh(@Param("toPartnerId") Long toPartnerId);

    int cancelFromAward(@Param("fromPartnerId") Long fromPartnerId);

    int cancelToAward(@Param("toPartnerId") Long toPartnerId);

}
