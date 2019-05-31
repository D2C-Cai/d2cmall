package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONArray;
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
import com.d2c.logger.model.RequisitionLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.RequisitionLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.Distributor;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.DiscountSettingService;
import com.d2c.member.service.DistributorService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.dto.GuanyiOrderDto;
import com.d2c.order.dto.GuanyiOrderDto.ShopCodeEnum;
import com.d2c.order.dto.RequisitionSummaryDto;
import com.d2c.order.model.GuanyiOrderItem;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.model.RequisitionItem.*;
import com.d2c.order.model.Store;
import com.d2c.order.query.RequisitionItemSearcher;
import com.d2c.order.service.GuanyiOrderItemService;
import com.d2c.order.service.GuanyiOrderService;
import com.d2c.order.service.RequisitionItemService;
import com.d2c.order.service.StoreService;
import com.d2c.order.third.guanyi.GuanyiErpClient;
import com.d2c.product.model.*;
import com.d2c.product.model.Product.ProductSaleType;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.service.*;
import com.d2c.product.support.SkuCodeBean;
import com.d2c.util.serial.SerialNumUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 调拨单明细
 */
@RestController
@RequestMapping(value = "/rest/order/requisitionitem")
public class RequisitionItemCtrl extends BaseCtrl<RequisitionItemSearcher> {

    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private RequisitionLogService requisitionLogService;
    @Autowired
    private DistributorService distributorService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuStockSummaryService productSkuStockSummaryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private DiscountSettingService discountSettingService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private GuanyiOrderService guanyiOrderService;
    @Autowired
    private GuanyiOrderItemService guanyiOrderItemService;

    /**
     * 同意平台网签协议
     *
     * @return
     */
    @RequestMapping(value = "/agreement", method = RequestMethod.POST)
    public Response agreement() {
        SuccessResponse result = new SuccessResponse();
        String token = getRequest().getHeader("accesstoken");
        MemberInfo member = memberInfoService.findByToken(token);
        memberInfoService.doAgreeMent(member.getId(), new Date());
        return result;
    }

