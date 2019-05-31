package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.dao.MagazineIssueMapper;
import com.d2c.member.model.Magazine;
import com.d2c.member.model.MagazineIssue;
import com.d2c.member.query.MagazineIssueSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("magazineIssueService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MagazineIssueServiceImpl extends ListServiceImpl<MagazineIssue> implements MagazineIssueService {

    @Autowired
    private MagazineIssueMapper magazineIssueMapper;
    @Autowired
    private MagazineService magazineService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MagazineIssue insert(MagazineIssue magazineIssue) {
        return this.save(magazineIssue);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCreate(Long magazineId, Integer quantity) {
        Magazine magazine = magazineService.findById(magazineId);
        if (quantity > 1000) {
            throw new BusinessException("一次性最多生成1000条！");
        }
        for (int i = 0; i < quantity; i++) {
            MagazineIssue magazineIssue = new MagazineIssue(magazine);
            this.save(magazineIssue);
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(MagazineIssue magazineIssue) {
        return this.updateNotNull(magazineIssue);
    }

    @Override
    public MagazineIssue findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public MagazineIssue findByCode(String code) {
        return magazineIssueMapper.findByCode(code);
    }

    @Override
    public PageResult<MagazineIssue> findBySearcher(MagazineIssueSearcher searcher, PageModel page) {
        PageResult<MagazineIssue> pager = new PageResult<>(page);
        int totalCount = magazineIssueMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<MagazineIssue> list = magazineIssueMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(MagazineIssueSearcher searcher) {
        return magazineIssueMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String operator) {
        return magazineIssueMapper.updateStatus(id, status, operator);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindPartner(Long id, Long partnerId, String partnerCode) {
        return magazineIssueMapper.doBindPartner(id, partnerId, partnerCode);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindPartnerTrader(String code, Long partnerTraderId, String partnerTraderCode) {
        return magazineIssueMapper.doBindPartnerTrader(code, partnerTraderId, partnerTraderCode);
    }

}
