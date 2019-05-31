package com.d2c.member.service;

import com.d2c.member.model.CollectionCard;
import com.d2c.member.model.CollectionCardRecord;

import java.util.List;

public interface CollectionCardRecordService {

    CollectionCardRecord insert(CollectionCardRecord collectionCardAwardRecord);

    List<CollectionCardRecord> findByMemberId(Long memberId, Long promotionId);

    // 发放集卡奖励
    CollectionCardRecord doCollectionCard(CollectionCard collectionCard);

    List<CollectionCardRecord> findStageRecord(Long memberId, Long promotionId);

    List<CollectionCardRecord> findRecently(Integer size);

}
