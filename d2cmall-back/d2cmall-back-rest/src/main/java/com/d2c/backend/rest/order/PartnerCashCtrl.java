package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
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
import com.d2c.order.model.PartnerCash;
import com.d2c.order.query.PartnerCashSearcher;
import com.d2c.order.service.PartnerCashService;
import com.d2c.order.service.tx.PartnerTxService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.CSVUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RestController
@RequestMapping("/rest/order/partnercash")
public class PartnerCashCtrl extends BaseCtrl<PartnerCashSearcher> {

    @Autowired
    private PartnerCashService partnerCashService;
    @Reference
    private PartnerTxService partnerTxService;
    // @Autowired
    // private GongmallConfig gongmallConfig;

    @Override
    protected Response doList(PartnerCashSearcher searcher, PageModel page) {
        PageResult<PartnerCash> pager = partnerCashService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(PartnerCashSearcher searcher) {
        return partnerCashService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "PartnerCash";
    }

    protected String getExportFileType2() {
        return "PartnerCash4FD";
    }

    @Override
    protected List<Map<String, Object>> getRow(PartnerCashSearcher searcher, PageModel page) {
        List<Map<String, Object>> list = new ArrayList<>();
        PageResult<PartnerCash> pager = partnerCashService.findBySearcher(searcher, page);
        for (PartnerCash pc : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("提现单号", pc.getSn());
            map.put("申请账号", pc.getPartnerCode());
            map.put("申请时间", DateUtil.second2str2(pc.getCreateDate()));
            map.put("申请人属性", pc.getPartnerLevel());
            map.put("申请状态", pc.getStatusName());
            map.put("申请金额", pc.getApplyAmount());
            map.put("税后金额", pc.getApplyTaxAmount());
            map.put("真实姓名", pc.getApplyName());
            map.put("身份证", pc.getIdentityCard());
            map.put("支付宝账号", pc.getAlipay());
            map.put("银行", pc.getBankType());
            map.put("银行支行",
                    (pc.getRegion() != null ? pc.getRegion() : "") + (pc.getBank() != null ? pc.getBank() : ""));
            map.put("银行卡号", pc.getBankSn());
            map.put("支付财务", pc.getPayMan());
            map.put("支付时间", DateUtil.second2str2(pc.getPayDate()));
            map.put("支付方式", pc.getPayType());
            map.put("支付账号", pc.getPayAccount());
            map.put("支付金额", pc.getPayAmount());
            map.put("支付流水号", pc.getPaySn());
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "提现单";
    }

    protected String getFileName2() {
        return "提现单财务支付";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"提现单号", "申请账号", "申请时间", "申请人属性", "申请状态", "申请金额", "税后金额", "真实姓名", "身份证", "支付宝账号", "银行",
                "银行支行", "银行卡号", "支付财务", "支付时间", "支付方式", "支付账号", "支付金额", "支付流水号"};
    }

    @Override
    protected Response doHelp(PartnerCashSearcher searcher, PageModel page) {
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

    @RequestMapping(value = "/refuse/{id}", method = RequestMethod.POST)
    public Response refuse(@PathVariable Long id, String refuseReason) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        PartnerCash partnerCash = partnerCashService.findById(id);
        String confirmMan = null;
        if (partnerCash.getStatus() == 0) {
            confirmMan = this.getLoginedAdmin().getUsername();
        }
        partnerTxService.doRefuseCash(id, refuseReason, this.getLoginedAdmin().getUsername(), confirmMan);
        return result;
    }

    @RequestMapping(value = "/pay/{id}", method = RequestMethod.POST)
    public Response pay(@PathVariable Long id, String paySn, String payMan, Date payDate) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        partnerTxService.doPaymentCash(id, paySn, payMan, payDate, admin.getUsername());
        return result;
    }

