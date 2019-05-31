package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class ErrorLogSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 来源
     */
    private String source;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 错误信息的关键字
     */
    private String keyword;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywords() {
        String keywords = "";
        if (StringUtils.isNotBlank(keyword)) {
            String[] list = keyword.split(",");
            if (list.length > 0) {
                keywords = "%";
                for (String k : list) {
                    keywords = keywords + k + '%';
                }
            }
        }
        return keywords;
    }

}
