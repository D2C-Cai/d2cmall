package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.content.dto.VoteSelectionDto;
import com.d2c.content.query.VoteItemSearcher;
import com.d2c.content.service.VoteItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/voteitem")
public class VoteItemCtrl extends BaseCtrl<VoteItemSearcher> {

    @Autowired
    private VoteItemService voteItemService;

    @Override
    protected Response doList(VoteItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(VoteItemSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(VoteItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(VoteItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/rank/{defId}", method = RequestMethod.POST)
    public Response rankGroupBySelection(@PathVariable Long defId, VoteItemSearcher searcher, PageModel page) {
        searcher.setDefId(defId);
        if (StringUtils.isBlank(searcher.getSort())) {
            searcher.setSort("count");
        }
        PageResult<VoteSelectionDto> pager = voteItemService.findByDefIdGroupBySelection(searcher, page);
        if (pager.getTotalCount() > 0) {
            List<VoteSelectionDto> list = pager.getList();
            VoteSelectionDto before = pager.getList().get(0);
            before.setRank(1);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getvCount().intValue() == before.getvCount().intValue()) {
                    list.get(i).setRank(before.getRank());
                } else {
                    list.get(i).setRank(i + 1);
                }
            }
            pager.setList(list);
        }
        SuccessResponse result = new SuccessResponse(pager);
        int totalCount = voteItemService.countBySearcher(searcher);
        result.put("totalCount", totalCount);
        return result;
    }

}
