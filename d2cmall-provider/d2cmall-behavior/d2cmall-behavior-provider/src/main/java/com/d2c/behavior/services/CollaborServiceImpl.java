package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.collabor.PersonActionMongoDao;
import com.d2c.behavior.mongo.dao.collabor.PersonCollaborMongoDao;
import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.behavior.mongo.model.collabor.PersonActionDO;
import com.d2c.behavior.mongo.model.collabor.PersonCollaborDO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.model.CartItem;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.CartService;
import com.d2c.order.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户商品协同过滤
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class CollaborServiceImpl implements CollaborService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    private MemberInfoService memberInfoService;
    @Reference
    private OrderItemService orderItemService;
    @Reference
    private CartService cartService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonActionMongoDao personActionMongoDao;
    @Autowired
    private PersonCollaborMongoDao personCollaborMongoDao;

    /**
     * 对基础数据进行初始化
     */
    @Override
    public Object init() {
        logger.info("开始初始化...");
        clean();
        return loadAll();
    }

    @Override
    public String clean() {
        logger.info("开始清除数据...");
        personCollaborMongoDao.cleanAll();
        personActionMongoDao.cleanAll();
        return "操作成功";
    }

    @Override
    public Object find() {
        return null;
    }

    /**
     * 用户商品协同过滤 全量训练
     */
    @Override
    public Object loadAll() {
        PageBucket<MemberInfo> bucket = new PageBucket<MemberInfo>(1000) {
            @Override
            public List<MemberInfo> nextList(int page, int pageSize) {
                return memberInfoService.findPageByLastLogin(page, pageSize);
            }
        };
        int limit = 1000;
        while (bucket.hasNext()) {
            MemberInfo bean = bucket.next();
            try {
                logger.info("开始加载用户 <" + bean.getNickname() + ":" + bean.getId() + "> ...");
                PersonDO person = personService.findBuildPerson(bean);
                if (person == null) {
                    logger.error(bean.getId() + "不能找到对应用户..." + JsonUt.serialize(bean));
                    continue;
                }
                PersonActionDO action = new PersonActionDO();
                action.setPersonId(person.getId());
                action.setMemberId(bean.getId());
                List<OrderItem> items = orderItemService.findByMemberInfoId(bean.getId(), new PageModel(1, limit));
                List<Long> orderProIds = new ArrayList<>();
                for (OrderItem item : items) {
                    orderProIds.add(item.getProductId());
                }
                action.setOrderProductIds(orderProIds);
                List<CartItem> cartItems = cartService.findCart(bean.getId()).getItems();
                List<Long> carProIds = new ArrayList<>();
                for (CartItem item : cartItems) {
                    carProIds.add(item.getProductId());
                }
                action.setCarProductIds(carProIds);
                List<Long> collIds = memberCollectionSearcherService.findProductIdsInCollection(bean.getId(),
                        new PageModel(1, limit));
                action.setCollectionProductIds(collIds);
                personActionMongoDao.save(action);
                logger.info("用户行为加载完成..." + action);
                List<PersonCollaborDO> pcList = collabor(action);
                logger.info("行为评分..." + JsonUt.serialize(pcList));
            } catch (Exception e) {
                logger.error("加载用户 <" + bean.getNickname() + ":" + bean.getId() + "> 失败...", e);
            }
        }
        return personCollaborMongoDao.findLimit(null, 100);
    }

    public List<PersonCollaborDO> collabor(PersonActionDO action) {
        List<PersonCollaborDO> list = new ArrayList<>();
        collaborImpl(list, action, action.getOrderProductIds(), 200.0);
        collaborImpl(list, action, action.getCarProductIds(), 100.0);
        collaborImpl(list, action, action.getCollectionProductIds(), 50.0);
        return list;
    }

    public void collaborImpl(List<PersonCollaborDO> list, PersonActionDO action, List<Long> proIds, Double rating) {
        proIds.forEach(pid -> {
            PersonCollaborDO bean = personCollaborMongoDao.findCollabor(action.getMemberId(), pid);
            if (bean == null) {
                bean = new PersonCollaborDO();
                bean.setPersonId(action.getPersonId());
                bean.setMemberId(action.getMemberId());
            }
            bean.setProductId(pid);
            bean.addRating(rating);
            personCollaborMongoDao.save(bean);
            list.add(bean);
        });
    }

}
