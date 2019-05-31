package com.d2c.common.mongodb.model;

import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.enums.ReportStatus;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * 任务报表类
 *
 * @author wull
 */
public abstract class ReportMongoDO extends BaseMongoDO {

    private static final long serialVersionUID = 1L;
    protected String name;
    @Indexed
    protected Date gmtCreate;
    protected String status = ReportStatus.START.name();
    protected long count;
    protected long errorCnt;
    protected String errors;

    /**
     * 持续时间
     */
    public long takeKeepTime() {
        return getGmtModified().getTime() - gmtCreate.getTime();
    }

    public String showKeepTime() {
        return DateUt.duration(takeKeepTime());
    }

    @Override
    public String toString() {
        return "已生成 " + count + " 数据";
    }

    public void add() {
        count++;
    }

    public void addCount(int cnt) {
        count = count + cnt;
    }

    public void restart(long count) {
        this.count = count;
    }

    public void addError(String error) {
        errorCnt++;
        if (errorCnt <= 50) {
            errors += "\n" + error;
            if (errorCnt == 50) {
                errors += "......";
            }
        }
    }

    public String takeStatusName() {
        return ReportStatus.getRemark(status);
    }
    //*****************************************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(long errorCnt) {
        this.errorCnt = errorCnt;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
