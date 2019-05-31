package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.dao.ArticleMapper;
import com.d2c.content.dto.ArticleDto;
import com.d2c.content.model.Article;
import com.d2c.content.query.ArticleSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service("articleService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ArticleServiceImpl extends ListServiceImpl<Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article findById(Long articleId) {
        return articleMapper.findById(articleId);
    }

    @Override
    @Cacheable(value = "article", key = "'article_'+#name", unless = "#result == null")
    public Article findOneByName(String name) {
        return articleMapper.findOneByName(name);
    }

    @Override
    public PageResult<Article> findByCategoryId(Long catalogId, PageModel page) {
        PageResult<Article> pager = new PageResult<>(page);
        int totalCount = articleMapper.countByCategoryId(catalogId);
        if (totalCount > 0) {
            List<Article> articleList = articleMapper.findByCategoryId(catalogId, page);
            pager.setList(articleList);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<Article> findBySearcher(ArticleSearcher search, PageModel page) {
        PageResult<Article> pager = new PageResult<>(page);
        int totalCount = articleMapper.countBySearcher(search);
        List<Article> list = new ArrayList<>();
        if (totalCount > 0) {
            list = articleMapper.findBySearcher(search, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearcher(ArticleSearcher searcher) {
        return articleMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Article insert(Article article) {
        if (article == null) {
            return null;
        }
        try {
            article = this.save(article);
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException("英文标题为:" + article.getName() + "的文章已存在，不能重复新增！");
        }
        return article;
    }

    @Override
    @CacheEvict(value = "article", key = "'article_'+#article.name")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Article article) {
        if (article == null) {
            return 0;
        }
        int result = this.updateNotNull(article);
        return result;
    }

    @Override
    @CacheEvict(value = "article", key = "'article_'+#name")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long articleId, String name) {
        if (articleId == null || articleId < 1) {
            return 0;
        }
        int result = articleMapper.delete(articleId);
        return result;
    }

    @Override
    @CacheEvict(value = "article", key = "'article_'+#article.name")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPublish(Article article) {
        int result = articleMapper.doPublish(article);
        return result;
    }

    @Override
    @CacheEvict(value = "article", key = "'article_'+#name")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doTop(boolean isTop, Long id, String name) {
        if (isTop) {
            return articleMapper.doTop(id);
        } else {
            return articleMapper.doCancelTop(id);
        }
    }

    @Override
    @CacheEvict(value = "article", key = "'article_'+#name")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRecommend(boolean isRecommend, Long id, String name) {
        if (isRecommend) {
            return articleMapper.doRecommend(id);
        } else {
            return articleMapper.doCancelRecommend(id);
        }
    }

    /**
     * 读取文件流 轻APP
     *
     * @throws IOException
     */
    @Override
    public String getUrl4Weixin(String readPath) throws IOException {
        String domain = "http://lightapp.d2cmall.com";
        URL targetUrl;
        String str = null;
        try {
            targetUrl = new URL(domain + "/" + readPath);
            BufferedReader in = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
            StringBuffer strBuffer = new StringBuffer();
            while ((str = in.readLine()) != null) {
                strBuffer.append(str);
            }
            str = strBuffer.toString();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public PageResult<ArticleDto> findDtoBySearcher(ArticleSearcher search, PageModel page) {
        PageResult<ArticleDto> pager = new PageResult<>(page);
        int totalCount = articleMapper.countBySearcher(search);
        List<ArticleDto> list = new ArrayList<>();
        if (totalCount > 0) {
            list = articleMapper.findDtoBySearcher(search, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public ArticleDto findDtoById(Long id) {
        return articleMapper.findDtoById(id);
    }

}
