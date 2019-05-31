package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.ProductReport;
import com.d2c.content.query.ProductReportSearcher;
import com.d2c.content.service.ProductReportService;
import com.d2c.logger.model.ProductReportLog;
import com.d2c.logger.service.ProductReportLogService;
import com.d2c.member.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 商品报告
 *
 * @author wwn
 */
@RestController
@RequestMapping("/rest/cms/productreport")
public class ProductReportCtrl extends BaseCtrl<ProductReportSearcher> {

    @Autowired
    private ProductReportService productReportService;
    @Autowired
    private ProductReportLogService productReportLogService;

    @Override
    protected Response doList(ProductReportSearcher searcher, PageModel page) {
        PageResult<ProductReport> pager = productReportService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(ProductReportSearcher searcher) {
        return productReportService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(ProductReportSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(ProductReportSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ProductReport report = productReportService.findById(id);
        result.put("report", report);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productReportService.delete(id, admin.getUsername());
        }
        return result;
    }

    /**
     * 商品报告审核不通过
     *
     * @param id
     * @param refuseReason
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public Response doRefuse(Long id, String refuseReason) throws NotLoginException, BusinessException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        productReportService.doRefuse(id, refuseReason, admin.getUsername());
        return result;
    }

    /**
     * 审核通过
     *
     * @param id
     * @param remark
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public Response doVerified(Long id, String remark) throws NotLoginException, BusinessException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        productReportService.doVerify(id, remark, admin.getUsername());
        return result;
    }

    /**
     * 获取操作日志
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response log(@PathVariable Long id, PageModel page) {
        PageResult<ProductReportLog> pager = productReportLogService.findByReportId(id, page);
        return new SuccessResponse(pager);
    }

}
