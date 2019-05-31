package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.model.PartnerWithhold;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "partnerWithholdService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerWithholdServiceImpl extends ListServiceImpl<PartnerWithhold> implements PartnerWithholdService {

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public PartnerWithhold insert(PartnerWithhold partnerWithhold) {
        partnerWithhold = this.save(partnerWithhold);
        return partnerWithhold;
    }

    @Override
    public PartnerWithhold findBySn(String sn) {
        return this.findOneByFieldName("sn", sn);
    }

}
