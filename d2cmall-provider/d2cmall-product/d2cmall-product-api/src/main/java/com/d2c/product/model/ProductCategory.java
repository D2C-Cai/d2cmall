package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 二级分类
 */
@Table(name = "p_product_category")
public class ProductCategory extends PreUserDO {

    /**
     * 树路径分隔符
     */
    public static final String PATH_SEPARATOR = ",";
    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("商品分类名称不能为空")
    private String name;
    /**
     * 代号
     */
    @AssertColumn("商品分类编号不能为空")
    private String code;
    /**
     * 排序
     */
    private Integer sequence = 0;
    /**
     * 树路径
     */
    private String path;
    /**
     * 大类ID
     */
    private Long topId;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 深度(=1为初级 =n则为子级)
     */
    private Integer depth = 1;
    /**
     * 是否末级
     */
    private Integer leaf = 1;
    /**
     * 停用 0/启用 1
     */
    private Integer status = 1;
    /**
     * 图片
     */
    private String pic;
    /**
     * 英文名
     */
    private String eName;
    /**
     * banner图
     */
    private String bannerPic;
    /**
     * banner跳转地址
     */
    private String bannerUrl;
    /**
     * 主规格组ID
     */
    private Long sp1GroupId;
    /**
     * 副规格组ID
     */
    private Long sp2GroupId;
    /**
     * 商品参数组ID
     */
    private Long attributeGroupId = 0L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Long getSp1GroupId() {
        return sp1GroupId;
    }

    public void setSp1GroupId(Long sp1GroupId) {
        this.sp1GroupId = sp1GroupId;
    }

    public Long getSp2GroupId() {
        return sp2GroupId;
    }

    public void setSp2GroupId(Long sp2GroupId) {
        this.sp2GroupId = sp2GroupId;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("name", getName());
        obj.put("id", getId());
        obj.put("path", getPath());
        return obj;
    }

}