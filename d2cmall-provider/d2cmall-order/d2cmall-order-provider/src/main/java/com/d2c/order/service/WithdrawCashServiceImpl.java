package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.WithdrawCashMapper;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.WithdrawCash;
import com.d2c.order.query.WithdrawCashSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 提现（申请-》已提交-》处理中-》已完成，申请-》已提交-》财务关闭）
 */
@Service("withdrawCashService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class WithdrawCashServiceImpl extends ListServiceImpl<WithdrawCash> implements WithdrawCashService {

    @Autowired
    private WithdrawCashMapper withdrawCashMapper;
    @Autowired
    private AccountItemService accountItemService;

    public WithdrawCash findById(Long id) {
        return withdrawCashMapper.selectByPrimaryKey(id);
    }

    public PageResult<WithdrawCash> findBySearch(WithdrawCashSearcher searcher, PageModel page) {
        PageResult<WithdrawCash> pager = new PageResult<WithdrawCash>(page);
        int totalCount = withdrawCashMapper.countBySearch(searcher);
        if (totalCount > 0) {
            pager.setTotalCount(totalCount);
            pager.setList(withdrawCashMapper.findBySearch(searcher, page));
        }
        return pager;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public WithdrawCash insert(WithdrawCash drawCash) {
        AccountItem item = accountItemService.doWithdrawCash(drawCash);
        if (item != null) {
            drawCash.setBillId(item.getId());
            drawCash = this.save(drawCash);
            if (drawCash.getId() <= 0) {
                throw new BusinessException("提现不成功！");
            }
            accountItemService.updateTransactionInfo(drawCash.getBillId(), drawCash.getId(),
                    "充值信息：" + drawCash.getPaySn());
        }
        return drawCash;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doConfirm(Long id) {
        return withdrawCashMapper.confirm(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doClose(Long id) {
        int result = withdrawCashMapper.close(id);
        if (result > 0) {
            WithdrawCash cash = withdrawCashMapper.selectByPrimaryKey(id);
            result = accountItemService.doCancel(cash.getBillId(), "sys", "");
        }
        return result;
    }

    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSuccess(WithdrawCash drawCash) {
        return withdrawCashMapper.success(drawCash);
    }

}
