package com.d2c.report.mongo.model.base;

import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.mongodb.model.SuperMongoDO;
import com.d2c.member.enums.PartnerLevelEnum;
import com.d2c.member.model.Partner;
import com.d2c.report.mongo.dto.SaleStatDTO;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * 买手销售数据统计
 *
 * @author wull
 */
public class PartnerSaleBaseDO extends SuperMongoDO {

    private static final long serialVersionUID = -7755422262982295261L;
    /**
     * 买手ID
     */
    @Indexed
    protected Long partnerId;
    /**
     * 状态 0.试用期 1.正常-1.未通过 -9.永久关闭
     */
    protected Integer status;
    /**
     * 0.AM 1.DM 2.买手
     */
    protected Integer level;
    /**
     * 登录账号
     */
    protected String loginCode;
    /**
     * 会员昵称
     */
    protected String name;
    /**
     * 会员头像
     */
    protected String headPic;
    /**
     * 会员ID
     */
    @Indexed
    protected Long memberId;
    /**
     * 上级分销ID
     */
    @Indexed
    protected Long parentId;
    /**
     * 店铺ID
     */
    protected Long storeId;
    /**
     * 导师ID
     */
    @Indexed
    protected Long masterId;
    /**
     * 运营顾问ID
     */
    protected Long counselorId;
    /**
     * 运营顾问昵称
     */
    protected String counselorName;
    /**
     * 运营顾问时间
     */
    protected Date counselorDate;
    /**
     * 运营顾问微信
     */
    protected String counselorWeixin;
    /**
     * 微信账号
     */
    protected String weixin;
    /**
     * 标签
     */
    protected String tags;
    /**
     * 创建日期
     */
    protected Date createDate;
    /**
     * 试用期结束时间
     */
    protected Date expiredDate;
    /**
     * 最后开单时间
     */
    protected Date consumeDate;
    /**
     * 邀请买手人数
     */
    protected Integer inviteBuyer;
    /**
     * 邀请DM人数
     */
    protected Integer inviteDM;
    /**
     * 邀请总人数
     */
    protected Integer inviteNum;
    /**
     * 实付金额 = 零售 + 团队业绩
     */
    protected Double payAmount = 0.0;
    /**
     * 销售单数 = 零售 + 团队单数
     */
    protected Long payCount = 0L;
    /**
     * 给上级创收金额
     * <p> level = 2 买手，直接销售的团队收益
     * <p> level = 1 DM，直接销售的间接团队直接收益  + 团队销售的间接团队收益
     */
    protected Double upperRebates = 0.0;
    /**
     * 直接销售数据
     */
    protected SaleStatDTO saleStat;
    /**
     * 团队销售数据
     */
    protected SaleStatDTO saleStatDM;
    /**
     * 间接团队销售数据
     */
    protected SaleStatDTO saleStatSDM;
    /**
     * AM销售数据
     */
    protected SaleStatDTO saleStatAM;
    /**
     * 首次开单时间
     */
    private Date firstDate;
    /**
     * 收益金额 = 零售收益 + 团队的团队收益 + DM的间接团队收益 + AM收益金额
     */
    private Double payRebates = 0.0;

    public PartnerSaleBaseDO() {
    }

    public PartnerSaleBaseDO(Partner partner) {
        setPartner(partner);
    }

    public void setPartner(Partner partner) {
        if (partner != null) {
            BeanUt.copyProperties(this, partner);
            this.partnerId = partner.getId();
        }
    }

    /**
     * 统计数据后，初始数据
     */
    public void init() {
        //邀请总人数
        if (inviteBuyer == null) inviteBuyer = 0;
        if (inviteDM == null) inviteDM = 0;
        inviteNum = inviteBuyer + inviteDM;
        //给上级创收金额
        if (level != null) {
            if (level.equals(PartnerLevelEnum.DM.getCode())) {
                upperRebates = saleStat.getSuperRebates() + saleStatSDM.getSuperRebates();
            } else if (level.equals(PartnerLevelEnum.BUYER.getCode())) {
                upperRebates = saleStat.getParentRebates();
            }
        }
        if (saleStat == null) saleStat = new SaleStatDTO();
        if (saleStatDM == null) saleStatDM = new SaleStatDTO();
        if (saleStatSDM == null) saleStatSDM = new SaleStatDTO();
        //实付金额 = 零售 + 团队业绩
        payAmount = saleStat.getPayAmount() + saleStatDM.getPayAmount();
        //销售单数 = 零售 + 团队单数
        payCount = saleStat.getCount() + saleStatDM.getCount();
        //收益金额 = 零售收益 + 团队的团队收益 + DM的间接团队收益 + AM收益金额
        payRebates = saleStat.getPartnerRebates() + saleStatDM.getParentRebates() + saleStatSDM.getSuperRebates() + saleStatAM.getMasterRebates();
    }
    //*******************************

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    public Integer getInviteBuyer() {
        return inviteBuyer;
    }

    public void setInviteBuyer(Integer inviteBuyer) {
        this.inviteBuyer = inviteBuyer;
    }

    public Integer getInviteDM() {
        return inviteDM;
    }

    public void setInviteDM(Integer inviteDM) {
        this.inviteDM = inviteDM;
    }

    public Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(Integer inviteNum) {
        this.inviteNum = inviteNum;
    }

    public Double getUpperRebates() {
        return upperRebates;
    }

    public void setUpperRebates(Double upperRebates) {
        this.upperRebates = upperRebates;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Long getPayCount() {
        return payCount;
    }

    public void setPayCount(Long payCount) {
        this.payCount = payCount;
    }

    public SaleStatDTO getSaleStat() {
        return saleStat;
    }

    public void setSaleStat(SaleStatDTO saleStat) {
        this.saleStat = saleStat;
    }

    public SaleStatDTO getSaleStatDM() {
        return saleStatDM;
    }

    public void setSaleStatDM(SaleStatDTO saleStatDM) {
        this.saleStatDM = saleStatDM;
    }

    public SaleStatDTO getSaleStatSDM() {
        return saleStatSDM;
    }

    public void setSaleStatSDM(SaleStatDTO saleStatSDM) {
        this.saleStatSDM = saleStatSDM;
    }

    public SaleStatDTO getSaleStatAM() {
        return saleStatAM;
    }

    public void setSaleStatAM(SaleStatDTO saleStatAM) {
        this.saleStatAM = saleStatAM;
    }

    public Double getPayRebates() {
        return payRebates;
    }

    public void setPayRebates(Double payRebates) {
        this.payRebates = payRebates;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Long getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Long counselorId) {
        this.counselorId = counselorId;
    }

    public Date getCounselorDate() {
        return counselorDate;
    }

    public void setCounselorDate(Date counselorDate) {
        this.counselorDate = counselorDate;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public String getCounselorWeixin() {
        return counselorWeixin;
    }

    public void setCounselorWeixin(String counselorWeixin) {
        this.counselorWeixin = counselorWeixin;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
