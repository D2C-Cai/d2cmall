package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.dao.CollectionCardDefMapper;
import com.d2c.member.model.CollectionCardDef;
import com.d2c.member.query.CollectionCardDefSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("collectionCardDefService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CollectionCardDefServiceImpl extends ListServiceImpl<CollectionCardDef>
        implements CollectionCardDefService {

    @Autowired
    private CollectionCardDefMapper collectionCardDefMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CollectionCardDef insert(CollectionCardDef def) {
        return this.save(def);
    }

    @Override
    public PageResult<CollectionCardDef> findBySearcher(CollectionCardDefSearcher query, PageModel page) {
        PageResult<CollectionCardDef> pager = new PageResult<CollectionCardDef>(page);
        Integer totalCount = collectionCardDefMapper.countBySearcher(query);
        List<CollectionCardDef> list = new ArrayList<>();
        if (totalCount > 0) {
            list = collectionCardDefMapper.findBySearcher(query, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(CollectionCardDef def) {
        return this.updateNotNull(def);
    }

    @Override
    public List<CollectionCardDef> findByPromotionId(Long promotionId) {
        return collectionCardDefMapper.findByPromotionId(promotionId);
    }

    @Override
    public CollectionCardDef selectCard(Long promotionId) {
        List<CollectionCardDef> awardList = collectionCardDefMapper.findOrderByWeight(promotionId);
        // 排除掉库存为0的奖品
        awardList = awardList.stream().filter(a -> a.getQuantity() > 0 && a.getWeight() > 0)
                .collect(Collectors.toList());
        if (awardList == null || awardList.size() == 0) {
            throw new BusinessException("您来晚了，所有奖品已经发放完了");
        }
        final Map<Long, Integer> awardLotteryWeight = new LinkedHashMap<>();
        int totalWeight = 0;
        for (CollectionCardDef award : awardList) {
            totalWeight += award.getWeight();
            awardLotteryWeight.put(award.getId(), totalWeight);
        }
        int randNum = new Random().nextInt(totalWeight);
        Long choosedAward = null;
        // 按照权重计算中奖区间
        for (Map.Entry<Long, Integer> e : awardLotteryWeight.entrySet()) {
            if (randNum >= 0 && randNum < e.getValue()) {
                choosedAward = e.getKey(); // 落入该奖品区间
                CollectionCardDef award = collectionCardDefMapper.selectByPrimaryKey(choosedAward);
                return award;
            }
        }
        // 最大权重且有库存
        return collectionCardDefMapper.findMaxWeight(promotionId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReduce(Long id) {
        return collectionCardDefMapper.doReduce(id);
    }

    @Override
    public CollectionCardDef findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return collectionCardDefMapper.updateStatus(id, status);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return collectionCardDefMapper.updateSort(id, sort);
    }

}
