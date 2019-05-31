package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.PartnerCounselor;
import com.d2c.member.query.PartnerCounselorSearcher;

public interface PartnerCounselorService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    PartnerCounselor findById(Long id);

    /**
     * 根据searcher查询
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<PartnerCounselor> findBySearcher(PageModel page, PartnerCounselorSearcher searcher);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(PartnerCounselorSearcher searcher);

    /**
     * 新增
     *
     * @param partnerCounselor
     * @return
     */
    PartnerCounselor insert(PartnerCounselor partnerCounselor);

    /**
     * 更新
     *
     * @param partnerCounselor
     * @return
     */
    int update(PartnerCounselor partnerCounselor);

    /**
     * 上下架
     *
     * @param id
     * @param mark
     * @return
     */
    int doMark(Long id, Integer mark);

}
