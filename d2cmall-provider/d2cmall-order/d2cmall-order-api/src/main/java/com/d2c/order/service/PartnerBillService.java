package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Partner;
import com.d2c.order.model.PartnerBill;
import com.d2c.order.query.PartnerBillSearcher;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PartnerBillService {

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    PartnerBill findById(Long id);

    /**
     * 新增数据
     *
     * @param partnerBill
     * @param partner
     * @return
     */
    PartnerBill insert(PartnerBill partnerBill, Partner partner);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PartnerBill> findBySearcher(PartnerBillSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearcher(PartnerBillSearcher searcher);

    /**
     * 通过订单明细查询
     *
     * @param orderItemId
     * @return
     */
    PartnerBill findByOrderItemId(Long orderItemId);

    /**
     * 更改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 关闭返利单
     *
     * @param orderItemId
     * @return
     */
    int doClose(Long orderItemId);

    /**
     * 分销今日业绩
     *
     * @param partnerId
     * @param beginDate
     * @param endDate
     * @return
     */
    Map<String, Object> findPartnerSummaryToday(Long partnerId, Date beginDate, Date endDate, Integer[] status);

    /**
     * 下级分销今日业绩
     *
     * @param partnerId
     * @param beginDate
     * @param endDate
     * @return
     */
    Map<String, Object> findChildrenSummaryToday(Long partnerId, Date beginDate, Date endDate, Integer[] status);

    /**
     * 根据状态分组<br/>
     * 直接订单（数量，返利金额，实付金额）<br/>
     * 团队订单（数量，返利金额，实付金额）<br/>
     * 间接团队订单（数量，返利金额，实付金额）<br/>
     * AM订单（数量，返利金额，实付金额）<br/>
     *
     * @return
     */
    List<Map<String, Object>> findBillSummary(Long id, String rid, String ratio);

    /**
     * 根据状态统计
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> findCountGroupByStatus(Date beginDate, Date endDate);

    /**
     * 根据等级统计
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> findCountGroupByLevel(Date beginDate, Date endDate);

}
