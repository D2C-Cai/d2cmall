package com.d2c.product.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.BrandContract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandContractMapper extends SuperMapper<BrandContract> {

    List<BrandContract> findByBrandId(Long brandId);

    List<Long> findIdsByBrandId(Long brandId);

    BrandContract findByBrandIdAndType(@Param("brandId") Long brandId, @Param("type") String type);

}
