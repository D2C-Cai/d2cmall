package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ShareRedPacketsPromotionMapper;
import com.d2c.order.model.ShareRedPacketsPromotion;
import com.d2c.order.query.ShareRedPacketsPromotionSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("shareRedPacketsPromotionService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ShareRedPacketsPromotionServiceImpl extends ListServiceImpl<ShareRedPacketsPromotion>
        implements ShareRedPacketsPromotionService {

    @Autowired
    private ShareRedPacketsPromotionMapper shareRedPacketsPromotionMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ShareRedPacketsPromotion insert(ShareRedPacketsPromotion shareRedPacketsPromotion) {
        return this.save(shareRedPacketsPromotion);
    }

    @Override
    public PageResult<ShareRedPacketsPromotion> findBySearcher(ShareRedPacketsPromotionSearcher searcher,
                                                               PageModel page) {
        PageResult<ShareRedPacketsPromotion> pager = new PageResult<ShareRedPacketsPromotion>();
        Integer totalCount = shareRedPacketsPromotionMapper.countBySearcher(searcher);
        List<ShareRedPacketsPromotion> list = new ArrayList<>();
        if (totalCount > 0) {
            list = shareRedPacketsPromotionMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ShareRedPacketsPromotion shareRedPacketsPromotion) {
        return this.updateNotNull(shareRedPacketsPromotion);
    }

    @Override
    public ShareRedPacketsPromotion findNowPromotion() {
        return shareRedPacketsPromotionMapper.findNowPromotion();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return this.updateFieldById(id.intValue(), "status", status);
    }

    @Override
    public Integer countBySearcher(ShareRedPacketsPromotionSearcher searcher) {
        return shareRedPacketsPromotionMapper.countBySearcher(searcher);
    }

}
