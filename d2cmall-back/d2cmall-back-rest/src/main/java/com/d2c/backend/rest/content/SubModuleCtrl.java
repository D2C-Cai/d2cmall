package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.core.cache.old.CacheCallback;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.content.dto.PageContentDto;
import com.d2c.content.dto.PageDefineDto;
import com.d2c.content.model.PageDefine.MODULE;
import com.d2c.content.model.PageDefine.TERMINAL;
import com.d2c.content.model.SubModule;
import com.d2c.content.query.PageContentSearcher;
import com.d2c.content.service.PageContentService;
import com.d2c.content.service.PageDefineService;
import com.d2c.content.service.SubModuleQueryService;
import com.d2c.content.service.SubModuleService;
import com.d2c.member.model.Admin;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/submodule")
public class SubModuleCtrl extends BaseCtrl<PageContentSearcher> {

    @Autowired
    private SubModuleService subModuleService;
    @Autowired
    private SubModuleQueryService subModuleQueryService;
    @Autowired
    private PageContentService pageContentService;
    @Autowired
    private PageDefineService pageDefineService;

    @Override
    protected List<Map<String, Object>> getRow(PageContentSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(PageContentSearcher searcher) {
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
    protected Response doHelp(PageContentSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(PageContentSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<SubModule> pager = subModuleQueryService.findBySearcher(searcher, page);
        List<SubModule> categorys = subModuleQueryService.findAllCategory(searcher.getParent());
        if (categorys != null && categorys.size() > 0) {
            List<SubModule> list = processCategorySort(categorys, pager.getList());
            pager.setList(list);
        }
        return new SuccessResponse(pager);
    }

    private List<SubModule> processCategorySort(List<SubModule> categorys, List<SubModule> list) {
        List<SubModule> all = new ArrayList<>();
        categorys.forEach(category -> {
            all.add(category);
            list.forEach(subModule -> {
                if (subModule.getCategoryId() != null
                        && category.getId().intValue() == subModule.getCategoryId().intValue()) {
                    all.add(subModule);
                }
            });
        });
        list.removeAll(all);
        all.addAll(list);
        return all;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        SubModule subModule = subModuleService.findById(id);
        result.put("subModule", subModule);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        result.setMessage("操作成功");
        try {
            int success = subModuleService.delete(id);
            if (success <= 0) {
                result.setStatus(-1);
                result.setMessage("操作不成功");
            }
        } catch (BusinessException e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SubModule subModule = JsonUtil.instance().toObject(data, SubModule.class);
        SuccessResponse result = new SuccessResponse();
        result.setMessage("操作成功");
        subModule = subModuleService.insert(subModule);
        if (subModule == null) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        result.put("subModule", subModule);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SubModule subModule = JsonUtil.instance().toObject(data, SubModule.class);
        SuccessResponse result = new SuccessResponse();
        result.setMessage("操作成功");
        int success = subModuleService.update(subModule);
        if (success <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
            return result;
        }
        subModuleService.updateCategoryId(id, subModule.getCategoryId());
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 店主中心首页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Response index() {
        SuccessResponse result = new SuccessResponse();
        PageDefineDto pageDefine = pageDefineService.findPageDefine(MODULE.CUSTOM, TERMINAL.PC, 1);
        if (pageDefine != null) {
            String key = CacheKey.MODULEHOMEPAGEKEY + "_" + pageDefine.getId();
            PageContentDto homePage = CacheTimerHandler.getAndSetCacheValue(key, 2,
                    new CacheCallback<PageContentDto>() {
                        Long pageDefineId = pageDefine.getId();

                        @Override
                        public PageContentDto doExecute() {
                            return pageContentService.findOneByModule(pageDefineId, 1);
                        }
                    });
            result.put("pageDefine", pageDefine);
            result.put("homePage", homePage);
        }
        return result;
    }

    @RequestMapping(value = "/default/{id}", method = RequestMethod.POST)
    public Response doDefault(@PathVariable Long id, int d) {
        SuccessResponse result = new SuccessResponse();
        int success = subModuleService.updateDefault(id, d);
        if (success <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    @RequestMapping(value = "/publish/{moduleId}", method = RequestMethod.POST)
    public Response publish(@PathVariable Long moduleId) {
        SuccessResponse result = new SuccessResponse();
        subModuleService.doPublish(moduleId);
        return result;
    }

    @RequestMapping(value = "/publish/section/{sectionId}", method = RequestMethod.POST)
    public Response publishSection(@PathVariable Long sectionId) {
        SuccessResponse result = new SuccessResponse();
        subModuleService.doPublishSection(sectionId);
        return result;
    }

    @RequestMapping(value = "/updateStatus/{moduleId}/{status}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long moduleId, @PathVariable Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = subModuleService.updateStatus(moduleId, status, admin.getUsername());
        if (success <= 0) {
            result.setStatus(-1);
            result.setMsg("操作不成功");
        }
        return result;
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public Response category(String parent) {
        SuccessResponse result = new SuccessResponse();
        List<SubModule> list = subModuleService.findCategory(parent);
        result.put("subModuleCategorys", list);
        return result;
    }

    @RequestMapping(value = "/move/category", method = RequestMethod.POST)
    public Response moveCategory(Long subModuleId, Long categoryId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        subModuleService.updateCategoryId(subModuleId, categoryId);
        return result;
    }

    @RequestMapping(value = "/onelevel/list", method = RequestMethod.GET)
    public Response oneLevelPage() {
        SuccessResponse result = new SuccessResponse();
        List<SubModule> subModuleList = subModuleQueryService.findByParent("HOME");
        result.put("subModuleCategorys", subModuleList);
        return result;
    }

}
