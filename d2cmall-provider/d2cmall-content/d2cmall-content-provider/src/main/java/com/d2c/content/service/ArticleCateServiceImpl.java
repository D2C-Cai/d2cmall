package com.d2c.content.service;

import com.d2c.content.dao.ArticleCategoryMapper;
import com.d2c.content.dto.ArticleCategoryHelpDto;
import com.d2c.content.model.ArticleCategory;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("articleCateService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ArticleCateServiceImpl extends ListServiceImpl<ArticleCategory> implements ArticleCateService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    public ArticleCategory findById(Long articleCategoryId) {
        return articleCategoryMapper.selectByPrimaryKey(articleCategoryId);
    }

    public List<ArticleCategory> findAll() {
        List<ArticleCategory> list = articleCategoryMapper.findAll();
        return list;
    }

    @Override
    public List<ArticleCategoryHelpDto> findAllForDto() {
        List<ArticleCategory> list = articleCategoryMapper.findAll();
        List<ArticleCategoryHelpDto> helpDtos = new ArrayList<ArticleCategoryHelpDto>();
        for (ArticleCategory act : list) {
            ArticleCategoryHelpDto dto = new ArticleCategoryHelpDto();
            BeanUtils.copyProperties(act, dto);
            helpDtos.add(dto);
        }
        return helpDtos;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ArticleCategory insert(ArticleCategory articleCategory) {
        if (articleCategory == null) {
            return null;
        }
        return this.save(articleCategory);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ArticleCategory articleCategory) {
        if (articleCategory == null) {
            return 0;
        }
        return this.updateNotNull(articleCategory);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return articleCategoryMapper.updateSort(id, sort);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long articleCategoryId) {
        if (articleCategoryId == null || articleCategoryId < 1) {
            return 0;
        }
        return articleCategoryMapper.delete(articleCategoryId);
    }

}
