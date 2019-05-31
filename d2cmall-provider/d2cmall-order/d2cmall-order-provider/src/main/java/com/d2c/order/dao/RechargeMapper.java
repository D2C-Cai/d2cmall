package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Recharge;
import com.d2c.order.query.RechargeSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeMapper extends SuperMapper<Recharge> {

    Recharge findBySn(@Param("sn") String sn);

    List<Recharge> findBySearch(@Param("searcher") RechargeSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") RechargeSearcher searcher);

    int doSubmit(@Param("id") Long id, @Param("submitor") String submitor, @Param("paySn") String paySn);

    int doClose(@Param("id") Long id, @Param("closer") String closer, @Param("closeReason") String closeReason);

}
