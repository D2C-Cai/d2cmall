package com.d2c.report.mongo.model;

import com.d2c.member.model.Partner;
import com.d2c.report.mongo.dto.SaleStatDTO;
import com.d2c.report.mongo.model.base.PartnerSaleBaseDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 经销商销售数据
 *
 * @author wull
 */
@Document
public class PartnerSaleDO extends PartnerSaleBaseDO {

    private static final long serialVersionUID = -7755422262982295261L;
    /**
     * ID 主键  partnerId
     */
    @Id
    private Long id;
    /**
     * 7日内实付金额
     * <p> 7日内 直接销售 + 团队销售金额
     */
    private Double weekPayAmount = 0.0;
    /**
     * 7日内销售单数
     * <p> 7日内 直接销售 + 团队销售单数
     */
    private Long weekPayCount = 0L;
    /**
     * 7天内直接数据
     */
    private SaleStatDTO weekSaleStat;
    /**
     * 7天内团队销售
     */
    private SaleStatDTO weekSaleStatDM;

    public PartnerSaleDO() {
    }

    public PartnerSaleDO(Partner partner) {
        super(partner);
        this.id = partner.getId();
    }

    /**
     * 统计数据后，初始数据
     */
    public void initSale() {
        if (weekSaleStat != null && weekSaleStatDM != null) {
            //7日实付金额
            weekPayAmount = weekSaleStat.getPayAmount() + weekSaleStatDM.getPayAmount();
            //7日销售单数
            weekPayCount = weekSaleStat.getCount() + weekSaleStatDM.getCount();
        }
    }
    //************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeekPayAmount() {
        return weekPayAmount;
    }

    public void setWeekPayAmount(Double weekPayAmount) {
        this.weekPayAmount = weekPayAmount;
    }

    public Long getWeekPayCount() {
        return weekPayCount;
    }

    public void setWeekPayCount(Long weekPayCount) {
        this.weekPayCount = weekPayCount;
    }

    public SaleStatDTO getWeekSaleStat() {
        return weekSaleStat;
    }

    public void setWeekSaleStat(SaleStatDTO weekSaleStat) {
        this.weekSaleStat = weekSaleStat;
    }

    public SaleStatDTO getWeekSaleStatDM() {
        return weekSaleStatDM;
    }

    public void setWeekSaleStatDM(SaleStatDTO weekSaleStatDM) {
        this.weekSaleStatDM = weekSaleStatDM;
    }

}
