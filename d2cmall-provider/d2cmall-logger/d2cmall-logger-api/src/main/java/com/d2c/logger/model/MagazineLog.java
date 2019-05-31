package com.d2c.logger.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 杂志浏览日志
 */
@Table(name = "log_magazine")
public class MagazineLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 杂志ID
     */
    private Long magazineId;
    /**
     * 杂志名称
     */
    private String magazineName;
    /**
     * 杂志编码
     */
    private String magazineCode;
    /**
     * 杂志图片
     */
    private String magazinePic;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public String getMagazineCode() {
        return magazineCode;
    }

    public void setMagazineCode(String magazineCode) {
        this.magazineCode = magazineCode;
    }

    public String getMagazinePic() {
        return magazinePic;
    }

    public void setMagazinePic(String magazinePic) {
        this.magazinePic = magazinePic;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("memberId", this.getMemberId());
        obj.put("magazineId", this.getMagazineId());
        obj.put("magazineName", this.getMagazineName());
        obj.put("magazineCode", this.getMagazineCode());
        obj.put("magazinePic", this.getMagazinePic());
        return obj;
    }

}
