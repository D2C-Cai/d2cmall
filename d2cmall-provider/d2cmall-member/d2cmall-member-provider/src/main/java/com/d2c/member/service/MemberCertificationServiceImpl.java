package com.d2c.member.service;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.dao.MemberCertificationMapper;
import com.d2c.member.model.MemberCertification;
import com.d2c.member.query.MemberCertificationSearcher;
import com.d2c.member.third.aliyun.CertificationClient;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service(value = "memberCertificationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class MemberCertificationServiceImpl extends ListServiceImpl<MemberCertification>
        implements MemberCertificationService {

    @Autowired
    private MemberCertificationMapper memberCertificationMapper;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    @Override
    public MemberCertification findDefaultOne(Long memberId) {
        return memberCertificationMapper.findDefaultOne(memberId);
    }

    @Override
    public MemberCertification findByMemberIdAndCard(Long memberId, String identityCard) {
        return memberCertificationMapper.findByMemberIdAndCard(memberId, identityCard);
    }

    @Override
    public PageResult<MemberCertification> findBySearcher(MemberCertificationSearcher searcher, PageModel page) {
        PageResult<MemberCertification> pager = new PageResult<>(page);
        int totalCount = memberCertificationMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<MemberCertification> list = memberCertificationMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(MemberCertificationSearcher searcher) {
        return memberCertificationMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDefault(Long id, Long memberId) {
        MemberCertification memberCertification = this.findOneById(id);
        if (memberCertification == null || memberCertification.getStatus() < 1) {
            throw new BusinessException("该条认证已失效，设置默认不成功！");
        }
        // 设置其他认证为非默认
        memberCertificationMapper.clearDefault(id, memberId);
        return memberCertificationMapper.doDefault(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDelete(Long id) {
        return this.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCertificate(MemberCertification certification) {
        Integer count = redisHandler.get("certification_" + certification.getMemberId());
        if (count == null) {
            count = 0;
        }
        if (count >= 5) {
            throw new BusinessException("您今天的验证次数已经超过上限了！");
        }
        MemberCertificationSearcher searcher = new MemberCertificationSearcher();
        searcher.setStatus(1);
        searcher.setMemberId(certification.getMemberId());
        int totalCount = this.countBySearcher(searcher);
        if (totalCount >= 5) {
            throw new BusinessException("最多可添加5个实名认证信息！");
        }
        MemberCertification sameCertification = this.findByMemberIdAndCard(certification.getMemberId(),
                certification.getIdentityCard());
        if (sameCertification != null) {
            throw new BusinessException("已存在相同身份证号的认证信息了！");
        }
        count = count + 1;
        if (count > 0) {
            redisHandler.set("certification_" + certification.getMemberId(), count);
        } else {
            // 一天5次，明天重置
            Date now = new Date();
            redisHandler.setInMinutes("certification_" + certification.getMemberId(), count,
                    DateUtil.dateSubtrationToMin(DateUtil.getEndOfDay(now), now));
        }
        String resultCode = CertificationClient.getInstance().doCertificate(certification.getRealName(),
                certification.getIdentityCard());
        if ("success".equals(resultCode)) {
            certification = this.save(certification);
            // 如果新增的是默认的，就把之前默认去掉
            if (certification.getIsdefault() == 1) {
                memberCertificationMapper.clearDefault(certification.getId(), certification.getMemberId());
            }
            if (certification.getId() > 0) {
                memberInfoService.updateCertificate(certification.getMemberId(), true);
                return 1;
            }
        } else {
            int availableCount = 5 - count;
            if (availableCount > 0) {
                throw new BusinessException(resultCode + "，请检查后重新输入，今日您还有" + availableCount + "次校验机会");
            } else {
                throw new BusinessException(resultCode + "，请检查后重新输入，今日您的校验次数已超限，24小时后重置");
            }
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(MemberCertification certification) {
        return this.updateNotNull(certification);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MemberCertification doInsert(MemberCertification certification) {
        return this.save(certification);
    }

    @Override
    public MemberCertification findByName(String name, Long memberId) {
        return memberCertificationMapper.findByName(name, memberId);
    }

}
