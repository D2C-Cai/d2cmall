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
import com.d2c.logger.model.StatementLog;
import com.d2c.logger.service.StatementLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.StatementDto;
import com.d2c.order.model.Statement;
import com.d2c.order.model.StatementItem;
import com.d2c.order.query.StatementSearcher;
import com.d2c.order.service.StatementItemService;
import com.d2c.order.service.StatementService;
import com.d2c.order.support.StatementPayBean;
import com.d2c.util.file.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/order/statement")
public class StatementCtrl extends BaseCtrl<StatementSearcher> {

    @Autowired
    private StatementService statementService;
    @Autowired
    private StatementItemService statementItemService;
    @Autowired
    private StatementLogService statementLogService;

    /**
     * 对账单详情
     *
     * @param statementId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/detail/{statementId}", method = {RequestMethod.GET, RequestMethod.POST})
    public Response detail(@PathVariable("statementId") Long statementId) throws NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        StatementDto dto = statementService.findDtoById(statementId);
        result.put("statement", dto);
        return result;
    }

    /**
     * 接收对账单
     *
     * @param statementId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/receive/{statementId}", method = RequestMethod.POST)
    public Response doReceive(@PathVariable("statementId") Long statementId) throws NotLoginException {
        AdminDto dto = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (dto.getDesignerId() == null) {
            result.setStatus(-1);
            result.setMessage("您没有该权限，请联系客服");
            return result;
        }
        int success = statementService.doReceive(statementId, dto.getDisplayName());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功！");
        }
        return result;
    }

    /**
     * 驳回对账单
     *
     * @param memo
     * @param statementId
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/back/{statementId}", method = RequestMethod.POST)
    public Response doBack(@PathVariable("statementId") Long statementId, String designerMemo)
            throws NotLoginException, BusinessException {
        AdminDto dto = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (dto.getDesignerId() == null) {
            result.setStatus(-1);
            result.setMessage("您没有该权限，请联系客服");
            return result;
        }
        int success = statementService.doBack(statementId, designerMemo, dto.getDisplayName());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功！");
        }
        return result;
    }

    /**
     * 确认对账单
     *
     * @param memo
     * @param statementId
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/confirm/{statementId}", method = RequestMethod.POST)
    public Response doConfirm(String designerMemo, @PathVariable("statementId") Long statementId)
            throws NotLoginException, BusinessException {
        AdminDto dto = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (dto.getDesignerId() == null) {
            result.setStatus(-1);
            result.setMessage("您没有该权限，请联系客服");
            return result;
        }
        int success = statementService.doConfirm(statementId, dto.getDisplayName());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功！");
        }
        return result;
    }

    /**
     * 更新单条明细备注
     *
     * @param model
     * @param memo
     * @param statementId
     * @return
     */
    @RequestMapping(value = "/update/item/memo", method = RequestMethod.POST)
    public Response updateItemMemo(String designerMemo, Long id) throws NotLoginException {
        AdminDto dto = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (dto.getDesignerId() == null) {
            result.setStatus(-1);
            result.setMessage("您没有该权限，请联系客服");
            return result;
        }
        int success = statementItemService.updateDesignerMemo(id, designerMemo, dto.getDisplayName());
        if (success < 0) {
            result.setMessage("操作不成功！");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 更新对账单备注
     *
     * @param model
     * @param memo
     * @param statementId
     * @return
     */
    @RequestMapping(value = "/update/memo", method = RequestMethod.POST)
    public Response updateMemo(String designerMemo, Long id) throws NotLoginException {
        AdminDto dto = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (dto.getDesignerId() == null) {
            result.setStatus(-1);
            result.setMessage("您没有该权限，请联系客服");
            return result;
        }
        int success = statementService.updateDesignerMemo(id, designerMemo, dto.getDisplayName());
        if (success < 0) {
            result.setMessage("操作不成功！");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 操作日志
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response logInfo(@PathVariable("id") Long id, String logType) {
        List<StatementLog> statementLogs = statementLogService.findByStatementId(id, logType);
        return new SuccessResponse(statementLogs);
    }

    /**
     * 文件导出
     *
     * @param id
     * @param page
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/designer/export", method = {RequestMethod.GET, RequestMethod.POST})
    public Response designerExport(HttpServletRequest request, StatementSearcher searcher, PageModel pageModel) {
        SuccessResponse result = new SuccessResponse();
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        try {
            if (searcher.getStatus().length > 0 && searcher.getStatus()[0] == null) {
                searcher.setStatus(null);
            }
            result.setMsg("导出成功！");
            String fileName = dto.getUsername().replace("\\", "_").replace("/", "_") + "_" + getFileName();
            String[] titleNames = getDesignerExportTitles();
            ExcelUtil excelUtil = new ExcelUtil(fileName, dto.getUsername());
            // 一次导出500条，如果有的话
            pageModel.setPageSize(20);
            int pagerNumber = 1;
            PageResult<Object> pageResult = new PageResult<>(pageModel);
            int totalCount = statementService.countBySearcher(searcher);
            pageResult.setTotalCount(totalCount);
            pageResult.setPageCount(totalCount / pageModel.getPageSize());
            boolean exportSuccess = true;
            // 在服务器端生产excel文件
            do {
                pageModel.setPageNumber(pagerNumber);
                exportSuccess = createExcel(excelUtil, titleNames, getDesignerRow(searcher, pageModel));
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() + 1 && exportSuccess);
            // 返回文件地址
            createExcelResult(result, excelUtil.getExportFileBean());
            // 保存导出记录
            if (exportSuccess) {
                saveLog(excelUtil.getExportFileBean().getFileName(), excelUtil.getExportFileBean().getDownloadPath(),
                        excelUtil.getExportFileBean().getFileSize(), "Statement");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    protected Response doList(StatementSearcher searcher, PageModel page) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        if (searcher.getStatus().length > 0 && searcher.getStatus()[0] == null) {
            searcher.setStatus(null);
        }
        PageResult<StatementDto> pager = statementService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(StatementSearcher searcher) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        return statementService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "Statement";
    }

    @Override
    protected List<Map<String, Object>> getRow(StatementSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        if (searcher.getStatus().length > 0 && searcher.getStatus()[0] == null) {
            searcher.setStatus(null);
        }
        PageResult<Statement> pager = statementService.findSimpleBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (Statement statement : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("对账单编号", statement.getSn());
            cellsMap.put("标题", statement.getTitle());
            cellsMap.put("品牌", statement.getDesignerName());
            cellsMap.put("结算件数", statement.getQuantity());
            cellsMap.put("吊牌总金额", statement.getTagAmount());
            cellsMap.put("结算年份", statement.getYear());
            cellsMap.put("结算月份", statement.getMonth());
            cellsMap.put("结算日期", statement.getPeriodOfMonthName());
            cellsMap.put("结算总金额", statement.getSettleAmount());
            cellsMap.put("状态", statement.getStatusName());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    protected List<Map<String, Object>> getDesignerRow(StatementSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<StatementDto> pager = statementService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (StatementDto dto : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("对账单编号", dto.getSn());
            for (StatementItem statementItem : dto.getStatementItems()) {
                Map<String, Object> cellsMapDetail = new HashMap<>();
                cellsMapDetail.putAll(cellsMap);
                cellsMapDetail.put("品牌", statementItem.getDesignerName());
                cellsMapDetail.put("订单编号", statementItem.getOrderSn());
                cellsMapDetail.put("D2C款号", statementItem.getInernalSn());
                cellsMapDetail.put("D2C条码", statementItem.getBarCode());
                cellsMapDetail.put("设计师款号", statementItem.getExternalSn());
                cellsMapDetail.put("设计师条码", statementItem.getExternalCode());
                cellsMapDetail.put("颜色", statementItem.getSp1Value());
                cellsMapDetail.put("尺码", statementItem.getSp2Value());
                cellsMapDetail.put("数量", statementItem.getQuantity());
                cellsMapDetail.put("吊牌价", statementItem.getTagPrice());
                cellsMapDetail.put("结算单价", statementItem.getSettlePrice());
                cellsMapDetail.put("结算金额", statementItem.getSettleAmount());
                cellsMapDetail.put("结算年份", statementItem.getSettleYear());
                cellsMapDetail.put("结算月份", statementItem.getSettleMonth());
                cellsMapDetail.put("结算日", statementItem.getSettleDay());
                cellsMapDetail.put("订单创建日期",
                        statementItem.getOrderItemTime() == null ? "" : sdf.format(statementItem.getOrderItemTime()));
                cellsMapDetail.put("发货日期", statementItem.getTransactionTime() == null ? ""
                        : sdf.format(statementItem.getTransactionTime()));
                cellsMapDetail.put("状态", statementItem.getStatusName());
                cellsMapDetail.put("门店", statementItem.getStoreName());
                cellsMapDetail.put("财务备注", statementItem.getRemark());
                cellsMapDetail.put("来源", statementItem.getTypeName());
                cellsMapDetail.put("关联订单号", statementItem.getRelationSn());
                rowList.add(cellsMapDetail);
            }
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "设计师对账单";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"对账单编号", "标题", "品牌", "结算件数", "结算单价", "吊牌总金额", "结算年份", "结算月份", "结算日", "结算总金额", "状态"};
    }

    protected String[] getDesignerExportTitles() {
        return new String[]{"对账单编号", "订单编号", "品牌", "D2C款号", "D2C条码", "设计师款号", "设计师条码", "颜色", "尺码", "数量", "吊牌价",
                "结算单价", "结算金额", "结算年份", "结算月份", "结算日", "订单创建日期", "发货日期", "状态", "门店", "财务备注", "来源", "关联订单号"};
    }

    @Override
    protected Response doHelp(StatementSearcher searcher, PageModel page) {
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
     * 支付，保存
     *
     * @param payer
     * @param paySn
     * @param payDate
     * @param id
     * @param payBank
     * @param payPic
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/pay/{statementId}", method = RequestMethod.POST)
    public Response doPay(String payer, String paySn, Date payDate, Long id, String payBank, String payPic,
                          BigDecimal payMoney, String adminMemo) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (StringUtils.isBlank(paySn) || payDate == null || StringUtils.isBlank(payer) || payMoney == null) {
            result.setMessage("支付流水号，支付时间，支付金额或支付人不能为空");
            result.setStatus(-1);
            return result;
        }
        int success = statementService.doPay(payer, paySn, payDate, id, payBank, payPic, admin.getUsername(), payMoney,
                adminMemo);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 账单结算
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/balance/{id}", method = RequestMethod.POST)
    public Response doSuccess(@PathVariable("id") Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            int success = statementService.doSuccess(id, admin.getUsername());
            if (success < 1) {
                result.setStatus(-1);
                result.setMessage("结算不成功！");
            }
        } catch (BusinessException e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 更新客服备注
     *
     * @param id
     * @param adminMemo
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/adminMemo", method = RequestMethod.POST)
    public Response updateAdminMemo(Long id, String adminMemo) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = statementService.updateAdminMemo(id, adminMemo, admin.getUsername());
        if (success < 1) {
            result.setMessage("操作不成功");
            result.setStatus(-1);
        }
        return result;
    }

    /***
     * 发送账单
     *
     * @param id
     * @param adminMemo
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response doSend(Long id, String adminMemo) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = statementService.doSend(id, admin.getUsername(), adminMemo);
        if (success < 1) {
            result.setMessage("发送不成功");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 导入支付表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pay/import", method = RequestMethod.POST)
    public Response importPament(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> execelData = this.getExcelData(request);
        List<StatementPayBean> beans = new ArrayList<>();
        try {
            beans = this.getStatementList(execelData);
        } catch (Exception e) {
            return new ErrorResponse("对账编号，支付时间，付款人，支付流水号或收款银行不能为空，支付时间格式为  yyyy-MM-dd");
        }
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                StatementPayBean bean = (StatementPayBean) object;
                Statement statement = statementService.findBySn(bean.getSn());
                if (statement == null) {
                    errorMsg.append("第" + row + "行，编号：" + bean.getSn() + "的对账单不存在" + "<br/>");
                    return false;
                }
                try {
                    statementService.doPay(bean.getPayer(), bean.getPaySn(), bean.getPayDate(), statement.getId(),
                            bean.getPayBank(), null, admin.getUsername(), bean.getPayMoney(), bean.getAdminMemo());
                    return true;
                } catch (Exception e) {
                    errorMsg.append("第" + row + "行，编号：" + statement.getSn() + e.getMessage() + "<br/>");
                    return false;
                }
            }
        });
    }

    private List<StatementPayBean> getStatementList(List<Map<String, Object>> execelData)
            throws BusinessException, Exception {
        List<StatementPayBean> beans = new ArrayList<>();
        for (Map<String, Object> map : execelData) {
            StatementPayBean bean = new StatementPayBean();
            bean.setSn(String.valueOf(map.get("对账单编号")));
            bean.setPayer(String.valueOf(map.get("付款人")));
            bean.setPaySn(String.valueOf(map.get("支付流水号")));
            bean.setPayBank(String.valueOf(map.get("收款银行")));
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            bean.setPayDate(sf.parse(String.valueOf(map.get("支付时间"))));
            bean.setAdminMemo(String.valueOf(map.get("备注")));
            if (map.get("支付金额") == null || map.get("支付金额") == "") {
                throw new BusinessException("第" + (beans.size() + 2) + "行支付金额不能为空");
            }
            bean.setPayMoney(new BigDecimal(String.valueOf(map.get("支付金额"))));
            beans.add(bean);
        }
        return beans;
    }

    /**
     * 申请用款
     *
     * @param flag
     * @param id
     * @param memo
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/apply/{flag}", method = RequestMethod.POST)
    public Response doApply(@PathVariable Integer flag, Long id, String memo) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = statementService.doApply(id, flag, admin.getUsername(), memo);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功！");
        }
        return result;
    }

    /**
     * 撤回对账单
     *
     * @param id
     * @param memo
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/retreat/{id}", method = RequestMethod.POST)
    public Response doRetreat(@PathVariable("id") Long id, String memo) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = statementService.doRetreat(id, memo, admin.getUsername());
        if (success < 1) {
            result.setStatus(-1);
        }
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功！");
        }
        return result;
    }

}
