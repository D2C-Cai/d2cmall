package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.PartnerCashMapper;
import com.d2c.order.model.PartnerCash;
import com.d2c.order.query.PartnerCashSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("partnerCashService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerCashServiceImpl extends ListServiceImpl<PartnerCash> implements PartnerCashService {

    @Autowired
    private PartnerCashMapper partnerCashMapper;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PartnerCash insert(PartnerCash partnerCash) {
        return this.save(partnerCash);
    }

    @Override
    public PartnerCash findById(Long id) {
        return partnerCashMapper.selectByPrimaryKey(id);
    }

    @Override
    public PartnerCash findBySn(String sn) {
        return partnerCashMapper.findBySn(sn);
    }

    @Override
    public PartnerCash findActiveByMobile(String loginCode) {
        return partnerCashMapper.findActiveByMobile(loginCode);
    }

    @Override
    public BigDecimal findApplyCashByPartnerId(Long partnerId) {
        return partnerCashMapper.findApplyCashByPartnerId(partnerId);
    }

    @Override
    public BigDecimal findWithCashByDate(Long partnerId, Date startDate, Date endDate, Integer taxType) {
        return partnerCashMapper.findWithCashByDate(partnerId, startDate, endDate, taxType);
    }

    @Override
    public PageResult<PartnerCash> findBySearcher(PartnerCashSearcher searcher, PageModel page) {
        PageResult<PartnerCash> pager = new PageResult<PartnerCash>(page);
        Integer totalCount = partnerCashMapper.countBySearcher(searcher);
        List<PartnerCash> list = new ArrayList<PartnerCash>();
        if (totalCount > 0) {
            list = partnerCashMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public Integer countBySearcher(PartnerCashSearcher searcher) {
        return partnerCashMapper.countBySearcher(searcher);
    }

    @Override
    public List<Map<String, Object>> findCountGroupByStatus() {
        return partnerCashMapper.findCountGroupByStatus();
    }

    @Override
    public PartnerCash findLastSuccessOne(Long partnerId, Date startDate, Date endDate, Integer taxType) {
        return partnerCashMapper.findLastSuccessOne(partnerId, startDate, endDate, taxType);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doAgree(Long id, String confirmMan, String confirmOperateMan) {
        return partnerCashMapper.doAgree(id, confirmMan, confirmOperateMan);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRefuse(Long id, String refuseReason, String refuseMan, String confirmOperateMan) {
        int success = partnerCashMapper.doRefuse(id, refuseReason, refuseMan, confirmOperateMan);
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPay(Long id, String paySn, BigDecimal applyAmount, String payMan, Date payDate) {
        int success = partnerCashMapper.doPay(id, paySn, applyAmount, payMan, payDate);
        return success;
    }

}
