package com.d2c.mybatis.mapper.expand.mapper;

import com.d2c.mybatis.mapper.expand.provider.MyUpdateProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.Marker;

/**
 * 扩展Update修改接口
 *
 * @author wull
 */
public interface MyUpdateMapper<T> extends Marker {

    /**
     * 修改单个数据属性数据
     */
    @UpdateProvider(type = MyUpdateProvider.class, method = "dynamicSQL")
    @Options(useCache = false, useGeneratedKeys = false)
    int updateFieldById(@Param("id") Integer id, @Param("key") String key, @Param("value") Object value);

}
