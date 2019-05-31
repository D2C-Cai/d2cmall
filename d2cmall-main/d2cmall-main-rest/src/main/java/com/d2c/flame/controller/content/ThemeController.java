package com.d2c.flame.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.dto.ThemeTagDto;
import com.d2c.content.model.Theme.ThemeType;
import com.d2c.content.model.ThemeTag;
import com.d2c.content.query.ThemeTagSearcher;
import com.d2c.content.search.model.SearcherTheme;
import com.d2c.content.search.query.ThemeSearcherBean;
import com.d2c.content.search.service.ThemeSearcherService;
import com.d2c.content.service.ThemeTagService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/theme")
public class ThemeController extends BaseController {

    @Autowired
    private ThemeTagService themeTagService;
    @Reference
    private ThemeSearcherService themeSearcherService;
    @Autowired
    private PartnerService partnerService;

    /**
     * 标签列表
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.GET)
    public ResponseResult tagList(String type) {
        ResponseResult result = new ResponseResult();
        List<ThemeTag> list = new ArrayList<>();
        if (type == null) {
            type = ThemeType.THEME.name();
        }
        if (ThemeType.WECHAT.name().equals(type)) {
            PageResult<ThemeTag> pager = new PageResult<>();
            try {
                MemberInfo memberInfo = this.getLoginMemberInfo();
                if (memberInfo.getPartnerId() != null) {
                    Partner partner = partnerService.findById(memberInfo.getPartnerId());
                    if (partner != null && partner.getStatus() >= 0) {
                        ThemeTagSearcher searcher = new ThemeTagSearcher();
                        searcher.setType(ThemeType.WECHAT.name());
                        searcher.setStatus(1);
                        if (partner.getLevel() == 2) {
                            searcher.setFix(1);
                        }
                        pager = themeTagService.findBySearcher(searcher, new PageModel());
                        list = pager.getList();
                    }
                }
            } catch (NotLoginException e) {
            }
        } else {
            list = themeTagService.findAll(type);
        }
        JSONArray array = new JSONArray();
        list.forEach(item -> array.add(item.toJson()));
        result.put("themeTags", array);
        return result;
    }

    /**
     * 专题列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(ThemeSearcherBean searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        if (searcher.getType() == null) {
            searcher.setType(ThemeType.THEME.name());
        }
        if (ThemeType.WECHAT.name().equals(searcher.getType())) {
            try {
                MemberInfo memberInfo = this.getLoginMemberInfo();
                if (memberInfo.getPartnerId() != null) {
                    Partner partner = partnerService.findById(memberInfo.getPartnerId());
                    if (partner != null && partner.getStatus() >= 0) {
                        searcher.setType(ThemeType.WECHAT.name());
                        if (partner.getLevel() == 2) {
                            searcher.setFix(1);
                        }
                    }
                }
            } catch (NotLoginException e) {
            }
        }
        searcher.setStatus(1);
        PageResult<SearcherTheme> pager = themeSearcherService.search(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("themes", pager, array);
        return result;
    }

    /**
     * 专题标签的数量
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/tag/counts", method = RequestMethod.GET)
    public ResponseResult countTag(String type) {
        ResponseResult result = new ResponseResult();
        List<ThemeTagDto> list = new ArrayList<>();
        if (type == null) {
            type = ThemeType.THEME.name();
        }
        if (ThemeType.WECHAT.name().equals(type)) {
            try {
                MemberInfo memberInfo = this.getLoginMemberInfo();
                if (memberInfo.getPartnerId() != null) {
                    Partner partner = partnerService.findById(memberInfo.getPartnerId());
                    if (partner != null && partner.getStatus() >= 0) {
                        ThemeTagSearcher searcher = new ThemeTagSearcher();
                        searcher.setType(ThemeType.WECHAT.name());
                        searcher.setStatus(1);
                        if (partner.getLevel() == 2) {
                            searcher.setFix(1);
                        }
                        list = themeTagService.countGroupByTag(null, searcher);
                    }
                }
            } catch (NotLoginException e) {
            }
        } else {
            list = themeTagService.countGroupByTag(type, null);
        }
        JSONArray array = new JSONArray();
        Long totalCount = 0L;
        for (ThemeTagDto dto : list) {
            array.add(dto.toJson());
            totalCount += dto.getCount();
        }
        if (ThemeType.THEME.name().equals(type)) {
            ThemeTag tag = themeTagService.findFixedOne();
            JSONObject obj = tag.toJson();
            obj.put("count", totalCount);
            array.add(0, obj);
        }
        result.put("countTags", array);
        return result;
    }

    /**
     * 专题详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        SearcherTheme searcherTheme = themeSearcherService.findById(id);
        JSONObject json = searcherTheme.toJson();
        result.put("theme", json);
        return result;
    }

    /**
     * 根据标签查找专题列表
     *
     * @param tagId
     * @param page
     * @return
     */
    @RequestMapping(value = "/tag/{id}", method = RequestMethod.GET)
    public ResponseResult themeList(@PathVariable("id") Long tagId, PageModel page) {
        ResponseResult result = new ResponseResult();
        ThemeSearcherBean searcher = new ThemeSearcherBean();
        searcher.setTagId(tagId);
        searcher.setStatus(1);
        PageResult<SearcherTheme> pager = themeSearcherService.search(searcher, page);
        result.put("themes", pager);
        return result;
    }

}
