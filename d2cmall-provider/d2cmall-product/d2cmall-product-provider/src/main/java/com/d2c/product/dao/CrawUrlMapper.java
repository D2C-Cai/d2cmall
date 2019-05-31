package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.CrawUrl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrawUrlMapper extends SuperMapper<CrawUrl> {

    List<CrawUrl> findListByStatus(@Param("status") int status, @Param("pager") PageModel pageModel);

    int countByStatus(@Param("status") int status);

    int updateStatusByUrl(@Param("crawUrl") String crawUrl, @Param("status") int status);

    CrawUrl findByRootUrlAndPageNo(@Param("rootUrl") String rootUrl, @Param("pageNo") String pageNo);

    CrawUrl findByProductId(@Param("productId") String productId);

    void deleteByDesignerId(@Param("craw_designer_id") Long crawDesignerId);

}
