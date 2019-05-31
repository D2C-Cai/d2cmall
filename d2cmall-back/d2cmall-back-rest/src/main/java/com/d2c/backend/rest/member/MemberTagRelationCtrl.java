package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.MemberTagRelationDto;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberTag;
import com.d2c.member.model.MemberTagRelation;
import com.d2c.member.query.MemberTagRelationSearcher;
import com.d2c.member.query.MemberTagSearcher;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberTagRelationService;
import com.d2c.member.service.MemberTagService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/tag4memberrelation")
public class MemberTagRelationCtrl extends BaseCtrl<MemberTagRelationSearcher> {

    @Autowired
    private MemberTagRelationService memberTagRelationService;
    @Autowired
    private MemberTagService memberTagService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    protected Response doList(MemberTagRelationSearcher searcher, PageModel page) {
        PageResult<MemberTagRelationDto> pager = memberTagRelationService.findBySearch(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(MemberTagRelationSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(MemberTagRelationSearcher searcher, PageModel page) {
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
    protected Response doHelp(MemberTagRelationSearcher searcher, PageModel page) {
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
        MemberTagRelation relation = JsonUtil.instance().toObject(data, MemberTagRelation.class);
        BeanUt.trimString(relation);
        relation = memberTagRelationService.insert(relation);
        SuccessResponse response = new SuccessResponse();
        response.put("tag4MemberRelation", relation);
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse response = new SuccessResponse();
        memberTagRelationService.deleteByIds(new Long[]{id});
        return response;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse response = new SuccessResponse();
        memberTagRelationService.deleteByIds(ids);
        return response;
    }

    @RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
    public Response usedList(@PathVariable Long id) {
        MemberTagSearcher tSearcher = new MemberTagSearcher();
        tSearcher.setStatus(1);
        PageResult<MemberTag> pager = memberTagService.findBySearch(new PageModel(), tSearcher);
        SuccessResponse response = new SuccessResponse(pager);
        MemberTagRelationSearcher searcher = new MemberTagRelationSearcher();
        searcher.setMemberId(id);
        List<MemberTagRelation> usedTag = memberTagRelationService.findByMemberId(id);
        response.put("usedTags", usedTag);
        return response;
    }

    /**
     * 批量插入数据
     *
     * @param shareId
     * @param tagIds
     * @param model
     * @return
     */
    @RequestMapping(value = "/batch/insert", method = RequestMethod.POST)
    public Response insert(String memberId, Long[] tagIds) {
        String[] ids = memberId.split(",");
        Long[] memberIds = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i].trim())) {
                memberIds[i] = Long.valueOf(ids[i].trim());
            }
        }
        memberTagRelationService.batchInsert(memberIds, tagIds);
        return new SuccessResponse();
    }

    /**
     * 导入标签会员表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import", method = RequestMethod.POST)
    public Response importMemberRelation(HttpServletRequest request) {
        this.getLoginedAdmin();
        Map<String, MemberTag> tagMap = new HashMap<String, MemberTag>();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                MemberTagRelation bean = new MemberTagRelation();
                String loginCode = map.get("D2C账号") != null ? String.valueOf(map.get("D2C账号")) : null;
                String memberId = map.get("会员Id") != null ? (String) map.get("会员Id").toString() : null;
                String tagIdStr = (String) map.get("标签Id").toString();
                if ((StringUtils.isBlank(loginCode) && StringUtils.isBlank(memberId))
                        && StringUtils.isBlank(tagIdStr)) {
                    errorMsg.append("数据异常" + "，错误原因：D2C账号和会员Id不能全部为空，会员标签不能空<br/>");
                    return false;
                }
                MemberInfo memberInfo = null;
                if (StringUtils.isNotBlank(loginCode)) {
                    memberInfo = memberInfoService.findByLoginCode(loginCode);
                    if (memberInfo == null) {
                        errorMsg.append("D2C账号：" + loginCode + "，错误原因：会员未找到<br/>");
                        return false;
                    }
                } else {
                    memberInfo = memberInfoService.findById(Long.parseLong(memberId));
                    if (memberInfo == null) {
                        errorMsg.append("会员ID：" + memberId + "，错误原因：会员未找到<br/>");
                        return false;
                    }
                }
                Long tagId = Long.parseLong(tagIdStr);
                bean.setMemberId(memberInfo.getId());
                bean.setTagId(tagId);
                String key = "tagId_" + tagId;
                MemberTag tag = tagMap.get(key);
                if (tag == null) {
                    if (tagMap.containsKey(key)) {
                        errorMsg.append("会员手机：" + loginCode + "，错误原因：标签未找到<br/>");
                        return false;
                    }
                    MemberTag temp = memberTagService.findById(tagId);
                    tagMap.put("tagId_" + tagId, temp);
                    if (temp == null) {
                        errorMsg.append("会员手机：" + loginCode + "，错误原因：标签未找到<br/>");
                        return false;
                    }
                    tag = temp;
                }
                bean.setTagName(tag.getName());
                memberTagRelationService.doReplaceInto(bean);
                return true;
            }
        });
    }

}
