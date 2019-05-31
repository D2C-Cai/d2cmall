package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.RechargeMapper;
import com.d2c.order.dto.RechargeDto;
import com.d2c.order.dto.RechargeRuleDto;
import com.d2c.order.handle.XonYProcessPriceBehavior;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.Recharge;
import com.d2c.order.query.RechargeSearcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("rechargeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RechargeServiceImpl extends ListServiceImpl<Recharge> implements RechargeService {

    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private RechargeRuleService rechargeRuleService;
    @Autowired
    private AccountItemService accountItemService;

    @Override
    public Recharge findById(Long id) {
        return this.rechargeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Recharge findBySn(String sn) {
        return rechargeMapper.findBySn(sn);
    }

    @Override
    public PageResult<RechargeDto> findBySearch(RechargeSearcher searcher, PageModel page) {
        PageResult<RechargeDto> pager = new PageResult<>(page);
        int totalCount = rechargeMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Recharge> values = rechargeMapper.findBySearch(searcher, page);
            List<RechargeDto> dtos = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                RechargeDto dto = new RechargeDto();
                BeanUtils.copyProperties(values.get(i), dto);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public int countBySearch(RechargeSearcher searcher) {
        int totalCount = rechargeMapper.countBySearch(searcher);
        return totalCount;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Recharge insert(Recharge recharge, boolean onlineRecharge) {
        RechargeSearcher searcher = new RechargeSearcher();
        searcher.setPayAccount(recharge.getPayAccount());
        searcher.setPayChannel(recharge.getPayChannel());
        searcher.setPaySn(recharge.getPaySn());
        int exist = rechargeMapper.countBySearch(searcher);
        if (exist > 0) {
            throw new BusinessException("充值不成功，请勿重复充值！");
        }
        if (StringUtils.isBlank(recharge.getPayType())) {
            recharge.setPayType(PayTypeEnum.RECHARGE.name());
        }
        // 充值类型为赠送，或者充值本金不大于0的，直接跳过充值规则
        if (recharge.getRechargeAmount().compareTo(new BigDecimal(0)) > 0) {
            RechargeRuleDto rule = rechargeRuleService.findValidRule();
            if (rule != null) {
                if (rule.getProcessMethod() != null && rule.getProcessMethod() instanceof XonYProcessPriceBehavior) {
                    rule.getProcessMethod().process(rule, recharge);
                }
            }
        }
        if (onlineRecharge == true) {
            recharge.setMemo("App钱包在线充值");
        }
        AccountItem item = accountItemService.doRecharge(recharge);
        recharge.setBillId(item.getId());
        recharge = this.save(recharge);
        accountItemService.updateTransactionInfo(recharge.getBillId(), recharge.getId(), "充值信息：" + recharge.getPaySn());
        return recharge;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doSubmit(Long id, String submitor, String paySn) {
        return rechargeMapper.doSubmit(id, submitor, paySn);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doClose(Long rechargeId, String closer, String closeReason) {
        Recharge recharge = rechargeMapper.selectByPrimaryKey(rechargeId);
        if (recharge.getStatus() > 0) {
            throw new BusinessException("订单已审核，取消不成功！");
        }
        if (recharge.getStatus() < 0) {
            throw new BusinessException("订单已取消，不可重复取消！");
        }
        int result = rechargeMapper.doClose(recharge.getId(), closer, closeReason);
        if (result > 0) {
            this.accountItemService.doCancel(recharge.getBillId(), recharge.getCloser(), recharge.getCloseReason());
        }
        return result;
    }

}