    /**
     * 门店采购导入
     *
     * @param requset
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/apply", method = RequestMethod.POST)
    public Response importType2(HttpServletRequest request) {
        AdminDto admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String applyRole = PurchaseRole.ADMIN.name();
                if (map.get("条码") == null || StringUtil.isBlank(map.get("条码").toString())) {
                    errorMsg.append("第" + row + "行：条码不能为空！<br/>");
                    return false;
                }
                if (map.get("数量") == null) {
                    errorMsg.append("第" + row + "行：数量不能为空！<br/>");
                    return false;
                }
                if (map.get("类型") == null || StringUtil.isBlank(map.get("类型").toString())) {
                    errorMsg.append("第" + row + "行：类型不能为空！<br/>");
                    return false;
                }
                String barCode = String.valueOf(map.get("条码"));
                Integer quantity = new BigDecimal(String.valueOf(map.get("数量"))).intValue();
                String storeCode = String.valueOf(map.get("门店编号"));
                String orderSn = map.get("预售单号") == null ? null : String.valueOf(map.get("预售单号"));
                String purchaseReason = String.valueOf(map.get("类型"));
                String storeMemo = map.get("备注") != null ? String.valueOf(map.get("备注")) : null;
                ProductSku productSku = productSkuService.findByBarCode(barCode);
                if (productSku == null) {
                    errorMsg.append("第" + row + "行：条码异常！<br/>");
                    return false;
                }
                if (quantity == null || quantity <= 0) {
                    errorMsg.append("第" + row + "行：数量异常，数量应大于0！<br/>");
                    return false;
                }
                Product product = productService.findById(productSku.getProductId());
                Store store = null;
                if (admin.getStoreId() != null) {
                    store = storeService.findById(admin.getStoreId());
                    if (store == null) {
                        throw new BusinessException("您所绑门店异常！");
                    }
                    admin.setUsername(store.getName());
                    applyRole = PurchaseRole.STORE.name();
                    if (SaleCategory.POPPRODUCT.name().equals(product.getSaleCategory())
                            || !ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                            || !ProductSource.D2CMALL.name().equals(product.getSource())) {
                        errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：采购商品需为D2C编码的 非跨境D2C商品！<br/>");
                        return false;
                    }
                } else {
                    if (admin.getDesignerId() != null) {
                        applyRole = PurchaseRole.DESIGNER.name();
                    }
                    store = storeService.findByCode(storeCode);
                    if (store == null) {
                        errorMsg.append("第" + row + "行：门店编号异常！<br/>");
                        return false;
                    }
                }
                if (StringUtil.isBlack(orderSn)) {
                    if ("客欠采购".equals(purchaseReason)) {
                        errorMsg.append("第" + row + "行：客欠采购，预售单号不能为空！<br/>");
                        return false;
                    }
                    orderSn = SerialNumUtil.buildRequisitionItemSn(store.getCode());
                }
                Brand brand = brandService.findById(product.getDesignerId());
                if (ProductSaleType.SELF.name().equalsIgnoreCase(product.getProductSaleType())) {
                    errorMsg.append("第" + row + "行：自产商品不允许采购！<br/>");
                    return false;
                }
                if (admin.getDesignerId() != null && admin.getDesignerId() != null
                        && (!admin.getDesignerId().equals(brand.getDesignersId()))) {
                    errorMsg.append("第" + row + "行：该sku未绑定您的品牌！<br/>");
                    return false;
                }
                RequisitionItemSearcher searcher = new RequisitionItemSearcher();
                searcher.setOrderSn(orderSn);
                searcher.setBarCode(barCode);
                if (requisitionItemService.countBySearcher(searcher) > 0) {
                    errorMsg.append("第" + row + "行：该订单号和sku已经生成过单据！<br/>");
                    return false;
                }
                RequisitionItem requisitionItem = new RequisitionItem(store, brand, product, productSku,
                        ItemType.STOREPCH, ItemStaus.INIT, orderSn, quantity);
                requisitionItem.setStoreMemo(storeMemo);
                requisitionItem.setPurchaseReason(requisitionItem.transferPurchase(purchaseReason).name());
                requisitionItem.setPurchaseRole(applyRole);
                try {
                    requisitionItemService.insert(requisitionItem, admin.getUsername());
                } catch (Exception e) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：订单编号和条码重复！<br/>");
                    return false;
                }
                return true;
            }
        });
    }

    /**
     * 货需采购导入
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/item", method = RequestMethod.POST)
    public Response importType4(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String orderSn = String.valueOf(map.get("订单编号"));
                String barCode = String.valueOf(map.get("条码"));
                String storeCode = String.valueOf(map.get("门店编号"));
                Store store = storeService.findByCode(storeCode);
                if (StringUtils.isEmpty(orderSn) || "null".equals(orderSn)) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：订单编号不能为空，建议取当前日期（yyyyMMdd）！<br/>");
                    return false;
                }
                if (store == null) {
                    errorMsg.append("门店编号：" + storeCode + "条码：" + barCode + "，错误原因：门店编号异常！<br/>");
                    return false;
                }
                ProductSku productSku = productSkuService.findByBarCode(barCode);
                if (productSku == null) {
                    errorMsg.append("门店编号：" + storeCode + "条码：" + barCode + "，错误原因：条码异常！<br/>");
                    return false;
                }
                String quantity = String.valueOf(map.get("数量"));
                if (quantity == null) {
                    errorMsg.append("门店编号：" + storeCode + "条码：" + barCode + "，错误原因：数量异常！<br/>");
                    return false;
                }
                Product product = productService.findById(productSku.getProductId());
                if (SaleCategory.POPPRODUCT.name().equals(product.getSaleCategory())
                        || !ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                        || !ProductSource.D2CMALL.name().equals(product.getSource())) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：采购商品需为D2C编码的 非跨境D2C商品！<br/>");
                    return false;
                }
                Brand brand = brandService.findById(product.getDesignerId());
                if (StringUtil.isNotBlank(orderSn)) {
                    orderSn = storeCode + "_" + orderSn;
                }
                RequisitionItem requisitionItem = new RequisitionItem(store, brand, product, productSku,
                        ItemType.GOODSPCH, ItemStaus.WAITSIGN, orderSn, new BigDecimal(quantity).intValue());
                requisitionItem.setPurchaseReason(PurchaseType.NORMAL.name());
                requisitionItem.setPurchaseRole(PurchaseRole.ADMIN.name());
                try {
                    requisitionItemService.insert(requisitionItem, admin.getUsername());
                } catch (Exception e) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：订单编号和条码重复！<br/>");
                    return false;
                }
                return true;
            }
        });
    }

    /**
     * 所有退货导入
     *
     * @param request
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/reship", method = RequestMethod.POST)
    public Response importType56(HttpServletRequest request, Integer type) {
        Admin admin = this.getLoginedAdmin();
        if (type == null || (type != 5 && type != 6)) {
            return new ErrorResponse("退货单导入类型不明确！");
        }
        List<String> importSn = new ArrayList<>();
        Store lbp = storeService.findByCode("001");
        SuccessResponse result = this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String orderSn = String.valueOf(map.get("订单编号"));
                String barCode = String.valueOf(map.get("条码"));
                String quantity = String.valueOf(map.get("数量"));
                String relationSn = String.valueOf(map.get("关联采购单号"));
                ProductSku productSku = productSkuService.findByBarCode(barCode);
                if (StringUtils.isEmpty(orderSn) || "null".equals(orderSn)) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：订单编号不能为空，建议取当前日期（yyyyMMdd）！<br/>");
                    return false;
                }
                if (productSku == null) {
                    errorMsg.append("条码：" + barCode + "，错误原因：条码异常！<br/>");
                    return false;
                }
                if (quantity == null) {
                    errorMsg.append("条码：" + barCode + "，错误原因：数量异常！<br/>");
                    return false;
                }
                Product product = productService.findById(productSku.getProductId());
                if (type == 6 && ProductSaleType.SELF.name().equals(product.getProductSaleType())) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：入库检验退货不支持自产商品！<br/>");
                    return false;
                }
                if (type == 5 && (!ProductSaleType.CONSIGN.name().equals(product.getProductSaleType())
                        || !ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                        || !ProductSource.D2CMALL.name().equals(product.getSource()))) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：销退退货只支持D2C非跨境代销商品！<br/>");
                    return false;
                }
                Brand brand = brandService.findById(product.getDesignerId());
                // 退货商品默认是发货的
                RequisitionItem requisitionItem = new RequisitionItem(lbp, brand, product, productSku,
                        ItemType.getStatus(type), ItemStaus.DELIVERED, orderSn, new BigDecimal(quantity).intValue());
                requisitionItem.setCreator(admin.getUsername());
                requisitionItem.setRelationSn(relationSn);
                requisitionItem.setDeliveryQuantity(Integer.parseInt(quantity));
                requisitionItem.setDeliveryCorp(String.valueOf(map.get("物流公司")));
                requisitionItem.setDeliverySn(String.valueOf(map.get("物流单号")));
                requisitionItem.setRemark(map.get("备注") == null ? "" : String.valueOf(map.get("备注")));
                try {
                    requisitionItem = requisitionItemService.insert(requisitionItem, admin.getUsername());
                    importSn.add(requisitionItem.getRequisitionSn());
                } catch (Exception e) {
                    errorMsg.append("订单编号：" + orderSn + "条码：" + barCode + "，错误原因：订单编号和条码重复！<br/>");
                    return false;
                }
                return true;
            }
        });
        if (importSn.size() > 0) {
            List<Map<String, Object>> list = requisitionItemService.countGroupByDesignerReship(importSn);
            for (Map<String, Object> map : list) {
                Long designerId = (Long) map.get("designerId");
                String designerName = map.get("designerName").toString();
                Long count = (Long) map.get("count");
                if (designerId != null && designerId != 0) {
                    Brand brand = brandService.findById(designerId);
                    MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                    if (memberInfo != null) {
                        String subject = "退货单提醒";
                        String content = "亲爱的" + designerName + "品牌主理人，您有" + count + "单退货单，收到商品后请及时处理。";
                        PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                        pushBean.setAppUrl("/rest/order/requisitionitem/list?status=3&type=56");
                        MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                        msgBean.setAppUrl("/rest/order/requisitionitem/list?status=3&type=56");
                        SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                                SmsLogType.REMIND, content);
                        msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 门店补货导入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/import/league", method = RequestMethod.POST)
    public Response importType8(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String storeCode = String.valueOf(map.get("门店编号"));
                String barCode = String.valueOf(map.get("条码"));
                String quantity = String.valueOf(map.get("数量"));
                Store store = storeService.findByCode(storeCode);
                if (store == null) {
                    errorMsg.append("门店编号：" + storeCode + "条码：" + barCode + "，错误原因：门店编号异常！<br/>");
                    return false;
                }
                if (quantity == null) {
                    errorMsg.append("门店编号：" + storeCode + "条码：" + barCode + "，错误原因：数量异常！<br/>");
                    return false;
                }
                ProductSku productSku = productSkuService.findByBarCode(barCode);
                if (productSku == null) {
                    errorMsg.append("门店编号：" + storeCode + "条码：" + barCode + "，错误原因：条码异常！<br/>");
                    return false;
                }
                Product product = productService.findById(productSku.getProductId());
                Brand brand = brandService.findById(product.getDesignerId());
                RequisitionItem requisitionItem = new RequisitionItem(store, null, brand, product, productSku,
                        ItemType.LEAGUEAPPLY, ItemStaus.WAITDELIVERED, null, new BigDecimal(quantity).intValue());
                requisitionItemService.insert(requisitionItem, admin.getUsername());
                return true;
            }
        });
    }

    /**
     * 采购发货导入
     *
     * @param requset
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/delivery", method = RequestMethod.POST)
    public Response importDelivery(HttpServletRequest request) {
        AdminDto admin = this.getLoginedAdmin();
        Set<Long> designerIds = null;
        if (admin.getDesignerId() != null) {
            designerIds = new HashSet<>();
            List<Brand> brands = brandService.findByDesignersId(admin.getDesignerId(), null);
            for (Brand b : brands) {
                designerIds.add(b.getId());
            }
        }
        final Set<Long> brandIds = designerIds;
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String requisitionSn = String.valueOf(map.get("采购单号"));
                Integer quantity = new BigDecimal(String.valueOf(map.get("发货数量"))).intValue();
                String deliveryCorp = String.valueOf(map.get("物流公司"));
                String deliverySn = String.valueOf(map.get("发货单号"));
                RequisitionItem requisitionItem = requisitionItemService.findByRequisitionSn(requisitionSn);
                if (requisitionItem == null) {
                    errorMsg.append("单号：" + requisitionSn + "，错误原因：采购单号不存在，请核实<br/>");
                    return false;
                }
                if (!(requisitionItem.getType() == 1 || requisitionItem.getType() == 2
                        || requisitionItem.getType() == 4)) {
                    errorMsg.append("单号：" + requisitionSn + "，错误原因：单号对应的不是采购单<br/>");
                    return false;
                }
                if (requisitionItem.getStatus() != 2) {
                    errorMsg.append("单号：" + requisitionSn + "，错误原因：采购单状态不是待发货<br/>");
                    return false;
                }
                if (quantity < 0 || quantity > requisitionItem.getQuantity()
                        || (requisitionItem.getType() == 1 && quantity.intValue() != requisitionItem.getQuantity())) {
                    errorMsg.append("单号：" + requisitionSn + "，错误原因：发货数量不符，请核实<br/>");
                    return false;
                }
                if (brandIds != null && !brandIds.contains(requisitionItem.getDesignerId())) {
                    errorMsg.append("单号：" + requisitionSn + "，错误原因：该采购单不是你的品牌，请核实<br/>");
                    return false;
                }
                try {
                    requisitionItemService.doDelivery(requisitionItem.getId(), quantity, deliverySn, deliveryCorp,
                            admin.getUsername());
                    return true;
                } catch (Exception e) {
                    errorMsg.append("单号：" + requisitionSn + "，错误原因：" + e.getMessage() + "<br/>");
                }
                return false;
            }
        });
    }

    /**
     * 申请采购输入sku校验
     *
     * @param model
     * @param sn
     * @param sizeGroupId
     * @return
     */
    @RequestMapping(value = "/check/sku", method = RequestMethod.POST)
    public Response checkSKU(ModelMap model, String sn, Long sp1GroupId, Long sp2GroupId) {
        SuccessResponse result = new SuccessResponse();
        if (!StringUtils.isEmpty(sn)) {
            sn = sn.toUpperCase().trim();
            SkuCodeBean bean = productService.getSKUCodeParser(sp1GroupId, sp2GroupId, sn);
            if (bean == null) {
                return new ErrorResponse("输入的SKU码解析错误, 请检查颜色/尺码是否存在！");
            }
            result.put("bean", bean);
        }
        return result;
    }

