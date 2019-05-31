package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.SalesProperty;
import com.d2c.product.model.SalesProperty.SalesPropertyType;
import com.d2c.product.query.SalesPropertySearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SalesPropertyMapper extends SuperMapper<SalesProperty> {

    Map<String, Object> findByUnique(@Param("groupId") Long groupId, @Param("type") SalesPropertyType type,
                                     @Param("code") String code);

    List<SalesProperty> findBySearch(@Param("searcher") SalesPropertySearcher searcher,
                                     @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") SalesPropertySearcher searcher);

    List<SalesProperty> findColors();

    List<SalesProperty> findSizes();

}
