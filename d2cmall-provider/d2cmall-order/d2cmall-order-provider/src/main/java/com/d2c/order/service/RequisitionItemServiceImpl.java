package com.d2c.order.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.core.annotation.AsyncMethod;
import com.d2c.logger.model.BurgeonErrorLog;
import com.d2c.logger.model.BurgeonErrorLog.DocSourceType;
import com.d2c.logger.model.BurgeonErrorLog.DocType;
import com.d2c.logger.model.RequisitionLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.BurgeonErrorLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.RequisitionLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.RequisitionItemMapper;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.dto.RequisitionSummaryDto;
import com.d2c.order.model.*;
import com.d2c.order.model.Compensation.CompensateMethod;
import com.d2c.order.model.CustomerCompensation.CompensationType;
import com.d2c.order.model.RequisitionItem.AllocateType;
import com.d2c.order.model.RequisitionItem.ItemStaus;
import com.d2c.order.model.RequisitionItem.ItemType;
import com.d2c.order.model.Store.BusTypeEnum;
import com.d2c.order.query.RequisitionItemSearcher;
import com.d2c.order.third.burgeon.BurgeonClient;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSaleType;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("requisitionItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RequisitionItemServiceImpl extends ListServiceImpl<RequisitionItem> implements RequisitionItemService {

    @Autowired
    private RequisitionItemMapper requisitionItemMapper;
    @Autowired
    private RequisitionLogService requisitionLogService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CompensationService compensationService;
    @Autowired
    private BurgeonErrorLogService burgeonErrorLogService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private CustomerCompensationService customerCompensationService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void insertLog(Long requisitionItemId, String requisitionSn, String logType, String info, String operator) {
        RequisitionLog log = new RequisitionLog();
        log.setCreator(operator);
        log.setRequisitionItemId(requisitionItemId);
        log.setInfo(info);
        log.setLogType(logType);
        log.setRequisitionSn(requisitionSn);
        this.requisitionLogService.insert(log);
        this.requisitionItemMapper.updateLastLogInfo(requisitionItemId, info);
    }

    @Override
    public RequisitionItem findById(Long id) {
        return requisitionItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public RequisitionItem findByOrderItemId(Long orderItemId) {
        return requisitionItemMapper.findByOrderItemId(orderItemId);
    }

    @Override
    public List<RequisitionItem> findByRequisitionId(Long requisitionId) {
        return requisitionItemMapper.findByRequisitionId(requisitionId);
    }

    @Override
    public RequisitionItem findLastOne(Integer type) {
        return requisitionItemMapper.findLastOne(type);
    }

    @Override
    public RequisitionItem findByRequisitionSn(String requisitionSn) {
        return requisitionItemMapper.findByRequisitionSn(requisitionSn);
    }

    @Override
    public List<RequisitionItem> findByDeliverySn(String sn) {
        return requisitionItemMapper.findByDeliverySn(sn);
    }

    @Override
    public PageResult<RequisitionItem> findBySearcher(RequisitionItemSearcher searcher, PageModel page) {
        PageResult<RequisitionItem> pager = new PageResult<>(page);
        int totalCount = requisitionItemMapper.countBySearcher(searcher);
        pager.setTotalCount(totalCount);
        if (totalCount > 0) {
            List<RequisitionItem> list = requisitionItemMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countBySearcher(RequisitionItemSearcher searcher) {
        return requisitionItemMapper.countBySearcher(searcher);
    }

    @Override
    public PageResult<RequisitionSummaryDto> findSummary(Integer type, PageModel page) {
        PageResult<RequisitionSummaryDto> pager = new PageResult<>(page);
        int totalCount = requisitionItemMapper.countSummary(type);
        pager.setTotalCount(totalCount);
        if (totalCount > 0) {
            List<RequisitionSummaryDto> list = requisitionItemMapper.findSummary(type, page);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public List<Map<String, Object>> countGroupByDesignerNew() {
        return requisitionItemMapper.countGroupByDesignerNew();
    }

    @Override
    public List<Map<String, Object>> countGroupByDesignerExpired() {
        return requisitionItemMapper.countGroupByDesignerExpired();
    }

    @Override
    public List<Map<String, Object>> countGroupByDesignerReship(List<String> importSn) {
        return requisitionItemMapper.countGroupByDesignerReship(importSn);
    }

    @Override
    public List<Map<String, Object>> countGroupByStore() {
        return requisitionItemMapper.countGroupByStore();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RequisitionItem insert(RequisitionItem requisitionItem, String operator) {
        requisitionItem.setCreator(operator);
        RequisitionItem rqItem = this.save(requisitionItem);
        if (rqItem != null) {
            JSONObject info = new JSONObject();
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()) {
                String str = "新增订单采购，设计师（" + requisitionItem.getDesignerName() + "）";
                if (rqItem.getDirect() == 1) {
                    str += "直发客户";
                }
                info.put("操作", str);
            } else if (requisitionItem.getType() == ItemType.STOREPCH.getCode()) {
                info.put("操作", "新增门店采购，设计师（" + requisitionItem.getDesignerName() + "）");
            } else if (requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                info.put("操作", "新增货需采购，设计师（" + requisitionItem.getDesignerName() + "）");
            } else if (requisitionItem.getType() == ItemType.RESHIPRTN.getCode()) {
                info.put("操作", "新增POP退货，设计师（" + requisitionItem.getDesignerName() + "）");
            } else if (requisitionItem.getType() == ItemType.QUALITYRTN.getCode()) {
                info.put("操作", "新增自营退货，设计师（" + requisitionItem.getDesignerName() + "）");
            } else if (requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                info.put("操作", "新增门店调拨，门店（" + requisitionItem.getStoreName() + "）直发客户");
            } else if (requisitionItem.getType() == ItemType.DEPOTALLC.getCode()) {
                info.put("操作", "新增仓库调拨，仓库（" + requisitionItem.getStoreName() + "）");
            }
            this.insertLog(rqItem.getId(), requisitionItem.getRequisitionSn(), "新增", info.toJSONString(), operator);
        }
        if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()) {
            Brand brand = brandService.findById(rqItem.getDesignerId());
            if (brand != null) {
                MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                if (memberInfo != null) {
                    String content = "亲爱的" + brand.getName() + "品牌主理人，您有一条新的采购单，最晚发货时间为"
                            + DateUtil.dayFormatCn(rqItem.getEstimateDate()) + "，为了防止超期赔偿，请及时处理。";
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, "采购单提醒", content);
                    msgBean.setPic(rqItem.getProductImg());
                    msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
                }
            }
        }
        return rqItem;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RequisitionItem doOtherInsert(RequisitionItem requisitionItem, String operator) {
        RequisitionItem item = this.insert(requisitionItem, operator);
        requisitionItem.setReceiveQuantity(requisitionItem.getQuantity());
        this.processBurgeon(requisitionItem);
        return item;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RequisitionItem doDesignerDelivery(OrderItem orderItem, String operator) {
        RequisitionItem old = requisitionItemMapper.findByOrderItemId(orderItem.getId());
        RequisitionItem requisitionItem = null;
        Store lbp = storeService.findByCode("001");
        if (old != null) {
            if (old.getStatus() < 0) {
                Brand brand = brandService.findById(orderItem.getDesignerId());
                if (brand.getDirect() == 1) {
                    Order order = orderService.findById(orderItem.getOrderId());
                    this.doCancelClose(old.getId(), ItemType.ORDERPCH.getCode(), order.getReciver(), order.getContact(),
                            order.getProvince() + order.getCity() + order.getAddress(), lbp.getId(), lbp.getName(),
                            operator, "重新指定设计师直发", old.getRequisitionId(), 1);
                } else {
                    this.doCancelClose(old.getId(), ItemType.ORDERPCH.getCode(), lbp.getLinker(), lbp.getTel(),
                            lbp.getAddress(), lbp.getId(), lbp.getName(), operator, "重新指定设计师采购", old.getRequisitionId(),
                            0);
                }
                old.setType(ItemType.ORDERPCH.getCode());
                requisitionItem = old;
            } else {
                throw new BusinessException("已经存在调拨，请勿重复调拨！");
            }
        } else {
            Product product = productService.findById(orderItem.getProductId());
            if (!ProductSaleType.CONSIGN.name().equals(product.getProductSaleType())
                    || !ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                    || !ProductSource.D2CMALL.name().equals(product.getSource())) {
                throw new BusinessException("设计师采购的为D2C非跨境代销商品！");
            }
            Order order = orderService.findById(orderItem.getOrderId());
            ProductSku productSku = productSkuService.findById(orderItem.getProductSkuId());
            Brand brand = brandService.findById(orderItem.getDesignerId());
            requisitionItem = new RequisitionItem(order, orderItem, lbp, brand, product, productSku, ItemType.ORDERPCH,
                    ItemStaus.WAITSIGN);
            requisitionItem.setExpiredDate(orderItem.getEstimateDate());
            requisitionItem.setEstimateDate(requisitionItem.getExpiredDate());
            requisitionItem = this.insert(requisitionItem, operator);
        }
        orderItemService.updateRequisition(orderItem.getId(), 1);
        orderItemService.updateEstimateDate(orderItem.getId(), requisitionItem.getExpiredDate(), "sys");
        return requisitionItem;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RequisitionItem doStoreDelivery(OrderItem orderItem, Long storeId, String operator) {
        RequisitionItem old = requisitionItemMapper.findByOrderItemId(orderItem.getId());
        RequisitionItem requisitionItem = null;
        Order order = orderService.findById(orderItem.getOrderId());
        if (old != null) {
            if (old.getStatus() < 0) {
                Store store = storeService.findById(storeId);
                this.doCancelClose(old.getId(), ItemType.STOREALLC.getCode(), order.getReciver(), order.getContact(),
                        order.getProvince() + order.getCity() + order.getAddress(), store.getId(), store.getName(),
                        operator, "重新指定门店（" + store.getName() + "）发货", old.getRequisitionId(), 0);
                old.setType(ItemType.STOREALLC.getCode());
                requisitionItem = old;
            } else {
                throw new BusinessException("已经存在调拨，请勿重复调拨！");
            }
        } else {
            ProductSku productSku = productSkuService.findById(orderItem.getProductSkuId());
            Product product = productService.findById(orderItem.getProductId());
            if (ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                    && ProductSource.D2CMALL.name().equals(product.getSource())) {
                Store store = storeService.findById(storeId);
                Brand brand = brandService.findById(orderItem.getDesignerId());
                requisitionItem = new RequisitionItem(order, orderItem, store, brand, product, productSku,
                        ItemType.STOREALLC, ItemStaus.WAITSIGN);
                requisitionItem = this.insert(requisitionItem, operator);
            } else {
                throw new BusinessException("门店采购类型应为D2C非跨境代销或买断商品！");
            }
        }
        orderItemService.updateRequisition(orderItem.getId(), 1);
        return requisitionItem;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RequisitionItem doSysDesignerDelivery(OrderItem orderItem) {
        RequisitionItem old = requisitionItemMapper.findByOrderItemId(orderItem.getId());
        RequisitionItem requisitionItem = null;
        Store lbp = storeService.findByCode("001");
        if (old != null) {
            if (old.getStatus() < 0) {
                Brand brand = brandService.findById(orderItem.getDesignerId());
                if (brand.getDirect() == 1) {
                    Order order = orderService.findById(orderItem.getOrderId());
                    this.doCancelClose(old.getId(), ItemType.ORDERPCH.getCode(), order.getReciver(), order.getContact(),
                            order.getProvince() + order.getCity() + order.getAddress(), lbp.getId(), lbp.getName(),
                            "sys", "重新指定设计师直发", old.getRequisitionId(), 1);
                } else {
                    this.doCancelClose(old.getId(), ItemType.ORDERPCH.getCode(), lbp.getLinker(), lbp.getTel(),
                            lbp.getAddress(), lbp.getId(), lbp.getName(), "sys", "重新打开调拨明细", old.getRequisitionId(), 0);
                }
                old.setType(ItemType.ORDERPCH.getCode());
                requisitionItem = old;
            } else {
                throw new BusinessException("已经存在调拨，请勿重复调拨！");
            }
        } else {
            Product product = productService.findById(orderItem.getProductId());
            if (!ProductSaleType.CONSIGN.name().equals(product.getProductSaleType())
                    || !ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                    || !ProductSource.D2CMALL.name().equals(product.getSource())) {
                return null;
            }
            Order order = orderService.findById(orderItem.getOrderId());
            ProductSku productSku = productSkuService.findById(orderItem.getProductSkuId());
            Brand brand = brandService.findById(orderItem.getDesignerId());
            requisitionItem = new RequisitionItem(order, orderItem, lbp, brand, product, productSku, ItemType.ORDERPCH,
                    ItemStaus.WAITSIGN);
            requisitionItem.setExpiredDate(orderItem.getEstimateDate());
            requisitionItem.setEstimateDate(requisitionItem.getExpiredDate());
            requisitionItem = this.insert(requisitionItem, "sys");
        }
        orderItemService.updateRequisition(orderItem.getId(), 1);
        orderItemService.updateEstimateDate(orderItem.getId(), requisitionItem.getExpiredDate(), "sys");
        return requisitionItem;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RequisitionItem doSysStoreDelivery(OrderItem orderItem, Long storeId) {
        RequisitionItem old = requisitionItemMapper.findByOrderItemId(orderItem.getId());
        RequisitionItem requisitionItem = null;
        Order order = orderService.findById(orderItem.getOrderId());
        if (old != null) {
            if (old.getStatus() < 0) {
                Store store = storeService.findById(storeId);
                this.doCancelClose(old.getId(), ItemType.STOREALLC.getCode(), order.getReciver(), order.getContact(),
                        order.getProvince() + order.getCity() + order.getAddress(), store.getId(), store.getName(),
                        "sys", "重新指定门店（" + store.getName() + "）发货", old.getRequisitionId(), 0);
                old.setType(ItemType.STOREALLC.getCode());
                requisitionItem = old;
            } else {
                throw new BusinessException("已经存在调拨，请勿重复调拨！");
            }
        } else {
            ProductSku productSku = productSkuService.findById(orderItem.getProductSkuId());
            Product product = productService.findById(orderItem.getProductId());
            if (ProductTradeType.COMMON.name().equals(product.getProductTradeType())
                    && ProductSource.D2CMALL.name().equals(product.getSource())) {
                Store store = storeService.findById(storeId);
                Brand brand = brandService.findById(orderItem.getDesignerId());
                requisitionItem = new RequisitionItem(order, orderItem, store, brand, product, productSku,
                        ItemType.STOREALLC, ItemStaus.WAITSIGN);
                requisitionItem = this.insert(requisitionItem, "sys");
            } else {
                throw new BusinessException("门店采购类型应为D2C非跨境代销或买断商品！");
            }
        }
        orderItemService.updateRequisition(orderItem.getId(), 1);
        return requisitionItem;
    }

    @Override
    @AsyncMethod(delay = 3000)
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void doSyncDesignerDelivery(List<String> orderSn) {
        for (String sn : orderSn) {
            List<OrderItem> orderItems = orderItemService.findByOrderSn(sn);
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getPop() == 1) {
                    this.doSysDesignerDelivery(orderItem);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doSubmit(Long id, String operator) {
        RequisitionItem requisitionItem = this.findById(id);
        int success = requisitionItemMapper.doSubmit(id);
        if (success > 0) {
            if (requisitionItem.getType() == ItemType.DEPOTALLC.getCode()
                    || requisitionItem.getType() == ItemType.LEAGUEAPPLY.getCode()
                    || requisitionItem.getType() == ItemType.LEAGUERETURN.getCode()) {
                requisitionItemMapper.doSign(id);
            }
            JSONObject info = new JSONObject();
            info.put("操作", "审核提交");
            this.insertLog(id, requisitionItem.getRequisitionSn(), "接收", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doSign(Long id, String operator) {
        RequisitionItem requisitionItem = this.findById(id);
        int success = requisitionItemMapper.doSign(id);
        if (success > 0) {
            JSONObject info = new JSONObject();
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                    || requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                info.put("操作", "设计师（" + requisitionItem.getDesignerName() + "）已接收");
            } else if (requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                info.put("操作", "门店（" + requisitionItem.getStoreName() + "）已接收");
            }
            this.insertLog(id, requisitionItem.getRequisitionSn(), "接收", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doRefuse(Long id, String operator, String operation) {
        RequisitionItem requisitionItem = this.findById(id);
        int success = requisitionItemMapper.doRefuse(id, operator, operation);
        if (success > 0) {
            orderItemService.updateRequisition(requisitionItem.getOrderItemId(), -1);
            JSONObject info = new JSONObject();
            info.put("操作", "拒绝调拨");
            info.put("拒绝原因", operation);
            this.insertLog(id, requisitionItem.getRequisitionSn(), "拒绝调拨", info.toJSONString(), operator);
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode() && requisitionItem.getExpiredDate() != null
                    && requisitionItem.getExpiredDate().before(new Date())) {
                // 设计师赔偿
                Compensation compensation = new Compensation(requisitionItem);
                compensationService.insert(compensation, CompensateMethod.FULL);
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doDelivery(Long id, Integer quantity, String deliverySn, String deliveryCorp, String operator)
            throws Exception {
        RequisitionItem requisitionItem = requisitionItemMapper.selectByPrimaryKey(id);
        if (!requisitionItem.getQuantity().equals(quantity) && requisitionItem.getType() != ItemType.STOREPCH.getCode()
                && requisitionItem.getType() != ItemType.GOODSPCH.getCode()) {
            throw new BusinessException("款号:" + requisitionItem.getProductSn() + "，颜色：" + requisitionItem.getSp1Value()
                    + "，尺码：" + requisitionItem.getSp2Value() + "，发货数量须等于调拨数量！");
        }
        int success = requisitionItemMapper.doDelivery(id, quantity, deliverySn, deliveryCorp);
        if (success > 0) {
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()) {
                orderItemService.updateRequisition(requisitionItem.getOrderItemId(), 2);
                if (requisitionItem.getDirect() == 1) {
                    // 设计师直发
                    orderItemService.doDeliveryItem(requisitionItem.getOrderItemId(), 1, requisitionItem.getBarCode(),
                            deliveryCorp, deliverySn, operator, true, quantity);
                    this.doReceive(id, quantity, operator, "设计师直发消费者，系统自动调拨入库");
                }
                // 设计师赔偿
                if (requisitionItem.getExpiredDate() != null
                        && requisitionItem.getExpiredDate().compareTo(new Date()) < 0) {
                    Compensation compensation = new Compensation(requisitionItem);
                    compensation.setCreator(operator);
                    compensation = compensationService.insert(compensation, null);
                    if (compensation != null && compensation.getId() != null && compensation.getId() > 0) {
                        OrderItem orderItem = orderItemService.findById(compensation.getOrderItemId());
                        customerCompensationService.doOrderItemCompensation(orderItem, compensation.getCreator(),
                                CompensationType.DESIGNERDELIVERY.getCode());
                    }
                }
            } else if (requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                orderItemService.doDeliveryItem(requisitionItem.getOrderItemId(), 0, requisitionItem.getBarCode(),
                        deliveryCorp, deliverySn, operator, true, quantity);
                this.doReceive(id, quantity, operator, "门店直发消费者，系统自动调拨入库");
                // 伯俊推单
                requisitionItem.setReceiveQuantity(quantity);
                requisitionItem.setDeliveryQuantity(quantity);
                this.processBurgeon(requisitionItem);
            }
            if (requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()
                    || (requisitionItem.getType() == ItemType.ORDERPCH.getCode() && requisitionItem.getDirect() == 0)) {
                // 推送物流
                logisticsService.createLogistics(deliveryCorp, deliverySn, Logistics.BusinessType.ALLOT.name(),
                        operator);
            }
            JSONObject info = new JSONObject(true);
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                    || requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                info.put("操作", "设计师（" + requisitionItem.getDesignerName() + "）已发货");
            } else if (requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                info.put("操作", "门店（" + requisitionItem.getStoreName() + "）已发货");
            } else if (requisitionItem.getType() == ItemType.RESHIPRTN.getCode()
                    || requisitionItem.getType() == ItemType.QUALITYRTN.getCode()
                    || requisitionItem.getType() == ItemType.DEPOTALLC.getCode()) {
                info.put("操作", "仓库（" + requisitionItem.getStoreName() + "）已发货");
            }
            info.put("物流公司", deliveryCorp);
            info.put("物流单号", deliverySn);
            this.insertLog(id, requisitionItem.getRequisitionSn(), "发货", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doReceive(Long id, Integer quantity, String operator, String info) throws Exception {
        RequisitionItem requisitionItem = requisitionItemMapper.selectByPrimaryKey(id);
        if (requisitionItem.getDefectiveQuantity() == null) {
            requisitionItem.setDefectiveQuantity(0);
        }
        if (!requisitionItem.getDeliveryQuantity().equals(quantity + requisitionItem.getDefectiveQuantity())) {
            throw new BusinessException("款号:" + requisitionItem.getProductSn() + "，颜色：" + requisitionItem.getSp1Value()
                    + "，尺码：" + requisitionItem.getSp2Value() + "，入库数量加上次品数量必须等于调拨数量！");
        }
        if (requisitionItem.getDefectiveQuantity() > 0) {
            Store store = storeService.findById(requisitionItem.getStoreId());
            Brand brand = brandService.findById(requisitionItem.getDesignerId());
            Product product = productService.findById(requisitionItem.getProductId());
            ProductSku productSku = productSkuService.findByBarCode(requisitionItem.getBarCode());
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                    || requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                RequisitionItem reshipItem = new RequisitionItem(store, brand, product, productSku, ItemType.QUALITYRTN,
                        ItemStaus.WAITDELIVERED, requisitionItem.getOrderSn() + "-R",
                        requisitionItem.getDefectiveQuantity());
                reshipItem.setRelationSn(requisitionItem.getRequisitionSn());
                this.insert(reshipItem, operator);
            } else if (requisitionItem.getType() == ItemType.LEAGUEAPPLY.getCode()) {
                Store jgcbt = storeService.findByCode("D2C101");
                RequisitionItem reshipItem = new RequisitionItem(store, jgcbt, brand, product, productSku,
                        ItemType.LEAGUERETURN, ItemStaus.WAITDELIVERED, requisitionItem.getOrderSn() + "-R",
                        requisitionItem.getDefectiveQuantity());
                reshipItem.setRelationSn(requisitionItem.getRequisitionSn());
                reshipItem.setAllocateReason(AllocateType.INFERIOR.name());
                this.insert(reshipItem, operator);
            }
        }
        int success = requisitionItemMapper.doReceive(id, quantity);
        if (success > 0) {
            if (requisitionItem.getOrderItemId() != null) {
                orderItemService.updateRequisition(requisitionItem.getOrderItemId(), 8);
            }
            JSONObject infoLog = new JSONObject();
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                    || requisitionItem.getType() == ItemType.DEPOTALLC.getCode()) {
                infoLog.put("操作", "仓库（" + requisitionItem.getStoreName() + "）已入库");
            } else if (requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                infoLog.put("操作", "门店（" + requisitionItem.getStoreName() + "）自动入库");
            } else if (requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                infoLog.put("操作", "门店（" + requisitionItem.getStoreName() + "）已入库");
            } else if (requisitionItem.getType() == ItemType.RESHIPRTN.getCode()
                    || requisitionItem.getType() == ItemType.QUALITYRTN.getCode()) {
                infoLog.put("操作", "设计师（" + requisitionItem.getDesignerName() + "）已收货");
            }
            infoLog.put("备注", info);
            this.insertLog(id, requisitionItem.getRequisitionSn(), "入库", infoLog.toJSONString(), operator);
            if (requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()
                    || requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                    || requisitionItem.getType() == ItemType.DEPOTALLC.getCode()
                    || requisitionItem.getType() == ItemType.LEAGUEAPPLY.getCode()
                    || requisitionItem.getType() == ItemType.LEAGUERETURN.getCode()) {
                requisitionItem.setReceiveQuantity(quantity);
                requisitionItem.setStatus(8);
                if (quantity > 0) {
                    // 伯俊不允许入库数量为0
                    this.processBurgeon(requisitionItem);
                }
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doCancelDelivery(Long id, String remark, String operator, String reason, String barcode) {
        int success = requisitionItemMapper.doCancelDelivery(id);
        if (StringUtils.isNotBlank(barcode)) {
            requisitionItemMapper.updateBarCode(id, barcode, null, null);
        }
        if (success > 0) {
            RequisitionItem rqItem = this.findOneById(id);
            if (rqItem.getOrderItemId() != null) {
                // 重新设置为调拨中
                orderItemService.updateRequisition(rqItem.getOrderItemId(), 1);
            }
            if ("设计师错发".equals(reason) || "收到商品是次品".equals(reason)) {
                this.doRequisition(rqItem, operator);
            }
            JSONObject info = new JSONObject();
            info.put("操作", "设计师重发");
            if (barcode != null) {
                info.put("实发条码", barcode);
            }
            info.put("原因", reason);
            info.put("备注", remark);
            this.insertLog(id, rqItem.getRequisitionSn(), "重发", info.toJSONString(), operator);
        }
        return success;
    }

    private void doRequisition(RequisitionItem rqItem, String modifyMan) {
        OrderItem orderItem = orderItemService.findById(rqItem.getOrderItemId());
        if (!ProductSource.D2CMALL.name().equals(orderItem.getProductSource())) {
            return;
        }
        Product product = productService.findById(rqItem.getProductId());
        Brand brand = brandService.findById(product.getDesignerId());
        Order order = orderService.findByOrderSn(rqItem.getOrderSn());
        ProductSku sku = productSkuService.findByBarCode(rqItem.getBarCode());
        Store lbp = storeService.findByCode("001");
        RequisitionItem requisitionItem = null;
        if ((1 == rqItem.getStatus() || 8 == rqItem.getStatus())
                && SaleCategory.POPPRODUCT.name().equals(rqItem.getSaleCategory())) {
            requisitionItem = new RequisitionItem(lbp, brand, product, sku, ItemType.RESHIPRTN, ItemStaus.WAITDELIVERED,
                    rqItem.getRequisitionSn(), rqItem.getQuantity());
            requisitionItem.setRelationSn(order.getOrderSn());
        } else if (1 == rqItem.getStatus() && SaleCategory.NORMALPRODUCT.name().equals(rqItem.getSaleCategory())) {
            requisitionItem = new RequisitionItem(lbp, brand, product, sku, ItemType.QUALITYRTN,
                    ItemStaus.WAITDELIVERED, rqItem.getRequisitionSn(), rqItem.getQuantity());
            requisitionItem.setRelationSn(order.getOrderSn());
        } else if (8 == rqItem.getStatus() && SaleCategory.NORMALPRODUCT.name().equals(rqItem.getSaleCategory())) {
            Store jgcbt = storeService.findByCode("D2C101");
            requisitionItem = new RequisitionItem(jgcbt, brand, product, sku, ItemType.DEPOTALLC,
                    ItemStaus.WAITDELIVERED, rqItem.getRequisitionSn(), rqItem.getQuantity());
            requisitionItem.setRelationSn(order.getOrderSn());
            requisitionItem.setOrderItemId(orderItem.getId());
        }
        this.doOtherInsert(requisitionItem, modifyMan);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doClose(Long id, String closeMan, String closeReason) {
        RequisitionItem item = requisitionItemMapper.selectByPrimaryKey(id);
        if (item.getStatus() == 0 || item.getStatus() == 1 || item.getStatus() == 2
                || (item.getStatus() == 3 && item.getType() != ItemType.STOREALLC.getCode())) {
            int success = requisitionItemMapper.doClose(id, closeMan, closeReason);
            if (success > 0) {
                if (item.getOrderItemId() != null) {
                    // 调拨明细关闭
                    orderItemService.updateRequisition(item.getOrderItemId(), -1);
                }
                RequisitionItem rqItem = this.findOneById(id);
                JSONObject info = new JSONObject();
                info.put("操作", "关闭" + item.getTypeName() + "单");
                info.put("原因", closeReason);
                this.insertLog(id, rqItem.getRequisitionSn(), "关闭", info.toJSONString(), closeMan);
                if ((item.getStatus() == 1 || item.getStatus() == 2) && ItemType.ORDERPCH.getCode() == item.getType()
                        && item.getExpiredDate() != null && item.getExpiredDate().before(new Date())) {
                    Compensation compensation = new Compensation(item);
                    compensationService.insert(compensation, null);
                }
                // 发送站内消息
                if (rqItem.getType() == ItemType.ORDERPCH.getCode()
                        || rqItem.getType() == ItemType.GOODSPCH.getCode()) {
                    Brand brand = brandService.findById(rqItem.getDesignerId());
                    MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                    if (memberInfo != null) {
                        String subject = "采购单提醒";
                        String content = "亲爱的" + rqItem.getDesignerName() + "品牌主理人，您的采购单" + rqItem.getRequisitionSn()
                                + "因用户发起退款货或其他原因已被关闭，无需采购发货，请知晓！";
                        PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                        MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                        msgBean.setPic(rqItem.getProductImg());
                        SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                                SmsLogType.REMIND, content);
                        msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                    }
                }
            }
            return success;
        } else {
            throw new BusinessException("已签收调拨单，不允许关闭！");
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doCancelClose(Long id, Integer type, String contact, String tel, String address, Long storeId,
                             String storeName, String operator, String operation, Long requisitionId, Integer direct) {
        int success = requisitionItemMapper.doCancelClose(id, type, contact, tel, address, storeId, storeName, direct);
        if (success > 0) {
            RequisitionItem rqItem = this.findOneById(id);
            if (rqItem.getOrderItemId() != null) {
                // 重新设置为调拨中
                orderItemService.updateRequisition(rqItem.getOrderItemId(), 1);
            }
            JSONObject info = new JSONObject();
            info.put("操作", operation);
            this.insertLog(id, rqItem.getRequisitionSn(), "重新打开", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doLock(Long id, Integer lock, String operator) {
        RequisitionItem requisitionItem = this.findById(id);
        if (lock == 1 && requisitionItem.getLocked() == 1) {
            throw new BusinessException("正在拦截中，请勿重复操作！");
        }
        int success = requisitionItemMapper.doLock(id, lock);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", lock == 1 ? "拦截调拨" : "取消拦截");
            this.insertLog(id, requisitionItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
            if (lock == 1 && (requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode())) { // 调拨拦截后发短信
                // 发送站内消息
                Brand brand = brandService.findById(requisitionItem.getDesignerId());
                MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                if (memberInfo != null) {
                    String subject = "采购单提醒";
                    String content = "亲爱的" + requisitionItem.getDesignerName() + "品牌主理人，您有采购单被拦截，采购单号 "
                            + requisitionItem.getRequisitionSn() + "，如有疑问，请咨询客服。";
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                    msgBean.setPic(requisitionItem.getProductImg());
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                }
            } else if (lock == 1 && requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                // 发送站内消息
                Brand brand = brandService.findById(requisitionItem.getDesignerId());
                MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                if (memberInfo != null) {
                    String subject = "调拨单提醒";
                    String content = "亲爱的" + requisitionItem.getStoreName() + "店长，您有调拨单被拦截，调拨单号 "
                            + requisitionItem.getRequisitionSn() + "，如有疑问，请咨询客服。";
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                    msgBean.setPic(requisitionItem.getProductImg());
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
                }
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doHandle(Long id, Integer handle, String operator, String memo) {
        int success = requisitionItemMapper.doHandle(id, handle, operator);
        if (success > 0) {
            RequisitionItem requisitionItem = this.findById(id);
            JSONObject info = new JSONObject();
            if (handle == 1) {
                info.put("操作", "调拨单标记为已处理");
            } else {
                info.put("操作", "调拨单取消处理");
            }
            info.put("操作时间", DateUtil.second2str(new Date()));
            info.put("备注", memo);
            this.insertLog(requisitionItem.getId(), requisitionItem.getRequisitionSn(), "处理调拨单", info.toJSONString(),
                    operator);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doCloseByOrderItem(Long orderItemId, String closeMan, String closeReason) {
        RequisitionItem rqItem = requisitionItemMapper.findByOrderItemId(orderItemId);
        if (rqItem == null) {
            throw new BusinessException("商品调拨明细不存在！");
        }
        if (rqItem.getStatus() < 0) {
            throw new BusinessException("商品调拨明细已关闭成功！");
        }
        int success = 1;
        if (rqItem.getStatus() == 0 || rqItem.getStatus() == 1 || rqItem.getStatus() == 2) {
            success = this.doClose(rqItem.getId(), closeMan, closeReason);
        } else if (rqItem.getStatus() == 8) {
            return 1;
        }
        if (success <= 0) {
            throw new BusinessException("调拨单状态不匹配，取消不成功！");
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doDeliverToCustomer(Long id, String deliveryBarCode, String deliveryCorpName, String deliveryNo,
                                   String operator) {
        RequisitionItem requisitionItem = requisitionItemMapper.selectByPrimaryKey(id);
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(requisitionItem.getOrderItemId());
        if (orderItem == null) {
            if ((requisitionItem.getType() == 2 || requisitionItem.getType() == 4)) {
                OrderItem orderItem2 = (OrderItemDto) orderItemService.findByOrderSnAndSku(requisitionItem.getOrderSn(),
                        requisitionItem.getBarCode());
                if (orderItem2 == null) {
                    throw new BusinessException("订单明细未找到，发货不成功！");
                }
                orderItem = orderItemService.findOrderItemDtoById(orderItem2.getId());
            } else {
                throw new BusinessException("调拨单类型不匹配，发货不成功！");
            }
        }
        if (!orderItem.getAftering().equals("none")) {
            throw new BusinessException("订单明细处于售后中，发货不成功！");
        }
        int success = orderItemService.doDeliveryItem(orderItem.getId(), 0, deliveryBarCode, deliveryCorpName,
                deliveryNo, operator, true, orderItem.getProductQuantity());
        if (success > 0) {
            requisitionItemMapper.doDeliverToCustomer(id);
            JSONObject info = new JSONObject();
            info.put("操作", "仓库直接发货给消费者");
            this.insertLog(id, requisitionItem.getRequisitionSn(), "仓库直发", info.toJSONString(), operator);
            requisitionItem.setDelivered(1);
            if (requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                if ("D2C101".equals(storeService.findById(requisitionItem.getStoreId()).getCode())) {
                    processBurgeon(requisitionItem);
                }
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doDeliverByOrderItem(Long orderItemId) {
        int success = requisitionItemMapper.doDeliverByOrderItem(orderItemId);
        if (success > 0) {
            RequisitionItem requisitionItem = this.findByOrderItemId(orderItemId);
            if (requisitionItem.getStatus() == 8 && requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                    && SaleCategory.NORMALPRODUCT.toString().equals(requisitionItem.getSaleCategory())) {
                requisitionItem.setDelivered(1);
                this.processBurgeon(requisitionItem);
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doFine(Long id, String sn, Integer fine) {
        RequisitionItem item = this.findById(id);
        int success = requisitionItemMapper.doFine(id, fine);
        if (success > 0) {
            JSONObject info = new JSONObject();
            if (fine == 1) {
                info.put("操作", "品牌" + item.getDesignerName() + "操作超时产生罚款");
            } else if (fine == 2) {
                info.put("操作", "品牌" + item.getDesignerName() + "操作超时产生赔偿单结算");
            }
            info.put("操作时间", DateUtil.second2str(new Date()));
            this.insertLog(id, sn, "处理采购单", info.toJSONString(), "系统");
        }
        return success;
    }

    @Override
    public int doReship(Long id, String operator) {
        RequisitionItem rqItem = this.findById(id);
        this.doRequisition(rqItem, operator);
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doStoreComment(Long id, String storeComment, Integer responseSpeed, Long storeId, String operator) {
        RequisitionItem item = this.findOneById(id);
        if (item == null) {
            throw new BusinessException("该采购单不存在！");
        }
        if (item.getType() != ItemType.STOREPCH.getCode()) {
            throw new BusinessException("单据类型不正确，无权限评价！");
        }
        if (item.getStatus() != RequisitionItem.ItemStaus.CLOSE.getCode()) {
            throw new BusinessException("采购单状态不正确！");
        }
        if (item.getResponseSpeed() != null) {
            throw new BusinessException("该采购单已评价！");
        }
        int success = requisitionItemMapper.doStoreComment(id, storeComment, responseSpeed);
        if (success > 0) {
            Store store = storeService.findById(storeId);
            JSONObject info = new JSONObject(true);
            info.put("操作", "采购单门店评论");
            info.put("操作时间", DateUtil.second2str(new Date()));
            if (store != null) {
                info.put("操作门店", store.getName());
            }
            info.put("操作内容",
                    "门店评价响应速度为" + RequisitionItem.SpeedType.getByCode(responseSpeed).getName() + "，评价：" + storeComment);
            this.insertLog(id, item.getRequisitionSn(), "优先级修改", info.toJSONString(), operator);
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateRemark(Long id, String remark, String operator) {
        int success = requisitionItemMapper.updateRemark(id, remark);
        if (success > 0) {
            RequisitionItem rqItem = this.findOneById(id);
            JSONObject info = new JSONObject();
            info.put("操作", "运营修改调拨商品备注");
            info.put("备注内容", remark);
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateStoreMemo(Long id, String memo, String operator) {
        int success = requisitionItemMapper.updateStoreMemo(id, memo);
        if (success > 0) {
            RequisitionItem rqItem = this.findOneById(id);
            JSONObject info = new JSONObject();
            info.put("操作", "门店修改调拨商品备注");
            info.put("备注内容", memo);
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateDesignerMemo(Long id, String memo, String operator) {
        int success = requisitionItemMapper.updateDesignerMemo(id, memo);
        if (success > 0) {
            RequisitionItem rqItem = this.findOneById(id);
            JSONObject info = new JSONObject();
            info.put("操作", "设计师修改调拨商品备注");
            info.put("备注内容", memo);
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateDesignerEstimateDate(Long id, Date date, String memo, String operator) {
        RequisitionItem rqItem = this.findOneById(id);
        int success = requisitionItemMapper.updateDesignerEstimateDate(id, memo, date);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "调整调拨商品预计发货时间");
            info.put("修改数量",
                    "原始：" + DateUtil.day2str(rqItem.getDesignerEstimateDate()) + "，新的：" + DateUtil.day2str(date));
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateQuantity(Long id, Integer quantity, String operator) {
        RequisitionItem rqItem = this.findOneById(id);
        int success = requisitionItemMapper.updateQuantity(id, quantity);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "调整调拨商品数量");
            info.put("修改数量", "原始：" + (rqItem.getQuantity() == null ? "0" : rqItem.getQuantity()) + "，新的：" + quantity);
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateDeliveryQuantity(Long id, Integer quantity, String operator) {
        RequisitionItem rqItem = this.findOneById(id);
        if (quantity > rqItem.getQuantity()) {
            throw new BusinessException("发货数量不能大于采购数量！");
        }
        int success = requisitionItemMapper.updateDeliveryQuantity(id, quantity);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "调整发货商品数量");
            info.put("修改数量", "原始：" + (rqItem.getDeliveryQuantity() == null ? "0" : rqItem.getDeliveryQuantity())
                    + "，新的：" + quantity);
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateDefectiveQuantity(Long id, Integer quantity, String operator) {
        RequisitionItem rqItem = this.findOneById(id);
        // if (rqItem.getReceiveQuantity() + quantity >
        // rqItem.getDeliveryQuantity()) {
        // throw new BusinessException("次品和实收数量不能大于发货数量！");
        // }
        int success = requisitionItemMapper.updateDefectiveQuantity(id, quantity);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "调整次品数量");
            info.put("修改数量", "原始：" + (rqItem.getDefectiveQuantity() == null ? "0" : rqItem.getDefectiveQuantity())
                    + "，新的：" + quantity);
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateReceiveQuantity(Long id, Integer quantity, String operator) {
        RequisitionItem rqItem = this.findOneById(id);
        if (rqItem.getDefectiveQuantity() + quantity > rqItem.getDeliveryQuantity()) {
            throw new BusinessException("次品和实收数量不能大于发货数量！");
        }
        int success = requisitionItemMapper.updateReceiveQuantity(id, quantity);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "调整入库商品数量");
            info.put("修改数量", "原始：" + (rqItem.getReceiveQuantity() == null ? "0" : rqItem.getReceiveQuantity()) + "，新的："
                    + quantity);
            this.insertLog(id, rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateBarCode(Long orderItemId, String barCode, String operator) {
        RequisitionItem rqItem = requisitionItemMapper.findByOrderItemId(orderItemId);
        if (rqItem == null) {
            return 0;
        }
        int success = 0;
        if (rqItem.getStatus() == RequisitionItem.ItemStaus.INIT.getCode()
                || rqItem.getStatus() == RequisitionItem.ItemStaus.WAITSIGN.getCode()) {
            String sp1 = "";
            String sp2 = "";
            ProductSku sku = productSkuService.findByBarCode(barCode);
            if (sku != null) {
                sp1 = sku.getSp1();
                sp2 = sku.getSp2();
            }
            success = requisitionItemMapper.updateBarCode(rqItem.getId(), barCode, sp1, sp2);
            if (success > 0) {
                JSONObject info = new JSONObject();
                info.put("操作", "根据订单明细修改条码");
                info.put("修改条码", "原始：" + rqItem.getBarCode() + "，新的：" + barCode);
                this.insertLog(rqItem.getId(), rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
            }
        } else if (rqItem.getStatus() > RequisitionItem.ItemStaus.WAITSIGN.getCode()) {
            throw new BusinessException("设计师已经签收，修改不成功，请进行锁定拦截操作！");
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateLogisticAddress(Long id, String contact, String tel, String address, String operator,
                                     String allocateReason) {
        RequisitionItem rqItem = this.findOneById(id);
        int success = requisitionItemMapper.updateLogisticAddress(id, contact, tel, address, allocateReason);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "修改收货地址");
            info.put("原地址", "联系人：" + rqItem.getContact() + "，联系电话：" + rqItem.getTel() + "地址：" + rqItem.getAddress());
            info.put("新地址", "联系人：" + contact + "，联系电话：" + tel + "地址：" + address);
            this.insertLog(rqItem.getId(), rqItem.getRequisitionSn(), "修改", info.toJSONString(), operator);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateDeliveryInfo(Long id, String deliveryCorp, String deliverySn, String operator) {
        RequisitionItem rqItem = this.findOneById(id);
        int result = requisitionItemMapper.updateDeliveryInfo(id, deliveryCorp, deliverySn, operator);
        if (result > 0) {
            JSONObject info = new JSONObject(true);
            info.put("操作", "物流修改");
            info.put("物流公司", deliveryCorp);
            info.put("物流单号", deliverySn);
            info.put("操作时间", DateUtil.second2str(new Date()));
            this.insertLog(rqItem.getId(), rqItem.getRequisitionSn(), "物流修改", info.toJSONString(), operator);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updatePriority(Long id, Integer priority, String operator) {
        RequisitionItem rqItem = this.findOneById(id);
        int result = requisitionItemMapper.updatePriority(id, priority);
        if (result > 0) {
            JSONObject info = new JSONObject(true);
            info.put("操作", "优先级修改");
            info.put("优先级", priority);
            info.put("操作时间", DateUtil.second2str(new Date()));
            this.insertLog(rqItem.getId(), rqItem.getRequisitionSn(), "优先级修改", info.toJSONString(), operator);
        }
        return result;
    }

    /**
     * 调拨单
     *
     * @throws Exception
     */
    private void sendBurgeonOnTransfer(RequisitionItem item, int mark) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        Store store = storeService.findById(item.getStoreId());
        JSONObject data = new JSONObject();
        data.put("refno", item.getRequisitionSn().replace("RE", "TF"));
        if (!"003".equals(store.getCode())
                && (item.getType() == ItemType.STOREPCH.getCode() || item.getType() == ItemType.GOODSPCH.getCode())) {// 门店或货需采购-门店发起的，采购后调拨
            data.put("orig", "101");// 官网零售仓001 官网加工仓002 顺丰仓003 直发仓101
            data.put("dest", store.getCode());
            data.put("remark",
                    "外部编号:" + item.getRequisitionSn() + "，设计师" + processInvalidParamForBurgeon(item.getDesignerName())
                            + "发给门店" + store.getName() + "，系统自动做采购入库单后调拨至门店" + store.getName());
            // D2C101加工仓发货线上的订单，需要调拨到零售仓001，然后做零售单
            if ("D2C101".equals(store.getCode()) && item.getDelivered() == 1) {
                data.put("orig", "D2C101");// 官网零售仓001 官网加工仓002 顺丰仓003 直发仓101
                data.put("dest", "001");
                data.put("remark", "外部编号:" + item.getRequisitionSn() + "，加工仓发货，系统自动做调拨单到零售仓");
            }
        } else if ("003".equals(store.getCode())
                && (item.getType() == ItemType.STOREPCH.getCode() || item.getType() == ItemType.GOODSPCH.getCode())) { // 门店或货需采购-官网发起的，采购后调拨
            data.put("orig", "002");// 官网加工仓002 顺丰仓003
            data.put("dest", "003");
            data.put("remark",
                    "外部编号:" + item.getRequisitionSn() + "，设计师" + processInvalidParamForBurgeon(item.getDesignerName())
                            + "发给门店" + store.getName() + "，系统自动做采购入库单后调拨至门店");
        } else if (item.getType() == ItemType.STOREALLC.getCode()) {// 门店调拨-门店发货业务
            data.put("orig", store.getCode());
            OrderItem orderItem = orderItemService.findById(item.getOrderItemId());
            if (orderItem != null && orderItem.getDplusCode() != null) { // D+店的订单调拨到D+店，由D+店做零售，算D+店业绩
                data.put("dest", orderItem.getDplusCode());
                data.put("remark", "外部编号:" + item.getRequisitionSn() + "，门店 " + store.getName() + "发货后，系统自动做调拨单到D+店"
                        + orderItem.getDplusCode());
            } else {
                data.put("dest", "001");
                data.put("remark",
                        "外部编号:" + item.getRequisitionSn() + "，门店 " + store.getName() + "发给消费者，系统自动做调拨单到官网零售仓");
            }
        } else if (item.getType() == ItemType.LEAGUEAPPLY.getCode()) {// D+店申请的调拨从D2C022发出，D2C022的库存由管易发货到该门店
            if (BusTypeEnum.DPLUS.name().equals(store.getBusType())) {
                if (item.getStatus() == 8) {
                    data.put("orig", "D2C022");
                    data.put("dest", store.getCode());
                    data.put("remark",
                            "外部编号:" + item.getRequisitionSn() + "，sku:" + item.getBarCode() + "，确认收货后，系统自动做调拨单");
                }
            }
        } else if (item.getType() == ItemType.DEPOTALLC.getCode() && "D2C101".equals(store.getCode())) {// 普通门店（仓库）的仓库调拨（目前只有退换货用到）
            OrderItem oi = orderItemService.findById(item.getOrderItemId());
            if (item.getStatus() == 2) {// 退换货单仓库确认后生成，从零售仓到销退仓078（实际上并没有从零售仓发货，但是因为零售退货到零售仓，所以从零售仓出，做到销退仓。随后需发往加工仓）
                if (oi.getDplusCode() != null) { // D+店销退，销退到D+店后，从D+店调拨去销退仓
                    data.put("orig", oi.getDplusCode());
                } else {
                    data.put("orig", "001");
                }
                data.put("dest", "078");
                data.put("remark",
                        "订单编号:" + oi.getOrderSn() + "，sku:" + item.getBarCode() + "退换货,仓库确认收货后，系统自动做零售退货单后做调拨单");
            }
            if (item.getStatus() == 8) {// 加工仓确认后，销退仓078做到加工仓
                data.put("orig", "078");
                data.put("dest", "D2C101");
                data.put("remark", "订单编号:" + oi.getOrderSn() + "，sku:" + item.getBarCode() + "退换货，加工仓确认收货后，系统自动做调拨单");
            }
        } else if (item.getType() == ItemType.LEAGUERETURN.getCode()) {
            data.put("refno", item.getRequisitionSn().replace("RE", "TF") + "_" + mark);
            if (AllocateType.UNSALE.name().equals(item.getAllocateReason())) {
                if (mark == 0) {
                    data.put("orig", store.getCode());
                    data.put("dest", "D2C022");
                    data.put("remark", "调拨订单:" + item.getRequisitionSn() + "，" + item.getStoreName() + "，物流:"
                            + item.getDeliveryCorp() + item.getDeliverySn() + "，系统自动做调拨单（次品退回）从D+店到中转仓");
                }
                if (mark == 1) {
                    data.put("orig", "D2C022");
                    data.put("dest", "D2C101");
                    data.put("remark", "调拨订单:" + item.getRequisitionSn() + "，" + item.getStoreName() + "，物流:"
                            + item.getDeliveryCorp() + item.getDeliverySn() + "，系统自动做调拨单（次品退回）从中转仓到加工仓");
                }
            }
            if (AllocateType.INFERIOR.name().equals(item.getAllocateReason())) {
                if (mark == 0) {
                    data.put("orig", store.getCode());
                    data.put("dest", "D2C022");
                    data.put("remark", "调拨订单:" + item.getRequisitionSn() + "，" + item.getStoreName() + "，物流:"
                            + item.getDeliveryCorp() + item.getDeliverySn() + "，系统自动做调拨单（次品退回）从D+店到中转仓");
                }
                if (mark == 1) {
                    data.put("orig", "D2C022");
                    data.put("dest", "007");
                    data.put("remark", "调拨订单:" + item.getRequisitionSn() + "，" + item.getStoreName() + "，物流:"
                            + item.getDeliveryCorp() + item.getDeliverySn() + "，系统自动做调拨单（次品退回）从中转仓到样品仓");
                }
            }
        } else if (item.getType() == ItemType.ORDERPCH.getCode()) {
            OrderItem oi = orderItemService.findById(item.getOrderItemId());
            if (oi != null && oi.getDplusCode() != null) {// D+店的订单触发订单采购，采购到官网后，需要调拨到D+店
                data.put("orig", "001");
                data.put("dest", oi.getDplusCode());
                data.put("remark", "调拨订单:" + item.getRequisitionSn() + "，" + item.getStoreName() + "，物流:"
                        + item.getDeliveryCorp() + item.getDeliverySn() + "，系统自动做调拨单（D+店订单的订单采购）从官网零售仓到D+店");
            }
        } else {
            return;
        }
        data.put("billdate", fmt.format(nowDate));
        data.put("dateout", fmt.format(nowDate));
        data.put("datein", fmt.format(nowDate));
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemqtyout = new JSONArray();
        JSONArray itemqtyin = new JSONArray();
        itemsku.add(item.getBarCode());
        itemqty.add(item.getReceiveQuantity().toString());
        itemqtyout.add(item.getReceiveQuantity().toString());
        itemqtyin.add(item.getReceiveQuantity().toString());
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemqtyout", itemqtyout);
        data.put("itemqtyin", itemqtyin);
        BurgeonClient.getInstance().sendBurgeon(data, "transfer");
    }

    /**
     * 采购单
     *
     * @throws Exception
     */
    private void sendBurgeonOnPur(RequisitionItem item) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        Store store = storeService.findById(item.getStoreId());
        JSONObject data = new JSONObject();
        data.put("refno", item.getRequisitionSn());
        if (!"003".equals(store.getCode())
                && (item.getType() == ItemType.STOREPCH.getCode() || item.getType() == ItemType.GOODSPCH.getCode())) {// 门店发起的门店或货需采购发往直发仓101
            data.put("store", "101");
            data.put("remark",
                    "外部编号:" + item.getRequisitionSn() + "设计师" + processInvalidParamForBurgeon(item.getDesignerName())
                            + "发给门店" + store.getName() + "，系统自动做采购入库单至直发仓");
        } else if ("003".equals(store.getCode())
                && (item.getType() == ItemType.STOREPCH.getCode() || item.getType() == ItemType.GOODSPCH.getCode())) {// 官网发起的门店或货需采购发往官网加工仓002
            data.put("store", "002");
            data.put("remark", "外部编号:" + item.getRequisitionSn() + "，设计师"
                    + processInvalidParamForBurgeon(item.getDesignerName()) + "发给顺丰仓" + "，系统自动做采购入库单至加工仓");
        } else if (item.getType() == ItemType.ORDERPCH.getCode()) {// 订单采购，不管是不是设计师直发，都是从设计师做单到官网LBP仓（货是官网卖的，零售单需要官网出，所以需要采购到官网）
            data.put("store", "001");
            data.put("remark", "外部编号:" + item.getRequisitionSn() + "，设计师"
                    + processInvalidParamForBurgeon(item.getDesignerName()) + "发给官网LBP仓" + "，系统自动做采购入库单至官网零售仓");
        } else {
            return;
        }
        data.put("billdate", fmt.format(nowDate));
        data.put("datein", fmt.format(nowDate));
        data.put("supplier", "0");// 供应商
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemprice = new JSONArray();
        itemsku.add(item.getBarCode());
        itemqty.add(item.getReceiveQuantity().toString());
        itemprice.add("0"); // 采购价
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemprice", itemprice);
        BurgeonClient.getInstance().sendBurgeon(data, "pur");
    }

    /**
     * 零售单
     *
     * @throws Exception
     */
    private void sendBurgeonOnRetail(RequisitionItem item) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        JSONObject data = new JSONObject();
        data.put("refno", item.getRequisitionSn());
        data.put("billdate", fmt.format(nowDate));
        Store store = storeService.findById(item.getStoreId());
        OrderItem oi = orderItemService.findById(item.getOrderItemId());
        if (oi != null && oi.getDplusCode() != null) {
            data.put("store", oi.getDplusCode());// D+店的订单算D+店的零售
        } else {
            data.put("store", "001");// 零售单都是官网LBP仓，线下门店自己做单
        }
        if (item.getType() == ItemType.ORDERPCH.getCode()) {
            data.put("remark", "外部编号:" + item.getRequisitionSn() + "，官网LBP仓发给消费者，系统自动做采购入库单到官网零售仓后做零售单");
        } else {
            data.put("remark",
                    "外部编号:" + item.getRequisitionSn() + "，门店+" + store.getName() + "发给消费者，系统自动做调拨单到官网零售仓后做零售单");
        }
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemprice = new JSONArray();
        JSONArray payway = new JSONArray();
        JSONArray payamt = new JSONArray();
        itemsku.add(item.getBarCode());
        itemqty.add(item.getQuantity().toString());
        BigDecimal price = oi.getActualAmount().divide(new BigDecimal(item.getQuantity()), 2, BigDecimal.ROUND_HALF_UP);
        itemprice.add(price.toString());
        payway.add("官网支付");
        payamt.add(price.multiply(new BigDecimal(item.getQuantity())).toString());
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemprice", itemprice);
        data.put("payway", payway);
        data.put("payamt", payamt);
        BurgeonClient.getInstance().sendBurgeon(data, "retail");
    }

    /**
     * 零售退货单
     *
     * @param item
     * @throws Exception
     */
    private void sendRetretail(RequisitionItem item) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        JSONObject data = new JSONObject();
        data.put("refno", item.getRequisitionSn().replace("RE", "RR"));
        data.put("billdate", fmt.format(nowDate));
        OrderItem orderItem = orderItemService.findById(item.getOrderItemId());
        if (orderItem.getDplusCode() != null) { // D+店订单销退到D+店，再调拨到销退仓
            data.put("store", orderItem.getDplusCode());
        } else {
            data.put("store", "001");// 官网零售退货单全部做到LBP仓
        }
        OrderItem oi = orderItemService.findById(item.getOrderItemId());
        data.put("remark", "订单编号:" + item.getRelationSn() + "，sku:" + oi.getProductSkuSn() + "退换货,仓库确认收货后，系统自动做零售退货单");
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemprice = new JSONArray();
        JSONArray payway = new JSONArray();
        JSONArray payamt = new JSONArray();
        itemsku.add(item.getBarCode());
        itemqty.add(item.getQuantity().toString());
        BigDecimal price = oi.getActualAmount().divide(new BigDecimal(item.getQuantity()), 2, BigDecimal.ROUND_HALF_UP);
        itemprice.add(price.toString());
        payway.add("官网支付");
        payamt.add("-" + price.multiply(new BigDecimal(item.getQuantity())).toString());
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemprice", itemprice);
        data.put("payway", payway);
        data.put("payamt", payamt);
        BurgeonClient.getInstance().sendBurgeon(data, "retretail");
    }

    private String processInvalidParamForBurgeon(String name) {
        return name.replace("'", " ");
    }

    /**
     * 伯俊单据处理
     *
     * @param requisitionItem
     * @throws Exception
     */
    private void processBurgeon(RequisitionItem requisitionItem) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Integer fallCount = 0;
                DocType type = null;
                try {
                    if (requisitionItem.getType() == ItemType.STOREPCH.getCode()
                            || requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                        Store store = storeService.findById(requisitionItem.getStoreId());
                        if (!"D2C101".equals(store.getCode()) || requisitionItem.getDelivered() == 0) {
                            fallCount = 2;
                            type = DocType.Pur;
                            sendBurgeonOnPur(requisitionItem);
                            if (!"003".equals(store.getCode())) {
                                fallCount = 1;
                                type = DocType.Transfer;
                                sendBurgeonOnTransfer(requisitionItem, 0);
                            }
                        } else {
                            // 强行拼接在原流程上，采购到加工仓后，加工仓发货，做调拨到零售仓，再从零售仓做零售
                            fallCount = 2;
                            type = DocType.Transfer;
                            sendBurgeonOnTransfer(requisitionItem, 0);
                            fallCount = 1;
                            type = DocType.Retail;
                            sendBurgeonOnRetail(requisitionItem);
                        }
                    }
                    if (requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                        fallCount = 2;
                        type = DocType.Transfer;
                        sendBurgeonOnTransfer(requisitionItem, 0);
                        fallCount = 1;
                        type = DocType.Retail;
                        sendBurgeonOnRetail(requisitionItem);
                    }
                    if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()) {
                        Product product = productService.findById(requisitionItem.getProductId());
                        if ("NORMALPRODUCT".equals(product.getSaleCategory())) {
                            if (requisitionItem.getDirect() == 0) {
                                if (requisitionItem.getDelivered() == 0) {
                                    fallCount = 1;
                                    type = DocType.Pur;
                                    sendBurgeonOnPur(requisitionItem);
                                }
                                if (requisitionItem.getDelivered() == 1) {
                                    fallCount = 1;
                                    type = DocType.Retail;
                                    sendBurgeonOnRetail(requisitionItem);
                                }
                            } else {
                                OrderItem oi = orderItemService.findById(requisitionItem.getOrderItemId());
                                if (oi != null && oi.getDplusCode() != null) {
                                    fallCount = 3;
                                    type = DocType.Pur;
                                    sendBurgeonOnPur(requisitionItem);
                                    fallCount = 2;
                                    type = DocType.Transfer;
                                    sendBurgeonOnTransfer(requisitionItem, 0);
                                    fallCount = 1;
                                    type = DocType.Retail;
                                    sendBurgeonOnRetail(requisitionItem);
                                } else {
                                    fallCount = 2;
                                    type = DocType.Pur;
                                    sendBurgeonOnPur(requisitionItem);
                                    fallCount = 1;
                                    type = DocType.Retail;
                                    sendBurgeonOnRetail(requisitionItem);
                                }
                            }
                        }
                    }
                    if (requisitionItem.getType() == ItemType.DEPOTALLC.getCode()) {// TODO如果status=2调拨失败时，重做status=8，会造成漏调拨单
                        if (requisitionItem.getStatus() == 2) {
                            Store store = storeService.findById(requisitionItem.getStoreId());
                            if ("D2C101".equals(store.getCode())) {// 仓库调拨发往加工仓百通的都是销退，需要先做到销退仓078
                                requisitionItem.setRequisitionSn("DA" + requisitionItem.getRequisitionSn());
                                fallCount = 2;
                                type = DocType.Retretail;
                                sendRetretail(requisitionItem);
                                fallCount = 1;
                                type = DocType.Transfer;
                                sendBurgeonOnTransfer(requisitionItem, 0);
                            }
                        }
                        if (requisitionItem.getStatus() == 8) {
                            fallCount = 1;
                            type = DocType.Transfer;
                            sendBurgeonOnTransfer(requisitionItem, 0);
                        }
                    }
                    if (requisitionItem.getType() == ItemType.LEAGUEAPPLY.getCode()) {
                        fallCount = 1;
                        type = DocType.Transfer;
                        sendBurgeonOnTransfer(requisitionItem, 0);
                    }
                    if (requisitionItem.getType() == ItemType.LEAGUERETURN.getCode()) {
                        fallCount = 2;
                        type = DocType.Transfer;
                        sendBurgeonOnTransfer(requisitionItem, 0);
                        fallCount = 1;
                        type = DocType.Transfer;
                        sendBurgeonOnTransfer(requisitionItem, 1);
                    }
                } catch (Exception e) { // 失败不回滚，记下出错日志
                    BurgeonErrorLog burgeonErrorLog = new BurgeonErrorLog();
                    burgeonErrorLog.setSourceType(DocSourceType.REQUISITION.name());
                    burgeonErrorLog.setBillDate(new Date());
                    burgeonErrorLog.setBurgeonSn(requisitionItem.getRequisitionSn());
                    burgeonErrorLog.setRequisitionSn(requisitionItem.getRequisitionSn());
                    burgeonErrorLog.setOrderSn(requisitionItem.getOrderSn());
                    burgeonErrorLog.setProductSku(requisitionItem.getBarCode());
                    burgeonErrorLog.setType(type.toString());
                    burgeonErrorLog.setQuantity(requisitionItem.getQuantity());
                    burgeonErrorLog.setError(type.getDisplay() + "失败:"
                            + (StringUtils.isBlank(e.getMessage()) ? "伯俊系统响应超时." : e.getMessage()));
                    burgeonErrorLog.setSourceId(requisitionItem.getId());
                    burgeonErrorLog.setFallCount(fallCount);
                    burgeonErrorLogService.insert(burgeonErrorLog);
                }
            }
        });
        executor.shutdown();
    }

    /**
     * 伯俊重新做单
     */
    @Override
    public int doReProcess(Long id) {
        int success = 1;
        BurgeonErrorLog burgeonErrorLog = burgeonErrorLogService.findById(id);
        if (DocSourceType.ORDERITEM.name().equals(burgeonErrorLog.getSourceType())) {
            return this.doReProcessOrderItem(burgeonErrorLog);
        }
        Integer fallCount = 2;
        DocType type = null;
        try {
            RequisitionItem requisitionItem = this.findById(burgeonErrorLog.getSourceId());
            if (requisitionItem.getType() == ItemType.STOREPCH.getCode()
                    || requisitionItem.getType() == ItemType.GOODSPCH.getCode()) {
                Store store = storeService.findById(requisitionItem.getStoreId());
                if (!"D2C101".equals(store.getCode()) || requisitionItem.getDelivered() == 0) {
                    if (burgeonErrorLog.getFallCount() == 2) {
                        fallCount = 2;
                        type = DocType.Pur;
                        this.sendBurgeonOnPur(requisitionItem);
                    }
                    if (!"003".equals(store.getCode())) {
                        fallCount = 1;
                        type = DocType.Transfer;
                        this.sendBurgeonOnTransfer(requisitionItem, 0);
                    }
                } else {
                    // 强行拼接在原流程上，采购到加工仓后，加工仓发货，做调拨到零售仓，再从零售仓做零售
                    if (burgeonErrorLog.getFallCount() == 2) {
                        fallCount = 2;
                        type = DocType.Transfer;
                        sendBurgeonOnTransfer(requisitionItem, 0);
                    }
                    fallCount = 1;
                    type = DocType.Retail;
                    sendBurgeonOnRetail(requisitionItem);
                }
            }
            if (requisitionItem.getType() == ItemType.STOREALLC.getCode()) {
                if (burgeonErrorLog.getFallCount() == 2) {
                    fallCount = 2;
                    type = DocType.Transfer;
                    this.sendBurgeonOnTransfer(requisitionItem, 0);
                }
                fallCount = 1;
                type = DocType.Retail;
                this.sendBurgeonOnRetail(requisitionItem);
            }
            if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()) {
                OrderItem oi = orderItemService.findById(requisitionItem.getOrderItemId());
                if (oi != null && oi.getDplusCode() != null) {// D+店的订单采购流程。采购到官网零售仓，调拨到D+店，再从D+店零售出去
                    if (burgeonErrorLog.getFallCount() == 3) {
                        fallCount = 3;
                        type = DocType.Pur;
                        sendBurgeonOnPur(requisitionItem);
                        fallCount = 2;
                        type = DocType.Transfer;
                        sendBurgeonOnTransfer(requisitionItem, 0);
                    } else if (burgeonErrorLog.getFallCount() == 2) {
                        fallCount = 2;
                        type = DocType.Transfer;
                        sendBurgeonOnTransfer(requisitionItem, 0);
                    }
                    fallCount = 1;
                    type = DocType.Retail;
                    sendBurgeonOnRetail(requisitionItem);
                } else {
                    if (burgeonErrorLog.getFallCount() == 2) {// 原来的两单连做
                        fallCount = 2;
                        type = DocType.Pur;
                        this.sendBurgeonOnPur(requisitionItem);
                        fallCount = 1;
                        type = DocType.Retail;
                        this.sendBurgeonOnRetail(requisitionItem);
                    }
                    if (burgeonErrorLog.getFallCount() == 1 && DocType.Pur.name().equals(burgeonErrorLog.getType())) { // 补做采购
                        fallCount = 1;
                        type = DocType.Pur;
                        this.sendBurgeonOnPur(requisitionItem);
                    }
                    if (burgeonErrorLog.getFallCount() == 1
                            && DocType.Retail.name().equals(burgeonErrorLog.getType())) {// 补做零售
                        fallCount = 1;
                        type = DocType.Retail;
                        this.sendBurgeonOnRetail(requisitionItem);
                    }
                }
            }
            if (requisitionItem.getType() == ItemType.DEPOTALLC.getCode()) {
                if (burgeonErrorLog.getFallCount() == 2) {
                    fallCount = 2;
                    type = DocType.Retretail;
                    requisitionItem.setStatus(2);
                    requisitionItem.setRequisitionSn("DA" + requisitionItem.getRequisitionSn());
                    this.sendRetretail(requisitionItem);
                }
                if (burgeonErrorLog.getRequisitionSn().contains("DA")) {
                    requisitionItem.setStatus(2);
                    requisitionItem.setRequisitionSn("DA" + requisitionItem.getRequisitionSn());
                    requisitionItem.setReceiveQuantity(requisitionItem.getQuantity());
                }
                fallCount = 1;
                type = DocType.Transfer;
                this.sendBurgeonOnTransfer(requisitionItem, 0);
            }
            if (requisitionItem.getType() == ItemType.LEAGUEAPPLY.getCode()) {
                fallCount = 1;
                type = DocType.Transfer;
                this.sendBurgeonOnTransfer(requisitionItem, 0);
            }
            if (requisitionItem.getType() == ItemType.LEAGUERETURN.getCode()) {
                if (burgeonErrorLog.getFallCount() == 2) {
                    fallCount = 2;
                    type = DocType.Transfer;
                    this.sendBurgeonOnTransfer(requisitionItem, 0);
                }
                fallCount = 1;
                type = DocType.Transfer;
                this.sendBurgeonOnTransfer(requisitionItem, 1);
            }
        } catch (Exception e) { // 失败不回滚，记下出错日志
            success = 0;
            burgeonErrorLog.setType(type.name());
            burgeonErrorLog.setError(type.getDisplay() + "失败:" + e.getMessage());
            burgeonErrorLog.setFallCount(fallCount);
            burgeonErrorLogService.update(burgeonErrorLog);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateReceive(Long id) {
        return requisitionItemMapper.updateReceive(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSku(Long id, Long skuId, String operator) {
        RequisitionItem item = this.findOneById(id);
        ProductSku sku = productSkuService.findById(skuId);
        if (sku == null) {
            throw new BusinessException("该sku不存在");
        }
        int success = 0;
        if (item.getOrderItemId() != null) {
            success = orderItemService.updateBarcodeById(sku.getBarCode(), item.getOrderItemId(), operator);
        } else {
            // 导入采购单
            if (item.getStatus() == RequisitionItem.ItemStaus.INIT.getCode()
                    || item.getStatus() == RequisitionItem.ItemStaus.WAITSIGN.getCode()) {
                success = requisitionItemMapper.updateBarCode(id, sku.getBarCode(), sku.getSp1(), sku.getSp2());
                if (success > 0) {
                    JSONObject info = new JSONObject();
                    info.put("操作", "根据订单明细修改条码");
                    info.put("修改条码", "原始：" + item.getBarCode() + "，新的：" + sku.getBarCode());
                    this.insertLog(item.getId(), item.getRequisitionSn(), "修改", info.toJSONString(), operator);
                }
            } else if (item.getStatus() > RequisitionItem.ItemStaus.WAITSIGN.getCode()) {
                throw new BusinessException("设计师已经签收，修改不成功，请进行锁定拦截操作！");
            }
        }
        return success;
    }

    @Override
    public PageResult<RequisitionItem> findStoreExpired(PageModel page) {
        PageResult<RequisitionItem> pager = new PageResult<>(page);
        int totalCount = requisitionItemMapper.countStoreExpired();
        if (totalCount > 0) {
            List<RequisitionItem> list = requisitionItemMapper.findStoreExpired(page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public List<Map<String, Object>> findExpiredByStoreId(Date deadline) {
        return requisitionItemMapper.findExpireByStoreId(deadline);
    }

    @Override
    public PageResult<RequisitionItem> findDesignerExpired(PageModel page, Date deadline) {
        PageResult<RequisitionItem> pager = new PageResult<>(page);
        int totalCount = requisitionItemMapper.countDesignerExpired(deadline);
        if (totalCount > 0) {
            List<RequisitionItem> list = requisitionItemMapper.findDesignerExpired(page, deadline);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countDesignerExpired(Date deadline) {
        return requisitionItemMapper.countDesignerExpired(deadline);
    }

    /**
     * 通过订单做伯俊单据，目前只有D+店需要，官网订单和线下都又调拨和门店直接做单
     */
    @Override
    public int doBurgeonForOrderItem(Long orderItemId, DocType type) {
        OrderItem orderItem = orderItemService.findById(orderItemId);
        Integer fallCount = 1;
        try {
            if (DocType.Retail.equals(type)) {// D+店购买确认收货，算D+店卖出（业绩算D+店）
                sendRetailForOrderItem(orderItem);
            }
            if (DocType.Retretail.equals(type)) {// D+店卖出销退回D+店
                sendRetretailForOrderItem(orderItem);
            }
            return 1;
        } catch (Exception e) { // 失败不回滚，记下出错日志
            BurgeonErrorLog burgeonErrorLog = new BurgeonErrorLog();
            burgeonErrorLog.setSourceType(DocSourceType.ORDERITEM.name());
            burgeonErrorLog.setBillDate(new Date());
            burgeonErrorLog.setBurgeonSn(orderItem.getOrderSn());
            burgeonErrorLog.setOrderSn(orderItem.getOrderSn());
            burgeonErrorLog.setProductSku(orderItem.getDeliveryBarCode());
            burgeonErrorLog.setType(type.toString());
            burgeonErrorLog.setQuantity(orderItem.getProductQuantity());
            burgeonErrorLog.setError(
                    type.getDisplay() + "失败:" + (StringUtils.isBlank(e.getMessage()) ? "伯俊系统响应超时." : e.getMessage()));
            burgeonErrorLog.setSourceId(orderItem.getId());
            burgeonErrorLog.setFallCount(fallCount);
            burgeonErrorLogService.insert(burgeonErrorLog);
        }
        return 0;
    }

    private int doReProcessOrderItem(BurgeonErrorLog burgeonErrorLog) {
        OrderItem orderItem = orderItemService.findById(burgeonErrorLog.getSourceId());
        DocType type = DocType.valueOf(burgeonErrorLog.getType());
        try {
            if (DocType.Retail.name().equals(burgeonErrorLog.getType())) {// D+店购买确认收货，算D+店卖出（业绩算D+店）
                sendRetailForOrderItem(orderItem);
            }
            if (DocType.Retretail.name().equals(burgeonErrorLog.getType())) {// D+店卖出销退回D+店
                sendRetretailForOrderItem(orderItem);
            }
            return 1;
        } catch (Exception e) { // 失败不回滚，记下出错日志
            burgeonErrorLog.setType(type.name());
            burgeonErrorLog.setError(type.getDisplay() + "失败:" + e.getMessage());
            burgeonErrorLog.setFallCount(1);
            burgeonErrorLogService.update(burgeonErrorLog);
        }
        return 0;
    }

    private void sendRetailForOrderItem(OrderItem orderItem) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        JSONObject data = new JSONObject();
        data.put("refno", orderItem.getOrderSn() + "_" + orderItem.getProductSkuId());
        data.put("billdate", fmt.format(nowDate));
        Store store = storeService.findById(orderItem.getDplusId());
        data.put("store", store.getCode());
        data.put("remark", "订单编号:" + orderItem.getOrderSn() + "，门店" + store.getName() + "发给消费者，系统自动做零售单");
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemprice = new JSONArray();
        JSONArray payway = new JSONArray();
        JSONArray payamt = new JSONArray();
        OrderItem oi = orderItem;
        itemsku.add(orderItem.getDeliveryBarCode());
        itemqty.add(orderItem.getDeliveryQuantity().toString());
        BigDecimal price = oi.getActualAmount().divide(new BigDecimal(orderItem.getDeliveryQuantity()), 2,
                BigDecimal.ROUND_HALF_UP);
        itemprice.add(price.toString());
        payway.add("官网支付");
        payamt.add(price.multiply(new BigDecimal(orderItem.getDeliveryQuantity())).toString());
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemprice", itemprice);
        data.put("payway", payway);
        data.put("payamt", payamt);
        BurgeonClient.getInstance().sendBurgeon(data, "retail");
    }

    private void sendRetretailForOrderItem(OrderItem orderItem) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        JSONObject data = new JSONObject();
        data.put("refno", orderItem.getOrderSn().replace("Q", "RR") + orderItem.getId());
        data.put("billdate", fmt.format(nowDate));
        Store store = storeService.findById(orderItem.getDplusId());
        data.put("store", store.getCode());
        data.put("remark",
                "订单编号:" + orderItem.getOrderSn() + "，sku:" + orderItem.getProductSkuSn() + "退换货,仓库确认收货后，系统自动做零售退货单");
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemprice = new JSONArray();
        JSONArray payway = new JSONArray();
        JSONArray payamt = new JSONArray();
        itemsku.add(orderItem.getDeliveryBarCode());
        itemqty.add(orderItem.getDeliveryQuantity().toString());
        BigDecimal price = orderItem.getActualAmount().divide(new BigDecimal(orderItem.getDeliveryQuantity()), 2,
                BigDecimal.ROUND_HALF_UP);
        itemprice.add(price.toString());
        payway.add("官网支付");
        payamt.add("-" + price.multiply(new BigDecimal(orderItem.getDeliveryQuantity())).toString());
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemprice", itemprice);
        data.put("payway", payway);
        data.put("payamt", payamt);
        BurgeonClient.getInstance().sendBurgeon(data, "retretail");
    }

    private void sendTransferForOrderItem(OrderItem orderItem) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        JSONObject data = new JSONObject();
        data.put("refno", orderItem.getOrderSn().replace("Q", "TF") + orderItem.getId());
        data.put("orig", "D2C022");
        data.put("dest", orderItem.getDplusCode());
        data.put("remark",
                "D+店订单编号：" + orderItem.getOrderSn() + "，sku:" + orderItem.getProductSkuSn() + "，发货后，系统自动做调拨单");
        data.put("billdate", fmt.format(nowDate));
        data.put("dateout", fmt.format(nowDate));
        data.put("datein", fmt.format(nowDate));
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemqtyout = new JSONArray();
        JSONArray itemqtyin = new JSONArray();
        itemsku.add(orderItem.getProductSkuSn());
        itemqty.add(orderItem.getProductQuantity().toString());
        itemqtyout.add(orderItem.getProductQuantity().toString());
        itemqtyin.add(orderItem.getProductQuantity().toString());
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemqtyout", itemqtyout);
        data.put("itemqtyin", itemqtyin);
        BurgeonClient.getInstance().sendBurgeon(data, "transfer");
    }

}
