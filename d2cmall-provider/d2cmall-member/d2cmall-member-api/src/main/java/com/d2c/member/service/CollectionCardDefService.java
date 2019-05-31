package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.CollectionCardDef;
import com.d2c.member.query.CollectionCardDefSearcher;

import java.util.List;

public interface CollectionCardDefService {

    CollectionCardDef insert(CollectionCardDef def);

    PageResult<CollectionCardDef> findBySearcher(CollectionCardDefSearcher query, PageModel page);

    int update(CollectionCardDef def);

    List<CollectionCardDef> findByPromotionId(Long promotionId);

    CollectionCardDef selectCard(Long promotionId);

    int doReduce(Long id);

    CollectionCardDef findById(Long id);

    int updateStatus(Long id, Integer status);

    int updateSort(Long id, Integer sort);

}
