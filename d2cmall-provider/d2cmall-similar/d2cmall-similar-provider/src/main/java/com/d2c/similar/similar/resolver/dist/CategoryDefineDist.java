package com.d2c.similar.similar.resolver.dist;

import com.d2c.common.core.helper.SpringHelper;
import com.d2c.product.service.ProductCategoryService;
import com.d2c.similar.entity.SimilarRuleDO;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义品类分级距离
 *
 * @author wull
 */
public class CategoryDefineDist extends AbstractDistance {

    private ProductCategoryService productCategoryService;
    private Map<Object, Object> map = new HashMap<>();

    @Override
    public void init(SimilarRuleDO rule) {
        productCategoryService = SpringHelper.getBean(ProductCategoryService.class);
        super.init(rule);
    }

    @Override
    public double doExec() {
        if (value.equals(target)) {
            return 0;
        } else if (getParentId(value).equals(getParentId(target))) {
            return 0.5;
        }
        return 1;
    }

    public Object getParentId(Object value) {
        Object parentId = map.get(value);
        if (parentId == null) {
            parentId = productCategoryService.findById((long) value).getParentId();
            map.put(value, parentId == null ? "" : parentId);
        }
        return parentId;
    }

}
