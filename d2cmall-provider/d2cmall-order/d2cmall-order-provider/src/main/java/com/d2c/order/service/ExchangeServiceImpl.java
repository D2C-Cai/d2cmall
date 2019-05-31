package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.BurgeonErrorLog.DocType;
import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.ExchangeLog.ExchangeLogType;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.ExchangeLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ExchangeMapper;
import com.d2c.order.dto.ExchangeDto;
import com.d2c.order.model.*;
import com.d2c.order.model.Exchange.ExchangeStatus;
import com.d2c.order.model.OrderItem.BusType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.RequisitionItem.ItemStaus;
import com.d2c.order.model.RequisitionItem.ItemType;
import com.d2c.order.query.ExchangeSearcher;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("exchangeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ExchangeServiceImpl extends ListServiceImpl<Exchange> implements ExchangeService {

    @Autowired
    private ExchangeMapper exchangeMapper;
    @Autowired
    private ExchangeLogService exchangeLogService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;

    /**
     * 换货日志
     */
    private void insertExchangeLog(Long exchangeId, Long orderItemId, Long orderId, String info, ExchangeLogType type,
                                   String creator) {
        ExchangeLog exchangeLog = new ExchangeLog();
        exchangeLog.setCreateDate(new Date());
        exchangeLog.setCreator(creator);
        exchangeLog.setInfo(info);
        exchangeLog.setExchangeLogType(type);
        exchangeLog.setExchangeId(exchangeId);
        exchangeLog.setOrderItemId(orderItemId);
        exchangeLog.setOrderId(orderId);
        exchangeLogService.insert(exchangeLog);
    }

    @Override
    public Exchange findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public Exchange findByIdAndMemberInfoId(Long id, Long memberInfoId) {
        return exchangeMapper.findByIdAndMemberInfoId(id, memberInfoId);
    }

    @Override
    public Exchange findBySn(String exchangeSn) {
        return exchangeMapper.findBySn(exchangeSn);
    }

    @Override
    public List<Exchange> findByDeliverySn(String nu) {
        return exchangeMapper.findByDeliverySn(nu);
    }

    @Override
    public PageResult<ExchangeDto> findMine(ExchangeSearcher searcher, PageModel page) {
        PageResult<ExchangeDto> pager = new PageResult<>(page);
        int totalCount = exchangeMapper.countBySearch(searcher);
        List<Exchange> list = new ArrayList<>();
        List<ExchangeDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = exchangeMapper.findBySearch(searcher, page);
            for (Exchange exchange : list) {
                ExchangeDto dto = new ExchangeDto();
                BeanUtils.copyProperties(exchange, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public PageResult<ExchangeDto> findBySearch(ExchangeSearcher searcher, PageModel page) {
        PageResult<ExchangeDto> pager = new PageResult<>(page);
        int totalCount = exchangeMapper.countBySearch(searcher);
        List<Exchange> list = new ArrayList<>();
        List<ExchangeDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = exchangeMapper.findBySearch(searcher, page);
            for (Exchange e : list) {
                ExchangeDto dto = new ExchangeDto();
                BeanUtils.copyProperties(e, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public int countBySearch(ExchangeSearcher searcher) {
        return exchangeMapper.countBySearch(searcher);
    }

    @Override
    public PageResult<Exchange> findDeliveredByDate(Date date, int interval, PageModel page) {
        PageResult<Exchange> pager = new PageResult<>(page);
        int totalCount = exchangeMapper.countDeliveredByDate(date, interval);
        if (totalCount > 0) {
            List<Exchange> list = exchangeMapper.findDeliveredByDate(date, interval, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countDeliveredByDate(Date date, int interval) {
        return exchangeMapper.countDeliveredByDate(date, interval);
    }

    @Override
    public Map<String, Object> countByStatusAndMemberId(Long memberInfoId) {
        List<Map<String, Object>> counts = exchangeMapper.countByStatusAndMemberId(memberInfoId);
        Map<String, Object> map = new HashMap<>();
        int processCount = 0;
        int totalCount = 0;
        for (Map<String, Object> count : counts) {
            Integer status = Integer.parseInt(count.get("exchange_status").toString());
            if (status > -1 && status < 8) {
                processCount = processCount + Integer.parseInt(count.get("counts").toString());
            }
            totalCount = totalCount + Integer.parseInt(count.get("counts").toString());
        }
        map.put("processCount", processCount);
        map.put("totalCount", totalCount);
        return map;
    }

    /**
     * 生成换货单
     *
     * @param exchange
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public Exchange insert(Exchange exchange, String modifyMan) {
        OrderItem orderItem = orderItemService.findById(exchange.getOrderItemId());
        if (orderItem.getStatus().equals(ItemStatus.SUCCESS.toString())) {
            throw new BusinessException("订单交易已经完成，申请换货不成功！");
        }
        exchange = this.save(exchange);
        if (exchange.getId() > 0) {
            // 数据库行锁订单明细，防止重复申请
            int result = orderItemService.doCreateExchange(exchange.getId(), exchange.getOrderItemId());
            if (result <= 0) {
                orderItemService.updateExchange(exchange.getId(), exchange.getOrderItemId());
                orderItemService.updateRefund(null, exchange.getOrderItemId());
                Exchange old = exchangeMapper.selectByPrimaryKey(orderItem.getExchangeId());
                if (old.getExchangeStatus() >= 0) {
                    throw new BusinessException("换货单已经提交了，不能重复申请！");
                }
            }
            String exchangeLog = "用户换货";
            if (exchange.getProxy() == 1) {
                exchangeLog = "客服代" + exchangeLog;
            }
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(), exchangeLog,
                    ExchangeLogType.create, modifyMan);
        }
        return exchange;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doLogistic(Long exchangeId, String deliverCorp, String deliverSn, String modifyMan) {
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        if (!exchange.getExchangeStatus().equals(ExchangeStatus.APPROVE.getCode())) {
            throw new BusinessException("客服没有同意，换货发货指令不成功！");
        }
        int result = exchangeMapper.doLogistic(exchange.getId(), deliverCorp, deliverSn);
        if (result > 0) {
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(),
                    "客服同意，用户已发货，物流公司：" + deliverCorp + "，物流编号：" + deliverSn, ExchangeLogType.sended, modifyMan);
            // 发货后 就将物流号推送到快递100
            logisticsService.createLogistics(deliverCorp, deliverSn, Logistics.BusinessType.EXCHANGE.name(), modifyMan);
        } else {
            throw new BusinessException("物流信息提交不成功，状态不匹配！");
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doReceive(Long exchangeId) {
        return exchangeMapper.doReceive(exchangeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doCancel(Long exchangeId, String modifyMan) {
        String info = "用户取消换货申请";
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        if (!exchange.getExchangeStatus().equals(ExchangeStatus.SUCCESS.getCode())) {
            exchange.setExchangeStatus(ExchangeStatus.MEMBERCLOSE.getCode());
            exchange.setCloser(modifyMan);
            exchange.setCloseDate(new Date());
            int success = exchangeMapper.doCancel(exchange);
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(), info,
                    ExchangeLogType.close, modifyMan);
            return success;
        } else {
            throw new BusinessException("取消换货申请不成功，状态不匹配！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerAgree(Long exchangeId, String modifyMan, String info, BigDecimal exchangePrice, String ip,
                               String backAddress, String backConsignee, String backMobile) {
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        if (exchange == null) {
            throw new BusinessException("换货申请单不存在，提交不成功！");
        }
        if (!exchange.getExchangeStatus().equals(ExchangeStatus.APPLY.getCode())) {
            throw new BusinessException("换货申请审核不成功，状态不匹配，当前状态为" + exchange.getExchangeStatusName());
        }
        if (exchangePrice == null) {
            throw new BusinessException("换货金额不能为空！");
        }
        exchange.setExchangeStatus(ExchangeStatus.APPROVE.getCode());
        exchange.setAuditor(modifyMan);
        exchange.setAuditDate(new Date());
        exchange.setAdminMemo(info);
        exchange.setExchangePrice(exchangePrice);
        int result = exchangeMapper.doCustomAgree(exchange);
        if (result > 0) {
            try {
                Order order = orderService.findById(exchange.getOrderId());
                // 发送短信验证码，港澳台国外号码发送短信时加上区号，大陆地区正常发送，登录账号不计入区号
                Map<String, String> map = new HashMap<>();
                map.put("name", exchange.getLoginCode());
                map.put("refundSn", exchange.getOrderSn());
                doSendExchangeMsg(exchange.getMemberId(), exchange.getLoginCode(), exchange.getId(), map, "换货同意的通知",
                        "尊敬的D2C会员，您的换货申请 （***"
                                + exchange.getOrderSn().substring(exchange.getOrderSn().length() - 6,
                                exchange.getOrderSn().length())
                                + "）客服已同意，请尽快填写物流信息，退货至：" + backAddress + "," + backConsignee + "  收 ，" + backMobile,
                        ip, order.getContact(), exchange.getProductImg());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(), "客服同意换货",
                    ExchangeLogType.modify, modifyMan);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerRefuse(Long exchangeId, String modifyMan, String info) {
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        if (exchange == null || !exchange.getExchangeStatus().equals(ExchangeStatus.APPLY.getCode())) {
            throw new BusinessException("换货申请拒绝不成功，状态不匹配！");
        }
        exchange.setExchangeStatus(ExchangeStatus.REFUSE.getCode());
        exchange.setCloser(modifyMan);
        exchange.setCloseDate(new Date());
        exchange.setAdminMemo(info);
        int result = exchangeMapper.doCustomRefuse(exchange);
        if (result > 0) {
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(), "客服拒绝换货：" + info,
                    ExchangeLogType.close, modifyMan);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerClose(Long exchangeId, String modifyMan, String info) {
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        exchange.setExchangeStatus(ExchangeStatus.CLOSE.getCode());
        exchange.setCloser(modifyMan);
        exchange.setCloseDate(new Date());
        exchange.setAdminMemo(info);
        int result = exchangeMapper.doCustomClose(exchange);
        if (result > 0) {
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(),
                    "客服代用户关闭申请：" + info, ExchangeLogType.close, modifyMan);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerDeliver(Long exchangeId, String modifyMan, String exchangeDeliveryCorp,
                                 String exchangeDeliverySn) {
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        if (exchange.getSkuId() == null) {
            throw new BusinessException("发货不成功，请先选择换货商品！");
        }
        exchange.setExchangeStatus(ExchangeStatus.DELIVERED.getCode());
        exchange.setExchangeDeliveryCorp(exchangeDeliveryCorp);
        exchange.setExchangeDeliverySn(exchangeDeliverySn);
        int result = exchangeMapper.doCustomerDeliver(exchange);
        if (result > 0) {
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(),
                    "客服已发货，物流公司：" + exchangeDeliveryCorp + "，物流单号：" + exchangeDeliverySn, ExchangeLogType.sended,
                    modifyMan);
            // 发货后 就将物流号推送到快递100
            logisticsService.createLogistics(exchangeDeliveryCorp, exchangeDeliverySn,
                    Logistics.BusinessType.DELIVERY.name(), modifyMan);
        }
        return result;
    }

    private void doSendExchangeMsg(Long memberInfoId, String loginCode, Long buzId, Map<String, String> emailC,
                                   String subject, String msg, String ip, String contact, String productPic) {
        String content = msg;
        PushBean pushBean = new PushBean(memberInfoId, content, 24);
        pushBean.setAppUrl("/member/exchange/" + buzId);
        MsgBean msgBean = new MsgBean(memberInfoId, 24, subject, content);
        msgBean.setAppUrl("/member/exchange/" + buzId);
        msgBean.setPic(productPic);
        SmsBean smsBean = new SmsBean(null, contact, SmsLogType.REMIND, content);
        msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doStoreAgree(Long exchangeId, String modifyMan, String info) {
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        if (exchange == null) {
            throw new BusinessException("换货申请单不存在，提交不成功！");
        }
        int result = 0;
        if (exchange.getExchangeStatus().equals(ExchangeStatus.WAITFORRECEIVE.getCode())) {
            exchange.setExchangeStatus(ExchangeStatus.WAITDELIVERED.getCode());
            exchange.setReceiver(modifyMan);
            exchange.setReceiveDate(new Date());
            result = exchangeMapper.doStoreAgree(exchange);
            if (result > 0) {
                this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(),
                        "仓库确认收货" + info == null ? "" : "（备注：" + info + "）", ExchangeLogType.receive, modifyMan);
                // 调拨单
                OrderItem orderItem = orderItemService.findById(exchange.getOrderItemId());
                if (orderItem.getDplusId() != null && BusType.DPLUS.name().equals(orderItem.getBusType())) {
                    requisitionItemService.doBurgeonForOrderItem(orderItem.getId(), DocType.Retretail);
                } else {
                    this.doRequisition(exchange, modifyMan);
                }
            }
        } else {
            throw new BusinessException("状态不匹配，目前状态为：" + exchange.getExchangeStatusName());
        }
        return result;
    }

    private void doRequisition(Exchange exchange, String modifyMan) {
        OrderItem orderItem = orderItemService.findById(exchange.getOrderItemId());
        if (!ProductSource.D2CMALL.name().equals(orderItem.getProductSource())) {
            return;
        }
        Product product = productService.findById(exchange.getOldProductId());
        Brand brand = brandService.findById(product.getDesignerId());
        Order order = orderService.findById(exchange.getOrderId());
        ProductSku sku = productSkuService.findById(exchange.getOldSkuId());
        Store lbp = storeService.findByCode("001");
        RequisitionItem requisitionItem = null;
        if (SaleCategory.POPPRODUCT.name().equals(exchange.getSaleCategory())) {
            requisitionItem = new RequisitionItem(lbp, brand, product, sku, ItemType.RESHIPRTN, ItemStaus.WAITDELIVERED,
                    exchange.getExchangeSn(), exchange.getQuantity());
            requisitionItem.setRelationSn(order.getOrderSn());
        } else if (SaleCategory.NORMALPRODUCT.name().equals(exchange.getSaleCategory())) {
            Store jgcbt = storeService.findByCode("D2C101");
            requisitionItem = new RequisitionItem(jgcbt, brand, product, sku, ItemType.DEPOTALLC,
                    ItemStaus.WAITDELIVERED, exchange.getExchangeSn(), exchange.getQuantity());
            requisitionItem.setRelationSn(order.getOrderSn());
            requisitionItem.setOrderItemId(exchange.getOrderItemId());
        }
        requisitionItemService.doOtherInsert(requisitionItem, modifyMan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doStoreRefuse(Long exchangeId, String modifyMan, String info) {
        Exchange exchange = this.exchangeMapper.selectByPrimaryKey(exchangeId);
        if (exchange == null) {
            throw new BusinessException("换货申请单不存在，提交不成功！");
        }
        int result = 0;
        if (exchange.getExchangeStatus().equals(ExchangeStatus.WAITFORRECEIVE.getCode())) {
            exchange.setExchangeStatus(ExchangeStatus.REFUSE.getCode());
            exchange.setRefuseReceiver(modifyMan);
            exchange.setRefuseReceiveDate(new Date());
            result = exchangeMapper.doStoreRefuse(exchange);
            if (result > 0) {
                this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(),
                        "仓库拒绝说明：" + info, ExchangeLogType.refuse, modifyMan);
            }
        } else {
            throw new BusinessException("状态不匹配，目前状态为：" + exchange.getExchangeStatusName());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doRefundClose(Long exchangeId, String modifyMan, String info, Refund refund) {
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        exchange.setExchangeStatus(ExchangeStatus.CLOSE.getCode());
        exchange.setCloser(modifyMan);
        exchange.setCloseDate(new Date());
        exchange.setAdminMemo(info);
        int result = exchangeMapper.doCustomClose(exchange);
        if (result > 0) {
            refund.setAllRefund(1);
            refund.setExchangeId(exchangeId);
            refund = refundService.insert(refund);
            if (refund.getId() > 0) {
                exchangeMapper.updateRefundId(exchangeId, refund.getId());
            } else {
                throw new BusinessException("换货单退款不成功！");
            }
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(),
                    "客服代用户关闭申请：" + info, ExchangeLogType.close, modifyMan);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateRefundId(Long exchangeId, Long refundId) {
        return exchangeMapper.updateRefundId(exchangeId, refundId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateToChangeSku(Long skuId, Long exchangeId, String modifyMan) {
        ProductSku sku = productSkuService.findById(skuId);
        int result = exchangeMapper.updateToChangeSku(sku, exchangeId);
        if (result > 0) {
            Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
            this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(),
                    "客服选择换货的商品货号为：" + sku.getInernalSn() + "，颜色：" + sku.getColorValue() + "，尺码：" + sku.getSizeValue(),
                    ExchangeLogType.modify, modifyMan);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateBackAddress(Long id, String backAddress, String backMobile, String backConsignee) {
        return exchangeMapper.updateBackAddress(id, backAddress, backMobile, backConsignee);
    }

}
