package com.d2c.similar.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.service.ProductCategoryService;
import com.d2c.similar.dto.SimilarSchemeDTO;
import com.d2c.similar.entity.SimilarSchemeDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wull
 */
@Service(protocol = "dubbo")
public class SimilarSchemeServiceImpl extends ListServiceImpl<SimilarSchemeDO> implements SimilarSchemeService {

    @Reference
    private ProductCategoryService productCategoryService;
    @Autowired
    private SimilarRuleService similarRuleService;

    /**
     * 相似度方案明细
     */
    public SimilarSchemeDTO getSchemeDetail(Integer schemeId) {
        SimilarSchemeDTO bean = convert(findOneById(schemeId));
        bean.setRules(similarRuleService.findRulesBySchemeId(schemeId));
        return bean;
    }

    /**
     * 修改方案任务是否已执行
     */
    public int updateSchemeHasExce(Integer id, Boolean hasExec) {
        return updateFieldById(id, "hasExec", hasExec);
    }

    /**
     * 根据product对应处理方案
     */
    public SimilarSchemeDO findSchemeByCategoryId(Object categoryId) {
        List<SimilarSchemeDO> list = findByFieldName("categoryId", categoryId);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    /**
     * 创建相似商品方案
     */
    public SimilarSchemeDO createScheme(SimilarSchemeDO scheme) {
        save(scheme);
        similarRuleService.createRuleBySchemeId(scheme.getId());
        return scheme;
    }

    public void clean() {
        similarRuleService.deleteAll();
        deleteAll();
    }

    private SimilarSchemeDTO convert(SimilarSchemeDO bean) {
        return ConvertUt.convertBean(bean, SimilarSchemeDTO.class);
    }
    //*************************************************
//	/**
//	 * 创建商品品类对应初始化离散数据
//	 */
//	private void buildCategoryData(SimilarSchemeDO scheme) {
//		List<ProductCategory> list = productCategoryService.findByTopId(scheme.getCategoryId().longValue());
//		SimilarRuleDO rule = similarRuleService.findRuleByCode(scheme.getId(), SimilarRuleEnum.PRODUCT_CATEGORY.name());
//		if(rule != null) {
//			for(ProductCategory pc : list){
//				createDimenKeyValue(rule, pc.getName(), pc.getId().toString());
//			}
//		}
//	}
//	private void createDimenKeyValue(SimilarRuleDO rule, String fieldName, String fieldValue) {
//		DimenKeyDO bean = dimenKeyService.createDimenKey(rule.getId(),fieldName, fieldValue);
//		for(DimenTplDO tpl : dimenTplService.findDimenTplByRuleCode(rule.getRuleCode())){
//			double dist = (0.5 - Math.random()) * 20 ;
//			dimenValueService.createDimenValue(bean.getId(), tpl.getId(), dist);
//		}
//	}
}
