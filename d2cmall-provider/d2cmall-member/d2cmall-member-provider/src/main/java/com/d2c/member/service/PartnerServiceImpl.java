package com.d2c.member.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.PartnerLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.PartnerLogService;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.dao.PartnerMapper;
import com.d2c.member.model.*;
import com.d2c.member.query.PartnerCounselorSearcher;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("partnerService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerServiceImpl extends ListServiceImpl<Partner> implements PartnerService {

    @Autowired
    private PartnerMapper partnerMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private PartnerInviteService partnerInviteService;
    @Autowired
    private PartnerStoreService partnerStoreService;
    @Autowired
    private PartnerLogService partnerLogService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private PartnerCounselorService partnerCounselorService;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Partner insert(Partner partner) {
        Member member = memberService.findPartnerOpenId(partner.getMemberId());
        if (member != null) {
            partner.setOpenId(member.getPartnerOpenId());
        }
        partner = this.save(partner);
        memberInfoService.doBindPartner(partner.getMemberId(), partner.getId());
        if (partner.getId() > 0) {
            PartnerStore partnerStore = new PartnerStore();
            partnerStore.setMemberId(partner.getMemberId());
            partnerStore.setPartnerId(partner.getId());
            partnerStore.setCreator(partner.getLoginCode());
            partnerStore.setPic(partner.getHeadPic());
            partnerStore.setName(partner.getName() + "的买手店");
            partnerStoreService.insert(partnerStore);
            PartnerLog log = new PartnerLog();
            log.setPartnerId(partner.getId());
            log.setType("创建");
            StringBuilder builder = new StringBuilder();
            builder.append("手机：" + partner.getLoginCode());
            builder.append(" 昵称：" + partner.getName());
            builder.append(" 等级：" + partner.getLevel());
            log.setInfo(builder.toString());
            log.setCreator(partner.getCreator());
            partnerLogService.insert(log);
            PartnerCounselorSearcher searcher = new PartnerCounselorSearcher();
            searcher.setStatus(1);
            PageResult<PartnerCounselor> pager = partnerCounselorService.findBySearcher(new PageModel(1, 10), searcher);
            if (pager.getTotalCount() > 0) {
                PartnerCounselor partnerCounselor = pager.getList()
                        .get(partner.getId().intValue() % pager.getList().size());
                SmsBean smsBean = new SmsBean("86", partner.getLoginCode(), SmsLogType.REMIND,
                        "【D2C】亲爱的" + partner.getName() + "，欢迎您加入D2C买手店，我是您的私人顾问，微信号" + partnerCounselor.getWeixin()
                                + "。为了更好的帮助您了解D2C，我们创建了专门指导群，请联系我邀请您进群。");
                msgUniteService.sendMsg(smsBean, null, null, null);
            }
        }
        return partner;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Partner partner) {
        int result = this.updateNotNull(partner);
        if (result > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(partner.getId());
            log.setType("修改信息");
            StringBuilder builder = new StringBuilder();
            builder.append("真实姓名：" + partner.getRealName());
            builder.append(" 身份证号：" + partner.getIdentityCard());
            builder.append(" 银行卡号：" + partner.getBankSn());
            builder.append(" 开户行：" + partner.getBank());
            builder.append(" 支付宝：" + partner.getAlipay());
            log.setInfo(builder.toString());
            log.setCreator(partner.getLastModifyMan());
            partnerLogService.insert(log);
        }
        return result;
    }

    @Override
    public Partner findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public Partner findByLoginCode(String loginCode) {
        return partnerMapper.findByLoginCode(loginCode);
    }

    @Override
    public Partner findByMemberId(Long memberId) {
        return partnerMapper.findByMemberId(memberId);
    }

    @Override
    public Partner findContract(String identityCard) {
        return partnerMapper.findContract(identityCard);
    }

    @Override
    public PageResult<Partner> findBySearcher(PartnerSearcher searcher, PageModel page) {
        PageResult<Partner> pager = new PageResult<>(page);
        int totalCount = partnerMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Partner> list = partnerMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(PartnerSearcher searcher) {
        return partnerMapper.countBySearcher(searcher);
    }

    @Override
    public PageResult<Partner> findExpired(PageModel page) {
        PageResult<Partner> pager = new PageResult<>(page);
        int totalCount = partnerMapper.countExpired();
        if (totalCount > 0) {
            List<Partner> list = partnerMapper.findExpired(page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countExpired() {
        return partnerMapper.countExpired();
    }

    @Override
    public List<Long> findVaildIds() {
        return partnerMapper.findVaildIds();
    }

    @Override
    public List<Map<String, Object>> countChildrenGroup(Long id, String rid) {
        return partnerMapper.countChildrenGroup(id, rid);
    }

    @Override
    public List<Map<String, Object>> countChildrenToday(Long id, String rid, Date startDate, Date endDate) {
        return partnerMapper.countChildrenToday(id, rid, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> findCountGroupByLevel(Date beginDate, Date endDate) {
        return partnerMapper.findCountGroupByLevel(beginDate, endDate);
    }

    @Override
    public List<Map<String, Object>> findCountGroupByStatus() {
        return partnerMapper.findCountGroupByStatus();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String operator) {
        int result = partnerMapper.updateStatus(id, status, operator);
        if (result > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("更新状态");
            log.setInfo("状态：" + status);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doChangeMobile(Long memberId, String newMobile) {
        return partnerMapper.doChangeMobile(memberId, newMobile);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doActive(Long id, String operator) {
        int result = partnerMapper.updateStatus(id, 1, operator);
        if (result > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("试用期通过");
            log.setInfo("试用期开单，试用期通过");
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCancel(Long id, Long memberId, String operator) {
        int result = this.deleteById(id);
        if (result > 0) {
            memberInfoService.doCancelPartner(memberId);
            memberInfoService.doCancelParent(id);
            partnerInviteService.cancelToAward(id);
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("未通过试用期");
            log.setInfo("买手身份取消，邀请的买手和会员关系解除");
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCreate(Long parentId, Long memberId, int level) {
        MemberInfo memberInfo = memberInfoService.findById(memberId);
        Partner partner = new Partner(memberInfo, level);
        Partner parent = this.findById(parentId);
        if (parent != null && parent.getStatus() > 0) {
            partner.setParentId(parentId);
            if (parent.getLevel() == 0) {
                partner.setMasterId(parentId);
            } else {
                partner.setMasterId(parent.getMasterId());
            }
            partner.setGiftCount(1);
            partner.setPointCount(Partner.POINT_GIFT_RATIO);
            partner = this.insert(partner);
            String path = partner.getId().toString();
            if (parent.getPath() != null) {
                path = path + "," + parent.getPath();
            }
            this.updatePath(partner.getId(), path);
            PartnerInvite partnerInvite = new PartnerInvite(parent, partner);
            partnerInviteService.insert(partnerInvite);
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPass(Long id, Long memberId, String operator) {
        int result = partnerMapper.updateStatus(id, 1, operator);
        if (result > 0) {
            memberInfoService.doBindPartner(memberId, id);
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("试用期复活");
            log.setInfo("未通过开单，试用期复活");
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRebate(Long id, BigDecimal amount, BigDecimal actualAmount, String operator) {
        int result = partnerMapper.doRebate(id, amount, actualAmount);
        if (result > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("获得返利");
            log.setInfo("返利金额：" + amount + " 实际支付：" + actualAmount);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doAward(Long partnerId, BigDecimal amount, String operator) {
        int success = partnerMapper.doAward(partnerId, amount);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(partnerId);
            log.setType("获得奖励");
            log.setInfo("奖励金额：" + amount);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doGift(Long partnerId, BigDecimal amount, String operator) {
        int success = partnerMapper.doGift(partnerId, amount);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(partnerId);
            log.setType("礼包奖励");
            log.setInfo("奖励金额：" + amount);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doApplyCash(Long id, BigDecimal amount, Integer direction) {
        return partnerMapper.doApplyCash(id, amount, direction);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doWithdCash(Long id, BigDecimal amount, BigDecimal applyAmount, String operator) {
        int success = partnerMapper.doWithdCash(id, amount, applyAmount);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("提现");
            log.setInfo("提现金额：" + amount);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindCounselor(Long id, Long counselorId, String operator) {
        int success = partnerMapper.doBindCounselor(id, counselorId);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("运营顾问");
            log.setInfo("选择运营顾问：" + counselorId);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doContract(Long id, Integer contract) {
        return partnerMapper.doContract(id, contract);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateConsumeDate(Long id, int status) {
        Date now = new Date();
        int first = partnerMapper.updateFirstDate(id, now);
        int success = partnerMapper.updateConsumeDate(id, now);
        // 试用期分销开单转正
        if (first == 1 && status == 0) {
            this.doActive(id, "sys");
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStoreId(Long id, Long storeId, String operator) {
        int success = partnerMapper.updateStoreId(id, storeId);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("申请店铺");
            log.setInfo("店铺ID：" + storeId);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateLevel(Long id, Integer level, String operator) {
        int success = partnerMapper.updateLevel(id, level);
        if (success > 0) {
            if (level < 2) {
                partnerMapper.updateStatus(id, 1, operator);
            }
            // if (level == 0) {
            // Partner partner = this.findById(id);
            // if (partner.getMasterId() != null) {
            // partnerMapper.updateGiftCount(partner.getMasterId(), -1 *
            // partner.getGiftCount());
            // }
            // }
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("更新等级");
            log.setInfo("等级：" + level);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateOpenId(Long id, String openId) {
        int success = partnerMapper.updateOpenId(id, openId);
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSeparate(Long id, Long childId, String operator) {
        int success = partnerMapper.doSeparate(childId);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("删除下级");
            log.setInfo("下级ID：" + childId);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        partnerMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doDelete(String loginCode) {
        return partnerMapper.doDelete(loginCode);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doWithhold(Long partnerId, BigDecimal amount, String operator, String type) {
        int success = partnerMapper.doWithhold(partnerId, amount);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(partnerId);
            log.setType(type);
            log.setInfo("扣款金额：" + amount);
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doBindStoreMark(Long id, Integer mark) {
        return this.updateFieldById(id.intValue(), "store_mark", mark);
    }

    @Override
    public List<Partner> findCancelRemind(Integer intervalDays) {
        return partnerMapper.findCancelRemind(intervalDays);
    }

    @Override
    public List<String> findOpenIdByMemberId(List<Long> ids) {
        return partnerMapper.findOpenIdByMemberId(ids);
    }

    @Override
    public List<String> findTags() {
        return partnerMapper.findTags();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateTags(Long id, String tags) {
        return partnerMapper.updateTags(id, tags);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updatePrestore(Long id, Integer count, String operator) {
        int success = partnerMapper.updatePrestore(id, count);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("预存礼包");
            log.setInfo("更新礼包个数：" + count + "个");
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int cancelPrestore(Long id, String operator) {
        int success = partnerMapper.cancelPrestore(id);
        if (success > 0) {
            PartnerLog log = new PartnerLog();
            log.setPartnerId(id);
            log.setType("预存礼包");
            log.setInfo("清空预存礼包");
            log.setCreator(operator);
            partnerLogService.insert(log);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updatePath(Long id, String path) {
        return partnerMapper.updatePath(id, path);
    }

    @Override
    public List<Long> findPointRelation(String path) {
        List<Long> ids = StringUtil.strToLongList(path);
        List<Partner> levels = partnerMapper.findLevelsByIds(ids);
        Map<Long, Integer> levelMap = new HashMap<Long, Integer>();
        for (Partner partner : levels) {
            levelMap.put(partner.getId(), partner.getLevel());
        }
        List<Long> result = new ArrayList<>();
        for (Long id : ids) {
            Integer level = levelMap.get(id);
            if (level != null) {
                if (level == 0) {
                    result.add(id);
                    break;
                } else if (level == 1) {
                    result.add(id);
                } else if (level == 2) {
                    continue;
                }
            }
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updatePoint(List<Long> ids, Integer count) {
        return partnerMapper.updatePoint(ids, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateUpgrade(Long id, Integer upgrade) {
        return partnerMapper.updateUpgrade(id, upgrade);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateWithdraw(Long id, Integer withdraw) {
        return partnerMapper.updateWithdraw(id, withdraw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doLogin(Long id) {
        return partnerMapper.doLogin(id);
    }

}
