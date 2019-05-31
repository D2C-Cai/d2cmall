package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;

/**
 * Pili直播
 */
@Table(name = "m_live_pili")
public class PiliLive extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 直播流ID
     */
    private String streamId;
    /**
     * RTMP推流地址
     */
    private String pushUrl;
    /**
     * RTMP直播地址
     */
    private String rtmpUrl;
    /**
     * HLS直播地址
     */
    private String hlsUrl;
    /**
     * HDL直播地址
     */
    private String hdlUrl;
    /**
     * 截图直播地址
     */
    private String picUrl;
    /**
     * 录像回看地址
     */
    private String replayUrl;
    /**
     * 直播状态 0:初始创建 4：直播开始 8：往期回顾
     */
    private Integer status = 0;
    /**
     * 直播标题
     */
    private String title;
    /**
     * 直播封面
     */
    private String cover;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员标识
     */
    private Integer memberType;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员性别
     */
    private String sex;
    /**
     * 置顶 0：不置顶 1：置顶
     */
    private Integer top = 0;
    /**
     * 上下架 0：下架 1：上架
     */
    private Integer mark = 1;
    /**
     * 累计观看人数（含倍率公式）
     */
    private Integer vcount = 0;
    /**
     * 累计观看人数（不含倍率公式）
     */
    private Integer tcount = 0;
    /**
     * 僵尸粉人数
     */
    private Integer vfans = 0;
    /**
     * 实时人数倍率
     */
    private Integer vrate = 0;
    /**
     * 礼物倍率
     */
    private Integer ratio = 0;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师名称
     */
    private String designerName;

    public PiliLive() {
    }

    public PiliLive(MemberInfo member) {
        this.streamId = SerialNumUtil.buildLiveStreamID();
        this.memberId = member.getId();
        this.memberType = member.getType();
        this.headPic = member.getHeadPic();
        this.nickname = member.getDisplayName();
        this.sex = member.getSex();
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public String getHdlUrl() {
        return hdlUrl;
    }

    public void setHdlUrl(String hdlUrl) {
        this.hdlUrl = hdlUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getReplayUrl() {
        return replayUrl;
    }

    public void setReplayUrl(String replayUrl) {
        this.replayUrl = replayUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getVcount() {
        return vcount;
    }

    public void setVcount(Integer vcount) {
        this.vcount = vcount;
    }

    public Integer getTcount() {
        return tcount;
    }

    public void setTcount(Integer tcount) {
        this.tcount = tcount;
    }

    public Integer getVfans() {
        return vfans;
    }

    public void setVfans(Integer vfans) {
        this.vfans = vfans;
    }

    public Integer getVrate() {
        return vrate;
    }

    public void setVrate(Integer vrate) {
        this.vrate = vrate;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("status", this.getStatus());
        obj.put("title", this.getTitle());
        obj.put("cover", this.getCover());
        obj.put("memberId", this.getMemberId());
        obj.put("memberType", this.getMemberType());
        obj.put("headPic", this.getHeadPic());
        obj.put("nickname", this.getNickname());
        obj.put("sex", this.getSex());
        obj.put("designerId", this.getDesignerId());
        obj.put("designerName", this.getDesignerName());
        obj.put("streamId", this.getStreamId());
        obj.put("pushUrl", this.getPushUrl());
        obj.put("rtmpUrl", this.getRtmpUrl());
        obj.put("hlsUrl", this.getHlsUrl());
        obj.put("hdlUrl", this.getHdlUrl());
        obj.put("replayUrl", this.getReplayUrl() == null ? "" : this.getReplayUrl());
        obj.put("vcount", this.getVcount());
        obj.put("vfans", this.getVfans());
        return obj;
    }

}
