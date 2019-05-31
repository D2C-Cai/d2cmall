package com.d2c.order.mongo.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.mongo.model.BargainHelpDO;

import java.util.Date;

public interface BargainHelpService {

    /**
     * 统计unionId在规定时间内绑砍的次数
     *
     * @param unionId
     * @param begainDate
     * @param endDate
     * @return
     */
    int countByUnionIdAndDate(String unionId, Date begainDate, Date endDate);

    /**
     * 统计unionId对应bargainId的次数限制
     *
     * @param unionId
     * @param bargainId
     * @return
     */
    int countByUnionIdAndBarginId(String unionId, String bargainId);

    /**
     * 查询砍价明细
     *
     * @param bargainId
     * @param page
     * @return
     */
    PageResult<BargainHelpDO> findByBargainId(String bargainId, PageModel page);

}
