package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.RequisitionError;
import com.d2c.order.query.RequisitionErrorSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RequisitionErrorMapper extends SuperMapper<RequisitionError> {

    int doProcess(@Param("id") Long id, @Param("operator") String operator);

    List<RequisitionError> findBySearcher(@Param("searcher") RequisitionErrorSearcher searcher,
                                          @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") RequisitionErrorSearcher searcher);

}
