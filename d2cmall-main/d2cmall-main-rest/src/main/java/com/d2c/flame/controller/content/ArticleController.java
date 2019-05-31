package com.d2c.flame.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.Article;
import com.d2c.content.service.ArticleService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherMemberArticle;
import com.d2c.member.search.service.MemberArticleSearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 文章
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Reference
    private MemberArticleSearcherService memberArticleSearcherService;

    /**
     * App的公告
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public ResponseResult notice(PageModel page) {
        ResponseResult result = new ResponseResult();
        page.setPageSize(10);
        PageResult<Article> pager = articleService.findByCategoryId(11L, page);
        List<Article> list = pager.getList();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            String[] memberArticleids = new String[list.size()];
            for (int i = 0; i < memberArticleids.length; i++) {
                memberArticleids[i] = memberInfo.getId() + "_" + list.get(i).getId();
            }
            Map<Long, SearcherMemberArticle> memberArticles = memberArticleSearcherService.findByIds(memberArticleids);
            for (Article item : list) {
                if (!memberArticles.containsKey(item.getId())) {
                    item = articleService.findOneByName(item.getName());
                    result.put("article", item.toJson());
                    break;
                }
            }
        } catch (NotLoginException e) {
            for (Article item : list) {
                item = articleService.findOneByName(item.getName());
                result.put("article", item.toJson());
                break;
            }
        }
        return result;
    }

    /**
     * 点击关闭公告
     *
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseResult cancel(Long articleId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SearcherMemberArticle searcherMemberArticle = new SearcherMemberArticle();
        searcherMemberArticle.setArticleId(articleId);
        searcherMemberArticle.setMemberId(memberInfo.getId());
        memberArticleSearcherService.insert(searcherMemberArticle);
        return result;
    }

    /**
     * 通过文章分类ID查询
     *
     * @param categoryId
     * @param page
     * @return
     */
    @RequestMapping(value = "/list/{categoryId}", method = RequestMethod.GET)
    public ResponseResult findByCategoryId(@PathVariable("categoryId") Long categoryId, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<Article> pager = articleService.findByCategoryId(categoryId, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("articleList", pager, array);
        return result;
    }

}
