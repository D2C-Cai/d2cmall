package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.RequisitionLog;

import java.util.List;

public interface RequisitionLogService {

    /**
     * 保存退款单日志
     *
     * @param entity
     * @return
     */
    RequisitionLog insert(RequisitionLog entity);

    /**
     * 根据id获取单条调拨单日志
     *
     * @param key
     * @return
     */
    RequisitionLog findById(Long key);

    /**
     * 调拨单操作日志
     *
     * @param requisitionId
     * @param page
     * @return
     */
    PageResult<RequisitionLog> findByRequisitionId(Long requisitionId, PageModel page);

    /**
     * 明细操作日志
     *
     * @param requisitionItemId
     * @param page
     * @return
     */
    PageResult<RequisitionLog> findByRequisitionItemId(Long requisitionItemId, Long requisitionId, PageModel page);

    /**
     * 提交调拨单后刷新调拨商品的requisitionSn
     *
     * @param ids
     * @param requisitionSn
     * @return
     */
    int updateSnByItemIds(List<Long> ids, String requisitionSn);

}
