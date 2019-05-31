package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.model.MemberLotto;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.AwardRecordMapper;
import com.d2c.order.dto.AwardRecordDto;
import com.d2c.order.model.AwardRecord;
import com.d2c.order.query.AwardRecordSearcher;
import com.d2c.product.model.AwardProduct;
import com.d2c.product.model.AwardSession;
import com.d2c.product.service.AwardProductService;
import com.d2c.product.service.AwardSessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "awardRecordService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AwardRecordServiceImpl extends ListServiceImpl<AwardRecord> implements AwardRecordService {

    @Autowired
    private AwardRecordMapper awardRecordMapper;
    @Autowired
    private AwardProductService awardProductService;
    @Autowired
    private AwardSessionService awardSessionService;

    @Override
    public List<AwardRecord> findListByRecently(Long sessionId) {
        return awardRecordMapper.findListByRecently(sessionId);
    }

    @Override
    public PageResult<AwardRecordDto> findBySearcher(AwardRecordSearcher searcher, PageModel page) {
        PageResult<AwardRecordDto> pager = new PageResult<>(page);
        int count = awardRecordMapper.countBySearcher(searcher);
        List<AwardRecordDto> dtos = new ArrayList<AwardRecordDto>();
        if (count > 0) {
            Map<Long, AwardSession> awardSessions = new HashMap<Long, AwardSession>();
            List<AwardRecord> list = awardRecordMapper.findBySearcher(searcher, page);
            for (AwardRecord awardRecord : list) {
                AwardRecordDto dto = new AwardRecordDto();
                BeanUtils.copyProperties(awardRecord, dto);
                if (awardRecord.getSessionId() != null && awardSessions.get(awardRecord.getSessionId()) == null) {
                    AwardSession awardSession = awardSessionService.findById(awardRecord.getSessionId());
                    dto.setAwardSession(awardSession);
                } else {
                    dto.setAwardSession(awardSessions.get(awardRecord.getSessionId()));
                }
                dtos.add(dto);
            }
        }
        pager.setList(dtos);
        pager.setTotalCount(count);
        return pager;
    }

    @Override
    public int countBySearcher(AwardRecordSearcher searcher) {
        return awardRecordMapper.countBySearcher(searcher);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public AwardRecord insert(AwardRecord awardRecord) {
        AwardRecord record = save(awardRecord);
        return record;
    }

    @Override
    public AwardRecord doStartLottery(MemberLotto member) {
        AwardRecord awardRecord = new AwardRecord();
        List<AwardProduct> awardList = null;
        awardList = awardProductService.findBySessionIdAndNow(member.getSessionId());
        // 排除掉库存为0的奖品
        awardList = awardList.stream().filter(a -> a.getQuantity() > 0 && a.getWeight() > 0)
                .collect(Collectors.toList());
        if (awardList == null || awardList.size() == 0) {
            throw new BusinessException("您来晚了，所有奖品已经发放完了");
        }
        final Map<Long, Integer> awardLotteryWeight = new LinkedHashMap<>();
        int totalWeight = 0;
        for (AwardProduct award : awardList) {
            totalWeight += award.getWeight();
            awardLotteryWeight.put(award.getId(), totalWeight);
        }
        int randNum = new Random().nextInt(totalWeight);
        Long choosedAward = null;
        // 按照权重计算中奖区间
        for (Map.Entry<Long, Integer> e : awardLotteryWeight.entrySet()) {
            if (randNum >= 0 && randNum < e.getValue()) {
                choosedAward = e.getKey(); // 落入该奖品区间
                AwardProduct award = awardProductService.findById(choosedAward);
                awardRecord = initAwardRecord(award, member);
                break;
            }
        }
        return awardRecord;
    }

    /**
     * 初始化抽奖记录
     *
     * @param awardProduct
     * @param memberLotto
     * @return
     */
    private AwardRecord initAwardRecord(AwardProduct awardProduct, MemberLotto memberLotto) {
        AwardRecord awardRecord = new AwardRecord();
        awardRecord.setLevelName(awardProduct.getLevelName());
        awardRecord.setLotteryTime(new Date());
        awardRecord.setAwardId(awardProduct.getId());
        awardRecord.setAwardLevel(awardProduct.getLevel());
        awardRecord.setAwardName(awardProduct.getName());
        awardRecord.setAwardProductType(awardProduct.getType());
        awardRecord.setAwardProductParam(awardProduct.getParam());
        awardRecord.setAwardPic(awardProduct.getPic());
        awardRecord.setLotteryTime(new Date());
        awardRecord.setMemberId(memberLotto.getMemberId());
        awardRecord.setHeadPic(memberLotto.getHeadPic());
        awardRecord.setMemberName(memberLotto.getMemberName());
        awardRecord.setModifyDate(new Date());
        awardRecord.setCreateDate(new Date());
        awardRecord.setLoginNo(memberLotto.getLoginNo());
        awardRecord.setSessionId(memberLotto.getSessionId());
        if (0 >= awardProduct.getQuantity()) {// 需要判断数量是否充足,不充足则用权重最高的商品来代替
            AwardProduct specialAward = awardProductService.findByMaxWeight(memberLotto.getSessionId());
            awardRecord.setAwardLevel(specialAward.getLevel());
            awardRecord.setLevelName(awardProduct.getLevelName());
            awardRecord.setAwardName(specialAward.getName());
            awardRecord.setAwardId(specialAward.getId());
            awardRecord.setAwardProductType(awardProduct.getType());
            awardRecord.setAwardProductParam(awardProduct.getParam());
        }
        return awardRecord;
    }

    @Override
    public List<AwardRecord> findByMemberIdAndSessionId(Long memberId, Long sessionId) {
        return awardRecordMapper.findByMemberIdAndSessionId(memberId, sessionId);
    }

    @Override
    public List<String> findAwardLevelName() {
        return awardRecordMapper.findAwardLevelName();
    }

}
