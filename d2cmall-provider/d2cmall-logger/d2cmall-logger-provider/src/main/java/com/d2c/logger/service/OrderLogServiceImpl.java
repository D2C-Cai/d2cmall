package com.d2c.logger.service;

import com.d2c.logger.model.OrderLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

@Service("orderLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class OrderLogServiceImpl extends ListServiceImpl<OrderLog> implements OrderLogService {

    public List<OrderLog> findByOrderId(Long orderId) {
        Example example = new Example(OrderLog.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        example.setOrderByClause("id desc");
        return mapper.selectByCondition(example);
    }

    @Override
    public OrderLog insert(OrderLog entity) {
        return this.save(entity);
    }

}
