package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.PartnerGiftMapper;
import com.d2c.order.model.PartnerGift;
import com.d2c.order.query.PartnerGiftSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("partnerGiftService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerGiftServiceImpl extends ListServiceImpl<PartnerGift> implements PartnerGiftService {

    @Autowired
    private PartnerGiftMapper partnerGiftMapper;

    @Override
    public PartnerGift findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public PartnerGift insert(PartnerGift partnerGift) {
        partnerGift = this.save(partnerGift);
        return partnerGift;
    }

    @Override
    public PageResult<PartnerGift> findBySearcher(PartnerGiftSearcher searcher, PageModel page) {
        PageResult<PartnerGift> pager = new PageResult<>(page);
        Integer toltalCount = partnerGiftMapper.countBySearcher(searcher);
        List<PartnerGift> list = new ArrayList<>();
        if (toltalCount > 0) {
            list = partnerGiftMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(toltalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Integer countBySearcher(PartnerGiftSearcher searcher) {
        return partnerGiftMapper.countBySearcher(searcher);
    }

}
