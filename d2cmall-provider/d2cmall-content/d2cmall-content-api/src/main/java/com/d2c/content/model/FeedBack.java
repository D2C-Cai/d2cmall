package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体类 -客户反馈
 */
@Table(name = "v_feedback")
public class FeedBack extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 登录号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String loginCode;
    /**
     * 会员名字
     */
    private String nickName;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 会员手机号
     */
    private String mobile;
    /**
     * 处理状态 0：未处理、1：处理中、2：重新处理、8：处理完成、-1：已关闭
     */
    private Integer status = 0;
    /**
     * 会员邮箱
     */
    private String email;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 回复内容
     */
    private String reply;
    /**
     * 回复人
     */
    private String replyMan;
    /**
     * 设备
     */
    private String device;
    /**
     * 版本号
     */
    private String version;
    /**
     * 图片
     */
    private String pic;
    /**
     * 类型
     */
    private String type = FeedBackType.FEEDBACK.toString();
    /**
     * 备注
     */
    private String meno;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyMan() {
        return replyMan;
    }

    public void setReplyMan(String replyMan) {
        this.replyMan = replyMan;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getTypeName() {
        switch (type) {
            case "FEEDBACK":
                return "意见反馈";
            case "ERROR":
                return "功能异常";
            case "ADVISE":
                return "新功能建议";
            case "SLOW":
                return "发货太慢";
            case "QUALITY":
                return "商品质量";
            case "SERVICEATTITUDE":
                return "客服态度";
            case "PRICEDIFFERENCE":
                return "买贵补差价";
            case "OTHER":
                return "其他";
        }
        return "其他";
    }

    public String[] getPicArray() {
        if (this.getPic() != null) {
            return this.getPic().split(",");
        }
        return new String[0];
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("nickName", StringUt.hideMobile(this.getNickName()));
        obj.put("headPic", this.getHeadPic());
        obj.put("content", this.getContent());
        obj.put("reply", this.getReply());
        obj.put("pic", this.getPicArray());
        return obj;
    }

    public enum FeedBackType {
        FEEDBACK("意见反馈") {
            @Override
            public List<Map<String, String>> getTypes() {
                if (feedBackTypes.size() == 0) {
                    feedBackTypes.add(getTypeItem("意见反馈", "FEEDBACK"));
                }
                return feedBackTypes;
            }
        },
        QUESTION("体验问题") {
            @Override
            public List<Map<String, String>> getTypes() {
                if (questionTypes.size() == 0) {
                    questionTypes.add(getTypeItem("功能异常", "ERROR"));
                    questionTypes.add(getTypeItem("新功能建议", "ADVISE"));
                    questionTypes.add(getTypeItem("其他", "OTHER"));
                }
                return questionTypes;
            }
        },
        COMPLAINT("投诉") {
            @Override
            public List<Map<String, String>> getTypes() {
                if (complaintTypes.size() == 0) {
                    complaintTypes.add(getTypeItem("发货太慢", "SLOW"));
                    complaintTypes.add(getTypeItem("商品质量", "QUALITY"));
                    complaintTypes.add(getTypeItem("客服态度", "SERVICEATTITUDE"));
                    complaintTypes.add(getTypeItem("买贵补差价", "PRICEDIFFERENCE"));
                    complaintTypes.add(getTypeItem("其他", "OTHER"));
                }
                return complaintTypes;
            }
        };
        private static List<Map<String, String>> feedBackTypes;
        private static List<Map<String, String>> questionTypes;
        private static List<Map<String, String>> complaintTypes;

        static {
            feedBackTypes = new ArrayList<Map<String, String>>();
            questionTypes = new ArrayList<Map<String, String>>();
            complaintTypes = new ArrayList<Map<String, String>>();
        }

        private String name;
        private String type;
        private String display;

        FeedBackType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Map<String, String>> getTypes() {
            if (FeedBackType.FEEDBACK.equals(this)) {
                return feedBackTypes;
            }
            if (FeedBackType.QUESTION.equals(this)) {
                return questionTypes;
            }
            if (FeedBackType.COMPLAINT.equals(this)) {
                return complaintTypes;
            }
            return new ArrayList<Map<String, String>>();
        }

        public HashMap<String, String> getTypeItem(String display, String type) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("type", type);
            map.put("display", display);
            return map;
        }

        ;
    }

}
