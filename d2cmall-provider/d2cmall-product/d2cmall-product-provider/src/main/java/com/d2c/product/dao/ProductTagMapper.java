package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductTag;
import com.d2c.product.query.ProductTagSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProductTagMapper extends SuperMapper<ProductTag> {

    List<ProductTag> findByProductId(Long productId);

    int countBySearch(@Param("searcher") ProductTagSearcher searcher);

    List<ProductTag> findBySearch(@Param("searcher") ProductTagSearcher searcher, @Param("pager") PageModel page);

    int delete(Long id);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int countSynProductTags(@Param("lastSysDate") Date lastSysDate);

    List<ProductTag> findSynProductTags(@Param("lastSysDate") Date lastSysDate, @Param("pager") PageModel page);

    List<ProductTag> findByType(String type);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

    List<ProductTag> findByIds(@Param("ids") Long[] ids);

    ProductTag findOneFixByType(String type);

}
