package com.d2c.util.file;

import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CSVUtil {

    private static final String FILE_SAVE_PATH = "/mnt/www/download";// 保存路径
    private static final String FILE_DOWNLOAD_PATH = "/download";// 下载路径
    /**
     * 错误信息
     */
    private StringBuilder errorMsg = new StringBuilder();
    /**
     * 输出的文件文件目录
     */
    private String outPath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小
     */
    private long fileSize = 0;
    /**
     * 表头
     */
    private List<Object> title = new ArrayList<>();
    /**
     * 每一行的内容
     */
    private List<Object> rowN = new ArrayList<>();

    /**
     * 生成文件
     *
     * @param filename
     * @return
     */
    public File getFile(String filename) {
        return getFile(filename, false);
    }

    public File getFile(String filename, Boolean createNew) {
        Date date = new Date();
        int year = DateUtil.getYearOfDate(date);
        int month = DateUtil.getMonthOfYear(date);
        int day = DateUtil.getDayOfMonth(date);
        String folderName = "/excel/" + year + "/" + month + "/" + day;
        File dir = new File(FILE_SAVE_PATH + folderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(FILE_SAVE_PATH + folderName + "/" + filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                errorMsg.append(e1.getMessage());
            }
        }
        if (createNew) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.setOutPath(FILE_DOWNLOAD_PATH + folderName + "/" + fileName);
        return file;
    }

    /**
     * 生成文件
     *
     * @param year
     * @param month
     * @param day
     * @param filename
     * @return
     */
    public File getFile(int year, int month, int day, String filename) {
        String folderName = "/excel/" + year + "/" + month + "/" + day;
        File dir = new File(FILE_SAVE_PATH + folderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(FILE_SAVE_PATH + folderName + "/" + filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 将标题写入文件
     *
     * @param listMap
     * @return
     */
    public boolean writeTitleToFile(String[] titleNames) {
        return writeTitleToFile(titleNames, fileName);
    }

    public boolean writeTitleToFile(String[] titleNames, String fileName) {
        return writeTitleToFile(titleNames, fileName, "GBK");
    }

    public boolean writeTitleToFile(String[] titleNames, String fileName, String charset) {
        File file = getFile(fileName);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), charset), 1024);
            setTitleNames(titleNames);
            writeTitle(bw);
            bw.flush();
        } catch (Exception e) {
            errorMsg.append(e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                FileChannel fc = null;
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                fc = fis.getChannel();
                this.setFileSize(fc.size());
                fc.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                errorMsg.append(e.getMessage());
            }
        }
        return errorMsg.length() == 0;
    }

    /**
     * 获取表的标题
     *
     * @param titleNames
     * @return
     */
    private List<Object> setTitleNames(String[] titleNames) {
        title.clear();
        for (int j = 0; j < titleNames.length; j++) {
            String item = titleNames[j];
            title.add(item);
        }
        return title;
    }

    /**
     * 写入一行标题
     *
     * @param csvLine
     * @param bw
     * @throws IOException
     */
    private void writeTitle(BufferedWriter bw) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
            String line = String.valueOf(title.get(i));
            if (StringUtil.isBlank(line) || line.equals("null")) {
                line = "";
            }
            sb.append("\"").append(line.replaceAll("\"", "\"\"")).append("\",");
        }
        bw.write(sb.deleteCharAt(sb.length() - 1).toString());
        bw.newLine();
    }

    /**
     * 将一行写入文件
     *
     * @param listMap
     * @param titleNames
     * @return
     */
    public boolean writeRowToFile(List<Map<String, Object>> listMap, String fileName) {
        return writeRowToFile(listMap, fileName, "GBK");
    }

    public boolean writeRowToFile(List<Map<String, Object>> listMap, String fileName, String charset) {
        File file = getFile(fileName);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), charset), 1024);
            if (listMap != null && listMap.size() >= 1) {
                for (Map<String, Object> map : listMap) {
                    setRowContent(map);
                    writeRow(bw);
                }
            }
            bw.flush();
        } catch (Exception e) {
            errorMsg.append(e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                FileChannel fc = null;
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                fc = fis.getChannel();
                this.setFileSize(fc.size());
                fc.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                errorMsg.append(e.getMessage());
            }
        }
        return errorMsg.length() == 0;
    }

    public boolean writeRowToFile(List<Map<String, Object>> listMap) {
        return writeRowToFile(listMap, fileName);
    }

    /**
     * 获取每一行的内容
     *
     * @param map
     * @param titleNames
     * @return
     */
    private List<Object> setRowContent(Map<String, Object> map) {
        rowN.clear();
        for (Object titleName : title) {
            rowN.add(map.get(titleName));
        }
        return rowN;
    }

    /**
     * 写入一行内容
     *
     * @param csvLine
     * @param bw
     * @throws IOException
     */
    private void writeRow(BufferedWriter bw) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < rowN.size(); i++) {
            String line = String.valueOf(rowN.get(i));
            if (StringUtil.isBlank(line) || line.equals("null")) {
                line = "";
            }
            if (line.matches("[0-9]+") && line.length() > 6) {
                line = line + "\t";
            }
            sb.append("\"").append(line.replaceAll("\"", "\"\"")).append("\",");
        }
        bw.write(sb.deleteCharAt(sb.length() - 1).toString());
        bw.newLine();
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public List<Object> getTitle() {
        return title;
    }

    public void setTitle(List<Object> title) {
        this.title = title;
    }

    public List<Object> getRowN() {
        return rowN;
    }

    public void setRowN(List<Object> rowN) {
        this.rowN = rowN;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String nowdate = format.format(new Date());
        this.fileName = fileName + "_" + nowdate + ".csv";
    }

}
