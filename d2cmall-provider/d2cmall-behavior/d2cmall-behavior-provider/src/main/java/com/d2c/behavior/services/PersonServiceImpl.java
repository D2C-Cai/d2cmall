package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.mongo.dao.PersonGroupMongoDao;
import com.d2c.behavior.mongo.dao.PersonMongoDao;
import com.d2c.behavior.mongo.dao.PersonThirdMongoDao;
import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.behavior.mongo.model.PersonGroupDO;
import com.d2c.behavior.mongo.model.PersonThirdDO;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户数据
 *
 * @author wull
 */
@Component
public class PersonServiceImpl implements PersonService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PersonMongoDao personMongoDao;
    @Autowired
    private PersonGroupMongoDao personGroupMongoDao;
    @Autowired
    private PersonThirdMongoDao personThirdMongoDao;
    @Reference
    private MemberInfoService memberInfoService;

    @Override
    public List<PersonDO> findPersonByGroupId(String groupId, Integer page, Integer limit) {
        PersonGroupDO group = personGroupMongoDao.findById(groupId);
        if (group == null) {
            return personMongoDao.findPage(page, limit);
        }
        return personMongoDao.findPageBean(group.getBson(), page, limit);
    }

    @Override
    public PersonDO findById(String id) {
        return personMongoDao.findById(id);
    }

    @Override
    public PersonThirdDO findCreatePersonThird(Member member, String personId) {
        PersonThirdDO bean = personThirdMongoDao.findById(member.getUnionId());
        if (bean == null) {
            bean = buildPersonThird(member, personId);
            personThirdMongoDao.save(bean);
        }
        return bean;
    }

    private PersonThirdDO buildPersonThird(Member member, String personId) {
        PersonThirdDO bean = new PersonThirdDO();
        bean.setMemberId(member.getId());
        bean.setUnionId(member.getUnionId());
        bean.setOpenId(member.getOpenId());
        bean.setNickname(member.getNickname());
        bean.setHeadImg(member.getHeadPic());
        bean.setSex(member.getSex());
        bean.setSource(member.getSource());
        bean.setPersonId(personId);
        personThirdMongoDao.save(bean);
        return bean;
    }
    //**************** memberInfo *****************

    @Override
    public PersonDO findBuildPerson(Long memberId) {
        if (memberId == null) return null;
        PersonDO person = personMongoDao.findByMemberInfoId(memberId);
        if (person == null) {
            MemberInfo member = memberInfoService.findById(memberId);
            if (member == null) {
                throw new BaseException("会员memberInfo不存在...memberId: " + memberId);
            }
            person = createPerson(member);
        }
        return person;
    }

    @Override
    public PersonDO findBuildPerson(MemberInfo member) {
        if (member == null) return null;
        PersonDO person = personMongoDao.findByMemberInfoId(member.getId());
        if (person == null) {
            person = createPerson(member);
        }
        return person;
    }

    private PersonDO createPerson(MemberInfo member) {
        if (member == null) return null;
        PersonDO person = convertPerson(member);
        if (person != null) {
            personMongoDao.insert(person);
        }
        return person;
    }

    @Override
    public PersonDO applySavePerson(MemberInfo member) {
        PersonDO old = personMongoDao.findByMemberInfoId(member.getId());
        PersonDO bean = convertPerson(member);
        if (old != null) {
            bean = BeanUt.apply(old, bean);
        }
        if (bean != null) {
            personMongoDao.save(bean);
        }
        return bean;
    }

    @Override
    public PersonDO save(PersonDO bean) {
        return personMongoDao.save(bean);
    }

    /**
     * 加载MemberInfo用户数据
     */
    private PersonDO convertPerson(MemberInfo bean) {
        try {
            AssertUt.notNull(bean.getMobile());
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
            return res;
        } catch (AssertException e) {
            logger.debug("加载MemberInfo用户数据校验失败... id:" + bean.getId() + e.getMessage());
        } catch (Exception e) {
            logger.info("加载MemberInfo用户数据失败..." + e.getMessage() + " bean: " + JsonUt.serialize(bean));
        }
        return null;
    }

}
