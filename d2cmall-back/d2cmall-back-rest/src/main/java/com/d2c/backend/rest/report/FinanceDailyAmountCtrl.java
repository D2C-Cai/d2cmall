package com.d2c.backend.rest.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.ExportLog;
import com.d2c.logger.service.ExportLogService;
import com.d2c.report.model.FinanceCodDailyAmount;
import com.d2c.report.model.FinanceOnlineDailyAmount;
import com.d2c.report.model.WalletDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;
import com.d2c.report.service.FinanceCodDailyAmountService;
import com.d2c.report.service.FinanceOnlineDailyAmountService;
import com.d2c.report.service.WalletDailyAmountService;
import com.d2c.util.file.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/report/financedailyamount")
public class FinanceDailyAmountCtrl extends BaseCtrl<FinanceDailyAmountSearcher> {

    @Reference
    private FinanceOnlineDailyAmountService financeOnlineDailyAmountService;
    @Reference
    private FinanceCodDailyAmountService financeCodDailyAmountService;
    @Autowired
    private ExportLogService exportLogService;
    @Reference
    private WalletDailyAmountService walletDailyAmountService;

    @Override
    protected Response doList(FinanceDailyAmountSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        if (searcher.getPaymentType() != null && searcher.getPaymentType() == 3) {
            PageResult<FinanceCodDailyAmount> pager = financeCodDailyAmountService.findCodBySearcher(searcher, page);
            SuccessResponse result = new SuccessResponse(pager);
            return result;
        } else if (searcher.getPaymentType() != null && searcher.getPaymentType() == 7) {
            PageResult<WalletDailyAmount> pager = walletDailyAmountService.findWalletBySearcher(searcher, page);
            SuccessResponse result = new SuccessResponse(pager);
            return result;
        } else {
            PageResult<FinanceOnlineDailyAmount> pager = financeOnlineDailyAmountService.findOnlineBySearcher(searcher,
                    page);
            SuccessResponse result = new SuccessResponse(pager);
            return result;
        }
    }

    @Override
    protected int count(FinanceDailyAmountSearcher searcher) {
        return financeOnlineDailyAmountService.countBySearcher(searcher);
    }

    private int codCount(FinanceDailyAmountSearcher searcher) {
        return financeCodDailyAmountService.countCodBySearcher(searcher);
    }

    private int walletCount(FinanceDailyAmountSearcher searcher) {
        return walletDailyAmountService.countWalletBySearcher(searcher);
    }

