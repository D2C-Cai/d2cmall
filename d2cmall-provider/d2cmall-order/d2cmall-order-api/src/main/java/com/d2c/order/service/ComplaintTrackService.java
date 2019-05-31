package com.d2c.order.service;

import com.d2c.order.model.ComplaintTrack;

import java.util.List;

public interface ComplaintTrackService {

    /**
     * 根据complaintId（投诉单id）从crm_complaintTrack中获取所有相关的处理信息，以列表形式返回。
     *
     * @param complaintId
     * @return
     */
    List<ComplaintTrack> findTrackByComplaintId(Long complaintId);

    /**
     * 以ComplaintTrack对象作为参数，将投诉单追踪单信息插入crm_complaintTrack表中。
     *
     * @param complaintTrack
     * @return ComplaintTrack
     */
    ComplaintTrack insert(ComplaintTrack complaintTrack);

}
