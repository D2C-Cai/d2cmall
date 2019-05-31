package com.d2c.mybatis.mapper.expand.template;

import com.d2c.mybatis.mapper.expand.mapperhelper.RelateHelper;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class RelateTemplate extends BaseMapperTemplate {

    public RelateTemplate(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String getJoinColumn(MappedStatement ms, Class<?> joinClass) {
        return RelateHelper.getJoinColumn(getEntityClass(ms), joinClass);
    }

    /**
     * 获取返回值类型 - 实体类型
     *
     * @param ms
     * @return
     */
    public Class<?> getMasterClass(MappedStatement ms) {
        return getEntityClass(ms, 1);
    }

    public Class<?> getSlaveClass(MappedStatement ms) {
        return getEntityClass(ms, 2);
    }

    public Class<?> getEntityClass(MappedStatement ms) {
        Class<?> clz = getEntityClass(ms, 0);
        RelateHelper.initRelateMap(clz, mapperHelper.getConfig());
        return clz;
    }

    protected Class<?> getEntityClass(MappedStatement ms, int i) {
        String msId = ms.getId();
        String key = msId + "-" + i;
        if (entityClassMap.containsKey(key)) {
            return entityClassMap.get(key);
        } else {
            Class<?> mapperClass = getMapperClass(msId);
            Type[] types = mapperClass.getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType t = (ParameterizedType) type;
                    if (t.getRawType() == this.mapperClass || this.mapperClass.isAssignableFrom((Class<?>) t.getRawType())) {
                        Class<?> returnType = (Class<?>) t.getActualTypeArguments()[i];
                        //获取该类型后，第一次对该类型进行初始化
                        EntityHelper.initEntityNameMap(returnType, mapperHelper.getConfig());
                        entityClassMap.put(key, returnType);
                        return returnType;
                    }
                }
            }
        }
        throw new MapperException("无法获取Mapper的第" + i + "个泛型类型:" + msId);
    }

}
