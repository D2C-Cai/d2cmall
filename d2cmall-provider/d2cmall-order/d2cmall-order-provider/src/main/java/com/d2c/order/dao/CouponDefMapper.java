package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CouponDef;
import com.d2c.order.query.CouponDefSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CouponDefMapper extends SuperMapper<CouponDef> {

    CouponDef findByCode(@Param("code") String code);

    CouponDef findByCipher(@Param("cipher") String cipher);

    CouponDef findByName(@Param("name") String name);

    List<CouponDef> findByProductId(@Param("productId") Long productId);

    CouponDef findByWxCardId(String wxCardId);

    List<CouponDef> findBySearcher(@Param("pager") PageModel pager, @Param("searcher") CouponDefSearcher searcher);

    Integer countBySearcher(@Param("searcher") CouponDefSearcher searcher);

    List<Long> findByUpdateClaimed(@Param("modifyDate") Date modifyDate, @Param("pager") PageModel page);

    int countByUpdateClaimed(@Param("modifyDate") Date modifyDate);

    CouponDef checkCipherBySearcher(@Param("searcher") CouponDefSearcher searcher);

    int updateClaimed(@Param("id") Long id, @Param("num") int num);

    int sendById(Long id);

    int bindWxCard(@Param("wxCardId") String wxCardId, @Param("id") Long id);

    int doMark(@Param("mark") Integer mark, @Param("defineId") Long defineId);

    int doCloseExpired();

    List<CouponDef> findAvailableCoupons();

    List<CouponDef> findAvailableByBrandId(@Param("brandId") Long brandId, @Param("free") Integer free);

}