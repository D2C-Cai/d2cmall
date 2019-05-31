package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.service.BaseService;
import com.d2c.member.model.MemberCertification;
import com.d2c.member.query.MemberCertificationSearcher;

public interface MemberCertificationService extends BaseService<MemberCertification> {

    /**
     * 查询默认实名信息
     *
     * @param memberId
     * @return
     */
    MemberCertification findDefaultOne(Long memberId);

    /**
     * 根据会员id和身份证查询
     *
     * @param memberId
     * @param identityCard
     * @return
     */
    MemberCertification findByMemberIdAndCard(Long memberId, String identityCard);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<MemberCertification> findBySearcher(MemberCertificationSearcher searcher, PageModel page);

    /**
     * 按条件统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(MemberCertificationSearcher searcher);

    /**
     * 设置默认
     *
     * @param id
     * @return
     */
    int doDefault(Long id, Long memberId);

    /**
     * 删除实名认证
     *
     * @return
     */
    int doDelete(Long id);

    /**
     * 验证
     *
     * @param certification
     * @return
     */
    int doCertificate(MemberCertification certification);

    /**
     * 更新
     *
     * @param certification
     * @return
     */
    int update(MemberCertification certification);

    /**
     * 认证
     *
     * @param certification
     * @return
     */
    MemberCertification doInsert(MemberCertification certification);

    /**
     * 根据收货人姓名查找认证
     *
     * @param reciver
     * @param id
     * @return
     */
    MemberCertification findByName(String reciver, Long memberId);

}
