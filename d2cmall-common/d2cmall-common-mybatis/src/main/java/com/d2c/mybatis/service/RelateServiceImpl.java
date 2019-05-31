package com.d2c.mybatis.service;

import com.d2c.common.api.dto.RelateDTO;
import com.d2c.common.api.model.BaseParentDO;
import com.d2c.common.api.service.RelateService;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.mybatis.mapper.expand.RelateMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Mybatis服务层关联表基础类
 */
public abstract class RelateServiceImpl<T extends BaseParentDO<?>, E, F> extends BaseServiceImpl<T>
        implements RelateService<T, E, F> {

    @Autowired
    protected RelateMapper<T, E, F> mapper;

    public List<F> getSlaveListByMasterId(Integer masterId) {
        return mapper.getSlaveListByMasterId(masterId);
    }

    public void saveRelate(RelateDTO<T> relate) {
        AssertUt.notNull(relate);
        if (relate.getAddList() != null) {
            for (T bean : relate.getAddList()) {
                this.save(bean);
            }
        }
        if (relate.getUpdateList() != null) {
            for (T bean : relate.getUpdateList()) {
                this.updateNotNull(bean);
            }
        }
        if (relate.getDeleteList() != null) {
            for (T bean : relate.getDeleteList()) {
                this.delete(bean);
            }
        }
    }

}
