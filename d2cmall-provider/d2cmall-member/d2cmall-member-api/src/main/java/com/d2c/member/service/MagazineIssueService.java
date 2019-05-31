package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MagazineIssue;
import com.d2c.member.query.MagazineIssueSearcher;

public interface MagazineIssueService {

    /**
     * 插入一条记录
     *
     * @param magazineIssue
     * @return
     */
    MagazineIssue insert(MagazineIssue magazineIssue);

    /**
     * 生成杂志发行册
     *
     * @param magazineId
     * @param quantity
     * @return
     */
    int doCreate(Long magazineId, Integer quantity);

    /**
     * 更新一条记录
     *
     * @param magazineIssue
     * @return
     */
    int update(MagazineIssue magazineIssue);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MagazineIssue findById(Long id);

    /**
     * 根据id查询
     *
     * @param code
     * @return
     */
    MagazineIssue findByCode(String code);

    /**
     * 分页查询
     *
     * @param searcher
     * @return
     */
    PageResult<MagazineIssue> findBySearcher(MagazineIssueSearcher searcher, PageModel page);

    /**
     * 按条件统计总数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(MagazineIssueSearcher searcher);

    /**
     * 发行册子
     *
     * @param quiz
     * @return
     */
    int updateStatus(Long id, Integer status, String operator);

    /**
     * 绑定分销商
     *
     * @param id
     * @param partnerId
     * @param partnerCode 分销商账号
     * @return
     */
    int doBindPartner(Long id, Long partnerId, String partnerCode);

    /**
     * 绑定二级分销
     *
     * @param code
     * @param partnerTraderId
     * @param partnerTraderCode 二级分销账号
     * @return
     */
    int doBindPartnerTrader(String code, Long partnerTraderId, String partnerTraderCode);

}
