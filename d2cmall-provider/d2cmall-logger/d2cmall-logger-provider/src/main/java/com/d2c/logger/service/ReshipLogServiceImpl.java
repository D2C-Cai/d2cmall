package com.d2c.logger.service;

import com.d2c.logger.model.ReshipLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

@Service("reshipLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ReshipLogServiceImpl extends ListServiceImpl<ReshipLog> implements ReshipLogService {

    public List<ReshipLog> findByReshipId(Long reshipId) {
        Example example = new Example(ReshipLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("reshipId", reshipId);
        example.setOrderByClause("id desc");
        return mapper.selectByCondition(example);
    }

    public List<ReshipLog> findByOrderItemId(Long orderItemId) {
        Example example = new Example(ReshipLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderItemId", orderItemId);
        return mapper.selectByCondition(example);
    }

    public List<ReshipLog> findByOrderId(Long orderId) {
        Example example = new Example(ReshipLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        return mapper.selectByCondition(example);
    }

    @Override
    public ReshipLog findById(Long key) {
        return this.findOneById(key);
    }

    @Override
    public ReshipLog insert(ReshipLog entity) {
        return this.save(entity);
    }

}
