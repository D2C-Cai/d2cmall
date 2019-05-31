package com.d2c.order.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.GuanyiOrderMapper;
import com.d2c.order.dto.GuanyiOrderDto;
import com.d2c.order.dto.GuanyiOrderDto.ShopCodeEnum;
import com.d2c.order.model.GuanyiOrder;
import com.d2c.order.model.GuanyiOrderItem;
import com.d2c.order.query.GuanyiOrderSearcher;
import com.d2c.order.third.burgeon.BurgeonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("guanyiOrderService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class GuanyiOrderServiceImpl extends ListServiceImpl<GuanyiOrder> implements GuanyiOrderService {

    @Autowired
    private GuanyiOrderMapper guanyiOrderMapper;
    @Autowired
    private GuanyiOrderItemService guanyiOrderItemService;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public GuanyiOrder insert(GuanyiOrderDto guanyiOrderDto) {
        GuanyiOrder guanyiOrder = new GuanyiOrder();
        BeanUtils.copyProperties(guanyiOrderDto, guanyiOrder);
        if (this.findOneByFieldName("code", guanyiOrderDto.getCode()) == null) {
            guanyiOrder = this.save(guanyiOrder);
        }
        if (guanyiOrder.getId() != null) {
            for (GuanyiOrderItem guanyiOrderItem : guanyiOrderDto.getItems()) {
                guanyiOrderItem.setOrderId(guanyiOrder.getId());
                guanyiOrderItemService.insert(guanyiOrderItem);
            }
        }
        return guanyiOrder;
    }

    @Override
    public PageResult<GuanyiOrderDto> findBySearch(GuanyiOrderSearcher search, PageModel page) {
        PageResult<GuanyiOrderDto> pager = new PageResult<GuanyiOrderDto>(page);
        Integer totalCount = guanyiOrderMapper.countBySearcher(search);
        List<GuanyiOrderDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            List<GuanyiOrder> list = guanyiOrderMapper.findBySearcher(search, page);
            for (GuanyiOrder guanyiOrder : list) {
                GuanyiOrderDto dto = new GuanyiOrderDto();
                BeanUtils.copyProperties(guanyiOrder, dto);
                dto.setItems(guanyiOrderItemService.findByOrderId(guanyiOrder.getId()));
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doHandle(Long id, Integer type, String handleMan, String handleContent) {
        return guanyiOrderMapper.doHandle(id, type, handleMan, handleContent);
    }

    @Override
    public GuanyiOrder findById(Long id) {
        return guanyiOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public GuanyiOrder findLastDeliverOrder() {
        return guanyiOrderMapper.findLastDeliverOrder();
    }

    @Override
    public Integer countBySearch(GuanyiOrderSearcher search) {
        return guanyiOrderMapper.countBySearcher(search);
    }

    // 管易推送做单
    @Override
    public Integer processBurgeon(final GuanyiOrderDto guanyiOrderDto, boolean reProcess, String operator) {
        int success = 1;
        // 处理伯俊做单
        String errorType = "";
        try {
            if (guanyiOrderDto.getTransfer() == 1) {
                errorType = "调拨单失败:";
                sendBurgeonOnTransfer(guanyiOrderDto);
                guanyiOrderDto.setTransfer(0);
            }
            if (guanyiOrderDto.getRetail() == 1) {
                errorType = "零售单失败:";
                sendBurgeonOnRetail(guanyiOrderDto);
                guanyiOrderDto.setRetail(0);
            }
            if (guanyiOrderDto.getRetpur() == 1) {
                errorType = "采购退货单失败:";
                sendBurgeonOnRetpur(guanyiOrderDto);
                guanyiOrderDto.setRetpur(0);
            }
        } catch (Exception e) {
            guanyiOrderDto.setBurgeonFall(1);
            guanyiOrderDto.setBurgeonError(errorType + (e.getMessage() == null ? "空指针异常" : e.getMessage()));
            success = 0;
        }
        if (success == 1 && reProcess) {
            guanyiOrderDto.setBurgeonHandle(1);
            guanyiOrderDto.setBurgeonHandleMan(operator);
            guanyiOrderDto.setBurgeonHandleDate(new Date());
        }
        guanyiOrderDto.setItems(null);
        // 不管是否失败-避免定时器重复处理
        guanyiOrderDto.setSuccess(1);
        this.update(guanyiOrderDto);
        return success;
    }

    /**
     * 调拨单
     *
     * @throws Exception
     */
    private void sendBurgeonOnTransfer(final GuanyiOrderDto guanyiOrderDto) throws Exception {// TODO
        // 添加报文日志
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        JSONObject data = new JSONObject();
        data.put("refno", guanyiOrderDto.getCode().replace("SDO", "TF"));
        if (!"D2C001".equals(guanyiOrderDto.getShopCode())) {
            data.put("orig", "003");
            String StoreCode = null;
            if ("D2C002".equals(guanyiOrderDto.getShopCode()) || "D2C024".equals(guanyiOrderDto.getShopCode())) {// D2C002做特殊，取具体门店的编号(即vipCode),D2C024是D+店统一仓
                StoreCode = guanyiOrderDto.getVipCode();
            } else {
                StoreCode = ShopCodeEnum.valueOf(guanyiOrderDto.getShopCode()).getStoreCode();
            }
            data.put("dest", StoreCode);
            data.put("remark",
                    "管易订单编号:" + guanyiOrderDto.getCode() + "，" + guanyiOrderDto.getVipName() + "，收件人:"
                            + guanyiOrderDto.getReceiverName() + "，物流:" + guanyiOrderDto.getExpressName() + "，"
                            + guanyiOrderDto.getExpressNo() + "，系统自动做调拨单");
        } else {
            data.put("orig", "003");// 官网零售仓001 官网加工仓002 顺丰仓003 直发仓101
            data.put("dest", ShopCodeEnum.valueOf(guanyiOrderDto.getShopCode()).getStoreCode());
            data.put("remark",
                    "管易订单编号:" + guanyiOrderDto.getCode() + "，官网订单编号:" + guanyiOrderDto.getPlatformCode() + "发货给消费者，物流:"
                            + guanyiOrderDto.getExpressName() + "，" + guanyiOrderDto.getExpressNo()
                            + "，系统自动做调拨单至官网零售仓");
        }
        data.put("billdate", fmt.format(new Date()));
        data.put("dateout", fmt.format(new Date()));
        data.put("datein", fmt.format(new Date()));
        if ("D2C002".equals(guanyiOrderDto.getShopCode())) {
            data.put("datein", "19700101");// 不入库固定值
        }
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemqtyout = new JSONArray();
        JSONArray itemqtyin = new JSONArray();
        for (GuanyiOrderItem guanyiOrderItem : guanyiOrderDto.getItems()) {
            if ("FJH001".equals(guanyiOrderItem.getSkuCode()) || "HHZ001".equals(guanyiOrderItem.getSkuCode())
                    || "RSHK001".equals(guanyiOrderItem.getSkuCode())) {
                continue;
            }
            itemsku.add(guanyiOrderItem.getSkuCode());
            itemqty.add(guanyiOrderItem.getQty().toString());
            itemqtyout.add(guanyiOrderItem.getQty().toString());
            itemqtyin.add(guanyiOrderItem.getQty().toString());
        }
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemqtyout", itemqtyout);
        data.put("itemqtyin", itemqtyin);
        BurgeonClient.getInstance().sendBurgeon(data, "transfer");
    }

    /**
     * 零售单
     *
     * @throws Exception
     */
    private void sendBurgeonOnRetail(final GuanyiOrderDto guanyiOrderDto) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        JSONObject data = new JSONObject();
        data.put("refno", guanyiOrderDto.getCode().replace("SDO", "RE"));
        data.put("billdate", fmt.format(guanyiOrderDto.getDeliveryDate()));
        if ("D2C024".equals(guanyiOrderDto.getShopCode())) {
            data.put("store", guanyiOrderDto.getVipCode());
            data.put("remark", "管易订单编号:" + guanyiOrderDto.getCode() + "，" + guanyiOrderDto.getPlatformCode()
                    + "发给消费者，系统自动做调拨单到对应D+店后做零售单");
        } else {
            data.put("store", "001");
            data.put("remark", "管易订单编号:" + guanyiOrderDto.getCode() + "，" + guanyiOrderDto.getPlatformCode()
                    + "发给消费者，系统自动做调拨单到官网零售仓后做零售单");
        }
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemprice = new JSONArray();
        JSONArray payway = new JSONArray();
        JSONArray payamt = new JSONArray();
        for (GuanyiOrderItem guanyiOrderItem : guanyiOrderDto.getItems()) {
            if ("FJH001".equals(guanyiOrderItem.getSkuCode()) || "HHZ001".equals(guanyiOrderItem.getSkuCode())
                    || "RSHK001".equals(guanyiOrderItem.getSkuCode())) {
                continue;
            }
            itemsku.add(guanyiOrderItem.getSkuCode());
            itemqty.add(guanyiOrderItem.getQty().toString());
            BigDecimal price = guanyiOrderItem.getAmountAfter().divide(new BigDecimal(guanyiOrderItem.getQty()), 2,
                    BigDecimal.ROUND_HALF_UP);
            itemprice.add(price.toString());
            payway.add("官网支付");
            payamt.add(price.multiply(new BigDecimal(guanyiOrderItem.getQty())).toString());
        }
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemprice", itemprice);
        data.put("payway", payway);
        data.put("payamt", payamt);
        BurgeonClient.getInstance().sendBurgeon(data, "retail");
    }

    /**
     * 采购退货单
     *
     * @throws Exception
     */
    private void sendBurgeonOnRetpur(final GuanyiOrderDto guanyiOrderDto) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        JSONObject data = new JSONObject();
        data.put("refno", guanyiOrderDto.getCode().replace("SDO", "RP"));
        data.put("billdate", fmt.format(guanyiOrderDto.getDeliveryDate()));
        data.put("dateout", fmt.format(guanyiOrderDto.getDeliveryDate()));
        data.put("store", "003");
        data.put("supplier", "0");// 供应商
        data.put("remark", "管易订单编号:" + guanyiOrderDto.getCode() + "，退货给设计师，系统自动做采购退货单");
        JSONArray itemsku = new JSONArray();
        JSONArray itemqty = new JSONArray();
        JSONArray itemprice = new JSONArray();
        for (GuanyiOrderItem guanyiOrderItem : guanyiOrderDto.getItems()) {
            if ("FJH001".equals(guanyiOrderItem.getSkuCode()) || "HHZ001".equals(guanyiOrderItem.getSkuCode())
                    || "RSHK001".equals(guanyiOrderItem.getSkuCode())) {
                continue;
            }
            itemsku.add(guanyiOrderItem.getSkuCode());
            itemqty.add(guanyiOrderItem.getQty().toString());
            itemprice.add(guanyiOrderItem.getAmountAfter()
                    .divide(new BigDecimal(guanyiOrderItem.getQty()), 2, BigDecimal.ROUND_HALF_UP).toString());
        }
        data.put("itemsku", itemsku);
        data.put("itemqty", itemqty);
        data.put("itemprice", itemprice);
        BurgeonClient.getInstance().sendBurgeon(data, "retpur");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Integer update(GuanyiOrder guanyiOrder) {
        return this.updateNotNull(guanyiOrder);
    }

    @Override
    public GuanyiOrder findByCode(String code) {
        return this.findOneByFieldName("code", code);
    }

}
