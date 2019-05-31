package com.d2c.mybatis.mapper.expand.config;

import com.d2c.mybatis.mapper.expand.RelateMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

public class MapperConfig extends MapperScannerConfigurer {

    public void setMarkerInterface(Class<?> superClass) {
        super.setMarkerInterface(superClass);
        getMapperHelper().registerMapper(Mapper.class);
        getMapperHelper().registerMapper(RelateMapper.class);
    }

}
