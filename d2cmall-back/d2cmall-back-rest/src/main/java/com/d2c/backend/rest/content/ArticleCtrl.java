package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.dto.ArticleDto;
import com.d2c.content.model.Article;
import com.d2c.content.query.ArticleSearcher;
import com.d2c.content.service.ArticleService;
import com.d2c.logger.model.Template;
import com.d2c.logger.model.Template.EnumTemplateType;
import com.d2c.logger.query.TemplateSearcher;
import com.d2c.logger.service.TemplateService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/article")
public class ArticleCtrl extends BaseCtrl<ArticleSearcher> {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TemplateService templateService;

    @Override
    protected List<Map<String, Object>> getRow(ArticleSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ArticleSearcher searcher) {
        return articleService.countBySearcher(searcher);
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
    protected Response doHelp(ArticleSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ArticleSearcher search, PageModel page) {
        BeanUt.trimString(search);
        PageResult<ArticleDto> pager = articleService.findDtoBySearcher(search, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("search", search);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        PageModel page = new PageModel();
        TemplateSearcher templateSearcher = new TemplateSearcher();
        templateSearcher.setTemplateType(EnumTemplateType.ARTICLE.name());
        PageResult<Template> pager = templateService.findBySearch(templateSearcher, page);
        result.put("articleTemplates", pager.getList());
        ArticleDto article = articleService.findDtoById(id);
        result.put("article", article);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        if (ids == null) {
            result.setMessage("文章为空，删除不成功");
            result.setStatus(-1);
            return result;
        }
        for (int i = 0; i < ids.length; i++) {
            Article article = articleService.findById(ids[i]);
            articleService.delete(ids[i], article.getName());
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        if (id == null) {
            result.setMessage("文章为空，删除不成功");
            result.setStatus(-1);
            return result;
        }
        Article article = articleService.findById(id);
        articleService.delete(id, article.getName());
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Article article = (Article) JsonUtil.instance().toObject(data, Article.class);
        if (article == null || article.getArticleTemplateId() == null) {
            result.setStatus(-1);
            return result;
        }
        String name = article.getName().trim();
        if (name.length() == 0) {
            result.setStatus(-1);
            result.setMessage("英文标题不能全部是空格！");
            return result;
        }
        article.setName(name);
        Article tempArticle = articleService.findOneByName(article.getName());
        if (tempArticle == null) {
            article = articleService.insert(article);
            result.put("article", article);
        } else {
            result.setStatus(-1);
            result.setMessage("英文标题不能重复！");
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Article article = (Article) JsonUtil.instance().toObject(data, Article.class);
        if (article == null || article.getArticleTemplateId() == null) {
            result.setMessage("文章为空，修改不成功");
            result.setStatus(-1);
            return result;
        }
        String name = article.getName().trim();
        if (name.length() == 0) {
            result.setStatus(-1);
            result.setMessage("英文标题不能全部是空格！");
            return result;
        }
        article.setName(name);
        Article tempArticle = articleService.findOneByName(article.getName());
        if (tempArticle == null || tempArticle.getId().equals(id)) {
            articleService.update(article);
        } else {
            result.setStatus(-1);
            result.setMessage("英文标题不能重复！");
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/checkname", method = RequestMethod.POST)
    public Response checkName(String name) {
        SuccessResponse result = new SuccessResponse();
        Article article = articleService.findOneByName(name);
        if (article != null) {
            result.setStatus(0);
        }
        return result;
    }

    @RequestMapping(value = "/publish/{publish}", method = RequestMethod.POST)
    public Response doPublish(@PathVariable Long publish, Long[] ids, boolean redirect) {
        SuccessResponse result = new SuccessResponse();
        for (int i = 0; i < ids.length; i++) {
            Article article = articleService.findById(ids[i]);
            article.setPublished(publish == 1 ? true : false);
            articleService.doPublish(article);
        }
        result.setMessage("操作成功！");
        return result;
    }

    @RequestMapping(value = "/mark/{mark}", method = RequestMethod.POST)
    public Response topOrRecommend(Long id, String type, @PathVariable Long mark) {
        Article article = articleService.findById(id);
        if (type.equals("doTop")) {
            articleService.doTop(mark == 1, id, article.getName());
        } else {
            articleService.doRecommend(mark == 1, id, article.getName());
        }
        return new SuccessResponse();
    }

}
