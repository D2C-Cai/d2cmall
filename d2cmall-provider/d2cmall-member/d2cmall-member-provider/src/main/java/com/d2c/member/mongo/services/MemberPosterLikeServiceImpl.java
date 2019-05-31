package com.d2c.member.mongo.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.mongo.dao.MemberPosterLikeMongoDao;
import com.d2c.member.mongo.model.MemberPosterDO;
import com.d2c.member.mongo.model.MemberPosterLikeDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service(protocol = "dubbo")
public class MemberPosterLikeServiceImpl implements MemberPosterLikeService {

    @Autowired
    private MemberPosterLikeMongoDao memberPosterLikeMongoDao;
    @Reference
    private MemberPosterService memberPosterService;

    @Override
    public MemberPosterLikeDO insert(MemberPosterLikeDO memberPosterLikeDO) {
        memberPosterLikeDO = memberPosterLikeMongoDao.save(memberPosterLikeDO);
        MemberPosterDO old = memberPosterService.findById(memberPosterLikeDO.getPostId());
        if (memberPosterLikeDO.getDirect() == 1) {
            memberPosterService.updateLikeCount(old.getId(), old.getLikeCount() + 1);
        } else if (memberPosterLikeDO.getDirect() == -1) {
            memberPosterService.updateDissCount(old.getId(), old.getDissCount() + 1);
        }
        return memberPosterLikeDO;
    }

    @Override
    public int countByDate(String memberId, String postId, Date startDate, Date endDate) {
        return memberPosterLikeMongoDao.countByDate(memberId, postId, startDate, endDate);
    }

    @Override
    public PageResult<MemberPosterLikeDO> findByPostId(String postId, PageModel page) {
        PageResult<MemberPosterLikeDO> dtos = new PageResult<>(page);
        long totalCount = memberPosterLikeMongoDao.countByPostId(postId);
        if (totalCount > 0) {
            List<MemberPosterLikeDO> list = memberPosterLikeMongoDao.findByPostId(postId, page.getPageNumber(),
                    page.getPageSize());
            dtos.setList(list);
        }
        dtos.setTotalCount((int) totalCount);
        return dtos;
    }

}
