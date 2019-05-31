package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.ProductReport;
import com.d2c.content.query.ProductReportSearcher;

public interface ProductReportService {

    /**
     * 分页查找
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ProductReport> findBySearcher(ProductReportSearcher searcher, PageModel page);

    /**
     * 统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(ProductReportSearcher searcher);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    ProductReport findById(Long id);

    /**
     * 审核拒绝
     *
     * @param id
     * @param refuseReason
     * @param username
     * @return
     */
    int doRefuse(Long id, String refuseReason, String username);

    /**
     * 审核通过
     *
     * @param id
     * @param refuseReason
     * @param username
     * @return
     */
    int doVerify(Long id, String remark, String username);

    /**
     * 取消提交
     *
     * @param id
     * @param operator
     * @return
     */
    int cancelSubmit(Long id, String operator);

    /**
     * 新增
     *
     * @param report
     * @return
     */
    ProductReport insert(ProductReport report);

    /**
     * 删除
     *
     * @param id
     * @param operator
     * @return
     */
    int delete(Long id, String operator);

    /**
     * 更新
     *
     * @param report
     * @return
     */
    int update(ProductReport report);

    /**
     * 客户的物理删除
     *
     * @param id
     * @return
     */
    int customerDelete(Long id);

    /**
     * 更新商品报告图片
     *
     * @param id
     * @param pic
     * @return
     */
    int updatePic(Long id, String pic);

}
