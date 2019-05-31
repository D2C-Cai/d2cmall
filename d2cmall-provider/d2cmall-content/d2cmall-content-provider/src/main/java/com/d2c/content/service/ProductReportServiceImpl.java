package com.d2c.content.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.dao.ProductReportMapper;
import com.d2c.content.model.ProductReport;
import com.d2c.content.model.ProductReport.ReportStatusEnum;
import com.d2c.content.query.ProductReportSearcher;
import com.d2c.logger.model.ProductReportLog;
import com.d2c.logger.model.ProductReportLog.ReportType;
import com.d2c.logger.service.ProductReportLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "productReportService")
@Transactional(readOnly = true, noRollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductReportServiceImpl extends ListServiceImpl<ProductReport> implements ProductReportService {

    @Autowired
    private ProductReportMapper productReportMapper;
    @Autowired
    private ProductReportLogService productReportLogService;

    @Override
    public PageResult<ProductReport> findBySearcher(ProductReportSearcher searcher, PageModel page) {
        PageResult<ProductReport> pager = new PageResult<>(page);
        int totalCount = productReportMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<ProductReport> list = productReportMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(ProductReportSearcher searcher) {
        return productReportMapper.countBySearcher(searcher);
    }

    @Override
    public ProductReport findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRefuse(Long id, String refuseReason, String operator) {
        ProductReport report = this.findById(id);
        if (report == null) {
            throw new BusinessException("报告不存在");
        }
        if (!report.getStatus().equals(ReportStatusEnum.submit.getCode())) {
            throw new BusinessException("报告状态不正确");
        }
        int success = productReportMapper.doRefuse(id, refuseReason, operator);
        if (success > 0) {
            JSONObject obj = new JSONObject();
            obj.put("操作", "商品报告审核不通过");
            obj.put("原因", refuseReason);
            ProductReportLog log = new ProductReportLog(id, ReportType.refuse.name(), obj.toJSONString(), operator);
            productReportLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doVerify(Long id, String remark, String operator) {
        ProductReport report = this.findById(id);
        if (report == null) {
            throw new BusinessException("报告不存在");
        }
        if (!report.getStatus().equals(ReportStatusEnum.submit.getCode())) {
            throw new BusinessException("报告状态不正确");
        }
        int success = productReportMapper.doVerify(id, remark, operator);
        if (success > 0) {
            JSONObject obj = new JSONObject();
            obj.put("操作", "商品报告审核通过");
            obj.put("原因", remark);
            ProductReportLog log = new ProductReportLog(id, ReportType.verify.name(), obj.toJSONString(), operator);
            productReportLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int cancelSubmit(Long id, String operator) {
        ProductReport report = this.findById(id);
        if (report == null) {
            throw new BusinessException("报告不存在");
        }
        int success = productReportMapper.cancelSumbit(id, operator);
        if (success > 0) {
            ProductReportLog log = new ProductReportLog(id, ReportType.cancel.name(), "取消商品报告的提交", operator);
            productReportLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductReport insert(ProductReport report) {
        report = this.save(report);
        if (report.getId() > 0) {
            ProductReportLog log = new ProductReportLog(report.getId(), ReportType.submit.name(), "商品报告提交成功",
                    report.getLoginCode());
            productReportLogService.insert(log);
        }
        return report;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductReport report) {
        int success = productReportMapper.updateReport(report);
        if (success > 0) {
            ProductReportLog log = new ProductReportLog(report.getId(), ReportType.submit.name(), "商品报告重新提交成功",
                    report.getLoginCode());
            productReportLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id, String operator) {
        int success = productReportMapper.deleteById(id, operator);
        if (success > 0) {
            ProductReportLog log = new ProductReportLog(id, ReportType.delete.name(), "删除报告", operator);
            productReportLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int customerDelete(Long id) {
        return productReportMapper.customerDelete(id);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updatePic(Long id, String pic) {
        return productReportMapper.updatePic(id, pic);
    }

}
