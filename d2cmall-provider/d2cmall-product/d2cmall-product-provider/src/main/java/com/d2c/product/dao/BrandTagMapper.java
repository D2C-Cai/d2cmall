package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.BrandTag;
import com.d2c.product.query.BrandTagSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BrandTagMapper extends SuperMapper<BrandTag> {

    List<BrandTag> findByDesignerId(Long designerId);

    List<BrandTag> findBySearch(@Param("searcher") BrandTagSearcher searcher, @Param("pager") PageModel page);

    int countBySearch(@Param("searcher") BrandTagSearcher searcher);

    List<BrandTag> findSynDesignerTags(@Param("lastSysDate") Date lastSysDate, @Param("pager") PageModel page);

    int countSynDesignerTags(@Param("lastSysDate") Date lastSysDate);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    BrandTag findFixedOne();

}
