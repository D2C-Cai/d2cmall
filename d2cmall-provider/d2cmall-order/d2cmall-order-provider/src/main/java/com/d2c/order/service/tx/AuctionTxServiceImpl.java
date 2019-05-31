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
import com.d2c.member.support.OrderInfo;
import com.d2c.order.model.AuctionOffer;
import com.d2c.order.service.AuctionMarginService;
import com.d2c.order.service.AuctionOfferService;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.service.AuctionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service(protocol = {"dubbo"})
public class AuctionTxServiceImpl implements AuctionTxService {

    @Autowired
    private AuctionMarginService auctionMarginService;
    @Autowired
    private AuctionOfferService auctionOfferService;
    @Autowired
    private AuctionProductService auctionProductService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Reference
    private AccountTxService accountTxService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AuctionOffer insertOffer(AuctionProduct product, Long memberInfoId, BigDecimal offer, Long marginId) {
        MemberInfo memberInfo = memberInfoService.findById(memberInfoId);
        AuctionOffer auctionOffer = new AuctionOffer(memberInfo);
        auctionOffer.setAuctionId(product.getId());
        auctionOffer.setAuctionProductId(product.getProductId());
        auctionOffer.setAuctionTitle(product.getTitle());
        auctionOffer.setMemberId(memberInfoId);
        auctionOffer.setLoginCode(memberInfo.getLoginCode());
        auctionOffer.setMemberNick(memberInfo.getDisplayName());
        auctionOffer.setOffer(offer);
        // 新插入的出价记录必然是价格最高的
        auctionOffer.setStatus(1);
        int result = auctionProductService.updateCurrentPrice(product.getId(), offer, marginId, memberInfoId);
        if (result > 0) {
            // 在插入时会先将其他拍卖纪录出局，即状态设为0
            auctionOfferService.doOut(auctionOffer.getAuctionId(), auctionOffer.getOffer());
            auctionOffer = auctionOfferService.insert(auctionOffer);
        }
        return auctionOffer;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doEndAuction(AuctionProduct auctionProduct) {
        Date now = new Date();
        int status = auctionMarginService.getMaxStatus(auctionProduct.getId());
        int result = 0;
        if (auctionProduct.getEndDate().getTime() <= now.getTime() && status < 2) {
            result = auctionMarginService.doSuccess(auctionProduct.getAuctionMarginId(),
                    auctionProduct.getCurrentPrice());
            if (result > 0) {
                auctionMarginService.doOut(auctionProduct.getId());
                AuctionOffer auctionOffer = auctionOfferService.findTopByAuctionId(auctionProduct.getId());
                auctionOfferService.doSuccess(auctionOffer.getId());
                auctionProductService.updateOfferId(auctionProduct.getId(), auctionOffer.getId());
                this.doSendMarginMsg(auctionOffer.getMemberId(), auctionOffer.getAuctionId(),
                        auctionOffer.getAuctionTitle(), "SUCCESS");
            }
        }
        return result;
    }

    private void doSendMarginMsg(Long memberId, Long auctionId, String auctionTitle, String type) {
        MemberInfo memberInfo = memberInfoService.findById(memberId);
        if ("SUCCESS".equalsIgnoreCase(type)) {
            String subject = "中拍提醒";
            String content = "尊敬的顾客，恭喜您！您在[" + auctionTitle + "]的拍卖活动中竞拍得标了！请您在24小时内按照成交价格支付拍品余款，如未按时支付将扣除缴纳的保证金！";
            PushBean pushBean = new PushBean(memberInfo.getId(), content, 29);
            MsgBean msgBean = new MsgBean(memberInfo.getId(), 29, subject, content);
            SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(), SmsLogType.REMIND,
                    content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
        } else {
            String subject = "违约提醒";
            String content = "尊敬的顾客，你在[" + auctionTitle + "]的拍卖活动中拍了，但在24小时内未支付余款，因此已将你的保证金扣为违约金！";
            PushBean pushBean = new PushBean(memberInfo.getId(), content, 10);
            MsgBean msgBean = new MsgBean(memberInfo.getId(), 10, subject, content);
            SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(), SmsLogType.REMIND,
                    content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
        }
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBackToWallet(OrderInfo orderInfo, Long marginId, String refundType, String refundSn, String refunder) {
        accountTxService.doRefund(orderInfo);
        return auctionMarginService.doBackMargin(marginId, refundType, refundSn, refunder);
    }

}