    @Override
    @RequestMapping(value = "/finance/export", method = RequestMethod.POST)
    public Response export(FinanceDailyAmountSearcher searcher, PageModel pageModel) {
        try {
            SuccessResponse result = new SuccessResponse();
            result.setMsg("导出成功！");
            String fileName = this.getLoginedAdmin().getUsername() + "_" + getFileName();
            String[] titleNames = getExportTitles();
            if (searcher.getPaymentType() != null && searcher.getPaymentType() == 3) {
                titleNames = getCODExportTitles();
                fileName = this.getLoginedAdmin().getUsername() + "_" + getCodFileName();
            } else if (searcher.getPaymentType() != null && searcher.getPaymentType() == 7) {
                titleNames = getWalletExportTitle();
                fileName = this.getLoginedAdmin().getUsername() + "_" + getWalletFileName();
            }
            CSVUtil csvUtil = new CSVUtil();
            csvUtil.setFileName(fileName);
            csvUtil.writeTitleToFile(titleNames);
            // 一次导出500条，如果有的话
            PageResult<Object> pageResult = new PageResult<>(pageModel);
            pageModel.setPageSize(500);
            int pagerNumber = 1;
            int totalCount = 0;
            if (searcher.getPaymentType() != null && searcher.getPaymentType() == 3) {
                totalCount = codCount(searcher);
            } else if (searcher.getPaymentType() != null && searcher.getPaymentType() == 7) {
                totalCount = walletCount(searcher);
            } else {
                totalCount = count(searcher);
            }
            pageResult.setTotalCount(totalCount);
            boolean exportSuccess = true;
            // 在服务器端生产excel文件
            do {
                pageModel.setPageNumber(pagerNumber);
                if (searcher.getPaymentType() != null && searcher.getPaymentType() == 3) {
                    exportSuccess = csvUtil.writeRowToFile(getCodRow(searcher, pageModel));
                } else if (searcher.getPaymentType() != null && searcher.getPaymentType() == 7) {
                    csvUtil.writeRowToFile(getWalletRow(searcher, pageModel));
                } else {
                    csvUtil.writeRowToFile(getRow(searcher, pageModel));
                }
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() && exportSuccess);
            // 返回文件地址
            createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
            // 保存导出记录
            if (searcher.getPaymentType() != null && searcher.getPaymentType() == 3) {
                saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), "codFinance");
            } else if (searcher.getPaymentType() != null && searcher.getPaymentType() == 7) {
                saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), "walletFinance");
            } else {
                saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), "onlineFinance");
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(FinanceDailyAmountSearcher searcher, PageModel page) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        PageResult<FinanceOnlineDailyAmount> pager = financeOnlineDailyAmountService.findOnlineBySearcher(searcher,
                page);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (FinanceOnlineDailyAmount financeOnlineDailyAmount : pager.getList()) {
            Map<String, Object> cellsMap = new HashMap<>();
            cellsMap.put("结算时间", financeOnlineDailyAmount.getCalculateDate() == null ? ""
                    : sf.format(financeOnlineDailyAmount.getCalculateDate()));
            cellsMap.put("下单金额", financeOnlineDailyAmount.getPreAmount());
            cellsMap.put("发货金额", financeOnlineDailyAmount.getDeliveryAmount());
            cellsMap.put("仅退款金额", financeOnlineDailyAmount.getRefundAmount());
            cellsMap.put("退款退货金额", financeOnlineDailyAmount.getReshipAmount());
            cellsMap.put("支付方式", financeOnlineDailyAmount.getPaymentTypeName());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    private List<Map<String, Object>> getCodRow(FinanceDailyAmountSearcher searcher, PageModel page) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        PageResult<FinanceCodDailyAmount> pager = financeCodDailyAmountService.findCodBySearcher(searcher, page);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (FinanceCodDailyAmount financeCodDailyAmount : pager.getList()) {
            Map<String, Object> cellsMap = new HashMap<>();
            cellsMap.put("结算时间", financeCodDailyAmount.getCalculateDate() == null ? ""
                    : sf.format(financeCodDailyAmount.getCalculateDate()));
            cellsMap.put("下单金额", financeCodDailyAmount.getReadyAmount());
            cellsMap.put("已发货金额", financeCodDailyAmount.getDeliveryAmount());
            cellsMap.put("结算金额", financeCodDailyAmount.getBalanceAmount());
            cellsMap.put("退款金额", financeCodDailyAmount.getRefundAmount());
            cellsMap.put("拒收金额", financeCodDailyAmount.getRefuseAmount());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    private List<Map<String, Object>> getWalletRow(FinanceDailyAmountSearcher searcher, PageModel page) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        PageResult<WalletDailyAmount> pager = walletDailyAmountService.findWalletBySearcher(searcher, page);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (WalletDailyAmount walletDailyAmount : pager.getList()) {
            Map<String, Object> cellsMap = new HashMap<>();
            cellsMap.put("结算时间", walletDailyAmount.getCalculateDate() == null ? ""
                    : sf.format(walletDailyAmount.getCalculateDate()));
            cellsMap.put("下单金额本金", walletDailyAmount.getReadyAmount());
            cellsMap.put("下单金额红包", walletDailyAmount.getReadyGiftAmount());
            cellsMap.put("发货金额", walletDailyAmount.getDeliveryAmount());
            cellsMap.put("仅退款本金", walletDailyAmount.getRefundAmount());
            cellsMap.put("仅退款红包", walletDailyAmount.getRefundGiftAmount());
            cellsMap.put("退款退货本金", walletDailyAmount.getReshipAmount());
            cellsMap.put("退款退货红包", walletDailyAmount.getReshipGiftAmount());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "财务在线支付金额分析报表";
    }

    private String getCodFileName() {
        return "财务货到付款金额分析报表";
    }

    private String getWalletFileName() {
        return "财务钱包支付金额分析表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"结算时间", "下单金额", "发货金额", "仅退款金额", "退款退货金额", "支付方式"};
    }

    private String[] getCODExportTitles() {
        return new String[]{"结算时间", "下单金额", "已发货金额", "结算金额", "退款金额", "拒收金额"};
    }

    private String[] getWalletExportTitle() {
        return new String[]{"结算时间", "下单金额本金", "下单金额红包", "发货金额", "仅退款本金", "仅退款红包", "退款退货本金", "退款退货红包"};
    }

    @Override
    protected void saveLog(String fileName, String filePath, long fileSize, String logType) throws NotLoginException {
        ExportLog exportLog = new ExportLog();
        exportLog.setFileName(fileName);
        exportLog.setFilePath(filePath);
        exportLog.setFileSize(fileSize);
        exportLog.setLogType(logType);
        exportLog.setCreator(this.getLoginedAdmin().getUsername());
        exportLogService.insert(exportLog);
    }

    @Override
    protected Response doHelp(FinanceDailyAmountSearcher searcher, PageModel page) {
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

}
