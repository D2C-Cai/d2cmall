package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 会员任务列表
 */
@Table(name = "m_member_task")
public class MemberTask extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 任务编码 ( codeType | subCode)
     */
    @AssertColumn("任务编码不能为空")
    private String code;
    /**
     * 任务类型编码
     */
    @AssertColumn("任务类型编码不能为空")
    private String codeType;
    /**
     * 业务编码
     */
    private String subCode;
    /**
     * 任务名称
     */
    @AssertColumn("任务名称不能为空")
    private String name;
    /**
     * 任务说明副标题
     */
    @AssertColumn("任务副标题不能为空")
    private String subName;
    /**
     * 任务类型
     */
    @AssertColumn("任务类型不能为空")
    private String type;
    /**
     * 附送积分
     */
    private Integer point = 0;
    /**
     * 最大执行次数
     */
    private Integer max = 3;
    /**
     * 点击跳转URL
     */
    private String url;
    /**
     * 是否启用
     */
    private Boolean exec = false;
    /**
     * 是否固定，不允许删除
     */
    private Boolean fixed = false;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 奖励类型
     */
    private String doAfter;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建code编码
     */
    public static String buildCode(String codeType, String subCode) {
        if (codeType == null)
            return null;
        if (subCode != null) {
            return codeType + "|" + subCode;
        }
        return codeType;
    }

    @Override
    public String toString() {
        return code + ":" + name;
    }

    public void makeCode() {
        code = buildCode(codeType, subCode);
    }

    public String getCode() {
        if (code == null) {
            makeCode();
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Boolean getExec() {
        return exec;
    }

    public void setExec(Boolean exec) {
        this.exec = exec;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDoAfter() {
        return doAfter;
    }

    public void setDoAfter(String doAfter) {
        this.doAfter = doAfter;
    }

}
