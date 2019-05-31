package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;

import javax.persistence.Table;

/**
 * 点赞的买家秀
 */
@Table(name = "m_member_like")
public class MemberLike extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
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
     * 买家秀ID
     */
    private Long shareId;
    /**
     * 买家秀标题
     */
    private String shareName;
    /**
     * 买家秀头图
     */
    private String sharePic;

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

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getSharePicFirst() {
        if (this.sharePic != null) {
            return sharePic.split(",")[0];
        }
        return "";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getMemberId());
        obj.put("nickName", StringUt.hideMobile(this.getNickName()));
        obj.put("headPic", this.getHeadPic());
        obj.put("shareId", this.getShareId());
        obj.put("shareName", this.getShareName());
        obj.put("sharePic", this.getSharePicFirst());
        return obj;
    }

}
