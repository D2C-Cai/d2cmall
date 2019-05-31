package com.d2c.logger.dao;

import com.d2c.logger.model.GuanyiSkuStockLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

public interface GuanyiSkuStockLogMapper extends SuperMapper<GuanyiSkuStockLog> {

    int updateBySku(@Param("sku") String sku, @Param("stock") Integer stock);

}
