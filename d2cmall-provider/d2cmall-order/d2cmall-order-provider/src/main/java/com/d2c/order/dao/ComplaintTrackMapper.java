package com.d2c.order.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.ComplaintTrack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComplaintTrackMapper extends SuperMapper<ComplaintTrack> {

    List<ComplaintTrack> findTrackByComplaintId(@Param("complaintId") String complaintId);

}
