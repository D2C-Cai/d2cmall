package com.d2c.behavior.mongo.dao;

import com.d2c.behavior.entity.UserOperateLogDO;
import com.d2c.behavior.mongo.model.OperLogDO;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperLogMongoDao extends ListMongoDao<OperLogDO> {
    //***************** mysql导入到mongo *******************

    public void insertLog(List<UserOperateLogDO> list) {
        BulkOperations oper = mongoTemplate.bulkOps(BulkMode.UNORDERED, OperLogDO.class);
        for (UserOperateLogDO bean : list) {
            oper.insert(convert(bean));
        }
        oper.execute();
    }

    private OperLogDO convert(UserOperateLogDO bean) {
        OperLogDO res = BeanUt.buildBean(bean, OperLogDO.class);
        res.setId(null);
        res.setLogId(bean.getId());
        res.setGmtModified(bean.getCreateDate());
        return res;
    }

}
