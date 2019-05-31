package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;

/**
 * 会员搜索记录
 */
@Table(name = "membersearchinfo")
public class MemberSearchInfo extends PreUserDO {

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

    public boolean check() {
        return StringUtils.isBlank(keyword);
    }

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatistic() {
        return statistic;
    }

    public void setStatistic(int statistic) {
        this.statistic = statistic;
    }

}
