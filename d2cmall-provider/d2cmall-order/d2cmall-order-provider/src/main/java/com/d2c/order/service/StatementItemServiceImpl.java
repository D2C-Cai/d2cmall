package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.StatementLog;
import com.d2c.logger.service.StatementLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.StatementItemMapper;
import com.d2c.order.model.StatementItem;
import com.d2c.order.query.StatementItemSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("statementItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class StatementItemServiceImpl extends ListServiceImpl<StatementItem> implements StatementItemService {

    @Autowired
    private StatementItemMapper statementItemMapper;
    @Autowired
    private StatementService statementService;
    @Autowired
    private StatementLogService statementLogService;

    @Override
    public PageResult<StatementItem> findBySearcher(StatementItemSearcher searcher, PageModel page) {
        PageResult<StatementItem> pager = new PageResult<>(page);
        int totalCount = statementItemMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<StatementItem> list = statementItemMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public StatementItem insert(StatementItem statementItem) {
        StatementItem newItem = findBySnAndBarCode(statementItem.getOrderSn(), statementItem.getBarCode());
        if (newItem != null) {
            throw new BusinessException("已有相同订单和条码的对账单明细，请勿重复导入！");
        }
        return this.save(statementItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteById(Long id, String operator) {
        StatementItem statementItem = this.findById(id);
        if (statementItem == null) {
            throw new BusinessException("对账单明细不存在");
        }
        if (statementItem.getStatus() > 0) {
            throw new BusinessException("非待发送状态不能删除");
        }
        int result = statementItemMapper.deleteById(id);
        if (result > 0) {
            // 驳回情况下，更新对账单的数量和结算总金额，售价总金额
            if (statementItem.getStatementId() != null) {
                statementService.updateSettle(
                        statementItem.getSettlePrice().multiply(new BigDecimal(statementItem.getQuantity() * (-1))),
                        statementItem.getTagPrice().multiply(new BigDecimal((-1) * statementItem.getQuantity())),
                        statementItem.getQuantity() * (-1), operator, statementItem.getStatementId());
            }
            String info = "订单编号:" + statementItem.getOrderSn() + "款号：" + statementItem.getBarCode() + "的对账单明细已删除";
            StatementLog statementLog = new StatementLog(StatementLog.LogType.Delete.name(), info, null, id, operator);
            statementLogService.insert(statementLog);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBack(Long statementId, String operator) {
        return this.updateByStatementId(statementId, StatementItem.ItemStaus.INIT.getCode(),
                StatementItem.ItemStaus.WAITCONFIRM.getCode(), operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doConfirm(Long statementId, String operator) {
        return this.updateByStatementId(statementId, StatementItem.ItemStaus.WAITPAY.getCode(),
                StatementItem.ItemStaus.WAITCONFIRM.getCode(), operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSend(Long statementId, int settleYear, int settleMonth, int periodOfMonth, String fromType,
                      Long designerId, String operator) {
        return statementItemMapper.doSend(statementId, settleYear, settleMonth, periodOfMonth, fromType, designerId,
                operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateByStatementId(Long id, Integer status, Integer oldStatus, String operator) {
        if (id == null || status == null) {
            return 0;
        } else {
            return statementItemMapper.updateByStatementId(id, status, oldStatus, operator);
        }
    }

    @Override
    public StatementItem findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSettleDate(Integer settleYear, Integer settleMonth, Integer settleDay, Long id, String operator) {
        StatementItem statementItem = this.findById(id);
        if (statementItem.getStatementId() != null) {
            throw new BusinessException("已生成对账单，不能更改此对账单明细的结算时间！");
        }
        int result = statementItemMapper.updateSettleDate(settleYear, settleMonth, settleDay, id, operator);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSuccess(Long statementId, String operator) {
        return statementItemMapper.updateByStatementId(statementId, StatementItem.ItemStaus.SUCCESS.getCode(),
                StatementItem.ItemStaus.WAITPAY.getCode(), operator);
    }

    @Override
    public StatementItem findBySnAndBarCode(String orderSn, String barCode) {
        StatementItem statementItem = statementItemMapper.findBySnAndBarCode(orderSn, barCode);
        return statementItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSettlePrice(Long statementItemId, BigDecimal settlePrice, String username) {
        StatementItem statementItem = this.findById(statementItemId);
        if (statementItem == null) {
            throw new BusinessException("对账单明细不存在");
        }
        if (statementItem.getStatus() > 0) {
            throw new BusinessException("非待发送状态不能修改结算单价");
        }
        if (statementItem.getProductPrice().compareTo(settlePrice) < 0) {
            throw new BusinessException("结算价不能大于销售价");
        }
        BigDecimal oldSettlePrice = (statementItem.getSettlePrice() == null ? new BigDecimal(0)
                : statementItem.getSettlePrice());
        // 值不变的不用更新
        if (oldSettlePrice.compareTo(settlePrice) == 0) {
            return 1;
        }
        if (settlePrice.compareTo(new BigDecimal(0)) <= 0) {
            throw new BusinessException("结算单价不能小于0");
        }
        int result = statementItemMapper.updateSettlePrice(statementItemId, settlePrice, username);
        if (result > 0) {
            statementService.updateSettle(
                    settlePrice.subtract(oldSettlePrice).multiply(new BigDecimal(statementItem.getQuantity())), null,
                    null, username, statementItem.getStatementId());
            String info = "结算单价修改，原结算单价：" + oldSettlePrice + "修改结算单价为 ：" + settlePrice;
            StatementLog statementLog = new StatementLog(StatementLog.LogType.Modify.name(), info, null,
                    statementItemId, username);
            statementLogService.insert(statementLog);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateAdminMemo(Long statementItemId, String memo, String operator) {
        StatementItem statementItem = this.findById(statementItemId);
        int result = statementItemMapper.updateAdminMemo(statementItemId, memo);
        if (result > 0) {
            String info = "客服明细备注修改："
                    + (statementItem.getRemark() == null ? "" : ("原始：" + statementItem.getRemark() + "，")) + " 修改为："
                    + memo;
            StatementLog statementLog = new StatementLog(StatementLog.LogType.Modify.name(), info,
                    statementItem.getStatementId(), statementItemId, operator);
            statementLogService.insert(statementLog);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateDesignerMemo(Long statementItemId, String designerMemo, String operator) {
        StatementItem statementItem = this.findById(statementItemId);
        int result = statementItemMapper.updateDesignerMemo(statementItemId, designerMemo);
        if (result > 0) {
            String info = "设计师明细备注修改："
                    + (statementItem.getDesignerMemo() == null ? "" : ("原始：" + statementItem.getDesignerMemo() + "，"))
                    + " 修改为：" + designerMemo;
            StatementLog statementLog = new StatementLog(StatementLog.LogType.Modify.name(), info,
                    statementItem.getStatementId(), statementItemId, operator);
            statementLogService.insert(statementLog);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doReceive(Long statementId, String operator) {
        return statementItemMapper.updateByStatementId(statementId, StatementItem.ItemStaus.WAITCONFIRM.getCode(),
                StatementItem.ItemStaus.WAITSIGN.getCode(), operator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int reSend(Long statementId, String operator) {
        return statementItemMapper.updateByStatementId(statementId, StatementItem.ItemStaus.WAITSIGN.getCode(),
                StatementItem.ItemStaus.INIT.getCode(), operator);
    }

    @Override
    public StatementItem findLastOne(Integer type) {
        StatementItem statement = statementItemMapper.findLastOne(type);
        return statement;
    }

    @Override
    public int countBySearcher(StatementItemSearcher searcher) {
        return statementItemMapper.countBySearcher(searcher);
    }

    @Override
    public List<StatementItem> findEmptySettle(int settleYear, int settleMonth, int periodOfMonth, String fromType,
                                               Long designerId) {
        return statementItemMapper.findEmptySettle(settleYear, settleMonth, periodOfMonth, fromType, designerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doRetreat(Long statementId, String operator) {
        return statementItemMapper.doRetreat(statementId, operator);
    }

}
