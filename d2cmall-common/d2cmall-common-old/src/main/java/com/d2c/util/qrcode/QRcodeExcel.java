package com.d2c.util.qrcode;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class QRcodeExcel {

    private HSSFWorkbook wb;

    public int exportExcel(List<String> inernalSNs, List<BufferedImage> imgs, int rowNum) throws Exception {
        ByteArrayOutputStream byteArrayOut = null;
        CreationHelper helper = null;
        Drawing drawing = null;
        ClientAnchor anchor = null;
        Picture pict = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFSheet sheet = null;
        if (rowNum == 0) {
            wb = new HSSFWorkbook();
            sheet = wb.createSheet();
            helper = wb.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            sheet.setColumnWidth(0, 260 * 15);
            sheet.setColumnWidth(1, 735 * 15);
            row = sheet.createRow(rowNum);
            cell = row.createCell(0);
            cell.setCellValue("商品货号");
            cell = row.createCell(1);
            cell.setCellValue("门店二维码");
        } else {
            sheet = wb.getSheetAt(0);
            helper = wb.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
        }
        CellStyle cellStyle = wb.createCellStyle();
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
            pict = drawing.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
            pict.resize();
        }
        return rowNum;
    }

    public void download(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
        if (wb == null) {
            return;
        }
        String file = fileName + ".xls";
        String str = "attachment;filename=";
        String name = "";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        name = java.net.URLEncoder.encode(file, "UTF-8");
        response.addHeader("Content-Disposition", str + new String(name.getBytes("UTF-8"), "UTF-8"));
        try {
            wb.write(response.getOutputStream());
            response.getOutputStream().flush();
            // 遍历，开始下载
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().close();
        }
    }

}
