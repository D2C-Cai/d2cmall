package com.d2c.report.mongo.dao;

import com.d2c.common.api.page.Pager;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.member.enums.PartnerStatusEnum;
import com.d2c.report.mongo.model.PartnerSaleDO;
import com.d2c.report.query.base.BasePartnerQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 买手销售数据统计
 *
 * @author wull
 */
@Component
public class PartnerSaleMongoDao extends ListMongoDao<PartnerSaleDO> {

    public List<PartnerSaleDO> findPartnerSaleList(BasePartnerQuery mongoQuery, Pager pager) {
        Query query = null;
        if (mongoQuery.getStatus() == null) {
            query = new Query(Criteria.where("status").ne(PartnerStatusEnum.CLOSE.getCode()));
        }
        return findQueryPage(query, mongoQuery, pager);
    }

    public long countPartnerSale(BasePartnerQuery mongoQuery) {
        Query query = null;
        if (mongoQuery.getStatus() == null) {
            query = new Query(Criteria.where("status").ne(PartnerStatusEnum.CLOSE.getCode()));
        }
        return count(query, mongoQuery);
    }

}
