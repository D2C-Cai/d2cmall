package com.d2c.behavior.mongo.service;

import com.d2c.behavior.mongo.dao.depict.DepictMongoDao;
import com.d2c.behavior.mongo.dao.depict.DepictTagMongoDao;
import com.d2c.behavior.mongo.model.depict.DepictDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户画像规则
 *
 * @author wull
 */
@Component
public class DepictMongoService {

    @Autowired
    private DepictMongoDao depictMongoDao;
    @Autowired
    private DepictTagMongoDao depictTagMongoDao;

    /**
     * 清除
     */
    public void cleanAll() {
        depictMongoDao.cleanAll();
        depictTagMongoDao.cleanAll();
    }

    /**
     * 修改并保存
     */
    public DepictDO save(DepictDO depict) {
        if (depict.getDepictTags() != null) {
            depict.getDepictTags().forEach((k, tag) -> {
                depictTagMongoDao.save(tag);
            });
        }
        return depictMongoDao.save(depict);
    }

    public List<DepictDO> findPage(Integer page, Integer limit) {
        return depictMongoDao.findPage(page, limit);
    }

}
