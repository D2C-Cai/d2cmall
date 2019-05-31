package com.d2c.mybatis.mapper.expand.provider;

import com.d2c.mybatis.mapper.expand.template.RelateTemplate;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class RelateProvider extends RelateTemplate {

    public RelateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据主表ID获取从属对象列表
     */
    public String getSlaveListByMasterId(MappedStatement ms) {
        Class<?> entityClz = getEntityClass(ms);
        Class<?> masterClz = getMasterClass(ms);
        Class<?> slaveClz = getSlaveClass(ms);
        setResultType(ms, slaveClz);
        String sql = "select b.* from "
                + tableName(entityClz) + " a left join "
                + tableName(slaveClz) + " b on b.id = a." + getJoinColumn(ms, slaveClz)
                + " where a." + getJoinColumn(ms, masterClz) + " = #{masterId}";
        return sql;
    }

}
