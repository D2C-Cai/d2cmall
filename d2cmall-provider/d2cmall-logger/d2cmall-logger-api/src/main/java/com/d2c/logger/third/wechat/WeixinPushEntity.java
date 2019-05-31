package com.d2c.logger.third.wechat;

import com.alibaba.fastjson.JSONObject;
import com.d2c.util.date.DateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeixinPushEntity implements Serializable {

    public final static DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private static final long serialVersionUID = 1L;
    /**
     * openId
     */
    private String openId;
    /**
     * 模板ID
     */
    private String templateId;
    /**
     * 跳转URL
     */
    private String url;
    /**
     * 跳小程序所需数据
     */
    private JSONObject miniprogram;
    /**
     * 消息内容
     */
    private JSONObject obj = new JSONObject();

    public WeixinPushEntity() {
    }

    public WeixinPushEntity(String pagepath) {
        JSONObject obj = new JSONObject();
        obj.put("appid", "wx6807f3a22c99318f");
        obj.put("pagepath", pagepath);
        this.miniprogram = obj;
    }

    /**
     * 订单状态提醒（返利单状态）
     *
     * @param openId
     * @param title
     * @param orderSn
     * @param date
     * @param remark
     * @param pagepath
     */
    public WeixinPushEntity(String openId, String title, String orderSn, Date date, String remark, String pagepath) {
        this(pagepath);
        this.templateId = "Da8o43Y83cbJaZuIgSRzd2h-JKcE305WRElhKgn6MfY";
        this.openId = openId;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(orderSn));
        obj.put("keyword2", processField(dateFormat.format(date)));
        obj.put("remark", processField(remark));
    }

    /**
     * 邀请注册成功通知（邀请记录）
     *
     * @param openId
     * @param title
     * @param nickName
     * @param loginCode
     * @param registerDate
     * @param remark
     * @param pagepath
     */
    public WeixinPushEntity(String openId, String title, String nickName, String loginCode, Date registerDate,
                            String remark, String pagepath) {
        this(pagepath);
        this.templateId = "uGvTLyCoHVVq1oSeJPUyVfwGled2TjDUYjtoXVckUI4";
        this.openId = openId;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(nickName));
        obj.put("keyword2", processField(loginCode));
        obj.put("keyword3", processField(dateFormat.format(registerDate)));
        obj.put("remark", processField(remark));
    }

    /**
     * 提现结果通知（提现通知）
     *
     * @param openId
     * @param title
     * @param applyDate
     * @param applyAmount
     * @param result
     * @param remark
     * @param pagepath
     */
    public WeixinPushEntity(String openId, String title, Date applyDate, BigDecimal applyAmount, String result,
                            String remark, String pagepath) {
        this(pagepath);
        this.templateId = "-sPLd7tZ5FbiBXyxD4vAPPZjzYhr6tyLvqzY5w1uF_Y";
        this.openId = openId;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(dateFormat.format(applyDate)));
        obj.put("keyword2", processField(applyAmount.toString()));
        obj.put("keyword3", processField(result));
        obj.put("remark", processField(remark));
    }

    /**
     * 帐户资金变动提醒（流水变化）
     *
     * @param openId
     * @param title
     * @param changeDate
     * @param changeAmount
     * @param direction
     * @param balanceAmount
     * @param remark
     * @param pagepath
     */
    public WeixinPushEntity(String openId, String title, Date changeDate, BigDecimal changeAmount, Integer direction,
                            BigDecimal balanceAmount, String remark, String pagepath) {
        this(pagepath);
        this.templateId = "f0jt5rUyrX0b4gHDiVKaGX7lBQRRFbZb8JcerY976kg";
        this.openId = openId;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(dateFormat.format(changeDate)));
        obj.put("keyword2", processField((direction > 0 ? "+" : "-") + changeAmount.toString()));
        obj.put("keyword3", processField(balanceAmount.toString()));
        obj.put("remark", processField(remark));
    }

    /**
     * 交易汇总通知（未支付订单）
     *
     * @param openId
     * @param title
     * @param limitDate
     * @param count
     * @param remark
     * @param pagepath
     */
    public WeixinPushEntity(String openId, String title, Date limitDate, Integer count, String remark,
                            String pagepath) {
        this(pagepath);
        this.templateId = "uNf9SXmzzXzf8p6SjafbwCwqglaeuR_ysxkFm6us0yA";
        this.openId = openId;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(dateFormat.format(limitDate)));
        obj.put("keyword2", processField(String.valueOf(count)));
        obj.put("remark", processField(remark));
    }

    /**
     * 统计结果通知（访客统计）
     *
     * @param openId
     * @param title
     * @param companyName
     * @param start
     * @param end
     * @param content
     * @param remark
     * @param pagepath
     */
    public WeixinPushEntity(String openId, String title, String companyName, Date start, Date end, String content,
                            String remark, String pagepath) {
        this(pagepath);
        this.templateId = "0xNhPHRG8LfSsJ2JjGuYkJXzonFIYVcmd3i0QOMOGds";
        this.openId = openId;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(companyName));
        obj.put("keyword2", processField(dateFormat.format(start) + "-" + dateFormat.format(end)));
        obj.put("keyword3", processField(content));
        obj.put("remark", processField(remark));
    }

    /**
     * 业务预警关闭通知
     *
     * @param openId
     * @param title
     * @param warnGoods
     * @param warnTitle
     * @param warnStatus
     * @param warnType
     * @param remark
     * @param pagepath
     */
    public WeixinPushEntity(String openId, String title, String warnGoods, String warnTitle, String warnStatus,
                            String warnType, String remark, String pagepath) {
        this(pagepath);
        if (pagepath == null) {
            this.miniprogram = null;
        }
        this.templateId = "hr9SXgOhNCvQed2CX13o9PRzudctcHNK5aVq6GxKT0U";
        this.openId = openId;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(warnGoods));
        obj.put("keyword2", processField(warnTitle));
        obj.put("keyword3", processField(warnStatus));
        obj.put("keyword4", processField(warnType));
        obj.put("remark", processField(remark));
    }

    /**
     * 活动加入提醒
     *
     * @param title
     * @param content
     * @param attendMan
     * @param attendDate
     * @param remark
     * @param pagepath
     * @param url
     */
    public WeixinPushEntity(String openId, String title, String content, String attendMan, Date attendDate,
                            String remark, String pagepath, String url) {
        this(pagepath);
        if (pagepath == null) {
            this.miniprogram = null;
        }
        this.templateId = "ROQ57n4de7Oc2Z5d5XvWCm-GOXVZaCHcdXBMQUTTacE";
        this.openId = openId;
        this.url = url;
        obj.put("first", processField(title));
        obj.put("keyword1", processField(content));
        obj.put("keyword2", processField(DateUtil.dayFormatCn(attendDate)));
        obj.put("keyword3", processField(attendMan));
        obj.put("remark", processField(remark));
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(JSONObject miniprogram) {
        this.miniprogram = miniprogram;
    }

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    private JSONObject processField(String value) {
        JSONObject obj = new JSONObject();
        obj.put("value", value);
        obj.put("color", "#173177");
        return obj;
    }

}
