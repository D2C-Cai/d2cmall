package com.d2c.product.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ExternalCardMapper;
import com.d2c.product.model.ExternalCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "externalCardService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ExternalCardServiceImpl extends ListServiceImpl<ExternalCard> implements ExternalCardService {

    @Autowired
    private ExternalCardMapper externalCardMapper;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doUse(Long memberId, String sn, Long sourceId, Long productId) {
        return externalCardMapper.doUse(memberId, sn, sourceId, productId);
    }

    @Override
    @TxTransaction
    public ExternalCard findBySn(Long memberId, String sn) {
        return externalCardMapper.findBySn(memberId, sn);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ExternalCard insert(ExternalCard card) {
        return this.save(card);
    }

    @Override
    public int countUsed(Long productId) {
        return externalCardMapper.countUsed(productId);
    }

}
