package com.d2c.util.file;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public class ExcelSizeReader {

    public JSONObject poiExcel2003(InputStream strPath, int sheetNum) throws Exception {
        JSONObject result = new JSONObject();
        HSSFWorkbook hwb = new HSSFWorkbook(strPath);
        HSSFSheet sheet = hwb.getSheetAt(sheetNum);
        HSSFRow row;
        JSONArray header = new JSONArray();
        JSONObject list = new JSONObject(true);
        for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            JSONObject each = new JSONObject(true);
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                if (i == 0) {
                    if (j > 0) {
                        header.add(row.getCell(j) == null ? "-" : row.getCell(j).toString());
                    }
                } else {
                    if (j > 0) {
                        each.put(header.getString(j - 1), row.getCell(j) == null ? "-" : row.getCell(j).toString());
                    }
                }
            }
            if (i > 0) {
                list.put(row.getCell(0).toString(), each);
            }
        }
        result.put("header", header);
        result.put("data", list);
        return result;
    }

    public JSONObject poiExcel2007(InputStream strPath, int sheetNum) throws Exception {
        JSONObject result = new JSONObject();
        XSSFWorkbook xwb = new XSSFWorkbook(strPath);
        XSSFSheet sheet = xwb.getSheetAt(sheetNum);
        XSSFRow row;
        JSONArray header = new JSONArray();
        JSONObject list = new JSONObject(true);
        for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            JSONObject each = new JSONObject(true);
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                if (i == 0) {
                    if (j > 0) {
                        header.add(row.getCell(j) == null ? "-" : row.getCell(j).toString());
                    }
                } else {
                    if (j > 0) {
                        each.put(header.getString(j - 1), row.getCell(j) == null ? "-" : row.getCell(j).toString());
                    }
                }
            }
            if (i > 0) {
                list.put(row.getCell(0).toString(), each);
            }
        }
        result.put("header", header);
        result.put("data", list);
        return result;
    }

}
