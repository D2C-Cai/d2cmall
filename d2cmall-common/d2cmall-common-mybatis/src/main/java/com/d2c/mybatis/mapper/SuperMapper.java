package com.d2c.mybatis.mapper;

import com.d2c.mybatis.mapper.expand.MyMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.base.delete.DeleteByPrimaryKeyMapper;
import tk.mybatis.mapper.common.rowbounds.SelectByConditionRowBoundsMapper;

public interface SuperMapper<T> extends BaseMapper, MyMapper<T>, ConditionMapper<T>, RowBoundsMapper<T>, BaseSelectMapper<T>,
        BaseInsertMapper<T>, BaseUpdateMapper<T>, DeleteByPrimaryKeyMapper<T>, MySqlMapper<T>, SelectByConditionRowBoundsMapper<T>, Marker {

}
