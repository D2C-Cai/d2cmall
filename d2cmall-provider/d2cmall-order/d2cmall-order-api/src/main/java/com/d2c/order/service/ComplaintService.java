package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.ComplaintDto;
import com.d2c.order.model.Complaint;
import com.d2c.order.query.ComplaintSearcher;

import java.util.List;
import java.util.Map;

public interface ComplaintService {

    /**
     * 根据id查询
     *
     * @param complaintId
     * @return
     */
    Complaint findById(String complaintId);

    /**
     * 根据类型和单号查询
     *
     * @param type
     * @param transactionSn
     * @return
     */
    Complaint findByTypeAndTransactionSn(String type, String transactionSn);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ComplaintDto> findPageBySearch(ComplaintSearcher searcher, PageModel page);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<ComplaintDto> findBySearch(ComplaintSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(ComplaintSearcher searcher);

    /**
     * 以MAP形式返回各种类型的投诉单数量
     *
     * @return Map<status   ,   count>
     */
    Map<String, Object> countComplaintMaps();

    /**
     * 新增
     *
     * @param complaint
     * @return
     */
    Complaint insert(Complaint complaint);

    /**
     * 更新
     *
     * @param complaint
     * @return
     */
    int update(Complaint complaint);

}
