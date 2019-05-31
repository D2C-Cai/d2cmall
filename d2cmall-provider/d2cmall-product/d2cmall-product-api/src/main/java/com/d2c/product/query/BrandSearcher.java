package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.product.search.query.DesignerSearchBean;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Date;

public class BrandSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 品牌ID
     */
    private Long designerId;
    /**
     * 设计师ID
     */
    private Long designersId;
    /**
     * 名称
     */
    private String name;
    /**
     * 设计师编码
     */
    private String brandCode;
    /**
     * 简介
     */
    private String intro;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 是否推荐
     */
    private Boolean isRecommend;
    /**
     * 编码
     */
    private String code;
    /**
     * 上下架
     */
    private Boolean marketable;
    /**
     * 设计师名
     */
    private String designers;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 是否删除
     */
    private Boolean deleted;
    /**
     * 排序
     */
    private String orderByStr;
    /**
     * 是否可以门店试衣
     */
    private Integer subscribe;
    /**
     * 设计领域
     */
    private String designArea;
    /**
     * 国家
     */
    private String country;
    /**
     * 风格
     */
    private String style;
    /**
     * 分组
     */
    private String pageGroup;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 状态
     */
    private Integer mark;
    /**
     * 是否支持售后
     */
    private Integer after;
    /**
     * 是否支持货到付款
     */
    private Integer cod;
    /**
     * 上架时间
     */
    private Date beginUpDate;
    private Date endUpDate;
    /**
     * 下架时间
     */
    private Date beginDownDate;
    private Date endDownDate;
    /**
     * 创建时间
     */
    private Date beginCreateDate;
    private Date endCreateDate;
    /**
     * 系列ID
     */
    private Long seriesId;
    /**
     * 优先品牌
     */
    private Integer prior;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getDesignersId() {
        return designersId;
    }

    public void setDesignersId(Long designersId) {
        this.designersId = designersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getMarketable() {
        return marketable;
    }

    public void setMarketable(Boolean marketable) {
        this.marketable = marketable;
    }

    public String getDesignArea() {
        return designArea;
    }

    public void setDesignArea(String designArea) {
        this.designArea = designArea;
    }

    public String getDesigners() {
        return designers;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOrderByStr() {
        return orderByStr;
    }

    public void setOrderByStr(String orderByStr) {
        this.orderByStr = orderByStr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Date getBeginUpDate() {
        return beginUpDate;
    }

    public void setBeginUpDate(Date beginUpDate) {
        this.beginUpDate = beginUpDate;
    }

    public Date getEndUpDate() {
        return endUpDate;
    }

    public void setEndUpDate(Date endUpDate) {
        this.endUpDate = endUpDate;
    }

    public Date getBeginDownDate() {
        return beginDownDate;
    }

    public void setBeginDownDate(Date beginDownDate) {
        this.beginDownDate = beginDownDate;
    }

    public Date getEndDownDate() {
        return endDownDate;
    }

    public void setEndDownDate(Date endDownDate) {
        this.endDownDate = endDownDate;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public Integer getPrior() {
        return prior;
    }

    public void setPrior(Integer prior) {
        this.prior = prior;
    }

    public DesignerSearchBean convert2DesignerSearchBean() {
        DesignerSearchBean searcherBean = new DesignerSearchBean();
        searcherBean.setKeyword(this.getKeyword());
        searcherBean.setCountry(this.getCountry());
        searcherBean.setDesignArea(this.getDesignArea());
        searcherBean.setStyle(this.getStyle());
        searcherBean.setTagId(this.getTagId());
        searcherBean.setPageGroup(this.getPageGroup());
        String[] sortFields = new String[]{};
        SortOrder[] sortOrders = new SortOrder[]{};
        if ("sales".equalsIgnoreCase(this.getOrderByStr())) {
            sortFields = new String[]{"sales", "sortDate"};
            sortOrders = new SortOrder[]{SortOrder.DESC, SortOrder.DESC};
        } else {
            sortFields = new String[]{"tfans", "sortDate"};
            sortOrders = new SortOrder[]{SortOrder.DESC, SortOrder.DESC};
        }
        searcherBean.setOrders(sortOrders);
        searcherBean.setSortFields(sortFields);
        searcherBean.setMark(1);
        return searcherBean;
    }

    public DesignerSearchBean convert2TagDesignerSearchBean() {
        DesignerSearchBean searcherBean = new DesignerSearchBean();
        searcherBean.setTagId(this.getTagId());
        String[] sortFields = new String[]{"tagSort"};
        SortOrder[] sortOrders = new SortOrder[]{SortOrder.DESC};
        searcherBean.setOrders(sortOrders);
        searcherBean.setSortFields(sortFields);
        searcherBean.setMark(1);
        return searcherBean;
    }

}
