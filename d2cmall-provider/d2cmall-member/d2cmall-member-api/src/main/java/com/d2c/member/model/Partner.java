package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分销商（返利）
 */
@Table(name = "m_partner")
public class Partner extends PreUserDO {

    /**
     * 礼包积分比 1：100
     */
    public static final int POINT_GIFT_RATIO = 100;
    private static final long serialVersionUID = 1L;
    /**
     * 状态 0.试用期 1.正常-1.暂时关店 -9.永久关闭
     */
    private Integer status;
    /**
     * 0.AM 1.DM 2.买手
     */
    private Integer level;
    /**
     * 是否电签 0.否 1.是
     */
    private Integer contract;
    /**
     * 登录账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    @AssertColumn("登录账号不能为空")
    private String loginCode;
    /**
     * 会员昵称
     */
    private String name;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 上级分销ID
     */
    private Long parentId;
    /**
     * 原上级分销ID
     */
    private Long oldParentId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 导师ID
     */
    private Long masterId;
    /**
     * 运营顾问ID
     */
    private Long counselorId;
    /**
     * 备注
     */
    private String memo;
    /**
     * 试用期结束时间
     */
    private Date expiredDate;
    /**
     * 首次开单时间
     */
    private Date firstDate;
    /**
     * 最后开单时间
     */
    private Date consumeDate;
    /**
     * 运营顾问时间
     */
    private Date counselorDate;
    /**
     * 最后登录时间
     */
    private Date lastLoginDate;
    /**
     * 总返利金额
     */
    private BigDecimal totalAmount = new BigDecimal(0);
    /**
     * 申请金额
     */
    private BigDecimal applyAmount = new BigDecimal(0);
    /**
     * 已提现金额
     */
    private BigDecimal cashAmount = new BigDecimal(0);
    /**
     * 总的返利订单实付金额
     */
    private BigDecimal totalOrderAmount = new BigDecimal(0);
    /**
     * 已结算金额
     */
    private BigDecimal balanceAmount = new BigDecimal(0);
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String identityCard;
    /**
     * 支付宝账号
     */
    private String alipay;
    /**
     * 微信账号
     */
    private String weixin;
    /**
     * openId
     */
    private String openId;
    /**
     * appId
     */
    private String appId;
    /**
     * 省市地区
     */
    private String region;
    /**
     * 开户银行
     */
    private String bank;
    /**
     * 银行类型
     */
    private String bankType;
    /**
     * 银行卡号
     */
    private String bankSn;
    /**
     * 现居住地
     */
    private String address;
    /**
     * 现职业
     */
    private String occupation;
    /**
     * 从业经历
     */
    private String experience;
    /**
     * 出生年月
     */
    private String birthday;
    /**
     * 微信好友数
     */
    private String weixinCount;
    /**
     * 微博粉丝数
     */
    private String weiboCount;
    /**
     * 月消费开支
     */
    private String consumption;
    /**
     * 婚姻状况
     */
    private String matrimony;
    /**
     * 营业执照
     */
    private String license;
    /**
     * 公司注册号
     */
    private String licenseNum;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 门店标识，是门店=1
     */
    private Integer storeMark;
    /**
     * 标签
     */
    private String tags;
    /**
     * 礼包数
     */
    private Integer giftCount;
    /**
     * 积分数
     */
    private Integer pointCount;
    /**
     * 预存礼包数
     */
    private Integer prestore;
    /**
     * 邀请人路径
     */
    private String path;
    /**
     * 升级 0.否 1.是
     */
    private Integer upgrade;
    /**
     * 提现 0.否 1.是
     */
    private Integer withdraw;

    public Partner() {
    }

