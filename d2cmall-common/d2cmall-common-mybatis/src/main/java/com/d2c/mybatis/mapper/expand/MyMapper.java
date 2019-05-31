package com.d2c.mybatis.mapper.expand;

import com.d2c.mybatis.mapper.expand.mapper.MySelectMapper;
import com.d2c.mybatis.mapper.expand.mapper.MyUpdateMapper;

/**
 * 扩展搜索接口
 *
 * @author wull
 */
public interface MyMapper<T> extends MySelectMapper<T>, MyUpdateMapper<T> {

}
