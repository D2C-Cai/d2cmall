package com.d2c.member.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Comment;
import com.d2c.member.model.CommentReply;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CommentDto extends Comment {

    private static final long serialVersionUID = 1L;
    /**
     * 评论回复
     */
    private List<CommentReply> commentReplys;

    public List<CommentReply> getCommentReplys() {
        return commentReplys;
    }

    public void setCommentReplys(List<CommentReply> commentReplys) {
        this.commentReplys = commentReplys;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        JSONArray replys = new JSONArray();
        for (CommentReply replay : this.getCommentReplys()) {
            JSONObject json = new JSONObject();
            json.put("id", replay.getId());
            json.put("commentId", replay.getCommentId());
            json.put("createDate", DateUtil.second2str2(replay.getCreateDate()));
            json.put("content", replay.getContent());
            json.put("type", replay.getType().toString());
            json.put("pic", replay.getPicsToArray());
            replys.add(json);
        }
        obj.put("replys", replys);
        return obj;
    }

    public String getDeviceName() {
        if (StringUtils.isNotBlank(this.getDevice())) {
            return DeviceTypeEnum.valueOf(this.getDevice()).getDisplay();
        }
        return "电脑PC";
    }

}
