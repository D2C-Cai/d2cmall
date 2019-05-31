package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.CollectionCardMapper;
import com.d2c.member.model.CollectionCard;
import com.d2c.member.query.CollectionCardSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("collectionCardService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class CollectionCardServiceImpl extends ListServiceImpl<CollectionCard> implements CollectionCardService {

    @Autowired
    private CollectionCardMapper CollectionCardMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CollectionCard insert(CollectionCard collectionCard) {
        return this.save(collectionCard);
    }

    @Override
    public PageResult<CollectionCard> findBySearcher(CollectionCardSearcher searcher, PageModel page) {
        PageResult<CollectionCard> pager = new PageResult<CollectionCard>(page);
        Integer totalCount = CollectionCardMapper.countBySearcher(searcher);
        List<CollectionCard> list = new ArrayList<>();
        if (totalCount > 0) {
            list = CollectionCardMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public List<CollectionCard> findByMemberId(Long memberId, Long promotionId) {
        return CollectionCardMapper.findByMemberId(memberId, promotionId);
    }

    @Override
    public Integer countBySearcher(CollectionCardSearcher searcher) {
        return CollectionCardMapper.countBySearcher(searcher);
    }

}
