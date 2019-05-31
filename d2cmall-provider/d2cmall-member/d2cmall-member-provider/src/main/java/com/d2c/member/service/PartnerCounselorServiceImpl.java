package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.PartnerCounselorMapper;
import com.d2c.member.model.PartnerCounselor;
import com.d2c.member.query.PartnerCounselorSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("partnerCounselorService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerCounselorServiceImpl extends ListServiceImpl<PartnerCounselor> implements PartnerCounselorService {

    @Autowired
    private PartnerCounselorMapper partnerCounselorMapper;

    @Override
    public PartnerCounselor findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<PartnerCounselor> findBySearcher(PageModel page, PartnerCounselorSearcher searcher) {
        PageResult<PartnerCounselor> pager = new PageResult<>(page);
        Integer totalCount = partnerCounselorMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<PartnerCounselor> list = partnerCounselorMapper.findBySearcher(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countBySearcher(PartnerCounselorSearcher searcher) {
        return partnerCounselorMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PartnerCounselor insert(PartnerCounselor partnerCounselor) {
        return this.save(partnerCounselor);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(PartnerCounselor partnerCounselor) {
        return this.updateNotNull(partnerCounselor);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doMark(Long id, Integer mark) {
        return partnerCounselorMapper.doMark(id, mark);
    }

}
