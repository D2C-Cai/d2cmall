package com.d2c.report.mongo.dao;

import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.common.mongodb.page.QueryHelper;
import com.d2c.report.mongo.model.PartnerSaleDayDO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 买手每日销售数据
 *
 * @author wull
 */
@Component
public class PartnerSaleDayMongoDao extends ListMongoDao<PartnerSaleDayDO> {

    public long countByPartnerId(Long partnerId) {
        return count(new Query(Criteria.where("partnerId").is(partnerId)));
    }

    public PartnerSaleDayDO findPartnerSaleDay(Long partnerId, String day) {
        return this.findOne(new Query(Criteria.where("partnerId").is(partnerId).and("day").is(day)));
    }

    public List<PartnerSaleDayDO> findPartnerSaleDayList(Long partnerId, Date startDate, Date endDate) {
        Criteria cri = Criteria.where("partnerId").is(partnerId);
        if (startDate != null && endDate != null) {
            cri.and("day").gte(DateUt.date2str(startDate)).lt(DateUt.date2str(endDate));
        } else if (startDate != null) {
            cri.and("day").gte(DateUt.date2str(startDate));
        } else if (endDate != null) {
            cri.and("day").lt(DateUt.date2str(endDate));
        }
        return this.find(QueryHelper.sort(new Query(cri), false, "day"));
    }

}
