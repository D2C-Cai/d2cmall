package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.RequisitionError;
import com.d2c.order.query.RequisitionErrorSearcher;

public interface RequisitionErrorService {

    /**
     * 新增
     *
     * @param requisitionError
     * @return
     */
    RequisitionError insert(RequisitionError requisitionError);

    /**
     * 日志
     *
     * @param id
     * @param jsonStr
     * @return
     */
    int doLog(Long id, String operation, String operator);

    /**
     * 处理
     *
     * @return
     */
    int doProcess(Long id, String operator);

    /**
     * 查询
     *
     * @param page
     * @return
     */
    PageResult<RequisitionError> findBySearcher(RequisitionErrorSearcher searcher, PageModel page);

    Integer countBySearcher(RequisitionErrorSearcher searcher);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    RequisitionError findById(Long id);

    /**
     * 修改情况说明
     *
     * @param id
     * @param remark
     * @param operator
     * @return
     */
    int doRemark(Long id, String remark, String operator);

}
