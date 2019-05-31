package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.ShareRedPackets;
import com.d2c.order.query.ShareRedPacketsSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ShareRedPacketsMapper extends SuperMapper<ShareRedPackets> {

    List<ShareRedPackets> findByGroupId(@Param("groupId") Long groupId, @Param("orderByStr") String orderByStr);

    List<ShareRedPackets> findHistory(@Param("memberId") Long memberId, @Param("sharePromotionId") Long promotionId,
                                      @Param("initiator") Integer initiator);

    BigDecimal sumMoneyByGroupId(@Param("groupId") Long groupId);

    int updateStatusByGroupId(@Param("groupId") Long groupId, @Param("status") Integer status);

    List<ShareRedPackets> findBySearcher(@Param("searcher") ShareRedPacketsSearcher searcher,
                                         @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") ShareRedPacketsSearcher searcher);

}
