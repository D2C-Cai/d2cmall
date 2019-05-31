package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Complaint;
import com.d2c.order.query.ComplaintSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ComplaintMapper extends SuperMapper<Complaint> {

    Complaint findComplaintById(String complaintId);

    Complaint findByTypeAndTransactionSn(@Param("type") String type, @Param("transactionSn") String transactionSn);

    List<Complaint> findBySearch(@Param("searcher") ComplaintSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ComplaintSearcher searcher);

    List<Map<String, Object>> countGroupByStatus();

}
