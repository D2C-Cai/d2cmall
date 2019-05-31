package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.RequisitionLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RequisitionLogMapper extends SuperMapper<RequisitionLog> {

    int countByRequisitionId(@Param("requisitionId") Long requisitionId);

    int countByRequisitionItemId(@Param("requisitionItemId") Long requisitionItemId,
                                 @Param("requisitionId") Long requisitionId);

    List<RequisitionLog> findByRequisitionItemId(@Param("requisitionItemId") Long requisitionItemId,
                                                 @Param("requisitionId") Long requisitionId, @Param("pager") PageModel page);

    List<RequisitionLog> findByRequisitionId(@Param("requisitionId") Long requisitionId,
                                             @Param("pager") PageModel page);

    int updateSnByItemIds(@Param("ids") List<Long> ids, @Param("requisitionSn") String requisitionSn);

}
