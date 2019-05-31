package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.convert.ConvertHelper;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.ExportLog;
import com.d2c.logger.service.ExportLogService;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Admin;
import com.d2c.member.model.CrmGroup;
import com.d2c.member.query.CrmGroupSearcher;
import com.d2c.member.service.CrmGroupService;
import com.d2c.order.dto.OrderExportDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderQueryService;
import com.d2c.order.service.OrderReportService;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.CSVUtil;
import com.d2c.util.file.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderExportCtrl extends SuperCtrl {

    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ExportLogService exportLogService;
    @Autowired
    private OrderReportService orderReportService;
    @Autowired
    private CrmGroupService crmGroupService;

    /**
     * 管易系统，商品格式导出
     *
     * @param request
     * @param response
     * @param query
     * @param page
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/rest/orders/excel/guanyi/product", method = RequestMethod.POST)
    public Response guanyiProductExcel(HttpServletRequest request, HttpServletResponse response, OrderSearcher query,
                                       PageModel page) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        BeanUt.trimString(query);
        if (!checkDate(result, query.getStartDate(), query.getEndDate())) {
            return result;
        }
        query.analyseOrderStatus();
        query.setSplit(false);
        query.setInCollage(false);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        String fileName = admin.getUsername() + "_" + "D2C订单导入-商品格式";
        String[] titleNames = getDetailDeliveryTableTitleNames();
        ExcelUtil excelUtil = new ExcelUtil(fileName, admin.getUsername());
        boolean exportSuccess = true;
        int pagerNumber = 1;
        PageResult<Order> pager = new PageResult<>(page);
        int totalCount = this.orderQueryService.countSimpleBySearch(query);
        pager.setTotalCount(totalCount);
        do {
            page.setPageNumber(pagerNumber);
            List<Order> orders = orderQueryService.findSimpleBySearch(query, page);
            exportSuccess = createExcel(excelUtil, titleNames, getDetailDeliveryRowList(orders));
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, excelUtil.getExportFileBean());
        if (exportSuccess) {
            this.saveExportLog(excelUtil.getExportFileBean().getFileName(),
                    excelUtil.getExportFileBean().getDownloadPath(), excelUtil.getExportFileBean().getFileSize(),
                    "GuanYiProduct");
        }
        return result;
    }

    private String[] getDetailDeliveryTableTitleNames() {
        return new String[]{"明细状态", "订单编号", "标题", "价格", "购买数量", "外部系统编号", "商品属性", "套餐信息", "备注", "订单状态", "商家编码",
                "实际单价", "标准单价"};
    }

    private List<Map<String, Object>> getDetailDeliveryRowList(List<Order> findOrders) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (Order order : findOrders) {
            List<OrderItemDto> orderItems = orderItemService.findDtoByOrderId(order.getId());
            for (OrderItemDto orderItem : orderItems) {
                ItemStatus status = ItemStatus.getItemStatusByName(orderItem.getStatus());
                if (status.getCode() < 0) {
                    continue;
                }
                if (orderItem.getRequisition() > 0) {
                    continue;
                }
                if (orderItem.getDeliveryType() != 1) {
                    cellsMap = new HashMap<>();
                    // 修改
                    cellsMap.put("明细状态", orderItem.getItemStatusName());
                    cellsMap.put("订单编号", order.getOrderSn());
                    cellsMap.put("标题", orderItem.getProductName());
                    cellsMap.put("价格", orderItem.getProductPrice());
                    cellsMap.put("购买数量", orderItem.getProductQuantity());
                    cellsMap.put("外部系统编号", orderItem.getProductSn());
                    String color = orderItem.getSp1Value();
                    String size = orderItem.getSp2Value();
                    if (size != null && (size.equals("均码") || size.equals("均"))) {
                        size = "F";
                    }
                    cellsMap.put("商品属性", "颜色：" + (color != null ? color : "") + ";尺码："
                            + (size != null ? size.replace("码", "") : ""));
                    cellsMap.put("套餐信息", null);
                    cellsMap.put("备注", "D2C全球设计师集成平台订单");
                    cellsMap.put("订单状态", "买家已付款，等待卖家发货");
                    cellsMap.put("商家编码", orderItem.getProductSn());
                    BigDecimal acUnitPrice = orderItem.getActualAmount().divide(new BigDecimal(orderItem.getQuantity()),
                            RoundingMode.HALF_UP);
                    cellsMap.put("实际单价", acUnitPrice);
                    cellsMap.put("标准单价", orderItem.getProductPrice());
                    rowList.add(cellsMap);
                }
            }
        }
        return rowList;
    }

    /**
     * 管易系统，订单格式导出
     *
     * @param request
     * @param response
     * @param query
     * @param page
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/rest/orders/excel/guanyi/order", method = RequestMethod.POST)
    public Response guanyiOrderExcel(HttpServletRequest request, HttpServletResponse response, OrderSearcher query,
                                     PageModel page) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        BeanUt.trimString(query);
        if (!checkDate(result, query.getStartDate(), query.getEndDate())) {
            return result;
        }
        query.analyseOrderStatus();
        query.setSplit(false);
        query.setInCollage(false);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        String fileName = admin.getUsername() + "_" + "D2C订单导入-订单格式";
        String[] titleNames = getSummaryDeliveryTableTitleNames();
        ExcelUtil excelUtil = new ExcelUtil(fileName, admin.getUsername());
        int pagerNumber = 1;
        boolean exportSuccess = true;
        PageResult<Order> pager = new PageResult<>(page);
        int totalCount = this.orderQueryService.countSimpleBySearch(query);
        pager.setTotalCount(totalCount);
        do {
            page.setPageNumber(pagerNumber++);
            List<Order> orders = orderQueryService.findSimpleBySearch(query, page);
            exportSuccess = createExcel(excelUtil, titleNames, getSummaryDeliveryRowList(orders));
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, excelUtil.getExportFileBean());
        if (exportSuccess) {
            this.saveExportLog(excelUtil.getExportFileBean().getFileName(),
                    excelUtil.getExportFileBean().getDownloadPath(), excelUtil.getExportFileBean().getFileSize(),
                    "GuanYiOrder");
        }
        return result;
    }

    private String[] getSummaryDeliveryTableTitleNames() {
        return new String[]{"明细状态", "订单编号", "买家会员名", "买家支付宝账号", "买家应付货款", "买家应付邮费", "买家支付积分", "总金额", "返点积分",
                "买家实际支付金额", "买家实际支付积分", "订单状态", "买家留言", "收货人姓名", "收货地址" + " ", "运送方式", "联系电话" + " ", "联系手机", "订单创建时间",
                "订单付款时间" + " ", "宝贝标题" + " ", "宝贝种类" + " ", "物流单号" + " ", "物流公司", "订单备注", "宝贝总数量", "店铺Id", "店铺名称",
                "D+店编码"};
    }

    private List<Map<String, Object>> getSummaryDeliveryRowList(List<Order> findOrders) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Order order : findOrders) {
            List<OrderItemDto> orderItems = orderItemService.findDtoByOrderId(order.getId());
            BigDecimal totalAmount = new BigDecimal(0);
            for (OrderItemDto orderItem : orderItems) {
                ItemStatus status = ItemStatus.getItemStatusByName(orderItem.getStatus());
                if (status.getCode() < 0) {
                    continue;
                }
                if (orderItem.getRequisition() > 0) {
                    continue;
                }
                if (orderItem.getDeliveryType() != 1) {
                    totalAmount = totalAmount.add(orderItem.getActualAmount());
                }
            }
            for (OrderItemDto orderItem : orderItems) {
                ItemStatus status = ItemStatus.getItemStatusByName(orderItem.getStatus());
                if (status.getCode() < 0) {
                    continue;
                }
                if (orderItem.getRequisition() > 0) {
                    continue;
                }
                if (orderItem.getDeliveryType() != 1) {
                    cellsMap = new HashMap<>();
                    cellsMap.put("订单编号", order.getOrderSn());
                    cellsMap.put("买家会员名",
                            StringUtils.isBlank(order.getBuyerInfo()) ? order.getReciver() : order.getBuyerInfo());
                    cellsMap.put("买家支付宝账号", null);
                    cellsMap.put("买家应付邮费", 0);
                    cellsMap.put("买家支付积分", 0);
                    cellsMap.put("返点积分", null);
                    cellsMap.put("买家实际支付积分", 0);
                    cellsMap.put("订单状态", "买家已付款，等待卖家发货");
                    cellsMap.put("买家留言", order.getMemo());
                    cellsMap.put("收货人姓名", order.getReciver());
                    cellsMap.put("收货地址 ", order.getDeliveryAddress());
                    cellsMap.put("运送方式", "快递");
                    cellsMap.put("联系电话 ", order.getContact());
                    cellsMap.put("联系手机", order.getContact());
                    cellsMap.put("订单创建时间", sdf.format(order.getCreateDate()));
                    if (order.getPaymentTime() != null) {
                        cellsMap.put("订单付款时间 ", sdf.format(order.getPaymentTime()));
                    }
                    cellsMap.put("买家应付货款", totalAmount);
                    cellsMap.put("总金额", totalAmount);
                    cellsMap.put("买家实际支付金额", orderItem.getActualAmount());
                    cellsMap.put("宝贝标题 ", orderItem.getProductName());
                    cellsMap.put("宝贝种类", "1");
                    cellsMap.put("物流单号", null);
                    cellsMap.put("物流公司", "顺丰速运");
                    cellsMap.put("订单备注", order.getAdminMemo());
                    cellsMap.put("宝贝总数量", orderItem.getProductQuantity());
                    cellsMap.put("店铺Id", 0);
                    cellsMap.put("店铺名称", "D2C全球设计师集成平台");
                    // 修改
                    cellsMap.put("明细状态", orderItem.getItemStatusName());
                    cellsMap.put("D+店编码", orderItem.getDplusCode() == null ? "" : orderItem.getDplusCode());
                    rowList.add(cellsMap);
                }
            }
        }
        return rowList;
    }

    /**
     * 订单表导出
     *
     * @param request
     * @param response
     * @param query
     * @param page
     * @param sum
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = {"/rest/orders/excel", "/rest/old/export/orders/excel"}, method = RequestMethod.POST)
    public Response excel(HttpServletRequest request, HttpServletResponse response, OrderSearcher query, PageModel page,
                          Integer sum) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        BeanUt.trimString(query);
        // 最多导出90天的数据
        if (!checkDate(result, query.getStartDate(), query.getEndDate(), 90 * 24 * 60 * 60 * 1000L)) {
            return result;
        }
        query.analyseOrderStatus();
        query.setSplit(false);
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(admin.getUsername() + "_" + "订单表");
        String[] titleNames = null;
        String logType = null;
        if (sum == 0) {
            titleNames = getTitleNames();
            logType = "Delivery";
        } else {
            titleNames = getTitleNames2();
            logType = "Orders";
        }
        csvUtil.writeTitleToFile(titleNames);
        PageResult<OrderExportDto> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        int totalCount = orderReportService.countBySearch(query);
        // 最多导出50000条数据
        if (totalCount >= 50000) {
            result.setStatus(-1);
            result.setMessage("订单数据超过5万条，无法导出！");
            return result;
        }
        pager.setTotalCount(totalCount);
        boolean exportSuccess = true;
        do {
            page.setPageNumber(pagerNumber);
            List<OrderExportDto> orderExportDtos = orderReportService.findBySearch(query, page);
            if (admin.getIsAccountLocked()) {
                ConvertHelper.convertList(orderExportDtos);
            }
            List<Map<String, Object>> list = getRowList(orderExportDtos, sum);
            exportSuccess = csvUtil.writeRowToFile(list);
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        if (exportSuccess) {
            this.saveExportLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), logType);
        }
        return result;
    }

    private List<Map<String, Object>> getRowList(List<OrderExportDto> orderExportDtos, Integer sum) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        Map<Long, String> crmMap = new HashMap<>();
        PageResult<CrmGroup> pager = crmGroupService.findBySearcher(new PageModel(), new CrmGroupSearcher());
        for (CrmGroup crm : pager.getList()) {
            crmMap.put(crm.getId(), crm.getName());
        }
        for (OrderExportDto dto : orderExportDtos) {
            int flag = sum;
            cellsMap = new HashMap<>();
            if (flag == 0 || sum == 1) {
                cellsMap.put("订单编号", dto.getOrderSn());
                cellsMap.put("下单时间", DateUtil.second2str(dto.getOrderCreateDate()));
                cellsMap.put("下单设备", DeviceTypeEnum.valueOf(dto.getDevice()).getDisplay());
                cellsMap.put("订单状态", OrderStatus.getStatus(dto.getOrderStatus()).getName());
                cellsMap.put("订单类型", OrderType.valueOf(dto.getType()).getDisplay());
                cellsMap.put("CRM小组", dto.getCrmGroupId() != null ? crmMap.get(dto.getCrmGroupId()) : "");
                cellsMap.put("门店会员", dto.getStoreGroupId());
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
                flag = 1 | flag;
            }
            cellsMap.put("运营小组", dto.getOperation());
            cellsMap.put("匹配ID", dto.getOrderItemId());
            cellsMap.put("明细状态", ItemStatus.getItemStatusByName(dto.getOrderItemStatus()).getName());
            cellsMap.put("明细实际支付", dto.getActualAmount());
            cellsMap.put("商品折扣", dto.getOrderitemPromotionAmount());
            cellsMap.put("满减平摊", dto.getOrderPromotionAmount());
            cellsMap.put("优惠券平摊", dto.getOrderitemCoupontAmount());
            cellsMap.put("红包平摊", dto.getOrderitemRedAmount());
            cellsMap.put("税费平摊", dto.getOrderitemTaxAmount());
            cellsMap.put("自买优惠", dto.getOrderitemPartnerAmount());
            cellsMap.put("钱包本金", dto.getCashAmount());
            cellsMap.put("钱包赠金", dto.getGiftAmount());
            cellsMap.put("门店ID", dto.getStoreId());
            cellsMap.put("售后", dto.getAfter());
            cellsMap.put("预计发货时间", dto.getEstimateDate() != null ? DateUtil.second2str(dto.getEstimateDate()) : "");
            cellsMap.put("发货时间", dto.getDeliveryTime() != null ? DateUtil.second2str(dto.getDeliveryTime()) : "");
            cellsMap.put("发货类型", dto.getDeliveryType() == null || dto.getDeliveryType() == 0 ? "D2C直发" : "品牌方直发");
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

    private String[] getTitleNames() {
        return new String[]{
                "订单状态信息", "匹配ID", "订单编号", "下单时间", "下单设备", "订单状态", "订单类型", "CRM小组", "门店会员", "客服备注", "订单完成时间",
                "订单支付信息", "支付时间", "支付类型", "支付流水号", "支付商户号", "商品总额", "满减金额", "优惠券金额", "红包金额", "运费金额", "税费金额",
                "收货地址信息", "会员ID", "会员账号", "会员手机", "会员邮箱", "收货人", "联系方式", "收货省份", "收货城市", "收货地址", "备注", "发票",
                "明细状态信息", "运营小组", "明细状态", "明细实际支付", "商品折扣", "满减平摊", "优惠券平摊", "红包平摊", "税费平摊", "自买优惠", "钱包本金", "钱包赠金",
                "门店ID", "售后",
                "明细发货信息", "预计发货时间", "发货时间", "发货类型", "物流编号", "物流公司",
                "明细商品信息", "商品款号", "设计师款号", "商品名称", "吊牌价", "销售价", "供货价", "数量", "品类", "品牌名", "商品备注",
                "明细条码信息", "商品条码", "实发条码", "颜色", "尺码", "顺丰库存", "门店库存", "直接返利比", "团队返利比", "间接团队返利比", "AM返利比"
        };
    }

    private String[] getTitleNames2() {
        return new String[]{
                "订单状态信息", "订单编号", "下单时间", "下单设备", "订单状态", "订单类型", "CRM小组", "门店会员", "客服备注", "订单完成时间",
                "订单支付信息", "支付时间", "支付类型", "支付流水号", "支付商户号", "商品总额", "满减金额", "优惠券金额", "红包金额", "运费金额", "税费金额",
                "收货地址信息", "会员ID", "会员账号", "会员手机", "会员邮箱", "收货人", "联系方式", "收货省份", "收货城市", "收货地址", "备注", "发票",
                "明细状态信息", "运营小组", "明细状态", "明细实际支付", "商品折扣", "满减平摊", "优惠券平摊", "红包平摊", "税费平摊", "自买优惠", "钱包本金", "钱包赠金",
                "门店ID", "售后",
                "明细发货信息", "预计发货时间", "发货时间", "发货类型", "物流编号", "物流公司",
                "明细商品信息", "商品款号", "设计师款号", "商品名称", "吊牌价", "销售价", "供货价", "数量", "品类", "品牌名", "商品备注",
                "明细条码信息", "商品条码", "实发条码", "颜色", "尺码", "顺丰库存", "门店库存", "直接返利比", "团队返利比", "间接团队返利比", "AM返利比"
        };
    }

    protected void saveExportLog(String fileName, String filePath, long fileSize, String logType)
            throws NotLoginException {
        ExportLog exportLog = new ExportLog();
        exportLog.setFileName(fileName);
        exportLog.setFilePath(filePath);
        exportLog.setFileSize(fileSize);
        exportLog.setLogType(logType);
        exportLog.setCreator(this.getLoginedAdmin().getUsername());
        exportLogService.insert(exportLog);
    }

    @RequestMapping(value = "/rest/orders/excel/guanyi/wait4delivery", method = RequestMethod.POST)
    public Response guanyiWaitForDeliveryExcel(HttpServletRequest request, HttpServletResponse response,
                                               OrderSearcher query, PageModel page) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        BeanUt.trimString(query);
        query.setItemStatus(new String[]{ItemStatus.NORMAL.name()});
        query.analyseOrderStatus();
        query.setSplit(false);
        query.setInCollage(false);
        query.setAfter(0);
        query.setRequisition(0);
        query.setProductSource(ProductSource.D2CMALL.name());
        ExcelUtil excelUtil = new ExcelUtil(admin.getUsername() + "_" + "管易格式待发货订单表", admin.getUsername());
        String[] titleNames = getGuanyiWait4deliveryTitleNames();
        String logType = "GuanyiWait4Delivery";
        PageResult<OrderExportDto> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        int totalCount = orderReportService.countBySearch(query);
        // 最多导出50000条数据
        if (totalCount >= 50000) {
            result.setStatus(-1);
            result.setMessage("订单数据超过5万条，无法导出！");
            return result;
        }
        pager.setTotalCount(totalCount);
        boolean exportSuccess = true;
        // 按订单统计明细总金额
        List<HashMap<String, Object>> maps = orderReportService.groupByOrderSn(query);
        HashMap<String, BigDecimal> totalAmoutMap = new HashMap<>();
        for (HashMap<String, Object> map : maps) {
            if (map.get("orderSn") != null) {
                totalAmoutMap.put(map.get("orderSn").toString(), new BigDecimal(map.get("price").toString()));
            }
        }
        do {
            page.setPageNumber(pagerNumber);
            List<OrderExportDto> orderExportDtos = orderReportService.findBySearch(query, page);
            if (admin.getIsAccountLocked()) {
                ConvertHelper.convertList(orderExportDtos);
            }
            List<Map<String, Object>> list = getGuanyiWait4DeliveryRowList(orderExportDtos, totalAmoutMap);
            exportSuccess = createExcel(excelUtil, titleNames, list);
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, excelUtil.getExportFileBean());
        if (exportSuccess) {
            this.saveExportLog(excelUtil.getExportFileBean().getFileName(),
                    excelUtil.getExportFileBean().getDownloadPath(), excelUtil.getExportFileBean().getFileSize(),
                    logType);
        }
        return result;
    }

    private String[] getGuanyiWait4deliveryTitleNames() {
        return new String[]{"订单编号", "买家会员", "支付金额", "商品名称", "商品代码", "规格代码", "规格名称", "数量", "价格", "商品备注", "运费", "买家留言",
                "收货人", "联系电话", "联系手机", "收货地址", "省", "市", "区", "邮编", "订单创建时间", "订单付款时间", "发货时间", "物流单号", "物流公司", "卖家备注",
                "发票种类", "发票类型", "发票抬头", "纳税人识别号", "开户行", "账号", "地址", "电话", "是否手机订单", "是否货到付款", "支付方式", "支付交易号", "真实姓名",
                "身份证号", "仓库名称", "预计发货时间", "预计送达时间", "订单类型", "是否分销订单", "订单明细状态", "D+店编码"};
    }

    private List<Map<String, Object>> getGuanyiWait4DeliveryRowList(List<OrderExportDto> dtos,
                                                                    HashMap<String, BigDecimal> map) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (OrderExportDto dto : dtos) {
            cellsMap = new HashMap<>();
            cellsMap.put("订单编号", dto.getOrderSn());
            cellsMap.put("买家会员", dto.getReciver());
            cellsMap.put("支付金额", map.get(dto.getOrderSn()));
            cellsMap.put("商品名称", dto.getProductName());
            cellsMap.put("商品代码", dto.getProductSn());
            cellsMap.put("规格代码", dto.getDeliveryBarCode());
            cellsMap.put("规格名称", dto.getSp1Value() + " " + processSp2Value(dto.getSp2Value()));
            cellsMap.put("数量", dto.getProductQuantity());
            cellsMap.put("价格", dto.getActualAmount());
            cellsMap.put("商品备注", "");
            cellsMap.put("运费", dto.getShippingRates());
            cellsMap.put("买家留言", dto.getMemo());
            cellsMap.put("收货人", dto.getReciver());
            cellsMap.put("联系电话", dto.getContact());
            cellsMap.put("联系手机", dto.getContact());
            cellsMap.put("收货地址", dto.getProvince() + dto.getCity() + dto.getAddress());
            cellsMap.put("省", dto.getProvince());
            cellsMap.put("市", dto.getCity());
            cellsMap.put("区", dto.getDistrict());
            cellsMap.put("邮编", "");
            cellsMap.put("订单创建时间", DateUtil.second2str(dto.getOrderCreateDate()));
            cellsMap.put("订单付款时间", DateUtil.second2str(dto.getOrderPayTime()));
            cellsMap.put("发货时间", "");
            cellsMap.put("物流单号", "");
            cellsMap.put("物流公司", "顺丰速运");
            cellsMap.put("卖家备注", dto.getAdminMemo());
            cellsMap.put("发票种类", "");
            cellsMap.put("发票类型", "");
            cellsMap.put("发票抬头", "");
            cellsMap.put("纳税人识别号", "");
            cellsMap.put("开户行", "");
            cellsMap.put("账号", "");
            cellsMap.put("电话", "");
            cellsMap.put("是否手机订单", "");
            cellsMap.put("是否货到付款", "");
            cellsMap.put("支付方式", PaymentTypeEnum.getByCode(dto.getPaymentType()).getDisplay());
            cellsMap.put("支付交易号", dto.getAlipaySn());//
            cellsMap.put("真实姓名", "");
            cellsMap.put("身份证号", "");
            cellsMap.put("仓库名称", "顺丰正品仓");
            cellsMap.put("预计发货时间", DateUtil.second2str(dto.getEstimateDate()));
            cellsMap.put("预计送达时间", "");
            cellsMap.put("订单类型", "销售订单");
            cellsMap.put("是否分销订单", "");
            cellsMap.put("订单明细状态", ItemStatus.valueOf(dto.getOrderItemStatus()).getDisplay());
            cellsMap.put("D+店编码", dto.getDplusCode() == null ? "" : dto.getDplusCode());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    private String processSp2Value(String sp2) {
        if (!StringUtils.isNoneBlank(sp2)) {
            return "";
        }
        sp2 = sp2.trim();
        if ("均码".equals(sp2)) {
            return "F";
        }
        return ("码".equals(sp2.substring(sp2.length() - 1)) ? sp2.substring(0, sp2.length() - 1) : sp2);
    }

}
