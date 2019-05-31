package com.d2c.logger.service;

import com.d2c.logger.model.RefundLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

@Service("refundLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RefundLogServiceImpl extends ListServiceImpl<RefundLog> implements RefundLogService {

    public List<RefundLog> findByRefundId(Long refundId) {
        Example example = new Example(RefundLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("refundId", refundId);
        example.setOrderByClause("id desc");
        return mapper.selectByCondition(example);
    }

    public List<RefundLog> findByOrderItemId(Long orderItemId) {
        Example example = new Example(RefundLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderItemId", orderItemId);
        return mapper.selectByCondition(example);
    }

    public List<RefundLog> findByOrderId(Long orderId) {
        Example example = new Example(RefundLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        return mapper.selectByCondition(example);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public RefundLog insert(RefundLog entity) {
        return this.save(entity);
    }

    @Override
    public RefundLog findById(Long key) {
        return this.findOneById(key);
    }

}
