package com.d2c.mybatis.mapper.expand.mapper;

import com.d2c.mybatis.mapper.expand.provider.MySelectProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Marker;

import java.util.List;

/**
 * 扩展搜索接口
 *
 * @author wull
 */
public interface MySelectMapper<T> extends Marker {

    /**
     * 根据单个参数查询
     */
    @SelectProvider(type = MySelectProvider.class, method = "dynamicSQL")
    List<T> selectByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    @SelectProvider(type = MySelectProvider.class, method = "dynamicSQL")
    T selectOneByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    /**
     * 根据单个参数查询，返回对应查询字段
     */
    @SelectProvider(type = MySelectProvider.class, method = "dynamicSQL")
    List<Object> selectResultByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue, @Param("result") String result);

    @SelectProvider(type = MySelectProvider.class, method = "dynamicSQL")
    Object selectOneResultByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue, @Param("result") String result);

}
