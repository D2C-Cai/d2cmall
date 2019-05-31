package com.d2c.member.search.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreUserDTO;
import com.d2c.common.base.utils.StringUt;

public class SearcherMemberAttention extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 设计师LOGO
     */
    private String designerPic;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getDesignerPic() {
        return designerPic;
    }

    public void setDesignerPic(String designerPic) {
        this.designerPic = designerPic;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getMemberId());
        obj.put("nickName", StringUt.hideMobile(this.getNickName()));
        obj.put("headPic", this.getHeadPic());
        obj.put("designerId", this.getDesignerId());
        obj.put("designerName", this.getDesignerName());
        obj.put("designerPic", this.getDesignerPic());
        return obj;
    }

}
