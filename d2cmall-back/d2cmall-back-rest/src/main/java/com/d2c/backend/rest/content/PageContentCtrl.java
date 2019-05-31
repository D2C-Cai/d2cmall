package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.FieldContent;
import com.d2c.content.model.FieldDefine;
import com.d2c.content.model.PageContent;
import com.d2c.content.query.FieldContentSearcher;
import com.d2c.content.service.FieldContentService;
import com.d2c.content.service.FieldDefineService;
import com.d2c.content.service.PageContentService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/modulehomepage")
public class PageContentCtrl extends BaseCtrl<BaseQuery> {

    @Autowired
    private PageContentService pageContentService;
    @Autowired
    private FieldContentService fieldContentService;
    @Autowired
    private FieldDefineService fieldDefineService;

    @Override
    protected List<Map<String, Object>> getRow(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(BaseQuery searcher) {
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
    protected Response doHelp(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(BaseQuery searcher, PageModel page) {
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

    /**
     * block列表
     *
     * @param pageId
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/edit/{pageId}", method = RequestMethod.POST)
    public Response editPageContent(@PathVariable Long pageId) {
        SuccessResponse result = new SuccessResponse();
        PageContent homePage = pageContentService.findOneByModule(pageId, 0);
        AssertUt.notNull(homePage, "模块为空，稍候再试");
        if (homePage != null) {
            List<FieldDefine> defs = fieldDefineService.findByPageDefId(pageId, 1);
            result.put("pageDefine", defs);
            result.put("modulePageId", homePage.getId());
        }
        return result;
    }

    /**
     * 编辑每个block的内容
     *
     * @param id
     * @param block
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/edit/{id}/{block}/{type}", method = RequestMethod.POST)
    public Response editBlock(@PathVariable Long id, @PathVariable String block, @PathVariable int type, PageModel page,
                              FieldContentSearcher searcher) {
        BeanUt.trimString(searcher);
        SuccessResponse result = new SuccessResponse();
        PageContent homePage = pageContentService.findOneByModule(id, 0);
        AssertUt.notNull(homePage, "模块为空，稍候再试");
        PageResult<FieldContent> blocks = fieldContentService.findByGroupAndPageId(block, homePage.getId(), page);
        if (type == 1) {
            result = new SuccessResponse(blocks);
        } else {
            Object blockContent = null;
            try {
                Class<?> clazz = PageContent.class;
                Field field = clazz.getDeclaredField(block);
                field.setAccessible(true);
                blockContent = field.get(homePage);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            result = new SuccessResponse(blockContent == null ? "" : blockContent.toString());
        }
        return result;
    }

    /**
     * 更新block模块
     *
     * @param id
     * @param blockValue
     * @param blockKey
     * @return
     */
    @RequestMapping(value = "/updateBlock", method = RequestMethod.POST)
    public Response updateBlock(Long modulePageId, String blockValue, String blockKey) throws Exception {
        SuccessResponse result = new SuccessResponse();
        PageContent homePage = pageContentService.findOneById(modulePageId);
        if (homePage != null) {
            BeanUtils.setProperty(homePage, blockKey, blockValue);
            BeanUt.setValue(homePage, blockKey, blockValue);
            pageContentService.update(homePage);
        }
        return result;
    }

    /**
     * 发布新版本
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST)
    public Response doPublish(@PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        PageContent modulePage = pageContentService.findOneByModule(id, 0);
        if (modulePage == null) {
            result.setStatus(-1);
            result.setMessage(" 不存在" + id);
            return result;
        }
        try {
            pageContentService.doPublish(modulePage);
            result.setMessage("发布成功！");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

}
