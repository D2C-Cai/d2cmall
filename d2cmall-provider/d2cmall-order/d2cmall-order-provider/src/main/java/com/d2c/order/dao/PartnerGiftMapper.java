package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.PartnerGift;
import com.d2c.order.query.PartnerGiftSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerGiftMapper extends SuperMapper<PartnerGift> {

    List<PartnerGift> findBySearcher(@Param("searcher") PartnerGiftSearcher searcher, @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") PartnerGiftSearcher searcher);

}
