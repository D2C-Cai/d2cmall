package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Brand;
import com.d2c.product.query.BrandSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends SuperMapper<Brand> {

    Brand findByCode(@Param("code") String code);

    Map<String, Object> findSimpleByCode(@Param("code") String code);

    Brand findByBrandCode(@Param("brandCode") String brandCode);

    Brand findByDomain(String domain);

    List<Brand> findByCouponDefId(@Param("pager") PageModel pager, @Param("defineId") Long defineId);

    int countByCouponDefId(@Param("defineId") Long defineId);

    List<Brand> findByDesignerTagId(@Param("tagId") Long tagId, @Param("pager") PageModel pager);

    int countByDesignerTagId(Long tagId);

    List<Brand> findByDesignersId(@Param("designersId") Long designersId, @Param("mark") Integer[] mark);

    List<Long> findIdsByDesignersId(@Param("designersId") Long designersId);

    String findNameByIds(@Param("designerIds") Long[] designerId);

    List<Brand> findBySearch(@Param("searcher") BrandSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") BrandSearcher searcher);

    List<Brand> findByLetters();

    int updateMark(@Param("id") Long id, @Param("mark") int mark, @Param("markMan") String markMan);

    int updateSubscribe(@Param("id") Long id, @Param("subscribe") int subscribe);

    int updateFans(@Param("id") Long id, @Param("fans") int fans);

    int updateAfter(@Param("id") Long id, @Param("after") Integer after);

    int updateCod(@Param("id") Long id, @Param("cod") Integer cod);

    int updateRecommend(@Param("id") Long id, @Param("recommend") Integer recommend);

    int updateTags(@Param("id") Long id, @Param("tags") String tags);

    int updateDesigners(@Param("id") Long id, @Param("code") String code, @Param("designers") String designers);

    int updateVideoById(@Param("id") Long id, @Param("video") String video);

    int updateBigPic(@Param("id") Long id, @Param("bigPic") String bigPic);

    int updateSales(@Param("id") Long id, @Param("sales") Integer sales);

    List<Long> findAllSales();

    int updateStyle(@Param("id") Long id, @Param("style") String style);

}
