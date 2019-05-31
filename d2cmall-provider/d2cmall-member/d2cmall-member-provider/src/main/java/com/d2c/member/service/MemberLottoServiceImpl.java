package com.d2c.member.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.member.dao.MemberLottoMapper;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberLotto;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberLottoService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberLottoServiceImpl extends ListServiceImpl<MemberLotto> implements MemberLottoService {

    @Autowired
    private MemberLottoMapper memberLottoMapper;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateMemberLottery(Long memberId, MemberLotto.LotteryOpportunityEnum lotteryOpportunityEnum,
                                   Long sessionId) {
        MemberLotto memberLotto = memberLottoMapper.findByMemberIdAndSessionId(memberId, sessionId);
        MemberInfo memberInfo = memberInfoService.findById(memberId);
        if (memberLotto == null) {// 为空需要新增
            memberLotto = new MemberLotto();
            memberLotto.setMemberId(memberInfo.getId());
            memberLotto.setLoginNo(memberInfo.getLoginCode());
            memberLotto.setMemberName(memberInfo.getNickname());
            memberLotto.setSessionId(sessionId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(lotteryOpportunityEnum.name(), lotteryOpportunityEnum.getCount());
            memberLotto.setOwnCount(lotteryOpportunityEnum.getCount());
            // if
            // (!LotteryOpportunityEnum.BARGAIN.equals(lotteryOpportunityEnum))
            // {// 查看该会员的砍价记录，有的话增加一次机会
            // long totalCount = bargainMongoDao.countMine(memberId);
            // if (totalCount > 0) {
            // jsonObject.put(LotteryOpportunityEnum.BARGAIN.name(), 1);
            // memberLotto.setOwnCount(2);
            // }
            // }
            memberLotto.setLotterySource(jsonObject.toJSONString());
            memberLotto.setHeadPic(memberInfo.getHeadPic());
            this.save(memberLotto);
        } else {
            JSONObject jsonObject = JSON.parseObject(memberLotto.getLotterySource());
            if (!jsonObject.containsKey(lotteryOpportunityEnum.name())) {
                jsonObject.put(lotteryOpportunityEnum.name(), lotteryOpportunityEnum.getCount());
                memberLottoMapper.updateMemberLotteryAddCount(memberLotto.getId(), jsonObject.toJSONString(),
                        lotteryOpportunityEnum.getCount());
            }
        }
        return 1;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDecreaseOpportunity(Long memberId, Long sessionId) {
        return this.memberLottoMapper.doDecreaseOpportunity(memberId, sessionId);
    }

    @Override
    public MemberLotto findByMemberIdAndSessionId(Long memberId, Long sessionId) {
        return memberLottoMapper.findByMemberIdAndSessionId(memberId, sessionId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doClear() {
        return this.deleteAll();
    }

}
