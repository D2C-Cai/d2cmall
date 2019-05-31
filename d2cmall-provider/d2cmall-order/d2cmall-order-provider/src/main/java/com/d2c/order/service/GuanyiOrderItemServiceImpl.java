package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.GuanyiOrderItemMapper;
import com.d2c.order.model.GuanyiOrderItem;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.query.GuanyiOrderItemSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("guanyiOrderItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class GuanyiOrderItemServiceImpl extends ListServiceImpl<GuanyiOrderItem> implements GuanyiOrderItemService {

    @Autowired
    private GuanyiOrderItemMapper guanyiOrderItemMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private RequisitionItemService requisitionItemService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public GuanyiOrderItem insert(GuanyiOrderItem guanyiOrderItem) {
        return this.save(guanyiOrderItem);
    }

    @Override
    public List<GuanyiOrderItem> findByOrderId(Long orderId) {
        return guanyiOrderItemMapper.findByOrderId(orderId);
    }

    public void processExpress(final GuanyiOrderItem goi, boolean reProcess, String operator) throws Exception {
        // 处理官网物流
        if (goi.getExpress() == 1) {
            processExpressByItem(goi);
        }
        if (reProcess) {
            goi.setExpressHandle(1);
            goi.setExpressHandleMan(operator);
            goi.setExpressHandleDate(new Date());
        }
        goi.setExpress(0);
        this.update(goi);
    }

    private Integer processExpressByItem(GuanyiOrderItem goi) throws Exception {
        OrderItem orderItem = orderItemService.findByOrderSnAndSku(goi.getPlatformCode(), goi.getSkuCode());
        if (orderItem == null) {
            RequisitionItem requisitionItem = requisitionItemService.findByRequisitionSn(goi.getPlatformCode());// D+店业务下管易发货的可能是仓库调拨
            if (requisitionItem == null) {
                throw new Exception("订单不存在");
            } else {
                if (requisitionItem.getStatus() == 2) {
                    return requisitionItemService.doDelivery(requisitionItem.getId(), goi.getQty(), goi.getExpressNo(),
                            goi.getExpressName(), "sys");
                } else {
                    throw new Exception("调拨单状态不符");
                }
            }
        }
        if (!"NORMAL".equals(orderItem.getStatus())) {
            throw new Exception("订单明细状态不符");
        }
        if (orderItem.getReshipId() != null || orderItem.getRefundId() != null || orderItem.getExchangeId() != null) {
            throw new Exception("订单明细处于售后中");
        }
        if (orderItem.getProductQuantity() != goi.getQty()) {
            throw new Exception("商品数量不一致");
        }
        if (orderItem.getRequisition() != null && orderItem.getRequisition() < 8 && orderItem.getRequisition() > 0) {
            throw new Exception("订单处于调拨中，发货不成功");
        }
        return orderItemService.doDeliveryItem(orderItem.getId(), 0, goi.getSkuCode(), goi.getExpressName(),
                goi.getExpressNo(), "sys", true, goi.getQty());
    }

    @Override
    public PageResult<GuanyiOrderItem> findBySearcher(GuanyiOrderItemSearcher searcher, PageModel page) {
        PageResult<GuanyiOrderItem> pager = new PageResult<GuanyiOrderItem>();
        Integer totalCount = guanyiOrderItemMapper.countBySearcher(searcher);
        List<GuanyiOrderItem> list = new ArrayList<GuanyiOrderItem>();
        if (totalCount > 0) {
            list = guanyiOrderItemMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Integer countBySearcher(GuanyiOrderItemSearcher searcher) {
        return guanyiOrderItemMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(GuanyiOrderItem guanyiOrderItem) {
        return this.updateNotNull(guanyiOrderItem);
    }

    @Override
    public GuanyiOrderItem findById(Long id) {
        return guanyiOrderItemMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doHandle(Long id, String handleMan, String handleContent) {
        return guanyiOrderItemMapper.doHandle(id, handleMan, handleContent);
    }

}
