package com.d2c.report.mongo.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.common.mongodb.page.QueryHelper;
import com.d2c.report.mongo.model.PartnerSaleMonthDO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 买手月销售数据
 *
 * @author wull
 */
@Component
public class PartnerSaleMonthMongoDao extends ListMongoDao<PartnerSaleMonthDO> {

    public List<PartnerSaleMonthDO> findPagePartnerMonth(Long partnerId, PageModel pager) {
        Query query = new Query(Criteria.where("partnerId").is(partnerId));
        if (pager.getPageSort() == null) {
            QueryHelper.sort(query, false, "month");
        }
        return findPage(query, pager);
    }

    public PartnerSaleMonthDO findPartnerSaleMonth(Long partnerId, String month) {
        return this.findOne(new Query(Criteria.where("partnerId").is(partnerId).and("month").is(month)));
    }

    public List<PartnerSaleMonthDO> findPartnerSaleMonthList(Long partnerId, Date startDate) {
        Criteria cri = Criteria.where("partnerId").is(partnerId);
        if (startDate != null) {
            cri.and("month").gte(DateUt.date2str(startDate));
        }
        Query query = new Query(cri);
        QueryHelper.sort(query, false, "month");
        return this.find(query);
    }

}
