package com.d2c.member.mongo.model;

import com.d2c.common.base.utils.StringUt;
import com.d2c.common.mongodb.model.BaseMongoDO;
import com.d2c.member.model.MemberTask;
import com.d2c.member.mongo.enums.MemberTaskState;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MemberTaskExecDO extends BaseMongoDO {

    private static final long serialVersionUID = -2582856287391456493L;
    @Indexed
    private Long memberId;
    /**
     * 任务编码
     */
    @Indexed
    private String code;
    /**
     * 任务类型
     */
    @Indexed
    private String taskType;
    /**
     * 完成状态
     */
    private String state;
    /**
     * 附送积分
     */
    private Integer point;
    /**
     * 执行次数
     */
    private Integer count = 0;
    /**
     * 最大执行次数
     */
    private Integer maxCount = 0;

    public MemberTaskExecDO() {
    }

    public MemberTaskExecDO(Long memberId, MemberTask task) {
        this.memberId = memberId;
        this.code = task.getCode();
        this.taskType = task.getType();
        this.point = task.getPoint();
        this.maxCount = task.getMax();
        this.state = MemberTaskState.TODO_TASK.name();
    }

    /**
     * 再次同步任务作业
     */
    public void syncTask(MemberTask task) {
        this.maxCount = task.getMax();
    }

    void initId() {
        this.id = StringUt.join("_", memberId, code);
    }

    public Integer add() {
        count++;
        isDone();
        return count;
    }

    /**
     * 是否完成任务
     */
    public boolean isDone() {
        if (MemberTaskState.isDone(state)) {
            return true;
        }
        if (count >= maxCount) {
            this.state = MemberTaskState.AWARD_TASK.name();
            return true;
        }
        return false;
    }

    /**
     * 是否已领取奖励
     */
    public boolean isAward() {
        return MemberTaskState.isAward(state);
    }
    //*************************************

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
