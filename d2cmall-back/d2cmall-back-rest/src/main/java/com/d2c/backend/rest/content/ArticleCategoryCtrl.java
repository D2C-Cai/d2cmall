package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.dto.ArticleCategoryHelpDto;
import com.d2c.content.model.ArticleCategory;
import com.d2c.content.query.ArticleCategorySearcher;
import com.d2c.content.service.ArticleCateService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/articlecategory")
public class ArticleCategoryCtrl extends BaseCtrl<ArticleCategorySearcher> {

    @Autowired
    private ArticleCateService articleCateService;

    @Override
    protected List<Map<String, Object>> getRow(ArticleCategorySearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ArticleCategorySearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
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
    protected Response doHelp(ArticleCategorySearcher searcher, PageModel page) {
        SuccessResponse reslut = new SuccessResponse();
        BeanUt.trimString(searcher);
        List<ArticleCategoryHelpDto> articleCategoryList = articleCateService.findAllForDto();
        reslut.put("articleCategoryList", articleCategoryList);
        return reslut;
    }

    @Override
    protected Response doList(ArticleCategorySearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<ArticleCategory> list = articleCateService.findAll();
        return new SuccessResponse(list);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ArticleCategory articleCategory = articleCateService.findById(id);
        result.put("articleCategory", articleCategory);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        articleCateService.delete(id);
        result.setMessage("文章分类删除成功");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        ArticleCategory articleCategory = (ArticleCategory) JsonUtil.instance().toObject(data, ArticleCategory.class);
        SuccessResponse result = new SuccessResponse();
        if (articleCategory == null) {
            result.setStatus(-1);
            return result;
        }
        articleCategory = articleCateService.insert(articleCategory);
        result.put("articleCategory", articleCategory);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        ArticleCategory articleCategory = (ArticleCategory) JsonUtil.instance().toObject(data, ArticleCategory.class);
        if (articleCategory == null) {
            result.setStatus(-1);
            return result;
        }
        articleCateService.update(articleCategory);
        result.setMessage("文章分类修改成功");
        return result;
    }

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public Response updateSort(Long[] id, Integer[] sort) {
        for (int i = 0; i < id.length; i++) {
            Long iid = id[i];
            if (iid != null && sort.length > i) {
                articleCateService.updateSort(iid, sort[i]);
            }
        }
        return new SuccessResponse();
    }

}
