package com.d2c.common.core.excel;

import com.d2c.common.base.exception.FileException;
import com.d2c.common.base.resolver.QueryResolver;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.base.utils.FileUt;
import com.d2c.common.core.excel.font.FontFactory;
import com.d2c.common.core.excel.font.SimpleFont;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class BaseExcel<T> {

    private static final int EXCEL_MAX_COUNT = 50000; // 默认单行宽度
    private static final int DEFAULT_COLUMN_WIDTH = 20; // 默认单行宽度
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected SimpleFont font;
    protected WritableWorkbook wb;
    protected WritableSheet ws;// 数据句柄
    protected List<ExcelColumn> columnList;
    protected int columnWidth = DEFAULT_COLUMN_WIDTH;
    protected Iterator<T> it;
    protected int columnSize;
    protected int count;
    protected int r;// 行

    /**
     * 初始化
     */
    public List<File> excel(Iterable<T> list) {
        AssertUt.notNull(list);
        it = list.iterator();
        List<File> files = new ArrayList<>();
        while (it.hasNext()) {
            files.add(createExcel());
        }
//		downloadFile(files);
        return files;
    }

    private File createExcel() {
//		File file = FileUt.getFileNoExists(webHelper.getUpdatePath() + "/" + getFileName() + ".xls");
        File file = FileUt.getFileNoExists("/" + getFileName() + ".xls");
        OutputStream out = FileUt.openOutputStream(file);
        try {
            r = 1;
            count = 0;
            columnSize = 0;
            font = FontFactory.initSimple();
            wb = Workbook.createWorkbook(out);
            ws = wb.createSheet(getTitle(), 0);
            initHeader();
            while (it.hasNext() && count < EXCEL_MAX_COUNT) {
                count++;
                initLineData(it.next());
            }
            wb.write();
        } catch (Exception e) {
            throw new FileException("Excel表生成失败...", e);
        } finally {
            try {
                wb.close();
            } catch (Exception e) {
            }
            IOUtils.closeQuietly(out);
        }
        return file;
    }

    /**
     * 初始化表头数据
     */
    private void initHeader() throws Exception {
        // 初始化列，设置列宽
        columnList = initColumn();
        columnSize = columnList.size();
        for (int i = 0; i < columnList.size(); i++) {
            ws.setColumnView(i, columnWidth);
        }
        // 标题
        ws.setRowView(0, true);
        ws.mergeCells(0, getRow(), columnSize - 1, getRow());
        ws.addCell(new Label(0, getRow(), this.getTitle(), font.title_wcfF));
        nextLine();
        // 列表头
        if (columnList != null) {
            int i = 0;
            for (ExcelColumn column : columnList) {
                ws.addCell(new Label(i, getRow(), column.getTitle(), font.wr_wcfF));
                i++;
            }
            nextLine();
        }
        // 扩展表头
        buildHeader();
    }

    /**
     * 报表单行数据
     */
    private void initLineData(T bean) throws Exception {
        Object obj;
        String value;
        int i = 0;
        for (ExcelColumn column : columnList) {
            obj = QueryResolver.getValue(bean, column.getName());
            if (obj instanceof Date) {
                obj = DateUt.second2str((Date) obj);
            }
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
            ws.addCell(new Label(i, getRow(), value, font.wcfF));
            i++;
        }
        nextLine();
    }
    /**
     * 打包下载文件
     */
//	private void downloadFile(List<File> files) {
//		if(!files.isEmpty()){
//			File file = files.get(0);
//			if(files.size() == 1){
//				webHelper.downloadExcel(file);
//			}else{
//				String zipPath = webHelper.getUpdatePath() + "/" + getFileName() + ".zip";
//				File zip = FileUt.zip(zipPath, files.toArray(new File[]{}));
//				webHelper.downloadZip(zip);
//				FileUt.delete(files);
//			}
//		}
//	}

    /**
     * 扩展表头
     */
    protected void buildHeader() throws Exception {
    }

    /**
     * 列表结构初始化
     */
    protected abstract List<ExcelColumn> initColumn();

    /**
     * 设置标题
     */
    public abstract String getTitle();

    /**
     * 设置文件名称
     */
    public abstract String getFileName();

    protected int getRow() {
        return r;
    }

    protected void nextLine() {
        r++;
    }

}
