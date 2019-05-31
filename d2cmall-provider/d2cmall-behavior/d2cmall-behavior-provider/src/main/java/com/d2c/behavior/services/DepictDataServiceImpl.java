package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.dto.OrderDpDTO;
import com.d2c.behavior.mongo.dao.depict.DepictDataMongoDao;
import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.behavior.mongo.model.depict.DepictDataDO;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.model.OrderItem;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户画像规则
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class DepictDataServiceImpl implements DepictDataService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DepictDataMongoDao depictDataMongoDao;
    @Autowired
    private PersonService personService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 发送画像数据并保存
     */
    public void addByOrderItem(OrderItem item) {
        OrderDpDTO dto = new OrderDpDTO(item);
        if (item.getProductId() != null) {
            dto.setProduct(productSearcherQueryService.findById(item.getProductId().toString()));
        }
        addDepictData(dto, "orderItem.buyerMemberId");
    }

    public void addByOrderItem(Iterable<OrderItem> list) {
        list.forEach(item -> {
            addByOrderItem(item);
        });
    }
    // ************************************************

    /**
     * 发送画像数据并保存
     */
    public <T> void addDepictDatas(Iterable<T> beans, String memberIdField) {
        beans.forEach(bean -> {
            addDepictData(bean, memberIdField);
        });
    }

    /**
     * 创建需画像数据
     */
    public <T> void addDepictData(T bean, String memberIdField) {
        try {
            Long memberId = BeanUt.getValue(bean, memberIdField, Long.class);
            if (memberId == null) {
                throw new BaseException("memberId获取失败...memberIdField: " + memberIdField);
            }
            PersonDO person = personService.findBuildPerson(memberId);
            if (person == null) return;
            DepictDataDO data = new DepictDataDO(bean, person);
            if (!depictDataMongoDao.exist(data.getId())) {
                depictDataMongoDao.save(data);
            }
        } catch (Exception e) {
            logger.error("插入画像数据失败... 原因:" + e.getMessage());
        }
    }

    public void cleanAll() {
        depictDataMongoDao.cleanAll();
    }

}
