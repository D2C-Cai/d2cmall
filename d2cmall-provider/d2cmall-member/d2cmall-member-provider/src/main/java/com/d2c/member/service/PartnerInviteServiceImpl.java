package com.d2c.member.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.utils.StringUt;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.WeixinPushService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.third.wechat.WeixinPushEntity;
import com.d2c.member.dao.PartnerInviteMapper;
import com.d2c.member.model.Partner;
import com.d2c.member.model.PartnerInvite;
import com.d2c.member.query.PartnerInviteSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("partnerInviteService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerInviteServiceImpl extends ListServiceImpl<PartnerInvite> implements PartnerInviteService {

    @Autowired
    private PartnerInviteMapper partnerInviteMapper;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private WeixinPushService weixinPushService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PartnerInvite insert(PartnerInvite partnerInvite) {
        partnerInvite = this.save(partnerInvite);
        if (partnerInvite.getId() > 0) {
            Partner from = partnerService.findById(partnerInvite.getFromPartnerId());
            String toStr = partnerInvite.getToLoginCode();
            WeixinPushEntity msgObj = new WeixinPushEntity(from.getOpenId(), "成功邀请" + toStr,
                    partnerInvite.getToNickname(), partnerInvite.getToLoginCode(), new Date(), "",
                    "/pages/member/invite/list");
            weixinPushService.send(msgObj);
            PushBean pushBean = new PushBean(partnerInvite.getFromMemberId(),
                    "成功邀请" + (partnerInvite.getToLevel() == 1 ? "DM" : "买手") + "(" + partnerInvite.getToNickname() + "，"
                            + StringUt.hideMobile(partnerInvite.getToLoginCode()) + ")加入团队！",
                    85);
            pushBean.setAppUrl("/to/partner/team");
            MsgBean msgBean = new MsgBean(partnerInvite.getFromMemberId(), 85, "邀请注册",
                    "成功邀请" + (partnerInvite.getToLevel() == 1 ? "DM" : "买手") + "(" + partnerInvite.getToNickname() + "，"
                            + StringUt.hideMobile(partnerInvite.getToLoginCode()) + ")加入团队！");
            msgBean.setAppUrl("/to/partner/team");
            msgUniteService.sendPush(pushBean, msgBean);
        }
        return partnerInvite;
    }

    @Override
    public PageResult<PartnerInvite> findBySearcher(PartnerInviteSearcher searcher, PageModel page) {
        PageResult<PartnerInvite> pager = new PageResult<>(page);
        int totalCount = partnerInviteMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<PartnerInvite> list = partnerInviteMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(PartnerInviteSearcher searcher) {
        return partnerInviteMapper.countBySearcher(searcher);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doAward(Long id) {
        return partnerInviteMapper.doAward(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRefresh(Long toPartnerId) {
        return partnerInviteMapper.doRefresh(toPartnerId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int cancelFromAward(Long fromPartnerId) {
        return partnerInviteMapper.cancelFromAward(fromPartnerId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int cancelToAward(Long toPartnerId) {
        return partnerInviteMapper.cancelToAward(toPartnerId);
    }

}
