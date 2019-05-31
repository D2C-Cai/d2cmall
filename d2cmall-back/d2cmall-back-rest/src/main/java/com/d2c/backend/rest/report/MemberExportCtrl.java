package com.d2c.backend.rest.report;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.logger.model.ExportLog;
import com.d2c.logger.service.ExportLogService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.service.MemberInfoService;
import com.d2c.util.file.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/report/memberexport")
public class MemberExportCtrl extends SuperCtrl {

    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ExportLogService exportLogService;

    /**
     * 会员表导出
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/excel/export", method = RequestMethod.POST)
    public Response export(MemberSearcher memberSearcher, PageModel page, HttpServletRequest request,
                           HttpServletResponse response, Boolean isMergeAdd) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        // 防止量太多
        if (!checkDate(result, memberSearcher.getStartDate(), memberSearcher.getEndDate())) {
            return result;
        }
        String fileName = this.getLoginedAdmin().getUsername() + "_会员表";
        String[] titleNames = getTitleNames();
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(fileName);
        csvUtil.writeTitleToFile(titleNames);
        PageResult<MemberInfo> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        int totalCount = memberInfoService.countBySearch(memberSearcher);
        pager.setTotalCount(totalCount);
        boolean exportSuccess = true;
        do {
            page.setPageNumber(pagerNumber++);
            pager = memberInfoService.findBySearch(memberSearcher, page);
            exportSuccess = csvUtil.writeRowToFile(getRowList(pager.getList()));
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        if (exportSuccess) {
            saveExportLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), "Member");
        }
        return result;
    }

    private List<Map<String, Object>> getRowList(List<MemberInfo> list) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (MemberInfo memberInfo : list) {
            cellsMap = new HashMap<>();
            cellsMap.put("注册时间", sdf.format(memberInfo.getCreateDate()));
            cellsMap.put("账号", memberInfo.getLoginCode());
            cellsMap.put("会员昵称", memberInfo.getDisplayName());
            cellsMap.put("真实姓名", memberInfo.getLoginCode());
            cellsMap.put("性别", memberInfo.getSex());
            cellsMap.put("生日", memberInfo.getBirthday() == null ? "" : sdf.format(memberInfo.getBirthday()));
            cellsMap.put("手机", memberInfo.getMobile());
            cellsMap.put("邮箱", memberInfo.getEmail());
            cellsMap.put("购买次数", "0");
            cellsMap.put("购买金额", "0");
            cellsMap.put("平均客单价", "0");
            cellsMap.put("最后登录时间", "0");
            cellsMap.put("最后购买时间", "0");
            rowList.add(cellsMap);
        }
        return rowList;
    }

    private String[] getTitleNames() {
        return new String[]{"账号", "真实姓名", "会员昵称", "性别", "手机", "邮箱", "生日", "购买次数", "购买金额", "平均客单价", "注册时间", "最后登录时间",
                "最后购买时间", "登入次数"};
    }

    protected void saveExportLog(String fileName, String filePath, long fileSize, String logType)
            throws NotLoginException {
        ExportLog exportLog = new ExportLog();
        exportLog.setFileName(fileName);
        exportLog.setFilePath(filePath);
        exportLog.setFileSize(fileSize);
        exportLog.setLogType(logType);
        exportLog.setCreator(this.getLoginedAdmin().getUsername());
        exportLogService.insert(exportLog);
    }

}
