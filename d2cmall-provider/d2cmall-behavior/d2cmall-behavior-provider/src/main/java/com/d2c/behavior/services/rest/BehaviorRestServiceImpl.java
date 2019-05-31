package com.d2c.behavior.services.rest;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.EventMongoDao;
import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.common.kafka.KafkaHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * REST服务
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class BehaviorRestServiceImpl implements BehaviorRestService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EventMongoDao eventMongoDao;
    @Autowired
    private KafkaHandler kafkaHandler;

    @Override
    public Object test() {
        kafkaHandler.send("test", "mytest-msg");
        return eventMongoDao.countAll();
    }

    @Override
    public Object updateEvent() {
        PageBucket<EventDO> bucket = new PageBucket<EventDO>() {
            @Override
            public List<EventDO> nextList(int page, int pageSize) {
                Query query = new Query(Criteria.where("targetId").exists(false).and("data.targetId").exists(true));
                return eventMongoDao.findPage(query, 1, pageSize);
            }
        };
        int i = 0;
        while (bucket.hasNext()) {
            EventDO bean = (EventDO) bucket.next();
            if (bean.getData() != null) {
                Object targetId = BeanUt.getValue(bean.getData(), "targetId");
                if (targetId != null) {
                    bean.setTargetId(targetId);
                    eventMongoDao.save(bean);
                    i++;
                }
            }
            if (i % 1000 == 0) {
                logger.info("已修改event:" + i);
            }
        }
        return ResultHandler.successMsg("完成数据修改:" + bucket.getCount());
    }

}
