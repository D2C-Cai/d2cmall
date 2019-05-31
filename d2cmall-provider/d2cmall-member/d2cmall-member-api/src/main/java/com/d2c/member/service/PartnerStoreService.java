package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.PartnerStore;
import com.d2c.member.query.PartnerStoreSearcher;

public interface PartnerStoreService {

    /**
     * 插入一条记录
     *
     * @param partnerStore
     * @return
     */
    PartnerStore insert(PartnerStore partnerStore);

    /**
     * 更新一条记录
     *
     * @param partnerStore
     * @return
     */
    int update(PartnerStore partnerStore);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    PartnerStore findById(Long id);

    /**
     * 根据memberId查询
     *
     * @param memberId
     * @return
     */
    PartnerStore findByMemberId(Long memberId);

    /**
     * 根据partnerId查询
     *
     * @param partnerId
     * @return
     */
    PartnerStore findByPartnerId(Long partnerId);

    /**
     * 分页查询
     *
     * @param searcher
     * @return
     */
    PageResult<PartnerStore> findBySearcher(PartnerStoreSearcher searcher, PageModel page);

    /**
     * 按条件统计总数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(PartnerStoreSearcher searcher);

}
