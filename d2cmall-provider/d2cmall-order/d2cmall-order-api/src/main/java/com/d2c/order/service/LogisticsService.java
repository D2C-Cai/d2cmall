package com.d2c.order.service;

import com.d2c.order.model.Logistics;
import com.d2c.order.model.LogisticsCompany;

import java.util.List;

public interface LogisticsService {

    int update(Logistics logistics);

    /**
     * 通过物流编号和物流公司查找（物流公司可以为null）
     *
     * @param sn
     * @param com
     * @param type 业务类型 可以不填
     * @return
     */
    Logistics findBySnAndCom(String nu, String com, String type);

    /**
     * 推送物流并记录信息
     *
     * @param com
     * @param deliverySn
     * @param type
     * @param operator
     * @return
     */
    int createLogistics(String com, String deliverySn, String type, String operator);

    /**
     * 根据物流公司名查物流公司
     *
     * @param comName
     * @return
     */
    LogisticsCompany findCompanyByName(String comName);

    /**
     * 根据物流编号查询
     *
     * @param sn
     * @param com
     * @return
     */
    List<Logistics> findAllBySn(String sn, String com);

}
