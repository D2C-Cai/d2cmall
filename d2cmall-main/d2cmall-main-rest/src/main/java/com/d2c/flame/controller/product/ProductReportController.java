package com.d2c.flame.controller.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.model.ProductReport;
import com.d2c.content.query.ProductReportSearcher;
import com.d2c.content.service.ProductReportService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 商品报告
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/productreport")
public class ProductReportController extends BaseController {

    @Autowired
    private ProductReportService productReportService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 商品报告列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(ProductReportSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        PageResult<ProductReport> pager = productReportService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("report", pager, array);
        return result;
    }

    /**
     * 提交商品报告
     *
     * @param report
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult doSubmit(ProductReport report) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (report == null || StringUtil.isBlack(report.getContent())) {
            throw new BusinessException("请勿提交空报告！");
        }
        if (report.getId() == null) {
            SearcherProduct product = productSearcherQueryService.findById(report.getProductId().toString());
            if (product != null) {
                report.setProductImg(product.getProductImageCover());
                report.setProductName(product.getName());
            }
            report.setLoginCode(memberInfo.getLoginCode());
            report.setMemberId(memberInfo.getId());
            report.setStatus(1);
            report.setSubmitDate(new Date());
            report.setCreator(memberInfo.getLoginCode());
            report = productReportService.insert(report);
        } else {
            productReportService.update(report);
        }
        result.put("report", report.toJson());
        return result;
    }

    /**
     * 取消商品报告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public ResponseResult doCancel(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        int success = productReportService.cancelSubmit(id, memberInfo.getLoginCode());
        if (success < 1) {
            throw new BusinessException("取消不成功！");
        }
        return result;
    }

    /**
     * 商品报告详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        this.getLoginMemberInfo();
        ProductReport report = productReportService.findById(id);
        result.put("report", report.toJson());
        return result;
    }

    /**
     * 删除商品报告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseResult delete(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        ProductReport report = productReportService.findById(id);
        if (!report.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该商品报告非本人");
        }
        int success = productReportService.customerDelete(id);
        if (success < 1) {
            throw new BusinessException("删除不成功！");
        }
        return result;
    }

    /**
     * 更新商品报告图片
     *
     * @param pic
     * @param id
     * @return
     */
    @RequestMapping(value = "/update/pic", method = RequestMethod.POST)
    public ResponseResult updatePic(String pic, Long id) {
        this.getLoginMemberInfo();
        ResponseResult result = new ResponseResult();
        if (StringUtils.isNotBlank(pic)) {
            productReportService.updatePic(id, pic);
        }
        return result;
    }

}
