package com.d2c.order.dto;

import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.AuctionMargin;
import com.d2c.order.model.base.IPaymentInterface;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.model.Product;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuctionMarginDto extends AuctionMargin implements IPaymentInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 商品
     */
    private Product product;
    /**
     * 拍卖商品
     */
    private AuctionProduct auctionProduct;
    /**
     * 地址
     */
    private AddressDto address;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public AuctionProduct getAuctionProduct() {
        return auctionProduct;
    }

    public void setAuctionProduct(AuctionProduct auctionProduct) {
        this.auctionProduct = auctionProduct;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    @Override
    public Long getBillSourceId() {
        return this.getId();
    }

    @Override
    public String getBillSourceSn() {
        return this.getMarginSn() + "-" + this.getStatus();
    }

    @Override
    public Date getBillSourceTime() {
        return this.getCreateDate();
    }

    @Override
    public String getBillSourceType() {
        return OrderTypeEnum.MARGIN.name();
    }

    @Override
    public String getBillSubject() {
        return this.getMarginSn() + "-" + this.getStatus();
    }

    @Override
    public String getBillBody() {
        return this.getMarginSn() + "-" + this.getStatus();
    }

    @Override
    public BigDecimal getBillTotalFee() {
        return this.getTotalAmount();
    }

    @Override
    public BigDecimal getBillShipFee() {
        return new BigDecimal("0");
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
        if (this.getStatus() == 0 || this.getStatus() == 2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean getCross() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @JsonIgnore
    public Map<String, String> getPayParams() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("billId_", String.valueOf(this.getBillSourceId()));
        map.put("billSn_", String.valueOf(this.getBillSourceSn()));
        map.put("billType_", String.valueOf(this.getBillSourceType()));
        map.put("billSubject_", String.valueOf(this.getBillSubject()));
        map.put("billTotalFee_", String.valueOf(this.getBillTotalFee()));
        map.put("backUrl_", "/auction/member/mymargin");
        map.put("goodsType_", "1");
        String payType = PaymentTypeEnum.ALIPAY + "," + PaymentTypeEnum.WXAPPPAY + "," + PaymentTypeEnum.WALLET;
        map.put("billPayTypes_", payType);
        return map;
    }

}
