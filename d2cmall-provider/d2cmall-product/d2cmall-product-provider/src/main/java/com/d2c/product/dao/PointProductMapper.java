package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.PointProduct;
import com.d2c.product.query.PointProductSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PointProductMapper extends SuperMapper<PointProduct> {

    /**
     * 按条件统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(@Param("searcher") PointProductSearcher searcher);

    /**
     * 按条件查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<PointProduct> findBySearcher(@Param("searcher") PointProductSearcher searcher, @Param("page") PageModel page);

    /**
     * 上下架
     *
     * @param id
     * @param mark
     * @param operator
     * @return
     */
    int updateMark(@Param("id") Long id, @Param("mark") Integer mark, @Param("operator") String operator);

    /**
     * 更新数量
     *
     * @param pointProductId
     * @param count
     * @return
     */
    int updateCount(@Param("id") Long id, @Param("count") int count);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @param operator
     * @return
     */
    int updateSort(@Param("id") Long id, @Param("sort") Integer sort, @Param("operator") String operator);

}
