package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.PartnerItem;
import com.d2c.order.query.PartnerItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PartnerItemMapper extends SuperMapper<PartnerItem> {

    List<PartnerItem> findBySearcher(@Param("searcher") PartnerItemSearcher searcher, @Param("page") PageModel page);

    Integer countBySearcher(@Param("searcher") PartnerItemSearcher searcher);

    int updateStatusBySourceId(@Param("sourceId") Long sourceId, @Param("sourceType") String sourceType,
                               @Param("sourceStatus") Integer sourceStatus);

    List<Map<String, Object>> findSummaryByType(@Param("partnerId") Long partnerId);

}
