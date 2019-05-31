package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.BurgeonErrorLog.DocType;
import com.d2c.logger.model.ReshipLog;
import com.d2c.logger.model.ReshipLog.ReshipLogType;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.ReshipLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.ReshipMapper;
import com.d2c.order.dto.ReshipDto;
import com.d2c.order.model.*;
import com.d2c.order.model.OrderItem.BusType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.RequisitionItem.ItemStaus;
import com.d2c.order.model.RequisitionItem.ItemType;
import com.d2c.order.model.Reship.ReshipStatus;
import com.d2c.order.query.ReshipSearcher;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("reshipService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ReshipServiceImpl extends ListServiceImpl<Reship> implements ReshipService {

    private static String[] ALL_STATUS = {"applyCount", "waitforreceiveCount"};
    @Autowired
    private RefundService refundService;
    @Autowired
    private ReshipMapper reshipMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private ReshipLogService reshipLogService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private StoreService storeService;

    /**
     * 退货日志
     */
    private void insertReshipLog(Reship reship, String operator, String info, ReshipLogType type) {
        ReshipLog reshipLog = new ReshipLog();
        reshipLog.setCreateDate(new Date());
        reshipLog.setCreator(operator);
        reshipLog.setInfo(info);
        reshipLog.setReshipLogType(type);
        reshipLog.setReshipId(reship.getId());
        reshipLog.setOrderItemId(reship.getOrderItemId());
        reshipLog.setOrderId(reship.getOrderId());
        reshipLog.setRefundId(reship.getRefundId());
        reshipLogService.insert(reshipLog);
    }

    @Override
    public Reship findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public Reship findBySn(String reshipSn) {
        return reshipMapper.findBySn(reshipSn);
    }

    @Override
    public Reship findByIdAndMemberInfoId(Long reshipId, Long memberInfoId) {
        Reship reship = reshipMapper.findByIdAndMemberInfoId(reshipId, memberInfoId);
        ReshipDto dto = new ReshipDto();
        BeanUtils.copyProperties(reship, dto);
        if (reship.getRefundId() != null && reship.getRefundId() > 0) {
            dto.setRefund(refundService.findById(reship.getRefundId()));
        }
        return dto;
    }

    @Override
    public List<Reship> findByDeliverySn(String nu) {
        return reshipMapper.findByDeliverySn(nu);
    }

    @Override
    public PageResult<ReshipDto> findMine(ReshipSearcher searcher, PageModel page) {
        PageResult<ReshipDto> pager = new PageResult<>(page);
        int totalCount = reshipMapper.countBySearch(searcher);
        List<Reship> list = new ArrayList<>();
        List<ReshipDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = reshipMapper.findBySearch(searcher, page);
            for (Reship reship : list) {
                ReshipDto dto = new ReshipDto();
                BeanUtils.copyProperties(reship, dto);
                if (reship.getRefundId() != null && reship.getRefundId() > 0) {
                    dto.setRefund(refundService.findById(reship.getRefundId()));
                }
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public PageResult<ReshipDto> findBySearch(ReshipSearcher searcher, PageModel page) {
        PageResult<ReshipDto> pager = new PageResult<>(page);
        int totalCount = reshipMapper.countBySearch(searcher);
        List<Reship> list = new ArrayList<>();
        List<ReshipDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = reshipMapper.findBySearch(searcher, page);
            for (Reship reship : list) {
                ReshipDto dto = new ReshipDto();
                BeanUtils.copyProperties(reship, dto);
                if (reship.getRefundId() != null && reship.getRefundId() > 0) {
                    dto.setRefund(refundService.findById(reship.getRefundId()));
                }
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public int countBySearcher(ReshipSearcher searcher) {
        return reshipMapper.countBySearch(searcher);
    }

    @Override
    public Map<String, Object> findAmountByDesigner(Long designerId) {
        return reshipMapper.findAmountByDesigner(designerId);
    }

    @Override
    public PageResult<Reship> findReshipForStatement(Date beginDate, PageModel page) {
        PageResult<Reship> pager = new PageResult<>(page);
        int totalCount = reshipMapper.countReshipForStatement(beginDate);
        if (totalCount > 0) {
            List<Reship> reships = reshipMapper.findReshipForStatement(beginDate, page);
            pager.setList(reships);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public List<Reship> findNotDeliveryClose() {
        return reshipMapper.findNotDeliveryClose();
    }

    @Override
    public List<Reship> findNotDeliveryRemind(Integer day) {
        return reshipMapper.findNotDeliveryRemind(day);
    }

    @Override
    public Map<String, Object> countByStatusAndMemberId(Long memberInfoId) {
        List<Map<String, Object>> counts = reshipMapper.countByStatusAndMemberId(memberInfoId);
        Map<String, Object> map = new HashMap<>();
        int processCount = 0;
        int totalCount = 0;
        for (Map<String, Object> count : counts) {
            Integer status = Integer.parseInt(count.get("reship_status").toString());
            if (status > -1 && status < 8) {
                processCount = processCount + Integer.parseInt(count.get("counts").toString());
            }
            totalCount = totalCount + Integer.parseInt(count.get("counts").toString());
        }
        map.put("processCount", processCount);
        map.put("totalCount", totalCount);
        return map;
    }

    @Override
    public Map<String, Object> countReshipsMaps() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> counts = reshipMapper.countGroupByStatus();
        for (Map<String, Object> count : counts) {
            String status = count.get("reship_status").toString();
            switch (status) {
                case "0":
                    map.put("applyCount", count.get("counts"));
                    break;
                case "4":
                    map.put("waitforreceiveCount", count.get("counts"));
                    break;
            }
        }
        // 将没有状态的数量，默认设置成0
        for (String status : ALL_STATUS) {
            if (map.get(status) == null) {
                map.put(status, 0);
            }
        }
        return map;
    }

    /**
     * 创建退货单
     *
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Reship insert(Reship reship) {
        OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
        if (orderItem.getStatus().equals(ItemStatus.SUCCESS.toString())) {
            throw new BusinessException("订单交易已经完成，申请退款退货不成功！");
        }
        reship = this.save(reship);
        if (reship.getId() > 0) {
            // 数据库行锁订单明细，防止重复申请
            int createSuccess = orderItemService.doCreateReship(reship.getId(), reship.getOrderItemId());
            if (createSuccess <= 0) {
                orderItemService.updateReship(reship.getId(), reship.getOrderItemId());
                orderItemService.updateRefund(null, reship.getOrderItemId());
                Reship old = reshipMapper.selectByPrimaryKey(orderItem.getReshipId());
                if (old.getReshipStatus() >= 0) {
                    throw new BusinessException("退货或退款单，不能重复申请！");
                }
            }
            // 用户未收货退货，模拟用户发货
            if (reship.getReshipStatus().equals(ReshipStatus.APPROVE.getCode()) && !reship.isReceived()
                    && reship.getProxy() == 1) {
                reship.setDeliveryCorpName(orderItem.getDeliveryCorpName());
                reship.setDeliverySn(orderItem.getDeliverySn());
                if (reship.getOrderPayType() != null) {
                    reship.setMemo(reship.getOrderPayTypeName() + "用户未收货退货");
                }
                int success = this.doLogistic(reship.getId(), reship.getDeliverySn(), reship.getDeliveryCorpName(),
                        reship.getMemo(), reship.getCreator());
                if (success > 0) {
                    this.insertReshipLog(reship, "sys", "用户未签收，申请退货，客服同意，默认用户拒签", ReshipLogType.sended);
                }
            }
        } else {
            throw new BusinessException("退货单新建不成功！");
        }
        String logInfo = "申请退货单";
        if (reship.getProxy() == 1) {
            logInfo = "客服代" + logInfo;
        }
        this.insertReshipLog(reship, reship.getCreator(), logInfo, ReshipLogType.create);
        return reship;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int doLogistic(Long reshipId, String deliverySn, String deliveryCorpName, String memo, String creator) {
        Reship reship = reshipMapper.selectByPrimaryKey(reshipId);
        if (!reship.getReshipStatus().equals(ReshipStatus.APPROVE.getCode())) {
            throw new BusinessException("客服没有同意，用户退货发货指令不成功！");
        }
        if (reship.getReshipStatus().equals(ReshipStatus.SUCCESS.getCode())) {
            throw new BusinessException("商户已收到货，用户退货发货指令不成功！");
        }
        int result = reshipMapper.doLogistic(reshipId, deliverySn, deliveryCorpName, memo);
        if (result > 0) {
            String logInfo = "用户已发货，物流公司：" + deliveryCorpName + "，物流编号：" + deliverySn;
            if (reship.getProxy() == 1) {
                logInfo = "代用户发货，物流公司：" + deliveryCorpName + "，物流编号：" + deliverySn;
            }
            this.insertReshipLog(reship, creator, logInfo, ReshipLogType.sended);
            // 发货后 就将物流号推送到快递100
            logisticsService.createLogistics(deliveryCorpName, deliverySn, Logistics.BusinessType.RESHIP.name(),
                    creator);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancel(Long reshipId, String modifyMan) {
        String info = "用户取消退货申请";
        Reship reship = reshipMapper.selectByPrimaryKey(reshipId);
        int success = 0;
        if (reship != null && !reship.getReshipStatus().equals(ReshipStatus.SUCCESS.getCode())) {
            reship.setReshipStatus(ReshipStatus.MEMBERCLOSE.getCode());
            reship.setCloser(modifyMan);
            reship.setCloseDate(new Date());
            success = reshipMapper.doCancel(reshipId, modifyMan, new Date());
            if (success > 0) {
                this.insertReshipLog(reship, modifyMan, info, ReshipLogType.close);
            }
            return success;
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerAgree(Long reshipId, BigDecimal totalAmount, String modifyMan, String info, String ip,
                               String backAddress, String backConsignee, String backMobile) {
        Reship reship = reshipMapper.selectByPrimaryKey(reshipId);
        if (reship == null || !reship.getReshipStatus().equals(ReshipStatus.APPLY.getCode())) {
            throw new BusinessException("退货申请审核不成功，状态不匹配！");
        }
        Setting setting = settingService.findByCode(Setting.RESHIPCLOSEDATE);
        Date deliveryExpiredDate = null;
        if (setting != null && setting.getStatus() == 1 && reship.isReceived()) {
            Integer limitDay = Integer.parseInt(Setting.defaultValue(setting, new Integer(7)).toString());
            deliveryExpiredDate = DateUtil.getIntervalDay(new Date(), limitDay);
        }
        int result = reshipMapper.doCustomAgree(reshipId, modifyMan, new Date(), info, deliveryExpiredDate);
        if (result > 0) {
            // 用户未收货退货，模拟用户发货
            OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
            if (!reship.isReceived()) {
                reship.setDeliverySn(orderItem.getDeliverySn());
                reship.setDeliveryCorpName(orderItem.getDeliveryCorpName());
                if (reship.getOrderPayType() != null) {
                    reship.setMemo(reship.getOrderPayTypeName() + "用户未收货退货");
                }
                int success = this.doLogistic(reship.getId(), reship.getDeliverySn(), reship.getDeliveryCorpName(),
                        reship.getMemo(), reship.getCreator());
                if (success > 0) {
                    this.insertReshipLog(reship, "sys", "用户未签收，申请退货，客服同意，默认用户拒签", ReshipLogType.sended);
                }
            } else {
                try {
                    Order order = orderService.findById(reship.getOrderId());
                    // 发送短信验证码 ，港澳台国外号码发送短信时加上区号，大陆地区正常发送，登录账号不计入区号
                    Map<String, String> map = new HashMap<>();
                    map.put("name", reship.getLoginCode());
                    map.put("refundSn", reship.getOrderSn());
                    doSendReshipMsg(reship.getMemberId(), reship.getLoginCode(), reship.getId(), map, "退款退货申请已经通过",
                            "尊敬的D2C会员，您的退货申请（***"
                                    + reship.getOrderSn().substring(reship.getOrderSn().length() - 6,
                                    reship.getOrderSn().length())
                                    + "）客服已同意，请尽快填写物流信息，退货至：" + backAddress + "，" + backConsignee + " 收  ，"
                                    + backMobile,
                            null, ip, order.getContact(), reship.getProductImg());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            this.insertReshipLog(reship, modifyMan, "同意退货", ReshipLogType.modify);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerRefuse(Long reshipId, String modifyMan, String info) {
        Reship reship = reshipMapper.selectByPrimaryKey(reshipId);
        if (reship == null) {
            throw new BusinessException("退货申请单不存在，关闭不成功！");
        }
        int result = 0;
        if (!reship.getReshipStatus().equals(ReshipStatus.SUCCESS.getCode())) {
            reship.setReshipStatus(ReshipStatus.REFUSE.getCode());
            result = reshipMapper.doCustomerRefuse(reshipId, modifyMan, new Date(), info);
            if (result > 0) {
                this.insertReshipLog(reship, modifyMan, "客服拒绝退货：" + info, ReshipLogType.close);
            }
        } else {
            throw new BusinessException("状态不匹配，目前状态为：" + reship.getReshipStatusName());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerClose(Long reshipId, String modifyMan, String info) {
        int result = 0;
        Reship reship = reshipMapper.selectByPrimaryKey(reshipId);
        if (reship == null) {
            throw new BusinessException("退货申请单不存在，关闭不成功！");
        }
        if (!reship.getReshipStatus().equals(ReshipStatus.SUCCESS.getCode())) {
            reship.setReshipStatus(ReshipStatus.CLOSE.getCode());
            result = reshipMapper.doCustomerClose(reshipId, modifyMan, new Date(), info);
            if (result > 0) {
                this.insertReshipLog(reship, modifyMan, "客服代用户关闭申请：" + info, ReshipLogType.close);
            }
        } else {
            throw new BusinessException("状态不匹配，目前状态为：" + reship.getReshipStatusName());
        }
        return result;
    }

    private void doSendReshipMsg(Long memberInfoId, String loginCode, Long buzId, Map<String, String> emailC,
                                 String subject, String msg, String terminal, String ip, String contact, String productPic) {
        String content = msg;
        PushBean pushBean = new PushBean(memberInfoId, content, 23);
        pushBean.setAppUrl("/member/reship/" + buzId);
        MsgBean msgBean = new MsgBean(memberInfoId, 23, subject, content);
        msgBean.setAppUrl("/member/reship/" + buzId);
        msgBean.setPic(productPic);
        SmsBean smsBean = new SmsBean(null, contact, SmsLogType.REMIND, content);
        msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doStoreAgree(Long reshipId, String modifyMan, String info, Refund refund) {
        Reship reship = reshipMapper.selectByPrimaryKey(reshipId);
        if (reship == null) {
            throw new BusinessException("退货申请单不存在，操作不成功！");
        }
        int result = 0;
        if (reship.getReshipStatus().equals(ReshipStatus.APPLY.getCode())
                || reship.getReshipStatus().equals(ReshipStatus.WAITFORRECEIVE.getCode())) {
            refund.setAllRefund(1);
            refund.setReshipId(reshipId);
            refund = refundService.insert(refund);
            if (refund.getId() > 0) {
                reship.setReshipStatus(ReshipStatus.SUCCESS.getCode());
                result = reshipMapper.doStoreAgree(reshipId, refund.getId(), modifyMan, new Date(), info);
                this.insertReshipLog(reship, modifyMan, "仓库确认收货，退货成功" + (info == null ? "" : "（备注：" + info + "）"),
                        ReshipLogType.completed);
                // 调拨单
                OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
                if (orderItem.getDplusId() != null && BusType.DPLUS.name().equals(orderItem.getBusType())) {
                    requisitionItemService.doBurgeonForOrderItem(orderItem.getId(), DocType.Retretail);
                } else {
                    this.doRequisition(reship, modifyMan);
                }
            }
        } else {
            throw new BusinessException("状态不匹配，目前状态为:" + reship.getReshipStatusName());
        }
        return result;
    }

    private void doRequisition(Reship reship, String modifyMan) {
        OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
        if (!ProductSource.D2CMALL.name().equals(orderItem.getProductSource())) {
            return;
        }
        Product product = productService.findById(reship.getProductId());
        Brand brand = brandService.findById(product.getDesignerId());
        Order order = orderService.findById(reship.getOrderId());
        ProductSku sku = productSkuService.findById(reship.getProductSkuId());
        Store lbp = storeService.findByCode("001");
        RequisitionItem requisitionItem = null;
        if (SaleCategory.POPPRODUCT.name().equals(reship.getSaleCategory())) {
            requisitionItem = new RequisitionItem(lbp, brand, product, sku, ItemType.RESHIPRTN, ItemStaus.WAITDELIVERED,
                    reship.getReshipSn(), reship.getActualQuantity());
            requisitionItem.setRelationSn(order.getOrderSn());
        } else if (SaleCategory.NORMALPRODUCT.name().equals(reship.getSaleCategory())) {
            Store jgcbt = storeService.findByCode("D2C101");
            requisitionItem = new RequisitionItem(jgcbt, brand, product, sku, ItemType.DEPOTALLC,
                    ItemStaus.WAITDELIVERED, reship.getReshipSn(), reship.getActualQuantity());
            requisitionItem.setRelationSn(order.getOrderSn());
            requisitionItem.setOrderItemId(reship.getOrderItemId());
        }
        requisitionItemService.doOtherInsert(requisitionItem, modifyMan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doStoreRefuse(Long reshipId, String modifyMan, String info) {
        Reship reship = reshipMapper.selectByPrimaryKey(reshipId);
        if (reship == null) {
            throw new BusinessException("退货申请单不存在，操作不成功！");
        }
        int result = 0;
        if (reship.getReshipStatus().equals(ReshipStatus.WAITFORRECEIVE.getCode())) {
            reship.setReshipStatus(ReshipStatus.REFUSE.getCode());
            reship.setRefuseReceiver(modifyMan);
            reship.setRefuseReceiveDate(new Date());
            result = reshipMapper.doStoreRefuse(reshipId, modifyMan, new Date(), info);
            if (result > 0) {
                // 退货日志
                this.insertReshipLog(reship, modifyMan, "仓库拒绝说明：" + info, ReshipLogType.refuse);
            }
        } else {
            throw new BusinessException("状态不匹配，目前状态为：" + reship.getReshipStatusName());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSysCloseReship(Long reshipId, String closer, String info) {
        int result = 0;
        Reship reship = this.findById(reshipId);
        if (!reship.getReshipStatus().equals(ReshipStatus.SUCCESS.getCode())) {
            reship.setReshipStatus(ReshipStatus.CLOSE.getCode());
            // 平台拒绝
            result = reshipMapper.doCustomerClose(reshipId, "sys", new Date(), info);
            if (result > 0) {
                this.insertReshipLog(reship, "sys", "系统拒绝退货", ReshipLogType.close);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateBackAddress(Long id, String backAddress, String backConsignee, String backMobile) {
        return reshipMapper.updateBackAddress(id, backAddress, backMobile, backConsignee);
    }

}
