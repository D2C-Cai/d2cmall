package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.AppVersionMongoDao;
import com.d2c.behavior.mongo.dao.PersonAddressMongoDao;
import com.d2c.behavior.mongo.dao.PersonDeviceMongoDao;
import com.d2c.behavior.mongo.dao.PersonSessionMongoDao;
import com.d2c.behavior.mongo.enums.SessionStatus;
import com.d2c.behavior.mongo.helper.VisitorFactory;
import com.d2c.behavior.mongo.model.*;
import com.d2c.behavior.mongo.service.EventMongoService;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.service.AddressService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Session会话
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class SessionServiceImpl implements SessionService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PersonSessionMongoDao personSessionMongoDao;
    @Autowired
    private PersonDeviceMongoDao personDeviceMongoDao;
    @Autowired
    private AppVersionMongoDao appVersionMongoDao;
    @Autowired
    private PersonAddressMongoDao personAddressMongoDao;
    @Autowired
    private PersonService personService;
    @Autowired
    private EventMongoService eventMongoService;
    @Reference
    private MemberInfoService memberInfoService;
    @Reference
    private AddressService addressService;

    /**
     * Session过期，重建Session
     * <p>
     * 根据Device设备数据重建
     *
     * @param deviceId
     */
    public PersonSessionDO nextSession(PersonDeviceDO device, MemberInfo memberInfo, String token) {
        AssertUt.notNull(device);
        PersonDO person = null;
        if (memberInfo != null) {
            person = personService.findBuildPerson(memberInfo);
        } else if (device.getPersonId() != null) {
            person = personService.findById(device.getPersonId());
        }
        PersonSessionDO session = new PersonSessionDO(device, person, token);
        // 关闭过期的Session
        updateSessionEnd(device.getUdid());
        syncSaveSessionAndDevice(session);
        return session;
    }

    /**
     * 第三方账户登录
     * <p>
     * 同步session，同步用户数据
     */
    public PersonSessionDO onThirdLogin(PersonSessionDO session, Member member) {
        if (!StringUtils.isEmpty(member.getLoginCode())) {
            MemberInfo memberInfo = memberInfoService.findByLoginCode(member.getLoginCode());
            session.setPerson(personService.applySavePerson(memberInfo));
        }
        PersonThirdDO third = personService.findCreatePersonThird(member, session.getPersonId());
        session.getDevice().setPersonThird(third);
        syncSaveSessionAndDevice(session);
        return session;
    }

    /**
     * 用户登录
     */
    public PersonSessionDO onLogin(PersonSessionDO session, MemberInfo memberInfo) {
        PersonDO person = personService.applySavePerson(memberInfo);
        setSessionAddress(memberInfo, person);
        session.setPerson(person);
        syncSaveSessionAndDevice(session);
        return session;
    }

    /**
     * 同步用户收货地址列表
     */
    private void setSessionAddress(MemberInfo memberInfo, PersonDO person) {
        if (person == null)
            return;
        if (person.getRealName() != null)
            return;
        List<AddressDto> addrs = addressService.findByMemberInfoId(memberInfo.getId());
        if (addrs == null || addrs.isEmpty())
            return;
        // 获取默认地址
        AddressDto dto = null;
        for (AddressDto addr : addrs) {
            if (addr.getIsdefault()) {
                dto = addr;
            }
        }
        if (dto != null) {
            dto = addrs.get(0);
        }
        if (dto == null) {
            return;
        }
        PersonAddressDO bean = new PersonAddressDO();
        bean.setPersonId(person.getId());
        bean.setName(dto.getName());
        bean.setPhone(dto.getMobile());
        bean.setDefault(dto.getIsdefault());
        bean.setAddress(dto.getStreet());
        if (dto.getProvince() != null) {
            bean.setProvince(dto.getProvince().getName());
        }
        if (dto.getCity() != null) {
            bean.setCity(dto.getCity().getName());
        }
        if (dto.getDistrict() != null) {
            bean.setArea(dto.getDistrict().getName());
        }
        person.setRealName(dto.getName());
        personAddressMongoDao.save(bean);
        personService.save(person);
    }

    /**
     * 查询设备
     */
    public PersonDeviceDO findDeviceById(String deviceId) {
        return personDeviceMongoDao.findById(deviceId);
    }

    /**
     * 查询用户最后一次会话
     */
    public PersonSessionDO findLastSessionByMemberId(Long memberInfoId) {
        return personSessionMongoDao.findLastSessionByMemberId(memberInfoId);
    }

    /**
     * 用户登出时，关闭Session会话
     */
    public void updateSessionClose(String sessionId) {
        PersonSessionDO bean = personSessionMongoDao.findSessionClose(sessionId);
        if (bean != null) {
            bean.setStatus(SessionStatus.END.name());
            bean.setEndTime();
            personSessionMongoDao.save(bean);
        }
    }

    /**
     * 关闭过期的Session
     */
    private void updateSessionEnd(String deviceId) {
        try {
            List<PersonSessionDO> list = personSessionMongoDao.findUnCloseSession(deviceId);
            list.forEach(bean -> {
                EventDO event = eventMongoService.getLastBySessionId(bean.getId());
                bean.setStatus(SessionStatus.END.name());
                bean.setEndTime(event != null ? event.getGmtModified() : new Date());
                personSessionMongoDao.save(bean);
            });
        } catch (Exception e) {
            logger.error("关闭过期的Session失败...", e);
        }
    }
    // ************** private ****************

    /**
     * 同步Session关联数值
     */
    private PersonSessionDO syncSaveSessionAndDevice(PersonSessionDO session) {
        try {
            PersonDeviceDO device = session.getDevice();
            AssertUt.notNull(device);
            PersonDO person = session.getPerson();
            if (person != null) {
                device.setNickname(person.getNickname());
                device.setHeadImg(person.getHeadImg());
                device.setSex(person.getSex());
                device.setPersonId(person.getId());
            } else if (device.getPersonThird() != null) {
                PersonThirdDO third = device.getPersonThird();
                device.setNickname(third.getNickname());
                device.setHeadImg(third.getHeadImg());
                device.setSex(third.getSex());
            } else {
                // 添加访客数据
                if (device.getNickname() == null) {
                    device.setNickname(VisitorFactory.getNickname());
                }
            }
            session.setNickname(device.getNickname());
            session.setHeadImg(device.getHeadImg());
            session.setSex(device.getSex());
            setAppVersion(device);
            // setting device
            device.setToken(session.getToken());
            personDeviceMongoDao.save(device);
            personSessionMongoDao.save(session);
        } catch (Exception e) {
            logger.error("同步Device和Session数据失败...", e);
        }
        return session;
    }

    /**
     * 设置App对应版本数据
     */
    private void setAppVersion(PersonDeviceDO device) {
        if (device.getApp() != null)
            return;
        AppVersionDO app = null;
        if (device.getAppVersionId() != null) {
            app = appVersionMongoDao.findById(device.getAppVersionId());
        }
        if (app != null && device.getAppTerminal() != null && device.getAppVersion() != null) {
            app = appVersionMongoDao.findOneByVersion(device.getAppTerminal(), device.getAppVersion());
            if (app == null) {
                app = appVersionMongoDao.save(new AppVersionDO(device.getAppTerminal(), device.getAppVersion()));
            }
        }
        device.setApp(app);
    }

}
