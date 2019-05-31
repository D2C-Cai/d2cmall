package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ShareRedPacketsMapper;
import com.d2c.order.model.ShareRedPackets;
import com.d2c.order.query.ShareRedPacketsSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("shareRedPacketsService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ShareRedPacketsServiceImpl extends ListServiceImpl<ShareRedPackets> implements ShareRedPacketsService {

    @Autowired
    private ShareRedPacketsMapper shareRedPacketsMapper;

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ShareRedPackets insert(ShareRedPackets shareRedPackets) {
        return this.save(shareRedPackets);
    }

    @Override
    @TxTransaction
    public List<ShareRedPackets> findByGroupId(Long groupId, String orderByStr) {
        return shareRedPacketsMapper.findByGroupId(groupId, orderByStr);
    }

    @Override
    public List<ShareRedPackets> findHistory(Long memberId, Long promotionId, Integer initiator) {
        return shareRedPacketsMapper.findHistory(memberId, promotionId, initiator);
    }

    @Override
    @TxTransaction
    public BigDecimal sumMoneyByGroupId(Long id) {
        return shareRedPacketsMapper.sumMoneyByGroupId(id);
    }

    @Override
    public PageResult<ShareRedPackets> findBySearcher(ShareRedPacketsSearcher searcher, PageModel page) {
        PageResult<ShareRedPackets> pager = new PageResult<ShareRedPackets>();
        Integer totalCount = shareRedPacketsMapper.countBySearcher(searcher);
        List<ShareRedPackets> list = new ArrayList<ShareRedPackets>();
        if (totalCount > 0) {
            list = shareRedPacketsMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Integer countBySearcher(ShareRedPacketsSearcher searcher) {
        return shareRedPacketsMapper.countBySearcher(searcher);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateStatusByGroupId(Long groupId, Integer status) {
        return shareRedPacketsMapper.updateStatusByGroupId(groupId, status);
    }

}
