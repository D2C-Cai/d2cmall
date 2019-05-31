package com.d2c.content.service;

import com.d2c.content.dto.ArticleCategoryHelpDto;
import com.d2c.content.model.ArticleCategory;

import java.util.List;

/**
 * 提供文章分类的相关数据库操作功能；
 */
public interface ArticleCateService {

    /**
     * 以Id（数据库article_category唯一标识）作为参数，获取相应文章分类的信息。
     *
     * @param id
     * @return ArticleCategory
     */
    ArticleCategory findById(Long id);

    /**
     * 从article_category表中获取所有的文章分类信息。
     *
     * @return List<E>
     */
    List<ArticleCategory> findAll();

    /**
     * 从article_category表中获取所有的文章分类信息,以HelpDto形式返回。
     *
     * @return List<E>
     */
    List<ArticleCategoryHelpDto> findAllForDto();

    /**
     * 以ArticleCategory对象作为参数，将文章分类信息插入article_category表中。
     *
     * @param articleCategory
     * @return int
     */
    ArticleCategory insert(ArticleCategory articleCategory);

    /**
     * 以ArticleCategory对象作为参数，将文章分类信息更新至article_category表中。
     *
     * @param articleCategory
     * @return int
     */
    int update(ArticleCategory articleCategory);

    /**
     * 更新顺序
     *
     * @param id
     * @param integer
     * @return
     */
    int updateSort(Long id, Integer integer);

    /**
     * 以Id（数据库article_category唯一标识）作为参数，删除对应的文章分类的信息。
     *
     * @param id
     * @return int
     */
    int delete(Long id);

}
