package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.PersonSessionMongoDao;
import com.d2c.behavior.mongo.dto.PersonSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户数据统计
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class PersonStatServiceImpl implements PersonStatService {

    @Autowired
    private PersonSessionMongoDao personSessionMongoDao;

    /**
     * 会员用户登录统计
     */
    public List<PersonSessionDTO> findPersonSessionList() {
        return personSessionMongoDao.findPersonSessionList(null, null);
    }

    /**
     * 访客用户登录统计
     */
    public List<PersonSessionDTO> findVistorSessionList() {
        return personSessionMongoDao.findVisitorSessionList(null, null);
    }

}
