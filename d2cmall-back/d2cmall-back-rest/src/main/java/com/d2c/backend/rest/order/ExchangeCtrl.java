package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.OrderLog.OrderLogType;
import com.d2c.logger.service.ExchangeLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.ExchangeDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.*;
import com.d2c.order.model.Exchange.ExchangeStatus;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.query.ExchangeSearcher;
import com.d2c.order.service.*;
import com.d2c.order.support.ExchangeDeliveryBean;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/rest/order/exchange")
public class ExchangeCtrl extends BaseCtrl<ExchangeSearcher> {

    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private ExchangeLogService exchangeLogService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private PaymentService paymentService;

    @Override
    protected List<Map<String, Object>> getRow(ExchangeSearcher searcher, PageModel page) {
        searcher.analysisStatus();
        PageResult<ExchangeDto> pager = exchangeService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ExchangeDto exchange : pager.getList()) {
            Order order = orderService.findById(exchange.getOrderId());
            Brand brand = brandService.findById(exchange.getDesignerId());
            Product product = productService.findById(exchange.getProductId());
            cellsMap = new HashMap<>();
            cellsMap.put("匹配ID", exchange.getOrderItemId());
            cellsMap.put("换货编号", exchange.getExchangeSn());
            cellsMap.put("申请日期", sdf.format(exchange.getCreateDate()));
            cellsMap.put("换货状态", ExchangeStatus.getStatus(exchange.getExchangeStatus()).getName());
            cellsMap.put("换货人", exchange.getLoginCode());
            cellsMap.put("换货理由", exchange.getExchangeReasonName());
            cellsMap.put("订单编号", exchange.getOrderSn());
            cellsMap.put("商品名称", exchange.getOldProductName());
            cellsMap.put("商品货号", exchange.getOldProductSn());
            cellsMap.put("设计师货号", exchange.getExternalSn());
            cellsMap.put("设计师品牌", exchange.getDesignerName());
            cellsMap.put("运营小组", brand.getOperation());
            cellsMap.put("老商品条码", exchange.getOldSkuSn());
            cellsMap.put("需换商品条码", exchange.getSkuSn());
            cellsMap.put("颜色", exchange.getSp1());
            cellsMap.put("尺码", exchange.getSp2());
            JSONObject category = new JSONObject();
            if (product != null && product.getProductCategory() != null) {
                JSONArray array = JSONArray.parseArray(product.getProductCategory());
                category = (JSONObject) array.get(array.size() - 1);
            }
            cellsMap.put("类别", category.get("name"));
            cellsMap.put("购买数量", exchange.getQuantity());
            cellsMap.put("物流公司", exchange.getDeliveryCorp());
            cellsMap.put("物流编号", exchange.getDeliverySn());
            cellsMap.put("寄件人", exchange.getSender());
            cellsMap.put("联系方式", exchange.getMobile());
            cellsMap.put("客户备注", exchange.getMemo());
            cellsMap.put("买家地址", order.getAddress());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(ExchangeSearcher searcher) {
        return exchangeService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "换货表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"匹配ID", "换货编号", "申请日期", "换货状态", "换货人", "换货理由", "订单编号", "商品名称", "商品货号", "设计师货号", "设计师品牌",
                "运营小组", "老商品条码", "需换商品条码", "颜色", "尺码", "类别", "购买数量", "物流公司", "物流编号", "寄件人", "联系方式", "买家地址", "客户备注"};
    }

