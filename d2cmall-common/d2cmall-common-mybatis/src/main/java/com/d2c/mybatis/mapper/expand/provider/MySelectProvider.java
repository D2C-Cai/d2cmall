package com.d2c.mybatis.mapper.expand.provider;

import com.d2c.mybatis.mapper.expand.template.BaseMapperTemplate;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class MySelectProvider extends BaseMapperTemplate {

    public MySelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据单个参数查询
     */
    public String selectByFieldName(MappedStatement ms) {
        return selectWhereSql(ms) + " ${fieldName} = #{fieldValue} ";
    }

    public String selectOneByFieldName(MappedStatement ms) {
        return selectByFieldName(ms) + LIMIT_ONE;
    }

    public String selectResultByFieldName(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        return "select ${result} from " + tableName(entityClass) + " where ${fieldName} = #{fieldValue} ";
    }

    public String selectOneResultByFieldName(MappedStatement ms) {
        return selectResultByFieldName(ms) + LIMIT_ONE;
    }

}
