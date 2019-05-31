package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.WardrobeCategory;
import com.d2c.member.query.WardrobeCategorySearcher;
import com.d2c.member.query.WardrobeSearcher;
import com.d2c.member.service.WardrobeCategoryService;
import com.d2c.member.service.WardrobeService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 衣橱分类
 *
 * @author Lain
 */
@RestController
@RequestMapping("/rest/society/wardrobecategory")
public class WardrobeCategoryCtrl extends BaseCtrl<WardrobeCategorySearcher> {

    @Autowired
    private WardrobeCategoryService WardrobeCategoryService;
    @Autowired
    private WardrobeService wardrobeService;

    @Override
    protected Response doList(WardrobeCategorySearcher query, PageModel page) {
        PageResult<WardrobeCategory> pager = WardrobeCategoryService.findBySearcher(query, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        WardrobeCategory wardrobeCategory = JsonUtil.instance().toObject(data, WardrobeCategory.class);
        BeanUt.trimString(wardrobeCategory);
        if (WardrobeCategoryService.findByName(wardrobeCategory.getName()) != null) {
            throw new BusinessException("该分类已经存在！");
        }
        wardrobeCategory = WardrobeCategoryService.insert(wardrobeCategory);
        SuccessResponse response = new SuccessResponse();
        response.put("wardrobeCategory", wardrobeCategory);
        return response;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse response = new SuccessResponse();
        WardrobeCategory wardrobeCategory = WardrobeCategoryService.findById(id);
        response.put("wardrobeCategory", wardrobeCategory);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        WardrobeCategory wardrobeCategory = JsonUtil.instance().toObject(data, WardrobeCategory.class);
        BeanUt.trimString(wardrobeCategory);
        WardrobeCategoryService.update(wardrobeCategory);
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        WardrobeSearcher query = new WardrobeSearcher();
        query.setCategoryId(id);
        if (wardrobeService.countBySearcher(query) > 0) {
            throw new BusinessException("该分类已经产生衣橱数据，暂不可删除！");
        }
        WardrobeCategoryService.delete(id);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(WardrobeCategorySearcher query, PageModel page) {
        PageResult<WardrobeCategory> pager = WardrobeCategoryService.findBySearcher(query, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(WardrobeCategorySearcher query) {
        // TODO Auto-generated method stub
        return 0;
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
    protected List<Map<String, Object>> getRow(WardrobeCategorySearcher query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Response status(Integer status, Long[] ids) {
        for (Long id : ids) {
            WardrobeCategoryService.updateStatus(id, status);
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public Response sort(Integer sort, Long id) {
        WardrobeCategoryService.updateSort(id, sort);
        return new SuccessResponse();
    }

}
