package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonMongoDao extends ListMongoDao<PersonDO> {

    public PersonDO findByPhone(String phone) {
        return findOne("phone", phone);
    }

    public PersonDO findByMemberInfoId(Long memberInfoId) {
        return findOne("memberInfoId", memberInfoId);
    }

    @Override
    public Collection<PersonDO> insert(Collection<PersonDO> list) {
        list.forEach(bean -> {
            save(bean);
        });
        return list;
    }

}
