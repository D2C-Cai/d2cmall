package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.dao.UserOperateLogMapper;
import com.d2c.logger.model.UserOperateLog;
import com.d2c.logger.query.UserOperateLogSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("userOperateLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class UserOperateLogServiceImpl extends ListServiceImpl<UserOperateLog> implements UserOperateLogService {

    @Autowired
    private UserOperateLogMapper userOperateLogMapper;

    public List<Map<String, Object>> count(UserOperateLogSearcher searcher) {
        return userOperateLogMapper.count(searcher);
    }

    @Override
    public UserOperateLog findById(Long key) {
        return this.findOneById(key);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public int insert(List<UserOperateLog> entitys, boolean file) {
        if (entitys != null && entitys.size() > 0) {
            for (int i = 0; i < entitys.size(); i++) {
                UserOperateLog log = entitys.get(i);
                if (!file) {
                    mapper.insert(log);
                } else {
                    String json = JSONObject.toJSONString(log);
                    logger.error(json);
                }
            }
        }
        return 1;
    }

    @Override
    public UserOperateLog save(UserOperateLog entity) {
        mapper.insert(entity);
        return entity;
    }

}
