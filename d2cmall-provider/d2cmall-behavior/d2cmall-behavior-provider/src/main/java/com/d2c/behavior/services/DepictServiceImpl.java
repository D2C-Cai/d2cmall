package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.depict.DepictMongoDao;
import com.d2c.behavior.mongo.model.depict.DepictDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.utils.ThreadUt;
import com.d2c.common.core.annotation.AsyncMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户画像
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class DepictServiceImpl implements DepictService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DepictMongoDao depictMongoDao;

    /**
     * 查询画像列表
     */
    public List<DepictDO> findPageByTagId(String tagId, PageModel pager) {
        return depictMongoDao.findPageByTagId(tagId, pager);
    }

    @AsyncMethod
    public void test() {
        logger.info("test调用开始...");
        ThreadUt.sleep(2000);
        logger.info("test调用结束...");
    }

}