    @Override
    protected Response doHelp(ExchangeSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ExchangeSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        searcher.analysisStatus();
        // 店主零售
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getStoreId() != null) {
            searcher.setMemberId(admin.getMemberId());
        }
        PageResult<ExchangeDto> pager = exchangeService.findBySearch(searcher, page);
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
    protected Response doInsert(JSONObject data) {
        Exchange exchange = JsonUtil.instance().toObject(data, Exchange.class);
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(exchange.getOrderItemId());
        if (!orderItem.getAftering().equals("none")) {
            result.setStatus(-1);
            result.setMessage("售后申请处理中或者已经完成，不能重复申请");
            return result;
        }
        Order order = orderService.findById(orderItem.getOrderId());
        try {
            // 对于在已发货状态且发货至今未超过一周的订单，生成换货单
            if (ItemStatus.DELIVERED.name().equals(orderItem.getStatus())
                    || ItemStatus.SIGNED.name().equals(orderItem.getStatus())) {
                Date date = orderItem.getSignDate();
                if (date != null) {
                    date = DateUtil.add(date, Calendar.DAY_OF_YEAR, -10);
                } else {
                    date = orderItem.getDeliveryTime();
                    date = DateUtil.add(date, Calendar.DAY_OF_YEAR, -15);
                }
                if (new Date().after(date)) {
                    ProductSku sku = productSkuService.findById(orderItem.getProductSkuId());
                    if (sku != null) {
                        exchange.createExchange(order, orderItem);
                        exchange.setProxy(1);
                        exchange.setCreator(admin.getUsername());
                        exchange.setAuditor(admin.getUsername());
                        exchange.setAuditDate(new Date());
                        exchange.setExchangeStatus(ExchangeStatus.APPROVE.getCode());
                        // 设计师直发退换货地址
                        if (orderItem.getDeliveryType() == 1) {
                            Brand brand = brandService.findById(orderItem.getDesignerId());
                            exchange.setBackAddress(brand.getAddress());
                            exchange.setBackMobile(brand.getMobile());
                            exchange.setBackConsignee(brand.getConsignee());
                        }
                        exchangeService.insert(exchange, admin.getUsername());
                    }
                }
            } else {
                result.setMessage("只有已发货的订单才能发起换货业务，提交不成功");
                result.setStatus(-1);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "Exchange";
    }

    /**
     * 代客户申请换货
     *
     * @param orderItemId
     * @param model
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/edit/{orderItemId}", method = RequestMethod.GET)
    public Response createExchange(@PathVariable Long orderItemId) throws NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(orderItemId);
        if (!orderItem.getAftering().equals("none")) {
            result.setStatus(-1);
            result.setMessage("售后申请处理中或者已经完成，不能重复申请");
            return result;
        }
        Order order = orderService.findById(orderItem.getOrderId());
        Exchange exchange = new Exchange();
        exchange.createExchange(order, orderItem);
        result.put("orderItem", orderItem);
        result.put("exchange", exchange);
        return result;
    }

    /**
     * 客服同意换货
     *
     * @param model
     * @param exchangeId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/agree/{exchangeId}", method = RequestMethod.POST)
    public Response agreeExchange(@PathVariable Long exchangeId, String info, BigDecimal exchangePrice,
                                  String backAddress, String backConsignee, String backMobile) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (StringUtils.isBlank(backAddress) || StringUtils.isBlank(backConsignee) || StringUtils.isBlank(backMobile)) {
            result.setMsg("请完整填写退货信息");
            result.setStatus(-1);
        } else {
            Exchange exchange = exchangeService.findById(exchangeId);
            if (exchange != null) {
                try {
                    int success = exchangeService.doCustomerAgree(exchangeId, admin.getUsername(), info, exchangePrice,
                            getLoginIp(), backAddress, backConsignee, backMobile);
                    if (success > 0) {
                        if (!StringUtils.isEmpty(exchange.getBackAddress())) {
                            if (!exchange.getBackAddress().equals(backAddress)
                                    || !exchange.getBackConsignee().equals(backConsignee)
                                    || !exchange.getBackMobile().equals(backMobile)) {
                                exchangeService.updateBackAddress(exchangeId, backAddress, backMobile, backConsignee);
                            }
                        }
                    }
                    result.setMessage("审核成功");
                } catch (Exception e) {
                    result.setMessage(e.getMessage());
                    result.setStatus(-1);
                }
            }
        }
        return result;
    }

    /**
     * 客服拒绝换货
     *
     * @param model
     * @param exchangeId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/refuse/{exchangeId}", method = RequestMethod.POST)
    public Response closeExchange(@PathVariable Long exchangeId, String info) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            Exchange exchange = exchangeService.findById(exchangeId);
            if (exchange != null && !exchange.getExchangeStatus().equals(ExchangeStatus.SUCCESS.getCode())) {
                exchangeService.doCustomerRefuse(exchangeId, admin.getUsername(), info);
                result.setMessage("拒绝换货");
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 客服选择要换的SKU
     *
     * @param model
     * @param skuId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/sku/{exchangeId}/{skuId}", method = RequestMethod.POST)
    public Response doChangeSku(@PathVariable Long exchangeId, @PathVariable Long skuId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        ProductSku sku = productSkuService.findById(skuId);
        int success = 0;
        try {
            success = exchangeService.updateToChangeSku(sku.getId(), exchangeId, admin.getUsername());
            if (success < 1) {
                result.setStatus(-1);
                result.setMessage("操作不成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        result.put("sku", sku);
        return result;
    }

    /**
     * 代客户填写物流信息
     *
     * @param reshipId
     * @param model
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/logistic/{exchangeId}", method = RequestMethod.GET)
    public Response customerLogistic(@PathVariable Long exchangeId) throws NotLoginException {
        Exchange exchange = exchangeService.findById(exchangeId);
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        if (exchange != null && !exchange.getExchangeStatus().equals(ExchangeStatus.APPROVE.getCode())) {
            result.setMessage("换货申请状态不正确，无法填写相关物流信息");
            return result;
        }
        result.put("exchange", exchange);
        return result;
    }

    /**
     * 代客户填写物流信息
     *
     * @param model
     * @param reshipId
     * @param deliveryCorpName
     * @param deliverySn
     * @param memo
     * @param backAccountSn
     * @param backAccountName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logistic/{exchangeId}", method = RequestMethod.POST)
    public Response doCustomerLogistic(@PathVariable Long exchangeId, String deliveryCorpName, String deliverySn)
            throws Exception {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Exchange exchange = exchangeService.findById(exchangeId);
        exchange.setDeliveryCorp(deliveryCorpName);
        exchange.setDeliverySn(deliverySn);
        // TODO 需要修改，测试，暂时屏蔽
        int reValue = exchangeService.doLogistic(exchange.getId(), deliveryCorpName, deliverySn, admin.getUsername());
        if (reValue > 0) {
            result.setStatus(1);
            result.setMessage("物流信息提交成功！");
        } else {
            result.setStatus(-1);
            result.setMessage("物流信息提交不成功！");
        }
        return result;
    }

    /**
     * 代客户关闭申请换货
     *
     * @param model
     * @param reshipId
     * @param info
     * @param agreeFlag
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/close/{exchangeId}", method = RequestMethod.POST)
    public Response customerCloseExchange(@PathVariable Long exchangeId, String info) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            Exchange exchange = exchangeService.findById(exchangeId);
            if (exchange != null && !exchange.getExchangeStatus().equals(ExchangeStatus.SUCCESS.getCode())) {
                exchangeService.doCustomerClose(exchange.getId(), admin.getUsername(), info);
                result.setMessage("关闭换货申请单成功");
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 仓库同意换货
     *
     * @param model
     * @param exchangeId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/store/received/{exchangeId}", method = RequestMethod.POST)
    public Response receivedExchange(@PathVariable Long exchangeId, String info) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Exchange exchange = exchangeService.findById(exchangeId);
        if (exchange != null) {
            exchangeService.doStoreAgree(exchange.getId(), admin.getUsername(), info);
        } else {
            result.setStatus(-1);
            result.setMessage("换货单不存在");
        }
        return result;
    }

    /**
     * 仓库拒绝换货
     *
     * @param model
     * @param exchangeId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/store/refuse/{exchangeId}", method = RequestMethod.POST)
    public Response refuseExchange(@PathVariable Long exchangeId, String info) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            exchangeService.doStoreRefuse(exchangeId, admin.getUsername(), info);
            result.setMessage("仓库拒绝换货成功！");
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 客服发货
     *
     * @param model
     * @param exchangeId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/customer/delivery/{exchangeId}", method = RequestMethod.POST)
    public Response deliverExchange(@PathVariable Long exchangeId, String exchangeDeliveryCorp,
                                    String exchangeDeliverySn) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (exchangeDeliveryCorp == null || exchangeDeliveryCorp.equals("") || exchangeDeliverySn == null
                || exchangeDeliverySn.equals("")) {
            result.setMessage("发货不成功，请填写发货信息！");
            result.setStatus(-1);
        } else {
            try {
                Exchange exchange = exchangeService.findById(exchangeId);
                if (exchange != null && !exchange.getExchangeStatus().equals(ExchangeStatus.SUCCESS.getCode())) {
                    exchangeService.doCustomerDeliver(exchange.getId(), admin.getUsername(), exchangeDeliveryCorp,
                            exchangeDeliverySn);
                    result.setMessage("客服发货成功");
                }
            } catch (Exception e) {
                result.setStatus(-1);
                result.setMessage(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 客服代退款
     *
     * @param model
     * @param exchangeId
     * @param info
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/refund/{exchangeId}", method = RequestMethod.POST)
    public Response refundExchange(@PathVariable Long exchangeId, String info) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Exchange exchange = exchangeService.findById(exchangeId);
        if (exchange != null && !exchange.getExchangeStatus().equals(ExchangeStatus.SUCCESS.getCode())) {
            OrderItem orderItem = orderItemService.findById(exchange.getOrderItemId());
            if (orderItem.getRefundId() != null) {
                Refund oldRefund = refundService.findById(orderItem.getRefundId());
                if (oldRefund != null && oldRefund.getRefundStatus() > 0) {
                    result.setStatus(-1);
                    result.setMessage("退款申请未处理完成，不能重复申请");
                    return result;
                }
            }
            Order order = orderService.findById(orderItem.getOrderId());
            try {
                Refund refund = new Refund();
                refund.createRefund(exchange, order.getPaymentType());
                refund.setLoginCode(order.getLoginCode());
                refund.setOrderPaySn(order.getPaymentSn());
                refund.setOrderPaymentId(order.getPaymentId());
                refund.setOrderCreateDate(order.getCreateDate());
                refund.setOrderMemo(order.getMemo());
                refund.setAuditor(admin.getUsername());
                refund.setAuditDate(new Date());
                refund.setMemberName(orderItem.getBuyerMemberName());
                refund.setRefundStatus(RefundStatus.WAITFORPAYMENT.getCode());
                refund.setCreator(admin.getUsername());
                refund.setDevice(exchange.getDevice());
                refund.setAppVersion(exchange.getAppVersion());
                refund.setApplyAmount(exchange.getPrice());
                refund.setTotalAmount(exchange.getPrice());
                refund.setProxy(1);
                refund.setBackAccountType(order.getPaymentType());
                refund.setBackAccountName(order.getPaymentSn());
                Payment pay = paymentService.findById(order.getPaymentId());
                if (pay != null) {
                    refund.setBackAccountSn(pay.getPayer() != null ? pay.getPayer() : pay.getAlipaySn());
                }
                exchangeService.doRefundClose(exchange.getId(), admin.getUsername(), info, refund);
                String refundLogInfo = orderItem.getProductSkuSn() + "，金额：" + orderItem.getProductPrice();
                if (refund.getProxy() == 1) {
                    refundLogInfo = "客服代换货单转退款：" + refundLogInfo;
                }
                orderItemService.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.AfterSale,
                        admin.getUsername(), refundLogInfo);
            } catch (Exception e) {
                result.setStatus(-1);
                result.setMessage("退款不成功");
                return result;
            }
        } else {
            result.setStatus(-1);
            result.setMessage("换货单状态不匹配，退款不成功");
            return result;
        }
        return result;
    }

    /**
     * 导入换货发货表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delivery/excel/import", method = RequestMethod.POST)
    public Response importExchangeDeliver(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        List<ExchangeDeliveryBean> beans = this.getExchangeDeliveryBeanList(excelData);
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                ExchangeDeliveryBean bean = (ExchangeDeliveryBean) object;
                if (StringUtils.isEmpty(bean.getDeliverySn())) {
                    return false;
                }
                Exchange exchange = exchangeService.findBySn(bean.getExchangeSn());
                if (exchange == null) {
                    errorMsg.append("单号：" + bean.getExchangeSn() + "，错误原因：换货单未找到<br/>");
                    return false;
                }
                if (exchange.getExchangeStatus() != 3) {
                    errorMsg.append("单号：" + bean.getExchangeSn() + "，错误原因：换货单状态不匹配<br/>");
                    return false;
                }
                if (!exchange.getSkuSn().equals(bean.getBarCode())) {
                    errorMsg.append("单号：" + bean.getExchangeSn() + "，错误原因：换货单的条码不一致<br/>");
                    return false;
                }
                exchangeService.doCustomerDeliver(exchange.getId(), admin.getUsername(), bean.getDeliveryCorpName(),
                        bean.getDeliverySn());
                return true;
            }
        });
    }

    private List<ExchangeDeliveryBean> getExchangeDeliveryBeanList(List<Map<String, Object>> excelData) {
        List<ExchangeDeliveryBean> list = new ArrayList<>();
        ExchangeDeliveryBean bean = null;
        for (Map<String, Object> map : excelData) {
            bean = new ExchangeDeliveryBean();
            bean.setExchangeSn(String.valueOf(map.get("换货编号")));
            bean.setBarCode(String.valueOf(map.get("商品SKU条码")));
            bean.setDeliveryCorpName(String.valueOf(map.get("物流公司")));
            bean.setDeliverySn(String.valueOf(map.get("物流单号")));
            list.add(bean);
        }
        return list;
    }

    /**
     * 换货单操作日志列表
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.POST)
    public Response logList(@PathVariable Long id) {
        List<ExchangeLog> logList = exchangeLogService.findByExchangeId(id);
        return new SuccessResponse(logList);
    }

}
