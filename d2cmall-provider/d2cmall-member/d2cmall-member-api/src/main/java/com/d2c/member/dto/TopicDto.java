package com.d2c.member.dto;

import com.d2c.member.model.Topic;

public class TopicDto extends Topic {

    private static final long serialVersionUID = 1L;
    /**
     * 买家秀数量
     */
    private Integer shareCount;

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

}
