package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.PersonAddressMongoDao;
import com.d2c.behavior.mongo.dao.PersonMongoDao;
import com.d2c.behavior.mongo.model.PersonAddressDO;
import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.model.Address;
import com.d2c.order.model.Area;
import com.d2c.order.service.AddressService;
import com.d2c.order.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * 用户数据导入
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class PersonImportServiceImpl implements PersonImportService {

    private final static ExecutorService pools = MyExecutors.newLimit(20);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //*************************** Address ***********************
    int addPerson = 0;
    @Reference
    private MemberInfoService memberInfoService;
    @Reference
    private AreaService areaService;
    @Reference
    private AddressService addressService;
    @Autowired
    private PersonMongoDao personMongoDao;
    @Autowired
    private PersonAddressMongoDao personAddressMongoDao;

    @Override
    public Object init() {
        personMongoDao.cleanAll();
        importPerson();
        return personMongoDao.findLimit(null, 20);
    }

    /**
     * 导入用户数据
     */
    @Override
    public void importPerson() {
        PageBucket<MemberInfo> bucket = new PageBucket<MemberInfo>() {
            List<QueryItem> querys;

            @Override
            public void init() {
                querys = new ArrayList<>();
                querys.add(new QueryItem("mobile", OperType.NOT_NULL));
            }

            @Override
            public List<MemberInfo> nextList(int page, int limit) {
                return memberInfoService.findQuery(querys, page, limit);
            }
        };
        Date start = new Date();
        logger.info("开始导入用户数据..." + bucket.getCount());
        while (bucket.hasNext()) {
            MemberInfo bean = bucket.next();
            int count = bucket.getCount();
            pools.execute(new Runnable() {
                @Override
                public void run() {
                    savePerson(bean);
                }
            });
            if (count % 1000 == 0) {
                logger.info(DateUt.duration(start, new Date()) + " 导入用户数据..." + count);
            }
        }
    }

    public PersonDO findBuildPerson(MemberInfo member) {
        PersonDO person = personMongoDao.findByMemberInfoId(member.getId());
        if (person == null) {
            person = savePerson(member);
        }
        return person;
    }

    /**
     * 历史埋点数据分析处理
     */
    private PersonDO savePerson(MemberInfo bean) {
        try {
            AssertUt.isNumber(bean.getMobile(), "电话不是数值..." + bean.getMobile());
            PersonDO res = new PersonDO();
            res.setPhone(bean.getMobile());
            res.setMemberInfoId(bean.getId());
            res.setHeadImg(bean.getHeadPic());
            res.setNickname(bean.getNickname());
            res.setSex(bean.getSex());
            res.setBirthday(bean.getBirthday());
            res.setEmail(bean.getEmail());
            res.setIdentityCard(bean.getIdentityCard());
            res.setRealName(bean.getRealName());
            res.setNationCode(bean.getNationCode());
            return personMongoDao.insert(res);
        } catch (AssertException e) {
            logger.debug("导入用户数据校验失败... id:" + bean.getId() + e.getMessage());
        } catch (Exception e) {
            logger.info("导入用户数据失败..." + e.getMessage() + " bean: " + JsonUt.serialize(bean));
        }
        return null;
    }

    /**
     * 导入收货地址
     */
    public void importAddress() {
        Map<String, String> map = getAreaMap();
        PageBucket<Address> bucket = new PageBucket<Address>() {
            @Override
            public List<Address> nextList(int page, int limit) {
                return addressService.findPage(page, limit);
            }
        };
        Date start = new Date();
        logger.info("开始收货地址..." + bucket.getCount());
        while (bucket.hasNext()) {
            Address bean = bucket.next();
            int count = bucket.getCount();
            pools.execute(new Runnable() {
                @Override
                public void run() {
                    saveAddress(map, bean);
                }
            });
            if (count % 1000 == 0) {
                logger.info(DateUt.duration(start, new Date()) + " 导入收货地址..." + count + " 新增导入用户: " + addPerson);
            }
        }
        logger.info("收货地址导入结束...共导入" + personAddressMongoDao.countAll());
    }

    /**
     * 收货地址导入
     */
    private PersonAddressDO saveAddress(Map<String, String> map, Address bean) {
        try {
            AssertUt.isPhone(bean.getMobile());
            PersonDO person = personMongoDao.findByPhone(bean.getMobile());
            PersonAddressDO res = new PersonAddressDO();
            res.setName(bean.getName());
            res.setPhone(bean.getMobile());
            res.setDefault(bean.getIsdefault());
            res.setProvince(map.get(bean.getRegionPrefix()));
            res.setCity(map.get(bean.getRegionMiddle()));
            res.setArea(map.get(bean.getRegionSuffix()));
            res.setAddress(bean.getStreet());
            if (person == null) {
                person = savePersonByAddress(res);
            }
            res.setPersonId(person.getId());
            return personAddressMongoDao.saveOnImport(res);
        } catch (AssertException e) {
            logger.debug("导入收货地址校验失败... id:" + bean.getId() + e.getMessage());
        } catch (Exception e) {
            logger.info("导入收货地址失败..." + e.getMessage() + " bean: " + JsonUt.serialize(bean));
        }
        return null;
    }

    private PersonDO savePersonByAddress(PersonAddressDO bean) {
        PersonDO person = new PersonDO();
        try {
            person.setPhone(bean.getPhone());
            person.setNickname(bean.getName());
            person.setRealName(bean.getName());
            addPerson++;
            person = personMongoDao.save(person);
        } catch (Exception e) {
            logger.info("导入保存为用户失败..." + e.getMessage() + " bean: " + JsonUt.serialize(person));
        }
        return person;
    }

    private Map<String, String> getAreaMap() {
        Map<String, String> map = new HashMap<>();
        for (Area bean : areaService.findAll()) {
            if (bean.getId() != null)
                map.put(bean.getId().toString(), bean.getName());
        }
        return map;
    }

}
