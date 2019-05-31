package com.d2c.behavior.services;

import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.behavior.mongo.model.PersonThirdDO;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;

import java.util.List;

public interface PersonService {

    /**
     * 根据用户分组获取用户列表
     */
    List<PersonDO> findPersonByGroupId(String groupId, Integer page, Integer limit);

    /**
     * 根据ID查询
     */
    PersonDO findById(String id);

    /**
     * 查询并创建第三方登录数据
     */
    PersonThirdDO findCreatePersonThird(Member member, String personId);

    /**
     * 查询并保存用户
     */
    PersonDO findBuildPerson(Long memberId);

    PersonDO findBuildPerson(MemberInfo member);

    /**
     * 同步用户数据
     */
    PersonDO applySavePerson(MemberInfo member);

    PersonDO save(PersonDO bean);

}