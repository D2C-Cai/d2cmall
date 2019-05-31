package com.d2c.member.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.dao.CollectionCardRecordMapper;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.CollectionCard;
import com.d2c.member.model.CollectionCardRecord;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.mongo.services.CollectCardTaskService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("collectionCardRecordService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CollectionCardRecordServiceImpl extends ListServiceImpl<CollectionCardRecord>
        implements CollectionCardRecordService {

    @Autowired
    private CollectionCardService collectionCardService;
    @Autowired
    private CollectionCardDefService collectionCardDefService;
    @Autowired
    private MemberIntegrationService memberIntegrationService;
    @Autowired
    private CollectionCardRecordMapper collectionCardRecordMapper;
    @Autowired
    private CollectCardTaskService collectCardTaskService;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CollectionCardRecord insert(CollectionCardRecord collectionCardRecord) {
        try {
            collectionCardRecord = this.save(collectionCardRecord);
        } catch (Exception e) {
            throw new BusinessException("你已经帮该好友集卡，暂不能翻卡");
        }
        // 自己翻卡，扣除每日次数
        if (collectionCardRecord.getStage() == null && collectionCardRecord.getFromId() == null) {
            boolean result = collectCardTaskService.doReduceByMemberId(collectionCardRecord.getMemberId());
            if (!result) {
                throw new BusinessException("你已经没有机会");
            }
        }
        return collectionCardRecord;
    }

    @Override
    public List<CollectionCardRecord> findByMemberId(Long memberId, Long promotionId) {
        return collectionCardRecordMapper.findByMemberId(memberId, promotionId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CollectionCardRecord doCollectionCard(CollectionCard collectionCard) {
        int success = collectionCardDefService.doReduce(collectionCard.getDefId());
        CollectionCardRecord record = new CollectionCardRecord(collectionCard);
        record = this.insert(record);
        if (success == 0) {
            throw new BusinessException("系统繁忙，请重新抽取");
        }
        collectionCard = collectionCardService.insert(collectionCard);
        MemberIntegration memberIntegration = new MemberIntegration(PointRuleTypeEnum.COLLECTIONCARD,
                collectionCard.getMemberId(), collectionCard.getLoginCode(), collectionCard.getId(),
                collectionCard.getCreateDate());
        memberIntegrationService.addIntegration(memberIntegration, PointRuleTypeEnum.COLLECTIONCARD, null,
                collectionCard.getPoint(), "集卡获得积分");
        return record;
    }

    @Override
    public List<CollectionCardRecord> findStageRecord(Long memberId, Long promotionId) {
        return collectionCardRecordMapper.findStageRecord(memberId, promotionId);
    }

    @Override
    public List<CollectionCardRecord> findRecently(Integer size) {
        return collectionCardRecordMapper.findRecently(size);
    }

}
