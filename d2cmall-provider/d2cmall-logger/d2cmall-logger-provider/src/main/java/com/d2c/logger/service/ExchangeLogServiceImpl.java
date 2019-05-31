package com.d2c.logger.service;

import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.ReshipLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

@Service("exchangeLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ExchangeLogServiceImpl extends ListServiceImpl<ExchangeLog> implements ExchangeLogService {

    public List<ExchangeLog> findByExchangeId(Long exchangeId) {
        Example example = new Example(ExchangeLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("exchangeId", exchangeId);
        example.setOrderByClause("id desc");
        return mapper.selectByCondition(example);
    }

    @Override
    public ExchangeLog insert(ExchangeLog entity) {
        return this.save(entity);
    }

    @Override
    public ExchangeLog findById(Long key) {
        return this.findOneById(key);
    }

    public List<ExchangeLog> findByOrderItemId(Long orderItemId) {
        Example example = new Example(ReshipLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderItemId", orderItemId);
        return mapper.selectByCondition(example);
    }

    public List<ExchangeLog> findByOrderId(Long orderId) {
        Example example = new Example(ReshipLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        return mapper.selectByCondition(example);
    }

}
