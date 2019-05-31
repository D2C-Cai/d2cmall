package com.d2c.mybatis.mapper.expand.template;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public abstract class BaseMapperTemplate extends MapperTemplate {

    protected static final String LIMIT_ONE = " limit 0,1 ";

    public BaseMapperTemplate(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 获得查询where之前的SQL, 返回实体类
     */
    protected String selectWhereSql(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(" where ");
        return sql.toString();
    }

}
