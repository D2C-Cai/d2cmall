package com.d2c.member.dto;

import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Consult;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ConsultDto extends Consult {

    private static final long serialVersionUID = 1L;
    private BigDecimal price;
    private BigDecimal salePrice;
    private BigDecimal originalPrice;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDeviceName() {
        if (StringUtils.isNotBlank(this.getDevice())) {
            return DeviceTypeEnum.valueOf(this.getDevice()).getDisplay();
        }
        return "电脑PC";
    }

}
