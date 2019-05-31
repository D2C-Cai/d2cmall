package com.d2c.order.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ComplaintTrackMapper;
import com.d2c.order.model.ComplaintTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("complaintTrackService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ComplaintTrackServiceImpl extends ListServiceImpl<ComplaintTrack> implements ComplaintTrackService {

    @Autowired
    private ComplaintTrackMapper complaintTrackMapper;

    public List<ComplaintTrack> findTrackByComplaintId(Long complaintId) {
        return complaintTrackMapper.findTrackByComplaintId(String.valueOf(complaintId));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public ComplaintTrack insert(ComplaintTrack complaintTrack) {
        return this.save(complaintTrack);
    }

}
