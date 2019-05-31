package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.ConsultDto;
import com.d2c.member.query.ConsultSearcher;
import com.d2c.member.service.ConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/consult")
public class ConsultCtrl extends BaseCtrl<ConsultSearcher> {

    @Autowired
    private ConsultService consultService;

    @Override
    protected List<Map<String, Object>> getRow(ConsultSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ConsultDto> pager = consultService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ConsultDto consult : pager.getList()) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("创建日期", sdf.format(consult.getCreateDate()));
            cellsMap.put("咨询商品", consult.getProductName());
            cellsMap.put("用户昵称", consult.getNickName());
            cellsMap.put("咨询内容", consult.getQuestion());
            cellsMap.put("客服回复", consult.getReply());
            cellsMap.put("回复日期", consult.getReplyDate());
            cellsMap.put("当前状态", consult.getStatus().intValue() != 0
                    ? (consult.getStatus().intValue() == 1 ? "未回复" : "已回复") : "未审核");
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(ConsultSearcher searcher) {
        return consultService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "商品咨询表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"创建日期", "咨询商品", "用户昵称", "咨询内容", "客服回复", "回复日期", "当前状态"};
    }

    @Override
    protected Response doHelp(ConsultSearcher searcher, PageModel page) {
        return doList(searcher, page);
    }

    @Override
    protected Response doList(ConsultSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ConsultDto> pager = consultService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        Map<String, Object> count = consultService.countGroupByStatus();
        result.put("count", count);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        consultService.deleteByIds(ids);
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        consultService.deleteByIds(new Long[]{id});
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "Consult";
    }

    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public Response reply(String reply, Long[] ids) {
        consultService.doReply(ids, reply);
        return new SuccessResponse();
    }

    @RequestMapping(value = "/top/{recomend}", method = RequestMethod.POST)
    public Response updateTop(Long[] ids, @PathVariable Integer recomend) {
        consultService.updateRecommand(ids, recomend);
        return new SuccessResponse();
    }

    @RequestMapping(value = "/canceldelete", method = RequestMethod.POST)
    public Response cancelDelete(Long[] ids, ModelMap model) {
        consultService.cancelDelete(ids);
        return new SuccessResponse();
    }

}
