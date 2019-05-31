package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ShareRedPacketsGroupMapper;
import com.d2c.order.model.ShareRedPacketsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("shareRedPacketsGroupService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ShareRedPacketsGroupServiceImpl extends ListServiceImpl<ShareRedPacketsGroup>
        implements ShareRedPacketsGroupService {

    @Autowired
    private ShareRedPacketsGroupMapper shareRedPacketsGroupMapper;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ShareRedPacketsGroup insert(ShareRedPacketsGroup group) {
        return this.save(group);
    }

    @Override
    @TxTransaction
    public ShareRedPacketsGroup findById(Long id) {
        return shareRedPacketsGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public ShareRedPacketsGroup findByInitiatorMemberId(Long memberId, Long promotionId) {
        return shareRedPacketsGroupMapper.findByInitiatorMemberId(memberId, promotionId);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateNumber(Long id, Integer number) {
        return shareRedPacketsGroupMapper.updateNumber(id, number);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return shareRedPacketsGroupMapper.updateStatus(id, status);
    }

}
