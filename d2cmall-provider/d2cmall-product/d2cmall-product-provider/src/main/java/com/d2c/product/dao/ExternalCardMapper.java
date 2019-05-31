package com.d2c.product.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ExternalCard;
import org.apache.ibatis.annotations.Param;

public interface ExternalCardMapper extends SuperMapper<ExternalCard> {

    int doUse(@Param("memberId") Long memberId, @Param("sn") String sn, @Param("sourceId") Long sourceId,
              @Param("productId") Long productId);

    ExternalCard findBySn(@Param("memberId") Long memberId, @Param("sn") String sn);

    int countUsed(@Param("productId") Long productId);

}