    @RequestMapping(value = "/agree/{id}", method = RequestMethod.POST)
    public Response agree(@PathVariable Long id, String confirmMan) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        partnerCashService.doAgree(id, confirmMan, admin.getUsername());
        // if (success > 0) {
        // PartnerCash pc = partnerCashService.findById(id);
        // DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // BigDecimal amount = pc.getApplyTaxAmount();
        // JSONObject json =
        // GongmallClient.getInstance().doWithdraw(gongmallConfig, pc.getSn(),
        // pc.getPartnerCode(),
        // pc.getApplyName(), amount, pc.getIdentityCard(), pc.getBankSn(),
        // timeFormat.format(pc.getCreateDate()), "");
        // if (!json.getString("success").equals("true")) {
        // result.setStatus(-1);
        // result.setMessage("工猫提现做单失败！");
        // return result;
        // }
        // }
        return result;
    }

    @RequestMapping(value = "/excel/export", method = RequestMethod.POST)
    public Response excel(HttpServletRequest request, HttpServletResponse response, PartnerCashSearcher searcher,
                          PageModel page, Integer mark) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        BeanUt.trimString(searcher);
        String fileName = "";
        String[] titleNames = null;
        String logType = "";
        if (mark == null || mark != 1) {
            fileName = admin.getUsername() + "_" + getFileName();
            logType = getExportFileType();
            titleNames = getExportTitles();
        } else {
            fileName = admin.getUsername() + "_" + getFileName2();
            logType = getExportFileType2();
            titleNames = getExportTitles();
        }
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(fileName);
        csvUtil.writeTitleToFile(titleNames);
        PageResult<PartnerBill> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        int totalCount = partnerCashService.countBySearcher(searcher);
        pager.setTotalCount(totalCount);
        boolean exportSuccess = true;
        do {
            page.setPageNumber(pagerNumber);
            List<Map<String, Object>> list = null;
            if (mark == null || mark != 1) {
                list = getRow(searcher, page);
            } else {
                list = getRow(searcher, page);
            }
            exportSuccess = csvUtil.writeRowToFile(list);
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        if (exportSuccess) {
            saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), logType);
        }
        return result;
    }

    /**
     * 导入提现表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import", method = RequestMethod.POST)
    public Response importBalance(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String paySn = String.valueOf(System.currentTimeMillis());
                String mobile = "";
                String amount = "";
                String status = "";
                try {
                    mobile = String.valueOf(map.get("手机号码"));
                    amount = String.valueOf(map.get("实发工资"));
                    status = String.valueOf(map.get("发放状态"));
                } catch (Exception e) {
                    errorMsg.append("第" + row + "行" + "，错误原因：数据异常<br/>");
                    return false;
                }
                if (!status.equals("发放成功")) {
                    errorMsg.append("第" + row + "行，错误原因：状态为发送失败<br/>");
                    return false;
                }
                PartnerCash pc = partnerCashService.findActiveByMobile(mobile);
                if (pc == null) {
                    errorMsg.append("第" + row + "行，错误原因：提现单不存在<br/>");
                    return false;
                }
                if (pc.getApplyTaxAmount().compareTo(new BigDecimal(amount)) != 0) {
                    errorMsg.append("第" + row + "行，错误原因：实发工资金额不相等<br/>");
                    return false;
                }
                partnerTxService.doPaymentCash(pc.getId(), paySn, admin.getUsername(), new Date(), admin.getUsername());
                return true;
            }
        });
    }

    /**
     * 导入审批表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import/confirm", method = RequestMethod.POST)
    public Response importConfirm(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String sn = "";
                String confirmMan = "";
                String confirmResult = "";
                String refuseReason = "";
                try {
                    sn = String.valueOf(map.get("提现单号"));
                    confirmMan = String.valueOf(map.get("审批人"));
                    confirmResult = String.valueOf(map.get("审批结果"));
                    refuseReason = map.get("拒绝原因") == null ? "" : String.valueOf(map.get("拒绝原因"));
                } catch (Exception e) {
                    errorMsg.append("第" + row + "行" + "，错误原因：数据异常<br/>");
                    return false;
                }
                if (StringUtils.isBlank(sn) || StringUtils.isBlank(confirmMan) || StringUtils.isBlank(confirmResult)) {
                    errorMsg.append("第" + row + "行，提现单号：" + sn + "，错误原因：审批人，审批结果不能为空<br/>");
                    return false;
                }
                if ("拒绝".equals(confirmResult) && StringUtils.isBlank(refuseReason)) {
                    errorMsg.append("第" + row + "行，提现单号：" + sn + "，错误原因：审核拒绝时，拒绝原因不能空<br/>");
                    return false;
                }
                PartnerCash pc = partnerCashService.findBySn(sn);
                if (pc == null) {
                    errorMsg.append("第" + row + "行，提现单号：" + sn + "，错误原因：提现单不存在<br/>");
                    return false;
                }
                if (0 != pc.getStatus()) {
                    errorMsg.append("第" + row + "行，提现单号：" + sn + "，错误原因：提现单状态异常<br/>");
                    return false;
                }
                if ("通过审核".equals(confirmResult)) {
                    partnerCashService.doAgree(pc.getId(), confirmMan, admin.getUsername());
                } else if ("拒绝".equals(confirmResult)) {
                    partnerTxService.doRefuseCash(pc.getId(), refuseReason, admin.getUsername(), admin.getUsername());
                } else {
                    errorMsg.append("第" + row + "行，提现单号：" + sn + "，错误原因：审核结果不正确（通过审核   或   拒绝）<br/>");
                    return false;
                }
                return true;
            }
        });
    }

    @RequestMapping(value = "/excel/export/gongmall", method = RequestMethod.POST)
    public Response excelForGongmall(HttpServletRequest request, HttpServletResponse response,
                                     PartnerCashSearcher searcher) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        BeanUt.trimString(searcher);
        if (searcher.getStatus() != 0 && searcher.getStatus() != 1) {
            throw new BusinessException("只允许导出待审核或待支付的的提现单");
        }
        String fileName = admin.getUsername() + "_" + "提现信息（工猫）";
        String[] titleNames = new String[]{"流水号", "工号", "姓名", "身份证号码", "银行账户", "手机号码", "开户行信息", "工资", "管理费（2.2%）",
                "个税", "实发工资"};
        String logType = "PartnerCash4Gongmall";
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(fileName);
        csvUtil.writeTitleToFile(titleNames);
        PageModel page = new PageModel();
        PageResult<PartnerBill> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        int totalCount = partnerCashService.countBySearcher(searcher);
        pager.setTotalCount(totalCount);
        boolean exportSuccess = true;
        do {
            page.setPageNumber(pagerNumber);
            List<Map<String, Object>> list = getRow4Gongmall(searcher, page);
            exportSuccess = csvUtil.writeRowToFile(list);
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        if (exportSuccess) {
            saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), logType);
        }
        return result;
    }

    private List<Map<String, Object>> getRow4Gongmall(PartnerCashSearcher searcher, PageModel page) {
        List<Map<String, Object>> list = new ArrayList<>();
        PageResult<PartnerCash> pager = partnerCashService.findBySearcher(searcher, page);
        for (PartnerCash pc : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("流水号", "");
            map.put("工号", "");
            map.put("姓名", pc.getApplyName());
            map.put("身份证号码", pc.getIdentityCard());
            map.put("银行账户", pc.getBankSn());
            map.put("手机号码", pc.getPartnerCode());
            map.put("开户行信息", pc.getBankType() == null ? "" : pc.getBankType());
            map.put("工资", pc.getApplyAmount());
            map.put("管理费（2.2%）", pc.getApplyAmount().multiply(new BigDecimal(0.022)).setScale(2, RoundingMode.HALF_UP));
            map.put("个税", pc.getApplyAmount().subtract(pc.getApplyTaxAmount()));
            map.put("实发工资", pc.getApplyTaxAmount());
            list.add(map);
        }
        return list;
    }

}
