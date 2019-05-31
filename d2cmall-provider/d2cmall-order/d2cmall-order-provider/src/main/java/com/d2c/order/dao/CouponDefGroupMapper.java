package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.query.CouponDefGroupSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CouponDefGroupMapper extends SuperMapper<CouponDefGroup> {

    CouponDefGroup findByCode(String code);

    CouponDefGroup findByCipher(@Param("cipher") String cipher);

    List<CouponDefGroup> findBySearch(@Param("searcher") CouponDefGroupSearcher searcher,
                                      @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") CouponDefGroupSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateFixDefIds(@Param("id") Long id, @Param("fixDefIds") String fixDefIds);

    int updateRandomDefIds(@Param("id") Long id, @Param("randomDefIds") String randomDefIds);

    int updateClaimed(int id);

    int updateClaimed(@Param("groupId") Long groupId, @Param("num") long num);

    List<Long> findByUpdateClaimed(@Param("modifyDate") Date modifyDate, @Param("pager") PageModel page);

    int countByUpdateClaimed(@Param("modifyDate") Date modifyDate);

}
