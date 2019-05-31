package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.order.model.PartnerBill;
import com.d2c.order.query.PartnerBillSearcher;
import com.d2c.order.service.PartnerBillService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返利单
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/rest/order/partnerbill")
public class PartnerBillCtrl extends BaseCtrl<PartnerBillSearcher> {

    @Autowired
    private PartnerBillService partnerBillService;

    @Override
    protected Response doList(PartnerBillSearcher searcher, PageModel page) {
        PageResult<PartnerBill> pager = partnerBillService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(PartnerBillSearcher searcher) {
        return partnerBillService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "PartnerBill";
    }

    @Override
    protected List<Map<String, Object>> getRow(PartnerBillSearcher searcher, PageModel page) {
        PageResult<PartnerBill> pager = partnerBillService.findBySearcher(searcher, page);
        List<Map<String, Object>> list = new ArrayList<>();
        for (PartnerBill pb : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("返利编号", pb.getSn());
            map.put("返利状态", pb.getStatusName());
            map.put("购买会员", pb.getBuyerCode());
            map.put("订单编号", pb.getOrderSn());
            map.put("订单时间", DateUtil.second2str2(pb.getCreateDate()));
            map.put("商品货号", pb.getProductSn());
            map.put("商品名称", pb.getProductName());
            map.put("直接返利ID", pb.getPartnerId() == null ? 0 : pb.getPartnerId());
            map.put("团队返利ID", pb.getParentId() == null ? 0 : pb.getParentId());
            map.put("间接团队返利ID", pb.getSuperId() == null ? 0 : pb.getSuperId());
            map.put("顶级返利ID", pb.getMasterId() == null ? 0 : pb.getMasterId());
            map.put("直接返利比(%)", pb.getPartnerId() == null ? 0 : pb.getPartnerRatio());
            map.put("团队返利比(%)", pb.getParentId() == null ? 0 : pb.getParentRatio());
            map.put("间接团队返利比(%)", pb.getSuperId() == null ? 0 : pb.getSuperRatio());
            map.put("顶级返利比(%)", pb.getMasterId() == null ? 0 : pb.getMasterRatio());
            map.put("支付金额(元)", pb.getActualAmount());
            map.put("直接返利金额(元)", pb.getPartnerId() == null ? 0
                    : pb.getPartnerRatio().multiply(pb.getActualAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            map.put("团队返利金额(元)", pb.getParentId() == null ? 0
                    : pb.getParentRatio().multiply(pb.getActualAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            map.put("间接团队返利金额(元)", pb.getSuperId() == null ? 0
                    : pb.getSuperRatio().multiply(pb.getActualAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            map.put("顶级返利金额(元)", pb.getMasterId() == null ? 0
                    : pb.getMasterRatio().multiply(pb.getActualAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "返利单";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"返利编号", "返利状态", "购买会员", "订单编号", "订单时间", "商品货号", "商品名称", "直接返利ID", "团队返利ID", "间接团队返利ID",
                "顶级返利ID", "直接返利比(%)", "团队返利比(%)", "间接团队返利比(%)", "顶级返利比(%)", "支付金额(元)", "直接返利金额(元)", "团队返利金额(元)",
                "间接团队返利金额(元)", "顶级返利金额(元)"};
    }

    @Override
    protected Response doHelp(PartnerBillSearcher searcher, PageModel page) {
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
     * 导出返利表
     *
     * @param request
     * @param response
     * @param searcher
     * @param page
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public Response excel(HttpServletRequest request, HttpServletResponse response, PartnerBillSearcher searcher,
                          PageModel page) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        BeanUt.trimString(searcher);
        String fileName = admin.getUsername() + "_" + getFileName();
        String[] titleNames = null;
        String logType = getExportFileType();
        titleNames = getExportTitles();
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(fileName);
        csvUtil.writeTitleToFile(titleNames);
        PageResult<PartnerBill> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        int totalCount = partnerBillService.countBySearcher(searcher);
        pager.setTotalCount(totalCount);
        boolean exportSuccess = true;
        do {
            page.setPageNumber(pagerNumber);
            List<Map<String, Object>> list = getRow(searcher, page);
            exportSuccess = csvUtil.writeRowToFile(list);
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        if (exportSuccess) {
            saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), logType);
        }
        return result;
    }

}
