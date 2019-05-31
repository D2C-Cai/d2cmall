package com.d2c.content.service;

import com.d2c.content.dao.FieldDefineMapper;
import com.d2c.content.model.FieldDefine;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("fieldDefineService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class FieldDefineServiceImpl extends ListServiceImpl<FieldDefine> implements FieldDefineService {

    @Autowired
    private FieldDefineMapper fieldDefineMapper;

    public List<FieldDefine> findByPageDefId(Long pageDefId) {
        List<FieldDefine> fields = fieldDefineMapper.findByPageDefId(pageDefId);
        return fields;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(FieldDefine def) {
        return this.updateNotNull(def);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public FieldDefine insert(FieldDefine def) {
        return this.save(def);
    }

    public FieldDefine findOne(Long pageDefId, String code) {
        return fieldDefineMapper.findOne(pageDefId, code);
    }

    public FieldDefine findOneById(Long id) {
        return fieldDefineMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FieldDefine> findByPageDefId(Long pageDefId, int status) {
        List<FieldDefine> fields = fieldDefineMapper.findByPageDefIdWithStatus(pageDefId, status);
        return fields;
    }

}
