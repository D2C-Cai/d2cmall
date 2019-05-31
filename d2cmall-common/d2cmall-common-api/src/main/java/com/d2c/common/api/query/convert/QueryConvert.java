package com.d2c.common.api.query.convert;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.annotation.search.SearchIgnore;
import com.d2c.common.api.annotation.search.SearchParam;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.QueryParam;
import com.d2c.common.api.query.model.SuperQuery;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.StringUt;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件转换器
 *
 * @author wull
 */
public class QueryConvert {

    public static List<QueryItem> convert(SuperQuery bean) {
        List<QueryItem> list = new ArrayList<>();
        if (bean != null) {
            bean.init();
            for (Field field : BeanUt.getAllFields(bean.getClass())) {
                if (field.getAnnotation(SearchIgnore.class) != null) {
                    continue;
                }
                Object value = BeanUt.getValue(bean, field.getName());
                if (value != null) {
                    if (value instanceof String) {
                        if (StringUt.isBlank((String) value)) {
                            continue;
                        }
                    }
                    SearchField ann = field.getAnnotation(SearchField.class);
                    list.add(buildQueryItem(ann, field.getName(), value));
                }
            }
        }
        return list;
    }

    private static QueryItem buildQueryItem(SearchField ann, String fieldName, Object value) {
        if (ann == null) {
            return new QueryItem(fieldName, value);
        }
        QueryItem item = new QueryItem();
        String name = StringUtils.isEmpty(ann.name()) ? ann.value() : ann.name();
        if (!StringUtils.isEmpty(name)) {
            item.setName(name);
        } else {
            item.setName(fieldName);
        }
        if (!StringUtils.isEmpty(ann.showName())) {
            item.setShowName(ann.showName());
        } else {
            item.setShowName(item.getName());
        }
        item.setOperator(ann.oper());
        item.setShowType(ann.show());
        item.setValueType(ann.type());
        item.setSortType(ann.sort());
        item.setParams(getParams(ann));
        item.setValue(item.getValueType().getValue(value));
        return item;
    }

    private static List<QueryParam> getParams(SearchField ann) {
        List<QueryParam> params = new ArrayList<>();
        for (SearchParam p : ann.params()) {
            params.add(new QueryParam(p));
        }
        return params;
    }

}
