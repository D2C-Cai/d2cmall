package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.ProductReport;
import com.d2c.content.query.ProductReportSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductReportMapper extends SuperMapper<ProductReport> {

    /**
     * 统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(@Param("searcher") ProductReportSearcher searcher);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<ProductReport> findBySearcher(@Param("searcher") ProductReportSearcher searcher,
                                       @Param("page") PageModel page);

    /**
     * 拒绝
     *
     * @param id
     * @param verifyReason
     * @param operator
     * @return
     */
    int doRefuse(@Param("id") Long id, @Param("verifyReason") String verifyReason, @Param("operator") String operator);

    /**
     * 通过
     *
     * @param id
     * @param verifyReason
     * @param operator
     * @return
     */
    int doVerify(@Param("id") Long id, @Param("verifyReason") String verifyReason, @Param("operator") String operator);

    /**
     * 取消提交
     *
     * @param id
     * @param operator
     * @return
     */
    int cancelSumbit(@Param("id") Long id, @Param("operator") String operator);

    /**
     * 重新编辑
     *
     * @param report
     * @return
     */
    int updateReport(@Param("report") ProductReport report);

    /**
     * 逻辑删除
     */
    int deleteById(@Param("id") Long id, @Param("operator") String operator);

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    int customerDelete(@Param("id") Long id);

    /**
     * 更新图片
     *
     * @param id
     * @param pic
     * @return
     */
    int updatePic(@Param("id") Long id, @Param("pic") String pic);

}
