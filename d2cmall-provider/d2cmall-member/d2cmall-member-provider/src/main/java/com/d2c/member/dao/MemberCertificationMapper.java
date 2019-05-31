package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberCertification;
import com.d2c.member.query.MemberCertificationSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberCertificationMapper extends SuperMapper<MemberCertification> {

    MemberCertification findDefaultOne(@Param("memberId") Long memberId);

    MemberCertification findByMemberIdAndCard(@Param("memberId") Long memberId,
                                              @Param("identityCard") String identityCard);

    List<MemberCertification> findBySearcher(@Param("searcher") MemberCertificationSearcher searcher,
                                             @Param("page") PageModel page);

    int countBySearcher(@Param("searcher") MemberCertificationSearcher searcher);

    int clearDefault(@Param("id") Long id, @Param("memberId") Long memberId);

    int doDefault(@Param("id") Long id);

    MemberCertification findByName(@Param("name") String name, @Param("memberId") Long memberId);

}
