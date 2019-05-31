package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.AwardSessionMapper;
import com.d2c.product.model.AwardSession;
import com.d2c.product.query.AwardSessionSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("awardSessionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AwardSessionServiceImpl extends ListServiceImpl<AwardSession> implements AwardSessionService {

    @Autowired
    private AwardSessionMapper awardProductGroupMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public AwardSession insert(AwardSession awardProductGroup) {
        awardProductGroupMapper.insert(awardProductGroup);
        return awardProductGroup;
    }

    @Override
    public AwardSession findById(Long id) {
        return awardProductGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<AwardSession> findBySearcher(AwardSessionSearcher searcher, PageModel page) {
        PageResult<AwardSession> pager = new PageResult<AwardSession>(page);
        Integer totalCount = awardProductGroupMapper.countBySearcher(searcher);
        List<AwardSession> list = new ArrayList<AwardSession>();
        if (totalCount > 0) {
            list = awardProductGroupMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return awardProductGroupMapper.updateStatus(id, status);
    }

    @Override
    public List<AwardSession> findByLotterySource(Integer over, String lotterySource) {
        return awardProductGroupMapper.findByLotterySource(over, lotterySource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(AwardSession awardSession) {
        return this.updateNotNull(awardSession);
    }

}
