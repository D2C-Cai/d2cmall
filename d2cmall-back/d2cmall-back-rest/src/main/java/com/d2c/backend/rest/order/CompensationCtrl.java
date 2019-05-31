package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.CompensationLog;
import com.d2c.logger.service.CompensationLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.CompensationDto;
import com.d2c.order.dto.CompensationSummaryDto;
import com.d2c.order.query.CompensationSearcher;
import com.d2c.order.service.CompensationService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设计师赔偿
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/rest/order/compensation")
public class CompensationCtrl extends BaseCtrl<CompensationSearcher> {

    @Autowired
    private CompensationService compensationService;
    @Autowired
    private CompensationLogService compensationLogService;

    @Override
    protected Response doList(CompensationSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        if (searcher.getBrandIds() != null) {
            searcher.setType("designer");
        } else if (searcher.getStoreId() != null) {
            searcher.setType("store");
        }
        PageResult<CompensationDto> pager = compensationService.findBySearcher(searcher, page);
        Map<String, Object> map = compensationService.calculateBySearcher(searcher);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("map", map);
        return result;
    }

    /**
     * 赔偿单汇总
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public Response summary(CompensationSearcher searcher, PageModel page) {
        PageResult<CompensationSummaryDto> pager = compensationService.findSummary(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(CompensationSearcher searcher) {
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        if (searcher.getBrandIds() != null) {
            searcher.setType("designer");
        } else if (searcher.getStoreId() != null) {
            searcher.setType("store");
        }
        return compensationService.countBySearcher(searcher);
    }

    protected int summaryCount(CompensationSearcher searcher) {
        return compensationService.countSummary(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "compensation";
    }

    protected String getSumExportFileType() {
        return "compensationSum";
    }

    @Override
    protected List<Map<String, Object>> getRow(CompensationSearcher searcher, PageModel page) {
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        if (searcher.getBrandIds() != null) {
            searcher.setType("designer");
        } else if (searcher.getStoreId() != null) {
            searcher.setType("store");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        PageResult<CompensationDto> pager = compensationService.findBySearcher(searcher, page);
        for (CompensationDto dto : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("调拨单号", dto.getRequisitionSn());
            map.put("品牌", dto.getBrandName());
            map.put("设计师", dto.getDesignerName());
            map.put("预计发货时间", dto.getEstimateDate() != null ? DateUtil.second2str(dto.getEstimateDate()) : "");
            map.put("响应时间", dto.getCreateDate() != null ? DateUtil.second2str(dto.getCreateDate()) : "");
            map.put("超期时间", dto.getExpiredDay());
            map.put("调拨单金额", dto.getTradeAmount());
            map.put("赔偿金额", dto.getAmount());
            map.put("实际赔偿金额", dto.getActualAmount());
            map.put("状态", dto.getStatusString());
            map.put("赔偿时间", dto.getBalanceDate() != null ? DateUtil.second2str(dto.getBalanceDate()) : "");
            map.put("赔偿操作人", dto.getBalanceMan());
            map.put("订单编号", dto.getOrderSn());
            map.put("运营小组", dto.getOperation());
            map.put("备注", dto.getRemark());
            list.add(map);
        }
        return list;
    }

    protected List<Map<String, Object>> getSummaryRow(CompensationSearcher searcher, PageModel page) {
        List<Map<String, Object>> list = new ArrayList<>();
        PageResult<CompensationSummaryDto> pager = compensationService.findSummary(searcher, page);
        for (CompensationSummaryDto dto : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("品牌", dto.getBrandName());
            map.put("设计师", dto.getDesignerName());
            map.put("赔偿单数", dto.getTotalQuantity());
            map.put("已赔偿单数", dto.getPayQuantity());
            map.put("赔偿金额", dto.getTotalAmount());
            map.put("已赔偿金额", dto.getPayAmount());
            map.put("运营小组", dto.getOperation());
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "赔偿单明细";
    }

    protected String getSumFileName() {
        return "赔偿单汇总";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"调拨单号", "品牌", "设计师", "预计发货时间", "响应时间", "超期时间", "调拨单金额", "赔偿金额", "实际赔偿金额", "状态", "赔偿时间",
                "赔偿操作人", "订单编号", "运营小组", "备注"};
    }

    protected String[] getSumExportTitles() {
        return new String[]{"品牌", "设计师", "赔偿单数", "已赔偿单数", "赔偿金额", "已赔偿金额", "运营小组"};
    }

    @Override
    protected Response doHelp(CompensationSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 赔偿单结算
     *
     * @param ids
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public Response doBalance(Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        String errorStr = "";
        int count = 0;
        for (Long id : ids) {
            try {
                compensationService.doBalance(id, admin.getUsername());
            } catch (BusinessException e) {
                errorStr = errorStr + "第" + count + "条，" + e.getMessage() + "</br>";
            }
            count++;
        }
        if (errorStr.length() > 0) {
            result.setMessage(errorStr);
        }
        return result;
    }

    /**
     * 更新赔偿价格
     *
     * @param id
     * @param price
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/price", method = RequestMethod.POST)
    public Response doUpdatePrice(Long id, BigDecimal price) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (price == null) {
            result.setStatus(-1);
            result.setMessage("赔偿金额不能空");
            return result;
        }
        if (price.compareTo(new BigDecimal(0)) < 0) {
            result.setStatus(-1);
            result.setMessage("赔偿金额不能负数");
            return result;
        }
        try {
            compensationService.updatePrice(id, price, admin.getUsername());
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 更新备注
     *
     * @param id
     * @param remark
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/remark", method = RequestMethod.POST)
    public Response doUpdatePrice(Long id, String remark) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            compensationService.updateRemark(id, remark, admin.getUsername());
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 操作日志
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response findCompensationList(@PathVariable("id") Long id, PageModel page) {
        PageResult<CompensationLog> pager = compensationLogService.findCompensation(id, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @RequestMapping(value = "/sum/export", method = RequestMethod.POST)
    public Response sumExport(HttpServletRequest request, CompensationSearcher searcher, PageModel pageModel) {
        try {
            SuccessResponse result = new SuccessResponse();
            result.setMsg("导出成功！");
            String fileName = this.getLoginedAdmin().getUsername() + "_" + getSumFileName();
            String[] titleNames = getSumExportTitles();
            CSVUtil csvUtil = new CSVUtil();
            csvUtil.setFileName(fileName);
            csvUtil.writeTitleToFile(titleNames);
            // 一次导出500条，如果有的话
            pageModel.setPageSize(500);
            PageResult<Object> pageResult = new PageResult<>(pageModel);
            int pagerNumber = 1;
            int totalCount = summaryCount(searcher);
            pageResult.setTotalCount(totalCount);
            boolean exportSuccess = true;
            // 在服务器端生产excel文件
            do {
                pageModel.setPageNumber(pagerNumber);
                exportSuccess = csvUtil.writeRowToFile(getSummaryRow(searcher, pageModel));
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() && exportSuccess);
            // 返回文件地址
            createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
            // 保存导出记录
            if (exportSuccess) {
                saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(),
                        this.getSumExportFileType());
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

}
