package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.Article;
import com.d2c.content.service.ArticleService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.freemarker.*;
import com.d2c.logger.model.Template;
import com.d2c.logger.service.TemplateService;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private FreeMarkerConfig freemarkerConfig;
    @Autowired
    private LoginMember loginMember;
    @Autowired
    private RenderUserAgent renderUserAgent;
    @Autowired
    private RenderSearchHot renderSearchHot;
    @Autowired
    private RenderNavigation renderNavigation;
    @Autowired
    private RenderStaticSign renderStaticSign;
    @Autowired
    private RenderStaticTimeStamp renderStaticTimeStamp;

    /**
     * 新闻页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
    public String article(ModelMap model, @PathVariable Long id, HttpServletResponse response) {
        Article article = articleService.findById(id);
        if (article == null) {
            return "error/404";
        }
        if (isNormalDevice() || isTabletDevice()) {
            try {
                response.setContentType("text/html;charset=utf-8");
                if (StringUtils.isEmpty(article.getContent())) {
                    article.setContent(article.getMobileContent());
                }
                PrintWriter pw = response.getWriter();
                pw.write(renderArticle(article));
                pw.flush();
                pw.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            return null;
        } else {
            model.put("article", article);
            return "cms/article_page";
        }
    }

    /**
     * 文章页
     *
     * @param model
     * @param name
     * @return
     */
    @RequestMapping(value = "/page/{name}", method = RequestMethod.GET)
    public String article(ModelMap model, @PathVariable String name, HttpServletResponse response) {
        Article article = articleService.findOneByName(name);
        if (article == null) {
            return "error/404";
        }
        if (isNormalDevice() || isTabletDevice()) {
            try {
                response.setContentType("text/html;charset=utf-8");
                if (StringUtils.isEmpty(article.getContent())) {
                    article.setContent(article.getMobileContent());
                }
                String html = renderArticle(article);
                if (html == null) {
                    html = "";
                }
                response.setContentType("text/html;charset=utf-8");
                PrintWriter pw = response.getWriter();
                pw.write(html);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            return null;
        } else {
            model.put("article", article);
            return "cms/article_page";
        }
    }

    private String renderArticle(final Article article) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("article", article);
        if (article.getArticleTemplateId() != null) {
            Template articleTemplate = templateService.findById(article.getArticleTemplateId());
            parameterMap.put("templateContent", articleTemplate.getContent());
            try {
                String templateContent = parameterMap.get("templateContent").toString();
                StringWriter writer = new StringWriter();
                Configuration config = this.getFreemarkConfig();
                new freemarker.template.Template("", new StringReader(templateContent), config).process(parameterMap,
                        writer);
                return writer.toString();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    private Configuration getFreemarkConfig() {
        Configuration config = freemarkerConfig.getConfiguration();
        if (config.getSharedVariable("loginMember") == null) {
            config.setSharedVariable("loginMember", loginMember);
        }
        if (config.getSharedVariable("renderUserAgent") == null) {
            config.setSharedVariable("renderUserAgent", renderUserAgent);
        }
        if (config.getSharedVariable("renderSearchHot") == null) {
            config.setSharedVariable("renderSearchHot", renderSearchHot);
        }
        if (config.getSharedVariable("renderNavigation") == null) {
            config.setSharedVariable("renderNavigation", renderNavigation);
        }
        if (config.getSharedVariable("renderStaticSign") == null) {
            config.setSharedVariable("renderStaticSign", renderStaticSign);
        }
        if (config.getSharedVariable("renderStaticTimeStamp") == null) {
            config.setSharedVariable("renderStaticTimeStamp", renderStaticTimeStamp);
        }
        return config;
    }

    /**
     * 文章预览
     *
     * @param model
     * @param name
     * @return
     */
    @RequestMapping(value = "/page/preview/{name}", method = RequestMethod.GET)
    public String previewPage(ModelMap model, @PathVariable String name, HttpServletResponse response) {
        Article article = articleService.findOneByName(name);
        if (article == null) {
            return "error/404";
        }
        if (isNormalDevice() || isTabletDevice()) {
            try {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter pw = response.getWriter();
                pw.write(renderArticle(article));
                pw.flush();
                pw.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            return null;
        } else {
            model.put("article", article);
            return "cms/article_page";
        }
    }

    @RequestMapping(value = "/article/{categoryId}", method = RequestMethod.GET)
    public String findByCategoryId(ModelMap model, @PathVariable("categoryId") Long categoryId, PageModel page) {
        PageResult<Article> articleList = articleService.findByCategoryId(categoryId, page);
        model.put("articleList", articleList);
        return "";
    }

}
