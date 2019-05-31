package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.model.RedPacketsItem.BusinessTypeEnum;
import com.d2c.order.model.ShareRedPackets;
import com.d2c.order.model.ShareRedPacketsGroup;
import com.d2c.order.model.ShareRedPacketsPromotion;
import com.d2c.order.service.ShareRedPacketsGroupService;
import com.d2c.order.service.ShareRedPacketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service(protocol = {"dubbo"})
public class ShareRedTxServiceimpl implements ShareRedTxService {

    private static final BigDecimal ONE = new BigDecimal(1);
    private static final BigDecimal TWO = new BigDecimal(2);
    @Autowired
    private ShareRedPacketsService shareRedPacketsService;
    @Autowired
    private ShareRedPacketsGroupService shareRedPacketsGroupService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Reference
    private AccountTxService accountTxService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ShareRedPackets insertShareRedPackets(ShareRedPackets shareRedPackets) {
        int success = shareRedPacketsGroupService.updateNumber(shareRedPackets.getGroupId(),
                shareRedPackets.getNumber());
        if (success > 0) {
            ShareRedPacketsGroup shareRedPacketsGroup = shareRedPacketsGroupService
                    .findById(shareRedPackets.getGroupId());
            shareRedPackets.setMoney(this.splitRedPackets(shareRedPacketsGroup));
            shareRedPackets = shareRedPacketsService.insert(shareRedPackets);
            if (shareRedPackets.getInitiator() != 1
                    && shareRedPackets.getNumber().equals(shareRedPacketsGroup.getMaxNumber())) {
                // 拼团成功
                shareRedPacketsGroupService.updateStatus(shareRedPacketsGroup.getId(), 1);
                shareRedPacketsService.updateStatusByGroupId(shareRedPacketsGroup.getId(), 1);
                this.processSuccess(shareRedPacketsGroup.getId());
            }
        }
        return shareRedPackets;
    }

    /**
     * @Description: 拆分红包
     */
    private BigDecimal splitRedPackets(final ShareRedPacketsGroup shareRedPacketsGroup) {
        BigDecimal MAXMONEY = shareRedPacketsGroup.getMaxMoney();
        BigDecimal MINMONEY = shareRedPacketsGroup.getMinMoney();
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        BigDecimal remainSize = new BigDecimal(
                shareRedPacketsGroup.getMaxNumber() - shareRedPacketsGroup.getCurrentNumber()).add(ONE);
        BigDecimal freezeSumMoney = shareRedPacketsService.sumMoneyByGroupId(shareRedPacketsGroup.getId());
        BigDecimal remainMoney = shareRedPacketsGroup.getToltalMoney()
                .subtract(freezeSumMoney == null ? new BigDecimal(0) : freezeSumMoney);
        BigDecimal mustMinMoney = remainMoney.subtract(MAXMONEY.multiply(remainSize.subtract(ONE)));
        mustMinMoney = mustMinMoney.compareTo(MINMONEY) >= 0 ? mustMinMoney : MINMONEY;
        if (remainSize.compareTo(ONE) == 0) {
            remainSize = remainSize.subtract(ONE);
            BigDecimal result = remainMoney.multiply(new BigDecimal(100)).divide(new BigDecimal(100));
            result = result.setScale(0, BigDecimal.ROUND_HALF_UP);
            return result;
        }
        Random r = new Random();
        BigDecimal max = remainMoney.divide(remainSize, 1).multiply(TWO);
        BigDecimal money = new BigDecimal(r.nextDouble()).multiply(max);
        money = money.compareTo(mustMinMoney) <= 0 ? mustMinMoney : money;
        money = money.compareTo(MAXMONEY) >= 0 ? MAXMONEY : money;
        money = money.setScale(0, BigDecimal.ROUND_HALF_UP);
        return money;
    }

    private void processSuccess(Long groupId) {
        List<ShareRedPackets> list = shareRedPacketsService.findByGroupId(groupId, null);
        for (ShareRedPackets s : list) {
            accountTxService.doSuccessRed(s.getMemberId(), BusinessTypeEnum.SHARE.name(), s.getMoney());
            String subject = "恭喜你瓜分红包成功";
            String content = "恭喜！您与其他" + (list.size() - 1) + "个伙伴一起瓜分红包成功，获得" + s.getMoney() + "元红包！记得使用哦";
            PushBean pushBean = new PushBean(s.getMemberId(), content, 29);
            pushBean.setAppUrl("/shareredpackets/" + s.getGroupId());
            MsgBean msgBean = new MsgBean(s.getMemberId(), 29, subject, content);
            msgBean.setAppUrl("/shareredpackets/" + s.getGroupId());
            SmsBean smsBean = new SmsBean(null, s.getLoginCode(), SmsLogType.REMIND, content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
        }
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ShareRedPacketsGroup createGroup(Long memberId, ShareRedPacketsPromotion shareRedPacketsPromotion) {
        ShareRedPacketsGroup shareRedPacketsGroup = new ShareRedPacketsGroup(shareRedPacketsPromotion, memberId);
        shareRedPacketsGroup = shareRedPacketsGroupService.insert(shareRedPacketsGroup);
        if (shareRedPacketsGroup != null && shareRedPacketsGroup.getId() != null) {
            ShareRedPackets myShareRedPackets = new ShareRedPackets(shareRedPacketsGroup);
            myShareRedPackets.setNumber(1);
            myShareRedPackets.setInitiator(1);
            myShareRedPackets.setMemberId(shareRedPacketsGroup.getInitiatorMemberId());
            MemberInfo memberInfo = memberInfoService.findById(memberId);
            myShareRedPackets.setLoginCode(memberInfo.getLoginCode());
            myShareRedPackets.setHeadPic(memberInfo.getHeadPic());
            myShareRedPackets.setNickName(memberInfo.getNickname());
            this.insertShareRedPackets(myShareRedPackets);
        }
        return shareRedPacketsGroup;
    }

}
