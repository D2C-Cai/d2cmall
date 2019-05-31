package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.AwardSession;
import com.d2c.product.query.AwardSessionSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AwardSessionMapper extends SuperMapper<AwardSession> {

    List<AwardSession> findBySearcher(@Param("searcher") AwardSessionSearcher searcher,
                                      @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") AwardSessionSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    List<AwardSession> findByLotterySource(@Param("over") Integer over, @Param("lotterySource") String lotterySource);

}
