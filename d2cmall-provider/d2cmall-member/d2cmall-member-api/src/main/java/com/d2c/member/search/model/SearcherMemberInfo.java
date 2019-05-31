package com.d2c.member.search.model;

import com.d2c.common.api.dto.PreUserDTO;

public class SearcherMemberInfo extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberInfoId;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 是否已汇总，默认0
     */
    private int statistic = 0;
    /**
     * IP
     */
    private String ip;

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getStatistic() {
        return statistic;
    }

    public void setStatistic(int statistic) {
        this.statistic = statistic;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