    /**
     * 门店/后台 采购申请
     *
     * @param barCode
     * @param quantity
     * @param orderSn
     * @param storeMemo
     * @param purchaseReason
     * @param storeCode
     * @return
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Response applyType24(String barCode, Integer quantity, String orderSn, String storeMemo,
                                String purchaseReason, String storeCode) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        String applyRole = PurchaseRole.ADMIN.name();
        ProductSku productSku = productSkuService.findByBarCode(barCode);
        if (productSku == null) {
            return new ErrorResponse("该条码对应的商品不存在");
        }
        Product product = productService.findById(productSku.getProductId());
        Brand brand = brandService.findById(product.getDesignerId());
        Store store = null;
        if (StringUtil.isBlank(storeCode)) {
            // 门店申请
            store = storeService.findById(admin.getStoreId());
            applyRole = PurchaseRole.STORE.name();
        } else {
            store = storeService.findByCode(storeCode);
            if (admin.getDesignerId() != null) {
                applyRole = PurchaseRole.DESIGNER.name();
            }
        }
        if (store == null) {
            return new ErrorResponse("没有该门店信息");
        }
        // 区分既绑定设计师又绑定门店的，设计师只能申请自己的商品
        if (StringUtil.isNotBlank(storeCode) && admin.getDesignerId() != null
                && !(brand.getDesignersId().equals(admin.getDesignerId()))) {
            return new ErrorResponse("该商品未绑定设计师品牌" + brand.getName());
        } else if (admin.getStoreId() != null) {
            admin.setUsername(store.getName());
            if (SaleCategory.POPPRODUCT.name().equals(product.getSaleCategory())
                    || !ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                    || !ProductSource.D2CMALL.name().equals(product.getSource())) {
                throw new BusinessException("门店采购只支持D2C编码的非跨境D2C商品");
            }
        }
        RequisitionItem requisitionItem = new RequisitionItem(store, brand, product, productSku, ItemType.STOREPCH,
                ItemStaus.INIT, orderSn, quantity);
        requisitionItem.setStoreMemo(storeMemo);
        requisitionItem.setPurchaseReason(purchaseReason);
        requisitionItem.setPurchaseRole(applyRole);
        requisitionItemService.insert(requisitionItem, admin.getUsername());
        return result;
    }

    /**
     * 店主申请仓库调拨
     *
     * @param barCode
     * @param quantity
     * @param storeMemo
     * @return
     */
    @RequestMapping(value = "/doAsk", method = RequestMethod.POST)
    public Response applyType8(String barCode, Integer quantity, String storeMemo) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        ProductSku productSku = productSkuService.findByBarCode(barCode);
        if (productSku == null) {
            return new ErrorResponse("该条码对应的商品不存在");
        }
        ProductSkuStockSummary summary = productSkuStockSummaryService.findBySkuId(productSku.getId());
        int stock = summary.getSfStock() + summary.getStStock();
        if (quantity > stock) {
            return new ErrorResponse("当前可申请的数量最大为" + stock + "件");
        }
        Distributor distributor = distributorService.findByMemberInfoId(admin.getMemberId());
        if (distributor == null) {
            return new ErrorResponse("此账号不是经销商账号！");
        }
        if (distributor.getGroupId() == null) {
            return new ErrorResponse("此经销商未绑定折扣组！");
        }
        Product product = productService.findById(productSku.getProductId());
        DiscountSetting ds = discountSettingService.findByGroupIdAndProductId(distributor.getGroupId(),
                product.getId());
        if (ds == null) {
            return new ErrorResponse("该商品不在规定可售范围内");
        }
        Brand brand = brandService.findById(product.getDesignerId());
        Store store = storeService.findById(admin.getStoreId());
        RequisitionItem requisitionItem = new RequisitionItem(store, null, brand, product, productSku,
                ItemType.LEAGUEAPPLY, ItemStaus.INIT, null, quantity);
        requisitionItem.setStoreMemo(storeMemo);
        requisitionItemService.insert(requisitionItem, admin.getUsername());
        return result;
    }

    /**
     * 店主退回仓库调拨
     *
     * @param barCode
     * @param quantity
     * @param storeMemo
     * @return
     */
    @RequestMapping(value = "/doBack", method = RequestMethod.POST)
    public Response applyType9(String barCode, Integer quantity, String storeMemo) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        ProductSku productSku = productSkuService.findByBarCode(barCode);
        if (productSku == null) {
            return new ErrorResponse("该条码对应的商品不存在");
        }
        Distributor distributor = distributorService.findByMemberInfoId(admin.getMemberId());
        if (distributor == null) {
            return new ErrorResponse("此账号不是经销商账号！");
        }
        if (distributor.getGroupId() == null) {
            return new ErrorResponse("此经销商未绑定折扣组！");
        }
        Product product = productService.findById(productSku.getProductId());
        Brand brand = brandService.findById(product.getDesignerId());
        Store store = storeService.findById(admin.getStoreId());
        Store jgcbt = storeService.findByCode("D2C101");
        ProductSkuStock productSkuStock = productSkuStockService.findOne(store.getCode(), barCode);
        if (productSkuStock == null) {
            return new ErrorResponse("该条码对应的商品不在店内！");
        }
        if (productSkuStock.getStock() < quantity) {
            return new ErrorResponse("退回数量不能超过店内库存！");
        }
        RequisitionItem requisitionItem = new RequisitionItem(store, jgcbt, brand, product, productSku,
                ItemType.LEAGUERETURN, ItemStaus.WAITDELIVERED, null, quantity);
        requisitionItem.setStoreMemo(storeMemo);
        requisitionItem.setAllocateReason(AllocateType.UNSALE.name());
        requisitionItemService.insert(requisitionItem, admin.getUsername());
        return result;
    }

    /**
     * 运营批量提交
     *
     * @param ids
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/batch/submit", method = RequestMethod.POST)
    public Response doSubmit(Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int count = 0;
        for (Long id : ids) {
            int success = requisitionItemService.doSubmit(id, admin.getUsername());
            if (success > 0) {
                count++;
            }
        }
        result.setMessage("操作成功" + count + "条，失败" + (ids.length - count) + "条！");
        return result;
    }

    /**
     * 设计师,门店批量签收
     *
     * @param id
     * @param info
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/batch/sign", method = RequestMethod.POST)
    public Response doSign(Long[] ids) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int count = 0;
        for (Long id : ids) {
            int success = requisitionItemService.doSign(id, admin.getUsername());
            if (success > 0) {
                count++;
            }
        }
        result.setMessage("操作成功" + count + "条，失败" + (ids.length - count) + "条！");
        return result;
    }

    /**
     * 设计师,门店拒绝
     *
     * @param id
     * @param info
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/refuse/{id}", method = RequestMethod.POST)
    public Response doRefuse(@PathVariable Long id, String info) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        requisitionItemService.doRefuse(id, admin.getUsername(), info);
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 设计师,门店退回
     *
     * @param id
     * @param info
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/back/{id}", method = RequestMethod.POST)
    public Response doBack(@PathVariable Long id, String info) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        requisitionItemService.doRefuse(id, admin.getUsername(), info);
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 设计师,门店发货
     *
     * @param id
     * @param quantity
     * @param deliveryCorp
     * @param deliverySn
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     * @throws Exception
     */
    @RequestMapping(value = "/deliver/{id}", method = RequestMethod.POST)
    public Response doDelivery(@PathVariable Long id, Integer quantity, String deliveryCorp, String deliverySn)
            throws NotLoginException, BusinessException, Exception {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (quantity == null) {
            result.setStatus(-1);
            result.setMessage("数据异常！");
            return result;
        }
        try {
            requisitionItemService.doDelivery(id, quantity, deliverySn, deliveryCorp, admin.getUsername());
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 设计师,门店批量发货
     *
     * @param ids
     * @param quantity
     * @param deliveryCorp
     * @param deliverySn
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     * @throws Exception
     */
    @RequestMapping(value = "/batch/deliver", method = RequestMethod.POST)
    public Response doBatchDelivery(Long[] ids, Integer[] quantity, String deliveryCorp, String deliverySn)
            throws NotLoginException, BusinessException, Exception {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        try {
            for (int i = 1; i < ids.length; i++) {
                requisitionItemService.doDelivery(ids[i], quantity[i], deliverySn, deliveryCorp, admin.getUsername());
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 设计师,门店,仓库收货
     *
     * @param id
     * @param quantity
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/receive/{id}", method = RequestMethod.POST)
    public Response doReceive(@PathVariable Long id, Integer quantity) throws Exception {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (quantity == null) {
            result.setStatus(-1);
            result.setMessage("数据异常！");
            return result;
        }
        requisitionItemService.doReceive(id, quantity, admin.getUsername(), "");
        return result;
    }

    /**
     * 关闭调拨明细
     *
     * @param ids
     * @param delayReason
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public Response doClose(Long[] ids, String closeReason) throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            requisitionItemService.doClose(id, admin.getUsername(), closeReason);
        }
        return new SuccessResponse();
    }

    /**
     * 仓库直接发货给消费者
     *
     * @param id
     * @param deliveryBarCode
     * @param deliveryCorpName
     * @param deliverySn
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/delivered/{id}", method = RequestMethod.POST)
    public Response doDeliverToCustomer(@PathVariable Long id, String deliveryBarCode, String deliveryCorpName,
                                        String deliverySn) throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        requisitionItemService.doDeliverToCustomer(id, deliveryBarCode, deliveryCorpName, deliverySn,
                admin.getUsername());
        return result;
    }

    /**
     * 设计师重发
     *
     * @param id
     * @param remark
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/cancel/delivery/{id}", method = RequestMethod.POST)
    public Response doCancelDelivery(@PathVariable Long id, String remark, String reason, String barcode)
            throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (!"未收到货".equals(reason) && !"设计师错发".equals(reason) && !"收到商品是次品".equals(reason)) {
            result.setStatus(-1);
            result.setMessage("重发原因不符合要求");
            return result;
        }
        requisitionItemService.doCancelDelivery(id, remark, admin.getUsername(), reason, barcode);
        return result;
    }

    /**
     * 采购单门店评论（在状态关闭中）
     *
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/store/comment", method = RequestMethod.POST)
    public Response doStoreComment(Integer responseSpeed, String storeComment, Long id, ModelMap model)
            throws NotLoginException, BusinessException {
        SuccessResponse result = new SuccessResponse();
        AdminDto admin = this.getLoginedAdmin();
        requisitionItemService.doStoreComment(id, storeComment, responseSpeed, admin.getStoreId(), admin.getUsername());
        return result;
    }

    /**
     * 拦截调拨单
     *
     * @param id
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/lock/{id}", method = RequestMethod.POST)
    public Response doLock(@PathVariable Long id, Integer status) throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        requisitionItemService.doLock(id, status, admin.getUsername());
        return result;
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
    @RequestMapping(value = "/handle/{flag}", method = RequestMethod.POST)
    public Response doHandle(@PathVariable Integer flag, Long id, String memo) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = requisitionItemService.doHandle(id, flag, admin.getUsername(), memo);
        if (success < 1) {
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 更新备注说明
     *
     * @param id
     * @param remark
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/remark", method = RequestMethod.POST)
    public Response updateRemark(Long id, String remark) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        int result = requisitionItemService.updateRemark(id, remark, admin.getUsername());
        if (result < 0) {
            return new ErrorResponse("更新不成功！");
        }
        return new SuccessResponse();
    }

    /**
     * 门店更新备注
     *
     * @param id
     * @param info
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/update/store/memo", method = RequestMethod.POST)
    public Response updateStoreMemo(Long id, String info) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        requisitionItemService.updateStoreMemo(id, info, admin.getUsername());
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 设计师更新备注
     *
     * @param id
     * @param info
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/update/designer/memo", method = RequestMethod.POST)
    public Response updateDesignerMemo(Long id, String info) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        requisitionItemService.updateDesignerMemo(id, info, admin.getUsername());
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 设计师更新发货时间
     *
     * @param id
     * @param info
     * @return
     * @throws BusinessException
     * @throws ParseException
     */
    @RequestMapping(value = "/update/designer/date", method = RequestMethod.POST)
    public Response updateDesignerEstimateDate(Long id, String info, String designerEstimateDate)
            throws NotLoginException, BusinessException, ParseException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Date estimateDate = null;
        if (!StringUtils.isEmpty(designerEstimateDate)) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            estimateDate = sf.parse(designerEstimateDate);
        }
        requisitionItemService.updateDesignerEstimateDate(id, estimateDate, info, admin.getUsername());
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 调整调拨数量
     *
     * @param id
     * @param quantity
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/quantity", method = RequestMethod.POST)
    public Response updateQuantity(Long id, Integer quantity) throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        RequisitionItem requisitionItem = requisitionItemService.findById(id);
        if (requisitionItem.getType() == ItemType.LEAGUERETURN.getCode()) {
            Store store = storeService.findById(admin.getStoreId());
            ProductSkuStock productSkuStock = productSkuStockService.findOne(store.getCode(),
                    requisitionItem.getBarCode());
            if (productSkuStock == null) {
                return new ErrorResponse("该条码对应的商品不在店内！");
            }
            if (productSkuStock.getStock() < quantity) {
                return new ErrorResponse("退回数量不能超过店内库存！");
            }
        }
        int result = requisitionItemService.updateQuantity(id, quantity, admin.getUsername());
        if (result < 0) {
            return new ErrorResponse("更新不成功！");
        }
        return new SuccessResponse();
    }

    /**
     * 更新设计师发货数量
     *
     * @param id
     * @param quantity
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/update/delivery/quantity", method = RequestMethod.POST)
    public Response updateDeliveryQuantity(Long id, Integer quantity) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        int result = requisitionItemService.updateDeliveryQuantity(id, quantity, admin.getUsername());
        if (result < 0) {
            return new ErrorResponse("更新不成功！");
        }
        return new SuccessResponse();
    }

    /**
     * 更新次品数量
     *
     * @param id
     * @param quantity
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/update/defective/quantity", method = RequestMethod.POST)
    public Response updateDefectiveQuantity(Long id, Integer quantity) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        int result = requisitionItemService.updateDefectiveQuantity(id, quantity, admin.getUsername());
        if (result < 0) {
            return new ErrorResponse("更新不成功！");
        }
        return new SuccessResponse();
    }

    /**
     * 更新仓库实际收货数量
     *
     * @param id
     * @param quantity
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/update/receive/quantity", method = RequestMethod.POST)
    public Response updateReceiveQuantity(Long id, Integer quantity) throws NotLoginException, BusinessException {
        Admin admin = this.getLoginedAdmin();
        int result = requisitionItemService.updateReceiveQuantity(id, quantity, admin.getUsername());
        if (result < 0) {
            return new ErrorResponse("更新不成功！");
        }
        return new SuccessResponse();
    }

    /**
     * 更改优先级
     *
     * @param id
     * @param priority
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/priority", method = RequestMethod.POST)
    public Response updatePriority(Long id, Integer priority) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        int result = requisitionItemService.updatePriority(id, priority, admin.getUsername());
        if (result < 0) {
            return new ErrorResponse("更新不成功！");
        }
        return new SuccessResponse();
    }

    /**
     * 修改物流信息
     *
     * @param id
     * @param deliveryCorp
     * @param deliverySn
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/change/logistics/{id}", method = RequestMethod.POST)
    public Response updateLogistics(@PathVariable Long id, String deliveryCorp, String deliverySn)
            throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (StringUtil.isBlank(deliveryCorp) || StringUtil.isBlank(deliverySn)) {
            result.setStatus(-1);
            result.setMessage("物流公司或物流编号不能为空！");
            return result;
        }
        requisitionItemService.updateDeliveryInfo(id, deliveryCorp, deliverySn, admin.getUsername());
        return result;
    }

    /**
     * 更改调拨地址
     *
     * @param ids
     * @param allocateReason
     * @return
     */
    @RequestMapping(value = "/change/address", method = RequestMethod.POST)
    public Response updateAddress(Long[] ids, String allocateReason) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Store store = null;
        if (allocateReason.equals(AllocateType.INFERIOR.name())) {
            store = storeService.findByCode("007");
        } else if (allocateReason.equals(AllocateType.UNSALE.name())) {
            store = storeService.findByCode("D2C101");
        } else {
            result.setStatus(-1);
            result.setMessage("修改类型不正确！");
            return result;
        }
        for (Long id : ids) {
            requisitionItemService.updateLogisticAddress(id, store.getLinker(), store.getTel(), store.getAddress(),
                    admin.getUsername(), allocateReason);
        }
        return result;
    }

    /**
     * 汇总查看
     *
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public Response summary(PageModel page, Integer type) throws NotLoginException {
        this.getLoginedAdmin();
        PageResult<RequisitionSummaryDto> pager = requisitionItemService.findSummary(type, page);
        return new SuccessResponse(pager);
    }

    /**
     * 操作日志
     *
     * @param id
     * @param page
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response log(@PathVariable Long id, PageModel page) throws NotLoginException {
        this.getLoginedAdmin();
        RequisitionItem item = this.requisitionItemService.findById(id);
        PageResult<RequisitionLog> pager = null;
        if (item != null) {
            pager = requisitionLogService.findByRequisitionItemId(id, item.getRequisitionId(), page);
        }
        return new SuccessResponse(pager);
    }

    /**
     * 操作日志
     *
     * @param id
     * @param page
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/log/orderitem/{orderItemId}", method = RequestMethod.GET)
    public Response orderitemLog(@PathVariable Long orderItemId, PageModel page) throws NotLoginException {
        this.getLoginedAdmin();
        RequisitionItem requisitionItem = requisitionItemService.findByOrderItemId(orderItemId);
        PageResult<RequisitionLog> pager = null;
        if (requisitionItem != null) {
            pager = requisitionLogService.findByRequisitionItemId(requisitionItem.getId(),
                    requisitionItem.getRequisitionId(), page);
        }
        return new SuccessResponse(pager);
    }

    /**
     * 修改颜色尺码
     *
     * @param id
     * @param skuId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/sku", method = RequestMethod.POST)
    public Response updateSku(Long id, Long skuId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            requisitionItemService.updateSku(id, skuId, admin.getUsername());
        } catch (BusinessException e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doList(RequisitionItemSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        PageResult<RequisitionItem> pager = requisitionItemService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(RequisitionItemSearcher searcher) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        return requisitionItemService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "RequisitionItem";
    }

    @Override
    protected List<Map<String, Object>> getRow(RequisitionItemSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        PageResult<RequisitionItem> pager = requisitionItemService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RequisitionItem item : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("订单编号", item.getOrderSn());
            cellsMap.put("订单创建时间", item.getOrderDate() == null ? "" : sdf.format(item.getOrderDate()));
            cellsMap.put("调拨类型", item.getTypeName());
            cellsMap.put("门店", item.getStoreName());
            cellsMap.put("调拨单号", item.getRequisitionSn());
            cellsMap.put("关联采购单号", item.getRelationSn());
            cellsMap.put("调拨状态", item.getStatusName());
            cellsMap.put("设计师", item.getDesignerName());
            cellsMap.put("设计师款号", item.getExternalSn());
            cellsMap.put("D2C款号", item.getProductSn());
            cellsMap.put("D2C条码", item.getBarCode());
            cellsMap.put("商品名称", item.getProductName());
            if (StringUtil.isNotBlank(item.getProductCategory())) {
                JSONArray array = JSONArray.parseArray(item.getProductCategory());
                cellsMap.put("品类", array.getJSONObject(array.size() - 1).getString("name"));
            }
            cellsMap.put("颜色", item.getSp1Value());
            cellsMap.put("尺码", item.getSp2Value());
            cellsMap.put("数量", item.getQuantity());
            cellsMap.put("发货数量", item.getDeliveryQuantity());
            cellsMap.put("实收数量", item.getReceiveQuantity());
            cellsMap.put("次品数量", item.getDefectiveQuantity());
            cellsMap.put("预计调拨时间",
                    item.getDesignerEstimateDate() == null ? "" : sdf.format(item.getDesignerEstimateDate()));
            cellsMap.put("承诺发货时间", item.getEstimateDate() == null ? "" : sdf.format(item.getEstimateDate()));
            cellsMap.put("备注", item.getRemark());
            cellsMap.put("收货地址", item.getAddress());
            cellsMap.put("联系电话", item.getTel());
            cellsMap.put("联系人", item.getContact());
            cellsMap.put("运营小组", item.getOperation());
            cellsMap.put("订单买家备注", item.getOrderCusMemo());
            cellsMap.put("拒绝原因", item.getDelayReason());
            cellsMap.put("品牌名称", item.getDesignerName());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "商品调拨明细表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"订单编号", "订单创建时间", "调拨类型", "门店", "调拨单号", "关联采购单号", "调拨状态", "设计师", "设计师款号", "D2C款号",
                "D2C条码", "商品名称", "品类", "颜色", "尺码", "数量", "发货数量", "实收数量", "次品数量", "预计调拨时间", "承诺发货时间", "备注", "收货地址",
                "联系电话", "联系人", "运营小组", "订单买家备注", "拒绝原因", "品牌名称"};
    }

    @Override
    protected Response doHelp(RequisitionItemSearcher searcher, PageModel page) {
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
     * 采购退货
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/doreship/{id}", method = RequestMethod.POST)
    public Response doReship(@PathVariable Long id) throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        requisitionItemService.doReship(id, admin.getUsername());
        return new SuccessResponse();
    }

    @RequestMapping(value = "/guanyi/code/{code}", method = RequestMethod.POST)
    public Response guanyiReProcess(@PathVariable String code) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (StringUtil.isBlack(code)) {
            result.setStatus(-1);
            result.setMessage("管易单号不能为空！");
            return result;
        }
        if (guanyiOrderService.findByCode(code) != null) {
            result.setStatus(-1);
            result.setMessage("该管易订单编号已拉取成功，请核实！");
            return result;
        }
        JSONObject response = new JSONObject();
        try {
            response = GuanyiErpClient.getInstance().queryDeliveryByCode(code);
        } catch (Exception e1) {
            result.setStatus(-1);
            result.setMessage(e1.getMessage());
            return result;
        }
        JSONArray array = response.getJSONArray("deliverys");
        if (array.size() == 0) {
            result.setStatus(-1);
            result.setMessage("单据不存在或未发货!");
            return result;
        }
        if (array.size() > 1) {
            result.setStatus(-1);
            result.setMessage("单据异常!");
            return result;
        }
        JSONObject obj = array.getJSONObject(0);
        GuanyiOrderDto guanyiOrderDto = new GuanyiOrderDto(obj);
        // 排除未知的管易门店
        if (!ShopCodeEnum.getShopCodeMap().containsKey(guanyiOrderDto.getShopCode())) {
            result.setStatus(-1);
            result.setMessage("该发货单门店异常！");
            return result;
        }
        JSONArray details = obj.getJSONArray("details");
        List<GuanyiOrderItem> items = new ArrayList<>();
        for (int j = 0; j < details.size(); j++) {
            items.add(new GuanyiOrderItem(details.getJSONObject(j), guanyiOrderDto));
        }
        guanyiOrderDto.setItems(items);
        try {
            guanyiOrderService.insert(guanyiOrderDto);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        guanyiOrderService.processBurgeon(guanyiOrderDto, false, admin.getUsername());
        if (guanyiOrderDto.getExpress() == 1) {
            for (GuanyiOrderItem guanyiOrderItem : guanyiOrderDto.getItems()) {
                try {
                    guanyiOrderItemService.processExpress(guanyiOrderItem, false, "定时器");
                } catch (Exception e) {
                    guanyiOrderItem.setExpressFall(1);
                    guanyiOrderItem.setExpressError(e.getMessage() == null ? "空指针异常" : e.getMessage());
                    guanyiOrderItemService.update(guanyiOrderItem);
                }
            }
        }
        return result;
    }

}
