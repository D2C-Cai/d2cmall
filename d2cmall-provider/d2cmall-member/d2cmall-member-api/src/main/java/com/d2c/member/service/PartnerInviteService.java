package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.PartnerInvite;
import com.d2c.member.query.PartnerInviteSearcher;

public interface PartnerInviteService {

    /**
     * 插入一条记录
     *
     * @param partnerInvite
     * @return
     */
    PartnerInvite insert(PartnerInvite partnerInvite);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PartnerInvite> findBySearcher(PartnerInviteSearcher searcher, PageModel page);

    /**
     * 按条件统计总数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(PartnerInviteSearcher searcher);

    /**
     * 给予奖励
     *
     * @param id
     * @return
     */
    int doAward(Long id);

    /**
     * 刷新邀请时间
     *
     * @param toPartnerId
     * @return
     */
    int doRefresh(Long toPartnerId);

    /**
     * 取消奖励
     *
     * @param fromPartnerId
     * @return
     */
    int cancelFromAward(Long fromPartnerId);

    /**
     * 取消奖励
     *
     * @param toPartnerId
     * @return
     */
    int cancelToAward(Long toPartnerId);

}
