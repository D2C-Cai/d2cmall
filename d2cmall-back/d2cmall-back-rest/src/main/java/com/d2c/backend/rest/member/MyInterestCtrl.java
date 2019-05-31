package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.MemberAttentionDto;
import com.d2c.member.dto.MemberCollectionDto;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.InterestSearcher;
import com.d2c.member.service.MemberAttentionService;
import com.d2c.member.service.MemberCollectionService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.CSVUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/myinterest")
public class MyInterestCtrl extends BaseCtrl<InterestSearcher> {

    @Autowired
    private MemberAttentionService memberAttentionService;
    @Autowired
    private MemberCollectionService memberColletionService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    protected List<Map<String, Object>> getRow(InterestSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(InterestSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getFileName() {
        return "会员喜欢的商品表";
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(InterestSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(InterestSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/list/{type}/{source}", method = RequestMethod.POST)
    public Response interestList(InterestSearcher searcher, @PathVariable String type, @PathVariable String source,
                                 PageModel page) {
        BeanUt.trimString(searcher);
        if (StringUtils.isNoneBlank(searcher.getLoginCode())) {
            MemberInfo memberInfo = memberInfoService.findByLoginCode(searcher.getLoginCode());
            if (memberInfo != null) {
                searcher.setMemberId(memberInfo.getId());
            }
        }
        if (type.equals("follow") && source.equals("designer")) {
            // 表示喜欢的设计师
            PageResult<MemberAttentionDto> pager = memberAttentionService.findBySearch(searcher, page);
            SuccessResponse response = new SuccessResponse(pager);
            return response;
        }
        if (type.equals("like") && source.equals("product")) {
            // 现在只要是喜欢，就表示是商品
            PageResult<MemberCollectionDto> pager = memberColletionService.findBySearch(searcher, page);
            SuccessResponse response = new SuccessResponse(pager);
            return response;
        }
        return new ErrorResponse("没有数据");
    }

    /**
     * 分类导出excel
     */
    @RequestMapping(value = "/excel/{type}/{source}", method = {RequestMethod.POST, RequestMethod.GET})
    public Response excel(HttpServletRequest request, HttpServletResponse response, InterestSearcher searcher,
                          @PathVariable String type, @PathVariable String source) throws IOException, NotLoginException {
        this.getLoginedAdmin();
        try {
            SuccessResponse result = new SuccessResponse();
            result.setMsg("导出成功！");
            String fileName = this.getLoginedAdmin().getUsername() + "_" + getFileName();
            CSVUtil csvUtil = new CSVUtil();
            csvUtil.setFileName(fileName);
            String[] titleNames = null;
            String logType = null;
            List<Map<String, Object>> rowList = null;
            // 一次导出500条，如果有的话
            PageModel pageModel = new PageModel();
            pageModel.setPageSize(500);
            int pagerNumber = 1;
            if (type.equals("follow") && source.equals("designer")) {
                titleNames = getTitleNames();
                fileName = this.getLoginedAdmin().getUsername() + "_" + "用户关注的设计师表";
                logType = "MyDesigner";
                rowList = getMemberAttentionRowList(searcher, pageModel);
            } else if (type.equals("like") && source.equals("product")) {
                fileName = this.getLoginedAdmin().getUsername() + "_" + "喜欢的商品表";
                titleNames = getTitleNames2();
                logType = "MyProduct";
                rowList = getMemberCollectionRowList(searcher, pageModel);
            } else {
                result.setStatus(-1);
                result.setMessage("没有相关信息，Type:" + type + ",Source:" + source);
                return result;
            }
            csvUtil.writeTitleToFile(titleNames);
            PageResult<Object> pageResult = new PageResult<Object>(pageModel);
            int totalCount = count(searcher);
            pageResult.setTotalCount(totalCount);
            boolean exportSuccess = true;
            // 在服务器端生产excel文件
            do {
                pageModel.setPageNumber(pagerNumber);
                exportSuccess = csvUtil.writeRowToFile(rowList);
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() && exportSuccess);
            // 返回文件地址
            createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
            // 保存导出记录
            if (exportSuccess) {
                saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), logType);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

    private List<Map<String, Object>> getMemberAttentionRowList(InterestSearcher searcher, PageModel page) {
        PageResult<MemberAttentionDto> pager = memberAttentionService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        for (MemberAttentionDto myInterest : pager.getList()) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("品牌名称", myInterest.getDesignerName());
            cellsMap.put("会员信息", myInterest.getNickName());
            cellsMap.put("联系方式", myInterest.getMember() == null ? "" : myInterest.getMember().getLoginCode());
            cellsMap.put("喜欢时间", DateUtil.second2str(myInterest.getCreateDate()));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    private List<Map<String, Object>> getMemberCollectionRowList(InterestSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MemberCollectionDto> pager = memberColletionService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        for (MemberCollectionDto myInterest : pager.getList()) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("作品名称", myInterest.getProductName());
            cellsMap.put("货号", myInterest.getProductInernalSN());
            cellsMap.put("设计师", myInterest.getDesigners());
            cellsMap.put("会员信息", myInterest.getMember() == null ? "" : myInterest.getMember().getLoginCode());
            cellsMap.put("联系方式", myInterest.getMember() == null ? "" : myInterest.getMember().getLoginCode());
            cellsMap.put("喜欢时间", DateUtil.second2str(myInterest.getCreateDate()));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    private String[] getTitleNames() {
        return new String[]{"品牌名称", "会员信息", "联系方式", "喜欢时间"};
    }

    private String[] getTitleNames2() {
        return new String[]{"作品名称", "货号", "设计师", "会员信息", "联系方式", "喜欢时间"};
    }

}
