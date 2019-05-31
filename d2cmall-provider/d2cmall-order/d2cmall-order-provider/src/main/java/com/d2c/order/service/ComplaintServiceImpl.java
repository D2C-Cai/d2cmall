package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ComplaintMapper;
import com.d2c.order.dto.ComplaintDto;
import com.d2c.order.model.Complaint;
import com.d2c.order.model.ComplaintTrack;
import com.d2c.order.query.ComplaintSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("complaintService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ComplaintServiceImpl extends ListServiceImpl<Complaint> implements ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;
    @Autowired
    private ComplaintTrackService complaintTrackService;

    public Complaint findById(String complaintId) {
        return complaintMapper.findComplaintById(complaintId);
    }

    public Complaint findByTypeAndTransactionSn(String type, String transactionSn) {
        return complaintMapper.findByTypeAndTransactionSn(type, transactionSn);
    }

    public PageResult<ComplaintDto> findPageBySearch(ComplaintSearcher searcher, PageModel page) {
        PageResult<ComplaintDto> pager = new PageResult<ComplaintDto>(page);
        List<Complaint> complaints = new ArrayList<Complaint>();
        List<ComplaintDto> complaintDto = new ArrayList<ComplaintDto>();
        int totalCount = complaintMapper.countBySearch(searcher);
        if (totalCount > 0) {
            complaints = complaintMapper.findBySearch(searcher, page);
            for (Complaint complaint : complaints) {
                ComplaintDto dto = new ComplaintDto();
                BeanUtils.copyProperties(complaint, dto);
                List<ComplaintTrack> complaintTracks = complaintTrackService.findTrackByComplaintId(dto.getId());
                dto.setComplaintTracks(complaintTracks);
                complaintDto.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(complaintDto);
        return pager;
    }

    public List<ComplaintDto> findBySearch(ComplaintSearcher searcher, PageModel page) {
        List<ComplaintDto> complaintDto = new ArrayList<ComplaintDto>();
        List<Complaint> complaints = complaintMapper.findBySearch(searcher, page);
        for (Complaint complaint : complaints) {
            ComplaintDto dto = new ComplaintDto();
            BeanUtils.copyProperties(complaint, dto);
            List<ComplaintTrack> complaintTracks = complaintTrackService.findTrackByComplaintId(dto.getId());
            dto.setComplaintTracks(complaintTracks);
            complaintDto.add(dto);
        }
        return complaintDto;
    }

    @Override
    public int countBySearch(ComplaintSearcher searcher) {
        return complaintMapper.countBySearch(searcher);
    }

    public Map<String, Object> countComplaintMaps() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> counts = complaintMapper.countGroupByStatus();
        for (Map<String, Object> count : counts) {
            String status = count.get("status").toString();
            switch (status) {
                case "INIT":
                    map.put("initCount", count.get("counts"));
                    break;
                case "PROCESS":
                    map.put("proCount", count.get("counts"));
                    break;
                case "REPROCESS":
                    map.put("reCount", count.get("counts"));
                    break;
            }
        }
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Complaint insert(Complaint complaint) {
        return save(complaint);
    }

    @Override
    public int update(Complaint complaint) {
        return this.updateNotNull(complaint);
    }

}