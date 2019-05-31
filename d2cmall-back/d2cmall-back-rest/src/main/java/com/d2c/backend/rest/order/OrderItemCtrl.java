package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.convert.ConvertHelper;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.ExportLog;
import com.d2c.logger.model.RefundLog;
import com.d2c.logger.model.ReshipLog;
import com.d2c.logger.service.ExchangeLogService;
import com.d2c.logger.service.ExportLogService;
import com.d2c.logger.service.RefundLogService;
import com.d2c.logger.service.ReshipLogService;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Admin;
import com.d2c.member.model.CrmGroup;
import com.d2c.member.query.CrmGroupSearcher;
import com.d2c.member.service.CrmGroupService;
import com.d2c.order.dto.OrderExportDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.dto.StoreDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.*;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.*;
import com.d2c.order.support.OrderItemBalanceBean;
import com.d2c.order.support.OrderItemDeliveryBean;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.reponse.KaolaOrderStatus;
import com.d2c.product.model.ProductSkuStock;
import com.d2c.product.service.ProductSkuStockService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/order/orderitem")
public class OrderItemCtrl extends BaseCtrl<OrderSearcher> {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private RefundLogService refundLogService;
    @Autowired
    private ReshipLogService reshipLogService;
    @Autowired
    private ExchangeLogService exchangeLogService;
    @Autowired
    private ExportLogService exportLogService;
    @Autowired
    private OrderReportService orderReportService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private CrmGroupService crmGroupService;

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
    protected List<Map<String, Object>> getRow(OrderSearcher searcher, PageModel page) {
        List<OrderExportDto> list = orderReportService.findBySearch(searcher, page);
        Admin admin = this.getLoginedAdmin();
        if (admin.getIsAccountLocked()) {
            ConvertHelper.convertList(list);
        }
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        Map<Long, String> crmMap = new HashMap<>();
        PageResult<CrmGroup> pager = crmGroupService.findBySearcher(new PageModel(), new CrmGroupSearcher());
        for (CrmGroup crm : pager.getList()) {
            crmMap.put(crm.getId(), crm.getName());
        }
        for (OrderExportDto dto : list) {
            cellsMap = new HashMap<>();
            cellsMap.put("匹配ID", dto.getOrderItemId());
            cellsMap.put("订单编号", dto.getOrderSn());
            cellsMap.put("下单时间", DateUtil.second2str(dto.getOrderCreateDate()));
            cellsMap.put("下单设备", DeviceTypeEnum.valueOf(dto.getDevice()).getDisplay());
            cellsMap.put("订单状态", OrderStatus.getStatus(dto.getOrderStatus()).getName());
            cellsMap.put("订单类型", OrderType.valueOf(dto.getType()).getDisplay());
            cellsMap.put("CRM小组", dto.getCrmGroupId() != null ? crmMap.get(dto.getCrmGroupId()) : "");
            cellsMap.put("客服备注", dto.getAdminMemo());
            cellsMap.put("订单完成时间", dto.getFinishDate() != null ? DateUtil.second2str(dto.getFinishDate()) : "");
            cellsMap.put("支付时间", dto.getOrderPayTime() != null ? DateUtil.second2str(dto.getOrderPayTime()) : "");
            cellsMap.put("支付类型", PaymentTypeEnum.getByCode(dto.getPaymentType()).getDisplay());
            cellsMap.put("支付流水号", dto.getPaymentSn());
            cellsMap.put("支付商户号", dto.getMchId());
            cellsMap.put("商品总额", dto.getTotalAmount());
            cellsMap.put("满减金额", dto.getPromotionAmount());
            cellsMap.put("优惠券金额", dto.getCouponAmount());
            cellsMap.put("红包金额", dto.getRedAmount());
            cellsMap.put("运费金额", dto.getShippingRates());
            cellsMap.put("会员ID", dto.getOrderMemberId());
            cellsMap.put("会员账号", dto.getOrderLoginCode());
            cellsMap.put("会员手机", dto.getMemberMobile());
            cellsMap.put("会员邮箱", dto.getMemberEmail());
            cellsMap.put("收货人", dto.getReciver());
            cellsMap.put("联系方式", dto.getContact());
            cellsMap.put("收货省份", dto.getProvince());
            cellsMap.put("收货城市", dto.getCity());
            cellsMap.put("收货地址", dto.getAddress());
            cellsMap.put("备注", dto.getMemo());
            cellsMap.put("发票", dto.getInvoiced());
            cellsMap.put("运营小组", dto.getOperation());
            cellsMap.put("明细状态", ItemStatus.getItemStatusByName(dto.getOrderItemStatus()).getName());
            cellsMap.put("明细实际支付", dto.getActualAmount());
            cellsMap.put("商品折扣", dto.getOrderitemPromotionAmount());
            cellsMap.put("满减平摊", dto.getOrderPromotionAmount());
            cellsMap.put("优惠券平摊", dto.getOrderitemCoupontAmount());
            cellsMap.put("红包平摊", dto.getOrderitemRedAmount());
            cellsMap.put("钱包本金", dto.getCashAmount());
            cellsMap.put("钱包赠金", dto.getGiftAmount());
            cellsMap.put("门店ID", dto.getStoreId());
            cellsMap.put("售后", dto.getAfter());
            cellsMap.put("预计发货时间", dto.getEstimateDate() != null ? DateUtil.second2str(dto.getEstimateDate()) : "");
            cellsMap.put("发货时间", dto.getDeliveryTime() != null ? DateUtil.second2str(dto.getDeliveryTime()) : "");
            cellsMap.put("物流编号", dto.getDeliverySn());
            cellsMap.put("物流公司", dto.getDeliveryCorpName());
            cellsMap.put("商品款号", dto.getProductSn());
            cellsMap.put("设计师款号", dto.getExternalSn());
            cellsMap.put("商品名称", dto.getProductName());
            cellsMap.put("吊牌价", dto.getOriginalCost());
            cellsMap.put("销售价", dto.getProductPrice());
            cellsMap.put("供货价", dto.getThirdCcyPrice());
            cellsMap.put("数量", dto.getProductQuantity());
            JSONObject category = new JSONObject();
            if (dto.getProductCategory() != null) {
                JSONArray array = JSONArray.parseArray(dto.getProductCategory());
                category = (JSONObject) array.get(array.size() - 1);
            }
            cellsMap.put("品类", category.get("name"));
            cellsMap.put("品牌名", dto.getDesignerName());
            cellsMap.put("商品备注", dto.getRemark());
            cellsMap.put("商品条码", dto.getProductSkuSn());
            cellsMap.put("实发条码", dto.getDeliveryBarCode());
            cellsMap.put("颜色", dto.getSp1Value());
            cellsMap.put("尺码", dto.getSp2Value());
            cellsMap.put("顺丰库存", dto.getSfStock());
            cellsMap.put("门店库存", dto.getStStock());
            cellsMap.put("直接返利比", dto.getPartnerId() == null ? 0 : dto.getPartnerRatio());
            cellsMap.put("团队返利比", dto.getParentId() == null ? 0 : dto.getParentRatio());
            cellsMap.put("间接团队返利比", dto.getSuperId() == null ? 0 : dto.getSuperRatio());
            cellsMap.put("AM返利比", dto.getMasterId() == null ? 0 : dto.getMasterRatio());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(OrderSearcher searcher) {
        return orderReportService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "订单明细列表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{
                "订单状态信息", "匹配ID", "订单编号", "下单时间", "下单设备", "订单状态", "订单类型", "CRM小组", "客服备注", "订单完成时间",
                "订单支付信息", "支付时间", "支付类型", "支付流水号", "支付商户号", "商品总额", "满减金额", "优惠券金额", "红包金额", "运费金额",
                "收货地址信息", "会员ID", "会员账号", "会员手机", "会员邮箱", "收货人", "联系方式", "收货省份", "收货城市", "收货地址", "备注", "发票",
                "明细状态信息", "运营小组", "明细状态", "明细实际支付", "商品折扣", "满减平摊", "优惠券平摊", "红包平摊", "钱包本金", "钱包赠金", "门店ID", "售后",
                "明细发货信息", "预计发货时间", "发货时间", "物流编号", "物流公司",
                "明细商品信息", "商品款号", "设计师款号", "商品名称", "吊牌价", "销售价", "数量", "品类", "品牌名", "商品备注",
                "明细条码信息", "商品条码", "实发条码", "颜色", "尺码", "顺丰库存", "门店库存", "直接返利比", "团队返利比", "间接团队返利比", "AM返利比"
        };
    }

    @Override
    protected Response doHelp(OrderSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(OrderSearcher searcher, PageModel page) {
        searcher.analysisPaymentType();
        searcher.setSplit(false);
        PageResult<OrderItemDto> pager = new PageResult<>(page);
        pager = orderItemService.findPageBySearcher(searcher, page);
        SuccessResponse response = new SuccessResponse(pager);
        Map<String, Object> countMap = orderItemService.getCountsMap(true, null);
        response.setDatas(countMap);
        return response;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "OrderItem";
    }

    /**
     * 订单明细发货
     *
     * @param deliveryCorpName
     * @param deliverySn
     * @param deliveryBarCode
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    public Response doDelivery(String deliveryCorpName, String deliverySn, String deliveryBarCode, Long id,
                               Integer deliveryQuantity) throws Exception {
        Admin admin = this.getLoginedAdmin();
        int status = deliverItem(id, deliveryCorpName, deliverySn, deliveryBarCode, admin.getUsername(),
                deliveryQuantity);
        SuccessResponse result = new SuccessResponse();
        result.put("deliverySn", deliverySn);
        result.put("deliveryCorpName", deliveryCorpName);
        result.put("deliveryBarCode", deliveryBarCode);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        result.put("deliveryDate", dateFormat.format(new Date()));
        result.setStatus(status);
        return result;
    }

    private int deliverItem(Long id, String deliveryCorpName, String deliverySn, String deliveryBarCode,
                            String operator, Integer deliveryQuantity) throws Exception {
        deliverySn = deliverySn.trim();
        OrderItem orderItem = orderItemService.findById(id);
        Order order = this.orderService.findById(orderItem.getOrderId());
        // 正常订单
        OrderStatus orderStatus = OrderStatus.getStatus(order.getOrderStatus());
        int success = -1;
        if ((orderStatus.equals(OrderStatus.WaitingForDelivery) || orderStatus.equals(OrderStatus.Success))
                && orderItem.getStatus().equals(ItemStatus.NORMAL.name())) {
            success = orderItemService.doDeliveryItem(orderItem.getId(), 0, deliveryBarCode, deliveryCorpName,
                    deliverySn, operator, true, deliveryQuantity);
        } else {
            success = orderItemService.updateDeliveryInfo(orderItem.getId(), deliveryCorpName, deliverySn,
                    deliveryBarCode, operator);
        }
        return success;
    }

    /**
     * 订单明细取消发货
     *
     * @param reason
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deliveryCancel", method = RequestMethod.POST)
    public Response cancelDelivery(String reason, Long id) throws Exception {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int flag = orderItemService.doCancelDelivery(id, reason, admin.getUsername());
        if (flag > 0) {
            result.setStatus(1);
            result.setMessage("取消发货成功");
        }
        return result;
    }

    /**
     * 订单明细设计师发货
     *
     * @param id
     * @param status
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/designer/delivery", method = RequestMethod.POST)
    public Response doDesignerDelivery(Long id, Integer status) throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = orderItemService.doDesignerDelivery(id, status, admin.getUsername());
        if (success <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功！");
        }
        OrderItem orderItem = orderItemService.findById(id);
        result.put("orderItem", orderItem);
        return result;
    }

    /**
     * 订单明细门店发货
     *
     * @param itemMemo
     * @param sku
     * @param id
     * @param storeId
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bindStore", method = RequestMethod.POST)
    public Response doStoreDelivery(String itemMemo, String sku, @RequestParam(value = "id", required = true) Long id,
                                    @RequestParam(value = "storeId", required = true) Long storeId)
            throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Store store = storeService.findById(storeId);
        int success = orderItemService.doBindStore(id, itemMemo, sku, storeId, store.getCode(), store.getName(),
                admin.getUsername());
        if (success == 0) {
            result.setStatus(-1);
            result.setMessage("无法指定发货门店！");
        } else {
            result.setMessage("指定门店门店发货成功！");
        }
        return result;
    }

    /**
     * 客服取消门店发货
     *
     * @param id
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/cancelStore/{id}", method = RequestMethod.POST)
    public Response cancelStore(@PathVariable Long id) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem.getStatus().equals(ItemStatus.NORMAL.name())) {
            int success = orderItemService.doCancelStore(id, "客服取消门店发货", admin.getUsername());
            if (success == 0) {
                result.setStatus(-1);
                result.setMessage("取消指定门店发货，不成功！");
            } else {
                result.setMessage("取消指定门店发货成功！");
            }
        } else {
            result.setStatus(-1);
            result.setMessage("订单状态不符，取消门店发货不成功，请进行订单锁定操作！");
        }
        return result;
    }

    /**
     * 结算订单明细
     *
     * @param id
     * @param deliverySn
     * @param balanceMoney
     * @param balanceReason
     * @param signDate
     * @param billNumber
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public Response doBalance(Long id, String deliverySn, BigDecimal balanceMoney, String balanceReason, Date signDate,
                              String billNumber) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (StringUtils.isBlank(deliverySn) || balanceMoney == null || signDate == null) {
            result.setStatus(-1);
            result.setMsg("物流单号，结算金额和签收时间不能为空！");
            return result;
        }
        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem == null) {
            result.setStatus(-1);
            result.setMsg("订单明细不存在，结算不成功！");
            return result;
        }
        if (orderItem.getBalance() == 1) {
            result.setStatus(-1);
            result.setMsg("该订单已结算！");
            return result;
        }
        if (orderItem == null || !deliverySn.trim().equals(orderItem.getDeliverySn().trim())) {
            result.setStatus(-1);
            result.setMsg("该订单物流编号不正确！！");
            return result;
        }
        ItemStatus itemStatus = ItemStatus.getItemStatusByName(orderItem.getStatus());
        if (itemStatus.getCode() != 2 && itemStatus.getCode() != 7 && itemStatus.getCode() != 8
                && itemStatus.getCode() != -2) {
            result.setStatus(-1);
            result.setMsg("该订单状态不正确！");
            return result;
        }
        signDate = (orderItem.getSignDate() != null ? orderItem.getSignDate() : signDate);
        try {
            orderItemService.doBalance(orderItem.getId(), signDate, admin.getUsername(), balanceMoney, balanceReason,
                    billNumber);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage(e.getMessage() + "，系统异常");
            return result;
        }
        result.setMsg("结算成功！");
        return result;
    }

    /**
     * 关闭订单明细（客服不要，或特殊情况无货，客服关闭订单明细）
     *
     * @param id
     * @param closeReason
     * @return
     * @throws IOException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    public Response doClose(@PathVariable Long id, String closeReason) throws IOException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem == null) {
            result.setStatus(-1);
            result.setMessage("订单不存在");
            return result;
        }
        Order order = this.orderService.findById(orderItem.getOrderId());
        if (order.getOrderStatus() == 1
                || ((order.getOrderStatus() <= 4 || order.getOrderStatus() >= 2) && order.getPaymentType() == 3)) {
            try {
                orderItem.setItemCloseReason(closeReason);
                orderItem.setItemCloseMan(admin.getUsername());
                orderItemService.doCloseItem(orderItem.getId(), admin.getUsername(), closeReason);
                result.setMessage("关闭订单明细成功");
            } catch (Exception e) {
                result.setMessage(e.getMessage());
                result.setStatus(-1);
            }
        } else {
            result.setStatus(-1);
            result.setMessage("订单状态为" + order.getOrderStatusName() + "，关闭不成功！");
        }
        return result;
    }

    /**
     * 订单明细锁定
     *
     * @param id
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/lock/{id}", method = RequestMethod.POST)
    public Response doLock(@PathVariable Long id, String info) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(id);
        ItemStatus status = ItemStatus.getItemStatusByName(orderItem.getStatus());
        if (status.equals(OrderItem.ItemStatus.DELIVERED) || status.equals(OrderItem.ItemStatus.SUCCESS)
                || status.getCode() < 0) {
            result.setStatus(-1);
            result.setMessage("订单明细锁定不成功，订单状态已为" + orderItem.getItemStatusName());
        } else {
            if (info == null)
                info = "订单已经锁定，请勿发货！";
            int flag = orderItemService.doLock(orderItem.getId(), info, admin.getUsername());
            if (flag > 0) {
                result.setStatus(1);
                result.setMessage("订单锁定成功");
            }
        }
        return result;
    }

    /**
     * 订单明细取消锁定
     *
     * @param id
     * @param info
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cancellock/{id}", method = RequestMethod.POST)
    public Response cancelLock(@PathVariable Long id, String info) throws Exception {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(id);
        if (orderItem.getLocked() == 1) {
            if (info == null)
                info = "订单已经取消锁定";
            int flag = orderItemService.doCancelLock(orderItem.getId(), info, admin.getUsername());
            if (flag > 0) {
                result.setStatus(1);
                result.setMessage("订单取消锁定成功");
            }
        } else {
            result.setStatus(-1);
            result.setMessage("订单明细取消锁定不成功，订单状态已为" + orderItem.getItemStatusName());
        }
        return result;
    }

    /**
     * 门店即时库存列表
     *
     * @param id
     * @param sku
     * @param isPrimary
     * @return
     */
    @RequestMapping(value = "/stockList/{id}", method = RequestMethod.POST)
    public Response stockList(@PathVariable Long id, @RequestParam(value = "sku", required = true) String sku,
                              boolean isPrimary) {
        List<ProductSkuStock> stockList = productSkuStockService.findBySkuAndStore(sku, isPrimary);
        List<StoreDto> stores = new ArrayList<>();
        for (ProductSkuStock stock : stockList) {
            Store store = storeService.findByCode(stock.getStoreCode());
            if (store == null) {
                continue;
            }
            StoreDto dto = new StoreDto();
            BeanUtils.copyProperties(store, dto, new String[]{});
            dto.setStock(stock.getStock());
            dto.setOccupyStock(stock.getOccupyStock());
            stores.add(dto);
        }
        OrderItem orderItem = orderItemService.findById(id);
        Order order = orderService.findById(orderItem.getOrderId());
        SuccessResponse result = new SuccessResponse(stores);
        result.put("id", id);
        result.put("storeId", orderItem.getStoreId());
        result.put("sku", sku);
        result.put("orderStatus", order.getOrderStatus());
        result.put("itemStatus", orderItem.getStatus());
        return result;
    }

    /**
     * 门店发货订单列表
     *
     * @param queryOrder
     * @param page
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/store/list", method = RequestMethod.POST)
    public Response storeList(OrderSearcher queryOrder, PageModel page) throws NotLoginException {
        this.getLoginedAdmin();
        queryOrder.analysisPaymentType();
        if (queryOrder.getStoreId() == null) {
            // 指定门店
            queryOrder.setStoreId(1L);
        }
        PageResult<OrderItemDto> pager = this.orderItemService.findPageBySearcher(queryOrder, page);
        SuccessResponse result = new SuccessResponse(pager);
        Map<String, Object> countMap = orderItemService.getCountsMap(true, null);
        result.setDatas(countMap);
        return result;
    }

    /**
     * 修改客服备注
     *
     * @param id
     * @param adminMemo
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/updateAdminMemo", method = RequestMethod.POST)
    public Response updateAdminMemo(Long id, String adminMemo) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        orderItemService.updateAdminMemo(id, adminMemo, admin.getUsername());
        result.setMessage("卖家备注修改成功");
        return result;
    }

    /**
     * 修改真实发货条码
     *
     * @param barcode
     * @param id
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/updateDeliveryCode", method = RequestMethod.POST)
    public Response updateDeliveryCode(String barcode, Long id) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = orderItemService.updateBarcodeById(barcode, id, admin.getUsername());
        result.setMessage("操作成功");
        if (success < 1) {
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 修改预计发货时间
     *
     * @param ids
     * @param estimateDate
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/updateEstimateDate", method = RequestMethod.POST)
    public Response updateEstimateDate(Long[] ids, Date estimateDate) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            OrderItem orderItem = orderItemService.findById(id);
            if (orderItem == null || !ItemStatus.NORMAL.name().equals(orderItem.getStatus())) {
                result.setStatus(-1);
                result.setMessage("明细状态不匹配，更新不成功");
                return result;
            }
            try {
                orderItemService.updateEstimateDate(id, estimateDate, admin.getUsername());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        result.setMessage("更新成功");
        return result;
    }

    /**
     * 修改结算金额
     *
     * @param id
     * @param balanceMoney
     * @param balanceReason
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/balance/update", method = RequestMethod.POST)
    public SuccessResponse updateBalnace(Long id, BigDecimal balanceMoney, String balanceReason)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        result.setMsg("修改成功");
        Admin admin = this.getLoginedAdmin();
        orderItemService.updateBalance(id, balanceMoney, admin.getUsername(), balanceReason);
        return result;
    }

    /**
     * 明细售后日志列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/after/log/{id}", method = RequestMethod.GET)
    public Response logList(@PathVariable Long id) {
        SuccessResponse response = new SuccessResponse();
        OrderItem orderItem = orderItemService.findById(id);
        List<RefundLog> refundLogList = new ArrayList<>();
        if (orderItem.getRefundId() != null) {
            refundLogList = refundLogService.findByRefundId(orderItem.getRefundId());
        }
        List<ReshipLog> reshipLogList = new ArrayList<>();
        if (orderItem.getReshipId() != null) {
            reshipLogList = reshipLogService.findByReshipId(orderItem.getReshipId());
        }
        List<ExchangeLog> exchangeLogList = new ArrayList<>();
        if (orderItem.getExchangeId() != null) {
            exchangeLogList = exchangeLogService.findByExchangeId(orderItem.getExchangeId());
        }
        response.put("refundLogList", refundLogList);
        response.put("reshipLogList", reshipLogList);
        response.put("exchangeLogList", exchangeLogList);
        return response;
    }

    /**
     * 导入发货表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delivery/excel/import", method = RequestMethod.POST)
    public Response importDeliver(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        List<OrderItemDeliveryBean> beans = this.getOrderItemDeliveryBeanList(excelData);
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                OrderItemDeliveryBean bean = (OrderItemDeliveryBean) object;
                if (StringUtils.isEmpty(bean.getDeliverySn())) {
                    return false;
                }
                OrderItem orderItem = orderItemService.findByOrderSnAndSku(bean.getOrderSn(), bean.getBarCode());
                if (orderItem == null) {
                    errorMsg.append("单号：" + bean.getOrderSn() + "，SKU:" + bean.getBarCode() + "，物流编号:"
                            + bean.getDeliverySn() + "，错误原因：订单未找到<br/>");
                    return false;
                } else if (orderItem.getRequisition() == 1) {
                    errorMsg.append("单号：" + bean.getOrderSn() + "，SKU:" + bean.getBarCode() + "，物流编号:"
                            + bean.getDeliverySn() + "，错误原因：明细订单处于调拨中 <br/>");
                    return false;
                } else if (orderItem.getRefundId() != null || orderItem.getReshipId() != null) {
                    errorMsg.append("单号：" + bean.getOrderSn() + "，SKU:" + bean.getBarCode() + "，物流编号:"
                            + bean.getDeliverySn() + "，错误原因：明细订单已经申请退款或退货 <br/>");
                    return false;
                } else {
                    OrderItem item = orderItem;
                    if (item.getStatus().equals(ItemStatus.NORMAL.name())) {
                        orderItemService.doDeliveryItem(item.getId(), 0, bean.getBarCode(), bean.getDeliveryCorpName(),
                                bean.getDeliverySn(), admin.getUsername(), true, bean.getDeliveryQuantity());
                        return true;
                    } else if (item.getStatus().equals(ItemStatus.DELIVERED.name())) {
                        errorMsg.append("单号：" + bean.getOrderSn() + "，SKU:" + bean.getBarCode() + "，物流编号:"
                                + bean.getDeliverySn() + "，错误原因：明细订单已经发货 <br/>");
                        return false;
                    } else {
                        errorMsg.append("单号：" + bean.getOrderSn() + "，SKU:" + bean.getBarCode() + "，物流编号:"
                                + bean.getDeliverySn() + "，错误原因：订单状态不匹配 <br/>");
                        return false;
                    }
                }
            }
        });
    }

    private List<OrderItemDeliveryBean> getOrderItemDeliveryBeanList(List<Map<String, Object>> excelData) {
        List<OrderItemDeliveryBean> list = new ArrayList<>();
        OrderItemDeliveryBean bean = null;
        for (Map<String, Object> map : excelData) {
            bean = new OrderItemDeliveryBean();
            bean.setOrderSn(String.valueOf(map.get("订单编号")));
            bean.setBarCode(String.valueOf(map.get("商品SKU条码")));
            bean.setDeliveryCorpName(String.valueOf(map.get("物流公司")));
            bean.setDeliverySn(String.valueOf(map.get("物流单号")));
            bean.setDeliveryQuantity(new BigDecimal(String.valueOf(map.get("实发数量"))).intValue());
            list.add(bean);
        }
        return list;
    }

    /**
     * 导入结算表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/balance/excel/import", method = RequestMethod.POST)
    public Response importBalance(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        List<OrderItemBalanceBean> beans = null;
        try {
            beans = this.getOrderItemBalanceBeanList(excelData);
        } catch (Exception e) {
            return new ErrorResponse("导入数据异常：请删除多余的空行，确认运单号码，代收货款金额和签收日期不能为空。日期格式为 yyyy-MM-dd");
        }
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                OrderItemBalanceBean bean = (OrderItemBalanceBean) object;
                if (StringUtils.isEmpty(bean.getDeliverySn()) || bean.getBalanceMoney() == null) {
                    return false;
                }
                List<OrderItemDto> orderItems = orderItemService.findByDeliverySn(bean.getDeliverySn());
                if (orderItems == null) {
                    errorMsg.append("第" + row + "行，物流编号：" + bean.getDeliverySn() + "，错误原因：明细订单未找到<br/>");
                    return false;
                } else {
                    BigDecimal ordersBalanceAmount = new BigDecimal(0);
                    for (OrderItemDto orderItem : orderItems) {
                        ItemStatus itemStatus = ItemStatus.getItemStatusByName(orderItem.getStatus());
                        if (orderItem.getBalance() == 1) {
                            errorMsg.append("第" + row + "行，物流编号：" + bean.getDeliverySn() + "，错误原因：明细订单已结算，不能重复结算<br/>");
                            return false;
                        } else if (itemStatus.getCode() != 2 && itemStatus.getCode() != 7 && itemStatus.getCode() != 8
                                && itemStatus.getCode() != -2) {
                            errorMsg.append("第" + row + "行，物流编号：" + bean.getDeliverySn() + "，错误原因：明细订单状态不正确<br/>");
                            return false;
                        } else {
                            BigDecimal actualAmount = ((orderItem.getActualAmount() != null
                                    ? orderItem.getActualAmount()
                                    : orderItem.getCodAmount()));
                            ordersBalanceAmount = ordersBalanceAmount.add(actualAmount);
                        }
                    }
                    if (ordersBalanceAmount.compareTo(bean.getBalanceMoney()) != 0) {
                        errorMsg.append("第" + row + "行，物流编号：" + bean.getDeliverySn() + "，错误原因：订单结算金额不正确！<br/>");
                        return false;
                    } else {
                        for (OrderItem orderItem : orderItems) {
                            BigDecimal actualAmount = (orderItem.getCodAmount() == null ? orderItem.getActualAmount()
                                    : orderItem.getCodAmount());
                            Date signDate = (orderItem.getSignDate() != null ? orderItem.getSignDate()
                                    : bean.getSignDate());
                            orderItemService.doBalance(orderItem.getId(), signDate, admin.getUsername(), actualAmount,
                                    bean.getBalanceReason(), bean.getBillNumber());
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    private List<OrderItemBalanceBean> getOrderItemBalanceBeanList(List<Map<String, Object>> excelData)
            throws Exception {
        List<OrderItemBalanceBean> list = new ArrayList<>();
        OrderItemBalanceBean bean = null;
        for (Map<String, Object> map : excelData) {
            bean = new OrderItemBalanceBean();
            bean.setDeliverySn(String.valueOf(map.get("运单号码")));
            bean.setBalanceMoney(new BigDecimal(String.valueOf(map.get("代收货款金额"))));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            bean.setSignDate(df.parse(String.valueOf(map.get("签收日期"))));
            bean.setBillNumber(String.valueOf(map.get("快递账单号")));
            list.add(bean);
        }
        return list;
    }

    /**
     * 明细管易导出
     *
     * @param request
     * @param queryOrder
     * @param page
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/store/excel", method = {RequestMethod.GET, RequestMethod.POST})
    public Response guanyiProductExcel(HttpServletRequest request, OrderSearcher queryOrder, PageModel page)
            throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse response = new SuccessResponse();
        BeanUt.trimString(queryOrder);
        if (queryOrder.getStoreId() == null) {
            // 指定门店
            queryOrder.setStoreId(1L);
        }
        queryOrder.analysisPaymentType();
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        String fileName = this.getLoginedAdmin().getUsername() + "_门店订单";
        String[] titleNames = getStoreOrderItemTableTitleNames();
        ExcelUtil excelUtil = new ExcelUtil(fileName, admin.getUsername());
        boolean exportSuccess = true;
        int pagerNumber = 1;
        Integer totalCount = orderItemService.countBySearcher(queryOrder);
        PageResult<OrderItem> pager = new PageResult<>();
        pager.setTotalCount(totalCount);
        do {
            page.setPageNumber(pagerNumber);
            List<OrderItemDto> list = orderItemService.findBySearcher(queryOrder, page);
            exportSuccess = createExcel(excelUtil, titleNames, getStoreOrderItemRowList(list));
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(response, excelUtil.getExportFileBean());
        if (exportSuccess) {
            saveLog(excelUtil.getExportFileBean().getFileName(), excelUtil.getExportFileBean().getDownloadPath(),
                    excelUtil.getExportFileBean().getFileSize(), "StoreOrder");
        }
        return response;
    }

    private String[] getStoreOrderItemTableTitleNames() {
        return new String[]{"发货日期", "明细状态", "订单编号", "标题", "价格", "购买数量", "外部系统编号", "商品属性", "备注", "门店备注", "门店",
                "客户付款时间", "调拨时间"};
    }

    private List<Map<String, Object>> getStoreOrderItemRowList(List<OrderItemDto> findOrders) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (OrderItemDto orderItem : findOrders) {
            cellsMap = new HashMap<>();
            cellsMap.put("明细状态", orderItem.getItemStatusName());
            cellsMap.put("订单编号", orderItem.getOrderSn());
            cellsMap.put("标题", orderItem.getProductName());
            cellsMap.put("价格", orderItem.getProductPrice());
            cellsMap.put("购买数量", orderItem.getProductQuantity());
            cellsMap.put("外部系统编号", orderItem.getProductSn());
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cellsMap.put("发货日期", orderItem.getDeliveryTime() != null ? fmt.format(orderItem.getDeliveryTime()) : "");
            String color = orderItem.getSp1Value();
            String size = orderItem.getSp2Value();
            if (size != null && (size.equals("均码") || size.equals("均"))) {
                size = "F";
            }
            cellsMap.put("商品属性",
                    "颜色：" + (color != null ? color : "") + ";尺码：" + (size != null ? size.replace("码", "") : ""));
            cellsMap.put("备注", orderItem.getItemMemo());
            cellsMap.put("门店备注", orderItem.getStoreMemo());
            cellsMap.put("门店", orderItem.getStoreId());
            if (orderItem.getBalanceDate() == null) {
                cellsMap.put("客户付款时间", "");
            } else {
                cellsMap.put("客户付款时间", fmt.format(orderItem.getBalanceDate()));
            }
            if (orderItem.getBindDate() != null) {
                cellsMap.put("调拨时间", fmt.format(orderItem.getBindDate()));
            }
            rowList.add(cellsMap);
        }
        return rowList;
    }

    /**
     * 物流查询
     *
     * @param sn
     * @param com
     * @return
     */
    @RequestMapping(value = "/logistics/info", method = RequestMethod.GET)
    public Response expressList(String sn, String com) {
        SuccessResponse result = new SuccessResponse();
        LogisticsCompany logisticsCompany = logisticsService.findCompanyByName(com);
        com = (logisticsCompany != null ? logisticsCompany.getCode() : "other");
        Logistics logistics = logisticsService.findBySnAndCom(sn, com, null);
        JSONObject json = null;
        if (logistics != null) {
            json = (JSONObject) JSON.toJSON(logistics);
            if (!StringUtils.isEmpty(logistics.getDeliveryInfo())) {
                json.put("deliveryInfo", JSON.parse(logistics.getDeliveryInfo(), Feature.OrderedField).toString());
                result.put("logistics", json);
                return result;
            }
        }
        result.setStatus(-1);
        result.setMessage("暂无物流信息");
        return result;
    }

    /**
     * 考拉订单状态查询，结果是与该明细同仓库的整单信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/kaola/info/{id}", method = RequestMethod.GET)
    public Response kaolaInfo(@PathVariable Long id) throws Exception {
        SuccessResponse result = new SuccessResponse();
        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem.getWarehouseId() == null || orderItem.getWarehouseId() == 0) {
            result.setStatus(-1);
            result.setMessage("该订单明细无仓库信息，无法查询考拉的状态！");
            return result;
        }
        KaolaOrderStatus kaolaOrderStatus = KaolaClient.getInstance()
                .queryOrderStatus(orderItem.getOrderSn() + "-" + orderItem.getWarehouseId());
        result.put("kaola", kaolaOrderStatus);
        return result;
    }

}
