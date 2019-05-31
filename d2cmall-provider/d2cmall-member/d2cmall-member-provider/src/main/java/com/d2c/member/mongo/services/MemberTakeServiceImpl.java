package com.d2c.member.mongo.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.mongo.dao.MemberTakeMongoDao;
import com.d2c.member.mongo.model.MemberTakeDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(protocol = "dubbo")
public class MemberTakeServiceImpl implements MemberTakeService {

    @Autowired
    private MemberTakeMongoDao memberTakeMongoDao;

    @Override
    public int insert(MemberTakeDO awardMemberDO) {
        if (memberTakeMongoDao.findByUnique(awardMemberDO.getUniqueMark()) == null) {
            awardMemberDO = memberTakeMongoDao.save(awardMemberDO);
        } else {
            throw new BusinessException("您已经参与了抽奖，中奖结果将在活动结束后公布！");
        }
        return 1;
    }

    @Override
    public List<MemberTakeDO> findByMemberIdAndType(Long memberId, String type) {
        return memberTakeMongoDao.findByMemberIdAndType(memberId, type);
    }

}
