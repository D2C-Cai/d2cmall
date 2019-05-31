package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.OrderLog.OrderLogType;
import com.d2c.logger.model.ReshipLog;
import com.d2c.logger.service.ReshipLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.ReshipDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.*;
import com.d2c.order.model.OrderItem.BusType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.model.Reship.ReshipStatus;
import com.d2c.order.query.ReshipSearcher;
import com.d2c.order.service.*;
import com.d2c.product.model.Brand;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/order/reship")
public class ReshipCtrl extends BaseCtrl<ReshipSearcher> {

    @Autowired
    private ReshipService reshipService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ReshipLogService reshipLogService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SettingService settingService;

    @Override
    protected List<Map<String, Object>> getRow(ReshipSearcher searcher, PageModel page) {
        PageResult<ReshipDto> pager = reshipService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ReshipDto reship : pager.getList()) {
            cellsMap = new HashMap<>();
            Brand brand = brandService.findById(reship.getDesignerId());
            OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
            cellsMap.put("匹配ID", reship.getOrderItemId());
            cellsMap.put("退货编号", reship.getReshipSn());
            cellsMap.put("申请日期", sdf.format(reship.getCreateDate()));
            cellsMap.put("退货状态", reship.getReshipStatusName());
            cellsMap.put("退货人", reship.getMemberName());
            cellsMap.put("退货理由", reship.getReshipReasonName());
            cellsMap.put("客服审核金额", reship.getTradeAmount());
            cellsMap.put("申请退货数量", reship.getQuantity());
            cellsMap.put("实际退货数量", reship.getActualQuantity());
            cellsMap.put("订单编号", reship.getOrderSn());
            cellsMap.put("支付方式", PaymentTypeEnum.getByCode(reship.getOrderPayType()).getDisplay());
            cellsMap.put("商品货号", reship.getProductSn());
            cellsMap.put("设计师货号", reship.getExternalSn());
            cellsMap.put("商品条码", reship.getProductSkuSn());
            cellsMap.put("商品名称", reship.getProductName());
            cellsMap.put("设计师品牌", reship.getDesignerName());
            cellsMap.put("物流公司", reship.getDeliveryCorpName());
            cellsMap.put("物流编号", reship.getDeliverySn());
            cellsMap.put("寄件人", reship.getSender());
            cellsMap.put("联系方式", reship.getMobile());
            cellsMap.put("退货备注", reship.getMemo());
            cellsMap.put("运营小组", brand.getOperation());
            cellsMap.put("订单发货时间", DateUtil.second2str(orderItem.getDeliveryTime()));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "退货表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"匹配ID", "退货编号", "申请日期", "退货状态", "退货人", "退货理由", "客服审核金额", "申请退货数量", "实际退货数量", "订单编号",
                "支付方式", "订单发货时间", "商品货号", "设计师货号", "商品条码", "商品名称", "设计师品牌", "物流公司", "物流编号", "寄件人", "联系方式", "退货备注",
                "运营小组"};
    }

    @Override
    protected Response doHelp(ReshipSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ReshipSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        searcher.analysisOrderPayType();
        page.setPageSize(20);
        // 店主零售
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getStoreId() != null) {
            searcher.setMemberId(admin.getMemberId());
        }
        PageResult<ReshipDto> pr = reshipService.findBySearch(searcher, page);
        SuccessResponse sr = new SuccessResponse(pr);
        return sr;
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
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        BigDecimal applyAmount = new BigDecimal(data.getDouble("applyAmount")).setScale(2, BigDecimal.ROUND_HALF_UP);
        Reship reship = JsonUtil.instance().toObject(data, Reship.class);
        Admin admin = this.getLoginedAdmin();
        OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
        if (orderItem.getReshipId() != null) {
            Reship oldReship = this.reshipService.findById(orderItem.getReshipId());
            if (oldReship != null && oldReship.getReshipStatus() > 0) {
                result.setStatus(-1);
                result.setMessage("退货申请未处理完成，不能重复申请");
                return result;
            }
        }
        if (orderItem.getRefundId() != null) {
            Refund oldRefund = this.refundService.findById(orderItem.getRefundId());
            if (oldRefund != null && oldRefund.getRefundStatus() > 0) {
                result.setStatus(-1);
                result.setMessage("退款申请未处理完成，不能重复申请");
                return result;
            }
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (ItemStatus.DELIVERED.name().equals(orderItem.getStatus())
                || ItemStatus.SIGNED.name().equals(orderItem.getStatus())
                || ItemStatus.SUCCESS.name().equals(orderItem.getStatus())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getCreateDate());
            calendar.add(Calendar.DAY_OF_WEEK, -2);
            if (new Date().after(calendar.getTime())) {
                ProductSku sku = productSkuService.findById(orderItem.getProductSkuId());
                if (sku != null) {
                    reship.createReship(order, orderItem);
                    reship.setProxy(1);
                    reship.setCreator(admin.getUsername());
                    reship.setAuditor(admin.getUsername());
                    reship.setAuditDate(new Date());
                    reship.setReshipStatus(ReshipStatus.APPROVE.getCode());
                    // 设计师直发退换货地址
                    if (orderItem.getDeliveryType() == 1) {
                        Brand brand = brandService.findById(orderItem.getDesignerId());
                        reship.setBackAddress(brand.getAddress());
                        reship.setBackMobile(brand.getMobile());
                        reship.setBackConsignee(brand.getConsignee());
                    }
                    Setting setting = settingService.findByCode(Setting.RESHIPCLOSEDATE);
                    Date deliveryExpiredDate = null;
                    if (setting != null && setting.getStatus() == 1 && reship.isReceived()) {
                        Integer limitDay = Integer.parseInt(Setting.defaultValue(setting, new Integer(7)).toString());
                        deliveryExpiredDate = DateUtil.getIntervalDay(new Date(), limitDay);
                    }
                    reship.setDeliveryExpiredDate(deliveryExpiredDate);
                    try {
                        reship = reshipService.insert(reship);
                        orderItemService.createOrderLog(orderItem.getOrderId(), orderItem.getId(),
                                OrderLogType.AfterSale, admin.getUsername(), "客服代申请退货：" + orderItem.getProductSkuSn()
                                        + "，金额：" + (applyAmount != null ? applyAmount : orderItem.getProductPrice()));
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        result.setStatus(-1);
                        result.setMessage(e.getMessage());
                        return result;
                    }
                }
            } else {
                result.setStatus(-1);
                result.setMessage("订单过期，不能退货");
                return result;
            }
        } else {
            result.setStatus(-1);
            result.setMessage("物流单未发货，申请退款退货不成功");
            return result;
        }
        if (orderItem.getBusType() != null && orderItem.getBusType().equals(BusType.DPLUS.name())) {
            this.receivedReship(reship.getId(), "店主零售，无需退货，自动收货");
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ReshipSearcher searcher) {
        return reshipService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "Reship";
    }

    /**
     * 代客户填写物流信息
     *
     * @param reshipId
     * @return
     */
    @RequestMapping(value = "/logistic/{reshipId}", method = RequestMethod.GET)
    public Response logistic(@PathVariable Long reshipId) {
        Reship reship = reshipService.findById(reshipId);
        SuccessResponse result = new SuccessResponse();
        if (reship != null && !reship.getReshipStatus().equals(ReshipStatus.APPROVE.getCode())) {
            result.setMessage("退货申请状态不正确，无法填写相关物流信息");
            return result;
        }
        result.put("reship", reship);
        return result;
    }

    /**
     * 代客户填写发货信息
     *
     * @param reshipId
     * @param deliveryCorpName
     * @param deliverySn
     * @param memo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logistic/{reshipId}", method = RequestMethod.POST)
    public Response doLogistic(@PathVariable Long reshipId, String deliveryCorpName, String deliverySn, String memo)
            throws Exception {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int reValue = reshipService.doLogistic(reshipId, deliverySn, deliveryCorpName, memo, admin.getUsername());
        if (reValue > 0) {
            result.setStatus(1);
            result.setMessage("物流信息提交成功！");
        } else {
            result.setStatus(-1);
            result.setMessage("物流信息不成功！");
        }
        return result;
    }

    /**
     * 新建 申请退货
     *
     * @param orderItemId
     * @return
     */
    @RequestMapping(value = "/create/{orderItemId}", method = RequestMethod.GET)
    public Response createReship(@PathVariable Long orderItemId) {
        SuccessResponse result = new SuccessResponse();
        OrderItem orderItem = orderItemService.findById(orderItemId);
        if (orderItem == null) {
            result.setMessage("订单明细不存在！");
            result.setStatus(-1);
            return result;
        }
        result.put("orderItem", orderItem);
        return result;
    }

    /**
     * 客服同意退货
     *
     * @param reshipId
     * @param totalAmount
     * @param info
     * @param backAddress
     * @param backConsignee
     * @param backMobile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/customer/agree/{reshipId}", method = RequestMethod.POST)
    public Response doCustomerAgree(@PathVariable Long reshipId, BigDecimal totalAmount, String info,
                                    String backAddress, String backConsignee, String backMobile) throws Exception {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (StringUtils.isBlank(backAddress) || StringUtils.isBlank(backConsignee) || StringUtils.isBlank(backMobile)) {
            result.setMsg("请完整填写退货信息");
            result.setStatus(-1);
        } else {
            Reship reship = this.reshipService.findById(reshipId);
            if (reship != null) {
                try {
                    int success = reshipService.doCustomerAgree(reship.getId(), totalAmount, admin.getUsername(), info,
                            getLoginIp(), backAddress, backConsignee, backMobile);
                    if (success > 0 && reship.isReceived()) {
                        if ((reship.getBackAddress() != null && !reship.getBackAddress().equals(backAddress))
                                || (reship.getBackConsignee() != null
                                && !reship.getBackConsignee().equals(backConsignee))
                                || (reship.getBackMobile() != null && !reship.getBackMobile().equals(backMobile))) {
                            reshipService.updateBackAddress(reshipId, backAddress, backConsignee, backMobile);
                        } else {
                            reshipService.updateBackAddress(reshipId, backAddress, backConsignee, backMobile);
                        }
                    }
                    result.setMsg("审核成功");
                } catch (Exception e) {
                    result.setMsg(e.getMessage());
                    result.setStatus(-1);
                }
            }
        }
        return result;
    }

    /**
     * 仓库确认收货
     *
     * @param reshipId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/reshipReceived/{reshipId}", method = RequestMethod.POST)
    public Response receivedReship(@PathVariable Long reshipId, String info) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            Reship reship = reshipService.findById(reshipId);
            Order order = orderService.findById(reship.getOrderId());
            Refund refund = new Refund();
            refund.createRefund(reship, order.getPaymentType());
            refund.setOrderPaySn(order.getPaymentSn());
            refund.setOrderPaymentId(order.getPaymentId());
            refund.setOrderCreateDate(order.getCreateDate());
            refund.setOrderMemo(order.getMemo());
            refund.setAuditor(admin.getUsername());
            refund.setAuditDate(new Date());
            refund.setMemberName(order.getMemberMobile());
            refund.setRefundStatus(RefundStatus.WAITFORPAYMENT.getCode());
            refund.setCreator(reship.getCreator());
            refund.setDevice(reship.getDevice());
            refund.setAppVersion(reship.getAppVersion());
            refund.setApplyAmount(reship.getTradeAmount());
            refund.setTotalAmount(reship.getTradeAmount());
            refund.setProxy(reship.getProxy());
            refund.setBackAccountType(order.getPaymentType());
            refund.setBackAccountName(order.getPaymentSn());
            Payment pay = paymentService.findById(order.getPaymentId());
            if (pay != null) {
                refund.setBackAccountSn(pay.getPayer() != null ? pay.getPayer() : pay.getAlipaySn());
            }
            reshipService.doStoreAgree(reshipId, admin.getUsername(), info, refund);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 仓库批量确认收货
     *
     * @param page
     * @param searcher
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/batchReceived", method = RequestMethod.POST)
    public Response batchReceived(PageModel page, ReshipSearcher searcher) throws NotLoginException {
        this.getLoginedAdmin();
        BeanUt.trimString(searcher);
        PageResult<ReshipDto> pager = reshipService.findBySearch(searcher, page);
        List<Long> reshipIds = new ArrayList<>();
        for (Reship reship : pager.getList()) {
            if (reship != null && reship.getReshipStatus().equals(ReshipStatus.WAITFORRECEIVE.getCode())) {
                reshipIds.add(reship.getId());
            }
        }
        for (Long reshipId : reshipIds) {
            this.receivedReship(reshipId, null);
        }
        return this.reshipStatusList(page, searcher, "0");
    }

    /**
     * 客服拒绝退货
     *
     * @param reshipId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/refuse/{reshipId}", method = RequestMethod.POST)
    public Response reshipRefuse(@PathVariable Long reshipId, String info) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        try {
            reshipService.doCustomerRefuse(reshipId, admin.getUsername(), info);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new SuccessResponse();
    }

    /**
     * 客服代客户关闭申请退货
     *
     * @param reshipId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/close/{reshipId}", method = RequestMethod.POST)
    public Response reshipClose(@PathVariable Long reshipId, String info) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        Response resp = null;
        try {
            int result = reshipService.doCustomerClose(reshipId, admin.getUsername(), info);
            if (result > 0) {
                resp = new SuccessResponse();
                resp.setMessage("客服代客户关闭申请退货成功");
            } else {
                resp = new ErrorResponse("仓库拒绝退货不成功");
            }
            return resp;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 仓库拒绝退货
     *
     * @param reshipId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/store/refuse/{reshipId}", method = RequestMethod.POST)
    public Response refuseReship(@PathVariable Long reshipId, String info) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        try {
            int result = reshipService.doStoreRefuse(reshipId, admin.getUsername(), info);
            if (result > 0) {
                return new SuccessResponse("", "仓库拒绝退货成功");
            } else {
                return new ErrorResponse("仓库拒绝退货不成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 按状态获取退货单
     *
     * @param page
     * @param searcher
     * @param agreeFlag
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Response reshipStatusList(PageModel page, ReshipSearcher searcher, String agreeFlag) {
        BeanUt.trimString(searcher);
        if ("1".equals(agreeFlag)) {
            searcher.setReshipStatus(
                    new Integer[]{ReshipStatus.APPROVE.getCode(), ReshipStatus.WAITFORRECEIVE.getCode()});
        }
        PageResult<ReshipDto> pager = reshipService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
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
        List<ReshipLog> logList = reshipLogService.findByReshipId(id);
        return new SuccessResponse(logList);
    }

}
