package com.d2c.util.file;

import com.d2c.util.date.DateUtil;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportFileBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String EXCEL_SAVE_PATH = "/mnt/www/download";// 保存路径
    private static final String EXCEL_DOWNLOAD_PATH = "/download";// 下载路径
    /**
     * 错误信息
     */
    private StringBuilder errorMsg = new StringBuilder();
    /**
     * 输出的文件文件目录
     */
    private String outPath = null;
    /**
     * 下载excel路径
     */
    private String filePath = null;
    /**
     * 文件名称
     */
    private String fileName = null;
    /**
     * 表名称
     */
    private String sheetName = null;
    /**
     * 文件大小
     */
    private long fileSize = 0;

    public ExportFileBean() {
    }

    public ExportFileBean(String fileName, String username) {
        Date date = new Date();
        int year = DateUtil.getYearOfDate(date);
        int month = DateUtil.getMonthOfYear(date);
        int day = DateUtil.getDayOfMonth(date);
        String folderName = "/excel/" + year + "/" + month + "/" + day;
        File dir = new File(EXCEL_SAVE_PATH + folderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String nowdate = format.format(now);
        this.sheetName = fileName;
        fileName = fileName + "_" + nowdate + ".xls";
        this.outPath = dir.getPath() + "/" + fileName;
        this.filePath = folderName + "/" + fileName;
        this.fileName = fileName;
    }

    public StringBuilder getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(StringBuilder errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * 生成文件下载路径
     */
    public String getDownloadPath() {
        return EXCEL_DOWNLOAD_PATH + filePath;
    }

}
