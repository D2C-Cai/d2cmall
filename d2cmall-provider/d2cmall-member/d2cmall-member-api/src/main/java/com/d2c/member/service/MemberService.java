package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Member;
import com.d2c.member.query.MemberSearcher;

import java.util.List;
import java.util.Map;

public interface MemberService {

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    Member findById(Long id);

    /**
     * 根据unionId查询用户
     *
     * @param unionId
     * @return
     */
    Member findByUnionId(String unionId);

    /**
     * 根据unionId查询用户
     *
     * @param source
     * @param unionId
     * @return
     */
    Member findByUnionIdAndSource(String source, String unionId);

    /**
     * 根据loginCode查询用户
     *
     * @param source
     * @param loginCode
     * @return
     */
    Member findByLoginCodeAndSource(String source, String loginCode);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Member> findBySearch(MemberSearcher searcher, PageModel page);

    /**
     * 用户数量
     *
     * @param searcher
     * @return
     */
    int countBySeach(MemberSearcher searcher);

    /**
     * 根据memberInfoId查询
     *
     * @param memberInfoId
     * @return
     */
    List<Member> findByMemberInfoId(Long memberInfoId);

    /**
     * 查询买手服务的openID
     *
     * @param memberInfoId
     * @return
     */
    Member findPartnerOpenId(Long memberInfoId);

    /**
     * 统计数量
     *
     * @param searcher
     * @return
     */
    List<Map<String, Object>> findCountGroupBySource(MemberSearcher searcher);

    /**
     * 查询D2C账户下的微信号
     *
     * @param memberInfoId
     * @return
     */
    Member findWeixinMember(Long memberInfoId);

    /**
     * 添加用户
     *
     * @param member
     * @return
     */
    Member insert(Member member);

    /**
     * 更新登录信息
     *
     * @param openId
     * @param unionId
     * @param source
     * @param nickname
     * @param headPic
     * @param sex
     * @param token
     * @param gzOpenId
     * @param partnerOpenId
     * @return
     */
    int updateLogin(String openId, String unionId, String source, String nickname, String headPic, String sex,
                    String token, String gzOpenId, String partnerOpenId);

    /**
     * 更新游客信息
     *
     * @param unionId
     * @param nickname
     * @param headPic
     * @param sex
     * @return
     */
    int updateInfo(String unionId, String nickname, String headPic, String sex);

    /**
     * 更新票据
     *
     * @param unionId
     * @param token
     * @return
     */
    int updateToken(String unionId, String token);

    /**
     * 根据票据查询
     *
     * @param token
     * @return
     */
    Member findByToken(String token);

    /**
     * 游客绑定会员
     *
     * @param unionId
     * @param memberInfoId
     * @param loginCode
     * @param nickname
     * @param headPic
     * @return
     */
    int doBindMemberInfo(String unionId, Long memberInfoId, String loginCode, String nickname, String headPic);

    /**
     * 会员解绑游客
     *
     * @param unionId
     * @return
     */
    int doCancelBind(String unionId);

    /**
     * 修改绑定手机
     *
     * @param memberInfoId
     * @param newMobile
     * @return
     */
    int doChangeMobile(Long memberInfoId, String newMobile);

    /**
     * 注销账户
     *
     * @param loginCode
     * @return
     */
    int doClean(String loginCode);

}
