package com.d2c.member.view;

import com.d2c.common.api.dto.BaseDTO;
import com.d2c.member.mongo.enums.MemberTaskState;

import java.util.Date;

/**
 * 用户任务数据
 *
 * @author wull
 */
public class MemberTaskVO extends BaseDTO {

    private static final long serialVersionUID = 3385780775527149555L;
    /**
     * 任务编码
     */
    private String code;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务说明副标题
     */
    private String subName;
    /**
     * 任务说明
     */
    private String explain = "";
    /**
     * 任务类型
     */
    private String type;
    /**
     * 完成状态
     */
    private String state = MemberTaskState.TODO_TASK.name();
    /**
     * 附送积分
     */
    private Integer point;
    /**
     * 服务跳转URL
     */
    private String url = "";
    /**
     * 已执行次数
     */
    private Integer count = 0;
    /**
     * 最大执行次数
     */
    private Integer max = 1;
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

    public String getStateShow() {
        return MemberTaskState.getValueOf(state).toString();
    }
    //***************************************

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getDoAfter() {
        return doAfter;
    }

    public void setDoAfter(String doAfter) {
        this.doAfter = doAfter;
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

}
