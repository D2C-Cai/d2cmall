package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.CollectionCard;
import com.d2c.member.query.CollectionCardSearcher;

import java.util.List;

public interface CollectionCardService {

    CollectionCard insert(CollectionCard collectionCard);

    PageResult<CollectionCard> findBySearcher(CollectionCardSearcher searcher, PageModel page);

    List<CollectionCard> findByMemberId(Long memberId, Long promotionId);

    Integer countBySearcher(CollectionCardSearcher searcher);

}
