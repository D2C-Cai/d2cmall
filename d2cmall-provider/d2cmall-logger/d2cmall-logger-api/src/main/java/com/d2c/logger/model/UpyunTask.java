package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * Upyun日志
 */
@Table(name = "upyun_task")
public class UpyunTask extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 任务Ids
     */
    private String taskIds;
    /**
     * 目标类型
     */
    private String sourceType;
    /**
     * 目标Id
     */
    private Long sourceId;
    /**
     * 状态 0 未完成 1已转码
     */
    private Integer status;
    /**
     * 回调视频地址
     */
    private String video;
    /**
     * 回调图片地址
     */
    private String pic;

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public enum SourceType {
        SHARE, PRODUCT, BRAND, COMMENT, WARDROBEC
    }

}
