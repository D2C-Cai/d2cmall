package com.d2c.logger.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 推荐日志
 */
@Table(name = "log_recommend")
public class RecommendLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 0:解绑 1：绑定
     */
    private int type = 1;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 推荐人ID
     */
    private Long recId;
    /**
     * 头像
     */
    private String headPic;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 返利金额
     */
    private BigDecimal recommendRebates;
    /**
     * 返利时间
     */
    private Date recommendDate;

    public RecommendLog(int type, Long memberId, Long recId, String nickName, BigDecimal recommendRebates,
                        Date recommendDate, String headPic) {
        super();
        this.type = type;
        this.memberId = memberId;
        this.recId = recId;
        this.nickName = nickName;
        this.recommendRebates = recommendRebates;
        this.recommendDate = recommendDate;
        this.headPic = headPic;
    }

    public RecommendLog() {
        super();
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getnickName() {
        return nickName;
    }

    public void setnickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getRecommendRebates() {
        return recommendRebates;
    }

    public void setRecommendRebates(BigDecimal recommendRebates) {
        this.recommendRebates = recommendRebates;
    }

    public JSONObject toSimpleJson() {
        JSONObject obj = new JSONObject();
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
        obj.put("nickname", this.getnickName() == null ? "" : this.getnickName());
        obj.put("recommendDate", this.getRecommendDate() == null ? "" : DateUtil.day2str2(this.getRecommendDate()));
        obj.put("recommendRebates", this.getRecommendRebates());
        return obj;
    }

}
