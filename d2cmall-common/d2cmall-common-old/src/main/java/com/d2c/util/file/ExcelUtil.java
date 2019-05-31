package com.d2c.util.file;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    private static final Log logger = LogFactory.getLog(ExcelUtil.class);
    private static final int MAX_ROW_NUMBER = 10000;// 单个excel最大数据行数
    private HSSFWorkbook exportHSSFWorkbook = null;
    private HSSFSheet sheet = null;
    private ExportFileBean exportFileBean = null;

    public ExcelUtil() {
    }

    public ExcelUtil(String fileName, String userName) {
        exportFileBean = new ExportFileBean(fileName, userName);
    }

    public static String getString(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }

    public static Integer getInteger(Object obj) {
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (Exception e) {
            return null;
        }
    }

    public static Double getDouble(Object obj) {
        try {
            return Double.parseDouble(String.valueOf(obj));
        } catch (Exception e) {
            return null;
        }
    }

    public static BigDecimal getBigDecimal(Object obj) {
        try {
            return new BigDecimal(String.valueOf(obj));
        } catch (Exception e) {
            return null;
        }
    }

    public ExportFileBean getExportFileBean() {
        return exportFileBean;
    }

    public void setExportFileBean(ExportFileBean exportFileBean) {
        this.exportFileBean = exportFileBean;
    }

    /**
     * 建立表模型
     *
     * @param titleNames
     * @param rowList
     */
    public void createExcel(String[] titleNames, List<Map<String, Object>> rowList) {
        try {
            if (exportHSSFWorkbook == null) {
                exportHSSFWorkbook = new HSSFWorkbook();
            }
            if (sheet == null || sheet.getLastRowNum() >= MAX_ROW_NUMBER) {
                String sheetName = exportFileBean.getSheetName();
                if (sheetName.length() > 25) {
                    sheetName = sheetName.substring(0, 25);
                }
                sheet = exportHSSFWorkbook.createSheet(
                        exportFileBean.getSheetName() + "_" + (exportHSSFWorkbook.getNumberOfSheets() + 1));
                HSSFRow row = null;
                HSSFCell cell = null;
                row = sheet.createRow(0);
                for (int i = 0; i < titleNames.length; i++) {
                    cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                    cell.setCellValue(titleNames[i]);
                    sheet.autoSizeColumn(i);
                }
            }
            insertHSSFWorkbook(sheet, titleNames, rowList);
            writeFile();
        } catch (Exception e) {
            exportFileBean.getErrorMsg().append(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    private void insertHSSFWorkbook(HSSFSheet sheet, String[] titleNames, List<Map<String, Object>> rowList) {
        ClientAnchor anchor = null;
        Picture pict = null;
        CreationHelper helper = null;
        Drawing drawing = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFCellStyle cellStyle = exportHSSFWorkbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        int rows = sheet.getLastRowNum();
        for (int i = 0; i < rowList.size(); i++) {// 放入每列具体内容
            row = sheet.createRow(rows + i + 1);
            Map<String, Object> cellsMap = rowList.get(i);
            for (int j = 0; j < titleNames.length; j++) {
                cell = row.createCell(j);
                String title = titleNames[j];
                Object value = cellsMap.get(title);
                if (value != null) {
                    if (title.equals("图片")) {
                        if (helper == null) {
                            helper = exportHSSFWorkbook.getCreationHelper();
                        }
                        if (drawing == null) {
                            drawing = sheet.createDrawingPatriarch();
                        }
                        sheet.setColumnWidth(j, 20 * 256);
                        row.createCell(j);
                        row.setHeightInPoints(160);
                        anchor = helper.createClientAnchor();
                        anchor.setCol1(j);
                        anchor.setRow1(rows + i + 1);
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        try {
                            ImageIO.write((BufferedImage) value, "PNG", byteArrayOut);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pict = drawing.createPicture(anchor,
                                exportHSSFWorkbook.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
                        pict.resize();
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(String.valueOf(value));
                    }
                }
            }
        }
    }

    private void writeFile() throws IOException, FileNotFoundException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(exportFileBean.getOutPath());
            os.flush();
            exportHSSFWorkbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            os.close();
        }
        FileChannel fc = null;
        FileInputStream fis = null;
        try {
            File f = new File(exportFileBean.getOutPath());
            if (f.exists() && f.isFile()) {
                fis = new FileInputStream(f);
                fc = fis.getChannel();
                exportFileBean.setFileSize(fc.size());
            }
        } finally {
            fis.close();
            fc.close();
        }
    }

    /**
     * 获取表内容
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getExcelData(InputStream inputStream) throws Exception {
        List<Map<String, Object>> excelData = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);// 读第一张表
        List<String> titles = this.getTitles(sheet, 0);
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Map<String, Object> map = this.getContent(sheet, rowIndex, titles);
            if (!map.isEmpty()) {
                excelData.add(map);
            }
        }
        inputStream.close();
        return excelData;
    }

    /**
     * 获取表内容
     *
     * @param inputStream
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getExcelData(InputStream inputStream, PageModel page) throws Exception {
        List<Map<String, Object>> excelData = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);// 读第一张表
        List<String> titles = this.getTitles(sheet, 0);
        for (int rowIndex = page.getStartNumber() + 1; rowIndex <= page.getStartNumber()
                + page.getPageSize(); rowIndex++) {
            Map<String, Object> map = this.getContent(sheet, rowIndex, titles);
            if (!map.isEmpty()) {
                excelData.add(map);
            }
        }
        inputStream.close();
        return excelData;
    }

    private List<String> getTitles(Sheet sheet, int rowIndex) throws Exception {
        List<Object> list = this.readRow(sheet, rowIndex, sheet.getRow(rowIndex).getLastCellNum() + 1);
        if (list.size() == 0) {
            throw new BusinessException("标题列不存在，导入不成功！");
        }
        List<String> titles = new ArrayList<>();
        Object obj = null;
        for (int i = 0; i < list.size(); i++) {
            obj = list.get(i);
            if (obj == null) {
                continue;
            }
            String str = String.valueOf(obj).trim();
            if (StringUtils.isNotBlank(str)) {
                titles.add(str);
            }
        }
        return titles;
    }

    private Map<String, Object> getContent(Sheet sheet, int rowIndex, List<String> titles) {
        Map<String, Object> rowMap = new HashMap<>();
        List<Object> rowList = this.readRow(sheet, rowIndex, titles.size());
        if (rowList.size() == 0) {
            return rowMap;
        }
        for (int i = 0; i < rowList.size(); i++) {
            rowMap.put(titles.get(i), rowList.get(i));
        }
        return rowMap;
    }

    private List<Object> readRow(Sheet sheet, int rowIndex, int rowsize) {
        List<Object> result = new ArrayList<>();
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return result;
        }
        Cell cell = null;
        for (int columnIndex = 0; columnIndex < rowsize; columnIndex++) {
            cell = row.getCell(columnIndex);
            if (cell == null) {
                result.add(cell);
                continue;
            }
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    result.add(cell.getStringCellValue().trim());
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                        String date1 = dff.format(date);
                        result.add(date1);
                    } else {
                        result.add(cell.getNumericCellValue());
                    }
                    break;
                default:
                    result.add(cell.getStringCellValue());
            }
        }
        return result;
    }

    /**
     * 导出二维码
     *
     * @param inernalSNs
     * @param imgs
     * @param rowNum
     */
    public void exportQRcodeExcel(List<String> inernalSNs, List<BufferedImage> imgs, int rowNum) {
        try {
            ByteArrayOutputStream byteArrayOut = null;
            CreationHelper helper = null;
            Drawing drawing = null;
            ClientAnchor anchor = null;
            Picture pict = null;
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFSheet sheet = null;
            if (rowNum == 0) {
                exportHSSFWorkbook = new HSSFWorkbook();
                sheet = exportHSSFWorkbook.createSheet();
                helper = exportHSSFWorkbook.getCreationHelper();
                drawing = sheet.createDrawingPatriarch();
                sheet.setColumnWidth(0, 260 * 15);
                sheet.setColumnWidth(1, 735 * 15);
                row = sheet.createRow(rowNum);
                cell = row.createCell(0);
                cell.setCellValue("商品货号");
                cell = row.createCell(1);
                cell.setCellValue("门店二维码");
            } else {
                sheet = exportHSSFWorkbook.getSheetAt(0);
                helper = exportHSSFWorkbook.getCreationHelper();
                drawing = sheet.createDrawingPatriarch();
            }
            CellStyle cellStyle = exportHSSFWorkbook.createCellStyle();
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            String inernalSN = null;
            BufferedImage img = null;
            for (int i = 0; i < inernalSNs.size(); i++) {// 放入每列具体内容
                rowNum++;
                row = sheet.createRow(rowNum);
                inernalSN = inernalSNs.get(i);
                img = imgs.get(i);
                if (StringUtils.isBlank(inernalSN) || img == null) {
                    continue;
                }
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(inernalSN);
                row.createCell(1);
                row.setHeightInPoints(228);
                anchor = helper.createClientAnchor();
                anchor.setRow1(rowNum);
                anchor.setCol1(1);
                byteArrayOut = new ByteArrayOutputStream();
                ImageIO.write(img, "PNG", byteArrayOut);
                pict = drawing.createPicture(anchor,
                        exportHSSFWorkbook.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
                pict.resize();
            }
            writeFile();
        } catch (Exception e) {
            exportFileBean.getErrorMsg().append(e.getMessage());
            e.printStackTrace();
        }
    }

}
