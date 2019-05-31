package com.d2c.order.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.LogisticsMapper;
import com.d2c.order.model.Logistics;
import com.d2c.order.model.LogisticsCompany;
import com.d2c.order.third.kd100.ExpressClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "logisticsService")
public class LogisticsServiceImpl extends ListServiceImpl<Logistics> implements LogisticsService {

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Logistics insert(Logistics logistics) {
        Logistics old = logisticsMapper.findBySnAndCom(logistics.getDeliverySn(), logistics.getDeliveryCode(),
                logistics.getType());
        if (old != null) {
            return old;
        }
        return this.save(logistics);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(Logistics logistics) {
        return logisticsMapper.update(logistics);
    }

    @Override
    public Logistics findBySnAndCom(String nu, String com, String type) {
        return logisticsMapper.findBySnAndCom(nu, com, type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int createLogistics(String comName, String deliverySn, String type, String operator) {
        // 如果是公司可查的物流就推送
        LogisticsCompany logisticsCompany = this.findCompanyByName(comName);
        if (logisticsCompany != null) {
            try {
                ExpressClient.getInstance().pushExpress(logisticsCompany.getCode(), deliverySn);
                // 无论是否推送成功都记一笔
                Logistics logistics = new Logistics();
                logistics.createLogistics(deliverySn, logisticsCompany.getCode(), type, operator);
                insert(logistics);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public LogisticsCompany findCompanyByName(String name) {
        return logisticsMapper.findCompanyByName(name);
    }

    @Override
    public List<Logistics> findAllBySn(String sn, String com) {
        return logisticsMapper.findAllBySn(sn, com);
    }

}
