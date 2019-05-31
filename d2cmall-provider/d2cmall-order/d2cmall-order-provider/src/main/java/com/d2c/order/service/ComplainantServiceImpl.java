package com.d2c.order.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ComplainantMapper;
import com.d2c.order.model.Complainant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("complainantService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ComplainantServiceImpl extends ListServiceImpl<Complainant> implements ComplainantService {

    @Autowired
    private ComplainantMapper complainantMapper;

    public Complainant findByMemberId(Long memberId) {
        return complainantMapper.findByMemberId(memberId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Complainant insert(Complainant complainant) {
        return this.save(complainant);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public int update(Complainant complainant) {
        return this.updateNotNull(complainant);
    }

}
