package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.text.DecimalFormat;

/**
 * 导出日志
 */
@Table(name = "log_export")
public class ExportLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private long fileSize;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSizeString() {
        DecimalFormat df2 = new DecimalFormat("###.00");
        String fileSizeString = df2.format((this.fileSize < 1024 ? 1024 : this.fileSize) / 1024) + "KB";
        return fileSizeString;
    }

}
