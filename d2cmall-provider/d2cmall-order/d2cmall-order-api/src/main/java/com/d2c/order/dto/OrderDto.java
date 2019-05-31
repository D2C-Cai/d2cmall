package com.d2c.order.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.model.CrmGroup;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.handle.PromotionCalculateResult;
import com.d2c.order.model.Order;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.Payment;
import com.d2c.order.model.base.IPaymentInterface;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.*;

public class OrderDto extends Order implements IPaymentInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 明细数量小计
     */
    private Integer itemTotal;
    /**
     * 购物车ID
     */
    private List<Long> cartItemIds;
    /**
     * 订单明细项
     */
    private List<OrderItemDto> orderItems;
    /**
     * 支付单
     */
    private Payment payment;
    /**
     * 下单用户
     */
    private MemberInfo memberInfo;
    /**
     * 是否支持货到付款
     */
    private Integer cod;
    /**
     * CRM客户经理
     */
    private CrmGroup crmGroup;
    /**
     * 默认3个小时
     */
    private int closeTime = 2 * 60 * 60;

    public OrderDto() {
    }

    public OrderDto(MemberInfo member) {
        this.setCreateDate(new Date());
        this.setCreator(member.getLoginCode());
        this.setLastModifyMan(member.getId() + ":" + member.getLoginCode());
        this.setOrderSn(SerialNumUtil.buildOrderSn(String.valueOf(member.getId())));
        this.setOrderStatus(OrderStatus.Initial.getCode());
        this.setProductTotalPrice(new BigDecimal(0));
        this.setTotalAmount(new BigDecimal(0));
        this.setPaidAmount(new BigDecimal(0));
        this.setMemberId(member.getId());
        this.setLoginCode(member.getLoginCode());
        this.setRecId(member.getRecId());
        this.setMemberEmail(member.getEmail());
        this.setMemberMobile(member.getMobile());
        this.setCrmGroupId(member.getCrmGroupId());
    }

    /**
     * 是否包含改sku
     *
     * @param skuSN
     * @return
     */
    public boolean containsSKU(String skuSN) {
        if (orderItems == null || skuSN == null)
            return false;
        for (OrderItem item : orderItems) {
            if (skuSN.equals(item.getProductSkuSn())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取一个订单下的item数量小计
     */
    public Integer getItemTotal() {
        List<OrderItemDto> orderItems = this.getOrderItems();
        Integer i = new Integer(0);
        if (orderItems != null) {
            for (OrderItem oi : orderItems) {
                i += oi.getProductQuantity();
            }
            this.itemTotal = i;
        }
        return this.itemTotal;
    }

    @JsonIgnore
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderItem item : orderItems) {
            totalPrice = totalPrice.add(item.getTotalPrice());
        }
        return totalPrice;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public Date getEndTime() {
        if (this.getOrderStatus().equals(OrderStatus.WaitingForPay.getCode())) {
            return DateUtil.add(this.getCreateDate(), Calendar.SECOND, this.getCloseTime());
        }
        return null;
    }

    private JSONArray cartItemIdsJson() {
        JSONArray array = new JSONArray();
        if (this.getCartItemIds() != null) {
            for (Long cartItemId : this.getCartItemIds()) {
                array.add(cartItemId);
            }
        }
        return array;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public CrmGroup getCrmGroup() {
        return crmGroup;
    }

    public void setCrmGroup(CrmGroup crmGroup) {
        this.crmGroup = crmGroup;
    }

    @Override
    public Long getBillSourceId() {
        return this.getId();
    }

    @Override
    public String getBillSourceSn() {
        return this.getOrderSn();
    }

    @Override
    public Date getBillSourceTime() {
        return this.getCreateDate();
    }

    @Override
    public String getBillSourceType() {
        return OrderTypeEnum.ORDER.name();
    }

    @JsonIgnore
    @Override
    public String getBillSubject() {
        String productName = this.getOrderItems().get(0).getProductName();
        if (productName.length() > 20) {
            productName = productName.substring(0, 20);
        }
        String productSn = this.getOrderItems().get(0).getProductSn();
        String subject = "D2C-" + productSn + "-" + productName;
        return subject;
    }

    @JsonIgnore
    @Override
    public String getBillBody() {
        String productName = this.getOrderItems().get(0).getProductName();
        String productSn = this.getOrderItems().get(0).getProductSn();
        String subject = "D2C-" + productSn + "-" + productName;
        return subject;
    }

    @Override
    public BigDecimal getBillTotalFee() {
        return this.getTotalPay();
    }

    @Override
    public BigDecimal getBillShipFee() {
        return this.getShippingRates();
    }

    @Override
    public Long getPaymentId() {
        return super.getPaymentId();
    }

    @Override
    public Long getMemberId() {
        return super.getMemberId();
    }

    @Override
    public Long getToMemberId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWaitPay() {
        return this.getOrderStatus() != null && this.getOrderStatus() == 1;
    }

    @Override
    public boolean getCross() {
        return this.getCrossBorder() != null && this.getCrossBorder() == 1;
    }

    @Override
    @JsonIgnore
    public Map<String, String> getPayParams() {
        Map<String, String> map = new HashMap<>();
        map.put("billId_", String.valueOf(this.getBillSourceId()));
        map.put("billSn_", String.valueOf(this.getBillSourceSn()));
        map.put("billType_", String.valueOf(this.getBillSourceType()).toLowerCase());
        map.put("billSubject_", String.valueOf(this.getBillSubject()));
        map.put("billTotalFee_", String.valueOf(this.getBillTotalFee()));
        map.put("backUrl_", "/member/order");
        map.put("goodsType_", "1");
        String payType = PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY + "," + PaymentTypeEnum.WALLET;
        map.put("billPayTypes_", payType);
        return map;
    }

    /**
     * 初始化活动
     *
     * @param calitem
     */
    public OrderDto initPromotion(PromotionCalculateResult calResult, List<OrderItemDto> orderItems) {
        if (calResult == null) {
            return this;
        }
        if (calResult.getLowStock() != null && calResult.getLowStock()) {
            throw new BusinessException("商品库存不足，购买不成功！");
        }
        this.setProductTotalPrice(calResult.getProductTotalAmount());
        this.setProductTotalQuantity(calResult.getProductTotalQuantity());
        this.setProductPromotionAmount(calResult.getProductPromotionAmount());
        this.setPromotionName(calResult.getOrderPromotionNames());
        this.setPromotionAmount(calResult.getOrderPromotionAmount());
        this.setTotalAmount(calResult.getTotalAmount());
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        obj.put("cartItemIds", this.cartItemIdsJson());
        obj.put("itemTotal", this.getItemTotal());
        obj.put("endTime", this.getEndTime() != null ? DateUtil.second2str(this.getEndTime()) : null);
        return obj;
    }

    public JSONObject toDtoJson() {
        JSONObject obj = this.toJson();
        JSONArray items = new JSONArray();
        for (OrderItemDto orderItem : this.getOrderItems()) {
            items.add(orderItem.toJson());
        }
        obj.put("items", items);
        return obj;
    }

}
