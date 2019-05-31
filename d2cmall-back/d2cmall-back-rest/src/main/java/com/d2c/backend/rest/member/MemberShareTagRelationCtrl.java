package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.MemberShare;
import com.d2c.member.model.MemberShareTagRelation;
import com.d2c.member.query.MemberShareTagRelationSearcher;
import com.d2c.member.service.MemberShareService;
import com.d2c.member.service.MemberShareTagRelationService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/tag4membersharerelation")
public class MemberShareTagRelationCtrl extends BaseCtrl<MemberShareTagRelationSearcher> {

    @Autowired
    private MemberShareTagRelationService memberShareTagRelationService;
    @Autowired
    private MemberShareService memberShareService;

    @Override
    protected List<Map<String, Object>> getRow(MemberShareTagRelationSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(MemberShareTagRelationSearcher searcher) {
        return 0;
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        return null;
    }

    @Override
    protected Response doHelp(MemberShareTagRelationSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected Response doList(MemberShareTagRelationSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected Response findById(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        MemberShareTagRelation relation = JsonUtil.instance().toObject(data, MemberShareTagRelation.class);
        BeanUt.trimString(relation);
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        return null;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/batch/insert", method = RequestMethod.POST)
    public Response insert(String shareId, Long[] tagIds, ModelMap model) {
        String[] ids = shareId.split(",");
        Long[] shareIds = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i].trim())) {
                shareIds[i] = Long.valueOf(ids[i].trim());
            }
        }
        for (Long id : shareIds) {
            memberShareTagRelationService.insert(id, tagIds);
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/batch/delete/{tagId}", method = RequestMethod.POST)
    public Response delete(@PathVariable Long tagId, Long[] shareIds) {
        for (Long shareId : shareIds) {
            if (tagId == null) {
                continue;
            }
            memberShareTagRelationService.deleteByTagIdAndMemberShareId(tagId, shareId);
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/updateSort/{tagId}/{shareId}/{sort}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Long shareId, @PathVariable Long tagId, @PathVariable Integer sort) {
        memberShareTagRelationService.updateSort(shareId, tagId, sort);
        return new SuccessResponse();
    }

    @RequestMapping(value = "/list/{tagId}", method = RequestMethod.GET)
    public Response findMemberSharesByTagId(@PathVariable Long tagId, PageModel page) {
        PageResult<MemberShare> pager = memberShareService.findByTagId(tagId, page);
        SuccessResponse response = new SuccessResponse(pager);
        response.put("tagId", tagId);
        return response;
    }

}
