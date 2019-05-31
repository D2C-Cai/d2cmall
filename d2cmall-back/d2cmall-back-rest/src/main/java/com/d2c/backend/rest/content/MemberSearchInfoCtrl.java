package com.d2c.backend.rest.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.MemberSearchInfo;
import com.d2c.logger.query.MemberSearchInfoSearcher;
import com.d2c.logger.service.MemberSearchKeyService;
import com.d2c.member.search.query.MemberInfoSearchBean;
import com.d2c.member.search.service.SearchInfoSearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/membersearchinfo")
public class MemberSearchInfoCtrl extends BaseCtrl<MemberSearchInfoSearcher> {

    @Autowired
    private MemberSearchKeyService memberSearchKeyService;
    @Reference
    private SearchInfoSearcherService searchInfoSearcherService;

    @Override
    protected List<Map<String, Object>> getRow(MemberSearchInfoSearcher searcher, PageModel page) {
        List<MemberSearchInfo> list = memberSearchKeyService.findBySearcher(searcher, page).getList();
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (MemberSearchInfo memberSearchInfo : list) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("关键字", memberSearchInfo.getKeyword());
            cellsMap.put("会员名称", memberSearchInfo.getCreator());
            cellsMap.put("会员ip", memberSearchInfo.getIp());
            cellsMap.put("搜索时间", sdf.format(memberSearchInfo.getCreateDate()));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(MemberSearchInfoSearcher searcher) {
        return memberSearchKeyService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "搜索关键字明细表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"关键字", "会员名称", "会员ip", "搜索时间"};
    }

    @Override
    protected String getExportFileType() {
        return "MemberSearchInfo";
    }

    @Override
    protected Response doHelp(MemberSearchInfoSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(MemberSearchInfoSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MemberSearchInfo> pager = memberSearchKeyService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id_ : ids) {
            memberSearchKeyService.delete(id_);
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        memberSearchKeyService.delete(id);
        result.setMessage("删除成功！");
        return result;
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

    @RequestMapping(value = "/doSum", method = RequestMethod.GET)
    public Response doSum(MemberInfoSearchBean searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = searchInfoSearcherService.findGroupBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

}
