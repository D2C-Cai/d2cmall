package com.d2c.member.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.PartnerStoreMapper;
import com.d2c.member.model.PartnerStore;
import com.d2c.member.query.PartnerStoreSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("partnerStoreService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerStoreServiceImpl extends ListServiceImpl<PartnerStore> implements PartnerStoreService {

    @Autowired
    private PartnerStoreMapper partnerStoreMapper;
    @Autowired
    private PartnerService partnerService;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PartnerStore insert(PartnerStore partnerStore) {
        partnerStore = this.save(partnerStore);
        partnerService.updateStoreId(partnerStore.getPartnerId(), partnerStore.getId(), partnerStore.getCreator());
        return partnerStore;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(PartnerStore partnerStore) {
        return this.updateNotNull(partnerStore);
    }

    @Override
    public PartnerStore findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PartnerStore findByMemberId(Long memberId) {
        return partnerStoreMapper.findByMemberId(memberId);
    }

    @Override
    public PartnerStore findByPartnerId(Long partnerId) {
        return partnerStoreMapper.findByPartnerId(partnerId);
    }

    @Override
    public PageResult<PartnerStore> findBySearcher(PartnerStoreSearcher searcher, PageModel page) {
        PageResult<PartnerStore> pager = new PageResult<>(page);
        int totalCount = partnerStoreMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<PartnerStore> list = partnerStoreMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(PartnerStoreSearcher searcher) {
        return partnerStoreMapper.countBySearcher(searcher);
    }

}
