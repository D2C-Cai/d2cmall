package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.entity.enums.OperateLogType;
import com.d2c.behavior.mongo.dao.EventMongoDao;
import com.d2c.behavior.mongo.dao.OperLogMongoDao;
import com.d2c.behavior.mongo.dao.PersonMongoDao;
import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.behavior.mongo.model.OperLogDO;
import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.base.bucket.PageBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 原埋点数据导入到Event
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class OperEventServiceImpl implements OperEventService {

    private final static ExecutorService pools = MyExecutors.newLimit(20);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PersonMongoDao personMongoDao;
    @Autowired
    private OperLogMongoDao operLogMongoDao;
    @Autowired
    private EventMongoDao eventMongoDao;

    /**
     * 导入原来的埋点数据
     */
    public void importEvent() {
        PageBucket<OperLogDO> bucket = new PageBucket<OperLogDO>() {
            @Override
            public List<OperLogDO> nextList(int page, int limit) {
                return operLogMongoDao.findPage(null, page, limit);
            }
        };
        Date start = new Date();
        logger.info("开始导入埋点数据..." + bucket.getCount());
        while (bucket.hasNext()) {
            OperLogDO bean = bucket.next();
            int count = bucket.getCount();
            pools.execute(new Runnable() {
                @Override
                public void run() {
                    saveEvent(bean);
                }
            });
            if (count % 100 == 0) {
                logger.info(DateUt.duration(start, new Date()) + " 导入埋点数据..." + count
                        + " lastDate: " + DateUt.time2str(bean.getGmtModified()));
            }
        }
    }

    /**
     * 历史埋点数据分析处理
     */
    private EventDO saveEvent(OperLogDO bean) {
        EventDO event = new EventDO();
        PersonDO person = personMongoDao.findByMemberInfoId(Long.parseLong(bean.getMemberId()));
        event.setGmtModified(bean.getGmtModified());
        event.setEvent(OperateLogType.getName(bean.getLogType()));
        if (person != null) {
            event.setPersonId(person.getId());
        }
        return eventMongoDao.save(event);
    }

}
