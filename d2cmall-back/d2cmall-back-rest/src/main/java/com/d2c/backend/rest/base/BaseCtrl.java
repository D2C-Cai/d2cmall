package com.d2c.backend.rest.base;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.convert.ConvertHelper;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.SuperQuery;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.AssertException;
import com.d2c.logger.model.ExportLog;
import com.d2c.logger.service.ExportLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.util.file.CSVUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public abstract class BaseCtrl<Query extends SuperQuery> extends SuperCtrl {

    @Autowired
    private ExportLogService exportLogService;

    /**
     * 大列表查询
     */
    @RequestMapping(value = {"/list", "/search"}, method = RequestMethod.POST)
    public Response list(Query query, PageModel pager) {
        Response result = doList(query, pager);
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getIsAccountLocked()) {
            SuccessResponse resp = (SuccessResponse) result;
            ConvertHelper.convertList(resp.getList());
        }
        return result;
    }

    protected abstract Response doList(Query query, PageModel pager);

    /**
     * 新增
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insert() {
        JSONObject data = this.getJSONObject();
        Admin admin = this.getLoginedAdmin();
        data.put("creator", admin.getUsername());
        Response resp = doInsert(data);
        if (resp.getStatus() > 0 && StringUtils.isEmpty(resp.getMessage())) {
            resp.setMessage("新增成功！");
        }
        return resp;
    }

    protected abstract Response doInsert(JSONObject data);

    /**
     * 查看
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response findOne(@PathVariable Long id) {
        return findById(id);
    }

    protected abstract Response findById(Long id);

    /**
     * 更新
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public Response update(@PathVariable Long id) {
        JSONObject data = this.getJSONObject();
        Admin admin = this.getLoginedAdmin();
        data.put("lastModifyMan", admin.getUsername());
        data.put("modifier", admin.getUsername());
        Response resp = doUpdate(id, data);
        if (resp.getStatus() > 0 && StringUtils.isEmpty(resp.getMessage())) {
            resp.setMessage("修改成功！");
        }
        return resp;
    }

    protected abstract Response doUpdate(Long id, JSONObject data);

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Response delete(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new AssertException("没有任何行被选中，无法进行删除操作！");
        }
        Response result = doDelete(id);
        if (result.getStatus() > 0 && StringUtils.isEmpty(result.getMessage())) {
            result.setMessage("删除成功!");
        }
        return result;
    }

    protected abstract Response doDelete(Long id);

    /**
     * 批量删除
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public Response batchDelete(Long[] id) {
        if (id == null || id.length <= 0) {
            throw new AssertException("没有任何行被选中，无法进行删除操作！");
        }
        return doBatchDelete(id);
    }

    protected abstract Response doBatchDelete(Long[] ids);

    /**
     * 小列表查询
     */
    @RequestMapping(value = "/help", method = RequestMethod.POST)
    public Response help(Query query, PageModel pager) {
        return doHelp(query, pager);
    }

    protected abstract Response doHelp(Query query, PageModel pager);

    /**
     * 总数count
     */
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public Response countList(Query query) {
        int count = this.count(query);
        return ResultHandler.successData("count", count);
    }

    protected abstract int count(Query query);

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Response export(Query query, PageModel pager) {
        SuccessResponse result = new SuccessResponse();
        result.setMsg("导出成功！");
        String fileName = this.getLoginedAdmin().getUsername() + "_" + getFileName();
        String[] titleNames = getExportTitles();
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(fileName);
        csvUtil.writeTitleToFile(titleNames);
        // 一次导出1000条，如果有的话
        pager.setPageSize(1000);
        PageResult<Object> pageResult = new PageResult<>(pager);
        int pagerNumber = 1;
        int totalCount = count(query);
        if (totalCount >= 50000) {
            result.setStatus(-1);
            result.setMessage("导出数据超过5万条，无法导出！");
            return result;
        }
        pageResult.setTotalCount(totalCount);
        boolean exportSuccess = true;
        // 在服务器端生产excel文件
        do {
            pager.setPageNumber(pagerNumber);
            exportSuccess = csvUtil.writeRowToFile(getRow(query, pager));
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pageResult.getPageCount() && exportSuccess);
        // 返回文件地址
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        // 保存导出记录
        if (exportSuccess) {
            saveExportLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize());
        }
        return result;
    }

    /**
     * 文件名
     */
    protected abstract String getFileName();

    /**
     * 标题行
     */
    protected abstract String[] getExportTitles();

    /**
     * 每行处理
     */
    protected abstract List<Map<String, Object>> getRow(Query query, PageModel page);

    /**
     * 文件类型
     */
    protected abstract String getExportFileType();

    /**
     * 导出日志
     */
    protected void saveExportLog(String fileName, String filePath, long fileSize) {
        String type = this.getExportFileType();
        this.saveLog(fileName, filePath, fileSize, type);
    }

    /**
     * 保存日志
     */
    protected void saveLog(String fileName, String filePath, long fileSize, String type) {
        ExportLog exportLog = new ExportLog();
        exportLog.setFileName(fileName);
        exportLog.setFilePath(filePath);
        exportLog.setFileSize(fileSize);
        exportLog.setLogType(type);
        exportLog.setCreator(this.getLoginedAdmin().getUsername());
        exportLogService.insert(exportLog);
    }

    /**
     * 导入excel 类型1
     */
    protected SuccessResponse processImportExcel(HttpServletRequest request, EachRow each) {
        SuccessResponse result = new SuccessResponse();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        Integer row = 1;
        Integer failCount = 0;
        StringBuilder errorMsg = new StringBuilder();
        for (Map<String, Object> map : excelData) {
            boolean success = each.process(map, row, errorMsg);
            row = row + 1;
            if (!success) {
                failCount++;
                continue;
            }
        }
        String message = "导入成功" + (excelData.size() - failCount) + "条！";
        if (failCount > 0) {
            message += "导入不成功：" + failCount + "条！不成功原因：" + "<br/>";
            message += errorMsg.toString();
        }
        result.setMessage(message);
        return result;
    }

    /**
     * 导入excel 类型2
     *
     * @param request
     * @param each
     * @return
     * @throws Exception
     */
    protected SuccessResponse processImportExcel(List<? extends Object> beans, EachBean each) {
        SuccessResponse result = new SuccessResponse();
        Integer row = 1;
        Integer failCount = 0;
        StringBuilder errorMsg = new StringBuilder();
        for (Object object : beans) {
            boolean success = each.process(object, row, errorMsg);
            row = row + 1;
            if (!success) {
                failCount++;
                continue;
            }
        }
        String message = "导入成功" + (beans.size() - failCount) + "条！";
        if (failCount > 0) {
            message += "导入不成功：" + failCount + "条！不成功原因：" + "<br/>";
            message += errorMsg.toString();
        }
        result.setMessage(message);
        return result;
    }

    public interface EachRow {

        boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg);

    }

    public interface EachBean {

        boolean process(Object object, Integer row, StringBuilder errorMsg);

    }

}