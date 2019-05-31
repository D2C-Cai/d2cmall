package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 系统参数设置
 */
@Table(name = "sys_setting")
public class Setting extends PreUserDO {

    // 版本升级安卓
    public final static String ANDROID = "ANDROID";
    // 版本升级苹果免费
    public final static String IOSFREE = "IOSFREE";
    // 版本升级苹果付费
    public final static String IOSPAY = "IOSPAY";
    // 版本升级苹果商家
    public final static String IOSBOSS = "IOSBOSS";
    // 版本升级苹果个人
    public final static String IOSPERSONAL = "IOSPERSONAL";
    // 订单普通未支付关闭
    public final static String ORDERCLOSECODE = "ORDERCLOSECODE";
    // 订单跨境未支付关闭
    public final static String CROSSCLOSECODE = "CROSSCLOSECODE";
    // 订单拼团未支付关闭
    public final static String COLLAGECLOSECODE = "COLLAGECLOSECODE";
    // 订单秒杀未支付关闭
    public final static String SECKILLCLOSECODE = "SECKILLCLOSECODE";
    // 订单普通售后关闭时间
    public final static String ORDERAFTERCLOSE = "ORDERAFTERCLOSE";
    // 退货退回时间
    public final static String RESHIPCLOSEDATE = "RESHIPCLOSEDATE";
    // 退货信息仓库
    public final static String BACKINFOCODE = "BACKINFOCODE";
    // 退货信息考拉
    public final static String KAOLABACKINFOCODE = "KAOLABACKINFOCODE";
    // JS更新时间戳
    public final static String TIMESTAMP = "TIMESTAMP";
    // 用户赔偿开关
    public final static String COMPENSATION = "COMPENSATION";
    // 分销全站系数
    public final static String REBATERATIO = "REBATERATIO";
    // 库存同步开关
    public final static String STOCKSYNC = "STOCKSYNC";
    // 无密退款开关
    public final static String REFUNDCODE = "REFUNDCODE";
    // 短信通道选择
    public final static String SMSCHOICE = "SMSCHOICE";
    // 施力说微信号
    public final static String BOSSWEIXIN = "BOSSWEIXIN";
    // 品牌入驻微信号
    public final static String BRADAPPLYWEIXIN = "BRADAPPLYWEIXIN";
    // 商品列表推荐活动搜索
    public final static String PRODUCTRECSEARCH = "PRODUCTRECSEARCH";
    // 港元汇率
    public final static String HKDEXRATE = "HKDEXRATE";
    private static final long serialVersionUID = 1L;
    /**
     * 模板 ID
     */
    private Long templateId;
    /**
     * 分类 ： EMAIL_DELIVERY 邮件订阅
     */
    private String type;
    /**
     * 会员类型
     */
    private String memberType;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员名称
     */
    private String loginCode;
    /**
     * 参数编号
     */
    @AssertColumn("参数编码不能为空")
    private String code;
    /**
     * 参数名称
     */
    @AssertColumn("参数名称不能为空")
    private String name;
    /**
     * 参数值
     */
    private String value;
    /**
     * 说明
     */
    private String info;
    /**
     * 0 未启用，1启用
     */
    private Integer status = 0;
    /**
     * 参数服务对象
     */
    private boolean system;
    /**
     * 参数服务对象
     */
    private boolean user;
    /**
     * 参数服务对象
     */
    private boolean designer;
    /**
     * 依赖ID
     */
    private Long belongTo;

    public static Object defaultValue(Setting entity, Object defaultValue) {
        if (entity != null && entity.getStatus() == 1) {
            return entity.getValue();
        } else {
            return defaultValue;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean isDesigner() {
        return designer;
    }

    public void setDesigner(boolean designer) {
        this.designer = designer;
    }

    public Long getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(Long belongTo) {
        this.belongTo = belongTo;
    }

    public enum SettingType {
        EMAIL_DELIVERY, STOCK_SYNC, SYS_PARAM;
    }

    /**
     * 平台，买家，卖家
     */
    public enum MemberType {
        MALL, BUYER, SELLER;
    }

}
