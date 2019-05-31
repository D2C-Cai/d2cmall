package com.d2c.mybatis.mapper.expand.provider;

import com.d2c.mybatis.mapper.expand.template.BaseMapperTemplate;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class MyUpdateProvider extends BaseMapperTemplate {

    public MyUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据单个参数查询，返回实体列表
     */
    public String updateFieldById(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        String sql = "update " + tableName(entityClass) + " set  ${key} = #{value} where id = #{id}";
        return sql;
    }

}
