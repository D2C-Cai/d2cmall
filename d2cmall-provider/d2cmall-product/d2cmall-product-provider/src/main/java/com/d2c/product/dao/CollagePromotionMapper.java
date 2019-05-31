package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.query.CollagePromotionSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollagePromotionMapper extends SuperMapper<CollagePromotion> {

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<CollagePromotion> findBySearcher(@Param("searcher") CollagePromotionSearcher searcher,
                                          @Param("page") PageModel page);

    /**
     * 统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(@Param("searcher") CollagePromotionSearcher searcher);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("operator") String operator);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

    /**
     * 更新当前开团数
     *
     * @param id
     * @param num
     * @return
     */
    int updateCurrentCount(@Param("id") Long id, @Param("num") int num);

}
