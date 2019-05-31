package com.d2c.logger.search.support;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.search.model.SearcherMessage;

import java.io.Serializable;

public class MessageSearchHelp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 大类ID
     */
    private Long majorType;
    /**
     * 未读数量
     */
    private Long unReadCount;
    /**
     * 最新的一条消息
     */
    private SearcherMessage searcherMessage;

    public Long getMajorType() {
        return majorType;
    }

    public void setMajorType(Long majorType) {
        this.majorType = majorType;
    }

    public Long getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(Long unReadCount) {
        this.unReadCount = unReadCount;
    }

    public SearcherMessage getSearcherMessage() {
        return searcherMessage;
    }

    public void setSearcherMessage(SearcherMessage searcherMessage) {
        this.searcherMessage = searcherMessage;
    }

    public JSONObject toJson() {
        JSONObject json = this.getSearcherMessage().toJson();
        json.put("unReadCount", this.getUnReadCount());
        json.put("majorType", this.getMajorType());
        return json;
    }

}
