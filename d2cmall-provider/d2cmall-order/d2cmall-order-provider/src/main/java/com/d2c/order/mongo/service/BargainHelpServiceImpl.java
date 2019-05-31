package com.d2c.order.mongo.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.mongo.dao.BargainHelpMongoDao;
import com.d2c.order.mongo.model.BargainHelpDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("bargainHelpService")
public class BargainHelpServiceImpl implements BargainHelpService {

    @Autowired
    private BargainHelpMongoDao bargainHelpMongoDao;

    @Override
    public int countByUnionIdAndDate(String unionId, Date begainDate, Date endDate) {
        return bargainHelpMongoDao.countByUnionIdAndDate(unionId, begainDate, endDate);
    }

    @Override
    public int countByUnionIdAndBarginId(String unionId, String bargainId) {
        return bargainHelpMongoDao.countByUnionIdAndBarginId(unionId, bargainId);
    }

    @Override
    public PageResult<BargainHelpDO> findByBargainId(String bargainId, PageModel page) {
        PageResult<BargainHelpDO> dtos = new PageResult<>(page);
        long totalCount = bargainHelpMongoDao.countByBargainId(bargainId);
        if (totalCount > 0) {
            List<BargainHelpDO> list = bargainHelpMongoDao.findByBargainId(bargainId, page.getPageNumber(),
                    page.getPageSize());
            dtos.setList(list);
        }
        dtos.setTotalCount((int) totalCount);
        return dtos;
    }

}
