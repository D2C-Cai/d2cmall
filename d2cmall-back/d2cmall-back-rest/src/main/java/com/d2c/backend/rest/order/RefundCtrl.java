package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.OrderLog.OrderLogType;
import com.d2c.logger.model.RefundLog;
import com.d2c.logger.service.RefundLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.dto.RefundDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.Payment;
import com.d2c.order.model.Refund;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.query.RefundSearcher;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderService;
import com.d2c.order.service.PaymentService;
import com.d2c.order.service.RefundService;
import com.d2c.order.service.tx.RefundTxService;
import com.d2c.order.support.RefundConfirmBean;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.enums.CloseReason;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/order/refund")
public class RefundCtrl extends BaseCtrl<RefundSearcher> {

    @Autowired
    private RefundService refundService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private RefundLogService refundLogService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private BrandService brandService;
    @Reference
    private RefundTxService refundTxService;

    @Override
    protected List<Map<String, Object>> getRow(RefundSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        searcher.analysisOrderPayType();
        searcher.analysisPayType();
        PageResult<RefundDto> pager = refundService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("0.00");
        for (RefundDto refund : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("匹配ID", refund.getOrderItemId());
            cellsMap.put("退款编号", refund.getRefundSn());
            cellsMap.put("申请日期", sdf.format(refund.getCreateDate()));
            cellsMap.put("原始订单创建时间", sdf.format(refund.getOrderCreateDate()));
            cellsMap.put("客服审核日期", refund.getAuditDate() == null ? "" : sdf.format(refund.getAuditDate()));
            cellsMap.put("订单编号", refund.getOrderSn());
            cellsMap.put("支付方式", refund.getOrderPayTypeName());
            cellsMap.put("支付流水号", refund.getOrderPaySn());
            cellsMap.put("商品条码", refund.getProductSkuSn());
            cellsMap.put("商品款号", refund.getProductSn());
            cellsMap.put("商品名称", refund.getProductName());
            cellsMap.put("设计师品牌", refund.getDesignerName());
            cellsMap.put("钱包本金", refund.getCashAmount());
            cellsMap.put("钱包赠金", refund.getGiftAmount());
            cellsMap.put("订单备注", refund.getOrderMemo());
            cellsMap.put("退货理由", refund.getRefundReason());
            cellsMap.put("退款理由", refund.getRefundReasonName());
            cellsMap.put("退货数量", refund.getQuantity());
            cellsMap.put("退款状态", refund.getRefundStatusName());
            cellsMap.put("退款方式", refund.getBackAccountType() == null ? ""
                    : PaymentTypeEnum.getByCode(refund.getBackAccountType()).getDisplay());
            cellsMap.put("是否仅退款", refund.getReshipId() == null ? "是" : "否");
            cellsMap.put("退款时间",
                    refund.getPayDate() != null ? sdf.format(refund.getPayDate()) : sdf.format(new Date()));
            cellsMap.put("退款账号", refund.getBackAccountSn() == null ? "" : refund.getBackAccountSn().trim());
            cellsMap.put("账号名称", refund.getBackAccountName() == null ? "" : refund.getBackAccountName().trim());
            cellsMap.put("退款流水号", refund.getPaySn());
            cellsMap.put("应退金额", df.format(refund.getTotalAmount()));
            cellsMap.put("实退金额", refund.getPayMoney() != null ? df.format(refund.getPayMoney()) : "");
            cellsMap.put("退款备注", refund.getMemo());
            cellsMap.put("设备终端", refund.getDeviceName());
            Brand brand = brandService.findById(refund.getDesignerId());
            cellsMap.put("运营小组", brand.getOperation());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(RefundSearcher searcher) {
        return refundService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "退款表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"匹配ID", "退款编号", "申请日期", "原始订单创建时间", "客服审核日期", "订单编号", "支付方式", "支付流水号", "商品条码", "商品款号",
                "商品名称", "设计师品牌", "钱包本金", "钱包赠金", "订单备注", "退货理由", "退款理由", "退货数量", "退款状态", "退款方式", "是否仅退款", "退款时间",
                "退款账号", "账号名称", "退款流水号", "应退金额", "实退金额", "退款备注", "设备终端", "运营小组"};
    }

    @Override
    protected Response doHelp(RefundSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(RefundSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        searcher.analysisOrderPayType();
        searcher.analysisPayType();
        // 店主零售
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getStoreId() != null) {
            searcher.setMemberId(admin.getMemberId());
        }
        PageResult<RefundDto> pager = refundService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Refund refund = JsonUtil.instance().toObject(data, Refund.class);
        Admin admin = this.getLoginedAdmin();
        OrderItem orderItem = orderItemService.findById(refund.getOrderItemId());
        if (orderItem == null) {
            result.setStatus(-1);
            result.setMessage("订单明细不存在！");
            return result;
        }
        if (orderItem.getRefundId() != null) {
            Refund oldRefund = refundService.findById(orderItem.getRefundId());
            if (oldRefund != null && oldRefund.getRefundStatus() > 0) {
                result.setStatus(-1);
                result.setMessage("退款申请未处理完成，不能重复申请");
                return result;
            }
        }
        if (refund.getAllRefund() != 1 && orderItem.getStatus().equals(ItemStatus.NORMAL.name())) {
            result.setStatus(-1);
            result.setMessage("未发货订单明细，必须全额退款");
            return result;
        }
        BigDecimal actualAmount = orderItem.getActualAmount().add(new BigDecimal("10.0"));
        if (actualAmount.compareTo(refund.getApplyAmount()) < 0) {
            result.setStatus(-1);
            result.setMessage(
                    "实际支付金额为" + orderItem.getActualAmount() + "，申请退款金额为" + refund.getApplyAmount() + "，申请不成功，请检查");
            return result;
        }
        Order order = orderService.findById(orderItem.getOrderId());
        try {
            // 对于在已付款状态或者已发货但是只退款的订单，生成退款单
            if (order.getOrderStatus() == OrderStatus.WaitingForDelivery.getCode()
                    || order.getOrderStatus() == OrderStatus.Delivered.getCode()) {
                refund.createRefund(order, orderItem);
                refund.setProxy(1);
                refund.setCreator(admin.getUsername());
                refund.setAuditDate(new Date());
                refund.setAuditor(admin.getUsername());
                refund.setRefundStatus(RefundStatus.WAITFORPAYMENT.getCode());
                refund.setTotalAmount(refund.getApplyAmount());
                Payment pay = paymentService.findById(order.getPaymentId());
                if (pay != null) {
                    refund.setBackAccountSn(pay.getPayer() != null ? pay.getPayer() : pay.getAlipaySn());
                }
                refundService.insert(refund);
                String refundLogInfo = orderItem.getProductSkuSn() + "，申请金额：" + refund.getTotalAmount();
                if (refund.getProxy() == 1) {
                    refundLogInfo = "客服代申请退款：" + refundLogInfo;
                }
                orderItemService.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.AfterSale,
                        admin.getUsername(), refundLogInfo);
                try {
                    JSONObject response = KaolaClient.getInstance().cancelOrder(
                            orderItem.getOrderSn() + "-" + orderItem.getWarehouseId(), CloseReason.Other, "客服代退款");
                    result.put("kaola", response);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            } else if (order.getOrderStatus() == OrderStatus.Success.getCode()) {
                result.setStatus(-1);
                result.setMessage("交易已经完成，申请退款不成功");
            } else {
                result.setStatus(-1);
                result.setMessage("退款不成功");
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        // 重新获取订单明细，更新前台的订单明细状态
        OrderItemDto newOrderItemDto = orderItemService.findOrderItemDtoById(refund.getOrderItemId());
        result.put("aftering", newOrderItemDto.getAfter());
        result.put("itemStatusName", newOrderItemDto.getItemStatusName());
        result.put("afterStatusName", newOrderItemDto.getAfterStatusName());
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "Refund";
    }

    /**
     * 考拉订单同仓库的明细列表
     *
     * @param orderItemId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/kaola/list", method = RequestMethod.GET)
    public ResponseResult kaolaList(Long orderItemId) {
        ResponseResult result = new ResponseResult();
        OrderItem orderItem = orderItemService.findById(orderItemId);
        List<OrderItemDto> list = orderItemService.findDtoByOrderId(orderItem.getOrderId());
        List<OrderItemDto> orderItems = new ArrayList<>();
        for (OrderItemDto item : list) {
            if (orderItem.getWarehouseId() != null && item.getWarehouseId() != null) {
                if (orderItem.getWarehouseId() > 0 && orderItem.getWarehouseId().equals(item.getWarehouseId())) {
                    orderItems.add(item);
                }
            }
        }
        result.put("orderItems", orderItems);
        return result;
    }

    /**
     * 代理申请退款
     *
     * @param orderItemId
     * @return
     */
    @RequestMapping(value = "/create/{orderItemId}", method = RequestMethod.POST)
    public Response createReship(@PathVariable Long orderItemId) {
        SuccessResponse result = new SuccessResponse();
        OrderItem orderItem = orderItemService.findById(orderItemId);
        if (orderItem == null) {
            result.setStatus(-1);
            result.setMessage("订单明细不存在！");
            return result;
        }
        result.put("orderItem", orderItem);
        return result;
    }

    /**
     * 按状态获取退款列表
     *
     * @param searcher
     * @param agreeFlag
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Response refundRefundStatusList(RefundSearcher searcher, String agreeFlag) {
        BeanUt.trimString(searcher);
        if (agreeFlag != null && "1".equals(agreeFlag)) {
            searcher.setRefundStatus(new Integer[]{RefundStatus.WAITFORPAYMENT.getCode()});
        }
        searcher.analysisOrderPayType();
        searcher.analysisPayType();
        PageModel page = new PageModel();
        page.setPageSize(100);
        PageResult<RefundDto> pager = refundService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("agreeFlag", agreeFlag);
        return result;
    }

    /**
     * 客服同意退款
     *
     * @param refundId
     * @param totalAmount
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/agree/{refundId}", method = RequestMethod.POST)
    public Response doCustomerAgree(@PathVariable Long refundId, BigDecimal totalAmount, String info)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            refundService.doCustomerAgree(refundId, totalAmount, admin.getUsername(), info);
            result.setMessage("操作成功！");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 客服拒绝退款
     *
     * @param refundId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/refuse/{refundId}", method = RequestMethod.POST)
    public Response doCustomerRefuse(@PathVariable Long refundId, String info) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        try {
            refundService.doCustomerRefuse(refundId, admin.getUsername(), "客服拒绝：" + info);
        } catch (Exception e) {
            logger.error(e.getMessage());
            ErrorResponse res = new ErrorResponse(e.getMessage());
            return res;
        }
        return new SuccessResponse();
    }

    /**
     * 客服锁定退款
     *
     * @param refundId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/lock/{refundId}", method = RequestMethod.POST)
    public Response doCustomerLock(@PathVariable Long refundId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Refund refund = this.refundService.findById(refundId);
        if (refund.getRefundStatus().equals(RefundStatus.WAITFORPAYMENT.getCode())) {
            int success = this.refundService.doLock(refundId, admin.getUsername());
            if (success > 0) {
                result.setMessage("锁定成功！");
            }
        } else {
            result.setMessage("状态不正确");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 客服取消锁定
     *
     * @param refundId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/cancellock/{refundId}", method = RequestMethod.POST)
    public Response doCustomerCancelLock(@PathVariable Long refundId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Refund refund = this.refundService.findById(refundId);
        if (refund.getLocked() > 0 && refund.getRefundStatus().equals(RefundStatus.WAITFORPAYMENT.getCode())) {
            int success = this.refundService.doCancelLock(refundId, admin.getUsername());
            if (success > 0) {
                result.setMessage("取消锁定成功！");
            }
        } else {
            result.setMessage("状态不正确");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 财务拒绝退款
     *
     * @param refundIds
     * @param info
     * @return
     * @throws NotLoginException
     * @throws ParseException
     * @throws BusinessException
     */
    @RequestMapping(value = "/finance/refuse", method = RequestMethod.POST)
    public Response doFinanceRefuse(String refundIds, String info) throws NotLoginException, BusinessException {
        String[] idArray = refundIds.split(",");
        Admin admin = this.getLoginedAdmin();
        for (int i = 0; i < idArray.length; i++) {
            if (idArray[i] != null && !idArray[i].equals("")) {
                Long refId = Long.valueOf(idArray[i]);
                refundService.doBack(refId, admin.getUsername(), "财务拒绝：" + info);
            }
        }
        return new SuccessResponse();
    }

    /**
     * 退款完成
     *
     * @param refundId
     * @return
     */
    @RequestMapping(value = "/finance/agree/{refundId}", method = RequestMethod.GET)
    public Response doFinanceAgree(@PathVariable Long refundId) {
        SuccessResponse result = new SuccessResponse();
        Refund refund = this.refundService.findById(refundId);
        if (refund.getRefundStatus().equals(RefundStatus.WAITFORPAYMENT.getCode())) {
            result.put("refund", refund);
            result.setStatus(1);
            return result;
        }
        result.setMessage("状态不正确");
        result.setStatus(-1);
        return result;
    }

    /**
     * 第三方支付退款完成
     *
     * @param refund
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/finance/agree/{refundId}", method = RequestMethod.POST)
    public Response doFinanceThirdPay(RefundDto refund) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            Refund oldRefund = refundService.findById(refund.getId());
            if (oldRefund.getPayType() == 7) {
                result.setStatus(-1);
                result.setMessage("钱包支付，不支持手动退款，原来返回到钱包");
                return result;
            }
            refundTxService.doSuccessRefund(refund.getId(), refund.getPayType(), refund.getPayDate(),
                    refund.getPayMoney(), refund.getPaySn(), admin.getUsername(), getLoginIp(),
                    oldRefund.getAllRefund(), admin.getId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setMessage(e.getMessage() + "，退款不成功");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 钱包支付退款完成
     *
     * @param refundId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/finance/agree/wallet/{refundId}", method = RequestMethod.POST)
    public Response doFinanceWalletPay(@PathVariable Long refundId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Refund refund = refundService.findById(refundId);
        try {
            if (refund.getPayType().equals(PaymentTypeEnum.WALLET.getCode())) {
                refundTxService.doSuccessRefundWallet(refund.getId(), admin.getUsername(), getLoginIp(),
                        refund.getAllRefund(), admin.getId());
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage() + "，退款不成功");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 客户要求退回 TODO
     *
     * @param refundIds
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/finance/back", method = RequestMethod.POST)
    public Response doFinanceBack(String refundIds) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        String[] idArray = refundIds.split(",");
        int logs = idArray.length;
        String info = "客户要求退回";
        if (logs == 1) {
            info = "客服要求退回";
        }
        for (int i = 0; i < idArray.length; i++) {
            if (idArray[i] != null && !idArray[i].equals("")) {
                Long refId = Long.valueOf(idArray[i]);
                Refund refund = this.refundService.findById(refId);
                try {
                    if (refund.getLocked() > 0
                            && refund.getRefundStatus().equals(RefundStatus.WAITFORPAYMENT.getCode())) {
                        refundService.doBack(refId, admin.getUsername(), info);
                        result.setMessage("退回成功！");
                        result.setStatus(1);
                    } else {
                        result.setMessage("存在退回不成功的单据！只有待财务退款并且未锁定的单据才能退回！");
                        result.setStatus(-1);
                    }
                } catch (Exception e) {
                    result.setMessage(e.getMessage());
                    result.setStatus(-1);
                }
            }
        }
        return result;
    }

    /**
     * 财务第三方退款失败补偿
     *
     * @param refund
     * @return
     */
    @RequestMapping(value = "/doSuccess", method = RequestMethod.POST)
    public Response doThirdSuccess(Refund refund) {
        SuccessResponse result = new SuccessResponse();
        try {
            refundTxService.doThirdSuccess(refund.getRefundSn(), refund.getPayDate(), refund.getPayMoney(),
                    refund.getPaySn());
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 客服修改退款账户
     *
     * @param refundId
     * @param accountSn
     * @param accountName
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/updateAccountInfo/{refundId}", method = RequestMethod.POST)
    public Response updateBackAccount(@PathVariable Long refundId, String accountSn, String accountName)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            refundService.updateBackAccount(refundId, accountSn, accountName, admin.getUsername());
            result.setMsg("操作成功！");
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 订单操作日志列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.POST)
    public Response logList(@PathVariable Long id) {
        List<RefundLog> logList = refundLogService.findByRefundId(id);
        return new SuccessResponse(logList);
    }

    /**
     * 导入退款表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import", method = RequestMethod.POST)
    public Response importRefund(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        List<RefundConfirmBean> beans = this.getRefundConfirmBeanList(excelData);
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                RefundConfirmBean bean = (RefundConfirmBean) object;
                Refund refund = refundService.findByRefundSn(bean.getRefundSn());
                if (refund == null) {
                    errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "，订单未找到 <br/>");
                    return false;
                }
                if (refund.getOrderPayType() == 7) {
                    errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "，钱包支付，不支持导入退款 <br/>");
                    return false;
                }
                if (refund.getRefundStatus().equals(RefundStatus.WAITFORPAYMENT.getCode())) {
                    if (StringUtils.isBlank(refund.getBackAccountSn())
                            || StringUtils.isBlank(refund.getBackAccountName())
                            || StringUtils.isBlank(bean.getBackAccountSn())
                            || StringUtils.isBlank(bean.getBackAccountName())
                            || StringUtils.isBlank(refund.getOrderSn())) {
                        errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "与数据有误，退款账号及名称不能为空 <br/>");
                        return false;
                    }
                    if (!refund.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                            .equals(bean.getPayMoney().setScale(2, BigDecimal.ROUND_HALF_UP))) {
                        errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "，退款金额不一致，请检查 <br/>");
                        return false;
                    }
                    if (!refund.getOrderSn().trim().equalsIgnoreCase(bean.getOrderSn().trim())) {
                        errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "，订单编号不一致，请检查 <br/>");
                        return false;
                    }
                    if (!refund.getBackAccountSn().trim().equalsIgnoreCase(bean.getBackAccountSn().trim())) {
                        errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "，退款账号不一致，请检查 <br/>");
                        return false;
                    }
                    if (!refund.getBackAccountName().trim().equalsIgnoreCase(bean.getBackAccountName().trim())) {
                        errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "，退款账号名称不一致，请检查 <br/>");
                        return false;
                    }
                    refund.setPayType(refund.getOrderPayType());
                    refund.setPayDate(bean.getPayDate());
                    refund.setPaySn(bean.getPaySn());
                    refund.setPayMoney(bean.getPayMoney());
                    try {
                        refundTxService.doSuccessRefund(refund.getId(), refund.getPayType(), refund.getPayDate(),
                                refund.getPayMoney(), refund.getPaySn(), admin.getUsername(), getLoginIp(),
                                refund.getAllRefund(), admin.getId());
                        return true;
                    } catch (Exception e) {
                        errorMsg.append(
                                "第" + row + "行，退款单号：" + bean.getRefundSn() + "，退款错误：" + e.getMessage() + "<br/>");
                        return false;
                    }
                } else {
                    errorMsg.append("第" + row + "行，退款单号：" + bean.getRefundSn() + "，状态不匹配，当前状态为："
                            + refund.getRefundStatusName() + "<br/>");
                    return false;
                }
            }
        });
    }

    private List<RefundConfirmBean> getRefundConfirmBeanList(List<Map<String, Object>> excelData)
            throws BusinessException {
        List<RefundConfirmBean> list = new ArrayList<>();
        RefundConfirmBean bean = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数
        for (Map<String, Object> map : excelData) {
            bean = new RefundConfirmBean();
            Object refundSn = map.get("退款编号");
            if (refundSn == null || refundSn.equals("")) {
                continue;
            }
            bean.setRefundSn(String.valueOf(refundSn));
            bean.setOrderSn(String.valueOf(map.get("订单编号")));
            bean.setBackAccountName(String.valueOf(map.get("账号名称")));
            bean.setBackAccountSn(String.valueOf(map.get("退款账号")));
            try {
                if (map.get("退款时间") != null) {
                    bean.setPayDate(sdf.parse(String.valueOf(map.get("退款时间"))));
                } else {
                    bean.setPayDate(new Date());
                }
            } catch (Exception e) {
                throw new BusinessException("第" + (list.size() + 2) + "行，日期格式转换有误，请检查");
            }
            bean.setPaySn(String.valueOf(map.get("退款流水号")));
            if (map.get("实退金额") != null) {
                String backMoney = map.get("实退金额").toString();
                BigDecimal payMoney = NumberUtils.parseNumber(backMoney, BigDecimal.class);
                bean.setPayMoney(payMoney);
            }
            list.add(bean);
        }
        return list;
    }

}
