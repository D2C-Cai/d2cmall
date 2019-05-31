package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.ArticleDto;
import com.d2c.content.model.Article;
import com.d2c.content.query.ArticleSearcher;

import java.io.IOException;

/**
 * 提供文章的相关数据库操作功能
 */
public interface ArticleService {

    /**
     * 以Id（数据库表Article唯一标识）作为参数，获取相应的文章信息。
     *
     * @param id
     * @return Article
     */
    Article findById(Long id);

    /**
     * 根据文章英文标题获取文章信息。
     *
     * @param name 英文标题
     * @return Article
     */
    Article findOneByName(String name);

    /**
     * 根据分类id，获取分类下的文章，采用PageModel分页，以list形式返回。
     *
     * @param catalogId 分类id
     * @param page      分页
     * @return List<Article>
     */
    PageResult<Article> findByCategoryId(Long catalogId, PageModel page);

    /**
     * 以ArticleSearch对象中包含的过滤条件从Article表中获取所有符合条件的文章信息， 以PageResult对象形式返回。
     *
     * @param search 过滤器
     * @param page   分页
     * @return PageResult<E>
     */
    PageResult<Article> findBySearcher(ArticleSearcher search, PageModel page);

    /**
     * 以ArticleSearch对象中包含的过滤条件从Article表中获取所有符合条件的文章信息数量
     *
     * @param searcher 过滤器
     * @return int
     */
    int countBySearcher(ArticleSearcher searcher);

    /**
     * 以Article对象作为参数，将文章信息插入Article表中。
     *
     * @param article
     * @return Article @throws
     */
    Article insert(Article article);

    /**
     * 文章发表功能，将草稿字段内（drafts）的内容赋值给正式内容（content），并修改文章的状态。
     *
     * @param article
     * @return int
     */
    int doPublish(Article article);

    /**
     * 根据文章id进行置顶或取消置顶操作。
     *
     * @param b
     * @param articleId 文章id
     * @return
     */
    int doTop(boolean b, Long articleId, String name);

    /**
     * 根据文章id进行是否推荐的操作。
     *
     * @param b
     * @param articleId 文章id
     * @return int
     */
    int doRecommend(boolean b, Long articleId, String name);

    /**
     * 以Article对象作为参数，将文章信息插入Article表中。
     *
     * @param article
     * @return int
     */
    int update(Article article);

    /**
     * 以Id（数据库表Article唯一标识）作为参数，删除相应的文章信息，并清除对应的缓存内容。
     *
     * @param articleId
     * @return
     */
    int delete(Long articleId, String name);

    /**
     * 根据文章地址，获取文章内容
     *
     * @param readPath 文章地址
     * @return
     * @throws IOException
     */
    String getUrl4Weixin(String readPath) throws IOException;

    /**
     * 查询Dto
     *
     * @param search
     * @param page
     * @return
     */
    PageResult<ArticleDto> findDtoBySearcher(ArticleSearcher search, PageModel page);

    ArticleDto findDtoById(Long id);

}