    public Partner(MemberInfo memberInfo, Integer level) {
        super();
        this.level = level;
        this.status = 1;
        this.memberId = memberInfo.getId();
        this.loginCode = memberInfo.getLoginCode();
        this.name = memberInfo.getDisplayName();
        this.headPic = memberInfo.getHeadPic();
        this.storeMark = 0;
        this.contract = 0;
        this.giftCount = 0;
        this.pointCount = 0;
        this.prestore = 0;
        this.upgrade = 1;
        this.withdraw = 1;
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

    public Integer getContract() {
        return contract;
    }

    public void setContract(Integer contract) {
        this.contract = contract;
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

    public Long getOldParentId() {
        return oldParentId;
    }

    public void setOldParentId(Long oldParentId) {
        this.oldParentId = oldParentId;
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

    public Long getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Long counselorId) {
        this.counselorId = counselorId;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Date getCounselorDate() {
        return counselorDate;
    }

    public void setCounselorDate(Date counselorDate) {
        this.counselorDate = counselorDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankSn() {
        return bankSn;
    }

    public void setBankSn(String bankSn) {
        this.bankSn = bankSn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeixinCount() {
        return weixinCount;
    }

    public void setWeixinCount(String weixinCount) {
        this.weixinCount = weixinCount;
    }

    public String getWeiboCount() {
        return weiboCount;
    }

    public void setWeiboCount(String weiboCount) {
        this.weiboCount = weiboCount;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getMatrimony() {
        return matrimony;
    }

    public void setMatrimony(String matrimony) {
        this.matrimony = matrimony;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getStoreMark() {
        return storeMark;
    }

    public void setStoreMark(Integer storeMark) {
        this.storeMark = storeMark;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(Integer giftCount) {
        this.giftCount = giftCount;
    }

    public Integer getPointCount() {
        return pointCount;
    }

    public void setPointCount(Integer pointCount) {
        this.pointCount = pointCount;
    }

    public Integer getPrestore() {
        return prestore;
    }

    public void setPrestore(Integer prestore) {
        this.prestore = prestore;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Integer upgrade) {
        this.upgrade = upgrade;
    }

    public Integer getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Integer withdraw) {
        this.withdraw = withdraw;
    }

    public boolean checkAvailable() {
        if (this.status >= 0) {
            return true;
        }
        return false;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("expiredDate", DateUtil.second2str2(this.getExpiredDate()));
        obj.put("name", this.getName());
        obj.put("loginCode", StringUt.hideMobile(this.getLoginCode()));
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
        obj.put("memberId", this.getMemberId());
        obj.put("parentId", this.getParentId());
        obj.put("masterId", this.getMasterId());
        obj.put("storeId", this.getStoreId());
        obj.put("partnerOpenId", this.getOpenId());
        obj.put("counselorId", this.getCounselorId());
        obj.put("totalAmount", this.getTotalAmount());
        obj.put("applyAmount", this.getApplyAmount());
        obj.put("cashAmount", this.getCashAmount());
        obj.put("totalOrderAmount", this.getTotalOrderAmount());
        obj.put("status", this.getStatus());
        obj.put("level", this.getLevel());
        obj.put("contract", this.getContract());
        obj.put("alipay", this.getAlipay() == null ? "" : StringUt.hideMobile(this.getAlipay()));
        obj.put("weixin", this.getWeixin() == null ? "" : this.getWeixin());
        obj.put("region", this.getRegion() == null ? "" : this.getRegion());
        obj.put("bank", this.getBank() == null ? "" : this.getBank());
        obj.put("bankType", this.getBankType() == null ? "" : this.getBankType());
        obj.put("bankSn", this.getBankSn() == null ? "" : StringUt.hideBankSn(this.getBankSn()));
        obj.put("address", this.getAddress() == null ? "" : this.getAddress());
        obj.put("occupation", this.getOccupation() == null ? "" : this.getOccupation());
        obj.put("experience", this.getExperience() == null ? "" : this.getExperience());
        obj.put("birthday", this.getBirthday() == null ? "" : this.getBirthday());
        obj.put("weixinCount", this.getWeixinCount() == null ? "" : this.getWeixinCount());
        obj.put("weiboCount", this.getWeiboCount() == null ? "" : this.getWeiboCount());
        obj.put("consumption", this.getConsumption() == null ? "" : this.getConsumption());
        obj.put("matrimony", this.getMatrimony() == null ? "" : this.getMatrimony());
        obj.put("realName", this.getRealName() == null ? "" : StringUt.hideBankSn(this.getRealName()));
        obj.put("identityCard", this.getIdentityCard() == null ? "" : StringUt.hideIDCard(this.getIdentityCard()));
        obj.put("company", this.getCompany() == null ? "" : this.getCompany());
        obj.put("license", this.getLicense() == null ? "" : this.getLicense());
        obj.put("licenseNum", this.getLicenseNum() == null ? "" : StringUt.hideLicenseNum(this.getLicenseNum()));
        obj.put("giftCount", this.getGiftCount());
        obj.put("pointCount", this.getPointCount());
        return obj;
    }

    public JSONObject toSimpleJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("loginCode", StringUt.hideMobile(this.getLoginCode()));
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
        return obj;
    }

}