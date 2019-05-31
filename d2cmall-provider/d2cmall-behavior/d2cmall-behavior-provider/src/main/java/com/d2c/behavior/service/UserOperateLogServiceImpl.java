package com.d2c.behavior.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.entity.UserOperateLogDO;
import com.d2c.behavior.mongo.dao.OperLogMongoDao;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.mybatis.service.JdbcServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(protocol = {"dubbo", "rest"})
public class UserOperateLogServiceImpl extends JdbcServiceImpl<UserOperateLogDO> implements UserOperateLogService {

    public static String[] tableSufs = new String[]{"201405", "201406", "201407", "201408", "201409", "201410",
            "201411", "201411", "201501", "201502", "201503", "201504", "201505", "201506", "201507", "201508",
            "201509", "201510", "201511", "201512", "201601", "201602", "201603", "201604", "201605", "201606",
            "201607", "201608", "201609", "201610", "201611", "201612", "201701", "201702", "201703", "201704",
            "201705", "201706", "201707", "201708", "201709", "201710", "201711", "201712", ""};
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected OperLogMongoDao operLogMongoDao;

    @Override
    public Object find() {
        return findOperLog("", 1, 20);
    }

    @Override
    public Object exportAll() {
        for (String tb : tableSufs) {
            try {
                PageBucket<UserOperateLogDO> bucket = findBucket(tb);
                while (bucket.hasNext()) {
                    logger.info("useroperatelog_" + tb + " 导入OperLog:" + bucket);
                    try {
                        operLogMongoDao.insertLog(bucket.getNextList());
                    } catch (Exception e) {
                        logger.error("useroperatelog_" + tb + " insertLog:", e);
                    }
                }
            } catch (Exception e) {
                logger.error("useroperatelog_" + tb + " findBucket:", e);
            }
        }
        return operLogMongoDao.findLimit(null, 20);
    }

    public PageBucket<UserOperateLogDO> findBucket(String tableSuf) {
        return new PageBucket<UserOperateLogDO>(null, 5000) {
            @Override
            public List<UserOperateLogDO> nextList(int page, int pageSize) {
                return findOperLog(tableSuf, page, pageSize);
            }
        };
    }

    public List<UserOperateLogDO> findOperLog(String tableSuf, int page, int pageSize) {
        if (!StringUtils.isEmpty(tableSuf)) {
            tableSuf = "_" + tableSuf;
        }
        String sql = "select * from useroperatelog" + tableSuf + getPageStr(page, pageSize);
        return this.findList(sql);
    }

}
