package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.query.BargainPromotionSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BargainPromotionMapper extends SuperMapper<BargainPromotion> {

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateMark(@Param("id") Long id, @Param("mark") Integer mark, @Param("operator") String operator);

    /**
     * 分页查询
     *
     * @param searcher
     * @param pager
     * @return
     */
    List<BargainPromotion> findBySearcher(@Param("searcher") BargainPromotionSearcher searcher,
                                          @Param("page") PageModel page);

    /**
     * 分页统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(@Param("searcher") BargainPromotionSearcher searcher);

    /**
     * 增加实际参团人数
     *
     * @param id
     * @return
     */
    int addCount(@Param("id") Long id);

}
