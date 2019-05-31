package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.PersonDeviceDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.stereotype.Component;

@Component
public class PersonDeviceMongoDao extends ListMongoDao<PersonDeviceDO> {

    /**
     * 查询设备，不存在则新建
     */
    public PersonDeviceDO findCreateDeviceById(String deviceId) {
        PersonDeviceDO bean = findById(deviceId);
        if (bean == null) {
            bean = insert(new PersonDeviceDO(deviceId));
        }
        return bean;
    }

}
