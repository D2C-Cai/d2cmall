package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.PersonAddressDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.stereotype.Component;

@Component
public class PersonAddressMongoDao extends ListMongoDao<PersonAddressDO> {

    /**
     * 导入用户地址
     */
    public PersonAddressDO saveOnImport(PersonAddressDO bean) {
        PersonAddressDO old = findByPhone(bean.getPhone());
        if (old != null) {
            //现有用户名称不为中文，新用户为中文，用新用户替换原来的用户地址
            if (!StringUt.isChinese(old.getName()) && StringUt.isChinese(bean.getName())) {
                bean.setId(old.getId());
                return super.save(bean);
            } else {
                return old;
            }
        }
        return super.insert(bean);
    }

    public PersonAddressDO findByPhone(String phone) {
        return findOne("phone", phone);
    }

}
