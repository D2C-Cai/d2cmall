package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Member;
import com.d2c.member.query.MemberSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberMapper extends SuperMapper<Member> {

    Member findByUnionId(@Param("unionId") String unionId);

    String findTokenByUnionId(@Param("unionId") String unionId);

    Member findByUnionIdAndSource(@Param("source") String source, @Param("unionId") String unionId);

    Member findByLoginCodeAndSource(@Param("source") String source, @Param("loginCode") String loginCode);

    List<Member> findBySeach(@Param("searcher") MemberSearcher searcher, @Param("pager") PageModel pager);

    int countBySeach(@Param("searcher") MemberSearcher searcher);

    List<Member> findByMemberInfoId(@Param("memberInfoId") Long memberInfoId);

    Member findPartnerOpenId(@Param("memberInfoId") Long memberInfoId);

    List<Map<String, Object>> findCountGroupBySource(@Param("searcher") MemberSearcher searcher);

    int updateLogin(@Param("openId") String openId, @Param("unionId") String unionId, @Param("source") String source,
                    @Param("nickname") String nickname, @Param("headPic") String headPic, @Param("sex") String sex,
                    @Param("token") String token, @Param("gzOpenId") String gzOpenId,
                    @Param("partnerOpenId") String partnerOpenId);

    int updateInfo(@Param("unionId") String unionId, @Param("nickname") String nickname,
                   @Param("headPic") String headPic, @Param("sex") String sex);

    int updateToken(@Param("unionId") String unionId, @Param("token") String token);

    Member findByToken(@Param("token") String token);

    int doBindMemberInfo(@Param("unionId") String unionId, @Param("memberInfoId") Long memberInfoId,
                         @Param("loginCode") String loginCode, @Param("nickname") String nickname, @Param("headPic") String headPic);

    int doCancelBind(@Param("unionId") String unionId);

    int doChangeMobile(@Param("memberInfoId") Long memberInfoId, @Param("newMobile") String newMobile);

    int doClean(@Param("loginCode") String loginCode);

}
