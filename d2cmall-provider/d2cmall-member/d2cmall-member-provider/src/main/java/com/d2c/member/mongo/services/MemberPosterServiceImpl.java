package com.d2c.member.mongo.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.mongo.dao.MemberPosterMongoDao;
import com.d2c.member.mongo.model.MemberPosterDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(protocol = "dubbo")
public class MemberPosterServiceImpl implements MemberPosterService {

    @Autowired
    private MemberPosterMongoDao memberPosterMongoDao;

    @Override
    public MemberPosterDO insert(MemberPosterDO memberPosterDO) {
        return memberPosterMongoDao.insert(memberPosterDO);
    }

    @Override
    public MemberPosterDO findById(String id) {
        return memberPosterMongoDao.findById(id);
    }

    @Override
    public MemberPosterDO findByMemberId(String memberId) {
        return memberPosterMongoDao.findByMemberId(memberId);
    }

    @Override
    public PageResult<MemberPosterDO> findTops(PageModel page) {
        PageResult<MemberPosterDO> dtos = new PageResult<>(page);
        long totalCount = memberPosterMongoDao.countTops();
        if (totalCount > 0) {
            List<MemberPosterDO> list = memberPosterMongoDao.findTops(page.getPageNumber(), page.getPageSize());
            dtos.setList(list);
        }
        dtos.setTotalCount((int) totalCount);
        return dtos;
    }

    @Override
    public MemberPosterDO updateLikeCount(String id, Integer count) {
        return memberPosterMongoDao.updateLikeCount(id, count);
    }

    @Override
    public MemberPosterDO updateDissCount(String id, Integer count) {
        return memberPosterMongoDao.updateDissCount(id, count);
    }

    @Override
    public int countRanking(Integer count) {
        return (int) memberPosterMongoDao.countRanking(count);
    }

}
