package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MagazineIssue;
import com.d2c.member.query.MagazineIssueSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MagazineIssueMapper extends SuperMapper<MagazineIssue> {

    MagazineIssue findByCode(@Param("code") String code);

    List<MagazineIssue> findBySearcher(@Param("searcher") MagazineIssueSearcher searcher,
                                       @Param("pager") PageModel pager);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("name") String name);

    int countBySearcher(@Param("searcher") MagazineIssueSearcher searcher);

    int doBindPartner(@Param("id") Long id, @Param("partnerId") Long partnerId,
                      @Param("partnerCode") String partnerCode);

    int doBindPartnerTrader(@Param("code") String code, @Param("partnerTraderId") Long partnerTraderId,
                            @Param("partnerTraderCode") String partnerTraderCode);

}
