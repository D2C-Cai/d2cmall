package com.d2c.behavior.services.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.depict.DepictMongoDao;
import com.d2c.behavior.mongo.service.DepictMongoService;
import com.d2c.behavior.services.DepictDataServiceImpl;
import com.d2c.behavior.services.TagService;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户画像
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class DepictRestServiceImpl implements DepictRestService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    private OrderItemService orderItemService;
    @Autowired
    private DepictDataServiceImpl depictDataService;
    @Autowired
    private TagService tagService;
    @Autowired
    private DepictMongoService depictMongoService;
    @Autowired
    private DepictMongoDao depictMongoDao;

    @Override
    public Object findPage(String tagId) {
        return depictMongoDao.findPageByTagId(tagId, new PageModel());
    }

    @Override
    public Object find() {
        return depictMongoService.findPage(1, 100);
    }

    @Override
    public Object count() {
        return depictMongoDao.countDepict();
    }

    /**
     * 对基础数据进行初始化
     */
    @Override
    public Object init() {
        logger.info("开始初始化用户标签...");
        return tagService.initTag();
    }

    @Override
    public String clean() {
        logger.info("开始清除数据...");
        cleanData();
        tagService.cleanAll();
        return "操作成功";
    }

    @Override
    public String cleanData() {
        depictDataService.cleanAll();
        depictMongoService.cleanAll();
        return "操作成功";
    }

    /**
     * 用户画像，加载历史订单数据
     */
    @Override
    public Object loadOrder(Integer limit) {
        PageBucket<OrderItem> list = new PageBucket<OrderItem>(limit) {
            @Override
            public List<OrderItem> nextList(int page, int pageSize) {
                return orderItemService.findPage(page, pageSize);
            }
        };
        depictDataService.addByOrderItem(list);
        return ResultHandler.success();
    }

}
