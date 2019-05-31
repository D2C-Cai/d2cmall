package com.d2c.behavior.services;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.core.model.KeyValue;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

public interface EventQueryService {

    /**
     * 查询用户浏览商品ID列表
     */
    public List<Long> findVisitProductIds(Long memberId, Integer limit);

    /**
     * 我的足迹 - 用户浏览历史记录
     */
    public PageResult<JSONObject> findProductVisit(Long memberId, PageModel page);

    public Integer countProductEvent(Long memberId);

    /**
     * 各店铺访问用户数量
     * <p>查询时间段内
     */
    public List<KeyValue> countVistiors(Date startTime);

}
